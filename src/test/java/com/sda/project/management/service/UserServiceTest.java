package com.sda.project.management.service;

import com.sda.project.management.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    void whenUserIsNotAdmin_shouldSaveUser() {
        User user = new User();
        //TODO use these if user is modified
//        user.setFirstName("alex");
//        user.setLastName("andru");
        user.setUsername("alex");
        user.setDisplayName("alexandru");
        user.setPassword("pass");
        user.setEmail("email@awesome.com");
        user.setRoles("USER");

        userService.save(user);

        assertThat(userService.findAll()).isNotNull();
    }
}