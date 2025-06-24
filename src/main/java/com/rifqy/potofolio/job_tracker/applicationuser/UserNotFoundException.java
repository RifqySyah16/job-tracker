package com.rifqy.potofolio.job_tracker.applicationuser;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message);
    }
}
