package com.mizarion.taskmanagement.service;

import com.mizarion.taskmanagement.dto.TaskDto;

import java.util.List;

public interface TaskService {

    TaskDto getTaskById(Long id);

    List<TaskDto> getAllTasks();

    List<TaskDto> getTasksByCreator(String creator);

    List<TaskDto> getTasksByAssigned(String creator);

    TaskDto createTask(TaskDto task, String creator);

    TaskDto updateTask(TaskDto task, Long taskID, String creator);

    void deleteTask(TaskDto task, String creator);

}
