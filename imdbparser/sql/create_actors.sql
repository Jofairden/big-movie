CREATE TABLE `actors` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(2000) NOT NULL,
	`gender` CHAR(1) NOT NULL,
	`occurrence` INT(11) NOT NULL,
	PRIMARY KEY (`id`),
	INDEX `name` (`name`),
	INDEX `gender` (`gender`),
	INDEX `occurrence` (`occurrence`),
	INDEX `id` (`id`)
)
COLLATE='latin1_swedish_ci'
ENGINE=InnoDB
AUTO_INCREMENT=4184729
;
