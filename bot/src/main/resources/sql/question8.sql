/* Geef het aantal films dat in een land gemaakt is weer in de tijd. Dwz maak een grafiek waarin op de x-as het jaar staat en op de y-as het aantal gemaakte films.(visualisatie) */
SELECT c.country, m.release_year, COUNT(*) AS number_movies
FROM country_movie cm
INNER JOIN movies m ON m.id = cm.movie_id
INNER JOIN countries c ON c.id = cm.country_id
GROUP BY c.country, m.release_year;