SELECT c.country as format, count(c.genre) as freq
FROM movies m
INNER JOIN country_movie c ON c.movie_id=m.id
INNER JOIN movie_genre g ON g.movie_id=m.id
WHERE c.country LIKE "%FRANCE%" OR c.country LIKE "%USA"
ORDER BY g.genre;