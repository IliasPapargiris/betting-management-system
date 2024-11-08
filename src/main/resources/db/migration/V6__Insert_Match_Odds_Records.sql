-- Insert data into the 'match_odds' table.

INSERT INTO public.match_odds (match_id, specifier, odd)
VALUES
    (1, 'HOME_WIN', 1.75),
    (1, 'DRAW', 3.25),
    (1, 'AWAY_WIN', 2.88),
    (2, 'HOME_WIN', 1.85),
    (2, 'DRAW', 1.95),
    (2, 'AWAY_WIN', 1.95);