package com.sda.project.management.repository;

import com.sda.project.management.model.Sprint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SprintRepository extends JpaRepository<Sprint, Long> {

    // HQL
    @Query("FROM Sprint s WHERE s.name = :name")
    Sprint getSprintIdsByName(String name);
}
