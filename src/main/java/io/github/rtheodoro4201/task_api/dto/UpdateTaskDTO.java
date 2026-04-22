package io.github.rtheodoro4201.task_api.dto;

import io.github.rtheodoro4201.task_api.enums.TaskPriority;

import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record UpdateTaskDTO(
        @Size(max = 200) String title,
        String description,
        TaskPriority priority,
        LocalDate dueDate
) {}