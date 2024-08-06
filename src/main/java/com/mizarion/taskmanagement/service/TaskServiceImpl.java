package com.mizarion.taskmanagement.service;

import com.mizarion.taskmanagement.dto.TaskDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Override
    public TaskDto getTaskById(Long id) {
        return null;
    }

    @Override
    public List<TaskDto> getAllTasks() {
        return null;
    }

    @Override
    public List<TaskDto> getTasksByCreator(String creator) {
        return null;
    }

    @Override
    public List<TaskDto> getTasksByAssigned(String creator) {
        return null;
    }

    @Override
    public TaskDto createTask(TaskDto task, String creator) {
        return null;
    }

    @Override
    public TaskDto updateTask(TaskDto task, Long taskID, String creator) {
        return null;
    }

    @Override
    public void deleteTask(TaskDto task, String creator) {

    }
}
