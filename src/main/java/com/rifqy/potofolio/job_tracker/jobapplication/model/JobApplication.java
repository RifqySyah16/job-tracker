package com.rifqy.potofolio.job_tracker.jobapplication.model;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.rifqy.potofolio.job_tracker.applicationuser.model.ApplicationUser;
import com.rifqy.potofolio.job_tracker.applicationuser.model.dto.ApplicationUserJobResponseDTO;
import com.rifqy.potofolio.job_tracker.jobapplication.model.dto.JobApplicationResponseDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String companyName;

    private String position;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    @Enumerated(EnumType.STRING)
    private JobStatus jobStatus;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private ApplicationUser applicationUser;

    public JobApplicationResponseDTO convertToResponse() {
        ApplicationUserJobResponseDTO applicationUserJobResponseDTO = this.applicationUser.convertToJobResponse();

        return JobApplicationResponseDTO.builder()
                .id(this.id)
                .companyName(this.companyName)
                .position(this.position)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .jobStatus(this.jobStatus)
                .applicationUserJobResponseDTO(applicationUserJobResponseDTO)
                .build();
    }
}
