package com.sda.project.management.service;

import com.sda.project.management.controller.exception.ResourceAlreadyExistsException;
import com.sda.project.management.controller.exception.ResourceNotFoundException;
import com.sda.project.management.model.Project;
import com.sda.project.management.model.Sprint;
import com.sda.project.management.model.User;
import com.sda.project.management.repository.ProjectRepository;
import com.sda.project.management.repository.SprintRepository;
import com.sda.project.management.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    private static final Logger log = LoggerFactory.getLogger(ProjectService.class);

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final SprintRepository sprintRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository,
                          UserRepository userRepository,
                          SprintRepository sprintRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.sprintRepository = sprintRepository;
    }

    public Project save(Project project) {
        log.info("save project {}", project);

        String name = project.getName();
        Optional<Project> existingProjectOptional = projectRepository.findByNameIgnoreCase(name);
        if (existingProjectOptional.isPresent()) {
            log.error("project with name {} already exists", name);
            throw new ResourceAlreadyExistsException("project with name " + name + " already exists");
        } else {
            return projectRepository.save(project);
        }
    }

    public List<Project> findAll() {
        log.info("find projects");

        return projectRepository.findAll();
    }

    public Project findById(Long id) {
        log.info("find project id {}", id);

        return projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("project not found"));
    }

    public void update(Project project) {
        log.info("update project {}", project);

        String name = project.getName();
        projectRepository.findByNameIgnoreCase(name)
                .filter(existingProject -> existingProject.getId().equals(project.getId()))
                .map(existingProject -> projectRepository.save(project))
                .orElseThrow(() -> {
                    log.error("project with name {} already exists", name);
                    throw new ResourceAlreadyExistsException("project with name " + name + " already exists");
                });
    }

    public void setProjectLead(Long projectId, Long userId) {
        log.info("save project lead {}", userId);

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("project not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user not found"));
        project.setProjectLead(user);
        projectRepository.save(project);
    }

    @Transactional
    public void addSprintToProject(Long projectId, Long sprintId) {
        Sprint sprint = sprintRepository.findById(sprintId)
                .orElseThrow(() -> new ResourceNotFoundException("sprint not found"));
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("project not found"));
        project.addSprint(sprint);
        projectRepository.save(project);
    }

    public void delete(Long id) {
        log.info("delete project {}", id);

        projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("project not found"));
        projectRepository.deleteById(id);
    }
}
