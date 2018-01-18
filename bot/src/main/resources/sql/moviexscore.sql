/* Wat zijn de kortste films met een waardering van 8.5 of hoger? */
SELECT 
	m.title, 
	m.running_time,
	m.score
FROM movies m
WHERE m.score >= ?
AND m.running_time = (SELECT MIN(running_time) FROM movies);