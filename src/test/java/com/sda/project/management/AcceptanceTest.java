package com.sda.project.management;

import com.sda.project.management.model.Project;
import com.sda.project.management.model.Sprint;
import com.sda.project.management.model.Task;
import com.sda.project.management.model.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class AcceptanceTest {

    @Test
    void startSprint() {
        User owner = createAdmin();
        User user = createUser();

        Project project = createProject(owner);

        Sprint sprint = createSprint();

        // add sprint
        project.addSprint(sprint);

        addTask1(owner, project, sprint);
        addTask2(user, project, sprint);

        assertThat(project.getSprints()).hasSize(1);
        assertThat(sprint.getTasks()).hasSize(2);
        assertThat(sprint.getPlannedStoryPoints()).isEqualTo(8);
    }

    private User createAdmin() {
        User owner = new User();
        owner.setDisplayName("admin");
        owner.setEmail("admin@gmail.com");
        owner.setUsername("admin");
        owner.setPassword("pass");
        owner.setRoles("ADMIN");
        return owner;
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

    private Project createProject(User owner) {
        Project project = new Project();
        project.setName("Sakura");
        project.setDescription("CRM web application");

        // set user
        project.setOwner(owner);
        return project;
    }

    private Sprint createSprint() {
        Sprint sprint = new Sprint();
        sprint.setName("SAK 20-1");
        sprint.setDateFrom(LocalDate.now());
        sprint.setDateTo(sprint.getDateFrom().plusDays(14));
        return sprint;
    }

    private void addTask2(User user, Project project, Sprint sprint) {
        Task task2 = new Task();
        // set project
        task2.setProject(project);

        task2.setSummary("my task2");
        task2.setDescription("complex task description");

        // set user
        task2.setAssignee(user);

        // set sprint
        task2.setSprint(sprint);

        task2.setStoryPoints(5);
    }

    private void addTask1(User owner, Project project, Sprint sprint) {
        Task task1 = new Task();
        // set project
        task1.setProject(project);

        task1.setSummary("my task1");
        task1.setDescription("complex task description");

        // set user
        task1.setAssignee(owner);

        // set sprint
        task1.setSprint(sprint);

        task1.setStoryPoints(3);
    }
}
