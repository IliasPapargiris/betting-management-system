package com.accepted.sports_betting.entity;


import com.accepted.sports_betting.entity.enums.Specifier;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "match_odds",
        uniqueConstraints = @UniqueConstraint(columnNames = {"match_id", "specifier"}))
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchOdds {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id", nullable = false)
    @ToString.Exclude
    private Match match;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Specifier specifier;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal odd;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MatchOdds matchOdds = (MatchOdds) o;
        return Objects.equals(id, matchOdds.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
