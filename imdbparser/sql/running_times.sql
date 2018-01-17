UPDATE movies a 
JOIN running_times_temp b ON (a.title = b.movieTitle)
AND (a.release_year = b.movieReleaseYear)
AND (a.occurrence = b.occurrence)
SET a.running_time = b.runningTime
WHERE a.running_time IS NULL;