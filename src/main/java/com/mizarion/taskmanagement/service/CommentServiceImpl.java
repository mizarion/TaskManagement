package com.mizarion.taskmanagement.service;

import com.mizarion.taskmanagement.dto.CommentDto;
import com.mizarion.taskmanagement.entity.CommentEntity;
import com.mizarion.taskmanagement.entity.TaskEntity;
import com.mizarion.taskmanagement.entity.UserEntity;
import com.mizarion.taskmanagement.exception.trowables.TaskNotFoundException;
import com.mizarion.taskmanagement.repository.CommentRepository;
import com.mizarion.taskmanagement.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final TaskRepository taskRepository;

    private final ModelMapper modelMapper;

    @Override
    public CommentDto addComment(Long taskId, String content, UserEntity currentUser) {
        TaskEntity task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(taskId));
        CommentEntity comment = CommentEntity.builder()
                .author(currentUser)
                .content(content)
                .task(task)
                .build();
        CommentEntity saved = commentRepository.save(comment);
        return modelMapper.map(saved, CommentDto.class);
    }

    @Override
    public List<CommentDto> getCommentsByTask(Long taskId) {
        TaskEntity task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(taskId));
        return task.getComments().stream()
                .map(e -> modelMapper.map(e, CommentDto.class))
                .toList();
    }
}
