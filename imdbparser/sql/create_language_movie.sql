CREATE TABLE `language_movie` (
	`movie_id` INT(11) NOT NULL,
	`language_id` INT(11) NOT NULL,
	INDEX `movie_id` (`movie_id`),
	INDEX `language_id` (`language_id`),
	CONSTRAINT `FK_language_movie_languages` FOREIGN KEY (`language_id`) REFERENCES `languages` (`id`),
	CONSTRAINT `FK_language_movie_movies` FOREIGN KEY (`movie_id`) REFERENCES `movies` (`id`)
)
ENGINE=InnoDB
;
