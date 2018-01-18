/* Geef het aantal films dat in een land gemaakt is weer in de tijd. Dwz maak een grafiek waarin op de x-as het jaar staat en op de y-as het aantal gemaakte films.(visualisatie) */
SELECT c.country, m.release_year, COUNT(*) AS number_movies
FROM movies m
INNER JOIN  country_movie cm ON cm.movie_id = m.id
INNER JOIN countries c ON c.id = cm.country_id
GROUP BY c.country, m.release_year
HAVING c.country LIKE '%Germany%';