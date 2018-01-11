CREATE TABLE `ratings` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`movie_id` INT(11) NOT NULL,
	`votes` INT(11) NOT NULL,
	`score` INT(11) NOT NULL,
	PRIMARY KEY (`id`),
	INDEX `id` (`id`),
	INDEX `movie_id` (`movie_id`),
	CONSTRAINT `FK_ratings_movies` FOREIGN KEY (`movie_id`) REFERENCES `movies` (`id`)
)
COLLATE='latin1_swedish_ci'
ENGINE=InnoDB
AUTO_INCREMENT=327676
;
