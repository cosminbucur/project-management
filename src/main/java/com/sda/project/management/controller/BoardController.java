package com.sda.project.management.controller;

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
public class BoardController {

    private static final Logger log = LoggerFactory.getLogger(BoardController.class);

    private final SprintService sprintService;
    private final ProjectService projectService;
    private final TaskService taskService;

    @Autowired
    public BoardController(ProjectService projectService,
                           SprintService sprintService,
                           TaskService taskService) {
        this.projectService = projectService;
        this.sprintService = sprintService;
        this.taskService = taskService;
    }

    @GetMapping("/projects/{id}/board")
    public String showBoardPage(Model model,
                                @PathVariable Long id) {
        model.addAttribute("project", projectService.findById(id));
        model.addAttribute("tasks", taskService.findAll());
        // TODO: no hardcode
        model.addAttribute("currentSprint", sprintService.findAll().get(0));
        return "project/board";
    }
}