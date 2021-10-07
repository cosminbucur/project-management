package com.sda.project.management.controller;

import com.sda.project.management.service.ProjectAccessService;
import com.sda.project.management.service.ProjectService;
import com.sda.project.management.service.SprintService;
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

    private final ProjectService projectService;
    private final SprintService sprintService;
    private final TaskService taskService;
    private final ProjectAccessService projectAccessService;

    @Autowired
    public BacklogController(ProjectService projectService,
                             SprintService sprintService,
                             TaskService taskService,
                             ProjectAccessService projectAccessService) {
        this.projectService = projectService;
        this.sprintService = sprintService;
        this.taskService = taskService;
        this.projectAccessService = projectAccessService;
    }

    @GetMapping("/projects/{id}/backlog")
    public String showBacklogPage(Model model,
                                  @PathVariable Long id) {
        model.addAttribute("tasks", taskService.findAll());
        model.addAttribute("project", projectService.findById(id));
        model.addAttribute("users", projectAccessService.getAssignedUsers(id));
        model.addAttribute("sprints", sprintService.getByProjectId(id));
        model.addAttribute("activeSprint", sprintService.getActiveSprintByProjectId(id));
        model.addAttribute("backlogTasks", taskService.getUnassignedTasksByProjectId(id));
        return "project/backlog";
    }
}