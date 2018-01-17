CREATE TABLE `genre_movie` (
	`movie_id` INT(11) NOT NULL,
	`genre_id` INT(11) NOT NULL,
	INDEX `genre_id` (`genre_id`),
	INDEX `movie_id` (`movie_id`),
	CONSTRAINT `FK_genre_movie_genres` FOREIGN KEY (`genre_id`) REFERENCES `genres` (`id`),
	CONSTRAINT `FK_genre_movie_movies` FOREIGN KEY (`movie_id`) REFERENCES `movies` (`id`)
)
COLLATE='latin1_swedish_ci'
ENGINE=InnoDB
;
