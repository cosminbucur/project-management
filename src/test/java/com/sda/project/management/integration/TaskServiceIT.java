package com.sda.project.management.integration;

import com.sda.project.management.dto.TaskEdit;
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
    ProjectService projectService;

    @Autowired
    SprintService sprintService;

    @Autowired
    TaskService taskService;

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
    void whenGetUnassignedTasksByProjectId_shouldReturnOk() {
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
        List<Task> projectUnassignedTasks = taskService.getUnassignedTasksByProjectId(project.getId());

        // then
        assertThat(projectUnassignedTasks).hasSize(1);
        assertThat("summary 2").isEqualTo(assignedTask.getSummary());
    }

    @Test
    void whenGetTasksBySprintId_shouldReturnOk() {
        // given

        // when

        // then
    }

    @Test
    void whenGetTasksBySprintIdAndStatus_shouldReturnOk() {
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
        Task savedTask = taskService.save(task);

        TaskEdit taskEdit = new TaskEdit();
        taskEdit.setProject(project);
        taskEdit.setTaskType(TaskType.TASK);
        taskEdit.setSummary("summary2");

        // when
        taskService.update(savedTask.getId(), taskEdit);

        // then
        assertThat(taskService.findById(savedTask.getId()).getSummary()).isEqualTo(taskEdit.getSummary());
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
    void whenRemoveTaskFromSprint_shouldHaveSprintWithoutTask() {
        // given
        Project project = new Project();
        project.setName("project");
        projectService.save(project);
        Sprint savedSprint = sprintService.save(project.getId());

        Task assignedTask = new Task();
        assignedTask.setTaskType(TaskType.TASK);
        assignedTask.setSummary("summary 2");
        assignedTask.setSprint(savedSprint);
        taskService.save(assignedTask);

        // when
        taskService.removeTaskFromSprint(savedSprint.getId(), assignedTask.getId());

        // then
        assertThat(taskService.getTasksBySprintId(savedSprint.getId())).isEmpty();
    }
}