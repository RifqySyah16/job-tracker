package com.rifqy.potofolio.job_tracker.jobapplication;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rifqy.potofolio.job_tracker.jobapplication.model.JobApplication;
import com.rifqy.potofolio.job_tracker.jobapplication.model.JobStatus;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JobApplicationService {
    private final JobApplicationRepository jobApplicationRepository;

    public Page<JobApplication> getAll(Optional<JobStatus> optionalJobStatus, Pageable pageable) {
        if (optionalJobStatus.isPresent()) {
            return this.jobApplicationRepository.findAllByJobStatusContainsIgnoreCase(optionalJobStatus.get(),
                    pageable);
        }

        return this.jobApplicationRepository.findAll(pageable);
    }

    public JobApplication getOne(Long id) {
        return this.jobApplicationRepository.findById(id)
                .orElseThrow(() -> new JobApplicationNotFoundException("Job application not found"));
    }

    public JobApplication create(JobApplication newJobApplication) {
        return this.jobApplicationRepository.save(newJobApplication);
    }

    public JobApplication update(JobApplication updatedJobApplication) {
        JobApplication existingJobApplication = this.getOne(updatedJobApplication.getId());
        updatedJobApplication.setId(existingJobApplication.getId());

        return this.jobApplicationRepository.save(updatedJobApplication);
    }

    public void delete(Long id) {
        JobApplication existingJobApplication = this.getOne(id);
        this.jobApplicationRepository.deleteById(existingJobApplication.getId());
    }
}
