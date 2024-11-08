package com.accepted.sports_betting.exception;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MatchConflictException extends RuntimeException{

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public MatchConflictException(String teamA, String teamB, LocalDate matchDate) {
        super(String.format("A match is already scheduled for either team %s or %s on %s",
                teamA, teamB, matchDate.format(DATE_FORMATTER)));
    }
}
