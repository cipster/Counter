package com.rainsoftware.application.service.user;

import com.rainsoftware.application.common.AuthUtils;
import com.rainsoftware.application.common.errors.NotFoundException;
import com.rainsoftware.application.domain.model.User;
import com.rainsoftware.application.domain.model.UserRole;
import com.rainsoftware.application.domain.repository.UserRepository;
import com.rainsoftware.application.domain.repository.UserRoleRepository;
import com.rainsoftware.application.model.Role;
import com.rainsoftware.application.model.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User findByUsername(String username) {
        Optional<User> byUsername = userRepository.findByUsername(username);

        return byUsername.orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found"));
    }

    @Override
    public User save(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        return userRepository.save(user);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public User addNewUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);

        User saved;
        try {
            saved = userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Username is already in use");
        }
        UserRole userRole = new UserRole();
        userRole.setRole(Role.ROLE_USER);
        userRole.setUsername(saved.getUsername());

        userRoleRepository.save(userRole);

        return saved;
    }

    @Override
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public List<UserDetails> findAllUserDetails() {
        List<User> allUsers = findAll();
        return allUsers
                .stream()
                .filter(user -> !user.isDeleted())
                .map(user -> new UserDetails(user, userRoleRepository.findRoleByUsername(user.getUsername())))
                .collect(toList());
    }

    @Override
    public org.springframework.security.core.userdetails.UserDetails getCurrentUserDetails() {
        return userDetailsService.loadUserByUsername(AuthUtils.getLoggedInUsername());
    }

    @Override
    public User getCurrentUser() {
        Optional<User> byUsername = userRepository.findByUsername(AuthUtils.getLoggedInUsername());

        return byUsername.orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    public User update(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        User userToUpdate = findByUsername(user.getUsername());
        userToUpdate.setProfileImage(user.getProfileImage());

        userToUpdate.setLastName(user.getLastName());
        userToUpdate.setFirstName(user.getFirstName());
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setPhone(user.getPhone());
        userToUpdate.setFirstPage(user.getFirstPage());
        userToUpdate.setNotifiable(user.isNotifiable());

        return userRepository.save(userToUpdate);
    }

    @Override
    public User updatePassword(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        User userToUpdate = findByUsername(user.getUsername());

        userToUpdate.setPassword(passwordEncoder.encode(user.getPassword()));
        userToUpdate.setLastPasswordChange(LocalDateTime.now());

        return userRepository.save(userToUpdate);
    }


    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User cannot be found"));
    }

    @Override
    public User deleteUser(long id) {
        User toDelete = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User cannot be found"));

        if (toDelete.isEnabled()) {
            throw new IllegalStateException(String.format("%s is still active. Disable this user before trying to delete him", toDelete.getUsername()));
        }

        toDelete.setDeleted(true);

        userRepository.save(toDelete);

        return toDelete;
    }

    @Override
    public User undoDeleteUser(long id) {
        User deleted = userRepository.findOne(id);

        deleted.setDeleted(false);

        userRepository.save(deleted);

        return deleted;
    }

    @Override
    public List<User> getActiveUsers() {
        List<User> allUsers = findAll();
        return allUsers
                .stream()
                .filter(user -> !user.isDeleted())
                .collect(toList());
    }
}
