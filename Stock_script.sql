-- MySQL Script generated by MySQL Workbench
-- Sun May  8 14:02:32 2022
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema stockmarket
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema stockmarket
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `stockmarket` DEFAULT CHARACTER SET utf8 ;
USE `stockmarket` ;

-- -----------------------------------------------------
-- Table `stockmarket`.`company`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `stockmarket`.`company` ;

CREATE TABLE IF NOT EXISTS `stockmarket`.`company` (
  `co_code` VARCHAR(10) NOT NULL,
  `co_name` VARCHAR(45) NOT NULL,
  `co_ceo` VARCHAR(45) NOT NULL,
  `co_turnover` INT NOT NULL,
  `co_website` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`co_code`),
  UNIQUE INDEX `co_code_UNIQUE` (`co_code` ASC) VISIBLE,
  INDEX `co_name` (`co_name` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `stockmarket`.`stock_exchange`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `stockmarket`.`stock_exchange` ;

CREATE TABLE IF NOT EXISTS `stockmarket`.`stock_exchange` (
  `se_name` VARCHAR(100) NOT NULL,
  `se_code` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`se_code`),
  UNIQUE INDEX `se_code_UNIQUE` (`se_code` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `stockmarket`.`company_stock_exchange`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `stockmarket`.`company_stock_exchange` ;

CREATE TABLE IF NOT EXISTS `stockmarket`.`company_stock_exchange` (
  `cse_co_code` VARCHAR(10) NOT NULL,
  `cse_se_code` VARCHAR(10) NOT NULL,
  `cse_price_date` DATETIME NOT NULL,
  `cse_stock_price` FLOAT NOT NULL,
  PRIMARY KEY (`cse_co_code`, `cse_se_code`, `cse_price_date`),
  INDEX `fk_Company_has_stock_exchange_stock_exchange1_idx` (`cse_se_code` ASC) VISIBLE,
  INDEX `fk_Company_has_stock_exchange_Company_idx` (`cse_co_code` ASC) VISIBLE,
  CONSTRAINT `fk_Company_has_stock_exchange_Company`
    FOREIGN KEY (`cse_co_code`)
    REFERENCES `stockmarket`.`company` (`co_code`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Company_has_stock_exchange_stock_exchange1`
    FOREIGN KEY (`cse_se_code`)
    REFERENCES `stockmarket`.`stock_exchange` (`se_code`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;