package com.sda.project.management.controller;

import com.sda.project.management.dto.TaskEdit;
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

    private final ProjectService projectService;
    private final SprintService sprintService;
    private final TaskService taskService;
    private final UserService userService;

    @Autowired
    public TaskController(ProjectService projectService,
                          SprintService sprintService,
                          TaskService taskService,
                          UserService userService) {
        this.projectService = projectService;
        this.sprintService = sprintService;
        this.taskService = taskService;
        this.userService = userService;
    }

    @GetMapping("/tasks")
    public String showTasksPage(Model model) {
        model.addAttribute("tasks", taskService.findAll());
        return "task/tasks";
    }

    @GetMapping("/projects/{projectId}/tasks/add")
    public String showAddForm(Model model,
                              @PathVariable Long projectId) {
        model.addAttribute("task", new Task());
        model.addAttribute("selectedProjectId", projectId);
        model.addAttribute("project", projectService.findById(projectId));
        model.addAttribute("users", userService.findAll());
        model.addAttribute("projects", projectService.findAll());
        model.addAttribute("sprints", sprintService.getByProjectId(projectId));
        return "task/task-add";
    }

    @PostMapping("/projects/{projectId}/tasks/add")
    public String add(Model model,
                      @PathVariable Long projectId,
                      @ModelAttribute Task task) {
        try {
            taskService.save(task);
            return "redirect:/projects/" + projectId + "/backlog";
        } catch (RuntimeException e) {
            String errorMessage = e.getMessage();
            log.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);
            model.addAttribute("task", task);
            return "redirect:/projects/" + projectId + "/tasks/add";
        }
    }

    @GetMapping("/projects/{projectId}/tasks/{taskId}/edit")
    public String showEditForm(Model model,
                               @PathVariable Long projectId,
                               @PathVariable Long taskId) {
        model.addAttribute("task", taskService.findById(taskId));
        model.addAttribute("users", userService.findAll());
        model.addAttribute("projects", projectService.findAll());
        model.addAttribute("sprints", sprintService.getByProjectId(projectId));
        return "task/task-edit";
    }

    @PostMapping("/projects/{projectId}/tasks/{taskId}/edit")
    public String edit(
            Model model,
            @PathVariable Long projectId,
            @PathVariable Long taskId,
            @ModelAttribute TaskEdit taskData) {
        try {
            taskService.update(taskId, taskData);
            return "redirect:/projects/" + projectId + "/backlog";
        } catch (RuntimeException e) {
            String errorMessage = e.getMessage();
            log.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);
            return "redirect:/projects/" + projectId + "/tasks/" + taskId + "/edit";
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

    @GetMapping("/projects/{projectId}/tasks/{taskId}/delete")
    public String delete(Model model,
                         @PathVariable Long projectId,
                         @PathVariable Long taskId) {
        try {
            taskService.delete(taskId);
            return "redirect:/projects/" + projectId + "/backlog";
        } catch (RuntimeException e) {
            String errorMessage = e.getMessage();
            log.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);
            return "task/tasks";
        }
    }
}