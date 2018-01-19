-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.2.12-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             9.5.0.5220
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Dumping structure for table bigmovie.actors
CREATE TABLE IF NOT EXISTS `actors` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(2000) NOT NULL,
  `gender` char(1) NOT NULL,
  `occurrence` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id` (`id`),
  KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4194060 DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table bigmovie.actor_movie
CREATE TABLE IF NOT EXISTS `actor_movie` (
  `actor_id` int(11) NOT NULL,
  `movie_id` int(11) NOT NULL,
  KEY `actor_id` (`actor_id`),
  KEY `movie_id` (`movie_id`),
  CONSTRAINT `FK_actor_movie_actors` FOREIGN KEY (`actor_id`) REFERENCES `actors` (`id`),
  CONSTRAINT `FK_actor_movie_movies` FOREIGN KEY (`movie_id`) REFERENCES `movies` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table bigmovie.countries
CREATE TABLE IF NOT EXISTS `countries` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `country` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id` (`id`),
  KEY `country` (`country`)
) ENGINE=InnoDB AUTO_INCREMENT=244 DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table bigmovie.country_movie
CREATE TABLE IF NOT EXISTS `country_movie` (
  `movie_id` int(11) NOT NULL,
  `country_id` int(11) NOT NULL,
  KEY `movie_id` (`movie_id`),
  KEY `country_id` (`country_id`),
  CONSTRAINT `FK_country_movie_countries` FOREIGN KEY (`country_id`) REFERENCES `countries` (`id`),
  CONSTRAINT `FK_country_movie_movies` FOREIGN KEY (`movie_id`) REFERENCES `movies` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table bigmovie.genres
CREATE TABLE IF NOT EXISTS `genres` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `genre` varchar(55) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id` (`id`),
  KEY `genre` (`genre`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table bigmovie.genre_movie
CREATE TABLE IF NOT EXISTS `genre_movie` (
  `movie_id` int(11) NOT NULL,
  `genre_id` int(11) NOT NULL,
  KEY `genre_id` (`genre_id`),
  KEY `movie_id` (`movie_id`),
  CONSTRAINT `FK_genre_movie_genres` FOREIGN KEY (`genre_id`) REFERENCES `genres` (`id`),
  CONSTRAINT `FK_genre_movie_movies` FOREIGN KEY (`movie_id`) REFERENCES `movies` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table bigmovie.languages
CREATE TABLE IF NOT EXISTS `languages` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `language` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id` (`id`),
  KEY `language` (`language`)
) ENGINE=InnoDB AUTO_INCREMENT=4722 DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table bigmovie.language_movie
CREATE TABLE IF NOT EXISTS `language_movie` (
  `movie_id` int(11) NOT NULL,
  `language_id` int(11) NOT NULL,
  KEY `movie_id` (`movie_id`),
  KEY `language_id` (`language_id`),
  CONSTRAINT `FK_language_movie_languages` FOREIGN KEY (`language_id`) REFERENCES `languages` (`id`),
  CONSTRAINT `FK_language_movie_movies` FOREIGN KEY (`movie_id`) REFERENCES `movies` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table bigmovie.locations
CREATE TABLE IF NOT EXISTS `locations` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `location` varchar(2000) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id` (`id`),
  KEY `location` (`location`)
) ENGINE=InnoDB AUTO_INCREMENT=193233 DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table bigmovie.location_movie
CREATE TABLE IF NOT EXISTS `location_movie` (
  `movie_id` int(11) NOT NULL,
  `location_id` int(11) NOT NULL,
  KEY `movie_id` (`movie_id`),
  KEY `location_id` (`location_id`),
  CONSTRAINT `FK_location_movie_locations` FOREIGN KEY (`location_id`) REFERENCES `locations` (`id`),
  CONSTRAINT `FK_location_movie_movies` FOREIGN KEY (`movie_id`) REFERENCES `movies` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table bigmovie.movies
CREATE TABLE IF NOT EXISTS `movies` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `title` varchar(2000) NOT NULL,
  `release_year` varchar(4) NOT NULL,
  `occurrence` int(11) NOT NULL DEFAULT 0,
  `running_time` int(11) DEFAULT NULL,
  `votes` int(11) DEFAULT NULL,
  `score` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id` (`id`),
  KEY `title` (`title`)
) ENGINE=InnoDB AUTO_INCREMENT=1438840 DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table bigmovie.movie_soundtrack
CREATE TABLE IF NOT EXISTS `movie_soundtrack` (
  `movie_id` int(11) NOT NULL,
  `soundtrack_id` int(11) NOT NULL,
  KEY `movie_id` (`movie_id`),
  KEY `soundtrack_id` (`soundtrack_id`),
  CONSTRAINT `FK_movie_soundtrack_movies` FOREIGN KEY (`movie_id`) REFERENCES `movies` (`id`),
  CONSTRAINT `FK_movie_soundtrack_soundtracks` FOREIGN KEY (`soundtrack_id`) REFERENCES `soundtracks` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table bigmovie.soundtracks
CREATE TABLE IF NOT EXISTS `soundtracks` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(2000) NOT NULL,
  `info` varchar(2000) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id` (`id`),
  KEY `title` (`title`)
) ENGINE=InnoDB AUTO_INCREMENT=603551 DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
