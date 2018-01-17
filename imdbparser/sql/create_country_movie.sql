CREATE TABLE `country_movie` (
	`movie_id` INT(11) NOT NULL,
	`country_id` INT(11) NOT NULL,
	INDEX `movie_id` (`movie_id`),
	INDEX `country_id` (`country_id`),
	CONSTRAINT `FK_country_movie_countries` FOREIGN KEY (`country_id`) REFERENCES `countries` (`id`),
	CONSTRAINT `FK_country_movie_movies` FOREIGN KEY (`movie_id`) REFERENCES `movies` (`id`)
)
ENGINE=InnoDB
;
