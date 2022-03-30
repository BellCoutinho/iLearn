package com.ilearn.vle.controller;

import java.util.List;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ilearn.vle.dto.ProjectDTO;
import com.ilearn.vle.dto.ProjectMemberDTO;
import com.ilearn.vle.service.ProjectService;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping()
    public ResponseEntity<List<ProjectDTO>> getAllProjects() {
	final var projects = this.projectService.takeAllProjects();
	return ResponseEntity.status(HttpStatus.FOUND).body(projects);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDTO> getTeacherById(@PathVariable Long id) {
	final var project =  this.projectService.takeProject(id);
        return ResponseEntity.status(HttpStatus.FOUND).body(project);
    }

    @PostMapping("/teacher/{teacher_id}")
    public ResponseEntity<ProjectDTO> createProject (@PathVariable("teacher_id") Long id, @Valid @RequestBody ProjectDTO project) {
	return ResponseEntity.status(HttpStatus.CREATED).body(this.projectService.registerProject(id, project));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectDTO> updateProject(@PathVariable("id") Long id, @Valid @RequestBody ProjectDTO project) {
        final var updatedProject = this.projectService.updateProject(id, project);
	return ResponseEntity.status(HttpStatus.OK).body(updatedProject);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable("id") Long id) {
        this.projectService.deleteProject(id);
	return ResponseEntity.status(HttpStatus.OK).body("resource deleted successfully");
    }

    @GetMapping("/{id}/members")
    public ResponseEntity<List<ProjectMemberDTO>> listMembers(@PathVariable("id") Long id) {
	final var projectMenbers = this.projectService.listAllMembers(id);
	return ResponseEntity.status(HttpStatus.FOUND).body(projectMenbers);
    }
}

