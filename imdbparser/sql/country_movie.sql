INSERT INTO country_movie (movie_id, country)
SELECT a.id AS movie_id, b.country
FROM movies a, country_movie_temp b
WHERE a.title = b.movieTitle
AND a.releaseYear = b.movieReleaseYear
AND a.occurrence = b.movieOccurrence;