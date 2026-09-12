package com.sanju.springboot.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sanju.springboot.entity.Role;
import com.sanju.springboot.entity.Student;
import com.sanju.springboot.repository.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService
{
	private final StudentRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public StudentServiceImpl(StudentRepository studentRepository, PasswordEncoder passwordEncoder, EmailService emailService) 
    {
        this.repository = studentRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

	@Override
	public Student registerStudent(Student student)
	{
		if(repository.findByEmail(student.getEmail()).isPresent())
		{
			throw new RuntimeException("Email already registered!");
		}
		
		student.setPassword(passwordEncoder.encode(student.getPassword()));
		
		student.setRole(Role.STUDENT);
		
		Student savedStudent = repository.save(student);

	    emailService.sendRegistrationEmail(savedStudent.getEmail(), savedStudent.getName());

	    return savedStudent;
	}

	@Override
	public List<Student> getAllStudents()
	{
		return repository.findAll();
	}

	@Override
	public Student getStudentById(Long id) 
	{
		return repository.findById(id).orElseThrow(() -> new RuntimeException("Student not found with ID: " + id));
	}

	@Override
	public Student updateStudent(Long id, Student student)
	{
		Student exist = getStudentById(id);
		
		exist.setName(student.getName());
		exist.setEmail(student.getEmail());
		exist.setPhone(student.getPhone());
		exist.setCourse(student.getCourse());
		
		return repository.save(exist);
	}

	@Override
	public void deleteStudent(Long id)
	{
		Student student = getStudentById(id);

        repository.delete(student);
	}
	
	@Override
	public long countStudents() 
	{
	    return repository.countByRole(Role.STUDENT);
	}
}
