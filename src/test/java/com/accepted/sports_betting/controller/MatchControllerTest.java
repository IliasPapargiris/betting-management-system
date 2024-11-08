package com.accepted.sports_betting.controller;

import com.accepted.sports_betting.dto.MatchRequestDTO;
import com.accepted.sports_betting.dto.MatchResponseDTO;
import com.accepted.sports_betting.exception.MatchConflictException;
import com.accepted.sports_betting.exception.MatchNotFoundException;
import com.accepted.sports_betting.fixture.MatchRequestDTOFixture;
import com.accepted.sports_betting.fixture.MatchResponseDTOFixture;
import com.accepted.sports_betting.service.MatchService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class MatchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MatchService matchService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateMatchWithBlankDescription() throws Exception {
        // Given
        MatchRequestDTO requestDTO = MatchRequestDTOFixture.getInstance().getDerby();
        requestDTO.setDescription("");

        // When & Then
        mockMvc.perform(post("/api/matches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Validation Failed"))
                .andExpect(jsonPath("$.messages[0]").value("description: Description is required."));

        verify(matchService, never()).createMatch(any(MatchRequestDTO.class));
    }


    @Test
    void testCreateMatchWithNullDate() throws Exception {
        // Given
        MatchRequestDTO requestDTO = MatchRequestDTOFixture.getInstance().getDerby();
        requestDTO.setMatchDate(null); // Invalid null date

        // When & Then
        mockMvc.perform(post("/api/matches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Validation Failed"))
                .andExpect(jsonPath("$.messages[0]").value("matchDate: Match date is required."));

        verify(matchService, never()).createMatch(any(MatchRequestDTO.class));
    }

    @Test
    void testCreateMatchWithPastDate() throws Exception {
        // Given
        MatchRequestDTO requestDTO = MatchRequestDTOFixture.getInstance().getDerby();
        requestDTO.setMatchDate(LocalDate.now().minusDays(1)); // Invalid past date

        // When & Then
        mockMvc.perform(post("/api/matches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Validation Failed"))
                .andExpect(jsonPath("$.messages[0]").value("matchDate: Match date must be in the future."));


        verify(matchService, never()).createMatch(any(MatchRequestDTO.class));
    }


    @Test
    void testCreateMatchWithBlankTeamA() throws Exception {
        // Given
        MatchRequestDTO requestDTO = MatchRequestDTOFixture.getInstance().getDerby();
        requestDTO.setTeamA(""); // Invalid blank Team A

        // When & Then
        mockMvc.perform(post("/api/matches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Validation Failed"))
                .andExpect(jsonPath("$.messages[0]").value("teamA: Team A is required."));

        verify(matchService, never()).createMatch(any(MatchRequestDTO.class));
    }

    @Test
    void testCreateMatchWithBlankTeamB() throws Exception {
        // Given
        MatchRequestDTO requestDTO = MatchRequestDTOFixture.getInstance().getDerby();
        requestDTO.setTeamB(""); // Invalid blank Team B

        // When & Then
        mockMvc.perform(post("/api/matches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Validation Failed"))
                .andExpect(jsonPath("$.messages[0]").value("teamB: Team B is required."));

        verify(matchService, never()).createMatch(any(MatchRequestDTO.class));
    }


    @Test
    void testCreateMatchWithNullSport() throws Exception {
        // Given
        MatchRequestDTO requestDTO = MatchRequestDTOFixture.getInstance().getDerby();
        requestDTO.setSport(null); // Invalid null Sport

        // When & Then
        mockMvc.perform(post("/api/matches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Validation Failed"))
                .andExpect(jsonPath("$.messages[0]").value("sport: Sport is required."));

        verify(matchService, never()).createMatch(any(MatchRequestDTO.class));
    }

    @Test
    void testCreateMatchWithSameTeams() throws Exception {
        // Given
        MatchRequestDTO requestDTO = MatchRequestDTOFixture.getInstance().getDerby();
        requestDTO.setTeamA("OSFP");
        requestDTO.setTeamB("OSFP"); // Same team for Team A and Team B

        // When & Then
        mockMvc.perform(post("/api/matches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.messages[0]").value("Team A and Team B must be distinct"))
                .andExpect(jsonPath("$.error").value("Validation Failed"))
                .andExpect(jsonPath("$.status").value(400));
    }

    @Test
    void testCreateMatchWithMultipleConstraintViolations() throws Exception {
        // Given
        MatchRequestDTO requestDTO = MatchRequestDTOFixture.getInstance().getDerby();
        requestDTO.setTeamB("OSFP");
        requestDTO.setMatchTime(null);
        requestDTO.setMatchDate(requestDTO.getMatchDate().minusDays(100));
        requestDTO.setSport(null);
        requestDTO.setDescription(null);


        // When & Then
        mockMvc.perform(post("/api/matches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Validation Failed"))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.messages").isArray())
                .andExpect(jsonPath("$.messages", hasSize(5)))
                .andExpect(jsonPath("$.messages", hasItem("sport: Sport is required.")))
                .andExpect(jsonPath("$.messages", hasItem("matchTime: Match time is required.")))
                .andExpect(jsonPath("$.messages", hasItem("matchDate: Match date must be in the future.")))
                .andExpect(jsonPath("$.messages", hasItem("description: Description is required.")))
                .andExpect(jsonPath("$.messages", hasItem("Team A and Team B must be distinct")))
                .andExpect(jsonPath("$.timestamp").exists());
    }



    @Test
    void testGetMatchByIdSuccessfully() throws Exception {
        // Given
        Long matchId = 1L;
        MatchResponseDTO responseDTO = MatchResponseDTOFixture.getInstance().getDerby();
        when(matchService.findMatchById(matchId)).thenReturn(responseDTO);

        // When & Then
        mockMvc.perform(get("/api/matches/{id}", matchId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(matchId))
                .andExpect(jsonPath("$.description").value("DERBY"));

        verify(matchService, times(1)).findMatchById(matchId);
    }

    @Test
    void testGetMatchByIdNotFound() throws Exception {
        // Given
        Long matchId = 999L;
        when(matchService.findMatchById(matchId)).thenThrow(new MatchNotFoundException(matchId));

        // When & Then
        mockMvc.perform(get("/api/matches/{id}", matchId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Match Not Found"))
                .andExpect(jsonPath("$.message").value("Match not found with id "+matchId));

        verify(matchService, times(1)).findMatchById(matchId);
    }

    @Test
    void testCreateGameSuccessfully() throws Exception {
        // Given
        MatchRequestDTO requestDTO = MatchRequestDTOFixture.getInstance().getDerby();
        MatchResponseDTO responseDTO = MatchResponseDTOFixture.getInstance().getDerby();
        when(matchService.createMatch(any(MatchRequestDTO.class))).thenReturn(responseDTO);

        // When & Then
        mockMvc.perform(post("/api/matches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(responseDTO.getId()))
                .andExpect(jsonPath("$.description").value(responseDTO.getDescription()));

        verify(matchService, times(1)).createMatch(any(MatchRequestDTO.class));
    }

    @Test
    void testCreateGameConflict() throws Exception {
        // Given
        MatchRequestDTO requestDTO = MatchRequestDTOFixture.getInstance().getDerby();
        doThrow(new MatchConflictException(requestDTO.getTeamA(), requestDTO.getTeamB(), requestDTO.getMatchDate()))
                .when(matchService).createMatch(any(MatchRequestDTO.class));

        // When & Then
        mockMvc.perform(post("/api/matches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value("Conflict"))
                .andExpect(jsonPath("$.message").value("A match is already scheduled for either team OSFP or PAO on "+requestDTO.getMatchDate()));

        verify(matchService, times(1)).createMatch(any(MatchRequestDTO.class));
    }

    @Test
    void testUpdateMatchSuccessfully() throws Exception {
        // Given
        Long matchId = 1L;
        MatchRequestDTO requestDTO = MatchRequestDTOFixture.getInstance().getSecondDerby();
        MatchResponseDTO responseDTO = MatchResponseDTOFixture.getInstance().getSecondDerby();
        when(matchService.updateMatch(eq(matchId), any(MatchRequestDTO.class))).thenReturn(responseDTO);

        // When & Then
        mockMvc.perform(put("/api/matches/{id}", matchId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(responseDTO.getId()))
                .andExpect(jsonPath("$.description").value(responseDTO.getDescription()));

        verify(matchService, times(1)).updateMatch(eq(matchId), any(MatchRequestDTO.class));
    }

    @Test
    void testUpdateMatchNotFound() throws Exception {
        // Given
        Long matchId = 999L;
        MatchRequestDTO requestDTO = MatchRequestDTOFixture.getInstance().getDerby();
        when(matchService.updateMatch(eq(matchId), any(MatchRequestDTO.class)))
                .thenThrow(new MatchNotFoundException(matchId));

        // When & Then
        mockMvc.perform(put("/api/matches/{id}", matchId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Match Not Found"))
                .andExpect(jsonPath("$.message").value("Match not found with id "+matchId));

        verify(matchService, times(1)).updateMatch(eq(matchId), any(MatchRequestDTO.class));
    }

    @Test
    void testGetAllMatchesSuccessfully() throws Exception {
        // Given
        MatchResponseDTO responseDTO1 = MatchResponseDTOFixture.getInstance().getDerby();
        MatchResponseDTO responseDTO2 = MatchResponseDTOFixture.getInstance().getSecondDerby();
        when(matchService.getAllMatches(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(responseDTO1, responseDTO2)));

        // When & Then
        mockMvc.perform(get("/api/matches"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].id").value(responseDTO1.getId()))
                .andExpect(jsonPath("$.content[1].id").value(responseDTO2.getId()));

        verify(matchService, times(1)).getAllMatches(any(Pageable.class));
    }

    @Test
    void testDeleteMatchSuccessfully() throws Exception {
        // Given
        Long matchId = 1L;
        doNothing().when(matchService).deleteMatch(matchId);

        // When & Then
        mockMvc.perform(delete("/api/matches/{id}", matchId))
                .andExpect(status().isNoContent());

        verify(matchService, times(1)).deleteMatch(matchId);
    }

    @Test
    void testDeleteMatchNotFound() throws Exception {
        // Given
        Long matchId = 999L;
        doThrow(new MatchNotFoundException(matchId)).when(matchService).deleteMatch(matchId);

        // When & Then
        mockMvc.perform(delete("/api/matches/{id}", matchId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Match Not Found"))
                .andExpect(jsonPath("$.message").value("Match not found with id "+matchId));

        verify(matchService, times(1)).deleteMatch(matchId);
    }
}
