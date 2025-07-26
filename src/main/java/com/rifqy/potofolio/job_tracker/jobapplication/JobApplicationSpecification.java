package com.rifqy.potofolio.job_tracker.jobapplication;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.rifqy.potofolio.job_tracker.jobapplication.model.JobApplication;
import com.rifqy.potofolio.job_tracker.jobapplication.model.JobStatus;
import com.rifqy.potofolio.job_tracker.jobapplication.model.dto.JobApplicationFilter;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class JobApplicationSpecification {

    public static Specification<JobApplication> withFilter(Long userId, JobApplicationFilter jobApplicationFilter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(buildUserPredicate(cb, root, userId));
            addPositionPredicate(cb, root, predicates, jobApplicationFilter.getPosition());
            addCompanyNamePredicate(cb, root, predicates, jobApplicationFilter.getCompanyName());
            addJobStatusPredicate(cb, root, predicates, jobApplicationFilter.getJobStatus());
            addDateRangePredicate(cb, root, predicates, jobApplicationFilter.getStartDate(), jobApplicationFilter.getEndDate());

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static Predicate buildUserPredicate(CriteriaBuilder cb, Root<JobApplication> root, Long userId) {
        return cb.equal(root.get("applicationUser").get("id"), userId);
    }

    private static void addPositionPredicate(CriteriaBuilder cb, Root<JobApplication> root,
                                             List<Predicate> predicates, String position) {
        if (position != null && !position.isBlank()) {
            predicates.add(cb.like(cb.lower(root.get("position")),
                    "%" + position.toLowerCase() + "%"));
        }
    }

    private static void addCompanyNamePredicate(CriteriaBuilder cb, Root<JobApplication> root,
                                                List<Predicate> predicates, String companyName) {
        if (companyName != null && !companyName.isBlank()) {
            predicates.add(cb.like(cb.lower(root.get("companyName")),
                    "%" + companyName.toLowerCase() + "%"));
        }
    }

    private static void addJobStatusPredicate(CriteriaBuilder cb, Root<JobApplication> root,
                                              List<Predicate> predicates, JobStatus jobStatus) {
        if (jobStatus != null) {
            predicates.add(cb.equal(root.get("jobStatus"), jobStatus));
        }
    }

    private static void addDateRangePredicate(CriteriaBuilder cb, Root<JobApplication> root,
                                              List<Predicate> predicates, LocalDate startDate, LocalDate endDate) {
        if (startDate != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("createdAt"),
                    Timestamp.valueOf(startDate.atStartOfDay())));
        }
        if (endDate != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("createdAt"),
                    Timestamp.valueOf(endDate.atTime(LocalTime.MAX))));
        }
    }
}
