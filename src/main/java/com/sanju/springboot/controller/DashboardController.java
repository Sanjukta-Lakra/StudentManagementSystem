package com.sanju.springboot.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController 
{
    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication)
    {
        boolean isAdmin = authentication
                .getAuthorities()
                .stream()
                .anyMatch(authority ->
                    authority.getAuthority()
                    .equals("ROLE_ADMIN")
                );

        if (isAdmin)
        {
            return "redirect:/admin/dashboard";
        }

        return "redirect:/student/dashboard";
    }
}
