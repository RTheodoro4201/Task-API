package io.github.rtheodoro4201.task_api.specification;

import io.github.rtheodoro4201.task_api.entity.Task;
import io.github.rtheodoro4201.task_api.enums.TaskStatus;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class TaskSpecifications {
    private TaskSpecifications() {
    }

    public static Specification<Task> titleContains(String title) {
        return (root, query, criteriaBuilder) -> {
            if (title == null || title.isBlank()) {
                return null;
            }

            return criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + title.toLowerCase() + "%");
        };
    }

    public static Specification<Task> dueDateEquals(LocalDate dueDate) {
        return (root, query, criteriaBuilder) -> {
            if (dueDate == null) {
                return null;
            }

            return criteriaBuilder.equal(root.get("dueDate"), dueDate);
        };
    }

    public static Specification<Task> statusEquals(TaskStatus status) {
        return (root, query, criteriaBuilder) -> {
            if (status == null) {
                return null;
            }

            return criteriaBuilder.equal(root.get("status"), status);
        };
    }
}
