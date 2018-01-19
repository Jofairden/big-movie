SELECT
  m.title
FROM
  movies AS m
  INNER JOIN actor_movie AS am ON am.movie_id = m.id
  INNER JOIN actors AS a ON a.id = am.actor_id
WHERE
  a.`name` LIKE ?;
