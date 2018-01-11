CREATE TABLE `actor_movie_temp` (
	`actorName` VARCHAR(2000) NOT NULL,
	`actorOccurrence` INT(11) NOT NULL,
	`movieTitle` VARCHAR(2000) NOT NULL,
	`movieReleaseYear` VARCHAR(4) NOT NULL,
	INDEX `actorName` (`actorName`),
	INDEX `actorOccurrence` (`actorOccurrence`),
	INDEX `FK_actor_movie_temp_movies` (`movieTitle`),
	INDEX `FK_actor_movie_temp_movies_2` (`movieReleaseYear`),
	CONSTRAINT `FK_actor_movie_temp_actors` FOREIGN KEY (`actorName`) REFERENCES `actors` (`name`),
	CONSTRAINT `FK_actor_movie_temp_actors_2` FOREIGN KEY (`actorOccurrence`) REFERENCES `actors` (`occurrence`),
	CONSTRAINT `FK_actor_movie_temp_movies` FOREIGN KEY (`movieTitle`) REFERENCES `movies` (`title`),
	CONSTRAINT `FK_actor_movie_temp_movies_2` FOREIGN KEY (`movieReleaseYear`) REFERENCES `movies` (`releaseYear`)
)
COLLATE='latin1_swedish_ci'
ENGINE=InnoDB
;
