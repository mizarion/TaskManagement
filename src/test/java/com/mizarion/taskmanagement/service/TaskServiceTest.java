package com.mizarion.taskmanagement.service;

import com.mizarion.taskmanagement.AbstractTaskManagementApplicationTests;
import com.mizarion.taskmanagement.TaskPriority;
import com.mizarion.taskmanagement.TaskStatus;
import com.mizarion.taskmanagement.dto.TaskDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;


class TaskServiceTest extends AbstractTaskManagementApplicationTests {

    @Autowired
    private TaskService taskService;


    private final TaskDto taskDtoUpdated = TaskDto.builder()
            .id(taskDto.getId())
            .header("test complete")
            .description("complete")
            .status(TaskStatus.COMPLETED)
            .priority(TaskPriority.HIGH)
            .creator(creator.getEmail())
            .assigned(creator.getEmail())
            .build();

    private final TaskDto taskDtoWrongCreator = TaskDto.builder()
            .id(taskDto.getId())
            .header("test complete")
            .description("complete")
            .status(TaskStatus.COMPLETED)
            .priority(TaskPriority.HIGH)
            .creator("wrong" + creator.getEmail())
            .assigned(creator.getEmail())
            .build();

    private final TaskDto taskDto2 = TaskDto.builder()
            .id(2L)
            .header("test header 2")
            .description("test 2")
            .status(TaskStatus.IN_PROGRESS)
            .priority(TaskPriority.MEDIUM)
            .creator(creator.getEmail())
            .assigned(assigned.getEmail())
            .build();

    private final TaskDto taskDtoSecondCreator = TaskDto.builder()
            .id(2L)
            .header("test header 2")
            .description("test 2")
            .status(TaskStatus.IN_PROGRESS)
            .priority(TaskPriority.MEDIUM)
            .creator(creator.getEmail() + "2")
            .assigned(assigned.getEmail() + "2")
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

    @Test
    void findByCreatorOrAssigned() {
        TaskDto task = TaskDto.builder()
                .id(10L)
                .header("test header")
                .description("test")
                .status(TaskStatus.IN_PROGRESS)
                .priority(TaskPriority.MEDIUM)
                .creator(creator.getEmail())
                .assigned(assigned.getEmail())
                .build();

        Assertions.assertEquals(0, taskService.getAllTasks().size());
        taskService.createTask(task);
        Assertions.assertEquals(1, taskService.getAllTasks().size());
        Pageable pageable = PageRequest.of(0, 10);

        // correct
        Assertions.assertEquals(1, taskService.getAllTasks(null, null, pageable).size());
        Assertions.assertEquals(1, taskService.getAllTasks(task.getCreator(), null, pageable).size());
        Assertions.assertEquals(1, taskService.getAllTasks(null, task.getAssigned(), pageable).size());
        Assertions.assertEquals(1, taskService.getAllTasks(task.getCreator(), task.getAssigned(), pageable).size());

        // wrong
        Assertions.assertEquals(0, taskService.getAllTasks( task.getAssigned(), null, pageable).size());
        Assertions.assertEquals(0, taskService.getAllTasks( null, task.getCreator(), pageable).size());
        Assertions.assertEquals(0, taskService.getAllTasks( task.getAssigned(), task.getCreator(), pageable).size());
    }

    @Test
    void findByCreatorOrAssigned2() {

        Assertions.assertEquals(0, taskService.getAllTasks().size());
        taskService.createTask(taskDto);
        taskService.createTask(taskDtoSecondCreator);
        Assertions.assertEquals(2, taskService.getAllTasks().size());
        Pageable pageable = PageRequest.of(0, 10);

        // correct
        Assertions.assertEquals(2, taskService.getAllTasks(null, null, pageable).size());
        Assertions.assertEquals(1, taskService.getAllTasks(taskDto.getCreator(), null, pageable).size());
        Assertions.assertEquals(1, taskService.getAllTasks(null, taskDto.getAssigned(), pageable).size());
        Assertions.assertEquals(1, taskService.getAllTasks(taskDto.getCreator(), taskDto.getAssigned(), pageable).size());

        // wrong
        Assertions.assertEquals(0, taskService.getAllTasks( taskDto.getAssigned(), null, pageable).size());
        Assertions.assertEquals(0, taskService.getAllTasks( null, taskDto.getCreator(), pageable).size());
        Assertions.assertEquals(0, taskService.getAllTasks( taskDto.getAssigned(), taskDto.getCreator(), pageable).size());
    }

}