package com.accepted.sports_betting.fixture;

import com.accepted.sports_betting.dto.MatchResponseDTO;
import com.accepted.sports_betting.entity.enums.Sport;

import java.time.LocalDate;
import java.time.LocalTime;

public class MatchResponseDTOFixture {

    private static MatchResponseDTOFixture instance;

    private MatchResponseDTOFixture() {}

    public static MatchResponseDTOFixture getInstance() {
        if (instance == null) {
            instance = new MatchResponseDTOFixture();
        }
        return instance;
    }

    private static class MatchResponseDTOBuilder {
        private Long id;
        private String description;
        private LocalDate matchDate;
        private LocalTime matchTime;
        private String teamA;
        private String teamB;
        private Sport sport;

        public MatchResponseDTOBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public MatchResponseDTOBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public MatchResponseDTOBuilder withMatchDate(LocalDate matchDate) {
            this.matchDate = matchDate;
            return this;
        }

        public MatchResponseDTOBuilder withMatchTime(LocalTime matchTime) {
            this.matchTime = matchTime;
            return this;
        }

        public MatchResponseDTOBuilder withTeamA(String teamA) {
            this.teamA = teamA;
            return this;
        }

        public MatchResponseDTOBuilder withTeamB(String teamB) {
            this.teamB = teamB;
            return this;
        }

        public MatchResponseDTOBuilder withSport(Sport sport) {
            this.sport = sport;
            return this;
        }

        public MatchResponseDTO build() {
            MatchResponseDTO matchResponseDTO = new MatchResponseDTO();
            matchResponseDTO.setId(this.id);
            matchResponseDTO.setDescription(this.description);
            matchResponseDTO.setMatchDate(this.matchDate);
            matchResponseDTO.setMatchTime(this.matchTime);
            matchResponseDTO.setTeamA(this.teamA);
            matchResponseDTO.setTeamB(this.teamB);
            matchResponseDTO.setSport(this.sport);
            return matchResponseDTO;
        }
    }

    public MatchResponseDTO getDerby() {
        return new MatchResponseDTOBuilder()
                .withId(1L)
                .withDescription("DERBY")
                .withMatchDate(LocalDate.now().plusDays(7))
                .withMatchTime(LocalTime.of(20, 30))
                .withTeamA("OSFP")
                .withTeamB("PAO")
                .withSport(Sport.FOOTBALL)
                .build();
    }

    public MatchResponseDTO getSecondDerby() {
        return new MatchResponseDTOBuilder()
                .withId(2L)
                .withDescription("DERBY 2")
                .withMatchDate(LocalDate.now().plusDays(7))
                .withMatchTime(LocalTime.of(21, 00))
                .withTeamA("AEK")
                .withTeamB("PAOK")
                .withSport(Sport.FOOTBALL)
                .build();
    }
}
