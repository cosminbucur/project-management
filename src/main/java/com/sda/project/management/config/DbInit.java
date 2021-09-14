package com.sda.project.management.config;

import com.sda.project.management.model.*;
import com.sda.project.management.repository.*;
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

    @Bean
    public CommandLineRunner initialData() {
        return args -> {
            log.info("create admin user");

            Privilege readPrivilege = createPrivilegeIfNotFound(PrivilegeType.READ_PRIVILEGE);
            Privilege writePrivilege = createPrivilegeIfNotFound(PrivilegeType.WRITE_PRIVILEGE);

            createRoleIfNotFound(RoleType.ADMIN, Set.of(readPrivilege, writePrivilege));
            createRoleIfNotFound(RoleType.USER, Set.of(readPrivilege, writePrivilege));

            User admin = createAdmin();

            createUser();

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
            sprintRepository.save(sprint);
        };
    }

    private User createAdmin() {
        User admin = new User(
                "admin@gmail.com",
                "{bcrypt}$2y$12$92ZkDrGVS3W5ZJI.beRlEuyRCPrIRlkEHz6T.7MVmH38l4/VAHhyi",
                "jon",
                "snow");
        Role adminRole = roleRepository.findByType(RoleType.ADMIN);
        admin.addRole(adminRole);
        userRepository.save(admin);
        return admin;
    }

    private void createUser() {
        User user = new User(
                "user@gmail.com",
                "{bcrypt}$2y$12$92ZkDrGVS3W5ZJI.beRlEuyRCPrIRlkEHz6T.7MVmH38l4/VAHhyi",
                "alex",
                "vasile");
        Role userRole = roleRepository.findByType(RoleType.USER);
        user.addRole(userRole);
        userRepository.save(user);
    }

    @Transactional
    Role createRoleIfNotFound(RoleType name, Set<Privilege> privileges) {
        Role role = roleRepository.findByType(name);
        if (role == null) {
            role = new Role(name);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
        return role;
    }

    @Transactional
    Privilege createPrivilegeIfNotFound(PrivilegeType name) {
        Privilege privilege = privilegeRepository.findByType(name);
        if (privilege == null) {
            privilege = new Privilege(name);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }
}
