package com.ilearn.vle.service;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ilearn.vle.domain.Project;
import com.ilearn.vle.domain.Teacher;
import com.ilearn.vle.domain.Student;
import com.ilearn.vle.domain.Role;
import com.ilearn.vle.dto.ProjectDTO;
import com.ilearn.vle.dto.ProjectMemberDTO;
import com.ilearn.vle.repository.TeacherRepository;
import com.ilearn.vle.repository.ProjectRepository;
import com.ilearn.vle.repository.StudentRepository;
import com.ilearn.vle.repository.RoleRepository;
import com.ilearn.vle.service.exception.TeacherNotFoundException;
import com.ilearn.vle.service.exception.TeacherAlreadyHasProjectException;
import com.ilearn.vle.service.exception.ProjectNotFoundException;
import com.ilearn.vle.service.exception.ProjectAlreadyExistsException;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private RoleRepository roleRepository; 

    public List<ProjectDTO> takeAllProjects() {
	final var projects = this.projectRepository.findAll();
	final var projectDTOs = (List<ProjectDTO>) new ArrayList<ProjectDTO>();
	for (Project project : projects) {
	    var projectDTO = new ProjectDTO();
	    BeanUtils.copyProperties(project, projectDTO);
	    projectDTOs.add(projectDTO);
	}
	return projectDTOs;
    }

    public ProjectDTO takeProject(Long id) {
	if (!this.projectRepository.existsById(id)) {
	    throw new ProjectNotFoundException("project does not exist");
	}
	final var project = this.projectRepository.findById(id)
	    .orElseThrow(() -> new ProjectNotFoundException("project does not exist"));
	final var projectDTO = new ProjectDTO();
	BeanUtils.copyProperties(project, projectDTO);
	
        return projectDTO;
    }
    
    public ProjectDTO registerProject(Long id, ProjectDTO projectDTO) {
	if (!this.teacherRepository.existsById(id)) {
	    throw new TeacherNotFoundException("teacher does not exist");
	}
	final var teacherResult = this.teacherRepository.findById(id);
	if (teacherResult.get().getProject() != null) {
	    throw new TeacherAlreadyHasProjectException("teacher already has a project");
	}
	if (!projectRepository.findByName(projectDTO.getName()).isEmpty()) {
	    throw new ProjectAlreadyExistsException("project already exists");
	}
	final var project = new Project();
	BeanUtils.copyProperties(projectDTO, project);
	teacherResult.get().setProject(project);
	final var role = this.roleRepository.findByName("Coordenador")
	    .orElse(new Role().builder().name("Coordenador").build());
	teacherResult.get().setProjectRole(role);
	project.setTeacher(teacherResult.get());

	this.projectRepository.save(project);
	return projectDTO;
    }

    public ProjectDTO updateProject(Long id, ProjectDTO updatedProject) {
	if (!this.projectRepository.existsById(id)) {
	    throw new ProjectNotFoundException("project does not exist");
	}
	final var project = this.projectRepository.findById(id)
	    .orElseThrow(() -> new ProjectNotFoundException("project does not exist"));

	BeanUtils.copyProperties(updatedProject, project);
	this.projectRepository.save(project);
        return updatedProject;
    }

    public void deleteProject(Long id) {
	if (!this.projectRepository.existsById(id)) {
	    throw new ProjectNotFoundException("project does not exist");
	}
        this.projectRepository.delete(this.projectRepository.findById(id).get());
    }

    public List<ProjectMemberDTO> listAllMembers(Long projectId) {
	if (!this.projectRepository.existsById(projectId)) {
	    throw new ProjectNotFoundException("project does not exist");
	}

	final var teacherMember = this.teacherRepository.findByProjectId(projectId);
	final var studentMembers = this.studentRepository.findByProjectId(projectId);

	final var projectMembers = (List<ProjectMemberDTO>) new ArrayList<ProjectMemberDTO>();
	if (!teacherMember.isEmpty()) {
	    projectMembers.add(new ProjectMemberDTO(teacherMember.get().getName(), teacherMember.get().getProjectRole().getName()));
	}
	for (Student student: studentMembers) {
	    final var projectMember = new ProjectMemberDTO(student.getName(), student.getProjectRole().getName());
	    projectMembers.add(projectMember);
	}

	return projectMembers;
    }
}

