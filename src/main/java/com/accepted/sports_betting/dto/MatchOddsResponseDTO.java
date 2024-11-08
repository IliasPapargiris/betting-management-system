package com.accepted.sports_betting.dto;

import com.accepted.sports_betting.entity.enums.Specifier;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class MatchOddsResponseDTO {

    private Long id;
    private Long matchId;
    private Specifier specifier;
    private BigDecimal odd;

}
