package com.sda.project.management.config;

import com.sda.project.management.model.User;
import com.sda.project.management.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DbInit {

    private static final Logger log = LoggerFactory.getLogger(DbInit.class);

    @Autowired
    private UserRepository userRepository;

    @Bean
    public CommandLineRunner initialData() {
        return args -> {
            log.info("create admin user");
            User admin = new User("admin", "{bcrypt}$2y$12$92ZkDrGVS3W5ZJI.beRlEuyRCPrIRlkEHz6T.7MVmH38l4/VAHhyi", "admin@gmail.com", "admin", "ADMIN");
            userRepository.save(admin);
        };
    }
}
