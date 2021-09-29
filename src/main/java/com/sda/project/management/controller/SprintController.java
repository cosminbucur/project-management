package com.sda.project.management.controller;

import com.sda.project.management.model.Sprint;
import com.sda.project.management.service.SprintService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SprintController {

    private static final Logger log = LoggerFactory.getLogger(SprintController.class);

    private final SprintService sprintService;

    @Autowired
    public SprintController(SprintService sprintService) {
        this.sprintService = sprintService;
    }

    @GetMapping("/sprints")
    public String showSprintsPage(Model model) {
        model.addAttribute("sprints", sprintService.findAll());
        return "sprint/sprints";
    }

    @PostMapping("/projects/{projectId}/sprints/add")
    public String add(Model model,
                      @PathVariable Long projectId) {
        try {
            sprintService.save(projectId);
            return "redirect:/projects/" + projectId + "/backlog";
        } catch (RuntimeException e) {
            String errorMessage = e.getMessage();
            log.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);
            return "redirect:/projects/" + projectId + "/backlog";
        }
    }

    @GetMapping("/sprints/{id}/edit")
    public String showEditForm(Model model, @PathVariable Long id) {
        Sprint sprint = sprintService.findById(id);
        model.addAttribute("sprint", sprint);
        return "sprint/sprint-edit";
    }

    @PostMapping("/sprints/{id}/edit")
    public String edit(@ModelAttribute Sprint sprint) {
        sprintService.update(sprint);
        return "redirect:/sprints";
    }

    @GetMapping("/sprints/{id}/delete")
    public String delete(@PathVariable Long id) {
        sprintService.delete(id);
        return "redirect:/sprints";
    }
}
