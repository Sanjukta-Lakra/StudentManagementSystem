package com.sanju.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sanju.springboot.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Long>
{

}
