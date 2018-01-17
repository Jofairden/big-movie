CREATE TABLE `movies` (
	`id` INT(10) NOT NULL AUTO_INCREMENT,
	`title` VARCHAR(2000) NOT NULL,
	`release_year` VARCHAR(4) NOT NULL,
	`occurrence` INT(11) NOT NULL DEFAULT '0',
	`running_time` INT(11) DEFAULT NULL,
	`votes` INT(11) DEFAULT NULL,
	`score` INT(11) DEFAULT NULL,
	PRIMARY KEY (`id`),
	INDEX `id` (`id`),
	INDEX `title` (`title`)
)
COLLATE='latin1_swedish_ci'
ENGINE=InnoDB
AUTO_INCREMENT=1
;