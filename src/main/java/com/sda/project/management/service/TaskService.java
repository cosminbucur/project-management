package com.sda.project.management.service;

import com.sda.project.management.controller.exception.ResourceNotFoundException;
import com.sda.project.management.model.Task;
import com.sda.project.management.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private static final Logger log = LoggerFactory.getLogger(TaskService.class);

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task save(Task task) {
        log.info("save task {}", task);

        return taskRepository.save(task);
    }

    public List<Task> findAll() {
        log.info("find tasks");

        return taskRepository.findAll();
    }

    public Task findById(Long id) {
        log.info("find task id {}", id);

        return taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("task not found"));
    }

    public List<Task> getTasksInSprint(Long sprintId) {
        log.info("get tasks in sprint {}", sprintId);

        return taskRepository.getTasksInSprint(sprintId);
    }

    public Task update(Task task) {
        log.info("update task {}", task);

        return taskRepository.save(task);
    }

    public void delete(Long id) {
        log.info("delete task {}", id);

        taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("task not found"));
        taskRepository.deleteById(id);
    }

    public List<Task> findProjectUnassignedTasks(Long id) {
        return taskRepository.findProjectUnassignedTasks(id);
    }
}
