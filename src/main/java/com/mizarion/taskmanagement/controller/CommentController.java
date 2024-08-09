package com.mizarion.taskmanagement.controller;

import com.mizarion.taskmanagement.dto.CommentDto;
import com.mizarion.taskmanagement.entity.UserEntity;
import com.mizarion.taskmanagement.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/{taskId}/comments")
@RequiredArgsConstructor
@Slf4j
public class CommentController {


    private final CommentService commentService;

    @PostMapping()
    public ResponseEntity<CommentDto> addComment(@PathVariable Long taskId,
                                                 @RequestBody String content,
                                                 @AuthenticationPrincipal UserEntity currentUser) {
//        log.info("principal =" + currentUser.getEmail());
        CommentDto comment = commentService.addComment(taskId, content, currentUser);
        return ResponseEntity.ok(comment);
    }

    @GetMapping()
    public ResponseEntity<List<CommentDto>> getCommentsByTask(@PathVariable Long taskId) {
        List<CommentDto> comments = commentService.getCommentsByTask(taskId);
        return ResponseEntity.ok(comments);
    }

}
