-- Migration to create the sport_enum type and match table if they do not exist.

-- Step 1: Create the ENUM type for sport if it doesn't already exist
CREATE TYPE sport_enum AS ENUM ('FOOTBALL', 'BASKETBALL');

-- Step 2: Create the match table if it doesn't already exist
CREATE TABLE IF NOT EXISTS public."match" (
    id bigserial NOT NULL,
    description varchar(255) NOT NULL,
    match_date date NOT NULL,
    match_time time NOT NULL,
    team_a varchar(100) NOT NULL,
    team_b varchar(100) NOT NULL,
    sport public."sport_enum" NOT NULL,
    CONSTRAINT match_pkey PRIMARY KEY (id),
    CONSTRAINT match_unique_constraint UNIQUE (match_date, team_a, team_b, sport),
    CONSTRAINT uk1pcwsssvvvdv57irs4i7x28fq UNIQUE (match_date, team_a, team_b, sport)
);