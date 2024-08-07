package com.mizarion.taskmanagement.controller;

import com.mizarion.taskmanagement.dto.UserDto;
import com.mizarion.taskmanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping()
    public ResponseEntity<List<UserDto>> getAllUsers() {

        List<UserDto> user = userService.getAllUsers();
        return ResponseEntity.ok(user);
    }
}
