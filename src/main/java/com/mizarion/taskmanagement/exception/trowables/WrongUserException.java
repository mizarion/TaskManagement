package com.mizarion.taskmanagement.exception.trowables;

import org.springframework.http.HttpStatus;

public class WrongUserException extends CustomException {

    public WrongUserException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
