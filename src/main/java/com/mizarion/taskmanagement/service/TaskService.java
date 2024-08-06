package com.mizarion.taskmanagement.service;

import com.mizarion.taskmanagement.dto.TaskDto;

import java.util.List;

public interface TaskService {

    TaskDto getTaskById(Long id);

    List<TaskDto> getAllTasks();

    List<TaskDto> getTasksByCreator(String creator);

    List<TaskDto> getTasksByAssigned(String assigned);

    TaskDto createTask(TaskDto task);

    TaskDto updateTask(TaskDto task);

    void deleteTask(Long id, String creator);

}
