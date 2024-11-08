package com.accepted.sports_betting.repository;

import com.accepted.sports_betting.entity.MatchOdds;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface MatchOddsRepository extends JpaRepository<MatchOdds,Long> {

    List<MatchOdds> findByMatchId(Long matchId);



    @Query(value = "SELECT CASE WHEN COUNT(mo.id) > 0 THEN true ELSE false END " +
            "FROM match_odds mo " +
            "WHERE mo.match_id = :matchId AND mo.specifier = :specifier",
            nativeQuery = true)
    boolean existsByMatchAndSpecifier(@Param("matchId") Long matchId, @Param("specifier") String specifier);

}
