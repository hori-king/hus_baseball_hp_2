package com.example.jyunko.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.jyunko.entity.User;
import com.example.jyunko.repository.UserRepository;

/** Creates the initial administrator only when credentials are supplied. */
@Component
public class AdminInitializer implements ApplicationRunner {
    private final UserRepository users;
    private final PasswordEncoder encoder;
    private final String username;
    private final String password;

    public AdminInitializer(UserRepository users, PasswordEncoder encoder,
            @Value("${ADMIN_USERNAME:admin}") String username,
            @Value("${ADMIN_PASSWORD:}") String password) {
        this.users = users;
        this.encoder = encoder;
        this.username = username;
        this.password = password;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (password.isBlank() || users.findByUsername(username) != null) {
            return;
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(encoder.encode(password));
        user.setRole("ADMIN");
        users.save(user);
    }
}
