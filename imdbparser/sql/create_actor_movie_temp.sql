CREATE TABLE `actor_movie_temp` (
	`actorName` VARCHAR(2000) NOT NULL,
	`actorOccurrence` INT(11) NOT NULL,
	`movieTitle` VARCHAR(2000) NOT NULL,
	`movieReleaseYear` VARCHAR(4) NOT NULL,
	`movieOccurrence` INT(11) NOT NULL
)
COLLATE='latin1_swedish_ci'
ENGINE=InnoDB
;
