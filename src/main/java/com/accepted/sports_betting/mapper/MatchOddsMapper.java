package com.accepted.sports_betting.mapper;

import com.accepted.sports_betting.dto.MatchOddsRequestDTO;
import com.accepted.sports_betting.dto.MatchOddsResponseDTO;
import com.accepted.sports_betting.entity.MatchOdds;

public final class MatchOddsMapper {

    private MatchOddsMapper(){
        throw new UnsupportedOperationException("Utility class");
    }

    public static MatchOddsResponseDTO toResponseDTO(MatchOdds matchOdds) {
        MatchOddsResponseDTO responseDTO = new MatchOddsResponseDTO();
        responseDTO.setId(matchOdds.getId());
        responseDTO.setMatchId(matchOdds.getMatch().getId());
        responseDTO.setSpecifier(matchOdds.getSpecifier());
        responseDTO.setOdd(matchOdds.getOdd());
        return responseDTO;
    }

    public static MatchOdds toEntity(MatchOddsRequestDTO requestDTO) {
        MatchOdds matchOdds = new MatchOdds();
        matchOdds.setSpecifier(requestDTO.getSpecifier());
        matchOdds.setOdd(requestDTO.getOdd());
        return matchOdds;
    }
}
