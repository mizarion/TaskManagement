package com.mizarion.taskmanagement.config;

import com.mizarion.taskmanagement.dto.CommentDto;
import com.mizarion.taskmanagement.entity.CommentEntity;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.typeMap(CommentEntity.class, CommentDto.class).addMappings(mapper -> {
            mapper.map(src -> src.getAuthor().getEmail(), CommentDto::setAuthorEmail);
            mapper.map(src -> src.getTask().getId(), CommentDto::setTaskId);
        });

        return modelMapper;
    }
}
