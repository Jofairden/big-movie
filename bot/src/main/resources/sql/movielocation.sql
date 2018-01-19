/* Welke films spelen (gedeeltelijk) in New York? */
SELECT
  m.title
FROM
  movies AS m
  INNER JOIN location_movie AS lm ON lm.movie_id = m.id
  INNER JOIN locations AS l ON l.id = lm.location_id
  AND l.location LIKE ?;
