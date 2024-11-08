package com.accepted.sports_betting.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DistinctTeamsValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface DistinctTeams {
    String message() default "Teams must be distinct";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
