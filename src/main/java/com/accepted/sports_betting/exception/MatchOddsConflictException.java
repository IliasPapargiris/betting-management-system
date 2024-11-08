package com.accepted.sports_betting.exception;

import com.accepted.sports_betting.entity.enums.Specifier;

public class MatchOddsConflictException extends RuntimeException {

    public MatchOddsConflictException(Long matchId, Specifier specifier) {
        super(String.format("Odds with specifier '%s' already exist for match ID %d.", specifier, matchId));
    }
}
