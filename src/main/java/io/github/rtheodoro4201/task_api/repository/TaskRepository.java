package io.github.rtheodoro4201.task_api.repository;

import io.github.rtheodoro4201.task_api.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface TaskRepository extends JpaRepository<Task, Long>{
    boolean existsByTitleAndDueDate(String title, LocalDate dueDate);
}
