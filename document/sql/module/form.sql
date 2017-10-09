CREATE TABLE `form` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `code` varchar(50) NOT NULL,
  `note` varchar(50) DEFAULT NULL,
  `create_date` datetime NOT NULL,
  `update_date` datetime NOT NULL,
  `grid_view` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='自定义表格';

CREATE TABLE `form_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fk_form_id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `code` varchar(50) NOT NULL,
  `note` varchar(500) DEFAULT NULL,
  `create_date` datetime NOT NULL,
  `update_date` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_form_id` (`fk_form_id`),
  CONSTRAINT `fk_form` FOREIGN KEY (`fk_form_id`) REFERENCES `form` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='自定义表格存储项';

CREATE TABLE `form_item_user_value` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fk_user_id` int(11) NOT NULL,
  `fk_form_item_id` int(11) NOT NULL,
  `value` varchar(200) DEFAULT NULL,
  `create_date` datetime NOT NULL,
  `update_date` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='自定义表格存储内容';