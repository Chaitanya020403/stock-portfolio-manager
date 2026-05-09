package com.stockportfolio.app.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PortfolioRequest {
    @NotBlank
    private String name;
}
