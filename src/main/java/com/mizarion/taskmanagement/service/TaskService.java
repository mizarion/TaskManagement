package com.mizarion.taskmanagement.service;

import com.mizarion.taskmanagement.dto.tasks.CreateTaskRequestDto;
import com.mizarion.taskmanagement.dto.tasks.TaskDto;
import com.mizarion.taskmanagement.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TaskService {

    TaskDto getTaskById(Long id);

    List<TaskDto> getAllTasks();

    Page<TaskDto> getAllTasks(String creator, String assigned, Pageable pageable);

    TaskDto createTask(CreateTaskRequestDto task, UserEntity user);

    TaskDto updateTask(TaskDto task, UserEntity user);

    void deleteTask(Long id, String currentUser);

}
