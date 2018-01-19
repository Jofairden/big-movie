/* Geef het aantal films dat in een land gemaakt is weer in de tijd. Dwz maak een grafiek waarin op de x-as het jaar staat en op de y-as het aantal gemaakte films.(visualisatie) */
SELECT
  country,
  release_year,
  COUNT(m.id) AS number_movies
FROM
  movies AS m
  LEFT  JOIN country_movie AS cm ON cm.movie_id = m.id
  LEFT  JOIN countries AS c ON c.id = cm.country_id
WHERE
  country LIKE '%Germany'
GROUP BY
  country,
  release_year;
