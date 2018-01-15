/* Maak een visuele presentatie van de in de loop van de tijd veranderende populariteit van onderwerpen (genre) van films. Denk daarbij aan horror, science-fiction, romance, geweld etc. Zoek zelf naar een goede manier om dit te presenteren.(Visualisatie) */
SELECT 
	ROUND(AVG(r.votes),0) AS avgVotes, 
	AVG(r.score) AS avgScore, 
	mg.genre, 
	m.releaseYear, 
	COUNT(mg.genre) AS movies_count
FROM ratings r
INNER JOIN movies m ON m.id = r.movie_id
INNER JOIN movie_genre mg ON m.id = mg.movie_id
GROUP BY mg.genre, m.releaseYear