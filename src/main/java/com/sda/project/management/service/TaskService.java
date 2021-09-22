package com.sda.project.management.service;

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
        return taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("task not found"));
    }

    public List<Task> getTasks(Long sprintId) {
        return taskRepository.getTasks(sprintId);
    }

    public Task update(Long id, Task task) {
        return taskRepository.save(task);
    }

    public void delete(Long id) {
        log.info("delete task {}", id);
        taskRepository.deleteById(id);
    }
}
