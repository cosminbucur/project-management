package com.sda.project.management.service;

import com.sda.project.management.model.User;
import com.sda.project.management.config.UserPrincipal;
import com.sda.project.management.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(email);
        }

        return new UserPrincipal(user);
    }

    public void saveCustomer(User user) {
        user.setRoles("ROLE_CUSTOMER");
        String password = user.getPassword();
        String encodePassword = passwordEncoder.encode(password);
        user.setPassword(encodePassword);
        userRepository.save(user);
    }

    public void save(User user) {
        log.info("save user {}", user);
        user.setRoles("USER");
        userRepository.save(user);
    }

    public List<User> findAll() {
        log.info("find users");
        return userRepository.findAll();
    }

    public User findById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("not found"));
    }

    public void update(User user) {
        userRepository.save(user);
    }

    public void delete(Long id) {
        log.info("delete user {}", id);
        userRepository.deleteById(id);
    }
}
