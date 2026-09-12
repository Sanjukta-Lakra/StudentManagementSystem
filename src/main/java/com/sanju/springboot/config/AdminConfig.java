package com.sanju.springboot.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.sanju.springboot.entity.Role;
import com.sanju.springboot.entity.Student;
import com.sanju.springboot.repository.StudentRepository;

@Configuration
public class AdminConfig 
{
    @Bean
    CommandLineRunner createAdmin(StudentRepository repository, PasswordEncoder passwordEncoder) 
    {
        return args -> {

            if (repository
                    .findByEmail("admin@gmail.com")
                    .isEmpty()) {

                Student admin = new Student();

                admin.setName("Administrator");

                admin.setEmail("admin@gmail.com");

                admin.setPhone("9999999999");

                admin.setCourse("Administration");

                admin.setPassword(
                    passwordEncoder.encode("admin123")
                );

                admin.setRole(Role.ADMIN);

                repository.save(admin);

                System.out.println(
                    "Admin created successfully!"
                );
            }
        };
    }
}
