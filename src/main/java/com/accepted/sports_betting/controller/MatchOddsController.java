package com.accepted.sports_betting.controller;

import com.accepted.sports_betting.dto.MatchOddsRequestDTO;
import com.accepted.sports_betting.dto.MatchOddsResponseDTO;
import com.accepted.sports_betting.dto.MatchResponseDTO;
import com.accepted.sports_betting.service.MatchOddsService;
import com.accepted.sports_betting.utils.JsonExamples;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/odds")
@RequiredArgsConstructor
public class MatchOddsController {

    private final MatchOddsService matchOddsService;

    @Operation(summary = "Get odds for a match", description = "Retrieve all odds associated with a specific match by match ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Odds retrieved successfully", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = MatchResponseDTO.class),
                    examples = @ExampleObject(value = JsonExamples.MATCH_ODDS_LIST_EXAMPLE_JSON))),
            @ApiResponse(responseCode = "404", description = "Match not found", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = MatchResponseDTO.class),
                    examples = @ExampleObject(value = JsonExamples.MATCH_NOT_FOUND_ERROR_JSON)))
    })
    @GetMapping("/matches/{matchId}")
    public ResponseEntity<List<MatchOddsResponseDTO>> getMatchOdds(@PathVariable Long matchId) {
        return ResponseEntity.ok(matchOddsService.getMatchOddsByMatchId(matchId));
    }

    @Operation(summary = "Create match odds", description = "Create new odds for a specific match.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Match odds created successfully", content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = JsonExamples.MATCH_ODDS_RESPONSE_JSON))),
            @ApiResponse(responseCode = "400", description = "Invalid input provided", content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = JsonExamples.MATCH_ODDS_VALIDATION_ERROR_JSON))),
            @ApiResponse(responseCode = "409", description = "Conflict with existing match odds for this specifier",content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = JsonExamples.MATCH_ODDS_CONFLICT_ERROR_JSON)))
    })
    @PostMapping
    public ResponseEntity<MatchOddsResponseDTO> createMatchOdds(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Match request details",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = JsonExamples.MATCH_ODDS_REQUEST_VALID_JSON)),
                    required = true)
            @Valid @RequestBody MatchOddsRequestDTO requestDTO) {
        MatchOddsResponseDTO createdOdds = matchOddsService.createMatchOdds(requestDTO);
        return ResponseEntity.status(201).body(createdOdds);
    }

    @Operation(summary = "Update match odds", description = "Update existing match odds by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Match odds updated successfully", content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = JsonExamples.MATCH_ODDS_RESPONSE_JSON))),
            @ApiResponse(responseCode = "404", description = "Match odds not found", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = MatchResponseDTO.class),
                    examples = @ExampleObject(value = JsonExamples.MATCH_NOT_FOUND_ERROR_JSON))),
            @ApiResponse(responseCode = "400", description = "Invalid input provided", content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = JsonExamples.MATCH_ODDS_VALIDATION_ERROR_JSON))),
            @ApiResponse(responseCode = "409", description = "Conflict with existing match odds for this specifier", content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = JsonExamples.MATCH_ODDS_CONFLICT_ERROR_JSON)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<MatchOddsResponseDTO> updateMatchOdds(
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Match request details",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = JsonExamples.MATCH_ODDS_REQUEST_VALID_JSON)),
                    required = true)
            @Valid @RequestBody MatchOddsRequestDTO requestDTO) {
        MatchOddsResponseDTO updatedOdds = matchOddsService.updateMatchOdds(id, requestDTO);
        return ResponseEntity.ok(updatedOdds);
    }

    @Operation(summary = "Delete match odds", description = "Delete match odds by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Match odds deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Match odds not found", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = MatchResponseDTO.class),
                    examples = @ExampleObject(value = JsonExamples.MATCH_NOT_FOUND_ERROR_JSON)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMatchOdds(@PathVariable Long id) {
        matchOddsService.deleteMatchOdds(id);
        return ResponseEntity.noContent().build();
    }





}
