package com.mizarion.taskmanagement.service;

import com.mizarion.taskmanagement.dto.CommentResponseDto;
import com.mizarion.taskmanagement.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {

    CommentResponseDto addComment(Long taskId, String content, UserEntity currentUser);

    Page<CommentResponseDto> getCommentsByTask(Long taskIdm, Pageable pageable);
}
