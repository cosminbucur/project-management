package com.sda.project.management.config;

import com.sda.project.management.controller.exception.ResourceAlreadyExistsException;
import com.sda.project.management.model.Privilege;
import com.sda.project.management.model.PrivilegeType;
import com.sda.project.management.model.Project;
import com.sda.project.management.model.ProjectAccess;
import com.sda.project.management.model.Role;
import com.sda.project.management.model.RoleType;
import com.sda.project.management.model.Sprint;
import com.sda.project.management.model.Task;
import com.sda.project.management.model.TaskStatus;
import com.sda.project.management.model.TaskType;
import com.sda.project.management.model.User;
import com.sda.project.management.repository.PrivilegeRepository;
import com.sda.project.management.repository.ProjectAccessRepository;
import com.sda.project.management.repository.ProjectRepository;
import com.sda.project.management.repository.RoleRepository;
import com.sda.project.management.repository.SprintRepository;
import com.sda.project.management.repository.TaskRepository;
import com.sda.project.management.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Set;

@Configuration
public class DbInit {

    private static final Logger log = LoggerFactory.getLogger(DbInit.class);

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private SprintRepository sprintRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectAccessRepository projectAccessRepository;

    @Bean
    public CommandLineRunner initialData() {
        return args -> {
            log.info("setup initial data");

            // create privileges
            Privilege readPrivilege = createPrivilegeIfNotFound(PrivilegeType.READ_PRIVILEGE);
            Privilege writePrivilege = createPrivilegeIfNotFound(PrivilegeType.WRITE_PRIVILEGE);

            // create roles
            createRoleIfNotFound(RoleType.ADMIN, Set.of(readPrivilege, writePrivilege));
            createRoleIfNotFound(RoleType.USER, Set.of(readPrivilege, writePrivilege));

            // create main admin, admin, user
            User mainAdmin = createMainAdmin();
            userRepository.save(mainAdmin);

            User admin = createAdmin();
            User user = createUser();

            Project project1 = new Project();
            project1.setName("Sakura");
            project1.setProjectKey("SAK");
            project1.setProjectLead(admin);
            projectRepository.save(project1);

            Project project2 = new Project();
            project2.setName("Agile");
            project2.setProjectKey("AGL");
            project2.setProjectLead(user);
            projectRepository.save(project2);

            // add sprint 1 to project 1
            Sprint sprint1 = new Sprint();
            sprint1.setName("SAK-20-1");
            sprint1.setDateFrom(LocalDate.now());
            sprint1.setDateTo(sprint1.getDateFrom().plusDays(14));
            sprint1.setSprintGoal("goal");
            sprint1.setActive(false);
            sprint1.setComplete(false);
            sprint1.setProject(project1);
            sprintRepository.save(sprint1);

            // add sprint 2 to project 1
            Sprint sprint2 = new Sprint();
            sprint2.setName("SAK-20-2");
            sprint2.setSprintGoal("goal 2");
            sprint2.setActive(false);
            sprint2.setComplete(false);
            sprint2.setProject(project1);
            sprintRepository.save(sprint2);

            // add sprint 3 to project 2
            Sprint sprint3 = new Sprint();
            sprint3.setName("AGL-20-1");
            sprint3.setDateFrom(LocalDate.now());
            sprint3.setDateTo(sprint3.getDateFrom().plusDays(14));
            sprint3.setSprintGoal("goal 3");
            sprint3.setActive(false);
            sprint3.setComplete(false);
            sprint3.setProject(project2);
            sprintRepository.save(sprint3);

            // add task 1 to project 1 sprint 1
            Task task1 = new Task();
            task1.setProject(project1);
            task1.setSummary("summary");
            task1.setStoryPoints(5);
            task1.setDescription("description");
            task1.setStatus(TaskStatus.TODO);
            task1.setTaskType(TaskType.STORY);
            task1.setAssignee(user);
            task1.setSprint(sprint1);
            taskRepository.save(task1);

            // add task 2 to project 1 sprint 1
            Task task2 = new Task();
            task2.setProject(project1);
            task2.setSummary("summary");
            task2.setStoryPoints(5);
            task2.setDescription("description");
            task2.setStatus(TaskStatus.TODO);
            task2.setTaskType(TaskType.TASK);
            task2.setAssignee(user);
            task2.setSprint(sprint1);
            taskRepository.save(task2);

            // add task 3 to project 1 backlog
            Task task3 = new Task();
            task3.setProject(project1);
            task3.setSummary("bug summary");
            task3.setDescription("bug description");
            task3.setStatus(TaskStatus.IN_PROGRESS);
            task3.setTaskType(TaskType.BUG);
            taskRepository.save(task3);

            // add task 4 to project 1 backlog
            Task task4 = new Task();
            task4.setProject(project1);
            task4.setSummary("bug summary");
            task4.setDescription("bug description");
            task4.setStatus(TaskStatus.DONE);
            task4.setTaskType(TaskType.TASK);
            taskRepository.save(task4);

            // add user to project 1
            ProjectAccess projectAccess1 = new ProjectAccess();
            projectAccess1.setUser(user);
            projectAccess1.setProject(project1);
            projectAccess1.setCreatedAt(LocalDate.now());
            projectAccessRepository.save(projectAccess1);

            // add admin to project 1
            ProjectAccess projectAccess2 = new ProjectAccess();
            projectAccess2.setUser(admin);
            projectAccess2.setProject(project1);
            projectAccess2.setCreatedAt(LocalDate.now());
            projectAccessRepository.save(projectAccess2);
        };
    }

    private User createMainAdmin() {
        User admin = new User(
                "main@gmail.com",
                "{bcrypt}$2y$12$92ZkDrGVS3W5ZJI.beRlEuyRCPrIRlkEHz6T.7MVmH38l4/VAHhyi",
                "jon",
                "snow");
        Role adminRole = roleRepository.findByType(RoleType.ADMIN).orElseThrow();
        admin.addRole(adminRole);
        userRepository.save(admin);
        return admin;
    }

    private User createAdmin() {
        User admin = new User(
                "admin@gmail.com",
                "{bcrypt}$2y$12$92ZkDrGVS3W5ZJI.beRlEuyRCPrIRlkEHz6T.7MVmH38l4/VAHhyi",
                "bill",
                "clinton");
        Role adminRole = roleRepository.findByType(RoleType.ADMIN).orElseThrow();
        admin.addRole(adminRole);
        userRepository.save(admin);
        return admin;
    }

    private User createUser() {
        User user = new User(
                "user@gmail.com",
                "{bcrypt}$2y$12$92ZkDrGVS3W5ZJI.beRlEuyRCPrIRlkEHz6T.7MVmH38l4/VAHhyi",
                "alex",
                "vasile");
        Role userRole = roleRepository.findByType(RoleType.USER).orElseThrow();
        user.addRole(userRole);
        return userRepository.save(user);
    }

    @Transactional
    Role createRoleIfNotFound(RoleType type, Set<Privilege> privileges) {
        return (Role) roleRepository.findByType(type)
                .map((existingPrivilege) -> {
                    throw new ResourceAlreadyExistsException("role already exists");
                })
                .orElseGet(() -> {
                    Role role = new Role(type);
                    role.setPrivileges(privileges);
                    return roleRepository.save(role);
                });
    }

    @Transactional
    Privilege createPrivilegeIfNotFound(PrivilegeType name) {
        return (Privilege) privilegeRepository.findByType(name)
                .map((existingPrivilege) -> {
                    throw new ResourceAlreadyExistsException("privilege already exists");
                })
                .orElseGet(() -> privilegeRepository.save(new Privilege(name)));
    }
}
