SELECT m.title
FROM movies m
INNER JOIN actor_movie am ON am.movie_id = m.id
INNER JOIN actors a ON a.id = am.actor_id
WHERE a.`name` LIKE ?