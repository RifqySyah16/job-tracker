package com.rifqy.potofolio.job_tracker.jobapplication;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rifqy.potofolio.job_tracker.applicationuser.ApplicationUserService;
import com.rifqy.potofolio.job_tracker.applicationuser.model.ApplicationUser;
import com.rifqy.potofolio.job_tracker.jobapplication.model.JobApplication;
import com.rifqy.potofolio.job_tracker.jobapplication.model.JobStatus;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JobApplicationService {
    private final ApplicationUserService applicationUserService;
    private final JobApplicationRepository jobApplicationRepository;

    public Page<JobApplication> getAll(Long userId, Optional<JobApplication> optionalPosition,
            Optional<JobStatus> optionalJobStatus, Pageable pageable) {
        if (optionalPosition.isPresent()) {
            return this.jobApplicationRepository.findAllByApplicationUserIdAndPositionContainsIgnoreCase(
                    userId, optionalPosition.get(), pageable);
        }
        if (optionalJobStatus.isPresent()) {
            return this.jobApplicationRepository.findAllByApplicationUserIdAndJobStatusContainsIgnoreCase(userId,
                    optionalJobStatus.get(), pageable);
        }

        return this.jobApplicationRepository.findAllByApplicationUserId(userId, pageable);
    }

    public JobApplication getOne(Long userId, Long id) {
        this.validationUserById(userId);

        return this.jobApplicationRepository.findByIdAndApplicationUserId(id, userId)
                .orElseThrow(() -> new JobApplicationNotFoundException("Job application not found"));
    }

    public JobApplication create(Long userId, JobApplication newJobApplication) {
        this.validationUserById(userId);

        ApplicationUser existingApplicationUser = this.applicationUserService.getOne(userId);
        newJobApplication.setApplicationUser(existingApplicationUser);

        if (newJobApplication.getJobStatus() == null) {
            newJobApplication.setJobStatus(JobStatus.WAITING_FEEDBACK);
        }

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

    public void generateExcel(Long userId, HttpServletResponse httpServletResponse) throws IOException {
        List<JobApplication> jobApplications = this.jobApplicationRepository.findAllByApplicationUserId(userId);

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Job Application Info");

        this.writeHeader(sheet);
        this.writeDataRows(sheet, jobApplications);

        for (int i = 0; i < 5; i++) {
            sheet.autoSizeColumn(i);
        }

        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        workbook.write(servletOutputStream);
        servletOutputStream.flush();

        servletOutputStream.close();
        workbook.close();
    }

    private void writeDataRows(XSSFSheet sheet, List<JobApplication> jobApplications) {
        int rowIndex = 1;
        for (JobApplication jobApplication : jobApplications) {
            XSSFRow row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(jobApplication.getId());
            row.createCell(1).setCellValue(jobApplication.getCompanyName());
            row.createCell(2).setCellValue(jobApplication.getPosition());
            row.createCell(3).setCellValue(this.convertJobStatus(jobApplication.getJobStatus()));
            row.createCell(4).setCellValue(jobApplication.getCreatedAt().toString());
        }
    }

    private void writeHeader(XSSFSheet sheet) {
        XSSFRow headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Job Application ID");
        headerRow.createCell(1).setCellValue("Company Name");
        headerRow.createCell(2).setCellValue("Position");
        headerRow.createCell(3).setCellValue("Job Status");
        headerRow.createCell(4).setCellValue("Created At");
    }

    private String convertJobStatus(JobStatus jobStatus) {
        if (jobStatus == null) {
            return "UNKNOWN";
        }

        return jobStatus.name();
    }

    private void validationUserById(Long userId) {
        if (userId == null) {
            throw new AccessJobApplicationDeniedException("Cannot add job application for another user");
        }
    }
}
