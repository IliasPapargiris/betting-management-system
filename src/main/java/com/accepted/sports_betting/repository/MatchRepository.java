package com.accepted.sports_betting.repository;

import com.accepted.sports_betting.entity.Match;
import com.accepted.sports_betting.entity.enums.Sport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;


public interface MatchRepository extends JpaRepository<Match ,Long> {


    @Query(value = "SELECT CASE WHEN COUNT(m.id) > 0 THEN true ELSE false END " +
            "FROM match m " +
            "WHERE m.match_date = :matchDate AND m.sport = CAST(:sport AS sport_enum) " +
            "AND (:teamA = m.team_a OR :teamB = m.team_b OR :teamA = m.team_b OR :teamB = m.team_a)",
            nativeQuery = true)
    boolean conflictedMatchExists(@Param("matchDate") LocalDate matchDate,
                                               @Param("sport") String sport,
                                               @Param("teamA") String teamA,
                                               @Param("teamB") String teamB);
}







