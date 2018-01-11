CREATE TABLE `location_movie_temp` (
	`movieTitle` VARCHAR(2000) NOT NULL,
	`movieReleaseYear` VARCHAR(4) NOT NULL,
	`location` VARCHAR(2000) NOT NULL,
	`movieOccurrence` INT(11) NOT NULL,
	INDEX `FK_location_movie_movies` (`movieTitle`),
	INDEX `FK_location_movie_movies_2` (`movieReleaseYear`),
	INDEX `FK_location_movie_movies_3` (`movieOccurrence`),
	CONSTRAINT `FK_location_movie_movies` FOREIGN KEY (`movieTitle`) REFERENCES `movies` (`title`),
	CONSTRAINT `FK_location_movie_movies_2` FOREIGN KEY (`movieReleaseYear`) REFERENCES `movies` (`releaseYear`),
	CONSTRAINT `FK_location_movie_movies_3` FOREIGN KEY (`movieOccurrence`) REFERENCES `movies` (`occurrence`)
)
ENGINE=InnoDB
;
