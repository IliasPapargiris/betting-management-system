package com.accepted.sports_betting.service;

import com.accepted.sports_betting.dto.MatchOddsRequestDTO;
import com.accepted.sports_betting.dto.MatchOddsResponseDTO;
import com.accepted.sports_betting.entity.Match;
import com.accepted.sports_betting.entity.MatchOdds;
import com.accepted.sports_betting.exception.MatchNotFoundException;
import com.accepted.sports_betting.exception.MatchOddsConflictException;
import com.accepted.sports_betting.exception.MatchOddsNotFoundException;
import com.accepted.sports_betting.fixture.MatchFixture;
import com.accepted.sports_betting.fixture.MatchOddsFixture;
import com.accepted.sports_betting.fixture.MatchOddsRequestDTOFixture;
import com.accepted.sports_betting.mapper.MatchOddsMapper;
import com.accepted.sports_betting.repository.MatchOddsRepository;
import com.accepted.sports_betting.repository.MatchRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = MatchOddsService.class)
class MatchOddsServiceTest {

    @Autowired
    private MatchOddsService matchOddsService;

    @MockBean
    private MatchRepository matchRepository;

    @MockBean
    private MatchOddsRepository matchOddsRepository;

    @Test
    void testGetMatchOddsByMatchIdSuccessfully() {
        // Given
        Long matchId = 1L;
        MatchOdds matchOdds1 = MatchOddsFixture.getInstance().getDerbyOdds();
        MatchOdds matchOdds2 = MatchOddsFixture.getInstance().getSecondDerbyOdds();
        List<MatchOdds> matchOddsList = List.of(matchOdds1, matchOdds2);

        when(matchRepository.existsById(matchId)).thenReturn(true);
        when(matchOddsRepository.findByMatchId(matchId)).thenReturn(matchOddsList);

        // When
        List<MatchOddsResponseDTO> result = matchOddsService.getMatchOddsByMatchId(matchId);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(matchRepository, times(1)).existsById(matchId);
        verify(matchOddsRepository, times(1)).findByMatchId(matchId);
    }

    @Test
    void testGetMatchOddsByMatchIdNotFound() {
        // Given
        Long matchId = 999L;
        when(matchRepository.existsById(matchId)).thenReturn(false);

        // When & Then
        assertThrows(MatchNotFoundException.class, () -> matchOddsService.getMatchOddsByMatchId(matchId));
        verify(matchRepository, times(1)).existsById(matchId);
        verify(matchOddsRepository, never()).findByMatchId(matchId);
    }

    @Test
    void testCreateMatchOddsSuccessfully() {
        // Given
        MatchOddsRequestDTO requestDTO = MatchOddsRequestDTOFixture.getInstance().getDerby();
        Match match = MatchFixture.getInstance().getDerby();
        MatchOdds matchOdds = MatchOddsMapper.toEntity(requestDTO);
        matchOdds.setMatch(match);

        when(matchRepository.findById(requestDTO.getMatchId())).thenReturn(Optional.of(match));
        when(matchOddsRepository.save(any(MatchOdds.class))).thenReturn(matchOdds);

        // When
        MatchOddsResponseDTO result = matchOddsService.createMatchOdds(requestDTO);

        // Then
        assertNotNull(result);
        assertEquals(match.getId(), result.getMatchId());
        verify(matchRepository, times(1)).findById(requestDTO.getMatchId());
        verify(matchOddsRepository, times(1)).save(any(MatchOdds.class));
    }

    @Test
    void testCreateMatchOddsConflict() {
        // Given
        MatchOddsRequestDTO requestDTO = MatchOddsRequestDTOFixture.getInstance().getDerby();
        Match match = MatchFixture.getInstance().getDerby();

        when(matchRepository.findById(requestDTO.getMatchId())).thenReturn(Optional.of(match));
        when(matchOddsRepository.existsByMatchAndSpecifier(match.getId(), requestDTO.getSpecifier().name())).thenReturn(true);

        // When & Then
        assertThrows(MatchOddsConflictException.class, () -> matchOddsService.createMatchOdds(requestDTO));
        verify(matchRepository, times(1)).findById(requestDTO.getMatchId());
        verify(matchOddsRepository, times(1)).existsByMatchAndSpecifier(match.getId(), requestDTO.getSpecifier().name());
        verify(matchOddsRepository, never()).save(any(MatchOdds.class));
    }

    @Test
    void testUpdateMatchOddsSuccessfully() {
        // Given
        Long matchOddsId = 1L;
        MatchOddsRequestDTO requestDTO = MatchOddsRequestDTOFixture.getInstance().getDerby();
        Match match = MatchFixture.getInstance().getDerby();
        MatchOdds matchOdds = new MatchOdds();
        matchOdds.setId(matchOddsId);

        when(matchOddsRepository.findById(matchOddsId)).thenReturn(Optional.of(matchOdds));
        when(matchRepository.findById(requestDTO.getMatchId())).thenReturn(Optional.of(match));
        when(matchOddsRepository.save(any(MatchOdds.class))).thenReturn(matchOdds);

        // When
        MatchOddsResponseDTO result = matchOddsService.updateMatchOdds(matchOddsId, requestDTO);

        // Then
        assertNotNull(result);
        assertEquals(matchOdds.getId(), result.getId());
        verify(matchOddsRepository, times(1)).findById(matchOddsId);
        verify(matchRepository, times(1)).findById(requestDTO.getMatchId());
        verify(matchOddsRepository, times(1)).save(any(MatchOdds.class));
    }

    @Test
    void testUpdateMatchOddsNotFound() {
        // Given
        Long matchOddsId = 999L;
        MatchOddsRequestDTO requestDTO = MatchOddsRequestDTOFixture.getInstance().getDerby();

        when(matchOddsRepository.findById(matchOddsId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(MatchOddsNotFoundException.class, () -> matchOddsService.updateMatchOdds(matchOddsId, requestDTO));
        verify(matchOddsRepository, times(1)).findById(matchOddsId);
    }

    @Test
    void testDeleteMatchOddsSuccessfully() {
        // Given
        Long matchOddsId = 1L;
        MatchOdds matchOdds = new MatchOdds();
        matchOdds.setId(matchOddsId);

        when(matchOddsRepository.findById(matchOddsId)).thenReturn(Optional.of(matchOdds));
        doNothing().when(matchOddsRepository).delete(matchOdds);

        // When
        matchOddsService.deleteMatchOdds(matchOddsId);

        // Then
        verify(matchOddsRepository, times(1)).findById(matchOddsId);
        verify(matchOddsRepository, times(1)).delete(matchOdds);
    }

    @Test
    void testDeleteMatchOddsNotFound() {
        // Given
        Long matchOddsId = 999L;
        when(matchOddsRepository.findById(matchOddsId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(MatchOddsNotFoundException.class, () -> matchOddsService.deleteMatchOdds(matchOddsId));
        verify(matchOddsRepository, times(1)).findById(matchOddsId);
    }
}
