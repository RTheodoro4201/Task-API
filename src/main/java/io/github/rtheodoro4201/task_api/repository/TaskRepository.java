package io.github.rtheodoro4201.task_api.repository;

import io.github.rtheodoro4201.task_api.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;

public interface TaskRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {
    boolean existsByTitleAndDueDate(String title, LocalDate dueDate);
}
