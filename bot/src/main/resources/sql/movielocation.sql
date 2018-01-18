/* Welke films spelen (gedeeltelijk) in New York? */
SELECT m.title
FROM movies m
INNER JOIN location_movie lm ON lm.movie_id = m.id
INNER JOIN locations l ON l.id = lm.location_id
AND l.location LIKE ?;