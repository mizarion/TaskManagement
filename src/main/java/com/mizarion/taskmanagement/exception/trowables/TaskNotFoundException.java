package com.mizarion.taskmanagement.exception.trowables;

import org.springframework.http.HttpStatus;

public class TaskNotFoundException extends CustomException {

    public TaskNotFoundException(Long id) {
        super("Task with id=" + id + " not found", HttpStatus.NOT_FOUND);
    }
}
