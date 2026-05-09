package com.stockportfolio.app;

import com.stockportfolio.app.dto.PortfolioRequest;
import com.stockportfolio.app.entity.Portfolio;
import com.stockportfolio.app.entity.User;
import com.stockportfolio.app.repository.PortfolioRepository;
import com.stockportfolio.app.repository.UserRepository;
import com.stockportfolio.app.service.PortfolioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PortfolioServiceTest {

    @Mock PortfolioRepository portfolioRepository;
    @Mock UserRepository userRepository;

    @InjectMocks PortfolioService portfolioService;

    @Test
    void testCreatePortfolio() {
        Authentication auth = mock(Authentication.class);
        SecurityContext ctx = mock(SecurityContext.class);
        when(ctx.getAuthentication()).thenReturn(auth);
        when(auth.getName()).thenReturn("chaitanya");
        SecurityContextHolder.setContext(ctx);

        User user = new User();
        user.setUsername("chaitanya");
        when(userRepository.findByUsername("chaitanya")).thenReturn(Optional.of(user));

        Portfolio saved = new Portfolio();
        saved.setName("Tech Stocks");
        when(portfolioRepository.save(any())).thenReturn(saved);

        PortfolioRequest req = new PortfolioRequest();
        req.setName("Tech Stocks");

        Portfolio result = portfolioService.createPortfolio(req);
        assertEquals("Tech Stocks", result.getName());
    }
}
