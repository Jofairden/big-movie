UPDATE movies a 
JOIN ratings_temp b ON (a.title = b.movieTitle)
AND (a.release_year = b.movieReleaseYear)
AND (a.occurrence = b.occurrence)
SET a.votes = b.votes, a.score = b.score
WHERE a.score IS NULL
AND a.votes IS NULL;