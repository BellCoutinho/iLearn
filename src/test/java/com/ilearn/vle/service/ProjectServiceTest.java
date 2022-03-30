package com.ilearn.vle.service;

import java.util.Optional;
import java.util.List;

import org.springframework.boot.test.context.SpringBootTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.equalTo;

import org.mockito.MockitoAnnotations;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;

import com.ilearn.vle.domain.Project;
import com.ilearn.vle.domain.Teacher;
import com.ilearn.vle.domain.Role;
import com.ilearn.vle.domain.Student;
import com.ilearn.vle.dto.ProjectDTO;
import com.ilearn.vle.dto.ProjectMemberDTO;
import com.ilearn.vle.repository.ProjectRepository;
import com.ilearn.vle.repository.TeacherRepository;
import com.ilearn.vle.repository.StudentRepository;
import com.ilearn.vle.repository.RoleRepository;
import com.ilearn.vle.service.ProjectService;

@SpringBootTest
public class ProjectServiceTest {

    @InjectMocks
    private ProjectService projectService;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private TeacherRepository teacherRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private StudentRepository studentRepository;
    
    @BeforeEach
    public void initMocks() {
	MockitoAnnotations.initMocks(this);
    }
    
    @Test
    @DisplayName("Should created project when teacher submit a new project")
    public void register_should_returnCreated() {
	// Given
	Teacher savedTeacher = new Teacher().builder()
	    .id(1L).name("Eduardo Jorge").education("Ciência da computação").email("eduardo@gmail.com").enrolment("172.080.266").build();
	Project expectedSavedProject = new Project().builder()
	    .id(1L).name("Banco de dados").description("Ensinando banco de dados nas escolas públicas").build();
	ProjectDTO expectedSavedProjectDTO = new ProjectDTO("Banco de dados", "Ensinando banco de dados nas escolas públicas");
	Role expectedRole = new Role().builder().id(1).name("Coordenador").build();
	
	// When
	when(teacherRepository.existsById(savedTeacher.getId())).thenReturn(true);
	when(teacherRepository.findById(savedTeacher.getId())).thenReturn(Optional.of(savedTeacher));
	when(projectRepository.findByName(expectedSavedProject.getName())).thenReturn(Optional.empty());
	when(roleRepository.findByName(expectedRole.getName())).thenReturn(Optional.of(expectedRole));
	when(projectRepository.save(expectedSavedProject)).thenReturn(expectedSavedProject);
	
	ProjectDTO createdProjectDTO = projectService.registerProject(savedTeacher.getId(), expectedSavedProjectDTO);
	
	// Then
	assertThat(createdProjectDTO.getName(), equalTo(expectedSavedProject.getName()));
	assertThat(createdProjectDTO.getDescription(), equalTo(expectedSavedProject.getDescription()));
    }

    @Test
    @DisplayName("Shound return all members of project when user lists all project members ")
    public void listAllMembers_should_returnAllMembers() {
	//Given
	final var expectedId = 1L;
	final var expectedSavedProject = new Project().builder()
	    .id(expectedId).name("Banco de dados").description("Ensinando banco de dados nas escolas públicas").build();
	final var allStudentMembers = List.of(
					      new Student().builder()
					      .id(1L).name("Gabriel").email("gabriel@gmail.com").enrolment("172.080.266")
					      .projectRole(new Role().builder().id(2).name("Sênior").build()).build(),
					      new Student().builder()
					      .id(2L).name("Maria").email("maria@gmail.com").enrolment("172.080.267")
					      .projectRole(new Role().builder().id(2).name("Sênior").build()).build(),
					      new Student().builder()
					      .id(3L).name("Pedro").email("pedro@gmail.com").enrolment("172.080.268")
					      .projectRole(new Role().builder().id(3).name("Master").build()).build()
					      );

	final var teacherMember = new Teacher().builder()
	    .id(1L).name("Eduardo Jorge").education("Ciência da computação").email("eduardo@gmail.com").enrolment("172.080.266")
	    .projectRole(new Role().builder().id(1).name("Coordenador").build()).build();

	//final var projectMembers = (List<ProjectMemberDTO>) new ArrayList<ProjectMemberDTO>();
	final var expectedProjectMemberDTOs = List.of(
						   new ProjectMemberDTO().builder().name("Eduardo Jorge").role("Coordenador").build(),
						   new ProjectMemberDTO().builder().name("Gabriel").role("Sênior").build(),
						   new ProjectMemberDTO().builder().name("Maria").role("Sênior").build(),
						   new ProjectMemberDTO().builder().name("Pedro").role("Master").build()
						   );
	//When
	when(projectRepository.existsById(expectedId)).thenReturn(true);
	when(studentRepository.findByProjectId(expectedId)).thenReturn(allStudentMembers);
	when(teacherRepository.findByProjectId(expectedId)).thenReturn(Optional.of(teacherMember));

	List<ProjectMemberDTO> actualMembers = projectService.listAllMembers(expectedId);
	
	//Then
	assertThat(actualMembers, equalTo(expectedProjectMemberDTOs));
    }
}

