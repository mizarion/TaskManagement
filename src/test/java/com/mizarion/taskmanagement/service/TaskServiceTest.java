package com.mizarion.taskmanagement.service;

import com.mizarion.taskmanagement.AbstractTaskManagementApplicationTests;
import com.mizarion.taskmanagement.dto.tasks.CreateTaskRequestDto;
import com.mizarion.taskmanagement.dto.tasks.TaskDto;
import com.mizarion.taskmanagement.entity.TaskPriority;
import com.mizarion.taskmanagement.entity.TaskStatus;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Slf4j
class TaskServiceTest extends AbstractTaskManagementApplicationTests {

    @Autowired
    private TaskService taskService;

    private final TaskDto taskDtoWrongCreator = TaskDto.builder()
            .id(taskDto.getId())
            .header("test complete")
            .description("complete")
            .status(TaskStatus.COMPLETED)
            .priority(TaskPriority.HIGH)
            .creator( creator2.getEmail())
            .assigned(assigned2.getEmail())
            .build();



    @Test
    void getAllTasks() {
        Assertions.assertTrue(taskService.getAllTasks().isEmpty());
        taskService.createTask(CreateTaskDTO, creator);
        List<TaskDto> allTasks = taskService.getAllTasks();
        Assertions.assertEquals(1, allTasks.size());
        Assertions.assertEquals(taskDto, allTasks.get(0));
    }

    @Test
    void createTaskBiDifCreator() {
        Assertions.assertTrue(taskService.getAllTasks().isEmpty());
        TaskDto task1 = taskService.createTask(CreateTaskDTO, creator);
        TaskDto task2 = taskService.createTask(createTaskDTOCreator2, creator2);
        List<TaskDto> allTasks = taskService.getAllTasks();
        Assertions.assertEquals(2, allTasks.size());

        Assertions.assertEquals(taskDto, taskService.getTaskById(task1.getId()));
        Assertions.assertEquals(taskDtoCreator2, taskService.getTaskById(task2.getId()));

        Assertions.assertEquals(taskDto, allTasks.get(0));
    }


    @Test
    void createAndGetTaskById() {
        taskService.createTask(CreateTaskDTO, creator);
        Assertions.assertEquals(taskDto, taskService.getTaskById(1L));
    }

    @Test
    void updateTask() {
        TaskDto taskDtoUpdated = TaskDto.builder()
                .id(taskDto.getId())
                .header("test complete")
                .description("complete")
                .status(TaskStatus.COMPLETED)
                .priority(TaskPriority.HIGH)
                .creator(creator.getEmail())
                .assigned(assigned.getEmail())
                .build();

        taskService.createTask(CreateTaskDTO, creator);
        Assertions.assertEquals(1, taskService.getAllTasks().size());
        Assertions.assertEquals(taskDto, taskService.getAllTasks().get(0));

        taskService.updateTask(taskDtoUpdated, creator);
        List<TaskDto> allTasks = taskService.getAllTasks();
        Assertions.assertEquals(1, allTasks.size());
        Assertions.assertNotEquals(taskDto, allTasks.get(0));
        Assertions.assertEquals(taskDtoUpdated, allTasks.get(0));
    }

    @Test
    void updateWTaskAndChangeOwnerShipAndAssigned() {
        taskService.createTask(CreateTaskDTO, creator);
        Assertions.assertEquals(1, taskService.getAllTasks().size());
        Assertions.assertThrows(Exception.class, () -> taskService.updateTask(taskDtoWrongCreator, creator));
        List<TaskDto> allTasks = taskService.getAllTasks();

        Assertions.assertEquals(1, allTasks.size());
        Assertions.assertEquals(taskDto, allTasks.get(0));
        Assertions.assertNotEquals(taskDtoWrongCreator, allTasks.get(0));
    }

    @Test
    void deleteTask() {
        taskService.createTask(CreateTaskDTO, creator);
        taskService.createTask(CreateTaskDTO2, creator);
        Assertions.assertEquals(2, taskService.getAllTasks().size());

        taskService.deleteTask(taskDto.getId(), taskDto.getCreator());
        Assertions.assertEquals(1, taskService.getAllTasks().size());
        Assertions.assertEquals(taskDto2, taskService.getAllTasks().get(0));

        taskService.deleteTask(taskDtoCreator2.getId(), taskDto.getCreator());
        Assertions.assertEquals(0, taskService.getAllTasks().size());
    }

    @Test
    void deleteTaskTwice() {
        taskService.createTask(CreateTaskDTO, creator);
        Assertions.assertEquals(1, taskService.getAllTasks().size());

        taskService.deleteTask(taskDto.getId(), taskDto.getCreator());
        Assertions.assertEquals(0, taskService.getAllTasks().size());
        Assertions.assertThrows(Exception.class, () -> taskService.deleteTask(taskDto.getId(), taskDto.getCreator()));
        Assertions.assertEquals(0, taskService.getAllTasks().size());
    }

