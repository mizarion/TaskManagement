package com.mizarion.taskmanagement.dto;


import com.mizarion.taskmanagement.TaskPriority;
import com.mizarion.taskmanagement.TaskStatus;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {

    private Long id;

    private String header;

    private String description;

    private TaskStatus status;

    private TaskPriority priority;

    private String creator;

    private String assigned;
}
