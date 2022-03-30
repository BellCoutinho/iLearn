package com.ilearn.vle.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import com.ilearn.vle.dto.ProjectMemberDTO;
import com.ilearn.vle.service.ProjectService;


@SpringBootTest
@AutoConfigureMockMvc
public class ProjectControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private ProjectService projectService;
    
    @Test
    @DisplayName("Shound return all members of project when user lists the project members")
    public void listMembers_should_returnAllMembers() throws Exception {
	final var expectedId = 1L;
	final var expectedProjectMemberDTOs = List.of(
						      new ProjectMemberDTO().builder().name("Eduardo Jorge").role("Coordenador").build(),
						      new ProjectMemberDTO().builder().name("Gabriel").role("Sênior").build(),
						      new ProjectMemberDTO().builder().name("Maria").role("Sênior").build(),
						      new ProjectMemberDTO().builder().name("Pedro").role("Master").build()
						      );
	when(projectService.listAllMembers(expectedId)).thenReturn(expectedProjectMemberDTOs);

	this.mockMvc.perform(get("/projects/" + expectedId + "/members").contentType(MediaType.APPLICATION_JSON))
	    .andExpect(status().isFound())
	    .andExpect(jsonPath("$", hasSize(4)))
	    .andExpect(jsonPath("$[0].name", is("Eduardo Jorge")))
	    .andExpect(jsonPath("$[0].role", is("Coordenador")))
	    .andExpect(jsonPath("$[1].name", is("Gabriel")))
	    .andExpect(jsonPath("$[1].role", is("Sênior")))
	    .andExpect(jsonPath("$[2].name", is("Maria")))
	    .andExpect(jsonPath("$[2].role", is("Sênior")))
	    .andExpect(jsonPath("$[3].name", is("Pedro")))
	    .andExpect(jsonPath("$[3].role", is("Master")));
    }
}

