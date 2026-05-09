package com.stockportfolio.app.repository;

import com.stockportfolio.app.entity.Portfolio;
import com.stockportfolio.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    List<Portfolio> findByUser(User user);
    List<Portfolio> findByUserId(Long userId);
}
