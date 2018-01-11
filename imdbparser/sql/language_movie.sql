INSERT INTO language_movie (movie_id, language)
SELECT a.id, b.language
FROM movies a, language_movie_temp b
WHERE a.title = b.movieTitle
AND a.releaseYear = b.movieReleaseYear
AND a.occurrence = b.movieOccurrence;