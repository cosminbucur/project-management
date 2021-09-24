package com.sda.project.management.controller;

import com.sda.project.management.model.Sprint;
import com.sda.project.management.service.SprintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SprintController {

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

    @GetMapping("/sprints/add")
    public String showAddForm(Model model) {
        model.addAttribute("sprint", new Sprint());
        return "sprint/sprint-add";
    }

    @PostMapping(value = "/sprints/add", params = "save")
    public String add(@ModelAttribute Sprint sprint){
        sprintService.save(sprint);
        return "redirect:/sprints";
    }

    @PostMapping(value = "/sprints/add", params = "cancel")
    public String cancelAdd() {
        return "redirect:/sprints";
    }

    @GetMapping("/sprints/{id}/edit")
    public String showEditForm(Model model, @PathVariable Long id) {
        Sprint sprint = sprintService.findById(id);
        model.addAttribute("sprint", sprint);
        return "sprint/sprint-edit";
    }

    @PostMapping(value = "/sprints/{id}/edit", params = "save")
    public String edit(@ModelAttribute Sprint sprint){
        sprintService.update(sprint);
        return "redirect:/sprints";
    }

    @PostMapping(value = "/sprints/{id}/edit", params = "cancel")
    public String cancelEdit() {
        return "redirect:/sprints";
    }

    @GetMapping("/sprints/{id}/delete")
    public String delete(@PathVariable Long id) {
        sprintService.delete(id);
        return "redirect:/sprints";
    }
}
