CREATE TABLE `running_times_temp` (
	`movieTitle` VARCHAR(2000) NOT NULL,
	`movieReleaseYear` VARCHAR(4) NOT NULL,
	`runningTime` INT(11) NOT NULL,
	`occurrence` INT(11) NOT NULL,
	INDEX `FK_running_times_temp_movies` (`movieTitle`),
	INDEX `FK_running_times_temp_movies_2` (`movieReleaseYear`),
	INDEX `FK_running_times_temp_movies_3` (`occurrence`),
	CONSTRAINT `FK_running_times_temp_movies` FOREIGN KEY (`movieTitle`) REFERENCES `movies` (`title`),
	CONSTRAINT `FK_running_times_temp_movies_2` FOREIGN KEY (`movieReleaseYear`) REFERENCES `movies` (`releaseYear`),
	CONSTRAINT `FK_running_times_temp_movies_3` FOREIGN KEY (`occurrence`) REFERENCES `movies` (`occurrence`)
)
COLLATE='latin1_swedish_ci'
ENGINE=InnoDB
;