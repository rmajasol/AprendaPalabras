SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

DROP SCHEMA IF EXISTS `rmaja85` ;
CREATE SCHEMA IF NOT EXISTS `rmaja85` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci ;
USE `rmaja85` ;

-- -----------------------------------------------------
-- Table `rmaja85`.`Language`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `rmaja85`.`Language` ;

CREATE  TABLE IF NOT EXISTS `rmaja85`.`Language` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `language` VARCHAR(45) NOT NULL ,
  `creationDate` DATETIME NOT NULL ,
  `User_id` INT UNSIGNED NOT NULL ,
  UNIQUE INDEX `language_UNIQUE` (`language` ASC) ,
  PRIMARY KEY (`id`) ,
  CONSTRAINT `fk_Language_User1`
    FOREIGN KEY (`User_id` )
    REFERENCES `rmaja85`.`User` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `rmaja85`.`Role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `rmaja85`.`Role` ;

CREATE  TABLE IF NOT EXISTS `rmaja85`.`Role` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `role` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `role_UNIQUE` (`role` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `rmaja85`.`User`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `rmaja85`.`User` ;

CREATE  TABLE IF NOT EXISTS `rmaja85`.`User` (
  `id` INT UNSIGNED NULL AUTO_INCREMENT ,
  `username` VARCHAR(20) NULL ,
  `password` VARCHAR(30) NULL ,
  `email` VARCHAR(254) NULL ,
  `registrationDate` DATETIME NULL ,
  `ipAddressReg` VARCHAR(15) NULL ,
  `validated` TINYINT(1) NULL ,
  `Language_id` INT UNSIGNED NULL ,
  `Role_id` INT UNSIGNED NULL ,
  `Default_lang_from` INT UNSIGNED NULL ,
  `Default_lang_to` INT UNSIGNED NULL ,
  `invertAcceptations` TINYINT(1) NULL ,
  `hideDefLangFrom` TINYINT(1) NULL ,
  `hideDefLangTo` TINYINT(1) NULL ,
  `autoAdding` TINYINT(1) NULL ,
  `hideUsername` TINYINT(1) NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) ,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) ,
  CONSTRAINT `fk_User_Language1`
    FOREIGN KEY (`Language_id` )
    REFERENCES `rmaja85`.`Language` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_User_Role1`
    FOREIGN KEY (`Role_id` )
    REFERENCES `rmaja85`.`Role` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_User_Language2`
    FOREIGN KEY (`Default_lang_from` )
    REFERENCES `rmaja85`.`Language` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_User_Language3`
    FOREIGN KEY (`Default_lang_to` )
    REFERENCES `rmaja85`.`Language` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `rmaja85`.`AccessKey`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `rmaja85`.`AccessKey` ;

