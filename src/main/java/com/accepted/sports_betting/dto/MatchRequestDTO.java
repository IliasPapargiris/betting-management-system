package com.accepted.sports_betting.dto;

import com.accepted.sports_betting.entity.enums.Sport;
import com.accepted.sports_betting.validation.DistinctTeams;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@DistinctTeams(message = "Team A and Team B must be distinct")
public class MatchRequestDTO {

    @NotBlank(message = "Description is required.")
    @Size(max = 255, message = "Description cannot exceed 255 characters.")
    private String description;

    @NotNull(message = "Match date is required.")
    @Future(message = "Match date must be in the future.")
    private LocalDate matchDate;

    @NotNull(message = "Match time is required.")
    private LocalTime matchTime;

    @NotBlank(message = "Team A is required.")
    @Size(max = 50, message = "Team A name cannot exceed 50 characters.")
    private String teamA;

    @NotBlank(message = "Team B is required.")
    @Size(max = 50, message = "Team B name cannot exceed 50 characters.")
    private String teamB;

    @NotNull(message = "Sport is required.")
    private Sport sport;
}
