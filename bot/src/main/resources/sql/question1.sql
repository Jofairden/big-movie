/*Welke films spelen in meer dan 1 land? */
SELECT title, COUNT(c.movie_id) AS total
FROM movies AS m, country_movie AS c
WHERE m.id = c.movie_id
GROUP BY c.movie_id
HAVING total > 1;
