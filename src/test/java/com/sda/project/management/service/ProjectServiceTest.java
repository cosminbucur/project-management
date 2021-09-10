package com.sda.project.management.service;

import com.sda.project.management.model.Project;
import com.sda.project.management.model.Sprint;
import com.sda.project.management.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ProjectServiceTest {

    @Autowired
    SprintService sprintService;

    @Autowired
    UserService userService;

    @Autowired
    ProjectService projectService;

    @Test
    void shouldSetLeadToProject() {
        // given
        Project project = new Project();
        project.setName("project");
        projectService.save(project);

        User user = new User("user", "test", "test@gmail.com", "Alex Vasile", "USER");
        userService.save(user);

        // when
        projectService.setProjectLead(project.getId(), user.getId());
        Project projectWithLead = projectService.findById(project.getId());

        // then
        assertThat(projectWithLead.getProjectLead()).isNotNull();
    }

    @Test
    void shouldAddSprintToProject() {
        // given
        Project project = new Project();
        project.setName("project");
        projectService.save(project);

        Sprint sprint = new Sprint();
        sprint.setName("sprint");
        sprintService.save(sprint);

        // when
        projectService.addSprintToProject(project.getId(), sprint.getId());
        Project projectWithSprint = projectService.findById(project.getId());

        // then
//        assertThat(projectWithSprint.getSprints()).contains(sprint);
    }

    @Test
    void shouldRemoveSprintFromProject() {

    }

}