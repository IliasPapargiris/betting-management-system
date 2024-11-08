package com.accepted.sports_betting.exception;

public class MatchOddsNotFoundException extends RuntimeException {

    public MatchOddsNotFoundException(Long id) {
        super(String.format("Match Odds not found with ID %d", id));
    }
}