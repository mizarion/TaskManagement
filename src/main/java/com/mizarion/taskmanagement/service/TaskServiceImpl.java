package com.mizarion.taskmanagement.service;

import com.mizarion.taskmanagement.dto.tasks.CreateTaskRequestDto;
import com.mizarion.taskmanagement.dto.tasks.TaskDto;
import com.mizarion.taskmanagement.dto.tasks.UpdateTaskRequestDto;
import com.mizarion.taskmanagement.entity.TaskEntity;
import com.mizarion.taskmanagement.entity.UserEntity;
import com.mizarion.taskmanagement.exception.trowables.TaskNotFoundException;
import com.mizarion.taskmanagement.exception.trowables.WrongUserException;
import com.mizarion.taskmanagement.repository.TaskRepository;
import com.mizarion.taskmanagement.repository.TaskSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    private final UserService userService;

    private final ModelMapper modelMapper;

    @Override
    public TaskDto getTaskById(Long id) {
        TaskEntity entity = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        return modelMapper.map(entity, TaskDto.class);
    }

    @Override
    public List<TaskDto> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(e -> modelMapper.map(e, TaskDto.class))
                .toList();
    }

    @Override
    public TaskDto createTask(CreateTaskRequestDto task, UserEntity currentUser) {
        TaskEntity entity = modelMapper.map(task, TaskEntity.class);
        if (task.getAssigned() != null && !task.getAssigned().isBlank()) {
            entity.setAssigned(userService.findUserEntityByEmail(task.getAssigned()));
        }
        entity.setCreator(currentUser);
        TaskEntity saved = taskRepository.save(entity);
        return modelMapper.map(saved, TaskDto.class);
    }

    @Override
    public TaskDto updateTask(UpdateTaskRequestDto updateTask, UserEntity user) {
        TaskEntity existingTask = taskRepository.findById(updateTask.getId())
                .orElseThrow(() -> new TaskNotFoundException(updateTask.getId()));
        if (existingTask.getCreator().getEmail().equals(user.getUsername())) {
            existingTask.setHeader(updateTask.getHeader());
            existingTask.setDescription(updateTask.getDescription());
            existingTask.setStatus(updateTask.getStatus());
            existingTask.setPriority(updateTask.getPriority());
            if (updateTask.getAssigned() != null && !updateTask.getAssigned().isBlank()) {
                existingTask.setAssigned(userService.findUserEntityByEmail(updateTask.getAssigned()));
            }
        } else if (existingTask.getAssigned().getEmail().equals(user.getUsername())) {
            existingTask.setStatus(updateTask.getStatus());
        } else {
            throw new WrongUserException("you are neither the creator nor the assigned");
        }
        TaskEntity saved = taskRepository.save(existingTask);
        return modelMapper.map(saved, TaskDto.class);
    }

    @Override
    public void deleteTask(Long id, String currentUser) {
        TaskEntity existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        if (!existingTask.getCreator().getEmail().equals(currentUser)) {
            throw new WrongUserException("You are not the creator of this task");
        }
        taskRepository.delete(existingTask);
    }

    @Override
    public Page<TaskDto> getAllTasks(String creator, String assigned, Pageable pageable) {

        Specification<TaskEntity> spec = TaskSpecification.byCreatorAndAssigned(creator, assigned);
        List<TaskDto> list = taskRepository.findAll(spec)
                .stream()
                .map(e -> modelMapper.map(e, TaskDto.class))
                .toList();

        return new PageImpl<>(list, pageable, list.size());
    }


}
