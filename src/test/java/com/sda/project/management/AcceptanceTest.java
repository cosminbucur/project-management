package com.sda.project.management;

import com.sda.project.management.model.Project;
import com.sda.project.management.model.Sprint;
import com.sda.project.management.model.Task;
import com.sda.project.management.model.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

// FIXME: create acceptance test using services
class AcceptanceTest {

    @Test
    void startSprint() {
        User projectLead = createAdmin();
        User user = createUser();

        Project project = createProject(projectLead);

        Sprint sprint = createSprint();

        // add sprint
        project.addSprint(sprint);

        addTask1(projectLead, project, sprint);
        addTask2(user, project, sprint);

        assertThat(project.getSprints()).hasSize(1);
        assertThat(sprint.getTasks()).hasSize(2);
        assertThat(sprint.getStoryPoints()).isEqualTo(8);
    }

    private User createAdmin() {
        User projectLead = new User();
        projectLead.setDisplayName("admin");
        projectLead.setEmail("admin@gmail.com");
        projectLead.setUsername("admin");
        projectLead.setPassword("pass");
        projectLead.setRoles("ADMIN");
        return projectLead;
    }

    private User createUser() {
        User user = new User();
        user.setDisplayName("user");
        user.setEmail("user@gmail.com");
        user.setUsername("user");
        user.setPassword("pass");
        user.setRoles("USER");
        return user;
    }

    private Project createProject(User projectLead) {
        Project project = new Project();
        project.setName("Sakura");
        project.setKey("SAK");

        // set user
        project.setProjectLead(projectLead);
        return project;
    }

    private Sprint createSprint() {
        Sprint sprint = new Sprint();
        sprint.setName("SAK 20-1");
        sprint.setDateFrom(LocalDate.now());
        sprint.setDateTo(sprint.getDateFrom().plusDays(14));
        return sprint;
    }

    private void addTask1(User assignee, Project project, Sprint sprint) {
        Task task = new Task();
        // set project
        task.setProject(project);

        task.setSummary("my task1");
        task.setDescription("complex task description");

        // set user
        task.setAssignee(assignee);

        // set sprint
        task.setSprint(sprint);

        task.setStoryPoints(3);
    }

    private void addTask2(User user, Project project, Sprint sprint) {
        Task task = new Task();
        // set project
        task.setProject(project);

        task.setSummary("my task2");
        task.setDescription("complex task description");

        // set user
        task.setAssignee(user);

        // set sprint
        task.setSprint(sprint);

        task.setStoryPoints(5);
    }
}
