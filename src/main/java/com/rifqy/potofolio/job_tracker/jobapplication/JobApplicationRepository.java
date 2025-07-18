package com.rifqy.potofolio.job_tracker.jobapplication;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.rifqy.potofolio.job_tracker.jobapplication.model.JobApplication;
import com.rifqy.potofolio.job_tracker.jobapplication.model.JobStatus;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {

    Page<JobApplication> findAllByApplicationUserIdAndJobStatusContainsIgnoreCase(Long userId, JobStatus jobStatus,
            Pageable pageable);

    Page<JobApplication> findAllByApplicationUserId(Long userId, Pageable pageable);

    Optional<JobApplication> findByIdAndApplicationUserId(Long id, Long userId);

}
