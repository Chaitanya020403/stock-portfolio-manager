package com.stockportfolio.app.service;

import com.stockportfolio.app.dto.HoldingRequest;
import com.stockportfolio.app.entity.*;
import com.stockportfolio.app.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class HoldingService {

    private final HoldingRepository holdingRepository;
    private final TransactionRepository transactionRepository;
    private final PortfolioService portfolioService;

    public List<Holding> getHoldings(Long portfolioId) {
        portfolioService.getPortfolioById(portfolioId);
        return holdingRepository.findByPortfolioId(portfolioId);
    }

    public Holding addHolding(Long portfolioId, HoldingRequest request) {
        Portfolio portfolio = portfolioService.getPortfolioById(portfolioId);

        Holding holding = new Holding();
        holding.setStockSymbol(request.getStockSymbol().toUpperCase());
        holding.setStockName(request.getStockName());
        holding.setQuantity(request.getQuantity());
        holding.setBuyPrice(request.getBuyPrice());
        holding.setPortfolio(portfolio);

        holdingRepository.save(holding);

        Transaction tx = new Transaction();
        tx.setStockSymbol(request.getStockSymbol().toUpperCase());
        tx.setAction(Transaction.ActionType.BUY);
        tx.setQuantity(request.getQuantity());
        tx.setPrice(request.getBuyPrice());
        tx.setPortfolio(portfolio);
        transactionRepository.save(tx);

        log.info("BUY {} x {} at {} in portfolio {}",
                request.getQuantity(), request.getStockSymbol(),
                request.getBuyPrice(), portfolioId);
        return holding;
    }

    public void removeHolding(Long portfolioId, Long holdingId) {
        portfolioService.getPortfolioById(portfolioId);
        Holding holding = holdingRepository.findById(holdingId)
                .orElseThrow(() -> new RuntimeException("Holding not found"));

        Transaction tx = new Transaction();
        tx.setStockSymbol(holding.getStockSymbol());
        tx.setAction(Transaction.ActionType.SELL);
        tx.setQuantity(holding.getQuantity());
        tx.setPrice(holding.getBuyPrice());
        tx.setPortfolio(holding.getPortfolio());
        transactionRepository.save(tx);

        holdingRepository.delete(holding);
        log.info("SELL holding {} from portfolio {}", holdingId, portfolioId);
    }

    public List<Transaction> getTransactions(Long portfolioId) {
        portfolioService.getPortfolioById(portfolioId);
        return transactionRepository.findByPortfolioIdOrderByExecutedAtDesc(portfolioId);
    }
}
