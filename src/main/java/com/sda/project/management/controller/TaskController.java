package com.sda.project.management.controller;

import com.sda.project.management.model.Task;
import com.sda.project.management.model.User;
import com.sda.project.management.service.TaskService;
import com.sda.project.management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TaskController {

    private final TaskService taskService;
    private final UserService userService;

    @Autowired
    public TaskController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    @GetMapping("tasks")
    public String showTasksPage(Model model) {
        model.addAttribute("tasks", taskService.findAll());
        return "task/tasks";
    }

    @GetMapping("/add-task")
    public String showAddForm(Model model) {
        model.addAttribute("task", new Task());
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "task/add-task";
    }

    @PostMapping("/task/add")
    public String addTask(@ModelAttribute Task task) {
        taskService.save(task);
        return "redirect:/tasks";
    }

    @GetMapping("/edit-task/{id}")
    public String showEditForm(Model model, @PathVariable("id") Long id) {
        Task task = taskService.findById(id);
        model.addAttribute("task", task);
        return "task/edit-task";
    }

    @PostMapping("/task/edit")
    public String editTask(@ModelAttribute Task task) {
        taskService.update(task);
        return "redirect:/tasks";
    }

    @GetMapping("/delete-task/{id}")
    public String deleteTask(@PathVariable("id") Long id) {
        taskService.delete(id);
        return "redirect:/tasks";
    }
}