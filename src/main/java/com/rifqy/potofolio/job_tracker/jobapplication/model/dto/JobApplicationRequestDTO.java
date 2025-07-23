package com.rifqy.potofolio.job_tracker.jobapplication.model.dto;

import com.rifqy.potofolio.job_tracker.applicationuser.model.ApplicationUser;
import com.rifqy.potofolio.job_tracker.jobapplication.model.JobApplication;
import com.rifqy.potofolio.job_tracker.jobapplication.model.JobStatus;

import jakarta.validation.constraints.NotBlank;
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

    private JobStatus jobStatus;

    public JobApplication convertToEntity(ApplicationUser applicationUser) {
        JobStatus finalJobStatus = this.jobStatus;
        if (finalJobStatus == null) {
            finalJobStatus = JobStatus.WAITING_FEEDBACK;
        }

        return JobApplication.builder()
                .companyName(this.companyName)
                .position(this.position)
                .jobStatus(finalJobStatus)
                .applicationUser(applicationUser)
                .build();
    }
}
