package com.sda.project.management.service;

import com.sda.project.management.controller.exception.ResourceNotFoundException;
import com.sda.project.management.model.Sprint;
import com.sda.project.management.model.Task;
import com.sda.project.management.repository.SprintRepository;
import com.sda.project.management.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TaskService {

    private static final Logger log = LoggerFactory.getLogger(TaskService.class);

    private final TaskRepository taskRepository;
    private final SprintRepository sprintRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, SprintRepository sprintRepository) {
        this.taskRepository = taskRepository;
        this.sprintRepository = sprintRepository;
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

    @Transactional
    public void removeTaskFromSprint(Long sprintId, Long taskId) {
        log.info("removed task {} from sprint {}", taskId, sprintId);

        Sprint sprintInDb = sprintRepository.findById(sprintId)
                .orElseThrow(() -> new ResourceNotFoundException("sprint not found"));
        sprintInDb.getTasks().remove(findById(taskId));
    }

}
