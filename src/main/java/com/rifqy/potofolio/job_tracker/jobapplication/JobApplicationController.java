package com.rifqy.potofolio.job_tracker.jobapplication;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rifqy.potofolio.job_tracker.applicationuser.ApplicationUserService;
import com.rifqy.potofolio.job_tracker.applicationuser.model.ApplicationUser;
import com.rifqy.potofolio.job_tracker.authentication.model.UserPrincipal;
import com.rifqy.potofolio.job_tracker.jobapplication.model.JobApplication;
import com.rifqy.potofolio.job_tracker.jobapplication.model.JobStatus;
import com.rifqy.potofolio.job_tracker.jobapplication.model.dto.JobApplicationRequestDTO;
import com.rifqy.potofolio.job_tracker.jobapplication.model.dto.JobApplicationResponseDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/job-applications")
public class JobApplicationController {
    private final JobApplicationService jobApplicationService;
    private final ApplicationUserService applicationUserService;

    @GetMapping
    public ResponseEntity<Page<JobApplicationResponseDTO>> getAll(
            Authentication authentication,
            @RequestParam(value = "name") Optional<JobApplication> optionalPosistion,
            @RequestParam(value = "category", required = false) Optional<JobStatus> optionalJobStatus,
            @RequestParam(value = "sort", defaultValue = "ASC") String sortString,
            @RequestParam(value = "order_by", defaultValue = "id") String orderBy,
            @RequestParam(value = "limit", defaultValue = "5") int limit,
            @RequestParam(value = "page", defaultValue = "1") int page) {
        
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Long userId = userPrincipal.getId();
        
        Sort sort = Sort.by(Sort.Direction.valueOf(sortString), orderBy);
        Pageable pageable = PageRequest.of(page - 1, limit, sort);
        Page<JobApplication> pageJobApplication = this.jobApplicationService.getAll(userId, optionalPosistion, optionalJobStatus, pageable);
        Page<JobApplicationResponseDTO> jobApplicationResponseDTOs = pageJobApplication.map(JobApplication::convertToResponse);

        return ResponseEntity.ok(jobApplicationResponseDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobApplicationResponseDTO> getOne(@PathVariable("id") Long id, Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Long userId = userPrincipal.getId();
        
        JobApplication jobApplication = this.jobApplicationService.getOne(userId, id);
        JobApplicationResponseDTO jobApplicationResponseDTO = jobApplication.convertToResponse();

        return ResponseEntity.ok(jobApplicationResponseDTO);
    }

    @PostMapping
    public ResponseEntity<JobApplicationResponseDTO> create(@RequestBody @Valid JobApplicationRequestDTO jobApplicationRequestDTO, Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Long userId = userPrincipal.getId();

        ApplicationUser applicationUser = this.applicationUserService.getOne(userId);
        
        JobApplication newJobApplication = jobApplicationRequestDTO.convertToEntity(applicationUser);

        JobApplication savedJobApplication = this.jobApplicationService.create(userId, newJobApplication);
        JobApplicationResponseDTO jobApplicationResponseDTO = savedJobApplication.convertToResponse();

        return ResponseEntity.status(HttpStatus.CREATED).body(jobApplicationResponseDTO);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<JobApplicationResponseDTO> update(@PathVariable("id") Long id, @RequestBody JobApplicationRequestDTO jobApplicationRequestDTO, Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Long userId = userPrincipal.getId();
        
        ApplicationUser applicationUser = this.applicationUserService.getOne(userId);
        JobApplication updatedJobApplication = jobApplicationRequestDTO.convertToEntity(applicationUser);

        updatedJobApplication.setId(id);
        JobApplication savedJobApplication = this.jobApplicationService.update(userId, updatedJobApplication);
        JobApplicationResponseDTO jobApplicationResponseDTO = savedJobApplication.convertToResponse();

        return ResponseEntity.ok(jobApplicationResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id, Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Long userId = userPrincipal.getId();
        
        this.jobApplicationService.delete(userId, id);

        return ResponseEntity.ok().build();
    }
}
