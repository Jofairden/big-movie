INSERT INTO movie_genre (movie_id, genre)
SELECT a.id, b.genre
FROM movies a, movie_genre_temp b
WHERE a.title = b.movieTitle
AND a.releaseYear = b.movieReleaseYear
AND a.occurrence = b.movieOccurrence;