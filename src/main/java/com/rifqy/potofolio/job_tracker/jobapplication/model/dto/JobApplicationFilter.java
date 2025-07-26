package com.rifqy.potofolio.job_tracker.jobapplication.model.dto;

import java.time.LocalDate;

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
public class JobApplicationFilter {
    private String position;

    private String companyName;

    private JobStatus jobStatus;

    private LocalDate startDate;

    private LocalDate endDate;

    public Boolean hasDateRange() {
        return this.startDate != null || this.endDate != null;
    }
}
