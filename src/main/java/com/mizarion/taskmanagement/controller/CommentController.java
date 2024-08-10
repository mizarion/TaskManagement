package com.mizarion.taskmanagement.controller;

import com.mizarion.taskmanagement.dto.CommentResponseDto;
import com.mizarion.taskmanagement.entity.UserEntity;
import com.mizarion.taskmanagement.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/{taskId}/comments")
@RequiredArgsConstructor
@Slf4j
public class CommentController {


    private final CommentService commentService;

    @PostMapping()
    public ResponseEntity<CommentResponseDto> addComment(@PathVariable Long taskId,
                                                         @RequestBody String content,
                                                         @AuthenticationPrincipal UserEntity currentUser) {
        CommentResponseDto comment = commentService.addComment(taskId, content, currentUser);
        return ResponseEntity.ok(comment);
    }

    @GetMapping()
    public ResponseEntity<Page<CommentResponseDto>> getCommentsByTask(@PathVariable Long taskId,
                                                                      @PageableDefault(size = 50, page = 0) Pageable pageable) {
        Page<CommentResponseDto> comments = commentService.getCommentsByTask(taskId, pageable);
        return ResponseEntity.ok(comments);
    }

}
