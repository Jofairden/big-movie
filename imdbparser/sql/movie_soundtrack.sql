INSERT INTO movie_soundtrack (movie_id, soundtrack_id)
SELECT a.id AS movie_id, c.id AS soundtrack_id
FROM movies a, movie_soundtrack_temp b, soundtracks c
WHERE a.title = b.movieTitle
AND a.release_year = b.movieReleaseYear
AND a.occurrence = b.movieOccurrence
AND b.soundtrackTitle = c.title
AND b.info = c.info;