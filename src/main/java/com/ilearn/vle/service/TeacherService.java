package com.ilearn.vle.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ilearn.vle.domain.Teacher;
import com.ilearn.vle.domain.Student;
import com.ilearn.vle.domain.Role;
import com.ilearn.vle.dto.LinkStudentDTO;
import com.ilearn.vle.repository.TeacherRepository;
import com.ilearn.vle.repository.StudentRepository;
import com.ilearn.vle.repository.ProjectRepository;
import com.ilearn.vle.repository.RoleRepository;
import com.ilearn.vle.service.exception.TeacherNotFoundException;
import com.ilearn.vle.service.exception.TeacherAlreadyExistsException;
import com.ilearn.vle.service.exception.StudentNotFoundException;
import com.ilearn.vle.service.exception.ProjectNotFoundException;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;
    
    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private ProjectRepository projectRepository;
    
    @Autowired
    private RoleRepository roleRepository; 
    
    public List<Teacher> takeAllTeachers() {
        return this.teacherRepository.findAll();
    }
    
    public Teacher takeTeacher(Long id) {
	if (!this.teacherRepository.existsById(id)) {
	    throw new TeacherNotFoundException("teacher does not exist");
	}
	final var teacher = this.teacherRepository.findById(id)
	    .orElseThrow(() -> new TeacherNotFoundException("teacher does not exist"));
        return teacher;
    }

    public Teacher registerTeacher(Teacher teacher) {
	if (!this.teacherRepository.findByEnrolment(teacher.getEnrolment()).isEmpty()) {
	    throw new TeacherAlreadyExistsException("teacher already exists");
	}
        return this.teacherRepository.save(teacher);
    }

    public Teacher updateTeacher(Long id, Teacher updatedTeacher) {
	if (!this.teacherRepository.existsById(id)) {
	    throw new TeacherNotFoundException("teacher does not exist");
	}
	final var teacher = this.teacherRepository.findById(id)
	    .orElseThrow(() -> new TeacherNotFoundException("teacher does not exist"));

	BeanUtils.copyProperties(updatedTeacher, teacher);
	this.teacherRepository.save(teacher);
        return teacher;
    }

    public void deleteTeatcher(Long id) {
	if (!this.teacherRepository.existsById(id)) {
	    throw new TeacherNotFoundException("teacher does not exist");
	}
        this.teacherRepository.delete(this.teacherRepository.findById(id).get());
    }

    public Student linkStudentToProject(LinkStudentDTO linkStudent) {
	if (!this.teacherRepository.existsById(linkStudent.getTeacherId())) {
	    throw new TeacherNotFoundException("teacher does not exist");
	}
	if (!this.studentRepository.existsById(linkStudent.getStudentId())) {
	    throw new StudentNotFoundException("student does not exist");
	}

	final var student = this.studentRepository.findById(linkStudent.getStudentId())
	    .orElseThrow(() -> new StudentNotFoundException("student does not exist"));

	final var teacher = this.teacherRepository.findByProject(linkStudent.getTeacherId())
	    .orElseThrow(() -> new ProjectNotFoundException("project does not exist"));
	
	student.setProject(teacher.getProject());
	final var role = this.roleRepository.findByName(linkStudent.getRole())
	    .orElse(new Role().builder().name(linkStudent.getRole()).build());
	student.setProjectRole(role);
	teacher.getProject().getStudents().add(student);
	this.studentRepository.save(student);
	this.projectRepository.save(teacher.getProject());
	return student;
    }
}

