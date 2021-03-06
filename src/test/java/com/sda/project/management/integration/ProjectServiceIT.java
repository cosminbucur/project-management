package com.sda.project.management.integration;

import com.sda.project.management.model.Project;
import com.sda.project.management.model.Sprint;
import com.sda.project.management.model.User;
import com.sda.project.management.service.ProjectService;
import com.sda.project.management.service.SprintService;
import com.sda.project.management.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ProjectServiceIT {

    @Autowired
    ProjectService projectService;

    @Autowired
    SprintService sprintService;

    @Autowired
    UserService userService;

    @Test
    void whenSaveNewProjectName_shouldReturnProject() {
        // given

        // when

        // then
    }

    @Test
    void whenSaveExistingProjectName_shouldNotSaveProject() {
        // given

        // when

        // then
    }

    @Test
    void whenFindAll_shouldReturnList() {
        // given

        // when

        // then
    }

    @Test
    void whenFindById_shouldReturnOne() {
        // given

        // when

        // then
    }

    @Test
    void whenUpdate_shouldUpdateProject() {
        // given

        // when

        // then
    }

    @Test
    void whenUpdateExistingName_shouldNotUpdateProject() {
        // given

        // when

        // then
    }

    @Test
    void whenSetProjectLead_shouldHaveAProjectLead() {
        // given
        Project project = new Project();
        project.setName("project");
        projectService.save(project);

        User user = new User("test@gmail.com", "test", "alex", "vasile");
        userService.save(user);

        // when
        projectService.setProjectLead(project.getId(), user.getId());
        Project projectWithLead = projectService.findById(project.getId());

        // then
        assertThat(projectWithLead.getProjectLead()).isNotNull();
    }

    @Test
    void whenAddSprintToProject_shouldHaveProjectWithSprint() {
        // given
        Project project = new Project();
        project.setName("project");
        Project savedProject = projectService.save(project);

        Sprint savedSprint = sprintService.save(savedProject.getId());

        // when
        projectService.addSprintToProject(project.getId(), savedSprint.getId());
        Project projectWithSprint = projectService.findById(project.getId());

        // then
        assertThat(projectWithSprint.getSprints()).contains(savedSprint);
    }

    @Test
    void whenDelete_shouldHaveNoProjects() {
        // given

        // when

        // then
    }
}