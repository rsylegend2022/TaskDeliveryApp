SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `users`
-- ----------------------------
DROP TABLE IF EXISTS `task`;
CREATE TABLE `task` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(256) DEFAULT NULL,
  `description` TEXT DEFAULT NULL COMMENT '密码',
  `address` TEXT DEFAULT NULL,
  `type` varchar(64) DEFAULT NULL,
  `urgency` varchar(64) DEFAULT NULL,
  `status` varchar(64) DEFAULT NULL,
  `creator` varchar(64) DEFAULT NULL,
  `owner` varchar(64) DEFAULT NULL,
  `workload` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `name` varchar(256) NOT NULL,
  `password` varchar(256) DEFAULT NULL,
  `role` BOOL DEFAULT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;
