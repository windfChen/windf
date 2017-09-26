CREATE TABLE `menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fk_parent_id` int(11) DEFAULT NULL,
  `parent_ids` varchar(255) DEFAULT NULL COMMENT '所有父节点的id用处搜索所有子节点',
  `name` varchar(50) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

