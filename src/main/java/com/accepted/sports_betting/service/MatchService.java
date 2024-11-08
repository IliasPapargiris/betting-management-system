package com.accepted.sports_betting.service;

import com.accepted.sports_betting.dto.MatchRequestDTO;
import com.accepted.sports_betting.dto.MatchResponseDTO;
import com.accepted.sports_betting.entity.Match;
import com.accepted.sports_betting.entity.enums.Sport;
import com.accepted.sports_betting.exception.MatchConflictException;
import com.accepted.sports_betting.exception.MatchNotFoundException;
import com.accepted.sports_betting.mapper.MatchMapper;
import com.accepted.sports_betting.repository.MatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;

    @Transactional(readOnly = true)
    public MatchResponseDTO findMatchById(Long id) {
        Match match = matchRepository.findById(id)
                .orElseThrow(() -> new MatchNotFoundException(id));

        return MatchMapper.toResponseDTO(match);
    }

    @Transactional
    public MatchResponseDTO createMatch(MatchRequestDTO matchRequestDTO) {
        Match match = MatchMapper.toEntity(matchRequestDTO);


        // Check if either team is already booked for the same sport on the same day
        checkTeamScheduleConflict(match);

        Match saved = matchRepository.save(match);
        return MatchMapper.toResponseDTO(saved);
    }

    public Page<MatchResponseDTO> getAllMatches(Pageable pageable) {
        Page<Match> matches = matchRepository.findAll(pageable);
        return matches.map(MatchMapper::toResponseDTO);
    }

    @Transactional
    public MatchResponseDTO updateMatch(Long id, MatchRequestDTO updateMatchDTO) {
        Match match = matchRepository.findById(id)
                .orElseThrow(() -> new MatchNotFoundException(id));

        match.setDescription(updateMatchDTO.getDescription());
        match.setMatchTime(updateMatchDTO.getMatchTime());

        // Check if the fields that could cause conflicts are changing
        if (isDateOrTeamsChanging(match, updateMatchDTO)) {
            boolean conflictExists = matchRepository.conflictedMatchExists(
                    updateMatchDTO.getMatchDate(),
                    updateMatchDTO.getSport().name(),
                    updateMatchDTO.getTeamA(),
                    updateMatchDTO.getTeamB());


            if (conflictExists) {
                throw new MatchConflictException(updateMatchDTO.getTeamA(), updateMatchDTO.getTeamB(), updateMatchDTO.getMatchDate());
            }

            match.setDescription(updateMatchDTO.getDescription());
            match.setMatchTime(updateMatchDTO.getMatchTime());
            match.setMatchDate(updateMatchDTO.getMatchDate());
            match.setTeamA(updateMatchDTO.getTeamA());
            match.setTeamB(updateMatchDTO.getTeamB());
            match.setSport(Sport.valueOf(updateMatchDTO.getSport().name()));
        }

        // Save the updated match
        Match savedMatch = matchRepository.save(match);
        return MatchMapper.toResponseDTO(savedMatch);
    }


    @Transactional
    public void deleteMatch(Long id) {
        Match match = matchRepository.findById(id)
                .orElseThrow(() -> new MatchNotFoundException(id));
        matchRepository.delete(match);
    }


    private void checkTeamScheduleConflict(Match match) {
        if (matchRepository.conflictedMatchExists(match.getMatchDate(), match.getSport().name(), match.getTeamA(), match.getTeamB())) {
            throw new MatchConflictException(match.getTeamA(), match.getTeamB(), match.getMatchDate());
        }
    }

    private boolean isDateOrTeamsChanging(Match match, MatchRequestDTO updateMatchDTO) {
        return !match.getMatchDate().equals(updateMatchDTO.getMatchDate()) ||
                !match.getTeamA().equals(updateMatchDTO.getTeamA()) ||
                !match.getTeamB().equals(updateMatchDTO.getTeamB());
    }

}