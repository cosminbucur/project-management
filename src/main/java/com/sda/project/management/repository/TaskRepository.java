package com.sda.project.management.repository;

import com.sda.project.management.model.Task;
import com.sda.project.management.model.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    Optional<Task> findById(Long id);

    @Query("FROM Task t WHERE t.sprint.id = :sprintId ")
    List<Task> getTasksBySprintId(Long sprintId);

    @Query("FROM Task t WHERE t.sprint.id = :sprintId AND t.status = :status")
    List<Task> getTasksBySprintIdAndStatus(Long sprintId, TaskStatus status);

    @Query("FROM Task t WHERE t.project.id = :projectId AND t.sprint is null")
    List<Task> getUnassignedTasksByProjectId(Long projectId);
}

