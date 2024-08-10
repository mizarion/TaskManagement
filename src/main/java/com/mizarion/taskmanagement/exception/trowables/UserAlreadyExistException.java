package com.mizarion.taskmanagement.exception.trowables;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistException extends CustomException {

    public UserAlreadyExistException(String username) {
        super("User " + username + " already exist", HttpStatus.NOT_ACCEPTABLE);
    }
}
