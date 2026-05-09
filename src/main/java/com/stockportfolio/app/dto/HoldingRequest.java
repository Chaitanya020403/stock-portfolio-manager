package com.stockportfolio.app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class HoldingRequest {
    @NotBlank
    private String stockSymbol;

    private String stockName;

    @Positive
    private Integer quantity;

    @Positive
    private BigDecimal buyPrice;
}
