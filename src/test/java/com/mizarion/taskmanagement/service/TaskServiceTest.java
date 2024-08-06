package com.mizarion.taskmanagement.service;

import com.mizarion.taskmanagement.TaskPriority;
import com.mizarion.taskmanagement.TaskStatus;
import com.mizarion.taskmanagement.dto.TaskDto;
import com.mizarion.taskmanagement.entity.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

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

    TaskDto taskDto2 = TaskDto.builder()
            .id(2L)
            .header("test header 2")
            .description("test 2")
            .status(TaskStatus.IN_PROGRESS)
            .priority(TaskPriority.MEDIUM)
            .creator(creator.getEmail())
            .assigned(assigned.getEmail())
            .build();


    TaskDto taskDtoUpdated = TaskDto.builder()
            .id(taskDto.getId())
            .header("test complete")
            .description("complete")
            .status(TaskStatus.COMPLETED)
            .priority(TaskPriority.HIGH)
            .creator(creator.getEmail())
            .assigned(creator.getEmail())
            .build();

    TaskDto taskDtoWrongCreator = TaskDto.builder()
            .id(taskDto.getId())
            .header("test complete")
            .description("complete")
            .status(TaskStatus.COMPLETED)
            .priority(TaskPriority.HIGH)
            .creator("wrong" + creator.getEmail())
            .assigned(creator.getEmail())
            .build();


    @Test
    void getAllTasks() {
        Assertions.assertTrue(taskService.getAllTasks().isEmpty());
        taskService.createTask(taskDto);
        List<TaskDto> allTasks = taskService.getAllTasks();
        Assertions.assertEquals(1, allTasks.size());
        Assertions.assertEquals(taskDto, allTasks.get(0));
    }

    @Test
    void createAndGetTaskById() {
        taskService.createTask(taskDto);
        Assertions.assertEquals(taskDto, taskService.getTaskById(1L));
    }

    @Test
    void getTasksByCreator() {
        Assertions.assertTrue(taskService.getAllTasks().isEmpty());
        taskService.createTask(taskDto);
        List<TaskDto> tasksByCreator = taskService.getTasksByCreator(creator.getEmail());
        Assertions.assertEquals(1, tasksByCreator.size());
        Assertions.assertEquals(taskDto, taskService.getTasksByCreator(creator.getEmail()).get(0));
    }

    @Test
    void getTasksByAssigned() {
        Assertions.assertTrue(taskService.getAllTasks().isEmpty());
        taskService.createTask(taskDto);
        List<TaskDto> tasksByCreator = taskService.getTasksByAssigned(assigned.getEmail());
        Assertions.assertEquals(1, tasksByCreator.size());
        Assertions.assertEquals(taskDto, taskService.getTasksByAssigned(assigned.getEmail()).get(0));
    }

    @Test
    void updateTask() {
        taskService.createTask(taskDto);
        Assertions.assertEquals(1, taskService.getAllTasks().size());

        taskService.updateTask(taskDtoUpdated);
        List<TaskDto> allTasks = taskService.getAllTasks();
        Assertions.assertEquals(1, allTasks.size());
        Assertions.assertNotEquals(taskDto, allTasks.get(0));
        Assertions.assertEquals(taskDtoUpdated, allTasks.get(0));
    }

    @Test
    void updateWrongTask() {

        taskService.createTask(taskDto);
        Assertions.assertEquals(1, taskService.getAllTasks().size());

        Assertions.assertThrows(Exception.class, () -> taskService.updateTask(taskDtoWrongCreator));
        List<TaskDto> allTasks = taskService.getAllTasks();

        Assertions.assertEquals(1, allTasks.size());
        Assertions.assertEquals(taskDto, allTasks.get(0));
        Assertions.assertNotEquals(taskDtoWrongCreator, allTasks.get(0));
    }


    @Test
    void deleteTask() {
        taskService.createTask(taskDto);
        taskService.createTask(taskDto2);
        Assertions.assertEquals(2, taskService.getAllTasks().size());

        taskService.deleteTask(taskDto.getId(), taskDto.getCreator());
        Assertions.assertEquals(1, taskService.getAllTasks().size());
        Assertions.assertEquals(taskDto2, taskService.getAllTasks().get(0));

        taskService.deleteTask(taskDto2.getId(), taskDto.getCreator());
        Assertions.assertEquals(0, taskService.getAllTasks().size());
    }

    @Test
    void deleteTaskTwice() {
        taskService.createTask(taskDto);
        Assertions.assertEquals(1, taskService.getAllTasks().size());

        taskService.deleteTask(taskDto.getId(), taskDto.getCreator());
        Assertions.assertEquals(0, taskService.getAllTasks().size());
        Assertions.assertThrows(Exception.class, () -> taskService.deleteTask(taskDto.getId(), taskDto.getCreator()));
        Assertions.assertEquals(0, taskService.getAllTasks().size());
    }

    @Test
    void deleteTaskWrongCreator() {
        taskService.createTask(taskDto);
        Assertions.assertEquals(1, taskService.getAllTasks().size());

        Assertions.assertThrows(Exception.class, () ->
                taskService.deleteTask(taskDtoWrongCreator.getId(), taskDtoWrongCreator.getCreator()));
        Assertions.assertEquals(1, taskService.getAllTasks().size());
    }
}