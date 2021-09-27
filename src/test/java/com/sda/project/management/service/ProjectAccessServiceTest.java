package com.sda.project.management.service;

import com.sda.project.management.model.ProjectAccess;
import com.sda.project.management.model.User;
import com.sda.project.management.repository.ProjectAccessRepository;
import com.sda.project.management.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProjectAccessServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    ProjectAccessRepository projectAccessRepository;

    @InjectMocks
    ProjectAccessService projectAccessService;

    @Test
    void whenGetUnassignedUsers_shouldReturnOk() {
        // given
        User user1 = new User("e", "p", "f", "l");
        user1.setId(1L);
        User user2 = new User("e", "p", "f", "l");
        user2.setId(2L);
        User user3 = new User("e", "p", "f", "l");
        user3.setId(3L);

        ProjectAccess projectAccess1 = new ProjectAccess();
        projectAccess1.setUser(user1);
        ProjectAccess projectAccess2 = new ProjectAccess();
        projectAccess2.setUser(user2);

        when(projectAccessRepository.getUsersInProject(anyLong()))
                .thenReturn(List.of(projectAccess1, projectAccess2));
        when(userRepository.findAll())
                .thenReturn(List.of(user1, user2, user3));

        // when
        Set<User> unassignedUsers = projectAccessService.getUnassignedUsers(1L);

        // then
        assertThat(unassignedUsers).hasSize(1);
        assertThat(unassignedUsers.contains(user3)).isTrue();
    }
}