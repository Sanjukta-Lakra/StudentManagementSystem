package com.sanju.springboot.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.sanju.springboot.entity.Course;
import com.sanju.springboot.entity.Student;
import com.sanju.springboot.repository.CourseRepository;
import com.sanju.springboot.repository.StudentRepository;
import com.sanju.springboot.service.StudentService;

@Controller
public class StudentController
{
    private final StudentService studentService;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public StudentController(StudentService studentService, StudentRepository studentRepository, CourseRepository courseRepository)
    {
        this.studentService = studentService;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    // Registration
    @PostMapping("/register")
    public String registerStudent(@ModelAttribute Student student, Model model) 
    {
        try 
        {
            studentService.registerStudent(student);

            model.addAttribute("success", "Registration Successful! Please login.");

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

        Student student = studentRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Student not found"));

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
}