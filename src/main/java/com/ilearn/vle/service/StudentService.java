package com.ilearn.vle.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ilearn.vle.domain.Student;
import com.ilearn.vle.domain.Class;
import com.ilearn.vle.repository.StudentRepository;
import com.ilearn.vle.repository.ClassRepository;
import com.ilearn.vle.service.exception.StudentNotFoundException;
import com.ilearn.vle.service.exception.StudentAlreadyExistsException;
import com.ilearn.vle.service.exception.ClassNotFoundException;

@Service
public class StudentService {
        
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ClassRepository classRepository;

    public List<Student> takeAllStudents() {
        return this.studentRepository.findAll();
    }

    public Student takeStudent(Long id) {
	if (!this.studentRepository.existsById(id)) {
	    throw new StudentNotFoundException("student does not exist");
	}
	final var student = this.studentRepository.findById(id)
	    .orElseThrow(() -> new StudentNotFoundException("student does not exist"));
        return student;
    }

    public Student registerStudent(Student student) {
	if (!this.studentRepository.findByEnrolment(student.getEnrolment()).isEmpty()) {
	    throw new StudentAlreadyExistsException("student already exists");
	}
        return this.studentRepository.save(student);
    }
    
    public Student updateStudent(Long id, Student updatedStudent) {
	if (!this.studentRepository.existsById(id)) {
	    throw new StudentNotFoundException("student does not exist");
	}
	final var student = this.studentRepository.findById(id)
	    .orElseThrow(() -> new StudentNotFoundException("student does not exist"));

	BeanUtils.copyProperties(updatedStudent, student);
	this.studentRepository.save(student);
	
        return student;
    }

    public void deleteStudent(Long id) {
	if (!this.studentRepository.existsById(id)) {
	    throw new StudentNotFoundException("student does not exist");
	}
        this.studentRepository.delete(this.studentRepository.findById(id).get());
    }
}
