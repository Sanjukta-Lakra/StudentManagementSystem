package com.sanju.springboot.service;

import java.util.List;

import com.sanju.springboot.entity.Student;

public interface StudentService 
{
    Student registerStudent(Student student);

    List<Student> getAllStudents();

    Student getStudentById(Long id);

    Student updateStudent(Long id, Student student);

    void deleteStudent(Long id);

    long countStudents();
}