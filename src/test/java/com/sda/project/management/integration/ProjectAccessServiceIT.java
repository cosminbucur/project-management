package com.sda.project.management.integration;

import com.sda.project.management.dto.ProjectAccessInfo;
import com.sda.project.management.model.Project;
import com.sda.project.management.model.User;
import com.sda.project.management.service.ProjectAccessService;
import com.sda.project.management.service.ProjectService;
import com.sda.project.management.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ProjectAccessServiceIT {

    @Autowired
    UserService userService;

    @Autowired
    ProjectService projectService;

    @Autowired
    ProjectAccessService projectAccessService;

    @Test
    void whenAddUserToProject_shouldHaveProjectWithUser() {
        // given
        Project project = new Project();
        project.setName("project");
        projectService.save(project);

        User user1 = new User("user1@gmail.com", "pass", "alex", "vasile");
        userService.save(user1);

        User user2 = new User("user2@gmail.com", "pass", "alex", "vasile");
        userService.save(user2);

        ProjectAccessInfo projectAccessInfo1 = new ProjectAccessInfo();
        projectAccessInfo1.setUserId(user1.getId());
        projectAccessInfo1.setProjectId(project.getId());

        ProjectAccessInfo projectAccessInfo2 = new ProjectAccessInfo();
        projectAccessInfo2.setUserId(user2.getId());
        projectAccessInfo2.setProjectId(project.getId());

        // when
        projectAccessService.addUserToProject(projectAccessInfo1);
        projectAccessService.addUserToProject(projectAccessInfo2);

        List<User> usersInProject = projectAccessService.getAssignedUsers(project.getId());

        // then
        assertThat(usersInProject).hasSize(2);
    }

    @Test
    void whenRemoveUserFromProject_shouldHaveProjectWithoutUser() {
        // given

        // when

        // then
    }

    @Test
    void whenGetUsersInProject_shouldReturnUserList() {
        // given

        // when

        // then
    }
}