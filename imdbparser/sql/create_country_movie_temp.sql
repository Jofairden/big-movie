CREATE TABLE `country_movie_temp` (
	`movieTitle` VARCHAR(2000) NOT NULL,
	`country` VARCHAR(255) NOT NULL,
	`movieReleaseYear` VARCHAR(4) NOT NULL,
	`movieOccurrence` INT(11) NOT NULL
)
COLLATE='latin1_swedish_ci'
ENGINE=InnoDB
;
