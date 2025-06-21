package com.rifqy.potofolio.job_tracker.jobapplication.model.dto;

import java.sql.Timestamp;

import com.rifqy.potofolio.job_tracker.applicationuser.model.dto.RegisterationResponseDTO;
import com.rifqy.potofolio.job_tracker.jobapplication.model.JobStatus;

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
public class JobApplicationResponseDTO {
    private Long id;

    private String companyName;

    private String position;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    private JobStatus jobStatus;

    private RegisterationResponseDTO registerationResponseDTO;
}
