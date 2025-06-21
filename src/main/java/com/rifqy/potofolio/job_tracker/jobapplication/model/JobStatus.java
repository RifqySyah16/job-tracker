package com.rifqy.potofolio.job_tracker.jobapplication.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.rifqy.potofolio.job_tracker.jobapplication.InvalidJobStatusException;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum JobStatus {
    WAITING_FEEDBACK("waiting feedback"),
    INTERVIEW("interview"),
    TEST("test"),
    HIRED("hired"),
    REJECTED("rejected"),
    IGNORED("ignored");

    private final String value;

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static JobStatus fromValue(String value) {
        for (JobStatus jobStatus : values()) {
            if (jobStatus.getValue().equalsIgnoreCase(value)) {
                return jobStatus;
            }
        }
        throw new InvalidJobStatusException("Invalid job status: " + value);
    }
}
