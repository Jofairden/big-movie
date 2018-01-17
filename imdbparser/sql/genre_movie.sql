INSERT INTO genre_movie (movie_id, genre_id)
SELECT a.id, c.id
FROM movies a, genre_movie_temp b, genres c
WHERE a.title = b.movieTitle
AND a.release_year = b.movieReleaseYear
AND a.occurrence = b.movieOccurrence
AND b.genre = c.genre;