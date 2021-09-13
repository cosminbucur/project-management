package com.sda.project.management.service;

import com.sda.project.management.model.Project;
import com.sda.project.management.model.ProjectAccess;
import com.sda.project.management.model.User;
import com.sda.project.management.repository.ProjectAccessRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectAccessService {

    private static final Logger log = LoggerFactory.getLogger(ProjectAccessService.class);
    private final ProjectAccessRepository projectAccessRepository;

    @Autowired
    public ProjectAccessService(ProjectAccessRepository projectAccessRepository) {
        this.projectAccessRepository = projectAccessRepository;
    }

    public ProjectAccess addUserToProject(User user, Project project) {
        log.info("adding user {} to project {}", user.getEmail(), project.getName());
        ProjectAccess projectAccess = new ProjectAccess();
        projectAccess.setProject(project);
        projectAccess.setUser(user);
        projectAccess.setCreatedAt(LocalDate.now());
        return projectAccessRepository.save(projectAccess);
    }

    public List<User> getUsersInProject(Project project) {
        List<ProjectAccess> projectAccessList = projectAccessRepository.getUsersInProject(project.getId());
        return projectAccessList.stream()
                .map(projectAccess -> projectAccess.getUser())
                .collect(Collectors.toList());
    }

}
