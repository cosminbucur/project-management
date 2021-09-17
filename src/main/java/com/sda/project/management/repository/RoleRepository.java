package com.sda.project.management.repository;

import com.sda.project.management.model.Role;
import com.sda.project.management.model.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByType(RoleType type);

    // select entity join with collection
    @Query("FROM Role r join r.users u WHERE u.id = :userId")
    Set<Role> getRoles(@Param("userId") Long userId);

}
