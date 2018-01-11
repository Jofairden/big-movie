INSERT INTO ratings (movie_id, votes, score)
SELECT a.id AS movie_id, b.votes, b.score
FROM movies a, ratings_temp b
WHERE a.title = b.movieTitle
AND a.releaseYear = b.movieReleaseYear
AND a.occurrence = b.occurrence
ORDER BY a.id;