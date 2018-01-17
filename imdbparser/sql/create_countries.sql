CREATE TABLE `countries` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`country` VARCHAR(255) NOT NULL,
	PRIMARY KEY (`id`),
	INDEX `id` (`id`),
	INDEX `country` (`country`)
)
COLLATE='latin1_swedish_ci'
ENGINE=InnoDB
;
