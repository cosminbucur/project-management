package com.sda.project.management.service;

import com.sda.project.management.controller.exception.ResourceNotFoundException;
import com.sda.project.management.model.Project;
import com.sda.project.management.model.Sprint;
import com.sda.project.management.model.Task;
import com.sda.project.management.repository.ProjectRepository;
import com.sda.project.management.repository.SprintRepository;
import com.sda.project.management.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SprintService {

    private static final Logger log = LoggerFactory.getLogger(SprintService.class);

    private final SprintRepository sprintRepository;
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;

    @Autowired
    public SprintService(SprintRepository sprintRepository, TaskRepository taskRepository, ProjectRepository projectRepository) {
        this.sprintRepository = sprintRepository;
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
    }

    public Sprint save(Long projectId, Sprint sprint) {
        log.info("save sprint {}", sprint);
        long count = sprintRepository.count() + 1;
        sprint.setName("Sprint " + count);
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("project was not found"));
        project.addSprint(sprint);
        projectRepository.save(project);
        return sprint;
    }

    public List<Sprint> findAll() {
        log.info("find sprints");

        return sprintRepository.findAll();
    }

    public Sprint findById(Long id) {
        log.info("find sprint {}", id);

        return sprintRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("sprint not found"));
    }

    public void update(Sprint sprint) {
        log.info("update sprint {}", sprint);

        sprintRepository.save(sprint);
    }

    public void addTaskToSprint(Long sprintId, Long taskId) {
        log.info("add task {} to sprint {}", taskId, sprintId);

        Sprint sprint = sprintRepository.findById(sprintId)
                .orElseThrow(() -> new ResourceNotFoundException("sprint not found"));
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("task not found"));
        sprint.addTask(task);
        sprintRepository.save(sprint);
    }

    public void delete(Long id) {
        log.info("delete sprint {}", id);

        sprintRepository.deleteById(id);
    }
}
