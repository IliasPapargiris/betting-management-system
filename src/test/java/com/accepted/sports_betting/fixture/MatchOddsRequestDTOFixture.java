package com.accepted.sports_betting.fixture;

import com.accepted.sports_betting.dto.MatchOddsRequestDTO;
import com.accepted.sports_betting.entity.enums.Specifier;

import java.math.BigDecimal;

public class MatchOddsRequestDTOFixture {

    private static MatchOddsRequestDTOFixture instance;

    private MatchOddsRequestDTOFixture() {}

    public static MatchOddsRequestDTOFixture getInstance() {
        if (instance == null) {
            instance = new MatchOddsRequestDTOFixture();
        }
        return instance;
    }

    private static class MatchOddsRequestDTOBuilder {
        private Long matchId;
        private Specifier specifier;
        private BigDecimal odd;

        public MatchOddsRequestDTOBuilder withMatchId(Long matchId) {
            this.matchId = matchId;
            return this;
        }

        public MatchOddsRequestDTOBuilder withSpecifier(Specifier specifier) {
            this.specifier = specifier;
            return this;
        }

        public MatchOddsRequestDTOBuilder withOdd(BigDecimal odd) {
            this.odd = odd;
            return this;
        }

        public MatchOddsRequestDTO build() {
            MatchOddsRequestDTO matchOddsRequestDTO = new MatchOddsRequestDTO();
            matchOddsRequestDTO.setMatchId(this.matchId);
            matchOddsRequestDTO.setSpecifier(this.specifier);
            matchOddsRequestDTO.setOdd(this.odd);
            return matchOddsRequestDTO;
        }
    }

    public MatchOddsRequestDTO getDerby() {
        return new MatchOddsRequestDTOBuilder()
                .withMatchId(1L)
                .withSpecifier(Specifier.HOME_WIN)
                .withOdd(BigDecimal.valueOf(1.5))
                .build();
    }

    public MatchOddsRequestDTO getSecondDerby() {
        return new MatchOddsRequestDTOBuilder()
                .withMatchId(2L)
                .withSpecifier(Specifier.HOME_WIN)
                .withOdd(BigDecimal.valueOf(1.5))
                .build();
    }
}
