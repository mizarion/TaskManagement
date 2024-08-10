package com.mizarion.taskmanagement.repository;

import com.mizarion.taskmanagement.entity.TaskEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long>, JpaSpecificationExecutor<TaskEntity> {

    @Query("SELECT t FROM TaskEntity AS t " +
           "WHERE (:creator IS NULL OR t.creator.email = :creator) " +
           "AND (:assigned IS NULL  OR t.assigned.email = :assigned)")
    Page<TaskEntity> findByCreatorAndAssigned(@Param("creator") String creator,
                                              @Param("assigned") String assigned,
                                              Pageable pageable);

}
