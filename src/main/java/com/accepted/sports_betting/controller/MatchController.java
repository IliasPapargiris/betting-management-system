package com.accepted.sports_betting.controller;

import com.accepted.sports_betting.dto.MatchRequestDTO;
import com.accepted.sports_betting.dto.MatchResponseDTO;
import com.accepted.sports_betting.service.MatchService;
import com.accepted.sports_betting.utils.JsonExamples;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/matches")
@RequiredArgsConstructor
@Validated

public class MatchController {
    private final MatchService matchService;


    @Operation(summary = "Get a match by ID", description = "This endpoint returns a match by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Match found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MatchResponseDTO.class),
                            examples = @ExampleObject(value = JsonExamples.MATCH_RESPONSE_JSON))),
            @ApiResponse(responseCode = "404", description = "Match not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MatchResponseDTO.class),
                            examples = @ExampleObject(value = JsonExamples.MATCH_NOT_FOUND_ERROR_JSON))
            )
    })
    @GetMapping("/{id}")
    public MatchResponseDTO getMatchById(@PathVariable Long id) {
        return matchService.findMatchById(id);
    }


    @Operation(summary = "Create a new match", description = "This endpoint allows you to create a new match.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Match created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MatchResponseDTO.class),
                            examples = @ExampleObject(value = JsonExamples.MATCH_RESPONSE_JSON))),
            @ApiResponse(responseCode = "400", description = "Invalid input provided",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = JsonExamples.VALIDATION_ERROR_JSON))),
            @ApiResponse(responseCode = "409", description = "Conflict with already booked match",
                    content = @Content(mediaType = "application/json",examples = @ExampleObject(value = JsonExamples.MATCH_CONFLICT_ERROR_JSON)))
    })
    @PostMapping
    public ResponseEntity<MatchResponseDTO> createGame(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Match request details",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = JsonExamples.MATCH_REQUEST_JSON)),
                    required = true
            )

            @Valid @RequestBody MatchRequestDTO matchRequestDTO){
        return ResponseEntity.status(201).body(matchService.createMatch(matchRequestDTO));
    }

    @Operation(summary = "Update a match", description = "This endpoint allows you to update an existing match by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Match updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MatchResponseDTO.class),
                            examples = @ExampleObject(value = JsonExamples.MATCH_RESPONSE_JSON))),
            @ApiResponse(responseCode = "400", description = "Invalid input provided",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = JsonExamples.VALIDATION_ERROR_JSON))),
            @ApiResponse(responseCode = "404", description = "Match not found",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = JsonExamples.MATCH_NOT_FOUND_ERROR_JSON))),
            @ApiResponse(responseCode = "409", description = "Conflict with already booked match",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = JsonExamples.MATCH_CONFLICT_ERROR_JSON)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<MatchResponseDTO> updateMatch(
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Match request details",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = JsonExamples.MATCH_REQUEST_JSON)),
                    required = true
            )
            @Valid @RequestBody MatchRequestDTO updateMatchDTO) {
        return ResponseEntity.ok(matchService.updateMatch(id, updateMatchDTO));
    }



    @Operation(summary = "Get all matches", description = "This endpoint returns a paginated list of all matches.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Matches retrieved successfully", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = MatchResponseDTO.class),
                    examples = @ExampleObject(value = JsonExamples.MATCHES_PAGEABLE_JSON)))
    })
    @GetMapping
    public ResponseEntity<Page<MatchResponseDTO>> getAllMatches(Pageable pageable) {
        Page<MatchResponseDTO> allMatches = matchService.getAllMatches(pageable);
        return ResponseEntity.ok(allMatches);
    }

    @Operation(summary = "Delete a match", description = "This endpoint allows you to delete a match by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Match deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Match not found",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = JsonExamples.MATCH_NOT_FOUND_ERROR_JSON)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMatch(@PathVariable Long id) {
        matchService.deleteMatch(id);
        return ResponseEntity.noContent().build();
    }
}
