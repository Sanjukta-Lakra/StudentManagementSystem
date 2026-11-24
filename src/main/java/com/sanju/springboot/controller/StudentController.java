package com.sanju.springboot.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.sanju.springboot.entity.Course;
import com.sanju.springboot.entity.Student;
import com.sanju.springboot.enums.AttendanceStatus;
import com.sanju.springboot.repository.AttendanceRepository;
import com.sanju.springboot.repository.CourseRepository;
import com.sanju.springboot.repository.StudentRepository;
import com.sanju.springboot.service.StudentService;

@Controller
public class StudentController
{
    private final StudentService studentService;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final AttendanceRepository attendanceRepository;

    public StudentController(
            StudentService studentService,
            StudentRepository studentRepository,
            CourseRepository courseRepository,
            AttendanceRepository attendanceRepository)
    {
        this.studentService = studentService;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.attendanceRepository = attendanceRepository;
    }

    // Registration
    @PostMapping("/register")
    public String registerStudent(@ModelAttribute Student student, Model model)
    {
        try
        {
            studentService.registerStudent(student);

            model.addAttribute("success",
                    "Registration Successful! Please login.");

            return "login";
        }
        catch (Exception e)
        {
            model.addAttribute("error", e.getMessage());

            return "register";
        }
    }

    // Student Dashboard
    @GetMapping("/student/dashboard")
    public String studentDashboard(Authentication authentication, Model model)
    {
        String email = authentication.getName();

        Student student = studentRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        model.addAttribute("studentName", student.getName());
        model.addAttribute("studentEmail", student.getEmail());
        model.addAttribute("studentPhone", student.getPhone());
        model.addAttribute("studentCourse", student.getCourse());

        // Find course details
        Course course = courseRepository
                .findAll()
                .stream()
                .filter(c ->
                    c.getCourseName()
                     .equalsIgnoreCase(student.getCourse())
                )
                .findFirst()
                .orElse(null);

        model.addAttribute("courseDetails", course);

        return "student-dashboard";
    }

    // Student Attendance
    @GetMapping("/student/attendance")
    public String studentAttendance(Authentication authentication, Model model)
    {
        String email = authentication.getName();

        Student student = studentRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // Get only the logged-in student's attendance
        var attendanceRecords =
                attendanceRepository.findByStudentOrderByAttendanceDateDesc(student);

        // Calculate attendance statistics
        long totalClasses = attendanceRepository.countByStudent(student);

        long presentDays =
                attendanceRepository.countByStudentAndStatus(
                        student, AttendanceStatus.PRESENT);

        long absentDays =
                attendanceRepository.countByStudentAndStatus(
                        student, AttendanceStatus.ABSENT);

        double attendancePercentage = 0.0;

        if (totalClasses > 0)
        {
            attendancePercentage =
                    (presentDays * 100.0) / totalClasses;
        }

        // Send data to the HTML page
        model.addAttribute("studentName", student.getName());
        model.addAttribute("attendanceRecords", attendanceRecords);
        model.addAttribute("totalClasses", totalClasses);
        model.addAttribute("presentDays", presentDays);
        model.addAttribute("absentDays", absentDays);
        model.addAttribute("attendancePercentage",
                String.format("%.2f", attendancePercentage));

        return "student-attendance";
    }
}
