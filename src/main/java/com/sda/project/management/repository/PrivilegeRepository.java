package com.sda.project.management.repository;

import com.sda.project.management.model.Privilege;
import com.sda.project.management.model.PrivilegeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

    Optional<Privilege> findByType(PrivilegeType type);
}
