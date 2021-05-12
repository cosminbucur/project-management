package com.sda.project.management.service;

import com.sda.project.management.model.Project;
import com.sda.project.management.repository.ProjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        projectRepository.save(project);
    }

    public List<Project> findAll() {
        log.info("find projects");
        return projectRepository.findAll();
    }

    public Project findById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("project not found"));
    }

    public void update(Project project) {
        projectRepository.save(project);
    }

    public void delete(Long id) {
        log.info("delete project {}", id);
        projectRepository.deleteById(id);
    }

}
