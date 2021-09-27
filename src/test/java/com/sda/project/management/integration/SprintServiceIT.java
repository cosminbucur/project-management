package com.sda.project.management.integration;

import com.sda.project.management.model.Sprint;
import com.sda.project.management.model.Task;
import com.sda.project.management.service.SprintService;
import com.sda.project.management.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SprintServiceIT {

    @Autowired
    SprintService sprintService;

    @Autowired
    TaskService taskService;

    @Test
    void whenSaveTask_shouldReturnTask() {
        // given
        Sprint sprint = new Sprint();
        sprint.setName("name");
        sprint.setDateFrom(LocalDate.now());
        sprint.setDateTo(LocalDate.now().plusDays(14));
        sprint.setStoryPoints(2);
        sprint.setSprintGoal("sprint goal");

        // when
        Sprint savedSprint = sprintService.save(sprint);

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
        Sprint sprint = new Sprint();
        sprint.setName("name");
        sprintService.save(sprint);

        Task task = new Task();
        task.setSummary("summary");
        taskService.save(task);

        // when
        sprintService.addTaskToSprint(sprint.getId(), task.getId());
        List<Task> tasks = taskService.getTasksInSprint(sprint.getId());

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
}