package com.sda.project.management.repository;

import com.sda.project.management.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    Optional<Task> findById(Long id);

    // DONE: convert to hql
    @Query("FROM Task t WHERE t.sprint.id = :id ")
    List<Task> getTasksInSprint(Long id);

    // HQL
    @Query("FROM Task t WHERE t.project.id = :id AND t.sprint is null")
    List<Task> findProjectUnassignedTasks(Long id);
}

