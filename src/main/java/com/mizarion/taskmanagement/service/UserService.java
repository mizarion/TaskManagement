package com.mizarion.taskmanagement.service;


import com.mizarion.taskmanagement.dto.RegisterRequest;
import com.mizarion.taskmanagement.dto.UserDto;
import com.mizarion.taskmanagement.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    UserDto register(RegisterRequest registerRequest);

    UserEntity findUserEntityByEmail(String email);

    Page<UserDto> getAllUsers(Pageable pageable);
}
