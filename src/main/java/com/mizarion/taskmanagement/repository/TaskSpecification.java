package com.mizarion.taskmanagement.repository;

import com.mizarion.taskmanagement.entity.TaskEntity;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class TaskSpecification {

    public static Specification<TaskEntity> byCreatorAndAssigned(String creatorEmail, String assignedEmail) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (creatorEmail != null) {
                predicates.add(criteriaBuilder.equal(root.get("creator").get("email"), creatorEmail));
            }

            if (assignedEmail != null) {
                predicates.add(criteriaBuilder.equal(root.get("assigned").get("email"), assignedEmail));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
