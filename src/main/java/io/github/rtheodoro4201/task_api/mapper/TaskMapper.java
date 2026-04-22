package io.github.rtheodoro4201.task_api.mapper;

import io.github.rtheodoro4201.task_api.dto.CreateTaskDTO;
import io.github.rtheodoro4201.task_api.dto.TaskResponseDTO;
import io.github.rtheodoro4201.task_api.dto.UpdateTaskDTO;
import io.github.rtheodoro4201.task_api.entity.Task;
import io.github.rtheodoro4201.task_api.enums.TaskPriority;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    public Task toEntity(CreateTaskDTO dto) {
        Task task = new Task();
        task.setTitle(dto.title());
        task.setDescription(dto.description());
        task.setPriority(dto.priority() != null ? dto.priority() : TaskPriority.MEDIUM);
        task.setDueDate(dto.dueDate());
        return task;
    }

    public void updateEntity(UpdateTaskDTO dto, Task task) {
        if (dto.title() != null && !dto.title().isBlank()) {
            task.setTitle(dto.title());
        }
        if (dto.description() != null) {
            task.setDescription(dto.description());
        }
        if (dto.priority() != null) {
            task.setPriority(dto.priority());
        }
        if (dto.dueDate() != null) {
            task.setDueDate(dto.dueDate());
        }
    }

    public TaskResponseDTO toResponse(Task task) {
        return new TaskResponseDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getPriority(),
                task.getDueDate(),
                task.getCreatedAt(),
                task.getUpdatedAt()
        );
    }
}