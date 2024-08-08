package com.mizarion.taskmanagement.service;


import com.mizarion.taskmanagement.dto.AuthRequest;
import com.mizarion.taskmanagement.dto.RegisterRequest;
import com.mizarion.taskmanagement.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto register(RegisterRequest registerRequest);

    UserDto findByEmail(String email);

    String authenticate(AuthRequest authRequest);

    List<UserDto> getAllUsers();
}
