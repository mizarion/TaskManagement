package com.mizarion.taskmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;

    private String email;

    private String password;

    // date?

    // list of created tasks

    // list of assigned tasks

    // list of comments?
}
