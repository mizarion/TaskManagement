package com.mizarion.taskmanagement.service;

import com.mizarion.taskmanagement.dto.AuthRequest;
import com.mizarion.taskmanagement.dto.RegisterRequest;
import com.mizarion.taskmanagement.dto.UserDto;
import com.mizarion.taskmanagement.entity.UserEntity;
import com.mizarion.taskmanagement.exception.trowables.UserAlreadyExistException;
import com.mizarion.taskmanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    @Override
    public UserDto register(RegisterRequest registerRequest) {

       if( userRepository.findByEmail(registerRequest.getEmail()).isPresent()){
           throw new UserAlreadyExistException(registerRequest.getEmail());
       }
        UserEntity saved = userRepository.save(modelMapper.map(registerRequest, UserEntity.class));
        return modelMapper.map(saved, UserDto.class);
    }

    @Override
    public UserDto findByEmail(String email) {
        return null;
    }

    @Override
    public String authenticate(AuthRequest authRequest) {
        return null;
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(e -> modelMapper.map(e, UserDto.class))
                .toList();
    }
}
