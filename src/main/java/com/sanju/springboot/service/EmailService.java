package com.sanju.springboot.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService
{
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) 
    {
        this.mailSender = mailSender;
    }

    public void sendRegistrationEmail(String to, String name) 
    {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(to);

        message.setSubject("Student Registration Successful");

        message.setText(
            "Hello " + name + ",\n\n"
            + "Your registration was successful!\n\n"
            + "Welcome to the Student Management System."
        );

        mailSender.send(message);
    }
}
