package com.mizarion.taskmanagement.service;

import com.mizarion.taskmanagement.dto.CommentResponseDto;
import com.mizarion.taskmanagement.entity.CommentEntity;
import com.mizarion.taskmanagement.entity.TaskEntity;
import com.mizarion.taskmanagement.entity.UserEntity;
import com.mizarion.taskmanagement.exception.trowables.TaskNotFoundException;
import com.mizarion.taskmanagement.repository.CommentRepository;
import com.mizarion.taskmanagement.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
    public CommentResponseDto addComment(Long taskId, String content, UserEntity currentUser) {
        TaskEntity task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(taskId));
        CommentEntity comment = CommentEntity.builder()
                .author(currentUser)
                .content(content)
                .task(task)
                .build();
        CommentEntity saved = commentRepository.save(comment);
        return modelMapper.map(saved, CommentResponseDto.class);
    }

    @Override
    public Page<CommentResponseDto> getCommentsByTask(Long taskId, Pageable pageable) {
        TaskEntity task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(taskId));

        List<CommentResponseDto> list = task.getComments().stream()
                .map(e -> modelMapper.map(e, CommentResponseDto.class))
                .toList();
        return new PageImpl<>(list, pageable, list.size());
    }
}
