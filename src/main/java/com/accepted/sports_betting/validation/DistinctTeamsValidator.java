package com.accepted.sports_betting.validation;

import com.accepted.sports_betting.dto.MatchRequestDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DistinctTeamsValidator implements ConstraintValidator<DistinctTeams, MatchRequestDTO> {

    @Override
    public void initialize(DistinctTeams constraintAnnotation) {
        // Initialization logic if needed
    }

    @Override
    public boolean isValid(MatchRequestDTO matchRequestDTO, ConstraintValidatorContext context) {
        if (matchRequestDTO.getTeamA() == null || matchRequestDTO.getTeamB() == null) {
            return true;
        }
        return !matchRequestDTO.getTeamA().equalsIgnoreCase(matchRequestDTO.getTeamB());
    }
}

