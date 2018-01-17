/* Maak een visuele presentatie van de in de loop van de tijd veranderende populariteit van onderwerpen (genre) van films. Denk daarbij aan horror, science-fiction, romance, geweld etc. Zoek zelf naar een goede manier om dit te presenteren.(Visualisatie) */
SELECT 
	ROUND(AVG(m.votes),0) AS avgVotes, 
	AVG(m.score) AS avgScore, 
	g.genre, 
	m.release_year, 
	COUNT(g.genre) AS movies_count
FROM movies m
INNER JOIN genre_movie gm ON gm.movie_id = m.id
INNER JOIN genres g ON g.id = gm.genre_id
WHERE m.votes IS NOT NULL
AND m.score IS NOT NULL
GROUP BY g.genre, m.release_year;