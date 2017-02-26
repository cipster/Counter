package com.rainsoftware.application.service.user;

import com.rainsoftware.application.domain.model.User;
import com.rainsoftware.application.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class FirstPageHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserRepository userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (authentication == null) {
            throw new IllegalStateException("Null Authentication");
        }

        User user = userService.findByUsername(authentication.getName()).orElse(null);

        if (user != null) {
            user.setLastLogin(LocalDateTime.now());
            userService.save(user);

            String firstPage = user.getFirstPage() != null ? user.getFirstPage() : "/";

            response.sendRedirect(firstPage);
        } else {
            response.sendRedirect("/");
        }
    }
}
