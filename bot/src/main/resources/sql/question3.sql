/* Welke films hebben de hoogste score met de minste stemmen? */
SELECT 
	m.title, 
	m.votes, 
	m.score
FROM movies m
WHERE m.votes = (SELECT MIN(votes) FROM movies)
AND m.score =(SELECT MAX(score) FROM movies);