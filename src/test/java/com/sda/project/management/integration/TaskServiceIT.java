package com.sda.project.management.integration;

import com.sda.project.management.model.Project;
import com.sda.project.management.model.Sprint;
import com.sda.project.management.model.Task;
import com.sda.project.management.model.TaskType;
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
class TaskServiceIT {

    @Autowired
    TaskService taskService;

    @Autowired
    ProjectService projectService;

    @Autowired
    SprintService sprintService;

    @BeforeEach
    void setUp() {
        taskService.findAll()
                .forEach(task -> taskService.delete(task.getId()));
        projectService.findAll()
                .forEach(project -> projectService.delete(project.getId()));
    }

    @Test
    void whenSaveTask_shouldReturnTask() {
        // given
        Project project = new Project();
        project.setName("project");
        projectService.save(project);

        Task task = new Task();
        task.setProject(project);
        task.setTaskType(TaskType.TASK);
        task.setSummary("summary");

        // when
        taskService.save(task);

        // then
        assertThat(taskService.findAll()).isNotNull();
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
    void whenUpdate_shouldUpdateTask() {
        // given
        Project project = new Project();
        project.setName("project");
        projectService.save(project);

        Task task = new Task();
        task.setProject(project);
        task.setTaskType(TaskType.TASK);
        task.setSummary("summary");
        taskService.save(task);

        Task taskUpdate = new Task();
        taskUpdate.setProject(project);
        taskUpdate.setTaskType(TaskType.TASK);
        taskUpdate.setSummary("summary2");

        // when
        Task updatedTask = taskService.update(taskUpdate);

        // then
        assertThat(updatedTask.getSummary()).isEqualTo(taskUpdate.getSummary());
    }

    @Test
    void whenDelete_shouldHaveProjectWithoutTask() {
        // given
        Project project = new Project();
        project.setName("project");
        projectService.save(project);

        Task task = new Task();
        task.setProject(project);
        task.setTaskType(TaskType.TASK);
        task.setSummary("summary");
        Task savedTask = taskService.save(task);

        // when
        taskService.delete(savedTask.getId());

        // then
        assertThat(taskService.findAll()).isEmpty();
    }

    @Test
    void whenFindProjectUnassignedTasks_shouldReturnList() {
        // given
        Project project = new Project();
        project.setName("project");
        projectService.save(project);

        Task unassignedTask = new Task();
        unassignedTask.setProject(project);
        unassignedTask.setTaskType(TaskType.TASK);
        unassignedTask.setSummary("summary 1");
        taskService.save(unassignedTask);

        Task assignedTask = new Task();
        assignedTask.setProject(project);
        assignedTask.setTaskType(TaskType.TASK);
        assignedTask.setSummary("summary 2");
        taskService.save(assignedTask);

        Sprint savedSprint = sprintService.save(project.getId());

        sprintService.addTaskToSprint(savedSprint.getId(), assignedTask.getId());

        // when
        List<Task> projectUnassignedTasks = taskService.findProjectUnassignedTasks(project.getId());

        // then
        assertThat(projectUnassignedTasks).hasSize(1);
        assertThat("summary 2").isEqualTo(assignedTask.getSummary());
    }
}