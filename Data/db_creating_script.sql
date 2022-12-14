
SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema studyDB
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema studyDB
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `studyDB` DEFAULT CHARACTER SET utf8 ;
USE `studyDB` ;

-- -----------------------------------------------------
-- Table `studyDB`.`Person`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `studyDB`.`Person` (
                                               `id` INT NOT NULL AUTO_INCREMENT,
                                               `name` VARCHAR(45) NOT NULL,
                                               `surname` VARCHAR(45) NOT NULL,
                                               `date_of_birth` DATE NOT NULL,
                                               PRIMARY KEY (`id`))
    ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

INSERT INTO person (name, surname, date_of_birth) VALUES ('Test1','Test1_2','2022:02:02');
INSERT INTO person (name, surname, date_of_birth) VALUES ('Test2','Test2_2','3033:03:03');
INSERT INTO person (name, surname, date_of_birth) VALUES ('Test23','Test3_2','4040:04:04');
