CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `sex` char(1) DEFAULT NULL,
  `fk_org_id` int(11) DEFAULT NULL,
  `fk_role_id` int(11) DEFAULT NULL,
  `fk_sso_user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_role_id` (`fk_role_id`),
  KEY `fk_org_id` (`fk_org_id`),
  KEY `fk_sso_user_id` (`fk_sso_user_id`),
  CONSTRAINT `user_fk_org_od` FOREIGN KEY (`fk_org_id`) REFERENCES `organization` (`id`),
  CONSTRAINT `user_fk_role_id` FOREIGN KEY (`fk_role_id`) REFERENCES `priority_role` (`id`),
  CONSTRAINT `user_fk_sso_user_id` FOREIGN KEY (`fk_sso_user_id`) REFERENCES `sso_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;