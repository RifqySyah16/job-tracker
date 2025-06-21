package com.rifqy.potofolio.job_tracker.jobapplication;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class JobApplicationNotFoundException extends RuntimeException {

    public JobApplicationNotFoundException(String message) {
        super(message);
    }
}
