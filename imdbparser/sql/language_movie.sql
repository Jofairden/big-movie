INSERT INTO language_movie (movie_id, language_id)
SELECT a.id, c.id
FROM movies a, language_movie_temp b, languages c
WHERE a.title = b.movieTitle
AND a.release_year = b.movieReleaseYear
AND a.occurrence = b.movieOccurrence
AND b.`language` = c.`language`;