-- 用户
CREATE TABLE `sso_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `turename` varchar(50) DEFAULT NULL COMMENT '真实姓名',
  `password` char(32) DEFAULT NULL COMMENT '密码',
  `email` varchar(100) DEFAULT NULL COMMENT '电子邮箱',
  `phone` varchar(15) DEFAULT NULL COMMENT '手机号码',
  `lastLoginIp` varchar(20) DEFAULT NULL COMMENT '最后一次登录ip',
  `lastLoginTime` datetime DEFAULT NULL COMMENT '最后一次登录时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unit_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=79 DEFAULT CHARSET=utf8 COMMENT='用户表存储登录相关信息';

-- 墙，砖
CREATE TABLE `wall_block` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `x` int(11) DEFAULT NULL COMMENT '横坐标',
  `y` int(11) DEFAULT NULL COMMENT '纵坐标',
  `z` varchar(10) DEFAULT NULL COMMENT '竖坐标',
  `createDate` datetime DEFAULT NULL,
  `modifyDate` datetime DEFAULT NULL,
  `fk_owner_id` int(11) DEFAULT NULL COMMENT '拥有者',
  PRIMARY KEY (`id`),
  KEY `index_x` (`x`),
  KEY `index_y` (`y`),
  KEY `index_x_y_z` (`x`,`y`,`z`),
  KEY `index_owner_id` (`fk_owner_id`),
  CONSTRAINT `fk_owner_id` FOREIGN KEY (`fk_owner_id`) REFERENCES `sso_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


