package com.sda.project.management.repository;

import com.sda.project.management.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    // SELECT COUNT(*) FROM users_roles WHERE role_id = 3
    @Query(value = "SELECT COUNT(*) FROM users_roles WHERE role_id = 3", nativeQuery = true)
    long countAdmins();

}
