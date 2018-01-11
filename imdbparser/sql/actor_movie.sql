INSERT INTO actor_movie
(
	SELECT a.id, b.id
	FROM actors a, movies b
	WHERE a.name =
		(
			SELECT actorName
			FROM actor_movie_temp
		)
	AND a.occurrence =
		(
			SELECT occurrence
			FROM actor_movie_temp
		)
	AND b.title =
		(
			SELECT movieTitle
			FROM actor_movie_temp
		)
	AND b.releaseYear ==
		(
			SELECT releaseYear
			FROM actor_movie_temp
		)
);