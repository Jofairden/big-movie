/* Welke films spelen (gedeeltelijk) in New York? */
SELECT m.title, l.location
FROM movies m
INNER JOIN location_movie l ON l.movie_id = m.id
AND l.location LIKE "%New York%";