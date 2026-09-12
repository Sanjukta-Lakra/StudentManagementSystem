package com.sanju.springboot.service;

import java.util.List;

import com.sanju.springboot.entity.Course;

public interface CourseService
{
    Course saveCourse(Course course);

    List<Course> getAllCourses();

    Course getCourseById(Long id);

    Course updateCourse(Long id, Course course);

    void deleteCourse(Long id);
}