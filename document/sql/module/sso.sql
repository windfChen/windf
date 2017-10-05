CREATE TABLE `sso` (
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
) ENGINE=InnoDB AUTO_INCREMENT=79 DEFAULT CHARSET=utf8 COMMENT='用户表存储登录相关信息'