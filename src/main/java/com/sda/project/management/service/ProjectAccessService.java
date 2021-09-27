package com.sda.project.management.service;

import com.sda.project.management.controller.exception.ResourceNotFoundException;
import com.sda.project.management.dto.ProjectAccessInfo;
import com.sda.project.management.model.Project;
import com.sda.project.management.model.ProjectAccess;
import com.sda.project.management.model.User;
import com.sda.project.management.repository.ProjectAccessRepository;
import com.sda.project.management.repository.ProjectRepository;
import com.sda.project.management.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProjectAccessService {

    private static final Logger log = LoggerFactory.getLogger(ProjectAccessService.class);

    private final ProjectAccessRepository projectAccessRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    @Autowired
    public ProjectAccessService(ProjectAccessRepository projectAccessRepository,
                                ProjectRepository projectRepository,
                                UserRepository userRepository) {
        this.projectAccessRepository = projectAccessRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    public void addUserToProject(ProjectAccessInfo projectAccessInfo) {
        Project project = projectRepository.findById(projectAccessInfo.getProjectId())
                .orElseThrow(() -> new ResourceNotFoundException("project not found"));

        if (projectAccessInfo.getUserId() != null) {
            User user = userRepository.findById(projectAccessInfo.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("user not found"));

            log.info("adding user {} to project {}", user.getEmail(), project.getName());
            ProjectAccess projectAccess = new ProjectAccess();
            projectAccess.setProject(project);
            projectAccess.setUser(user);
            projectAccess.setCreatedAt(LocalDate.now());
            projectAccessRepository.save(projectAccess);
        }
    }

    public ProjectAccess removeUserFromProject(Long userId, Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("project not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user not found"));
        project.setProjectLead(user);

        log.info("removing user {} from project {}", user.getEmail(), project.getName());
        ProjectAccess projectAccess = new ProjectAccess();
        projectAccess.setProject(null);
        projectAccess.setUser(null);
        projectAccess.setCreatedAt(LocalDate.now());
        return projectAccessRepository.save(projectAccess);
    }

    public List<User> getAssignedUsers(Long id) {
        return projectAccessRepository.getUsersInProject(id).stream()
                .map(projectAccess -> projectAccess.getUser())
                .collect(Collectors.toList());
    }

    public Set<User> getUnassignedUsers(Long id) {
        List<Long> assignedUsers = getUserIdsInProject(id);
        return userRepository.findAll().stream()
                .filter(user -> !assignedUsers.contains(user.getId()))
                .collect(Collectors.toSet());
    }

    private List<Long> getUserIdsInProject(Long id) {
        return projectAccessRepository.getUsersInProject(id).stream()
                .map(projectAccess -> projectAccess.getUser().getId())
                .collect(Collectors.toList());
    }
}
