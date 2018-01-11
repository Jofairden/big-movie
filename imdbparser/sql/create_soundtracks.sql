CREATE TABLE `soundtracks` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`title` VARCHAR(2000) NOT NULL,
	`info` VARCHAR(2000) NULL DEFAULT NULL,
	PRIMARY KEY (`id`),
	INDEX `id` (`id`),
	INDEX `title` (`title`)
)
COLLATE='latin1_swedish_ci'
ENGINE=InnoDB
AUTO_INCREMENT=655351
;