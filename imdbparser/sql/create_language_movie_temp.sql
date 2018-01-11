CREATE TABLE `language_movie_temp` (
	`movieTitle` VARCHAR(2000) NOT NULL,
	`movieReleaseYear` VARCHAR(4) NOT NULL,
	`language` VARCHAR(255) NOT NULL,
	`movieOccurrence` VARCHAR(4) NOT NULL
)
COLLATE='latin1_swedish_ci'
ENGINE=InnoDB
;
