package com.sda.project.management.repository;

import com.sda.project.management.model.ProjectAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectAccessRepository extends JpaRepository<ProjectAccess, Long> {

    @Query(value = "SELECT * FROM project_access p WHERE p.project_id = :projectId", nativeQuery = true)
    List<ProjectAccess> getUsersInProject(@Param("projectId")  Long projectId);
}
