CREATE TABLE `ratings_temp` (
	`votes` INT(11) NOT NULL,
	`score` FLOAT NOT NULL,
	`movieTitle` VARCHAR(2000) NOT NULL,
	`movieReleaseYear` VARCHAR(4) NOT NULL,
	`occurrence` INT(1) NOT NULL
)
COLLATE='latin1_swedish_ci'
ENGINE=InnoDB
;
