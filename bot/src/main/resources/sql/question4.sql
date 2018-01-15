/* Wat is de kortste film met een waardering van 8.5 of hoger? */
SELECT 
	m.title, 
	t.running_time
FROM movies m, ratings r, running_times t
WHERE m.id = r.movie_id
AND r.score >= 8.5
AND m.id = t.movie_id
AND m.id IN 
	(
		SELECT m.id
		FROM running_times t
		INNER JOIN movies m ON m.id = t.movie_id
		WHERE t.running_time =
		(
			SELECT MIN(t2.running_time)
			FROM running_times AS t2
		)
	);