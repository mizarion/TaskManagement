package com.mizarion.taskmanagement.controller;

import com.mizarion.taskmanagement.dto.UserDto;
import com.mizarion.taskmanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping()
    public ResponseEntity<Page<UserDto>> getAllUsers(@PageableDefault(size = 50, page = 0) Pageable pageable) {
        return ResponseEntity.ok(userService.getAllUsers(pageable));
    }
}
