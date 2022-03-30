package com.ilearn.vle.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectMemberDTO {
    @NotBlank(message = "Name cannot be empty")
    private String name;

    @NotBlank(message = "Role cannot be empty")
    private String role;
}
