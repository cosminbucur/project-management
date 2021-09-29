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
            log.info("create admin user");

            Privilege readPrivilege = createPrivilegeIfNotFound(PrivilegeType.READ_PRIVILEGE);
            Privilege writePrivilege = createPrivilegeIfNotFound(PrivilegeType.WRITE_PRIVILEGE);

            createRoleIfNotFound(RoleType.ADMIN, Set.of(readPrivilege, writePrivilege));
            createRoleIfNotFound(RoleType.USER, Set.of(readPrivilege, writePrivilege));

            User admin = createAdmin();
            User user = createUser();

            Project project = new Project();
            project.setName("Sakura");
            project.setProjectKey("SAK");
            project.setProjectLead(admin);
            projectRepository.save(project);

            Sprint sprint = new Sprint();
            sprint.setName("SAK-20-1");
            sprint.setDateFrom(LocalDate.now());
            sprint.setDateTo(sprint.getDateFrom().plusDays(14));
            sprint.setStoryPoints(20);
            sprint.setSprintGoal("goal");

            Task task1 = new Task();
            task1.setProject(project);
            task1.setSummary("summary");
            task1.setStoryPoints(5);
            task1.setDescription("description");
            task1.setStatus(TaskStatus.TODO);
            task1.setTaskType(TaskType.TASK);
            task1.setAssignee(user);

            sprint.addTask(task1);
            sprintRepository.save(sprint);

            Task task2 = new Task();
            task2.setProject(project);
            task2.setSummary("bug summary");
            task2.setDescription("bug description");
            task2.setStatus(TaskStatus.IN_PROGRESS);
            task2.setTaskType(TaskType.BUG);
            taskRepository.save(task2);

            Task task3 = new Task();
            task3.setProject(project);
            task3.setSummary("bug summary");
            task3.setDescription("bug description");
            task3.setStatus(TaskStatus.IN_PROGRESS);
            task3.setTaskType(TaskType.BUG);
            taskRepository.save(task3);

            ProjectAccess projectAccess1 = new ProjectAccess();
            projectAccess1.setUser(user);
            projectAccess1.setProject(project);
            projectAccess1.setCreatedAt(LocalDate.now());
            projectAccessRepository.save(projectAccess1);

            ProjectAccess projectAccess2 = new ProjectAccess();
            projectAccess2.setUser(admin);
            projectAccess2.setProject(project);
            projectAccess2.setCreatedAt(LocalDate.now());
            projectAccessRepository.save(projectAccess2);
        };
    }

    private User createAdmin() {
        User admin = new User(
                "admin@gmail.com",
                "{bcrypt}$2y$12$92ZkDrGVS3W5ZJI.beRlEuyRCPrIRlkEHz6T.7MVmH38l4/VAHhyi",
                "jon",
                "snow");
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
        Role userRole = roleRepository.findByType(RoleType.USER).orElseThrow();;
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
