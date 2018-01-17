CREATE TABLE `locations` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`location` VARCHAR(2000) NOT NULL,
	PRIMARY KEY (`id`),
	INDEX `id` (`id`),
	INDEX `location` (`location`)
)
COLLATE='latin1_swedish_ci'
ENGINE=InnoDB
AUTO_INCREMENT=1
;
