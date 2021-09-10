package com.sda.project.management.controller;

import com.sda.project.management.controller.exception.ResourceAlreadyExistsException;
import com.sda.project.management.model.Project;
import com.sda.project.management.model.User;
import com.sda.project.management.service.TaskService;
import com.sda.project.management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final UserService userService;
    private final TaskService taskService;

    @Autowired
    public UserController(UserService userService, TaskService taskService) {
        this.userService = userService;
        this.taskService = taskService;
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @GetMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("user", new User());
        return "user/register";
    }

    @GetMapping("users")
    public String showUsersPage(Model model) {
        model.addAttribute("users", userService.findAll());
        return "user/users";
    }

    @PostMapping("register/add")
    public String register(@ModelAttribute User user) {
        userService.saveCustomer(user);
        return "redirect:/login";
    }

    @PostMapping(value = "/register/add", params = "save")
    public String add(Model model, @ModelAttribute User user) {
        try {
            userService.save(user);
            return "redirect:/";
        } catch (ResourceAlreadyExistsException e) {
            String errorMessage = e.getMessage();
            model.addAttribute("errorMessage", errorMessage);
            return "user/register";
        }
    }

    @PostMapping(value = "/register/add", params = "cancel")
    public String cancelRegister() {
        return "redirect:/";
    }

    @PostMapping(path = "user/add")
    public String addUser(@ModelAttribute User user) {
        userService.save(user);
        return "redirect:/users";
    }

    @GetMapping(path = "edit-user/{id}")
    public String showEditForm(Model model, @PathVariable("id") Long id) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "user/edit-user";
    }

    @PostMapping(path = "user/edit")
    public String editUser(@ModelAttribute User user) {
        userService.update(user);
        return "redirect:/users";
    }

    @GetMapping(path = "delete-user/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/users";
    }
}