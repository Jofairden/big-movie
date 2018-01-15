/* Welke films hebben de hoogste score met de minste stemmen? */
SELECT 
	m.title, 
	r.votes, 
	r.score
FROM ratings AS r
INNER JOIN movies m ON m.id = r.movie_id
WHERE r.votes = 
	(
		SELECT MIN(r.votes)
		FROM ratings r
    	INNER JOIN movies m ON m.id = r.movie_id
	)
AND r.score =
	(
		SELECT MAX(r.score)
		FROM ratings r
		INNER JOIN movies m ON m.id = r.movie_id
	);