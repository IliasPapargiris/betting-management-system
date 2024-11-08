package com.accepted.sports_betting.fixture;

import com.accepted.sports_betting.entity.Match;
import com.accepted.sports_betting.entity.enums.Sport;

import java.time.LocalDate;
import java.time.LocalTime;

public class MatchFixture {

    private static MatchFixture instance;

    private MatchFixture() {}

    public static MatchFixture getInstance() {
        if (instance == null) {
            instance = new MatchFixture();
        }
        return instance;
    }

    private static class MatchBuilder {
        private Long id;
        private String description;
        private LocalDate matchDate;
        private LocalTime matchTime;
        private String teamA;
        private String teamB;
        private Sport sport;

        public MatchBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public MatchBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public MatchBuilder withMatchDate(LocalDate matchDate) {
            this.matchDate = matchDate;
            return this;
        }

        public MatchBuilder withMatchTime(LocalTime matchTime) {
            this.matchTime = matchTime;
            return this;
        }

        public MatchBuilder withTeamA(String teamA) {
            this.teamA = teamA;
            return this;
        }

        public MatchBuilder withTeamB(String teamB) {
            this.teamB = teamB;
            return this;
        }

        public MatchBuilder withSport(Sport sport) {
            this.sport = sport;
            return this;
        }

        public Match build() {
            Match match = new Match();
            match.setId(this.id);
            match.setDescription(this.description);
            match.setMatchDate(this.matchDate);
            match.setMatchTime(this.matchTime);
            match.setTeamA(this.teamA);
            match.setTeamB(this.teamB);
            match.setSport(this.sport);
            return match;
        }
    }

    public Match getDerby() {
        return new MatchBuilder()
                .withId(1L)
                .withDescription("DERBY")
                .withMatchDate(LocalDate.now())
                .withMatchTime(LocalTime.now())
                .withTeamA("OSFP")
                .withTeamB("PAO")
                .withSport(Sport.FOOTBALL)
                .build();
    }

    public Match getSecondDerby() {
        return new MatchBuilder()
                .withId(2L)
                .withDescription("DERBY 2")
                .withMatchDate(LocalDate.now().plusDays(1))
                .withMatchTime(LocalTime.now())
                .withTeamA("AEK")
                .withTeamB("PAOK")
                .withSport(Sport.FOOTBALL)
                .build();
    }
}