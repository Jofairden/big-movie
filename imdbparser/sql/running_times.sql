INSERT INTO running_times (movie_id, running_time)
SELECT a.id AS movie_id, b.runningTime
FROM movies a, running_times_temp b
WHERE a.title = b.movieTitle
AND a.releaseYear = b.movieReleaseYear
AND a.occurrence = b.occurrence
ORDER BY a.id;