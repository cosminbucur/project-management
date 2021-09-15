package com.sda.project.management.service;

import com.sda.project.management.config.security.UserPrincipal;
import com.sda.project.management.controller.exception.ResourceAlreadyExistsException;
import com.sda.project.management.model.Role;
import com.sda.project.management.model.RoleType;
import com.sda.project.management.model.User;
import com.sda.project.management.repository.RoleRepository;
import com.sda.project.management.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email + " not found"));
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .map(user -> {
                    Set<Role> roles = roleRepository.getRoles(user.getId());
                    return new UserPrincipal(user, roles);
                })
                .orElseThrow(() -> new UsernameNotFoundException(email + " not found"));
    }

    @Transactional
    public void save(User user) {
        log.info("save user {}", user);

        String email = user.getEmail();
        userRepository.findByEmail(email.toLowerCase())
                .map((existingUser) -> {
                    log.error("user with email {} already exists", email);
                    throw new ResourceAlreadyExistsException("user with email " + email + " already exists");
                })
                .orElseGet(() -> saveUser(user));
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

    private User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository.findByType(RoleType.USER);
        user.addRole(userRole);
        return userRepository.save(user);
    }
}
