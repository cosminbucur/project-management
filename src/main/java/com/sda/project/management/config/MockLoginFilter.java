package com.sda.project.management.config;

import com.sda.project.management.model.User;
import com.sda.project.management.model.UserPrincipal;
import com.sda.project.management.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// TODO: remove this in production
/**
 * Bypasses spring security login form with in memory authentication
 */
@Component
public class MockLoginFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(MockLoginFilter.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
            User admin = userRepository.findByUsername("ADMIN");
            UserPrincipal userPrincipal = new UserPrincipal(admin);

            Authentication authentication = new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            response.sendRedirect("/");
        }

        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
