CREATE TABLE `movie_soundtrack_temp` (
	`movieTitle` VARCHAR(2000) NOT NULL,
	`soundtrackTitle` VARCHAR(2000) NOT NULL,
	`movieReleaseYear` VARCHAR(4) NOT NULL,
	`movieOccurrence` INT(11) NOT NULL,
	`info` VARCHAR(2000) NULL DEFAULT NULL,
	INDEX `FK_movie_soundtrack_temp_movies` (`movieTitle`),
	INDEX `FK_movie_soundtrack_temp_soundtracks` (`soundtrackTitle`),
	INDEX `FK_movie_soundtrack_temp_movies_2` (`movieReleaseYear`),
	INDEX `FK_movie_soundtrack_temp_movies_3` (`movieOccurrence`),
	CONSTRAINT `FK_movie_soundtrack_temp_movies` FOREIGN KEY (`movieTitle`) REFERENCES `movies` (`title`),
	CONSTRAINT `FK_movie_soundtrack_temp_movies_2` FOREIGN KEY (`movieReleaseYear`) REFERENCES `movies` (`releaseYear`),
	CONSTRAINT `FK_movie_soundtrack_temp_movies_3` FOREIGN KEY (`movieOccurrence`) REFERENCES `movies` (`occurrence`),
	CONSTRAINT `FK_movie_soundtrack_temp_soundtracks` FOREIGN KEY (`soundtrackTitle`) REFERENCES `soundtracks` (`title`)
)
COLLATE='latin1_swedish_ci'
ENGINE=InnoDB
;
