package com.mizarion.taskmanagement.repository;

import com.mizarion.taskmanagement.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long>  {

}
