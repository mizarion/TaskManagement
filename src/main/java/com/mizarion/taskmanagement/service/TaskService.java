package com.mizarion.taskmanagement.service;

import com.mizarion.taskmanagement.dto.tasks.CreateTaskRequestDto;
import com.mizarion.taskmanagement.dto.tasks.TaskDto;
import com.mizarion.taskmanagement.dto.tasks.UpdateTaskRequestDto;
import com.mizarion.taskmanagement.entity.UserEntity;
import com.mizarion.taskmanagement.exception.trowables.TaskNotFoundException;
import com.mizarion.taskmanagement.exception.trowables.WrongUserException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TaskService {

    TaskDto getTaskById(Long id);

    List<TaskDto> getAllTasks();

    /**
     * Retrieves a paginated list of tasks based on the provided creator and assigned user.
     * If either the creator or assigned parameters are null,
     * tasks will be fetched accordingly without filtering by that criterion.
     *
     * @param creator  the email of the user who created the tasks.
     *                 Can be {@code null} to fetch tasks by any creator.
     * @param assigned the email of the user to whom the tasks are assigned.
     *                Can be {@code null} to fetch tasks assigned to any user.
     * @param pageable the pagination and sorting information.
     *                 Must not be {@code null}.
     * @return a paginated {@link Page} of {@link TaskDto} objects representing the tasks that match the specified criteria.
     */
    Page<TaskDto> getAllTasks(String creator, String assigned, Pageable pageable);

    /**
     * Creates a new task based on the provided task details.
     * The task is created by the current authenticated user, who becomes the creator of the task.
     * Optionally, the task can be assigned to another user if specified.
     *
     * @param task the DTO containing the details of the task to be created. Must not be {@code null}.
     * @param user the currently authenticated user who is creating the task. Must not be {@code null}.
     * @return the newly created task as a {@link TaskDto}.
     */
    TaskDto createTask(CreateTaskRequestDto task, UserEntity user);

    /**
     * Updates the task with the given details.
     * The task can be updated either by the creator of the task or by the assigned user.
     * The creator can update all fields of the task, while the assigned user can only update the task's status.
     *
     * @param updateTask the DTO containing the updated task details. Must not be {@code null}.
     * @param user       the currently authenticated user making the update request. Must not be {@code null}.
     * @return the updated task as a {@link TaskDto}.
     * @throws TaskNotFoundException if the task with the given ID does not exist.
     * @throws WrongUserException    if the authenticated user is neither the creator nor the assigned user of the task.
     */
    TaskDto updateTask(UpdateTaskRequestDto updateTask, UserEntity user);

    void deleteTask(Long id, String currentUser);

}
