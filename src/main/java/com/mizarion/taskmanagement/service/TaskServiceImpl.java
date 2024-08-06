package com.mizarion.taskmanagement.service;

import com.mizarion.taskmanagement.dto.TaskDto;
import com.mizarion.taskmanagement.entity.TaskEntity;
import com.mizarion.taskmanagement.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    private final ModelMapper modelMapper;

    @Override
    public TaskDto getTaskById(Long id) {
        return modelMapper.map(taskRepository.findById(id).get(), TaskDto.class);
    }

    @Override
    public List<TaskDto> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(e -> modelMapper.map(e, TaskDto.class))
                .toList();
    }

    @Override
    public List<TaskDto> getTasksByCreator(String creator) {
        return taskRepository.getTaskEntitiesByCreator(creator)
                .stream().map(e -> modelMapper.map(e, TaskDto.class))
                .toList();
    }

    @Override
    public List<TaskDto> getTasksByAssigned(String assigned) {
        return taskRepository.getTaskEntitiesByAssigned(assigned)
                .stream().map(e -> modelMapper.map(e, TaskDto.class))
                .toList();
    }

    @Override
    public TaskDto createTask(TaskDto task) {
        TaskEntity entity = modelMapper.map(task, TaskEntity.class);
//        entity.setCreator(creator);
        TaskEntity saved = taskRepository.save(entity);
        return modelMapper.map(saved, TaskDto.class);
    }

    @Override
    public TaskDto updateTask(TaskDto updateTask) {
        TaskEntity existingTask = taskRepository.findById(updateTask.getId())
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (!existingTask.getCreator().equals(updateTask.getCreator())) {
            throw new RuntimeException("You are not the creator of this task");
        }

        TaskEntity UpdatedEntity = modelMapper.map(updateTask, TaskEntity.class);
        taskRepository.save(UpdatedEntity);
        return updateTask;
    }

    @Override
    public void deleteTask(Long id, String creator) {
        TaskEntity existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (!existingTask.getCreator().equals(creator)) {
            throw new RuntimeException("You are not the creator of this task");
        }

        taskRepository.delete(existingTask);
    }
}
