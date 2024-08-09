package com.mizarion.taskmanagement.service;

import com.mizarion.taskmanagement.dto.TaskDto;
import com.mizarion.taskmanagement.entity.TaskEntity;
import com.mizarion.taskmanagement.exception.trowables.TaskNotFoundException;
import com.mizarion.taskmanagement.exception.trowables.WrongUserException;
import com.mizarion.taskmanagement.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
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
    public TaskDto createTask(TaskDto task) {
        TaskEntity saved = taskRepository.save(modelMapper.map(task, TaskEntity.class));
        return modelMapper.map(saved, TaskDto.class);
    }

    @Override
    public TaskDto updateTask(TaskDto updateTask) {
        TaskEntity existingTask = taskRepository.findById(updateTask.getId())
                .orElseThrow(() -> new TaskNotFoundException(updateTask.getId()));
        if (!existingTask.getCreator().equals(updateTask.getCreator())) {
            throw new WrongUserException("You are not the creator of this task");
        }
        TaskEntity UpdatedEntity = modelMapper.map(updateTask, TaskEntity.class);
        UpdatedEntity.setComments(existingTask.getComments());
        taskRepository.save(UpdatedEntity);
        return updateTask;
    }

    @Override
    public void deleteTask(Long id, String creator) {
        TaskEntity existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        if (!existingTask.getCreator().equals(creator)) {
            throw new WrongUserException("You are not the creator of this task");
        }
        taskRepository.delete(existingTask);
    }

    @Override
    public List<TaskDto> getAllTasks(String creator, String assigned, Pageable pageable) {
        return taskRepository.findByCreatorAndAssigned(creator,assigned, pageable).stream()
                .map(e -> modelMapper.map(e, TaskDto.class))
                .toList();
    }
}
