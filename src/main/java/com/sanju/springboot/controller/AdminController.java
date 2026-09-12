package com.sanju.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sanju.springboot.entity.Student;
import com.sanju.springboot.entity.Role;
import com.sanju.springboot.service.CourseService;
import com.sanju.springboot.service.StudentService;

@Controller
@RequestMapping("/admin")
public class AdminController 
{
    private final StudentService studentService;
    private final CourseService courseService;

    public AdminController(StudentService studentService, CourseService courseService) 
    {
        this.studentService = studentService;
        this.courseService = courseService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) 
    {
        // Get all students
        long studentCount = studentService.getAllStudents()
                .stream()
                .filter(student -> student.getRole() != Role.ADMIN)
                .count();

        // Get all courses
        long courseCount = courseService.getAllCourses()
                .size();

        // Send counts to dashboard
        model.addAttribute("studentCount", studentCount);
        model.addAttribute("courseCount", courseCount);

        return "admin-dashboard";
    }

    @GetMapping("/students")
    public String getAllStudents(Model model)
    {
        model.addAttribute("students", studentService.getAllStudents());

        return "students";
    }

    @GetMapping("/students/edit/{id}")
    public String editStudent(@PathVariable Long id, Model model) 
    {
        Student student = studentService.getStudentById(id);

        model.addAttribute("student", student);

        // ALL COURSES
        model.addAttribute("courses", courseService.getAllCourses());

        return "edit-student";
    }

    @PostMapping("/students/update/{id}")
    public String updateStudent(@PathVariable Long id, @ModelAttribute Student student) 
    {
        studentService.updateStudent(id, student);

        return "redirect:/admin/students";
    }

    @GetMapping("/students/delete/{id}")
    public String deleteStudent(@PathVariable Long id) 
    {
        studentService.deleteStudent(id);

        return "redirect:/admin/students";
    }
}