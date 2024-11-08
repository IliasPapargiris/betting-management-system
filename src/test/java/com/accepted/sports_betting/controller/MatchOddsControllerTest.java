package com.accepted.sports_betting.controller;

import com.accepted.sports_betting.dto.MatchOddsRequestDTO;
import com.accepted.sports_betting.dto.MatchOddsResponseDTO;
import com.accepted.sports_betting.entity.enums.Specifier;
import com.accepted.sports_betting.exception.MatchNotFoundException;
import com.accepted.sports_betting.exception.MatchOddsConflictException;
import com.accepted.sports_betting.exception.MatchOddsNotFoundException;
import com.accepted.sports_betting.fixture.MatchOddsRequestDTOFixture;
import com.accepted.sports_betting.fixture.MatchOddsResponseDTOFixture;
import com.accepted.sports_betting.service.MatchOddsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class MatchOddsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MatchOddsService matchOddsService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetMatchOddsSuccessfully() throws Exception {
        // Given
        Long matchId = 1L;
        MatchOddsResponseDTO oddsResponse1 = MatchOddsResponseDTOFixture.getInstance().getDerby();
        MatchOddsResponseDTO oddsResponse2 = MatchOddsResponseDTOFixture.getInstance().getSecondDerby();
        when(matchOddsService.getMatchOddsByMatchId(matchId)).thenReturn(List.of(oddsResponse1, oddsResponse2));

        // When & Then
        mockMvc.perform(get("/api/odds/matches/{matchId}", matchId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(oddsResponse1.getId()))
                .andExpect(jsonPath("$[1].id").value(oddsResponse2.getId()));

        verify(matchOddsService, times(1)).getMatchOddsByMatchId(matchId);
    }

    @Test
    void testGetMatchOddsNotFound() throws Exception {
        // Given
        Long matchId = 999L;
        when(matchOddsService.getMatchOddsByMatchId(matchId)).thenThrow(new MatchNotFoundException(matchId));

        // When & Then
        mockMvc.perform(get("/api/odds/matches/{matchId}", matchId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Match Not Found"))
                .andExpect(jsonPath("$.message").value("Match not found with id " + matchId));

        verify(matchOddsService, times(1)).getMatchOddsByMatchId(matchId);
    }

    @Test
    void testCreateMatchOddsSuccessfully() throws Exception {
        // Given
        MatchOddsRequestDTO requestDTO = MatchOddsRequestDTOFixture.getInstance().getDerby();
        MatchOddsResponseDTO responseDTO = MatchOddsResponseDTOFixture.getInstance().getSecondDerby();
        when(matchOddsService.createMatchOdds(any(MatchOddsRequestDTO.class))).thenReturn(responseDTO);

        // When & Then
        mockMvc.perform(post("/api/odds")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(responseDTO.getId()))
                .andExpect(jsonPath("$.odd").value(responseDTO.getOdd().toString()));

        verify(matchOddsService, times(1)).createMatchOdds(any(MatchOddsRequestDTO.class));
    }

    @Test
    void testCreateMatchOddsConflict() throws Exception {
        // Given
        MatchOddsRequestDTO requestDTO = MatchOddsRequestDTOFixture.getInstance().getDerby();
        doThrow(new MatchOddsConflictException(1L, requestDTO.getSpecifier()))
                .when(matchOddsService).createMatchOdds(any(MatchOddsRequestDTO.class));

        // When & Then
        mockMvc.perform(post("/api/odds")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value("Conflict"))
                .andExpect(jsonPath("$.message").value("Odds with specifier '"+ Specifier.HOME_WIN +"' already exist for match ID "+requestDTO.getMatchId()+"."));

        verify(matchOddsService, times(1)).createMatchOdds(any(MatchOddsRequestDTO.class));
    }

    @Test
    void testUpdateMatchOddsSuccessfully() throws Exception {
        // Given
        Long oddsId = 1L;
        MatchOddsRequestDTO requestDTO = MatchOddsRequestDTOFixture.getInstance().getDerby();
        MatchOddsResponseDTO responseDTO = MatchOddsResponseDTOFixture.getInstance().getSecondDerby();
        when(matchOddsService.updateMatchOdds(eq(oddsId), any(MatchOddsRequestDTO.class))).thenReturn(responseDTO);

        // When & Then
        mockMvc.perform(put("/api/odds/{id}", oddsId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(responseDTO.getId()))
                .andExpect(jsonPath("$.odd").value(responseDTO.getOdd().toString()));

        verify(matchOddsService, times(1)).updateMatchOdds(eq(oddsId), any(MatchOddsRequestDTO.class));
    }

    @Test
    void testUpdateMatchOddsNotFound() throws Exception {
        // Given
        Long oddsId = 999L;
        MatchOddsRequestDTO requestDTO = MatchOddsRequestDTOFixture.getInstance().getDerby();
        when(matchOddsService.updateMatchOdds(eq(oddsId), any(MatchOddsRequestDTO.class)))
                .thenThrow(new MatchOddsNotFoundException(oddsId));

        // When & Then
        mockMvc.perform(put("/api/odds/{id}", oddsId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Match Odds Not Found"))
                .andExpect(jsonPath("$.message").value("Match Odds not found with ID " + oddsId));

        verify(matchOddsService, times(1)).updateMatchOdds(eq(oddsId), any(MatchOddsRequestDTO.class));
    }

    @Test
    void testDeleteMatchOddsSuccessfully() throws Exception {
        // Given
        Long oddsId = 1L;
        doNothing().when(matchOddsService).deleteMatchOdds(oddsId);

        // When & Then
        mockMvc.perform(delete("/api/odds/{id}", oddsId))
                .andExpect(status().isNoContent());

        verify(matchOddsService, times(1)).deleteMatchOdds(oddsId);
    }

    @Test
    void testDeleteMatchOddsNotFound() throws Exception {
        // Given
        Long oddsId = 999L;
        doThrow(new MatchOddsNotFoundException(oddsId)).when(matchOddsService).deleteMatchOdds(oddsId);

        // When & Then
        mockMvc.perform(delete("/api/odds/{id}", oddsId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Match Odds Not Found"))
                .andExpect(jsonPath("$.message").value("Match Odds not found with ID " + oddsId));

        verify(matchOddsService, times(1)).deleteMatchOdds(oddsId);
    }
}