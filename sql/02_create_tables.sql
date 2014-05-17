USE lasers_and_mirrors;

CREATE TABLE `level` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(100) NOT NULL,
	`completed` ENUM('true','false') NULL DEFAULT 'false',
	PRIMARY KEY (`id`),
	UNIQUE INDEX `name` (`name`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB;


CREATE TABLE `game_object` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`level_id` INT(11) NOT NULL,
	`type` ENUM('diamond','laser','mirror','other') NOT NULL,
	`x` DOUBLE NOT NULL DEFAULT '0',
	`y` DOUBLE NOT NULL DEFAULT '0',
	`rot` DOUBLE NOT NULL DEFAULT '0' COMMENT 'range: [0, 180)',
	`color` CHAR(7) DEFAULT '#FF0000' COMMENT 'format: #RRGGBB',
	PRIMARY KEY (`id`),
	INDEX `type` (`type`),
	INDEX `FK_game_object_level` (`level_id`),
	CONSTRAINT `FK_game_object_level` FOREIGN KEY (`level_id`) REFERENCES `level` (`id`) ON UPDATE CASCADE ON DELETE CASCADE
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB;
