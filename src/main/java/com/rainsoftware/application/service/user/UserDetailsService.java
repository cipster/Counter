package com.rainsoftware.application.service.user;

import com.rainsoftware.application.domain.model.User;
import com.rainsoftware.application.domain.repository.UserRepository;
import com.rainsoftware.application.domain.repository.UserRoleRepository;
import com.rainsoftware.application.model.UserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsService.class);
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    @Autowired
    public UserDetailsService(UserRepository userRepository, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }


    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isPresent()) {
            List<String> userRoles = userRoleRepository.findRoleByUsername(username);

            return new UserDetails(user.get(), userRoles);
        } else {
            String message = "No user present with username " + username;
            LOGGER.info(message);
            throw new UsernameNotFoundException(message);
        }
    }
}
