package com.sda.project.management.controller;

import com.sda.project.management.controller.exception.ResourceAlreadyExistsException;
import com.sda.project.management.model.Project;
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
public class ProjectController {

    private final ProjectService projectService;
    private final UserService userService;

    @Autowired
    public ProjectController(ProjectService projectService, UserService userService) {
        this.projectService = projectService;
        this.userService = userService;
    }

    @GetMapping("/projects")
    public String showProjectsPage(Model model) {
        model.addAttribute("projects", projectService.findAll());
        return "project/projects";
    }

    @GetMapping("/projects/add")
    public String showAddForm(Model model) {
        model.addAttribute("project", new Project());
        model.addAttribute("users", userService.findAll());
        return "project/project-add";
    }

    @PostMapping(value = "/projects/add", params = "save")
    public String add(Model model, @ModelAttribute Project project) {
        try {
            projectService.save(project);
            return "redirect:/projects";
        } catch (ResourceAlreadyExistsException e) {
            String errorMessage = e.getMessage();
            model.addAttribute("errorMessage", errorMessage);
            return "project/project-add";
        }
    }

    @PostMapping(value = "/projects/add", params = "cancel")
    public String cancelAdd() {
        return "redirect:/projects";
    }

    @GetMapping("/projects/{id}/edit")
    public String showEditForm(Model model, @PathVariable("id") Long id) {
        model.addAttribute("project", projectService.findById(id));
        model.addAttribute("users", userService.findAll());
        return "project/project-edit";
    }

    @PostMapping(value = "/projects/{id}/edit", params = "save")
    public String edit(
            Model model,
            @PathVariable("id") Long id,
            @ModelAttribute Project project) {
        projectService.update(project);
        return "redirect:/projects";
    }

    @PostMapping(value = "/projects/{id}/edit", params = "cancel")
    public String cancelEdit() {
        return "redirect:/projects";
    }

    @GetMapping("/projects/{id}/delete")
    public String delete(Model model, @PathVariable("id") Long id) {
        try {
            projectService.delete(id);
            return "redirect:/projects";
        } catch (RuntimeException e) {
            String errorMessage = e.getMessage();
            model.addAttribute("errorMessage", errorMessage);
            return "project/projects";
        }
    }
}