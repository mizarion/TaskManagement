package com.mizarion.taskmanagement.service;

import com.mizarion.taskmanagement.TaskPriority;
import com.mizarion.taskmanagement.TaskStatus;
import com.mizarion.taskmanagement.dto.TaskDto;
import com.mizarion.taskmanagement.entity.UserEntity;
import com.mizarion.taskmanagement.repository.TaskRepository;
import com.mizarion.taskmanagement.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class TaskServiceTest {

    @Autowired
    private TaskService taskService;


    UserEntity creator = UserEntity.builder()
            .id(1L)
            .email("creator@example.com")
            .build();

    UserEntity assigned = UserEntity.builder()
            .id(2L)
            .email("assigned@example.com")
            .build();

    TaskDto taskDto = TaskDto.builder()
            .id(1L)
            .header("test header")
            .description("test")
            .status(TaskStatus.IN_PROGRESS)
            .priority(TaskPriority.MEDIUM)
            .creator(creator.getEmail())
            .assigned(assigned.getEmail())
            .build();


    @Test
    void getAllTasks() {
        Assertions.assertTrue(taskService.getAllTasks().isEmpty());

        taskService.createTask(taskDto, creator.getEmail());
        Assertions.assertEquals(1, taskService.getAllTasks().size());
    }

    @Test
    void createAndGetTaskById() {
        taskService.createTask(taskDto, creator.getEmail());
        Assertions.assertEquals(taskDto, taskService.getTaskById(1L));
    }

//    @Test
//    void getTasksByCreator() {
////        taskService.createTask(taskDto, creator.getEmail());
////        Assertions.assertEquals(taskDto, taskService.getTasksByCreator(creator.getEmail()));
//    }

//    @Test
//    void getTasksByAssigned() {
//    }
//
//    @Test
//    void updateTask() {
//    }
//
//    @Test
//    void deleteTask() {
//    }
}