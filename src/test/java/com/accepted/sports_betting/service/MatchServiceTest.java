package com.accepted.sports_betting.service;

import com.accepted.sports_betting.dto.MatchRequestDTO;
import com.accepted.sports_betting.dto.MatchResponseDTO;
import com.accepted.sports_betting.entity.Match;
import com.accepted.sports_betting.exception.MatchConflictException;
import com.accepted.sports_betting.exception.MatchNotFoundException;
import com.accepted.sports_betting.fixture.MatchFixture;
import com.accepted.sports_betting.fixture.MatchRequestDTOFixture;
import com.accepted.sports_betting.mapper.MatchMapper;
import com.accepted.sports_betting.repository.MatchRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = MatchService.class)
class MatchServiceTest {

    @Autowired
    private MatchService matchService;

    @MockBean
    private MatchRepository matchRepository;

    @Test
    void testFindMatchByIdSuccessfully() {
        // Given
        Long matchId = 1L;
        Match match = MatchFixture.getInstance().getDerby();
        when(matchRepository.findById(matchId)).thenReturn(Optional.of(match));

        // When
        MatchResponseDTO result = matchService.findMatchById(matchId);

        // Then
        assertNotNull(result);
        assertEquals(match.getId(), result.getId());
        verify(matchRepository, times(1)).findById(matchId);
    }

    @Test
    void testFindMatchByIdNotFound() {
        // Given
        Long matchId = 999L;
        when(matchRepository.findById(matchId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(MatchNotFoundException.class, () -> matchService.findMatchById(matchId));
        verify(matchRepository, times(1)).findById(matchId);
    }

    @Test
    void testCreateMatchSuccessfully() {
        // Given
        MatchRequestDTO requestDTO = MatchRequestDTOFixture.getInstance().getDerby();
        Match match = MatchMapper.toEntity(requestDTO);
        when(matchRepository.conflictedMatchExists(match.getMatchDate(),match.getSport().name(),match.getTeamA(), match.getTeamB())).thenReturn(false);
        when(matchRepository.save(any(Match.class))).thenReturn(match);

        // When
        MatchResponseDTO result = matchService.createMatch(requestDTO);

        // Then
        assertNotNull(result);
        assertEquals(match.getId(), result.getId());
        verify(matchRepository, times(1)).conflictedMatchExists(match.getMatchDate(),match.getSport().name(),match.getTeamA(), match.getTeamB());
        verify(matchRepository, times(1)).save(any(Match.class));
    }

    @Test
    void testCreateMatchWithConflict() {
        // Given
        MatchRequestDTO requestDTO = MatchRequestDTOFixture.getInstance().getDerby();
        Match match = MatchMapper.toEntity(requestDTO);
        when(matchRepository.conflictedMatchExists(match.getMatchDate(),match.getSport().name(),match.getTeamA(), match.getTeamB())).thenReturn(true);

        // When & Then
        assertThrows(MatchConflictException.class, () -> matchService.createMatch(requestDTO));
        verify(matchRepository, times(1)).conflictedMatchExists(match.getMatchDate(),match.getSport().name(),match.getTeamA(), match.getTeamB());
        verify(matchRepository, never()).save(any(Match.class));
    }

    @Test
    void testUpdateMatchSuccessfully() {
        // Given
        Long matchId = 1L;
        MatchRequestDTO updateDTO = MatchRequestDTOFixture.getInstance().getDerby();
        Match match = MatchFixture.getInstance().getDerby();

        when(matchRepository.findById(matchId)).thenReturn(Optional.of(match));
        when(matchRepository.save(any(Match.class))).thenReturn(match);

        // When
        MatchResponseDTO result = matchService.updateMatch(matchId, updateDTO);

        // Then
        assertNotNull(result);
        assertEquals(match.getId(), result.getId());
        verify(matchRepository, times(1)).findById(matchId);
        verify(matchRepository, times(1)).save(any(Match.class));
    }

    @Test
    void testUpdateMatchNotFound() {
        // Given
        Long matchId = 999L;
        MatchRequestDTO updateDTO = MatchRequestDTOFixture.getInstance().getDerby();
        when(matchRepository.findById(matchId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(MatchNotFoundException.class, () -> matchService.updateMatch(matchId, updateDTO));
        verify(matchRepository, times(1)).findById(matchId);
    }

    @Test
    void testDeleteMatchSuccessfully() {
        // Given
        Long matchId = 1L;
        Match match = MatchFixture.getInstance().getDerby();
        when(matchRepository.findById(matchId)).thenReturn(Optional.of(match));
        doNothing().when(matchRepository).delete(match);

        // When
        matchService.deleteMatch(matchId);

        // Then
        verify(matchRepository, times(1)).findById(matchId);
        verify(matchRepository, times(1)).delete(match);
    }

    @Test
    void testDeleteMatchNotFound() {
        // Given
        Long matchId = 999L;
        when(matchRepository.findById(matchId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(MatchNotFoundException.class, () -> matchService.deleteMatch(matchId));
        verify(matchRepository, times(1)).findById(matchId);
    }
}
