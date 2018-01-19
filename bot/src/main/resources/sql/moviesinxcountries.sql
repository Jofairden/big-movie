/*Welke films spelen in meer dan 1 land? */
SELECT
  title,
  COUNT(cm.movie_id) AS `total`
FROM
  movies AS m
  INNER JOIN country_movie AS cm ON cm.movie_id = m.id
GROUP BY
  movie_id
HAVING
  total > ?;
