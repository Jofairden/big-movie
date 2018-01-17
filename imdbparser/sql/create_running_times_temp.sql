CREATE TABLE `running_times_temp` (
	`movieTitle` VARCHAR(2000) NOT NULL,
	`movieReleaseYear` VARCHAR(4) NOT NULL,
	`runningTime` INT(11) NOT NULL,
	`occurrence` INT(11) NOT NULL
)
COLLATE='latin1_swedish_ci'
ENGINE=InnoDB
;
