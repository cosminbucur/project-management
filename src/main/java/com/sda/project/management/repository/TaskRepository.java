package com.sda.project.management.repository;

import com.sda.project.management.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    Optional<Task> findById(Long id);

    @Query(value = "SELECT * FROM task t WHERE t.sprint_id = :sprintId ", nativeQuery = true)
    List<Task> getTasks(@Param("sprintId") Long sprintId);
}

