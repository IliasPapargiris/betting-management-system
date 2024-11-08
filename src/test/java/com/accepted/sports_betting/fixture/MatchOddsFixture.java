package com.accepted.sports_betting.fixture;

import com.accepted.sports_betting.entity.Match;
import com.accepted.sports_betting.entity.MatchOdds;
import com.accepted.sports_betting.entity.enums.Specifier;

import java.math.BigDecimal;

public class MatchOddsFixture {

    private static MatchOddsFixture instance;

    private MatchOddsFixture() {}

    public static MatchOddsFixture getInstance() {
        if (instance == null) {
            instance = new MatchOddsFixture();
        }
        return instance;
    }

    private static class MatchOddsBuilder {
        private Long id;
        private Match match;
        private Specifier specifier;
        private BigDecimal odd;

        public MatchOddsBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public MatchOddsBuilder withMatch(Match match) {
            this.match = match;
            return this;
        }

        public MatchOddsBuilder withSpecifier(Specifier specifier) {
            this.specifier = specifier;
            return this;
        }

        public MatchOddsBuilder withOdd(BigDecimal odd) {
            this.odd = odd;
            return this;
        }

        public MatchOdds build() {
            MatchOdds matchOdds = new MatchOdds();
            matchOdds.setId(this.id);
            matchOdds.setMatch(this.match);
            matchOdds.setSpecifier(this.specifier);
            matchOdds.setOdd(this.odd);
            return matchOdds;
        }
    }

    public MatchOdds getDerbyOdds() {
        return new MatchOddsBuilder()
                .withId(1L)
                .withMatch(MatchFixture.getInstance().getDerby())
                .withSpecifier(Specifier.HOME_WIN)
                .withOdd(BigDecimal.valueOf(1.50))
                .build();
    }

    public MatchOdds getSecondDerbyOdds() {
        return new MatchOddsBuilder()
                .withId(2L)
                .withMatch(MatchFixture.getInstance().getDerby())
                .withSpecifier(Specifier.HOME_WIN)
                .withOdd(BigDecimal.valueOf(1.50))
                .build();
    }
}