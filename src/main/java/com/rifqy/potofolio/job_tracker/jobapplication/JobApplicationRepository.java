package com.rifqy.potofolio.job_tracker.jobapplication;

import com.rifqy.potofolio.job_tracker.jobapplication.model.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface JobApplicationRepository 
        extends JpaRepository<JobApplication, Long>, JpaSpecificationExecutor<JobApplication> {

    Optional<JobApplication> findByIdAndApplicationUserId(Long id, Long userId);

    List<JobApplication> findAllByApplicationUserId(Long userId);
}
