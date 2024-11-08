package com.accepted.sports_betting.dto;

import com.accepted.sports_betting.entity.enums.Sport;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class MatchResponseDTO {

    private Long id;

    private String description;

    private LocalDate matchDate;

    private LocalTime matchTime;

    private String teamA;

    private String teamB;

    private Sport sport;
}
