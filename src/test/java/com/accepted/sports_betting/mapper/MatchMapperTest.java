package com.accepted.sports_betting.mapper;
import com.accepted.sports_betting.dto.MatchRequestDTO;
import com.accepted.sports_betting.dto.MatchResponseDTO;
import com.accepted.sports_betting.entity.Match;
import com.accepted.sports_betting.fixture.MatchFixture;
import com.accepted.sports_betting.fixture.MatchRequestDTOFixture;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

//@SpringBootTest(classes = MatchMapper.class)
public class MatchMapperTest {

    @Autowired
    MatchMapper matchMapper;

    @Test
    void testToEntity() {
        // Given
        MatchRequestDTO matchRequestDTO = MatchRequestDTOFixture.getInstance().getDerby();

        // When
        Match match = MatchMapper.toEntity(matchRequestDTO);

        // Then
        assertNotNull(match);
        assertEquals(matchRequestDTO.getDescription(), match.getDescription());
        assertEquals(matchRequestDTO.getMatchDate(), match.getMatchDate());
        assertEquals(matchRequestDTO.getMatchTime(), match.getMatchTime());
        assertEquals(matchRequestDTO.getTeamA(), match.getTeamA());
        assertEquals(matchRequestDTO.getTeamB(), match.getTeamB());
        assertEquals(matchRequestDTO.getSport(), match.getSport());
    }

    @Test
    void testToResponseDTO() {
        // Given
        Match match = MatchFixture.getInstance().getDerby();

        // When
        MatchResponseDTO matchResponseDTO = MatchMapper.toResponseDTO(match);

        // Then
        assertNotNull(matchResponseDTO);
        assertEquals(match.getId(), matchResponseDTO.getId());
        assertEquals(match.getDescription(), matchResponseDTO.getDescription());
        assertEquals(match.getMatchDate(), matchResponseDTO.getMatchDate());
        assertEquals(match.getMatchTime(), matchResponseDTO.getMatchTime());
        assertEquals(match.getTeamA(), matchResponseDTO.getTeamA());
        assertEquals(match.getTeamB(), matchResponseDTO.getTeamB());
        assertEquals(match.getSport(), matchResponseDTO.getSport());
    }


}
