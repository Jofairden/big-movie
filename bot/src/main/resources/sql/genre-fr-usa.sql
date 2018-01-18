SELECT c.country as `format`, g.genre as `genre`, count(m.title) as  `freq`
FROM movies m
INNER JOIN country_movie cm ON cm.movie_id = m.id
INNER JOIN genre_movie gm ON gm.movie_id = m.id
INNER JOIN countries c ON c.id = cm.country_id
INNER JOIN genres g ON g.id = gm.genre_id
WHERE c.country LIKE '%usa%' OR c.country LIKE '%france%'
GROUP BY c.country, g.genre;