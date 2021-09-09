package com.sda.project.management.config;

import com.sda.project.management.model.Project;
import com.sda.project.management.model.Sprint;
import com.sda.project.management.model.User;
import com.sda.project.management.repository.ProjectRepository;
import com.sda.project.management.repository.SprintRepository;
import com.sda.project.management.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class DbInit {

    private static final Logger log = LoggerFactory.getLogger(DbInit.class);

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
            User admin = new User("admin", "{bcrypt}$2y$12$92ZkDrGVS3W5ZJI.beRlEuyRCPrIRlkEHz6T.7MVmH38l4/VAHhyi", "admin@gmail.com", "Jon Snow", "ADMIN");
            userRepository.save(admin);

            User user = new User("user", "{bcrypt}$2y$12$92ZkDrGVS3W5ZJI.beRlEuyRCPrIRlkEHz6T.7MVmH38l4/VAHhyi", "user@gmail.com", "Alex Vasile", "USER");
            userRepository.save(user);

            Project project = new Project();
            project.setName("Sakura");
            project.setDescription("CRM web application");
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
}
