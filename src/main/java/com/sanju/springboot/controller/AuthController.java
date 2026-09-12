package com.sanju.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sanju.springboot.service.CourseService;

@Controller
public class AuthController 
{
    private final CourseService courseService;

    public AuthController(CourseService courseService) 
    {
        this.courseService = courseService;
    }

    @GetMapping("/")
    public String home()
    {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login()
    {
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model)
    {
        model.addAttribute("courses", courseService.getAllCourses());

        return "register";
    }
}