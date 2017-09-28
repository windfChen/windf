CREATE TABLE `menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fk_parent_id` int(11) DEFAULT NULL,
  `parent_ids` varchar(255) DEFAULT NULL COMMENT '所有父节点的id用处搜索所有子节点',
  `code` varchar(50) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `priority_role` (
  `id` int(50) NOT NULL AUTO_INCREMENT,
  `code` varchar(10) DEFAULT NULL COMMENT '编号',
  `name` varchar(50) DEFAULT NULL COMMENT '名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `priority_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL COMMENT '名称',
  `serial_number` int(11) DEFAULT NULL COMMENT '序列号',
  `fk_priority_group_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_priority_group_id` (`fk_priority_group_id`),
  CONSTRAINT `fk_priority_group_id` FOREIGN KEY (`fk_priority_group_id`) REFERENCES `priority_group` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `priority` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fk_priority_group_id` int(11) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `url` varchar(200) NOT NULL COMMENT '权限对应的url地址',
  `method` varchar(10) DEFAULT NULL COMMENT '请求方式：GET、POST',
  PRIMARY KEY (`id`),
  KEY `priority_fk_priority_group_id` (`fk_priority_group_id`),
  CONSTRAINT `priority_fk_priority_group_id` FOREIGN KEY (`fk_priority_group_id`) REFERENCES `priority_group` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `priority_r_role_priority` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fk_role_id` int(11) NOT NULL,
  `fk_priority_id` int(11) NOT NULL,
  `flag_Isvalid` int(1) DEFAULT NULL COMMENT '是否有效',
  PRIMARY KEY (`id`),
  UNIQUE KEY `role_priority_unique` (`fk_role_id`,`fk_priority_id`),
  KEY `role_priority_fk_priority_id` (`fk_priority_id`),
  CONSTRAINT `role_priority_fk_priority_id` FOREIGN KEY (`fk_priority_id`) REFERENCES `priority` (`id`),
  CONSTRAINT `role_priority_fk_role_id` FOREIGN KEY (`fk_role_id`) REFERENCES `priority_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


