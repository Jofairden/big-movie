/* Maak een visuele presentatie van de in de loop van de tijd veranderende populariteit van onderwerpen (genre) van films. Denk daarbij aan horror, science-fiction, romance, geweld etc. Zoek zelf naar een goede manier om dit te presenteren.(Visualisatie) */
SELECT
  genre,
  release_year,
  COUNT(ALL m.id) AS count_movies, /* # movies in this genre and release year */
  COUNT(ALL m.id) OVER (PARTITION BY genre) AS movies_in_genre, /* # movies in this genre */
  COUNT(ALL genre) OVER (PARTITION BY release_year) AS genres_in_year /* # genres in this year */
FROM
  movies AS m
  INNER JOIN genre_movie AS gm ON gm.movie_id = m.id
  INNER JOIN genres AS g ON g.id = gm.genre_id
WHERE
  m.votes IS NOT NULL
  AND m.score IS NOT NULL
GROUP BY
  genre,
  release_year;
