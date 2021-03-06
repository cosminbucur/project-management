package com.sda.project.management;

import com.sda.project.management.dto.ProjectAccessInfo;
import com.sda.project.management.model.Privilege;
import com.sda.project.management.model.PrivilegeType;
import com.sda.project.management.model.Project;
import com.sda.project.management.model.Role;
import com.sda.project.management.model.RoleType;
import com.sda.project.management.model.Sprint;
import com.sda.project.management.model.Task;
import com.sda.project.management.model.User;
import com.sda.project.management.service.PrivilegeService;
import com.sda.project.management.service.ProjectAccessService;
import com.sda.project.management.service.ProjectService;
import com.sda.project.management.service.RoleService;
import com.sda.project.management.service.SprintService;
import com.sda.project.management.service.TaskService;
import com.sda.project.management.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Set;

@SpringBootTest
class AcceptanceTest {

    @Autowired
    PrivilegeService privilegeService;

    @Autowired
    RoleService roleService;

    @Autowired
    UserService userService;

    @Autowired
    ProjectService projectService;

    @Autowired
    SprintService sprintService;

    @Autowired
    TaskService taskService;

    @Autowired
    ProjectAccessService projectAccessService;

    @Test
    void acceptanceFlow() {
        // create privileges
        Privilege writePrivilege = privilegeService.save(PrivilegeType.WRITE_PRIVILEGE);
        Privilege readPrivilege = privilegeService.save(PrivilegeType.READ_PRIVILEGE);

        // create roles
        Role adminRole = roleService.save(RoleType.ADMIN, Set.of(writePrivilege));

        Role userRole = roleService.save(RoleType.USER, Set.of(readPrivilege));

        // create users
        User projectLead = createAdmin(adminRole);
        User user = createUser(userRole);
        userService.save(projectLead);
        userService.save(user);

        // create project
        Project project = createProject();

        // set project lead
        project.setProjectLead(projectLead);
        Project savedProject = projectService.save(project);

        // create sprint
        Sprint sprint = createSprint();
        sprintService.save(savedProject.getId());

        // create tasks
        Task task1 = createTask1();
        Task task2 = createTask2();
        taskService.save(task1);
        taskService.save(task2);

        ProjectAccessInfo projectAccessInfo = new ProjectAccessInfo();
        projectAccessInfo.setUserId(user.getId());
        projectAccessInfo.setProjectId(project.getId());

        // add user to project
        projectAccessService.addUserToProject(projectAccessInfo);
        Project projectWithUsers = projectService.findById(project.getId());
//        assertThat(projectWithUsers.getProjectAccessList()).hasSize(1);

        // add sprint to project
        projectService.addSprintToProject(project.getId(), sprint.getId());
//        assertThat(project.getSprints()).hasSize(1);

        // add tasks to sprint
        sprintService.addTaskToSprint(sprint.getId(), task1.getId());
        sprintService.addTaskToSprint(sprint.getId(), task2.getId());
//        assertThat(sprint.getTasks()).hasSize(2);
//        assertThat(sprint.getStoryPoints()).isEqualTo(8);
    }

    private User createAdmin(Role role) {
        User user = new User();
        user.setEmail("test-admin@gmail.com");
        user.setPassword("pass");
        user.addRole(role);
        return user;
    }

    private User createUser(Role role) {
        User user = new User();
        user.setEmail("test-user@gmail.com");
        user.setPassword("pass");
        user.addRole(role);
        return user;
    }

    private Project createProject() {
        Project project = new Project();
        project.setName("project");
        project.setProjectKey("PRO");
        return project;
    }

    private Sprint createSprint() {
        Sprint sprint = new Sprint();
        sprint.setName("PRO 20-1");
        sprint.setDateFrom(LocalDate.now());
        sprint.setDateTo(sprint.getDateFrom().plusDays(14));
        sprint.setSprintGoal("learning");
        return sprint;
    }

    private Task createTask1() {
        Task task = new Task();
        task.setSummary("my task1");
        task.setDescription("easy task");
        task.setStoryPoints(3);
        return task;
    }

    private Task createTask2() {
        Task task = new Task();
        task.setSummary("my task2");
        task.setDescription("hard task");
        task.setStoryPoints(5);
        return task;
    }
}
