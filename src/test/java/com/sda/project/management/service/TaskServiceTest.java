package com.sda.project.management.service;

import com.sda.project.management.model.Project;
import com.sda.project.management.model.Task;
import com.sda.project.management.model.TaskType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TaskServiceTest {

    @Autowired
    TaskService taskService;

    @Autowired
    ProjectService projectService;

    @Test
    void shouldSaveTask() {
        Project project = createProject();

        Task task = new Task();
        task.setProject(project);
        task.setTaskType(TaskType.TASK);
        task.setSummary("summary");

        taskService.save(task);

        assertThat(taskService.findAll()).isNotNull();
    }

    @Test
    void shouldUpdateTask() {
        Project project = createProject();

        Task task = new Task();
        task.setProject(project);
        task.setTaskType(TaskType.TASK);
        task.setSummary("summary");

        taskService.save(task);

        Task taskUpdate = new Task();
        taskUpdate.setProject(project);
        taskUpdate.setTaskType(TaskType.TASK);
        taskUpdate.setSummary("summary2");

        Task updatedTask = taskService.update(task.getId(), taskUpdate);

        assertThat(updatedTask.getSummary()).isEqualTo(taskUpdate.getSummary());
    }

    private Project createProject() {
        Project project = new Project();
        project.setName("project");
        projectService.save(project);
        return project;
    }
}