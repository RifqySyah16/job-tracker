package com.rifqy.potofolio.job_tracker.applicationuser.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterationResponseDTO {
    private Long id;

    private String name;

    private String email;

    private String username;

    private String password;
}
