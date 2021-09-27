package com.sda.project.management.controller;

import com.sda.project.management.service.ProjectAccessService;
import com.sda.project.management.service.ProjectService;
import com.sda.project.management.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class BacklogController {

    private static final Logger log = LoggerFactory.getLogger(BacklogController.class);

    private final TaskService taskService;
    private final ProjectService projectService;
    private final ProjectAccessService projectAccessService;

    @Autowired
    public BacklogController(TaskService taskService,
                             ProjectService projectService,
                             ProjectAccessService projectAccessService) {
        this.taskService = taskService;
        this.projectService = projectService;
        this.projectAccessService = projectAccessService;
    }

    @GetMapping("/projects/{id}/backlog")
    public String showBacklogPage(Model model,
                                  @PathVariable Long id) {
        model.addAttribute("tasks", taskService.findAll());
        model.addAttribute("project", projectService.findById(id));
        model.addAttribute("users", projectAccessService.getAssignedUsers(id));
        return "project/backlog";
    }
}