package com.sanju.springboot.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sanju.springboot.entity.Role;
import com.sanju.springboot.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long>
{
    Optional<Student> findByEmail(String email);

    long countByRole(Role role);
}