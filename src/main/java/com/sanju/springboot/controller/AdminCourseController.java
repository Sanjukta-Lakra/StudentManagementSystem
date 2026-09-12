package com.sanju.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sanju.springboot.entity.Course;
import com.sanju.springboot.service.CourseService;

@Controller
@RequestMapping("/admin")
public class AdminCourseController 
{
    private final CourseService courseService;

    public AdminCourseController(CourseService courseService) 
    {
        this.courseService = courseService;
    }

    // Show all courses
    @GetMapping("/courses")
    public String getAllCourses(Model model)
    {
        model.addAttribute("courses", courseService.getAllCourses());

        return "admin-courses";
    }

    // Open add course page
    @GetMapping("/courses/add")
    public String addCoursePage(Model model)
    {
        model.addAttribute("course", new Course());

        return "add-course";
    }

    // Save course
    @PostMapping("/courses/save")
    public String saveCourse(@ModelAttribute Course course) 
    {
        courseService.saveCourse(course);

        return "redirect:/admin/courses";
    }

    // Open edit course page
    @GetMapping("/courses/edit/{id}")
    public String editCourse(@PathVariable Long id, Model model) 
    {
        model.addAttribute("course", courseService.getCourseById(id));

        return "edit-course";
    }

    // Update course
    @PostMapping("/courses/update/{id}")
    public String updateCourse(@PathVariable Long id, @ModelAttribute Course course) 
    {
        courseService.updateCourse(id, course);

        return "redirect:/admin/courses";
    }

    // Delete course
    @GetMapping("/courses/delete/{id}")
    public String deleteCourse(@PathVariable Long id)
    {
        courseService.deleteCourse(id);

        return "redirect:/admin/courses";
    }
}
