INSERT INTO location_movie (movie_id, location)
SELECT a.id AS movie_id, b.location
FROM movies a, location_movie_temp b
WHERE a.title = b.movieTitle
AND a.releaseYear = b.movieReleaseYear
AND a.occurrence = b.movieOccurrence;