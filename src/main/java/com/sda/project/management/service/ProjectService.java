package com.sda.project.management.service;

import com.sda.project.management.controller.exception.ResourceAlreadyExistsException;
import com.sda.project.management.controller.exception.ResourceNotFoundException;
import com.sda.project.management.model.Project;
import com.sda.project.management.repository.ProjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    private static final Logger log = LoggerFactory.getLogger(ProjectService.class);
    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public void save(Project project) {
        log.info("save project {}", project);
        String name = project.getName();

        Optional<Project> projectOptional = projectRepository.findByName(name.toLowerCase());
        if (projectOptional.isPresent()) {
            throw new ResourceAlreadyExistsException("project with name " + name + " already exists");
        } else {
            projectRepository.save(project);
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
        findById(project.getId());
        projectRepository.save(project);
    }

    public void delete(Long id) {
        log.info("delete project {}", id);
        projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("project not found"));
        projectRepository.deleteById(id);
    }

    private boolean exists(Long id) {
        return projectRepository.existsById(id);
    }

}
