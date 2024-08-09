package com.mizarion.taskmanagement.service;

import com.mizarion.taskmanagement.dto.CommentDto;
import com.mizarion.taskmanagement.entity.UserEntity;

import java.util.List;

public interface CommentService {

    CommentDto addComment(Long taskId, String content, UserEntity currentUser);

    List<CommentDto> getCommentsByTask(Long taskId);
}
