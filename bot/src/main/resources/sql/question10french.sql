SELECT m.title, c.country, g.genre
FROM movies m
INNER JOIN country_movie c ON c.movie_id=m.id
INNER JOIN movie_genre g ON g.movie_id=m.id
WHERE c.country LIKE "%FRANCE%"
ORDER BY g.genre;