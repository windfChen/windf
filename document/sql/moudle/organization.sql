CREATE TABLE `organization` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fk_parent_id` int(11) DEFAULT NULL,
  `parent_ids` varchar(200) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `sort` int(255) DEFAULT NULL,
  `note` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_parent_id` (`fk_parent_id`),
  CONSTRAINT `organization_fk_parent_id` FOREIGN KEY (`fk_parent_id`) REFERENCES `organization` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;