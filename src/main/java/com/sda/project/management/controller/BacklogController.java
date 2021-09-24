package com.sda.project.management.controller;

import com.sda.project.management.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BacklogController {

    private static final Logger log = LoggerFactory.getLogger(BacklogController.class);

    private final TaskService taskService;

    @Autowired
    public BacklogController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/backlog")
    public String showBacklogPage(Model model) {
        model.addAttribute("tasks", taskService.findAll());
        return "project/backlog";
    }
}