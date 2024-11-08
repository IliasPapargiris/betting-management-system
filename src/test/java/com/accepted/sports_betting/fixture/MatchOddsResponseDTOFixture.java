package com.accepted.sports_betting.fixture;

import com.accepted.sports_betting.dto.MatchOddsResponseDTO;
import com.accepted.sports_betting.entity.enums.Specifier;

import java.math.BigDecimal;

public class MatchOddsResponseDTOFixture {

    private static MatchOddsResponseDTOFixture instance;

    private MatchOddsResponseDTOFixture() {}

    public static MatchOddsResponseDTOFixture getInstance() {
        if (instance == null) {
            instance = new MatchOddsResponseDTOFixture();
        }
        return instance;
    }

    private static class MatchOddsResponseDTOBuilder {
        private Long id;
        private Long matchId;
        private Specifier specifier;
        private BigDecimal odd;

        public MatchOddsResponseDTOBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public MatchOddsResponseDTOBuilder withMatchId(Long matchId) {
            this.matchId = matchId;
            return this;
        }

        public MatchOddsResponseDTOBuilder withSpecifier(Specifier specifier) {
            this.specifier = specifier;
            return this;
        }

        public MatchOddsResponseDTOBuilder withOdd(BigDecimal odd) {
            this.odd = odd;
            return this;
        }

        public MatchOddsResponseDTO build() {
            MatchOddsResponseDTO matchOddsResponseDTO = new MatchOddsResponseDTO();
            matchOddsResponseDTO.setId(this.id);
            matchOddsResponseDTO.setMatchId(this.matchId);
            matchOddsResponseDTO.setSpecifier(this.specifier);
            matchOddsResponseDTO.setOdd(this.odd);
            return matchOddsResponseDTO;
        }
    }

    public MatchOddsResponseDTO getDerby() {
        return new MatchOddsResponseDTOBuilder()
                .withId(1L)
                .withMatchId(1L)
                .withSpecifier(Specifier.HOME_WIN)
                .withOdd(BigDecimal.valueOf(1.5))
                .build();
    }

    public MatchOddsResponseDTO getSecondDerby() {
        return new MatchOddsResponseDTOBuilder()
                .withId(2L)
                .withMatchId(2L)
                .withSpecifier(Specifier.HOME_WIN)
                .withOdd(BigDecimal.valueOf(1.5))
                .build();
    }
}
