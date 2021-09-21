package com.sda.project.management.controller;

import com.sda.project.management.controller.exception.ResourceAlreadyExistsException;
import com.sda.project.management.model.Task;
import com.sda.project.management.service.TaskService;
import com.sda.project.management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TaskController {

    private final TaskService taskService;
    private final UserService userService;

    @Autowired
    public TaskController(TaskService taskService, UserService userService, UserService userService1) {
        this.taskService = taskService;
        this.userService = userService1;
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
        return "task/task-add";
    }

    @PostMapping(value = "/tasks/add", params = "save")
    public String add(Model model, @ModelAttribute Task task) {
        try {
            taskService.save(task);
            return "redirect:/tasks";
        } catch (ResourceAlreadyExistsException e) {
            String errorMessage = e.getMessage();
            model.addAttribute("errorMessage", errorMessage);
            return "task/task-add";
        }
    }

    @PostMapping(value = "/tasks/add", params = "cancel")
    public String cancelAdd() {
        return "redirect:/tasks";
    }

    @GetMapping("/tasks/{id}/edit")
    public String showEditForm(Model model, @PathVariable("id") Long id) {
        model.addAttribute("task", taskService.findById(id));
        model.addAttribute("users", userService.findAll());
        return "task/task-edit";
    }

    @PostMapping(value = "/tasks/{id}/edit", params = "save")
    public String edit(
            Model model,
            @PathVariable("id") Long id,
            @ModelAttribute Task task) {
        taskService.update(task);
        return "redirect:/tasks";
    }

    @PostMapping(value = "/tasks/{id}/edit", params = "cancel")
    public String cancelEdit() {
        return "redirect:/tasks";
    }

    @GetMapping("/tasks/{id}/delete")
    public String delete(Model model, @PathVariable("id") Long id) {
        try {
            taskService.delete(id);
            return "redirect:/tasks";
        } catch (RuntimeException e) {
            String errorMessage = e.getMessage();
            model.addAttribute("errorMessage", errorMessage);
            return "task/tasks";
        }
    }
}