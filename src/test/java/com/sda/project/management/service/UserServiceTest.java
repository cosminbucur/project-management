package com.sda.project.management.service;

import com.sda.project.management.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    void whenSaveUserWithUniqueEmail_shouldSaveUser() {
        // given
        User user = new User("test@gmail.com", "pass", "jon", "snow");

        // when
        userService.save(user);

        // then
        User actualUser = userService.findByEmail("test@gmail.com");
        assertThat(actualUser.getEmail()).isEqualTo("test@gmail.com");
    }

    @Test
    void whenSaveUserWithExistingEmail_shouldNotSaveUser() {
        // given

        // when

        // then
    }

    @Test
    void whenFindAll_shouldReturnList() {
        // given

        // when

        // then
    }

    @Test
    void whenFindByExistingEmail_shouldReturnOne() {
        // given

        // when

        // then
    }


    @Test
    void whenFindByNonExistingEmail_shouldThrowException() {
        // given

        // when

        // then
    }

    @Test
    void whenFindById_shouldReturnOne() {
        // given

        // when

        // then
    }

    @Test
    void whenUpdate_shouldUpdateProject() {
        // given

        // when

        // then
    }

    @Test
    void whenDelete_shouldHaveProjectWithoutUsers() {
        // given

        // when

        // then
    }
}