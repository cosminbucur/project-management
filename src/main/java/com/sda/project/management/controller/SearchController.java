package com.sda.project.management.controller;

import com.sda.project.management.dto.TaskInfo;
import com.sda.project.management.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SearchController {

    private final TaskService taskService;

    @Autowired
    public SearchController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/search")
    public List<TaskInfo> searchTask(@RequestParam(value = "q", required = false) String query) {
        return taskService.search(query);
    }

}
