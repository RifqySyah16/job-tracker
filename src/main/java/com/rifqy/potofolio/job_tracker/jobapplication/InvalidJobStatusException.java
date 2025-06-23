package com.rifqy.potofolio.job_tracker.jobapplication;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class InvalidJobStatusException extends RuntimeException {

    public InvalidJobStatusException(String message) {
        super(message);
    }
}
