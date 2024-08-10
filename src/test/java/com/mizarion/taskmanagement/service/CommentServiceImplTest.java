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
        taskService.createTask(CreateTaskDTO, creator);
        Assertions.assertEquals(0, commentService.getCommentsByTask(taskDto.getId(), PAGEABLE).getContent().size());
        commentService.addComment(taskDto.getId(), "addComment", creator);
        Assertions.assertEquals(1, commentService.getCommentsByTask(taskDto.getId(), PAGEABLE).getContent().size());
    }

    @Test
    void tryToAddCommentToNonExistedTask() {
        Assertions.assertThrows(Exception.class,
                () -> commentService.getCommentsByTask(taskDto.getId(), PAGEABLE).getContent().size());
        Assertions.assertThrows(Exception.class,
                () -> commentService.addComment( taskDto.getId(), "tryToAddCommentToNonExistedTask", creator));
        Assertions.assertThrows(Exception.class,
                () -> commentService.getCommentsByTask(taskDto.getId(), PAGEABLE).getContent().size());
    }

    @Test
    void deleteCommentsWithTask() {
        taskService.createTask(CreateTaskDTO, creator);
        Assertions.assertEquals(0, commentService.getCommentsByTask(taskDto.getId(), PAGEABLE).getContent().size());
        commentService.addComment(taskDto.getId(), "addComment", creator);
        Assertions.assertEquals(1, commentService.getCommentsByTask(taskDto.getId(), PAGEABLE).getContent().size());

        taskService.deleteTask(taskDto.getId(), taskDto.getCreator());
        Assertions.assertThrows(Exception.class,
                () -> commentService.getCommentsByTask(taskDto.getId(), PAGEABLE).getContent().size());
    }

}