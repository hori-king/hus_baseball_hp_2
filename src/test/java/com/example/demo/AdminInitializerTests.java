package com.example.demo;

import com.example.jyunko.config.AdminInitializer;
import com.example.jyunko.entity.User;
import com.example.jyunko.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import org.mockito.ArgumentCaptor;

class AdminInitializerTests {
    @Test
    void initialPasswordIsHashedAndExistingAccountIsPreserved() {
        UserRepository users = mock(UserRepository.class);
        var encoder = new BCryptPasswordEncoder();
        var initializer = new AdminInitializer(users, encoder, "admin", "test-password-only");
        initializer.run(new DefaultApplicationArguments());
        var saved = ArgumentCaptor.forClass(User.class);
        verify(users).save(saved.capture());
        assertThat(encoder.matches("test-password-only", saved.getValue().getPassword())).isTrue();
        assertThat(saved.getValue().getRole()).isEqualTo("ADMIN");
        reset(users);
        when(users.findByUsername("admin")).thenReturn(saved.getValue());
        initializer.run(new DefaultApplicationArguments());
        verify(users, never()).save(any());
    }

    @Test
    void noDefaultPasswordIsCreated() {
        UserRepository users = mock(UserRepository.class);
        new AdminInitializer(users, new BCryptPasswordEncoder(), "admin", "")
            .run(new DefaultApplicationArguments());
        verifyNoInteractions(users);
    }
}
