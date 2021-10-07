package com.sda.project.management.repository;

import com.sda.project.management.model.Sprint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SprintRepository extends JpaRepository<Sprint, Long> {

    Sprint findByName(String name);

    @Query("FROM Sprint s WHERE s.project.id = :id")
    List<Sprint> findByProjectId(Long id);
}
