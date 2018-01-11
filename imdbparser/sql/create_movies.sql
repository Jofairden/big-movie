CREATE TABLE `movies` (
	`id` INT(10) NOT NULL AUTO_INCREMENT,
	`title` VARCHAR(2000) NOT NULL,
	`releaseYear` VARCHAR(4) NOT NULL,
	`occurrence` INT(11) NOT NULL DEFAULT '0',
	PRIMARY KEY (`id`),
	UNIQUE INDEX `id` (`id`),
	INDEX `title` (`title`),
	INDEX `releaseYear` (`releaseYear`),
	INDEX `occurrence` (`occurrence`)
)
COLLATE='latin1_swedish_ci'
ENGINE=InnoDB
AUTO_INCREMENT=1440887
;
