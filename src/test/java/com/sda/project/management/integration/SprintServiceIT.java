package com.sda.project.management.integration;

import com.sda.project.management.model.Project;
import com.sda.project.management.model.Sprint;
import com.sda.project.management.model.Task;
import com.sda.project.management.service.ProjectService;
import com.sda.project.management.service.SprintService;
import com.sda.project.management.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SprintServiceIT {

    @Autowired
    ProjectService projectService;

    @Autowired
    SprintService sprintService;

    @Autowired
    TaskService taskService;

    @BeforeEach
    void setUp() {
        projectService.findAll()
                .forEach(project -> projectService.delete(project.getId()));
        sprintService.findAll()
                .forEach(sprint -> sprintService.delete(sprint.getId()));
        taskService.findAll()
                .forEach(task -> taskService.delete(task.getId()));
    }

    @Test
    void whenSaveTask_shouldReturnTask() {
        // given
        Project project = new Project();
        project.setName("project");
        Project savedProject = projectService.save(project);

        // when
        Sprint savedSprint = sprintService.save(savedProject.getId());

        // then
        assertThat(savedSprint).isNotNull();
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
    void whenAddTaskToSprint_shouldHaveSprintWithTask() {
        // given
        Project project = new Project();
        project.setName("project");
        Project savedProject = projectService.save(project);

        Sprint savedSprint = sprintService.save(savedProject.getId());

        Task task = new Task();
        task.setSummary("summary");
        taskService.save(task);

        // when
        sprintService.addTaskToSprint(savedSprint.getId(), task.getId());
        List<Task> tasks = taskService.getTasksInSprint(savedSprint.getId());

        // then
        assertThat(tasks).hasSize(2);
    }

    @Test
    void whenRemoveTaskFromSprint_shouldHaveSprintWithoutTask() {
        // given

        // when

        // then
    }

    @Test
    void whenDelete_shouldHaveProjectWithoutSprints() {
        // given

        // when

        // then
    }

    @Test
    void whenDelete_shouldRemoveTasksAndDeleteSprint() {
        // given
        Project project = new Project();
        project.setName("project");
        Project savedProject = projectService.save(project);

        Sprint savedSprint = sprintService.save(savedProject.getId());

        Task task = new Task();
        task.setSummary("summary");
        task.setSprint(savedSprint);
        taskService.save(task);

        // when
        sprintService.delete(savedSprint.getId());

        // then
        assertThat(sprintService.findAll()).isEmpty();
        assertThat(taskService.findAll()).isNotNull();
    }
}