    @Test
    void deleteTaskWrongCreator() {
        taskService.createTask(CreateTaskDTO, creator);
        Assertions.assertEquals(1, taskService.getAllTasks().size());

        Assertions.assertThrows(Exception.class, () ->
                taskService.deleteTask(taskDtoWrongCreator.getId(), taskDtoWrongCreator.getCreator()));
        Assertions.assertEquals(1, taskService.getAllTasks().size());
    }

    @Test
    void findByCreatorOrAssigned() {
        CreateTaskRequestDto task = CreateTaskRequestDto.builder()
//                .id(10L)
                .header("test header")
                .description("test")
                .status(TaskStatus.IN_PROGRESS)
                .priority(TaskPriority.MEDIUM)
//                .creator(creator.getEmail())
                .assigned(assigned.getEmail())
                .build();

        Assertions.assertEquals(0, taskService.getAllTasks().size());
        taskService.createTask(task, creator);

        Assertions.assertEquals(1, taskService.getAllTasks().size());
        Pageable pageable = PageRequest.of(0, 10);

        // correct
        Assertions.assertEquals(1, taskService.getAllTasks(null, null, pageable).getContent().size());
        Assertions.assertEquals(1, taskService.getAllTasks(creator.getEmail(), null, pageable).getContent().size());
        Assertions.assertEquals(1, taskService.getAllTasks(null, task.getAssigned(), pageable).getContent().size());
        Assertions.assertEquals(1, taskService.getAllTasks(creator.getEmail(), task.getAssigned(), pageable).getContent().size());

        // wrong
        Assertions.assertEquals(0, taskService.getAllTasks( task.getAssigned(), null, pageable).getContent().size());
        Assertions.assertEquals(0, taskService.getAllTasks(null, creator.getEmail(), pageable).getContent().size());
        Assertions.assertEquals(0, taskService.getAllTasks(task.getAssigned(), creator.getEmail(), pageable).getContent().size());
    }

    @Test
    void findByCreatorOrAssigned2() {
        Assertions.assertEquals(0, taskService.getAllTasks().size());
        taskService.createTask(CreateTaskDTO, creator);
        taskService.createTask(createTaskDTOCreator2, creator2);
        Pageable pageable = PageRequest.of(0, 10);

        // correct
        Assertions.assertEquals(2, taskService.getAllTasks().size());
        Assertions.assertEquals(2, taskService.getAllTasks(null, null, pageable).getContent().size());
        Assertions.assertEquals(1, taskService.getAllTasks(creator.getEmail(), null, pageable).getContent().size());
        Assertions.assertEquals(1, taskService.getAllTasks(creator2.getEmail(), null, pageable).getContent().size());
        Assertions.assertEquals(1, taskService.getAllTasks(null, assigned.getEmail(), pageable).getContent().size());
        Assertions.assertEquals(1, taskService.getAllTasks(null, assigned2.getEmail(), pageable).getContent().size());
        Assertions.assertEquals(1, taskService.getAllTasks(taskDto.getCreator(), taskDto.getAssigned(), pageable).getContent().size());

        // wrong
        Assertions.assertEquals(0, taskService.getAllTasks( taskDto.getAssigned(), null, pageable).getContent().size());
        Assertions.assertEquals(0, taskService.getAllTasks( null, taskDto.getCreator(), pageable).getContent().size());
        Assertions.assertEquals(0, taskService.getAllTasks( taskDto.getAssigned(), taskDto.getCreator(), pageable).getContent().size());
    }

    @Test
    void findAllWithNulls() {
        taskService.createTask(CreateTaskDTO, creator);
        Pageable pageable = PageRequest.of(0, 10);
        Assertions.assertEquals(1, taskService.getAllTasks(null, null, pageable).getContent().size());
        Assertions.assertEquals(taskService.getAllTasks(), taskService.getAllTasks(null, null, pageable).getContent());

        Assertions.assertEquals(1, taskService.getAllTasks(taskDto.getCreator(), null, pageable).getContent().size());
    }

    @Test
    void findAllWithNullsWithOutAssigned() {
        CreateTaskRequestDto createTaskRequestDto = CreateTaskRequestDto.builder()
                .header(taskDto.getHeader())
                .description(taskDto.getDescription())
                .status(TaskStatus.IN_PROGRESS)
                .priority(TaskPriority.MEDIUM)
                .build();

        taskService.createTask(createTaskRequestDto, creator);
        Assertions.assertEquals(1, taskService.getAllTasks().size());
        Assertions.assertEquals(1, taskService.getAllTasks(null, null, PAGEABLE).getContent().size());
        Assertions.assertEquals(taskService.getAllTasks(), taskService.getAllTasks(null, null, PAGEABLE).getContent());

        Assertions.assertEquals(1, taskService.getAllTasks(creator.getEmail(), null, PAGEABLE).getContent().size());
    }

}