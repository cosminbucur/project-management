package com.sda.project.management.service;

import com.sda.project.management.model.Project;
import com.sda.project.management.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ProjectAccessServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    ProjectService projectService;

    @Autowired
    ProjectAccessService projectAccessService;

    @Test
    void shouldAddUserToProject() {
        // given
        Project project = new Project();
        project.setName("project");
        projectService.save(project);

        User user1 = new User("user1@gmail.com", "pass", "alex", "vasile");
        userService.save(user1);

        User user2 = new User( "user2@gmail.com", "pass", "alex", "vasile");
        userService.save(user2);

        // when
        projectAccessService.addUserToProject(user1, project);
        projectAccessService.addUserToProject(user2, project);

        List<User> usersInProject = projectAccessService.getUsersInProject(project);

        // then
        assertThat(usersInProject).hasSize(2);
    }
}