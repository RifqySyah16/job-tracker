package com.rifqy.potofolio.job_tracker.jobapplication;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.rifqy.potofolio.job_tracker.jobapplication.model.JobApplication;
import com.rifqy.potofolio.job_tracker.jobapplication.model.JobStatus;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {

    Page<JobApplication> findAllByApplicationUserAndJobStatusContainsIgnoreCase(Long userId, JobStatus jobStatus,
            Pageable pageable);

}
