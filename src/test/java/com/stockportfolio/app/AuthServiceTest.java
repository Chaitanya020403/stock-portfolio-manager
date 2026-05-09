package com.stockportfolio.app;

import com.stockportfolio.app.dto.RegisterRequest;
import com.stockportfolio.app.entity.User;
import com.stockportfolio.app.repository.UserRepository;
import com.stockportfolio.app.security.JwtUtil;
import com.stockportfolio.app.service.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock UserRepository userRepository;
    @Mock PasswordEncoder passwordEncoder;
    @Mock JwtUtil jwtUtil;
    @Mock AuthenticationManager authenticationManager;

    @InjectMocks AuthService authService;

    @Test
    void testRegisterSuccess() {
        RegisterRequest req = new RegisterRequest();
        req.setUsername("chaitanya");
        req.setEmail("chaitanya@test.com");
        req.setPassword("password123");

        when(userRepository.existsByUsername("chaitanya")).thenReturn(false);
        when(userRepository.existsByEmail("chaitanya@test.com")).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("encoded");

        authService.register(req);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testRegisterDuplicateUsername() {
        RegisterRequest req = new RegisterRequest();
        req.setUsername("chaitanya");
        req.setEmail("test@test.com");
        req.setPassword("password123");

        when(userRepository.existsByUsername("chaitanya")).thenReturn(true);

        assertThrows(RuntimeException.class, () -> authService.register(req));
    }
}
