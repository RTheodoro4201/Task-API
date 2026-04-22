package io.github.rtheodoro4201.task_api.dto;

import io.github.rtheodoro4201.task_api.enums.TaskPriority;
import io.github.rtheodoro4201.task_api.enums.TaskStatus;

import java.time.Instant;
import java.time.LocalDate;

public record TaskResponseDTO(
        Long id,
        String title,
        String description,
        TaskStatus status,
        TaskPriority priority,
        LocalDate dueDate,
        Instant createdAt,
        Instant updatedAt
){}