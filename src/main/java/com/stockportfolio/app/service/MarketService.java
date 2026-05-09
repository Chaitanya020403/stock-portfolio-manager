package com.stockportfolio.app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class MarketService {

    @Value("${finnhub.api.key}")
    private String apiKey;

    @Value("${finnhub.base.url}")
    private String baseUrl;

    private final RestTemplate restTemplate;

    @SuppressWarnings("unchecked")
    public Map<String, Object> getStockQuote(String symbol) {
        String url = baseUrl + "/quote?symbol=" + symbol.toUpperCase() + "&token=" + apiKey;
        log.info("Fetching price for symbol: {}", symbol);
        try {
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            Map<String, Object> result = new HashMap<>();
            if (response != null && response.get("c") != null) {
                result.put("symbol", symbol.toUpperCase());
                result.put("currentPrice", response.get("c"));
                result.put("highPrice", response.get("h"));
                result.put("lowPrice", response.get("l"));
                result.put("openPrice", response.get("o"));
                result.put("previousClose", response.get("pc"));
                result.put("change", response.get("d"));
                result.put("percentChange", response.get("dp"));
            }
            return result;
        } catch (Exception e) {
            log.error("Error fetching stock data for {}: {}", symbol, e.getMessage());
            return Map.of("error", "Could not fetch data for " + symbol);
        }
    }
}
