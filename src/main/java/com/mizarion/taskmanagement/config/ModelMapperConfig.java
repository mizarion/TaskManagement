package com.mizarion.taskmanagement.config;

import com.mizarion.taskmanagement.dto.CommentResponseDto;
import com.mizarion.taskmanagement.dto.tasks.TaskDto;
import com.mizarion.taskmanagement.entity.CommentEntity;
import com.mizarion.taskmanagement.entity.TaskEntity;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.typeMap(CommentEntity.class, CommentResponseDto.class).addMappings(mapper -> {
            mapper.map(src -> src.getAuthor().getEmail(), CommentResponseDto::setAuthorEmail);
            mapper.map(src -> src.getTask().getId(), CommentResponseDto::setTaskId);
        });

        modelMapper.typeMap(TaskEntity.class, TaskDto.class).addMappings(mapper -> {
            mapper.map(src -> src.getCreator().getEmail(), TaskDto::setCreator);
            mapper.map(src -> src.getAssigned().getEmail(), TaskDto::setAssigned);
        });

        return modelMapper;
    }
}
