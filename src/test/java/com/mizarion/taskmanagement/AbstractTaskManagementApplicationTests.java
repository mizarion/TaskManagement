package com.mizarion.taskmanagement;

import com.mizarion.taskmanagement.dto.RegisterRequest;
import com.mizarion.taskmanagement.dto.tasks.CreateTaskRequestDto;
import com.mizarion.taskmanagement.dto.tasks.TaskDto;
import com.mizarion.taskmanagement.entity.TaskPriority;
import com.mizarion.taskmanagement.entity.TaskStatus;
import com.mizarion.taskmanagement.entity.UserEntity;
import com.mizarion.taskmanagement.service.TaskService;
import com.mizarion.taskmanagement.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public abstract class AbstractTaskManagementApplicationTests {

    protected static final UserEntity creator = UserEntity.builder()
            .id(1L)
            .email("creator@example.com")
            .password("password")
            .build();

    protected static final UserEntity creator2 = UserEntity.builder()
            .id(2L)
            .email("creator_2@example.com")
            .password("password")
            .build();

    protected static final UserEntity assigned = UserEntity.builder()
            .id(2L)
            .email("assigned@example.com")
            .password("password")
            .build();

    protected static final UserEntity assigned2 = UserEntity.builder()
            .id(2L)
            .email("assigned2@example.com")
            .password("password")
            .build();

    protected static final TaskDto taskDto = TaskDto.builder()
            .id(1L)
            .header("test header")
            .description("test")
            .status(TaskStatus.IN_PROGRESS)
            .priority(TaskPriority.MEDIUM)
            .creator(creator.getEmail())
            .assigned(assigned.getEmail())
            .build();

    protected static final CreateTaskRequestDto CreateTaskDTO = CreateTaskRequestDto.builder()
//            .id(1L)
            .header(taskDto.getHeader())
            .description(taskDto.getDescription())
            .status(TaskStatus.IN_PROGRESS)
            .priority(TaskPriority.MEDIUM)
//            .creator(creator)
            .assigned(taskDto.getAssigned())
            .build();

    protected static final TaskDto taskDto2 = TaskDto.builder()
            .id(2L)
            .header("test header 2")
            .description("test 2")
            .status(TaskStatus.IN_PROGRESS)
            .priority(TaskPriority.MEDIUM)
            .creator(creator.getEmail())
            .assigned(assigned.getEmail())
            .build();

    protected static final CreateTaskRequestDto CreateTaskDTO2 = CreateTaskRequestDto.builder()
//            .id(1L)
            .header(taskDto2.getHeader())
            .description(taskDto2.getDescription())
            .status(TaskStatus.IN_PROGRESS)
            .priority(TaskPriority.MEDIUM)
//            .creator(creator)
            .assigned(taskDto2.getAssigned())
            .build();

    protected static final TaskDto taskDtoCreator2 = TaskDto.builder()
            .id(2L)
            .header("test header 2")
            .description("test 2")
            .status(TaskStatus.IN_PROGRESS)
            .priority(TaskPriority.MEDIUM)
            .creator(creator2.getEmail())
            .assigned(assigned2.getEmail())
            .build();

    protected static final CreateTaskRequestDto createTaskDTOCreator2 = CreateTaskRequestDto.builder()
//            .id(1L)
            .header(taskDtoCreator2.getHeader())
            .description(taskDtoCreator2.getDescription())
            .status(TaskStatus.IN_PROGRESS)
            .priority(TaskPriority.MEDIUM)
//            .creator(creator)
            .assigned(taskDtoCreator2.getAssigned())
            .build();

    @Autowired
    protected TaskService taskService;

    @Autowired
    protected UserService userService;

    protected final static Pageable PAGEABLE = PageRequest.of(0, 10);


    @BeforeEach
    void setup() {
        userService.register(new RegisterRequest(creator.getEmail(), creator.getPassword()));
        userService.register(new RegisterRequest(creator2.getEmail(), creator2.getPassword()));
        userService.register(new RegisterRequest(assigned.getEmail(), assigned.getPassword()));
        userService.register(new RegisterRequest(assigned2.getEmail(), assigned2.getPassword()));
    }

    @Test
    void contextLoads() {
    }

}
