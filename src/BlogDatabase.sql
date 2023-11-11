-- Database
DROP DATABASE IF EXISTS `blog`;

CREATE SCHEMA `blog` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- User Table
CREATE TABLE `blog`.`user` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(50) NULL DEFAULT NULL,
  `last_name` VARCHAR(50) NULL DEFAULT NULL,
  `username` VARCHAR(50) UNIQUE NOT NULL,
  `mobile` VARCHAR(15) NULL,
  `email` VARCHAR(50) NULL,
  `password_hash` VARCHAR(32) NOT NULL,
  `registered_at` DATETIME NOT NULL,
  `last_login` DATETIME NULL DEFAULT NULL,
  `intro` TINYTEXT NULL DEFAULT NULL,
  `profile` TEXT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `uq_mobile` (`mobile` ASC),
  UNIQUE INDEX `uq_email` (`email` ASC)
);

-- Post Table
CREATE TABLE `blog`.`post` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `author_id` BIGINT NOT NULL,
  `parent_id` BIGINT NULL DEFAULT NULL,
  `title` VARCHAR(75) NOT NULL,
  `meta_title` VARCHAR(100) NULL,
  `slug` VARCHAR(100) NOT NULL,
  `summary` TINYTEXT NULL,
  `published` TINYINT(1) NOT NULL DEFAULT 0,
  `created_at` DATETIME NOT NULL,
  `updated_at` DATETIME NULL DEFAULT NULL,
  `published_at` DATETIME NULL DEFAULT NULL,
  `content` TEXT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `uq_slug` (`slug` ASC),
  INDEX `idx_post_user` (`author_id` ASC),
  CONSTRAINT `fk_post_user`
    FOREIGN KEY (`author_id`)
    REFERENCES `blog`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);

-- Post Table - Additional Index and Constraint
ALTER TABLE `blog`.`post` 
ADD INDEX `idx_post_parent` (`parent_id` ASC);
ALTER TABLE `blog`.`post` 
ADD CONSTRAINT `fk_post_parent`
  FOREIGN KEY (`parent_id`)
  REFERENCES `blog`.`post` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

-- Post Meta Table
CREATE TABLE `blog`.`post_meta` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `post_id` BIGINT NOT NULL,
  `key` VARCHAR(50) NOT NULL,
  `content` TEXT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `idx_meta_post` (`post_id` ASC),
  UNIQUE INDEX `uq_post_meta` (`post_id` ASC, `key` ASC),
  CONSTRAINT `fk_meta_post`
    FOREIGN KEY (`post_id`)
    REFERENCES `blog`.`post` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
) ENGINE = InnoDB;

-- Post Comment Table
CREATE TABLE `blog`.`post_comment` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `post_id` BIGINT NOT NULL,
  `parent_id` BIGINT NULL DEFAULT NULL,
  `title` VARCHAR(100) NOT NULL,
  `published` TINYINT(1) NOT NULL DEFAULT 0,
  `created_at` DATETIME NOT NULL,
  `published_at` DATETIME NULL DEFAULT NULL,
  `content` TEXT NULL DEFAULT NULL,
  `user_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `idx_comment_post` (`post_id` ASC),
  INDEX `idx_comment_user` (`user_id` ASC),
  CONSTRAINT `fk_comment_post`
    FOREIGN KEY (`post_id`)
    REFERENCES `blog`.`post` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_comment_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `blog`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);

-- Post Comment Table - Additional Index and Constraint
ALTER TABLE `blog`.`post_comment` 
ADD INDEX `idx_comment_parent` (`parent_id` ASC);
ALTER TABLE `blog`.`post_comment` 
ADD CONSTRAINT `fk_comment_parent`
  FOREIGN KEY (`parent_id`)
  REFERENCES `blog`.`post_comment` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

-- Category Table
CREATE TABLE `blog`.`category` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `parent_id` BIGINT NULL DEFAULT NULL,
  `title` VARCHAR(75) NOT NULL,
  `meta_title` VARCHAR(100) NULL DEFAULT NULL,
  `slug` VARCHAR(100) NOT NULL,
  `content` TEXT NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
);

-- Category Table - Additional Index and Constraint
ALTER TABLE `blog`.`category` 
ADD INDEX `idx_category_parent` (`parent_id` ASC);
ALTER TABLE `blog`.`category` 
ADD CONSTRAINT `fk_category_parent`
  FOREIGN KEY (`parent_id`)
  REFERENCES `blog`.`category` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

-- Post Category Table
CREATE TABLE `blog`.`post_category` (
  `post_id` BIGINT NOT NULL,
  `category_id` BIGINT NOT NULL,
  PRIMARY KEY (`post_id`, `category_id`),
  INDEX `idx_pc_category` (`category_id` ASC),
  INDEX `idx_pc_post` (`post_id` ASC),
  CONSTRAINT `fk_pc_post`
    FOREIGN KEY (`post_id`)
    REFERENCES `blog`.`post` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_pc_category`
    FOREIGN KEY (`category_id`)
    REFERENCES `blog`.`category` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);

-- Tag Table
CREATE TABLE `blog`.`tag` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(75) NOT NULL,
  `meta_title` VARCHAR(100) DEFAULT NULL,
  `slug` VARCHAR(100) NOT NULL,
  `content` TEXT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Post Tag Table
CREATE TABLE `blog`.`post_tag` (
  `post_id` BIGINT NOT NULL,
  `tag_id` BIGINT NOT NULL,
  PRIMARY KEY (`post_id`,`tag_id`),
  INDEX `idx_pt_tag` (`tag_id`),
  INDEX `idx_pt_post` (`post_id`),
  CONSTRAINT `fk_pt_post`
    FOREIGN KEY (`post_id`)
    REFERENCES `blog`.`post` (`id`),
  CONSTRAINT `fk_pt_tag`
    FOREIGN KEY (`tag_id`)
    REFERENCES `blog`.`tag` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Role Table
CREATE TABLE `blog`.`role` (
  `user_id` BIGINT NOT NULL,
  `role` ENUM('ROLE_ADMIN', 'ROLE_AUTHOR', 'ROLE_GUEST') NOT NULL,
  PRIMARY KEY (`user_id`, `role`),
  INDEX `idx_role_user` (`user_id` ASC),
  CONSTRAINT `fk_role_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `blog`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

