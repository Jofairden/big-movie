INSERT INTO location_movie (movie_id, location_id)
SELECT a.id AS movie_id, c.id AS location_id
FROM movies a, location_movie_temp b, locations c
WHERE a.title = b.movieTitle
AND a.release_year = b.movieReleaseYear
AND a.occurrence = b.movieOccurrence
AND b.location = c.location;