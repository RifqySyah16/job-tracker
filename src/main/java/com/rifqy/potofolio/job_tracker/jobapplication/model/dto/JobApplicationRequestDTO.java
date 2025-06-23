package com.rifqy.potofolio.job_tracker.jobapplication.model.dto;

import com.rifqy.potofolio.job_tracker.applicationuser.model.ApplicationUser;
import com.rifqy.potofolio.job_tracker.applicationuser.model.dto.UserJobApplicationRequestDTO;
import com.rifqy.potofolio.job_tracker.jobapplication.model.JobApplication;
import com.rifqy.potofolio.job_tracker.jobapplication.model.JobStatus;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobApplicationRequestDTO {
    @NotBlank(message = "Company name is required")
    private String companyName;

    @NotBlank(message = "Position is required")
    private String position;

    @NotNull(message = "Job status is required")
    private JobStatus jobStatus;

    @Valid
    private UserJobApplicationRequestDTO userJobApplicationRequestDTO;

    public JobApplication convertToEntity() {
        ApplicationUser applicationUser = this.userJobApplicationRequestDTO.convertToEntity();

        return JobApplication.builder()
                .companyName(this.companyName)
                .position(this.position)
                .jobStatus(this.jobStatus)
                .applicationUser(applicationUser)
                .build();
    }
}
