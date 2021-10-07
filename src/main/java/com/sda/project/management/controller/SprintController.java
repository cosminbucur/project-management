package com.sda.project.management.controller;

import com.sda.project.management.dto.SprintUpdate;
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

import javax.validation.Valid;

@Controller
public class SprintController {

    private static final Logger log = LoggerFactory.getLogger(SprintController.class);

    private final SprintService sprintService;

    @Autowired
    public SprintController(SprintService sprintService) {
        this.sprintService = sprintService;
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

    @GetMapping("/projects/{projectId}/sprints/{sprintId}/edit")
    public String showEditForm(Model model,
                               @PathVariable Long projectId,
                               @PathVariable Long sprintId) {
        Sprint sprint = sprintService.findById(sprintId);
        model.addAttribute("projectId", projectId);
        model.addAttribute("sprint", sprint);
        return "sprint/sprint-edit";
    }

    @PostMapping("/projects/{projectId}/sprints/{sprintId}/edit")
    public String edit(Model model,
                       @PathVariable Long projectId,
                       @PathVariable Long sprintId,
                       @Valid @ModelAttribute SprintUpdate sprintData) {
        try {
            sprintService.update(sprintId, sprintData);
            return "redirect:/projects/{projectId}/backlog";
        } catch (RuntimeException e) {
            String errorMessage = e.getMessage();
            log.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);
            return "redirect:/projects/" + projectId + "/sprints/" + sprintId + "/edit";
        }
    }

    @GetMapping("/projects/{projectId}/sprints/{sprintId}/start")
    public String start(@PathVariable Long projectId,
                        @PathVariable Long sprintId) {
        sprintService.start(sprintId);
        return "redirect:/projects/" + projectId + "/backlog";
    }

    @GetMapping("/projects/{projectId}/sprints/{sprintId}/complete")
    public String complete(@PathVariable Long projectId,
                           @PathVariable Long sprintId) {
        sprintService.complete(sprintId);
        return "redirect:/projects/" + projectId + "/backlog";
    }

    @GetMapping("/projects/{projectId}/sprints/{sprintId}/delete")
    public String delete(Model model,
                         @PathVariable Long projectId,
                         @PathVariable Long sprintId) {
        try {
            sprintService.delete(sprintId);
            return "redirect:/projects/{projectId}/backlog";
        } catch (RuntimeException e) {
            String errorMessage = e.getMessage();
            log.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);
            return "redirect:/projects/" + projectId + "/sprints/" + sprintId + "/delete";
        }
    }
}
