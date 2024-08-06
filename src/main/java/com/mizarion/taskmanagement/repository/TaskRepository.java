package com.mizarion.taskmanagement.repository;

import com.mizarion.taskmanagement.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

    List<TaskEntity> getTaskEntitiesByCreator(String creator);

    List<TaskEntity> getTaskEntitiesByAssigned(String assigned);
}
