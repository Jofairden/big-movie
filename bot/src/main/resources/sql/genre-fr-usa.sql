SELECT
  c.country,
  g.genre,
  count(m.title) AS `freq`
FROM
  movies as m
  INNER JOIN country_movie AS cm ON cm.movie_id = m.id
  INNER JOIN genre_movie AS gm ON gm.movie_id = m.id
  INNER JOIN countries AS c ON c.id = cm.country_id
  INNER JOIN genres AS g ON g.id = gm.genre_id
WHERE
  c.country = 'USA'
  OR c.country = 'France'
GROUP BY
  country,
  genre;
