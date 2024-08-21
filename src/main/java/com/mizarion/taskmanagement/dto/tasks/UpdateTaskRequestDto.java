package com.mizarion.taskmanagement.dto.tasks;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mizarion.taskmanagement.entity.TaskPriority;
import com.mizarion.taskmanagement.entity.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTaskRequestDto {

    @JsonIgnore
    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    @Schema(description = "Header of the task", example = "Hot fix")
    private String header;

    @Size(max = 500)
    @Schema(description = "Detailed description of the task", example = "Investigate and fix the issue.")
    private String description;

    @NotNull
    @Schema(description = "Current status of the task", example = "IN_PROGRESS")
    private TaskStatus status;

    @NotNull
    @Schema(description = "Priority level of the task", example = "HIGH")
    private TaskPriority priority;

    @Size(max = 50)
    @Schema(description = "Assigned person for the task", example = "assigned@exaple.com")
    private String assigned;
}
