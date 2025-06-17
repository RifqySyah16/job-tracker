package com.rifqy.potofolio.job_tracker.applicationuser.model.dto;

import com.rifqy.potofolio.job_tracker.applicationuser.model.ApplicationUser;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterationRequestDTO {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email is Required")
    private String email;

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;

    public ApplicationUser convertToEntity() {
        return ApplicationUser.builder()
                .name(this.name)
                .email(this.email)
                .username(this.username)
                .password(this.password)
                .build();
    }
}
