package com.mizarion.taskmanagement;

import com.mizarion.taskmanagement.dto.RegisterRequest;
import com.mizarion.taskmanagement.dto.TaskDto;
import com.mizarion.taskmanagement.entity.UserEntity;
import com.mizarion.taskmanagement.service.TaskService;
import com.mizarion.taskmanagement.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public abstract class AbstractTaskManagementApplicationTests {

    protected static final UserEntity creator = UserEntity.builder()
            .id(1L)
            .email("creator@example.com")
            .build();
    protected static final UserEntity assigned = UserEntity.builder()
            .id(2L)
            .email("assigned@example.com")
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

    @Autowired
    protected TaskService taskService;

    @Autowired
    protected UserService userService;

    @BeforeEach
    void setup() {
        userService.register(new RegisterRequest(creator.getEmail(), creator.getPassword()));
        userService.register(new RegisterRequest(assigned.getEmail(), assigned.getPassword()));
    }

    @Test
    void contextLoads() {
    }

}
