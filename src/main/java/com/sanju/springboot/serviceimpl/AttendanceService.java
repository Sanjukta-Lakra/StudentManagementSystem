package com.sanju.springboot.serviceimpl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sanju.springboot.entity.Attendance;
import com.sanju.springboot.entity.Student;
import com.sanju.springboot.enums.AttendanceStatus;
import com.sanju.springboot.repository.AttendanceRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttendanceService
{
    private final AttendanceRepository attendanceRepository;

    // Mark or update attendance
    @Transactional
    public Attendance markAttendance(
            Student student,
            LocalDate attendanceDate,
            AttendanceStatus status)
    {
        Attendance attendance = attendanceRepository
                .findByStudentAndAttendanceDate(student, attendanceDate)
                .orElseGet(Attendance::new);

        attendance.setStudent(student);
        attendance.setAttendanceDate(attendanceDate);
        attendance.setStatus(status);

        return attendanceRepository.save(attendance);
    }

    // Get all attendance records (Admin)
    @Transactional(readOnly = true)
    public List<Attendance> getAllAttendance()
    {
        return attendanceRepository.findAllByOrderByAttendanceDateDesc();
    }

    // Get attendance history of a student
    @Transactional(readOnly = true)
    public List<Attendance> getStudentAttendance(Student student)
    {
        return attendanceRepository
                .findByStudentOrderByAttendanceDateDesc(student);
    }

    // Get attendance records for a particular date
    @Transactional(readOnly = true)
    public List<Attendance> getAttendanceByDate(LocalDate date)
    {
        return attendanceRepository.findByAttendanceDate(date);
    }

    // Count total classes
    @Transactional(readOnly = true)
    public long getTotalClasses(Student student)
    {
        return attendanceRepository.countByStudent(student);
    }

    // Count present classes
    @Transactional(readOnly = true)
    public long getPresentClasses(Student student)
    {
        return attendanceRepository.countByStudentAndStatus(
                student, AttendanceStatus.PRESENT);
    }

    // Count absent classes
    @Transactional(readOnly = true)
    public long getAbsentClasses(Student student)
    {
        return attendanceRepository.countByStudentAndStatus(
                student, AttendanceStatus.ABSENT);
    }

    // Calculate attendance percentage
    @Transactional(readOnly = true)
    public double getAttendancePercentage(Student student)
    {
        long total = getTotalClasses(student);

        if (total == 0)
        {
            return 0.0;
        }

        long present = getPresentClasses(student);

        return (present * 100.0) / total;
    }
}
