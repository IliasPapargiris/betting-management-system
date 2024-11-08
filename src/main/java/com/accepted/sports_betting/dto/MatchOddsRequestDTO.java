package com.accepted.sports_betting.dto;

import com.accepted.sports_betting.entity.enums.Specifier;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class MatchOddsRequestDTO {

    @NotNull(message = "Match ID is required.")
    private Long matchId;

    @NotNull(message = "Specifier is required.")
    private Specifier specifier;

    @NotNull(message = "Odd is required.")
    @DecimalMin(value = "0.01", message = "Odd must be greater than zero.")
    private BigDecimal odd;
}
