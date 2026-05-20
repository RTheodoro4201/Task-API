package io.github.rtheodoro4201.task_api.controller;


import io.github.rtheodoro4201.task_api.dto.CreateTaskDTO;
import io.github.rtheodoro4201.task_api.dto.TaskResponseDTO;
import io.github.rtheodoro4201.task_api.dto.UpdateTaskDTO;
import io.github.rtheodoro4201.task_api.enums.TaskStatus;
import io.github.rtheodoro4201.task_api.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<TaskResponseDTO> createTask(@RequestBody @Valid CreateTaskDTO taskDTO) {
        TaskResponseDTO createdTaskResponse = taskService.save(taskDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdTaskResponse.id())
                .toUri();

        return ResponseEntity.created(uri).body(createdTaskResponse);
    }

    @GetMapping
    public ResponseEntity<Page<TaskResponseDTO>> getAllTasks(@PageableDefault(size = 10) Pageable pageable) {
        Page<TaskResponseDTO> taskPageable = taskService.getAllTasks(pageable);
        return ResponseEntity.ok(taskPageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> getTaskById(@PathVariable Long id) {
        TaskResponseDTO taskResponseDTO = taskService.getTaskById(id);
        return ResponseEntity.ok(taskResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> updateTask (@Valid @RequestBody UpdateTaskDTO updateTaskDTO, @PathVariable Long id) {
        TaskResponseDTO taskResponseDTO = taskService.update(updateTaskDTO, id);
        return ResponseEntity.ok(taskResponseDTO);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TaskResponseDTO> updateTaskStatus(@PathVariable Long id, @RequestParam TaskStatus status) {
        return ResponseEntity.ok(taskService.updateStatus(id, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
