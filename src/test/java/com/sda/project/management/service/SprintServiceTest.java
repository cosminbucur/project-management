package com.sda.project.management.service;

import com.sda.project.management.model.Sprint;
import com.sda.project.management.model.Task;
import com.sda.project.management.repository.SprintRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SprintServiceTest {

    @Autowired
    SprintService sprintService;

    @Autowired
    TaskService taskService;

    // FIXME: not working
    @Test
    void shouldAddTaskToSprint() {
        // given
        Sprint sprint = new Sprint();
        sprint.setName("sprint");
        sprintService.save(sprint);

        Task task = new Task();
        task.setSummary("task");
        taskService.save(task);

        // when
        sprintService.addTaskToSprint(sprint.getId(), task.getId());
        List<Task> tasks = taskService.getTasks(sprint.getId());

        // then
        assertThat(tasks).hasSize(2);
    }

    @Test
    void shouldCreateSprint(){

        Sprint sprint = new Sprint();
        sprint.setName("alex");
        sprint.setDateFrom(LocalDate.now());
        sprint.setDateTo(LocalDate.now().plusDays(5));
        sprint.setStoryPoints(2);
        sprint.setSprintGoal("terminate");

        sprintService.save(sprint);

        assertThat(sprintService.findById(sprint.getId()).getSprintGoal()).isEqualTo("terminate");
    }
}