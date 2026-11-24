package com.sanju.springboot.controller;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sanju.springboot.entity.Attendance;
import com.sanju.springboot.entity.Student;
import com.sanju.springboot.enums.AttendanceStatus;
import com.sanju.springboot.repository.StudentRepository;
import com.sanju.springboot.serviceimpl.AttendanceService;

@Controller
@RequestMapping("/admin/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;
    private final StudentRepository studentRepository;

    public AttendanceController(
            AttendanceService attendanceService,
            StudentRepository studentRepository) {

        this.attendanceService = attendanceService;
        this.studentRepository = studentRepository;
    }

    // Display attendance for all students
    @GetMapping
    public String showAttendance(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date,
            Model model) {

        if (date == null) {
            date = LocalDate.now();
        }

        // Get all students except Admin
        List<Student> students = studentRepository.findAll()
                .stream()
                .filter(student -> student.getRole() == null
                        || !student.getRole().name().equals("ADMIN"))
                .toList();

        // Get attendance records for selected date
        List<Attendance> attendanceRecords =
                attendanceService.getAttendanceByDate(date);

        // Create attendance map
        Map<Long, AttendanceStatus> attendanceMap =
                attendanceRecords.stream()
                        .collect(Collectors.toMap(
                                attendance -> attendance.getStudent().getId(),
                                Attendance::getStatus,
                                (oldValue, newValue) -> newValue,
                                LinkedHashMap::new
                        ));

        model.addAttribute("students", students);
        model.addAttribute("attendanceMap", attendanceMap);
        model.addAttribute("selectedDate", date);

        return "admin-attendance";
    }

    // Mark or update attendance
    @PostMapping("/mark")
    public String markAttendance(
            @RequestParam Long studentId,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate attendanceDate,

            @RequestParam AttendanceStatus status) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Student not found"));

        attendanceService.markAttendance(
                student,
                attendanceDate,
                status
        );

        return "redirect:/admin/attendance?date=" + attendanceDate;
    }
}
