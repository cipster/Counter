package com.rainsoftware.application.service.user;

import com.rainsoftware.application.domain.model.User;
import com.rainsoftware.application.model.UserDetails;

import java.util.List;

public interface UserService {
    User findByUsername(String username);

    User save(User user);

    User addNewUser(User user);

    List<User> findAll();

    List<UserDetails> findAllUserDetails();

    org.springframework.security.core.userdetails.UserDetails getCurrentUserDetails();

    User getCurrentUser();

    User update(User user);

    User updatePassword(User user);

    User getUserByUsername(String username);

    User deleteUser(long id);

    User undoDeleteUser(long id);

    List<User> getActiveUsers();
}
