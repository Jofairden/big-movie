INSERT INTO country_movie (movie_id, country_id)
SELECT a.id AS movie_id, c.id AS country_id
FROM movies a, country_movie_temp b, countries c
WHERE a.title = b.movieTitle
AND a.release_year = b.movieReleaseYear
AND a.occurrence = b.movieOccurrence
AND b.country = c.country;