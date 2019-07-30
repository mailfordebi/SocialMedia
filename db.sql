CREATE TABLE `socialnetwork`.`user_info` (
  `userid` VARCHAR(50) NOT NULL,
  `first_name` VARCHAR(45) NULL,
  `last_name` VARCHAR(45) NULL,
  PRIMARY KEY (`userid`));

CREATE TABLE `socialnetwork`.`post` (
  `postid` VARCHAR(50) NOT NULL,
  `userid` VARCHAR(50) NOT NULL,
  `content` BLOB NULL,
  `published_date` DATETIME NULL,
  PRIMARY KEY (`postid`, `userid`));
  
CREATE TABLE `socialnetwork`.`follow` (
  `followerId` VARCHAR(50) NOT NULL,
  `followeeId` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`followerId`, `followeeId`));
  
ALTER TABLE `socialnetwork`.`follow` 
ADD INDEX `follow_follows_to_user_id_idx` (`followeeId` ASC) VISIBLE;
;

ALTER TABLE `socialnetwork`.`follow` 
ADD CONSTRAINT `follow_follower_to_user_id`
  FOREIGN KEY (`followerId`)
  REFERENCES `socialnetwork`.`user_info` (`userId`),
ADD CONSTRAINT `follow_followees_to_user_id`
  FOREIGN KEY (`followeeId`)
  REFERENCES `socialnetwork`.`user_info` (`userId`);
