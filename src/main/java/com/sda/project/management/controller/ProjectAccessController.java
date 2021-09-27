package com.sda.project.management.controller;

import com.sda.project.management.dto.ProjectAccessInfo;
import com.sda.project.management.service.ProjectAccessService;
import com.sda.project.management.service.ProjectService;
import com.sda.project.management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProjectAccessController {

    private final ProjectAccessService projectAccessService;
    private final ProjectService projectService;
    private final UserService userService;

    @Autowired
    public ProjectAccessController(ProjectAccessService projectAccessService,
                                   ProjectService projectService,
                                   UserService userService) {
        this.projectAccessService = projectAccessService;
        this.projectService = projectService;
        this.userService = userService;
    }

    @GetMapping("/projects/{projectId}/access")
    public String showProjectAccessPage(
            Model model,
            @PathVariable Long projectId) {
        model.addAttribute("project", projectService.findById(projectId));
        model.addAttribute("projectAccess", new ProjectAccessInfo());
        model.addAttribute("users", projectAccessService.getUnassignedUsers(projectId));
        return "project/project-access";
    }

    @PostMapping("/projects/{projectId}/access")
    public String addUserToProject(
            @PathVariable Long projectId,
            @ModelAttribute ProjectAccessInfo projectAccessInfo) {
        projectAccessService.addUserToProject(projectAccessInfo);
        return "redirect:/projects/" + projectId + "/backlog";
    }

}