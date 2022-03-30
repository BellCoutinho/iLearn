package com.ilearn.vle.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ilearn.vle.domain.Class;
import com.ilearn.vle.domain.Student;
import com.ilearn.vle.service.ClassService;

@RestController
@RequestMapping("/classes")
public class ClassController {
    
    @Autowired
    private ClassService classService;

    @GetMapping
    public ResponseEntity<List<Class>> getClasses() {
	final var klasses = this.classService.takeAllClasses();
	return ResponseEntity.status(HttpStatus.FOUND).body(klasses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Class> getClassById(@PathVariable Long id) {
	final var klass = this.classService.takeClass(id);
	return ResponseEntity.status(HttpStatus.FOUND).body(klass);
    }

    @PostMapping
    public ResponseEntity<Class> createClass(@RequestBody Class klass) {
	final var createdKlass = this.classService.registerClass(klass);
	return ResponseEntity.status(HttpStatus.CREATED).body(createdKlass);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Class> updateClass(@PathVariable("id") Long id, @RequestBody Class klass) {
	final var updatedKlass = this.classService.updateClass(id, klass);
	return ResponseEntity.status(HttpStatus.OK).body(updatedKlass);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClass(@PathVariable("id") Long id) {
	this.classService.deleteClass(id);
	return ResponseEntity.status(HttpStatus.OK).body("resource deleted successfully");
    }

    @PatchMapping("/{classId}/enrollStudent/{studentId}")
    public ResponseEntity<Student> enrollStudent(@PathVariable("classId") Long classId, @PathVariable("studentId") Long studentId) {
	final var student = this.classService.enrollStudent(classId, studentId);
	return ResponseEntity.status(HttpStatus.CREATED).body(student);
    }
}
