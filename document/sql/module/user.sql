CREATE TABLE `user` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `name` varchar(50) DEFAULT NULL,
    `create_date` datetime DEFAULT NULL,
    `update_date` datetime DEFAULT NULL,
    `fk_sso_user_id` int(11) DEFAULT NULL,
    `fk_priority_role_id` int(11) DEFAULT NULL,
    `fk_organization_id` int(11) DEFAULT NULL,
    `sex` char(1) DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `index__fk_sso_user_id` (`fk_sso_user_id`) USING BTREE,
    KEY `index__fk_priority_role_id` (`fk_priority_role_id`) USING BTREE,
    KEY `index__fk_organization_id` (`fk_organization_id`) USING BTREE,
    CONSTRAINT `user__fk_sso_user_id` FOREIGN KEY (`fk_sso_user_id`) REFERENCES `sso_user` (`id`),
    CONSTRAINT `user__fk_priority_role_id` FOREIGN KEY (`fk_priority_role_id`) REFERENCES `priority_role` (`id`),
    CONSTRAINT `user__fk_organization_id` FOREIGN KEY (`fk_organization_id`) REFERENCES `organization` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `test` (
    `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主见',
    `name` varchar(50) DEFAULT ' 默认值' COMMENT '哈哈',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='哈哈';
