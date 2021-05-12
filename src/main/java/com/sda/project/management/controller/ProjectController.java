package com.sda.project.management.controller;

import com.sda.project.management.model.Project;
import com.sda.project.management.model.User;
import com.sda.project.management.service.ProjectService;
import com.sda.project.management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ProjectController {

    private final ProjectService projectService;
    private final UserService userService;

    @Autowired
    public ProjectController(ProjectService projectService, UserService userService) {
        this.projectService = projectService;
        this.userService = userService;
    }

    @GetMapping("projects")
    public String showProjectsPage(Model model) {
        List<Project> foundProjects = projectService.findAll();
        model.addAttribute("projects", foundProjects);
        return "project/projects";
    }

    @GetMapping("add-project")
    public String showAddForm(Model model) {
        model.addAttribute("project", new Project());
        List<User> foundUsers = userService.findAll();
        model.addAttribute("users", foundUsers);
        return "project/add-project";
    }

    @PostMapping("projects/add")
    public String addProject(@ModelAttribute Project project) {
        projectService.save(project);
        return "redirect:/projects";
    }

    @GetMapping("projects/{id}")
    public String showEditForm(Model model, @PathVariable("id") Long id) {
        Project project = projectService.findById(id);
        model.addAttribute("projectToBeEdit", project);
        List<User> userEntities = userService.findAll();
        model.addAttribute("user", userEntities);
        return "project/edit-project";
    }

    @PostMapping("project/edit")
    public String editProject(@ModelAttribute Project project) {
        projectService.update(project);
        return "redirect:/projects";
    }

    @GetMapping("delete-project/{id}")
    public String delete(@PathVariable("id") Long id) {
        projectService.delete(id);
        return "redirect:/projects";
    }
}