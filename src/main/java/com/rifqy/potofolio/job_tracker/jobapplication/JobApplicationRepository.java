package com.rifqy.potofolio.job_tracker.jobapplication;

import java.util.List;
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

        List<JobApplication> findAllByApplicationUserId(Long userId);

        Page<JobApplication> findAllByApplicationUserIdAndJobStatusContainsIgnoreCase(Long userId,
                        JobApplication jobApplication, Pageable pageable);
}
