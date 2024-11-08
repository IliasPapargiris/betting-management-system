package com.accepted.sports_betting.mapper;

import com.accepted.sports_betting.dto.MatchRequestDTO;
import com.accepted.sports_betting.dto.MatchResponseDTO;
import com.accepted.sports_betting.entity.Match;

public final class MatchMapper {

    private MatchMapper() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static Match toEntity(MatchRequestDTO matchRequestDTO) {
        Match match = new Match();
        match.setDescription(matchRequestDTO.getDescription());
        match.setMatchDate(matchRequestDTO.getMatchDate());
        match.setMatchTime(matchRequestDTO.getMatchTime());
        match.setTeamA(matchRequestDTO.getTeamA());
        match.setTeamB(matchRequestDTO.getTeamB());
        match.setSport(matchRequestDTO.getSport());
        return match;
    }

    public static MatchResponseDTO toResponseDTO(Match match) {
        MatchResponseDTO responseDTO = new MatchResponseDTO();
        responseDTO.setId(match.getId());
        responseDTO.setDescription(match.getDescription());
        responseDTO.setMatchDate(match.getMatchDate());
        responseDTO.setMatchTime(match.getMatchTime());
        responseDTO.setTeamA(match.getTeamA());
        responseDTO.setTeamB(match.getTeamB());
        responseDTO.setSport(match.getSport());
        return responseDTO;
    }
}
