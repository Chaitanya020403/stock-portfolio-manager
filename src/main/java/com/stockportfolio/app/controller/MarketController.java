package com.stockportfolio.app.controller;

import com.stockportfolio.app.service.MarketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/market")
@RequiredArgsConstructor
public class MarketController {

    private final MarketService marketService;

    @GetMapping
    public String marketPage(Model model) {
        return "market/search";
    }

    @GetMapping("/search")
    public String searchStock(@RequestParam String symbol, Model model) {
        model.addAttribute("quote", marketService.getStockQuote(symbol));
        model.addAttribute("symbol", symbol.toUpperCase());
        return "market/search";
    }
}
