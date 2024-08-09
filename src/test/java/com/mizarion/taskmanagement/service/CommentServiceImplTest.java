package com.mizarion.taskmanagement.service;

import com.mizarion.taskmanagement.AbstractTaskManagementApplicationTests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class CommentServiceImplTest extends AbstractTaskManagementApplicationTests {

    @Autowired
    private CommentService commentService;

    @Test
    void addComment() {
        taskService.createTask(taskDto);
        Assertions.assertEquals(0, commentService.getCommentsByTask(taskDto.getId()).size());
        commentService.addComment(taskDto.getId(), "addComment", creator);
        Assertions.assertEquals(1, commentService.getCommentsByTask(taskDto.getId()).size());
    }

    @Test
    void tryToAddCommentToNonExistedTask() {
        Assertions.assertThrows(Exception.class,
                () -> Assertions.assertEquals(0, commentService.getCommentsByTask(taskDto.getId()).size()));
        Assertions.assertThrows(Exception.class,
                () -> commentService.addComment( taskDto.getId(), "tryToAddCommentToNonExistedTask", creator));
        Assertions.assertThrows(Exception.class,
                () -> Assertions.assertEquals(0, commentService.getCommentsByTask(taskDto.getId()).size()));
    }
}