package com.sda.project.management.service;

import com.sda.project.management.controller.exception.ResourceNotFoundException;
import com.sda.project.management.dto.SprintUpdate;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

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

    @Transactional
    public Sprint save(Long projectId) {
        long nextSprintNumber = sprintRepository.count() + 1;
        String sprintName = "Sprint " + nextSprintNumber;
        Sprint sprint = new Sprint(sprintName);
        log.info("save sprint {}", sprint);
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("project was not found"));
        project.addSprint(sprint);
        return sprintRepository.findByName(sprintName);
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

    public void update(Long sprintId, SprintUpdate sprintData) {
        log.info("update sprint {} with data {}", sprintId, sprintData);

        sprintRepository.findById(sprintId)
                .map(existingSprint -> updateEntity(sprintData, existingSprint))
                .map(updatedSprint -> sprintRepository.save(updatedSprint))
                .orElseThrow(() -> new ResourceNotFoundException("sprint not found"));
    }

    // TODO: extract in mapper
    private Sprint updateEntity(SprintUpdate sprintData, Sprint existingSprint) {
        existingSprint.setName(sprintData.getName());
        existingSprint.setDateFrom(sprintData.getDateFrom());
        existingSprint.setDateTo(sprintData.getDateTo());
        existingSprint.setSprintGoal(sprintData.getSprintGoal());
        return existingSprint;
    }

    @Transactional
    public void addTaskToSprint(Long sprintId, Long taskId) {
        log.info("add task {} to sprint {}", taskId, sprintId);

        Sprint sprint = sprintRepository.findById(sprintId)
                .orElseThrow(() -> new ResourceNotFoundException("sprint not found"));
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("task not found"));
        sprint.addTask(task);
        sprintRepository.save(sprint);
    }

    @Transactional
    public void delete(Long id) {
        log.info("delete sprint {}", id);

        Sprint sprint = sprintRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("sprint not found"));

        Set<Task> tasks = sprint.getTasks();
        sprint.removeTasksFromSprint(tasks);

        sprintRepository.deleteById(id);
    }
}
