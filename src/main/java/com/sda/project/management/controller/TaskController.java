package com.sda.project.management.controller;

import com.sda.project.management.model.Task;
import com.sda.project.management.service.ProjectService;
import com.sda.project.management.service.SprintService;
import com.sda.project.management.service.TaskService;
import com.sda.project.management.service.UserService;
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
public class TaskController {

    private static final Logger log = LoggerFactory.getLogger(TaskController.class);

    private final TaskService taskService;
    private final UserService userService;
    private final ProjectService projectService;
    private final SprintService sprintService;

    @Autowired
    public TaskController(TaskService taskService,
                          UserService userService,
                          ProjectService projectService,
                          SprintService sprintService) {
        this.taskService = taskService;
        this.userService = userService;
        this.projectService = projectService;
        this.sprintService = sprintService;
    }

    @GetMapping("/tasks")
    public String showTasksPage(Model model) {
        model.addAttribute("tasks", taskService.findAll());
        return "task/tasks";
    }

    @GetMapping("/tasks/add")
    public String showAddForm(Model model) {
        model.addAttribute("task", new Task());
        model.addAttribute("users", userService.findAll());
        model.addAttribute("projects", projectService.findAll());
        model.addAttribute("sprints", sprintService.findAll());
        return "task/task-add";
    }

    @PostMapping("/tasks/add")
    public String add(Model model, @ModelAttribute Task task) {
        try {
            taskService.save(task);
            return "redirect:/backlog";
        } catch (RuntimeException e) {
            String errorMessage = e.getMessage();
            log.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);
            return "task/task-add";
        }
    }

    @GetMapping("/tasks/{id}/edit")
    public String showEditForm(Model model, @PathVariable Long id) {
        model.addAttribute("task", taskService.findById(id));
        model.addAttribute("users", userService.findAll());
        model.addAttribute("projects", projectService.findAll());
        model.addAttribute("sprints", sprintService.findAll());
        return "task/task-edit";
    }

    @PostMapping("/tasks/{id}/edit")
    public String edit(
            Model model,
            @PathVariable Long id,
            @ModelAttribute Task task) {
        try {
            taskService.update(task);
            return "redirect:/backlog";
        } catch (RuntimeException e) {
            String errorMessage = e.getMessage();
            log.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);
            return "redirect:/tasks/" + id + "/edit";
        }
    }

    @GetMapping("/projects/{projectId}/sprints/{sprintId}/tasks/{taskId}/delete")
    public String removeTaskFromSprint(Model model,
                                       @PathVariable Long projectId,
                                       @PathVariable Long sprintId,
                                       @PathVariable Long taskId) {
        try {
            taskService.removeTaskFromSprint(sprintId, taskId);
            return "redirect:/projects/" + projectId + "/backlog";
        } catch (RuntimeException e) {
            String errorMessage = e.getMessage();
            log.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);
            return "redirect:/projects/" + projectId + "/backlog";
        }
    }

    @GetMapping("/tasks/{id}/delete")
    public String delete(Model model, @PathVariable Long id) {
        try {
            taskService.delete(id);
            return "redirect:/tasks";
        } catch (RuntimeException e) {
            String errorMessage = e.getMessage();
            log.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);
            return "task/tasks";
        }
    }
}