package com.ilearn.vle.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LinkStudentDTO {
    @NotEmpty(message = "Teacher Id cannot be empty")
    private Long teacherId;

    @NotEmpty(message = "Student Id cannot be empty")
    private Long studentId;

    @NotBlank(message = "Role name cannot be empty")
    private String role; 
}
