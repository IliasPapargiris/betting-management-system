-- Migration to create the match_odds table and constraints if they do not exist.

-- Create the match_odds table if it doesn't already exist
CREATE TABLE IF NOT EXISTS public.match_odds (
    id bigserial NOT NULL,
    match_id int8 NOT NULL,
    specifier varchar(10) NOT NULL,
    odd numeric(5, 2) NOT NULL,
    CONSTRAINT matchodds_match_specifier_unique UNIQUE (match_id, specifier),
    CONSTRAINT matchodds_pkey PRIMARY KEY (id),
    CONSTRAINT matchodds_specifier_check CHECK ((specifier::text = ANY (ARRAY['HOME_WIN'::varchar, 'DRAW'::varchar, 'AWAY_WIN'::varchar]))),
    CONSTRAINT uk9ooy0fiqtr5le9ed38k96mx84 UNIQUE (match_id, specifier)
);
