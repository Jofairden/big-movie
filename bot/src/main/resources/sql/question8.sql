/* Geef het aantal films dat in een land gemaakt is weer in de tijd. Dwz maak een grafiek waarin op de x-as het jaar staat en op de y-as het aantal gemaakte films.(visualisatie) */
SELECT cm.country, m.releaseYear, COUNT(*) AS number_movies
FROM country_movie cm
INNER JOIN movies m ON m.id = cm.movie_id
GROUP BY cm.country, m.releaseYear
