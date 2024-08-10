package com.mizarion.taskmanagement.controller;

import com.mizarion.taskmanagement.dto.tasks.CreateTaskRequestDto;
import com.mizarion.taskmanagement.dto.tasks.TaskDto;
import com.mizarion.taskmanagement.entity.UserEntity;
import com.mizarion.taskmanagement.service.TaskService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskDto> createTask(@RequestBody @Valid CreateTaskRequestDto taskDto,
                                              @AuthenticationPrincipal UserEntity currentUser) {
        TaskDto task = taskService.createTask(taskDto, currentUser);
        return ResponseEntity.ok(task);
    }

    @GetMapping()
    public ResponseEntity<Page<TaskDto>> getTasks(@RequestParam(required = false) String creator,
                                                  @RequestParam(required = false) String assigned,
                                                  @PageableDefault(size = 50, page = 0) Pageable pageable) {
        return ResponseEntity.ok(taskService.getAllTasks(creator, assigned, pageable));
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable @Positive Long taskId,
                                              @RequestBody @Valid TaskDto taskDto,
                                              @AuthenticationPrincipal UserEntity currentUser) {
        taskDto.setId(taskId);
        TaskDto updated = taskService.updateTask(taskDto, currentUser);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable @Positive Long taskId,
                                           @AuthenticationPrincipal UserEntity currentUser) {
        taskService.deleteTask(taskId, currentUser.getEmail());
        return ResponseEntity.ok().build();
    }
}
