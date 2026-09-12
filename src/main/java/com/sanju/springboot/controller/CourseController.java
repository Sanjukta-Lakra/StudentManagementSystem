package com.sanju.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sanju.springboot.service.CourseService;

@Controller
public class CourseController 
{
    private final CourseService courseService;

    public CourseController(CourseService courseService) 
    {
        this.courseService = courseService;
    }

    @GetMapping("/courses")
    public String courses(Model model)
    {
        model.addAttribute("courses", courseService.getAllCourses());

        return "courses";
    }
}
