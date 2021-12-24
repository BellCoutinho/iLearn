package com.ilearn.vle.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import com.ilearn.vle.domain.Student;
import com.ilearn.vle.domain.Class;
import com.ilearn.vle.repository.StudentRepository;
import com.ilearn.vle.repository.ClassRepository;

@RequestMapping("/students")
@RestController
public class StudentController {
    
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ClassRepository classRepository;

    @GetMapping
    public List<Student> getStudents() {
        return this.studentRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Student> getStudentById(@PathVariable Long id) {
        return this.studentRepository.findById(id);
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return this.studentRepository.save(student);
    }
    
    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable("id") Long id, @RequestBody Student student) {
        Optional<Student> updatedStudent = this.studentRepository.findById(id);
        if (updatedStudent.isPresent()) {
            updatedStudent.get().setName(student.getName());
            updatedStudent.get().setEnrolment(student.getEnrolment());
            updatedStudent.get().setEmail(student.getEmail());
            return this.studentRepository.save(updatedStudent.get());
        }
        return this.studentRepository.save(student);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable("id") Long id) {
        this.studentRepository.delete(this.studentRepository.findById(id).get());
    }

    @PatchMapping("/{studentId}/enrollStudent/{classId}")
    public Student updateStudentEnrollment(@PathVariable("classId") Long classId, 
                                           @PathVariable("studentId") Long studentId,
                                           @RequestBody Student student) {
        Optional<Student> updatedStudent = this.studentRepository.findById(studentId);
        Optional<Class> classOptional = this.classRepository.findById(classId);
        if (updatedStudent.isEmpty())
            return null;
        if (classOptional.isEmpty())
            return null;

        updatedStudent.get().setKlass(classOptional.get());

        return this.studentRepository.save(updatedStudent.get());
    }

}
