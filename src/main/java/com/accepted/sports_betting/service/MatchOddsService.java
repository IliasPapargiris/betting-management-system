package com.accepted.sports_betting.service;

import com.accepted.sports_betting.dto.MatchOddsRequestDTO;
import com.accepted.sports_betting.dto.MatchOddsResponseDTO;
import com.accepted.sports_betting.entity.Match;
import com.accepted.sports_betting.entity.MatchOdds;
import com.accepted.sports_betting.exception.MatchNotFoundException;
import com.accepted.sports_betting.exception.MatchOddsConflictException;
import com.accepted.sports_betting.exception.MatchOddsNotFoundException;
import com.accepted.sports_betting.mapper.MatchOddsMapper;
import com.accepted.sports_betting.repository.MatchOddsRepository;
import com.accepted.sports_betting.repository.MatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchOddsService {

    private final MatchRepository matchRepository;
    private final MatchOddsRepository matchOddsRepository;

    @Transactional(readOnly = true)
    public List<MatchOddsResponseDTO> getMatchOddsByMatchId(Long matchId) {
        if (!matchRepository.existsById(matchId)) {
            throw new MatchNotFoundException(matchId);
        }

        return matchOddsRepository.findByMatchId(matchId)
                .stream()
                .map(MatchOddsMapper::toResponseDTO).collect(Collectors.toList());
    }

    @Transactional
    public MatchOddsResponseDTO createMatchOdds(MatchOddsRequestDTO requestDTO) {
        Match match = matchRepository.findById(requestDTO.getMatchId())
                .orElseThrow(() -> new MatchNotFoundException(requestDTO.getMatchId()));

        checkForMatchOddsConflict(match, requestDTO);

        MatchOdds matchOdds = MatchOddsMapper.toEntity(requestDTO);
        matchOdds.setMatch(match);

        MatchOdds savedOdds = matchOddsRepository.save(matchOdds);
        return MatchOddsMapper.toResponseDTO(savedOdds);
    }

    @Transactional
    public MatchOddsResponseDTO updateMatchOdds(Long id, MatchOddsRequestDTO requestDTO) {
        MatchOdds matchOdds = matchOddsRepository.findById(id)
                .orElseThrow(() -> new MatchOddsNotFoundException(id));

        Match match = matchRepository.findById(requestDTO.getMatchId())
                .orElseThrow(() -> new MatchNotFoundException(requestDTO.getMatchId()));


        checkForMatchOddsConflictOnUpdate(matchOdds, requestDTO);

        matchOdds.setMatch(match);
        matchOdds.setSpecifier(requestDTO.getSpecifier());
        matchOdds.setOdd(requestDTO.getOdd());

        // Save the updated MatchOdds entity
        MatchOdds updatedOdds = matchOddsRepository.save(matchOdds);
        return MatchOddsMapper.toResponseDTO(updatedOdds);
    }

    @Transactional
    public void deleteMatchOdds(Long id) {
        MatchOdds matchOdds = matchOddsRepository.findById(id)
                .orElseThrow(() -> new MatchOddsNotFoundException(id));
        matchOddsRepository.delete(matchOdds);
    }


    private void checkForMatchOddsConflict(Match match, MatchOddsRequestDTO requestDTO) {
        if (matchOddsRepository.existsByMatchAndSpecifier(match.getId(), requestDTO.getSpecifier().name())) {
            throw new MatchOddsConflictException(match.getId(), requestDTO.getSpecifier());
        }
    }

    private void checkForMatchOddsConflictOnUpdate(MatchOdds existingMatchOdds, MatchOddsRequestDTO requestDTO) {
        boolean isConflict = matchOddsRepository.existsByMatchAndSpecifier(
                requestDTO.getMatchId(), requestDTO.getSpecifier().name());

        // If there's a conflict & it's not the same record do not allow the update
        if (isConflict && !(existingMatchOdds.getMatch().getId().equals(requestDTO.getMatchId()) &&
                existingMatchOdds.getSpecifier().equals(requestDTO.getSpecifier()))) {
            throw new MatchOddsConflictException(requestDTO.getMatchId(), requestDTO.getSpecifier());
        }

    }

}
