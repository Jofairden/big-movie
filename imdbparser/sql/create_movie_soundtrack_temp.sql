CREATE TABLE `movie_soundtrack_temp` (
	`movieTitle` VARCHAR(2000) NOT NULL,
	`soundtrackTitle` VARCHAR(2000) NOT NULL,
	`movieReleaseYear` VARCHAR(4) NOT NULL,
	`movieOccurrence` INT(11) NOT NULL,
	`info` VARCHAR(2000) NULL DEFAULT NULL
)
COLLATE='latin1_swedish_ci'
ENGINE=InnoDB
;
