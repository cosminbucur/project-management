package com.sda.project.management;

import com.sda.project.management.model.Project;
import com.sda.project.management.model.Sprint;
import com.sda.project.management.model.Task;
import com.sda.project.management.model.User;
import com.sda.project.management.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AcceptanceTest {

    @Autowired
    UserService userService;

    @Autowired
    ProjectService projectService;

    @Autowired
    SprintService sprintService;

    @Autowired
    TaskService taskService;

    @Autowired
    ProjectAccessService projectAccessService;

    @Test
    void acceptanceFlow() {
        // create users
        User projectLead = createAdmin();
        User user = createUser();
        userService.save(projectLead);
        userService.save(user);

        // create project
        Project project = createProject();

        // set project lead
        project.setProjectLead(projectLead);
        projectService.save(project);

        // create sprint
        Sprint sprint = createSprint();
        sprintService.save(sprint);

        // create tasks
        Task task1 = createTask1();
        Task task2 = createTask2();
        taskService.save(task1);
        taskService.save(task2);

        // add user to project
        projectAccessService.addUserToProject(user, project);
        Project projectWithUsers = projectService.findById(project.getId());
//        assertThat(projectWithUsers.getProjectAccessList()).hasSize(1);

        // add sprint to project
        projectService.addSprintToProject(project.getId(), sprint.getId());
//        assertThat(project.getSprints()).hasSize(1);

        // add tasks to sprint
        sprintService.addTaskToSprint(sprint.getId(), task1.getId());
        sprintService.addTaskToSprint(sprint.getId(), task2.getId());
//        assertThat(sprint.getTasks()).hasSize(2);
//        assertThat(sprint.getStoryPoints()).isEqualTo(8);
    }

    private User createAdmin() {
        User user = new User();
        user.setDisplayName("admin");
        user.setEmail("admin@gmail.com");
        user.setPassword("pass");
        user.setRoles("ADMIN");
        return user;
    }

    private User createUser() {
        User user = new User();
        user.setDisplayName("user");
        user.setEmail("user@gmail.com");
        user.setPassword("pass");
        user.setRoles("USER");
        return user;
    }

    private Project createProject() {
        Project project = new Project();
        project.setName("project");
        project.setProjectKey("PRO");
        return project;
    }

    //TODO-Alex: not working
    private Sprint createSprint() {
        Sprint sprint = new Sprint();
        sprint.setName("PRO 20-1");
        sprint.setDateFrom(LocalDate.now());
        sprint.setDateTo(sprint.getDateFrom().plusDays(14));
        sprint.setSprintGoal("learning");
        return sprint;
    }

    private Task createTask1() {
        Task task = new Task();
        task.setSummary("my task1");
        task.setDescription("easy task");
        task.setStoryPoints(3);
        return task;
    }

    private Task createTask2() {
        Task task = new Task();
        task.setSummary("my task2");
        task.setDescription("hard task");
        task.setStoryPoints(5);
        return task;
    }
}
