CREATE TABLE `user` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `name` varchar(50) DEFAULT NULL,
    `create_date` datetime DEFAULT NULL,
    `update_date` datetime DEFAULT NULL,
    `fk_sso_user_id` int(11) DEFAULT NULL,
    `fk_role_id` int(11) DEFAULT NULL,
    `fk_organization_id` int(11) DEFAULT NULL,
    `sex` char(1) DEFAULT NULL,
    `test_test` varchar(20) DEFAULT ' 默认值' COMMENT '哈哈',


) ENGINE=InnoDB DEFAULT CHARSET=utf8;
