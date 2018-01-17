CREATE TABLE `actors` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(2000) NOT NULL,
	`gender` CHAR(1) NOT NULL,
	`occurrence` INT(11) NOT NULL,
	PRIMARY KEY (`id`),
	INDEX `id` (`id`),
	INDEX `name` (`name`)
)
COLLATE='latin1_swedish_ci'
ENGINE=InnoDB
AUTO_INCREMENT=1bigmovie_backup
;
