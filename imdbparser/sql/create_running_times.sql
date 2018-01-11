CREATE TABLE `running_times` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`movie_id` INT(11) NOT NULL,
	`running_time` INT(11) NOT NULL,
	PRIMARY KEY (`id`),
	INDEX `id` (`id`),
	INDEX `FK_running_times_movies` (`movie_id`),
	CONSTRAINT `FK_running_times_movies` FOREIGN KEY (`movie_id`) REFERENCES `movies` (`id`)
)
COLLATE='latin1_swedish_ci'
ENGINE=InnoDB
AUTO_INCREMENT=786421
;
