package com.sanju.springboot.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sanju.springboot.entity.Student;
import com.sanju.springboot.repository.StudentRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService
{
    private final StudentRepository studentRepository;

    public CustomUserDetailsService(StudentRepository studentRepository) 
    {
        this.studentRepository = studentRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException 
    {
        Student student = studentRepository
                .findByEmail(email)
                .orElseThrow(() ->
                    new UsernameNotFoundException(
                        "User not found: " + email
                    )
                );

        return User.builder()
                .username(student.getEmail())
                .password(student.getPassword())
                .roles(student.getRole().name())
                .build();
    }
}