CREATE  TABLE IF NOT EXISTS `rmaja85`.`AccessKey` (
  `accessKey` VARCHAR(80) NOT NULL ,
  `User_id` INT UNSIGNED NOT NULL ,
  UNIQUE INDEX `key_UNIQUE` (`accessKey` ASC) ,
  PRIMARY KEY (`User_id`) ,
  CONSTRAINT `fk_Key_User1`
    FOREIGN KEY (`User_id` )
    REFERENCES `rmaja85`.`User` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `rmaja85`.`Unsuscribed`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `rmaja85`.`Unsuscribed` ;

CREATE  TABLE IF NOT EXISTS `rmaja85`.`Unsuscribed` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `User_id` INT UNSIGNED NOT NULL ,
  `feedback` VARCHAR(500) NULL ,
  `banned` TINYINT(1) NOT NULL ,
  `unsuscriptionDate` DATETIME NOT NULL ,
  PRIMARY KEY (`id`) ,
  CONSTRAINT `fk_Unsuscribed_User1`
    FOREIGN KEY (`User_id` )
    REFERENCES `rmaja85`.`User` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `rmaja85`.`Word`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `rmaja85`.`Word` ;

CREATE  TABLE IF NOT EXISTS `rmaja85`.`Word` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `word` VARCHAR(140) NOT NULL ,
  `creationDate` DATETIME NOT NULL ,
  `User_id` INT UNSIGNED NOT NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `word_UNIQUE` (`word` ASC) ,
  CONSTRAINT `fk_Word_User1`
    FOREIGN KEY (`User_id` )
    REFERENCES `rmaja85`.`User` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `rmaja85`.`WordLanguage`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `rmaja85`.`WordLanguage` ;

CREATE  TABLE IF NOT EXISTS `rmaja85`.`WordLanguage` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `Word_id` INT UNSIGNED NOT NULL ,
  `Language_id` INT UNSIGNED NOT NULL ,
  `creationDate` DATETIME NOT NULL ,
  `User_id` INT UNSIGNED NOT NULL ,
  PRIMARY KEY (`id`) ,
  CONSTRAINT `fk_Word_has_Language_Word1`
    FOREIGN KEY (`Word_id` )
    REFERENCES `rmaja85`.`Word` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Word_has_Language_Language1`
    FOREIGN KEY (`Language_id` )
    REFERENCES `rmaja85`.`Language` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_WordLanguage_User1`
    FOREIGN KEY (`User_id` )
    REFERENCES `rmaja85`.`User` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `rmaja85`.`Acceptation`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `rmaja85`.`Acceptation` ;

CREATE  TABLE IF NOT EXISTS `rmaja85`.`Acceptation` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `acceptation` VARCHAR(100) NOT NULL ,
  `creationDate` DATETIME NOT NULL ,
  `User_id` INT UNSIGNED NOT NULL ,
  PRIMARY KEY (`id`) ,
  CONSTRAINT `fk_Definition_User1`
    FOREIGN KEY (`User_id` )
    REFERENCES `rmaja85`.`User` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `rmaja85`.`WLA`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `rmaja85`.`WLA` ;

CREATE  TABLE IF NOT EXISTS `rmaja85`.`WLA` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `WordLanguage_id` INT UNSIGNED NOT NULL ,
  `Acceptation_id` INT UNSIGNED NULL ,
  `creationDate` DATETIME NOT NULL ,
  `User_id` INT UNSIGNED NOT NULL ,
  PRIMARY KEY (`id`) ,
  CONSTRAINT `fk_WordLanguage_has_Acceptation_WordLanguage1`
    FOREIGN KEY (`WordLanguage_id` )
    REFERENCES `rmaja85`.`WordLanguage` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_WordLanguage_has_Acceptation_Acceptation1`
    FOREIGN KEY (`Acceptation_id` )
    REFERENCES `rmaja85`.`Acceptation` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_WLA_User1`
    FOREIGN KEY (`User_id` )
    REFERENCES `rmaja85`.`User` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `rmaja85`.`Translation`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `rmaja85`.`Translation` ;

CREATE  TABLE IF NOT EXISTS `rmaja85`.`Translation` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `creationDate` DATETIME NOT NULL ,
  `User_id` INT UNSIGNED NOT NULL ,
  `WLA1_id` INT UNSIGNED NOT NULL ,
  `WLA2_id` INT UNSIGNED NOT NULL ,
  PRIMARY KEY (`id`) ,
  CONSTRAINT `fk_Translation_User1`
    FOREIGN KEY (`User_id` )
    REFERENCES `rmaja85`.`User` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Translation_WLA1`
    FOREIGN KEY (`WLA1_id` )
    REFERENCES `rmaja85`.`WLA` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Translation_WLA2`
    FOREIGN KEY (`WLA2_id` )
    REFERENCES `rmaja85`.`WLA` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `rmaja85`.`Phrase`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `rmaja85`.`Phrase` ;

CREATE  TABLE IF NOT EXISTS `rmaja85`.`Phrase` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `phrase` VARCHAR(140) NOT NULL ,
  `WLA_id` INT UNSIGNED NOT NULL ,
  PRIMARY KEY (`id`) ,
  CONSTRAINT `fk_Phrase_WLA1`
    FOREIGN KEY (`WLA_id` )
    REFERENCES `rmaja85`.`WLA` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `rmaja85`.`TranslationContext`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `rmaja85`.`TranslationContext` ;

CREATE  TABLE IF NOT EXISTS `rmaja85`.`TranslationContext` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `creationDate` DATETIME NOT NULL ,
  `Phrase1id` INT UNSIGNED NOT NULL ,
  `Phrase2id` INT UNSIGNED NOT NULL ,
  `User_id` INT UNSIGNED NOT NULL ,
  `Translation_id` INT UNSIGNED NOT NULL ,
  PRIMARY KEY (`id`) ,
  CONSTRAINT `fk_TranslatedPhrase_Phrase1`
    FOREIGN KEY (`Phrase2id` )
    REFERENCES `rmaja85`.`Phrase` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_TranslatedPhrase_Phrase2`
    FOREIGN KEY (`Phrase1id` )
    REFERENCES `rmaja85`.`Phrase` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_TranslatedPhrase_User1`
    FOREIGN KEY (`User_id` )
    REFERENCES `rmaja85`.`User` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Context_Translation1`
    FOREIGN KEY (`Translation_id` )
    REFERENCES `rmaja85`.`Translation` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `rmaja85`.`UserHasTranslation`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `rmaja85`.`UserHasTranslation` ;

CREATE  TABLE IF NOT EXISTS `rmaja85`.`UserHasTranslation` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `User_id` INT UNSIGNED NOT NULL ,
  `Translation_id` INT UNSIGNED NOT NULL ,
  `dateAdded` DATETIME NOT NULL ,
  `learned` TINYINT(1) NOT NULL ,
  PRIMARY KEY (`id`) ,
  CONSTRAINT `fk_User_has_Translation_User1`
    FOREIGN KEY (`User_id` )
    REFERENCES `rmaja85`.`User` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_User_has_Translation_Translation1`
    FOREIGN KEY (`Translation_id` )
    REFERENCES `rmaja85`.`Translation` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `rmaja85`.`UserCreatesTrAccep`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `rmaja85`.`UserCreatesTrAccep` ;

CREATE  TABLE IF NOT EXISTS `rmaja85`.`UserCreatesTrAccep` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `Translation_id` INT UNSIGNED NOT NULL ,
  `Acceptation_id` INT UNSIGNED NOT NULL ,
  `User_id` INT UNSIGNED NOT NULL ,
  `creationDate` DATETIME NOT NULL ,
  PRIMARY KEY (`id`) ,
  CONSTRAINT `fk_UserHasTrAccep_Translation1`
    FOREIGN KEY (`Translation_id` )
    REFERENCES `rmaja85`.`Translation` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_UserHasTrAccep_Acceptation1`
    FOREIGN KEY (`Acceptation_id` )
    REFERENCES `rmaja85`.`Acceptation` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_UserHasTrAccep_User1`
    FOREIGN KEY (`User_id` )
    REFERENCES `rmaja85`.`User` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `rmaja85`.`Report`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `rmaja85`.`Report` ;

CREATE  TABLE IF NOT EXISTS `rmaja85`.`Report` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `Translation_id` INT UNSIGNED NULL ,
  `TranslationContext_id` INT UNSIGNED NULL ,
  `UserCreatesTrAccep_id` INT UNSIGNED NULL ,
  `User_id` INT UNSIGNED NOT NULL ,
  PRIMARY KEY (`id`) ,
  CONSTRAINT `fk_Report_Translation1`
    FOREIGN KEY (`Translation_id` )
    REFERENCES `rmaja85`.`Translation` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Report_User1`
    FOREIGN KEY (`User_id` )
    REFERENCES `rmaja85`.`User` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Report_TranslationContext1`
    FOREIGN KEY (`TranslationContext_id` )
    REFERENCES `rmaja85`.`TranslationContext` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Report_UserCreatesTrAccep1`
    FOREIGN KEY (`UserCreatesTrAccep_id` )
    REFERENCES `rmaja85`.`UserCreatesTrAccep` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
