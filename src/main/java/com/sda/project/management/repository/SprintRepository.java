package com.sda.project.management.repository;

import com.sda.project.management.model.Sprint;
import com.sda.project.management.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SprintRepository extends JpaRepository<Sprint, Long> {

    // HQL
//    @Query("SELECT task FROM Task WHERE t.name = :sprintId")
    @Query(value = "SELECT * FROM task t WHERE t.sprint_id = :sprintId ", nativeQuery = true)
    List<Task> getTasks(@Param("sprintId") Long sprintId);
}
