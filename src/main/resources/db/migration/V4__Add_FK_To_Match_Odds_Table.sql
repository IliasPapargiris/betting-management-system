-- Migration to add a foreign key to match_odds referencing the match table.

-- Add fk to match_odds table
ALTER TABLE public.match_odds
ADD CONSTRAINT matchodds_match_id_fkey
FOREIGN KEY (match_id) REFERENCES public."match"(id) ON DELETE CASCADE;