package com.rifqy.potofolio.job_tracker.jobapplication;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rifqy.potofolio.job_tracker.applicationuser.ApplicationUserService;
import com.rifqy.potofolio.job_tracker.applicationuser.model.ApplicationUser;
import com.rifqy.potofolio.job_tracker.jobapplication.model.JobApplication;
import com.rifqy.potofolio.job_tracker.jobapplication.model.JobStatus;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JobApplicationService {
    private final ApplicationUserService applicationUserService;
    private final JobApplicationRepository jobApplicationRepository;

    public Page<JobApplication> getAll(Long userId, Optional<JobStatus> optionalJobStatus, Pageable pageable) {
        if (optionalJobStatus.isPresent()) {
            return this.jobApplicationRepository.findAllByApplicationUserAndJobStatusContainsIgnoreCase(userId, optionalJobStatus.get(),
                    pageable);
        }

        return this.jobApplicationRepository.findAll(pageable);
    }

    public JobApplication getOne(Long userId, Long id) {
        JobApplication jobApplication = this.jobApplicationRepository.findById(id)
                .orElseThrow(() -> new JobApplicationNotFoundException("Job application not found"));
        this.validationUserById(jobApplication, userId);

        return jobApplication;
    }

    public JobApplication create(Long userId, JobApplication newJobApplication) {
        this.validationUserById(newJobApplication, userId);

        ApplicationUser existingApplicationUser = this.applicationUserService.getOne(userId);
        newJobApplication.setApplicationUser(existingApplicationUser);
        
        return this.jobApplicationRepository.save(newJobApplication);
    }

    public JobApplication update(Long userId, JobApplication updatedJobApplication) {
        JobApplication existingJobApplication = this.getOne(userId, updatedJobApplication.getId());
        updatedJobApplication.setId(existingJobApplication.getId());

        return this.jobApplicationRepository.save(updatedJobApplication);
    }

    public void delete(Long userId, Long id) {
        JobApplication existingJobApplication = this.getOne(userId, id);
        this.jobApplicationRepository.deleteById(existingJobApplication.getId());
    }

    private void validationUserById(JobApplication jobApplication, Long userId) {
        if (jobApplication.getApplicationUser().getId().equals(userId)) {
            throw new AccessJobApplicationDeniedException("User cannot add job application for another user");
        }
    }
}
