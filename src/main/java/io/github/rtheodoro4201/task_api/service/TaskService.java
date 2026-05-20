package io.github.rtheodoro4201.task_api.service;

import io.github.rtheodoro4201.task_api.dto.CreateTaskDTO;
import io.github.rtheodoro4201.task_api.dto.TaskResponseDTO;
import io.github.rtheodoro4201.task_api.dto.UpdateTaskDTO;
import io.github.rtheodoro4201.task_api.entity.Task;
import io.github.rtheodoro4201.task_api.enums.TaskStatus;
import io.github.rtheodoro4201.task_api.exception.TaskAlreadyExistsException;
import io.github.rtheodoro4201.task_api.exception.TaskNotFoundException;
import io.github.rtheodoro4201.task_api.mapper.TaskMapper;
import io.github.rtheodoro4201.task_api.repository.TaskRepository;
import io.github.rtheodoro4201.task_api.utils.ErrorMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);

    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    @Transactional
    public TaskResponseDTO save(CreateTaskDTO taskDTO) {
        if (taskRepository.existsByTitleAndDueDate(taskDTO.title(), taskDTO.dueDate())) {
            throw new TaskAlreadyExistsException(ErrorMessages.TASK_ALREADY_EXISTS_MESSAGE);
        }
        Task mappedTask = taskMapper.toEntity(taskDTO);

        Task savedTask = taskRepository.save(mappedTask);
        logger.info("Tarefa criada: {}", savedTask);

        return taskMapper.toResponse(savedTask);
    }

    @Transactional(readOnly = true)
    public Page<TaskResponseDTO> getAllTasks(Pageable pageable) {
        return taskRepository.findAll(pageable)
                .map(taskMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public TaskResponseDTO getTaskById(Long id) {
        return taskMapper.toResponse(taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(ErrorMessages.TASK_NOT_FOUND_MESSAGE)));
    }

    @Transactional
    public TaskResponseDTO update(UpdateTaskDTO taskDTO, Long id) {
        Task updatedTask = taskMapper.updateEntity(taskDTO, taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(ErrorMessages.TASK_NOT_FOUND_MESSAGE)));

        Task savedTask = taskRepository.save(updatedTask);
        logger.info("Tarefa atualizada: {}", savedTask);

        return taskMapper.toResponse(savedTask);
    }

    @Transactional
    public TaskResponseDTO updateStatus(Long id, TaskStatus status) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(ErrorMessages.TASK_NOT_FOUND_MESSAGE));

        task.setStatus(status);

        Task updatedTask = taskRepository.save(task);
        logger.info("Status da tarefa atualizado: {}", updatedTask);

        return taskMapper.toResponse(updatedTask);
    }

    @Transactional
    public void delete(Long id) {
        Task deletedTask = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(ErrorMessages.TASK_NOT_FOUND_MESSAGE));

        taskRepository.delete(deletedTask);
        logger.info("Tarefa deletada: {}", deletedTask);
    }
}
