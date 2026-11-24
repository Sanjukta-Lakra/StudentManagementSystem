package com.sanju.springboot.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sanju.springboot.entity.Student;
import com.sanju.springboot.enums.Role;

public interface StudentRepository extends JpaRepository<Student, Long>
{
    Optional<Student> findByEmail(String email);

    long countByRole(Role role);
}