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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ilearn.vle.domain.Teacher;
import com.ilearn.vle.domain.Student;
import com.ilearn.vle.dto.LinkStudentDTO;
import com.ilearn.vle.service.TeacherService;
import com.ilearn.vle.service.StudentService;

@RestController
@RequestMapping("/teachers")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private StudentService studentService;
    
    @GetMapping()
    public ResponseEntity<List<Teacher>> getTeachers() {
        final var teachers = this.teacherService.takeAllTeachers();
	return ResponseEntity.status(HttpStatus.FOUND).body(teachers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Teacher> getTeacherById(@PathVariable Long id) {
	final var teacher =  this.teacherService.takeTeacher(id);
        return ResponseEntity.status(HttpStatus.FOUND).body(teacher);
    }

    @PostMapping
    public ResponseEntity<Teacher> createTeacher(@RequestBody Teacher teacher) {
        final var createdTeacher = this.teacherService.registerTeacher(teacher);
	return ResponseEntity.status(HttpStatus.CREATED).body(createdTeacher);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Teacher> updateTeacher(@PathVariable("id") Long id, @RequestBody Teacher teacher) {
        final var updatedTeacher = this.teacherService.updateTeacher(id, teacher);
	return ResponseEntity.status(HttpStatus.OK).body(updatedTeacher);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTeatcher(@PathVariable("id") Long id) {
        this.teacherService.deleteTeatcher(id);
	return ResponseEntity.status(HttpStatus.OK).body("resource deleted successfully");
    }

    @PatchMapping("/project")
    public ResponseEntity<String> linkStudent(@RequestBody LinkStudentDTO linkStudent) {
	final var linkedStudent = this.teacherService.linkStudentToProject(linkStudent);
	return ResponseEntity.status(HttpStatus.OK).body("resource updated successfully");
    }
}

