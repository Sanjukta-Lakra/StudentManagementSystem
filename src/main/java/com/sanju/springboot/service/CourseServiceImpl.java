package com.sanju.springboot.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sanju.springboot.entity.Course;
import com.sanju.springboot.repository.CourseRepository;

@Service
public class CourseServiceImpl implements CourseService
{
    private final CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public Course saveCourse(Course course) 
    {
        return courseRepository.save(course);
    }

    @Override
    public List<Course> getAllCourses() 
    {
        return courseRepository.findAll();
    }

    @Override
    public Course getCourseById(Long id) 
    {
        return courseRepository.findById(id).orElse(null);
    }

    @Override
    public Course updateCourse(Long id, Course course)
    {
        Course existing = courseRepository.findById(id).orElse(null);

        if (existing != null) 
        {
            existing.setCourseName(course.getCourseName());
            existing.setDescription(course.getDescription());
            existing.setDuration(course.getDuration());
            existing.setFees(course.getFees());
            existing.setMode(course.getMode());
            existing.setStatus(course.getStatus());

            return courseRepository.save(existing);
        }

        return null;
    }

    @Override
    public void deleteCourse(Long id)
    {
        courseRepository.deleteById(id);
    }
}