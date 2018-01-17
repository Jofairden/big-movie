CREATE TABLE `languages` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`language` VARCHAR(255) NOT NULL,
	PRIMARY KEY (`id`),
	INDEX `id` (`id`),
	INDEX `language` (`language`)
)
COLLATE='latin1_swedish_ci'
ENGINE=InnoDB
;
