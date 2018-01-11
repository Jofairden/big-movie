INSERT INTO actor_movie
SELECT a.id AS actor_id, b.id AS movie_id
FROM actors a, movies b, actress_movie_temp c
WHERE a.name = c.actorName
AND a.occurrence = c.actorOccurrence
AND a.gender = "f"
AND b.title = c.movieTitle
AND b.releaseYear = c.movieReleaseYear
AND b.occurrence = c.movieOccurrence;