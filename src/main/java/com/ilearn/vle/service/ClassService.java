package com.ilearn.vle.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ilearn.vle.domain.Class;
import com.ilearn.vle.domain.Student;
import com.ilearn.vle.repository.ClassRepository;
import com.ilearn.vle.repository.StudentRepository;
import com.ilearn.vle.service.exception.ClassNotFoundException;
import com.ilearn.vle.service.exception.ClassAlreadyExistsException;
import com.ilearn.vle.service.exception.StudentNotFoundException;

@Service
public class ClassService {
    
    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private StudentRepository studentRepository;

    public List<Class> takeAllClasses() {
        return this.classRepository.findAll();
    }

    public Class takeClass(Long id) {
	if (!this.classRepository.existsById(id)) {
	    throw new ClassNotFoundException("class does not exist");
	}
	final var klass = this.classRepository.findById(id)
	    .orElseThrow(() -> new ClassNotFoundException("class does not exist"));
        return klass;
    }

    public Class registerClass(Class klass) {
	if (!this.classRepository.findByDiscipline(klass.getDiscipline()).isEmpty()) {
	    throw new ClassAlreadyExistsException("class already exists");
	}
        return this.classRepository.save(klass);
    }
    
    public Class updateClass(Long id, Class updatedKlass) {
	if (!this.classRepository.existsById(id)) {
	    throw new ClassNotFoundException("class does not exist");
	}
	final var klass = this.classRepository.findById(id)
	    .orElseThrow(() -> new ClassNotFoundException("class does not exist"));

	BeanUtils.copyProperties(updatedKlass, klass);
	this.classRepository.save(klass);
	
        return klass;
    }

    public void deleteClass(Long id) {
	if (!this.classRepository.existsById(id)) {
	    throw new ClassNotFoundException("class does not exist");
	}
        this.classRepository.delete(this.classRepository.findById(id).get());
    }

    public Student enrollStudent(Long klassId, Long studentId) {
	final var klass = this.classRepository.findById(klassId)
	    .orElseThrow(() -> new ClassNotFoundException("class does not exist"));
	final var student = this.studentRepository.findById(studentId)
	    .orElseThrow(() -> new StudentNotFoundException("student does not exist"));
        student.setKlass(klass);
        this.studentRepository.save(student);
        return student;
    }
}
