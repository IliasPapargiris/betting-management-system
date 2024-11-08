package com.accepted.sports_betting.fixture;

import com.accepted.sports_betting.dto.MatchRequestDTO;
import com.accepted.sports_betting.entity.enums.Sport;

import java.time.LocalDate;
import java.time.LocalTime;

public class MatchRequestDTOFixture {

    private static MatchRequestDTOFixture instance;

    private MatchRequestDTOFixture() {}

    public static MatchRequestDTOFixture getInstance() {
        if (instance == null) {
            instance = new MatchRequestDTOFixture();
        }
        return instance;
    }

    private static class MatchRequestDTOBuilder {
        private String description;
        private LocalDate matchDate;
        private LocalTime matchTime;
        private String teamA;
        private String teamB;
        private Sport sport;

        public MatchRequestDTOBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public MatchRequestDTOBuilder withMatchDate(LocalDate matchDate) {
            this.matchDate = matchDate;
            return this;
        }

        public MatchRequestDTOBuilder withMatchTime(LocalTime matchTime) {
            this.matchTime = matchTime;
            return this;
        }

        public MatchRequestDTOBuilder withTeamA(String teamA) {
            this.teamA = teamA;
            return this;
        }

        public MatchRequestDTOBuilder withTeamB(String teamB) {
            this.teamB = teamB;
            return this;
        }

        public MatchRequestDTOBuilder withSport(Sport sport) {
            this.sport = sport;
            return this;
        }

        public MatchRequestDTO build() {
            MatchRequestDTO matchRequestDTO = new MatchRequestDTO();
            matchRequestDTO.setDescription(this.description);
            matchRequestDTO.setMatchDate(this.matchDate);
            matchRequestDTO.setMatchTime(this.matchTime);
            matchRequestDTO.setTeamA(this.teamA);
            matchRequestDTO.setTeamB(this.teamB);
            matchRequestDTO.setSport(this.sport);
            return matchRequestDTO;
        }
    }

    public MatchRequestDTO getDerby() {
        return new MatchRequestDTOBuilder()
                .withDescription("DERBY")
                .withMatchDate(LocalDate.now().plusDays(7))
                .withMatchTime(LocalTime.of(20, 30))
                .withTeamA("OSFP")
                .withTeamB("PAO")
                .withSport(Sport.FOOTBALL)
                .build();
    }

    public MatchRequestDTO getSecondDerby() {
        return new MatchRequestDTOBuilder()
                .withDescription("DERBY 2")
                .withMatchDate(LocalDate.now().plusDays(7))
                .withMatchTime(LocalTime.of(21, 00))
                .withTeamA("AEK")
                .withTeamB("PAOK")
                .withSport(Sport.FOOTBALL)
                .build();
    }
}
