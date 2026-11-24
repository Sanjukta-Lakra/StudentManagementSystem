package com.sanju.springboot.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sanju.springboot.entity.Attendance;
import com.sanju.springboot.entity.Student;
import com.sanju.springboot.enums.AttendanceStatus;

public interface AttendanceRepository extends JpaRepository<Attendance, Long>
{
    // Get all attendance records
    List<Attendance> findAllByOrderByAttendanceDateDesc();

    // Get attendance history of a student
    List<Attendance> findByStudentOrderByAttendanceDateDesc(Student student);

    // Get attendance by date
    List<Attendance> findByAttendanceDate(LocalDate attendanceDate);

    // Find attendance by student and date
    Optional<Attendance> findByStudentAndAttendanceDate(
        Student student, LocalDate attendanceDate);

    // Count attendance
    long countByStudentAndStatus(Student student, AttendanceStatus status);

    long countByStudent(Student student);
}
