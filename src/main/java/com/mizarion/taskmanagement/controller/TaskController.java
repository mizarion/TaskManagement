package com.mizarion.taskmanagement.controller;

import com.mizarion.taskmanagement.dto.TaskDto;
import com.mizarion.taskmanagement.service.TaskService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskDto> createTask(@RequestBody @Valid TaskDto taskDto,
                                              Authentication authentication) {
        taskDto.setCreator(authentication.getName());
        TaskDto task = taskService.createTask(taskDto);
        return ResponseEntity.ok(task);
    }

    @GetMapping()
    public ResponseEntity<List<TaskDto>> getTasks(@RequestParam(required = false)  String creator,
                                                  @RequestParam(required = false)  String assigned,
                                                  @PageableDefault(sort = "id") Pageable pageable) {
        return ResponseEntity.ok(taskService.getAllTasks(creator, assigned,pageable));
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable @Positive Long taskId,
                                              @RequestBody @Valid TaskDto taskDto,
                                              Authentication authentication) {
        taskDto.setId(taskId);
        taskDto.setCreator(authentication.getName());
        TaskDto updated = taskService.updateTask(taskDto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable @Positive Long taskId,
                                           Authentication authentication) {
        taskService.deleteTask(taskId, authentication.getName());
        return ResponseEntity.ok().build();
    }

}
