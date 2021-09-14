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
    void whenSave_shouldSaveUser() {
        // given
        User user = new User("test@gmail.com", "pass", "jon", "snow");

        // when
        userService.save(user);

        // then
        User actualUser = userService.findByEmail("test@gmail.com");
        assertThat(actualUser.getEmail()).isEqualTo("test@gmail.com");
    }
}