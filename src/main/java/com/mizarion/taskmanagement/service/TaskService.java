package com.mizarion.taskmanagement.service;

import com.mizarion.taskmanagement.dto.TaskDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TaskService {

    TaskDto getTaskById(Long id);

    List<TaskDto> getAllTasks();

    List<TaskDto> getAllTasks(String creator, String assigned, Pageable pageable);

    TaskDto createTask(TaskDto task);

    TaskDto updateTask(TaskDto task);

    void deleteTask(Long id, String creator);
}
