package com.accepted.sports_betting.exception;

public class MatchNotFoundException extends RuntimeException{
    public MatchNotFoundException(long matchId) {
        super(String.format("Match not found with id %d",matchId));
    }
}
