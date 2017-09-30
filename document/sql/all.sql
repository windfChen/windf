/*
Navicat MySQL Data Transfer

Source Server         : 138
Source Server Version : 50528
Source Host           : 192.168.44.138:3306
Source Database       : gjs

Target Server Type    : MYSQL
Target Server Version : 50528
File Encoding         : 65001

Date: 2017-09-30 20:09:48
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for announcement
-- ----------------------------
DROP TABLE IF EXISTS `announcement`;
CREATE TABLE `announcement` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '公告ID',
  `announcetitle` varchar(2000) NOT NULL COMMENT '公告标题',
  `announcetext` text COMMENT '公告内容',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `release_time` datetime DEFAULT NULL COMMENT '发布时间',
  `begin_time` datetime NOT NULL COMMENT '开始时间',
  `end_time` datetime NOT NULL COMMENT '结束时间',
  `is_online` int(10) NOT NULL COMMENT '是否线上申报，1表示是，0表示否',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '发布状态：1-发布；0-未发布',
  `fk_form_id` int(11) DEFAULT NULL COMMENT '表格模板id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8 COMMENT='公告表存储公告信息';

-- ----------------------------
-- Records of announcement
-- ----------------------------
INSERT INTO `announcement` VALUES ('3', '北京关于2016年度教育部人文社会科学研究专项任务项目', '各省、自治区、直辖市党委教育工作部门、教育厅（教委），新疆生产建设兵团教育局，部属各高等学校：<br/>\r\n　　为深入学习贯彻党的十八大和十八届三中、四中、五中全会精神，落实立德树人根本任务，加强高校思想政治教育队伍建设，教育部思政司决定继续实施“思想政治教育中青年杰出人才支持计划”。现将有关申报工作通知如下：<br/>\r\n　　<strong>一、指导思想和培养目标</strong> <br/>\r\n　　全面加强思想政治教育队伍建设，引导和鼓励中青年思想政治教育工作者注重理论水平和综合素质的提升，探索和创新实践工作模式。培养一批坚持正确的政治方向、工作业绩突出、学术水平较高、理论宣讲能力较强的名嘴、名家，推出一批理论联系实际的有影响力、说服力的名篇、名著，提升思想政治教育科学化水平。<br/><br/> \r\n　　<strong>二、申报条件</strong> <br/>\r\n　　1．热爱祖国，热爱人民，拥护中国共产党的领导，拥护中国特色社会主义制度。遵守宪法和法律法规。贯彻党的教育方针，热爱党的教育事业，坚持立德树人，为人师表。<br/> \r\n　　2．在思想政治教育领域的理论研究、教育教学、人才培养等方面具有一定的成果和良好的发展潜力；在思想政治教育工作制度体系建设、工作项目设计、内容形式拓展、方法路径创新等提升思想政治教育工作科学化水平的实践中具有先进的理念和一定的典型经验。<br/> \r\n　　3. 具有一定的科学研究能力、实践创新能力和理论宣讲能力，能结合工作实践在思想政治教育领域开展深入研究；能够领衔较高水平的科研团队，主持省部级以上科研课题；能推动理论与实践的结合，促进成果的转化应用；能在大学生中有效开展理论阐释和主题教育。<br/> \r\n　　4．原则上具有副高级以上专业技术职称，年龄不超过45周岁。<br/> \r\n　　5．申报范围：全国普通高等学校从事思想政治教育研究和实践的相关人员，鼓励其他相关学科领域有志于从事思想政治教育研究与实践的优秀人才报名参加。<br/> <br/> ', '2017-09-26 17:47:20', '2017-09-27 20:53:35', '2017-09-26 17:47:23', '2017-09-26 17:47:26', '1', '1', '1');
INSERT INTO `announcement` VALUES ('29', '关于2016年度教育部人文社会科学研究专项任务项目', '各省、自治区、直辖市党委教育工作部门、教育厅（教委），新疆生产建设兵团教育局，部属各高等学校：<br/>\r\n　　为深入学习贯彻党的十八大和十八届三中、四中、五中全会精神，落实立德树人根本任务，加强高校思想政治教育队伍建设，教育部思政司决定继续实施“思想政治教育中青年杰出人才支持计划”。现将有关申报工作通知如下：<br/>\r\n　　<strong>一、指导思想和培养目标</strong> <br/>\r\n　　全面加强思想政治教育队伍建设，引导和鼓励中青年思想政治教育工作者注重理论水平和综合素质的提升，探索和创新实践工作模式。培养一批坚持正确的政治方向、工作业绩突出、学术水平较高、理论宣讲能力较强的名嘴、名家，推出一批理论联系实际的有影响力、说服力的名篇、名著，提升思想政治教育科学化水平。<br/><br/> \r\n　　<strong>二、申报条件</strong> <br/>\r\n　　1．热爱祖国，热爱人民，拥护中国共产党的领导，拥护中国特色社会主义制度。遵守宪法和法律法规。贯彻党的教育方针，热爱党的教育事业，坚持立德树人，为人师表。<br/> \r\n　　2．在思想政治教育领域的理论研究、教育教学、人才培养等方面具有一定的成果和良好的发展潜力；在思想政治教育工作制度体系建设、工作项目设计、内容形式拓展、方法路径创新等提升思想政治教育工作科学化水平的实践中具有先进的理念和一定的典型经验。<br/> \r\n　　3. 具有一定的科学研究能力、实践创新能力和理论宣讲能力，能结合工作实践在思想政治教育领域开展深入研究；能够领衔较高水平的科研团队，主持省部级以上科研课题；能推动理论与实践的结合，促进成果的转化应用；能在大学生中有效开展理论阐释和主题教育。<br/> \r\n　　4．原则上具有副高级以上专业技术职称，年龄不超过45周岁。<br/> \r\n　　5．申报范围：全国普通高等学校从事思想政治教育研究和实践的相关人员，鼓励其他相关学科领域有志于从事思想政治教育研究与实践的优秀人才报名参加。<br/> <br/> ', '2017-09-27 20:36:52', '2017-09-27 20:39:17', '2017-10-02 00:00:00', '2017-10-05 00:00:00', '1', '1', '1');
INSERT INTO `announcement` VALUES ('51', 'test', '<p>ettatet a</p>', '2017-09-29 11:44:35', null, '2017-09-29 00:00:00', '2017-10-07 00:00:00', '1', '0', '1');
INSERT INTO `announcement` VALUES ('53', 'tttttt', '<p>11111111111</p>', '2017-09-29 14:13:54', null, '2017-08-27 00:00:00', '2017-09-10 00:00:00', '1', '0', '2');

-- ----------------------------
-- Table structure for announcement_role
-- ----------------------------
DROP TABLE IF EXISTS `announcement_role`;
CREATE TABLE `announcement_role` (
  `announcement_id` int(11) NOT NULL COMMENT '公告ID',
  `role_id` int(11) NOT NULL COMMENT '发布对象ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='公告与用户关联表';

-- ----------------------------
-- Records of announcement_role
-- ----------------------------
INSERT INTO `announcement_role` VALUES ('29', '1');
INSERT INTO `announcement_role` VALUES ('29', '2');
INSERT INTO `announcement_role` VALUES ('3', '1');

-- ----------------------------
-- Table structure for example
-- ----------------------------
DROP TABLE IF EXISTS `example`;
CREATE TABLE `example` (
  `id` varchar(50) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `nick_name` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of example
-- ----------------------------

-- ----------------------------
-- Table structure for form
-- ----------------------------
DROP TABLE IF EXISTS `form`;
CREATE TABLE `form` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `code` varchar(50) NOT NULL,
  `note` varchar(50) DEFAULT NULL,
  `create_date` datetime NOT NULL,
  `update_date` datetime NOT NULL,
  `grid_view` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of form
-- ----------------------------
INSERT INTO `form` VALUES ('1', '2017年全国高校基层党组织建设情况统计表（表一）', '1', null, '2017-09-29 10:25:40', '2017-09-29 10:25:43', '{ \"info\" : { \"title\" : \"2017年全国高校基层党组织建设情况统计表（表一）\", \"foot\" : \"<pre>说明：1.本表行列关系如下:如，列关系：01=02+03+04，行关系：1=2+3+4+5\\r\\n 2.统计数据截止至2017年6月30日。</pre>\" }, \"title\" : [{ \"text\" : \"\", \"rowSpan\" : 2, \"colSpan\" : 2, \"subData\" : [{ \"text\" : \"甲\", \"colSpan\" : 2, \"code\" : \"e3r2\" }] },{ \"text\" : \"编号\", \"code\" : \"e3g4\", \"width\" : 40, \"rowSpan\" : 2, \"colSpan\" : 1, \"subData\" : [{ \"text\" : \"乙\", \"code\" : \"e3r3\" }] },{ \"text\" : \"高校总数\", \"width\" : 60, \"rowSpan\" : 2, \"colSpan\" : 1, \"subData\" : [{ \"text\" : \"1\", \"code\" : \"e3t4\" }] },{ \"text\" : \"建立党委的高校\", \"height\" : 40, \"subData\" : [{ \"text\" : \"小计\", \"height\" : 40, \"width\" : 60, \"subData\" : [{ \"text\" : \"2\", \"code\" : \"e2g4\" }] }] },{ \"text\" : \"建立总支部的高校\", \"height\" : 40, \"subData\" : [{ \"text\" : \"小计\", \"height\" : 40, \"width\" : 60, \"subData\" : [{ \"text\" : \"3\", \"code\" : \"e2g5\" }] }] },{ \"text\" : \"建立支部的高校\", \"height\" : 40, \"subData\" : [{ \"text\" : \"小计\", \"height\" : 40, \"width\" : 60, \"subData\" : [{ \"text\" : \"4\", \"code\" : \"e2g9\" }] }] },{ \"text\" : \"未建立党组织的高校\", \"height\" : 40, \"subData\" : [{ \"text\" : \"小计\", \"height\" : 40, \"width\" : 60, \"subData\" : [{ \"text\" : \"5\", \"code\" : \"e3g9\" }] }] },{ \"text\" : \"高校基层党组织总数\", \"rowSpan\" : 1, \"colSpan\" : 6, \"subData\" : [{ \"text\" : \"高校党委数\", \"width\" : 60, \"subData\" : [{ \"text\" : \"6\", \"code\" : \"e3w8\" }] },{ \"text\" : \"院系党员数\", \"width\" : 60, \"subData\" : [{ \"text\" : \"7\", \"code\" : \"e3w5\" }] },{ \"text\" : \"院系党总支数\", \"width\" : 60, \"subData\" : [{ \"text\" : \"8\", \"code\" : \"e3w6\" }] },{ \"text\" : \"教职工党总支（党支部）数（含离退休人员党支部数）\", \"width\" : 60, \"subData\" : [{ \"text\" : \"9\", \"code\" : \"e3w4\" }] },{ \"text\" : \"离退休人员党支部数\", \"width\" : 60, \"subData\" : [{ \"text\" : \"10\", \"code\" : \"e3w2\" }] },{ \"text\" : \"学生党支部数\", \"width\" : 60, \"subData\" : [{ \"text\" : \"11\", \"code\" : \"e3y4\" }] }] }], \"left\": [{ \"text\": \"总计\", \"rowSpan\" : 1, \"colSpan\" : 2, \"subData\" : [{ \"text\": \"01\", \"code\" : \"t1t1\" }] },{ \"text\": \"合计\", \"rowSpan\" : 3, \"colSpan\" : 1, \"width\" : 30, \"subData\" : [{ \"text\": \"本科院校\", \"rowSpan\" : 1, \"colSpan\" : 1, \"height\" : 60, \"width\" : 60, \"subData\" : [{ \"text\": \"02\", \"code\" : \"t2t1\" }] },{ \"text\": \"专科院校（含职业技术学院）\", \"rowSpan\" : 1, \"colSpan\" : 1, \"height\" : 60, \"subData\" : [{ \"text\": \"03\", \"code\" : \"t4t1\" }] },{ \"text\": \"民办高校（含独立学院）\", \"rowSpan\" : 1, \"colSpan\" : 1, \"height\" : 60, \"subData\" : [{ \"text\": \"04\", \"code\" : \"t1t7\" }] }] }] }');
INSERT INTO `form` VALUES ('2', '培 训 回 执', '2', null, '2017-09-29 10:26:09', '2017-09-29 10:26:12', '{ \"info\": { \"title\": \"培 训 回 执\", \"foot\": \"\" }, \"titleCount\" : 6, \"data\": [{ \"type\": \"label\", \"text\": \"姓名\", \"height\": 60, \"width\": 120 }, { \"type\": \"input\", \"code\": \"a1b1\", \"width\": 120 }, { \"type\": \"label\", \"text\": \"性别\", \"width\": 60 }, { \"type\": \"input\", \"code\": \"a1b2\", \"width\": 60 }, { \"type\": \"label\", \"text\": \"民族\", \"width\": 60 }, { \"type\": \"input\", \"code\": \"a1b3\", \"width\": 60 }, { \"type\": \"label\", \"text\": \"工作单位及职务\", \"height\": 120 }, { \"type\": \"texarea\", \"code\": \"a2b1\", \"colSpan\": 5 }, { \"type\": \"label\", \"text\": \"办公电话\", \"height\": 60 }, { \"type\": \"input\", \"code\": \"a3b1\", \"colSpan\": 2 }, { \"type\": \"label\", \"text\": \"手机\" }, { \"type\": \"input\", \"code\": \"a3b2\", \"colSpan\": 2 }, { \"type\": \"label\", \"text\": \"电子信箱\", \"height\": 60 }, { \"type\": \"input\", \"code\": \"a4b1\", \"colSpan\": 2 }, { \"type\": \"label\", \"text\": \"传真\" }, { \"type\": \"input\", \"code\": \"a4b2\", \"colSpan\": 2 }, { \"type\": \"label\", \"text\": \"通讯地址\", \"height\": 60 }, { \"type\": \"input\", \"code\": \"a5b1\", \"colSpan\": 5 }, { \"type\": \"label\", \"text\": \"备注\", \"height\": 60 }, { \"type\": \"input\", \"code\": \"a6b1\", \"colSpan\": 5 }], \"title\": [], \"left\": [] }');

-- ----------------------------
-- Table structure for form_item
-- ----------------------------
DROP TABLE IF EXISTS `form_item`;
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
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of form_item
-- ----------------------------
INSERT INTO `form_item` VALUES ('1', '1', '党员数', 't1t1-e3r3', null, '2017-09-18 14:29:49', '2017-09-18 14:29:52');
INSERT INTO `form_item` VALUES ('2', '1', '党员数', 't1t1-e3t4', null, '2017-09-18 14:01:09', '2017-09-18 14:01:12');
INSERT INTO `form_item` VALUES ('3', '1', '党员数', 't1t1-e2g4', null, '2017-09-18 14:01:09', '2017-09-18 14:01:12');
INSERT INTO `form_item` VALUES ('4', '1', '党员数', 't1t1-e2g5', null, '2017-09-18 14:01:09', '2017-09-18 14:01:12');
INSERT INTO `form_item` VALUES ('5', '1', '党员数', 't1t1-e2g9', null, '2017-09-18 14:01:09', '2017-09-18 14:01:12');
INSERT INTO `form_item` VALUES ('6', '1', '党员数', 't1t1-e3g9', null, '2017-09-18 14:01:09', '2017-09-18 14:01:12');
INSERT INTO `form_item` VALUES ('7', '1', '党员数', 't1t1-e3w8', null, '2017-09-18 14:01:09', '2017-09-18 14:01:12');
INSERT INTO `form_item` VALUES ('8', '1', '党员数', 't1t1-e3w5', null, '2017-09-18 14:01:09', '2017-09-18 14:01:12');
INSERT INTO `form_item` VALUES ('9', '1', '党员数', 't1t1-e3w6', null, '2017-09-18 14:01:09', '2017-09-18 14:01:12');
INSERT INTO `form_item` VALUES ('10', '1', '党员数', 't1t1-e3w4', null, '2017-09-18 14:01:09', '2017-09-18 14:01:12');
INSERT INTO `form_item` VALUES ('11', '1', '党员数', 't1t1-e3w2', null, '2017-09-18 14:01:09', '2017-09-18 14:01:12');
INSERT INTO `form_item` VALUES ('12', '1', '党员数', 't2t1-e3r3', null, '2017-09-18 14:01:09', '2017-09-18 14:01:12');
INSERT INTO `form_item` VALUES ('13', '1', '党员数', 't2t1-e3t4', null, '2017-09-18 14:01:09', '2017-09-18 14:01:12');
INSERT INTO `form_item` VALUES ('14', '1', '党员数', 't2t1-e2g4', null, '2017-09-18 14:01:09', '2017-09-18 14:01:12');
INSERT INTO `form_item` VALUES ('15', '1', '党员数', 't2t1-e2g5', null, '2017-09-18 14:01:09', '2017-09-18 14:01:12');
INSERT INTO `form_item` VALUES ('16', '1', '党员数', 't2t1-e2g9', null, '2017-09-18 14:01:09', '2017-09-18 14:01:12');
INSERT INTO `form_item` VALUES ('17', '1', '党员数', 't2t1-e3g9', null, '2017-09-18 14:01:09', '2017-09-18 14:01:12');
INSERT INTO `form_item` VALUES ('18', '1', '党员数', 't2t1-e3w8', null, '2017-09-18 14:01:09', '2017-09-18 14:01:12');
INSERT INTO `form_item` VALUES ('19', '1', '党员数', 't2t1-e3w5', null, '2017-09-18 14:01:09', '2017-09-18 14:01:12');
INSERT INTO `form_item` VALUES ('20', '1', '党员数', 't2t1-e3w6', null, '2017-09-18 14:01:09', '2017-09-18 14:01:12');
INSERT INTO `form_item` VALUES ('21', '1', '党员数', 't2t1-e3w4', null, '2017-09-18 14:01:09', '2017-09-18 14:01:12');
INSERT INTO `form_item` VALUES ('22', '1', '党员数', 't2t1-e3w2', null, '2017-09-18 14:01:09', '2017-09-18 14:01:12');
INSERT INTO `form_item` VALUES ('23', '1', '党员数', 't4t1-e3r3', null, '2017-09-18 14:01:09', '2017-09-18 14:01:12');
INSERT INTO `form_item` VALUES ('24', '1', '党员数', 't4t1-e3t4', null, '2017-09-18 14:01:09', '2017-09-18 14:01:12');
INSERT INTO `form_item` VALUES ('25', '1', '党员数', 't4t1-e2g4', null, '2017-09-18 14:01:09', '2017-09-18 14:01:12');
INSERT INTO `form_item` VALUES ('26', '1', '党员数', 't4t1-e2g5', null, '2017-09-18 14:01:09', '2017-09-18 14:01:12');
INSERT INTO `form_item` VALUES ('27', '1', '党员数', 't4t1-e2g9', null, '2017-09-18 14:01:09', '2017-09-18 14:01:12');
INSERT INTO `form_item` VALUES ('28', '1', '党员数', 't4t1-e3g9', null, '2017-09-18 14:01:09', '2017-09-18 14:01:12');
INSERT INTO `form_item` VALUES ('29', '1', '党员数', 't4t1-e3w8', null, '2017-09-18 14:01:09', '2017-09-18 14:01:12');
INSERT INTO `form_item` VALUES ('30', '1', '党员数', 't4t1-e3w5', null, '2017-09-18 14:01:09', '2017-09-18 14:01:12');
INSERT INTO `form_item` VALUES ('31', '1', '党员数', 't4t1-e3w6', null, '2017-09-18 14:01:09', '2017-09-18 14:01:12');
INSERT INTO `form_item` VALUES ('32', '1', '党员数', 't4t1-e3w4', null, '2017-09-18 14:01:09', '2017-09-18 14:01:12');
INSERT INTO `form_item` VALUES ('33', '1', '党员数', 't4t1-e3w2', null, '2017-09-18 14:01:09', '2017-09-18 14:01:12');
INSERT INTO `form_item` VALUES ('34', '1', '党员数', 't1t7-e3r3', null, '2017-09-18 14:01:09', '2017-09-18 14:01:12');
INSERT INTO `form_item` VALUES ('35', '1', '党员数', 't1t7-e3t4', null, '2017-09-18 14:01:09', '2017-09-18 14:01:12');
INSERT INTO `form_item` VALUES ('36', '1', '党员数', 't1t7-e2g4', null, '2017-09-18 14:01:09', '2017-09-18 14:01:12');
INSERT INTO `form_item` VALUES ('37', '1', '党员数', 't1t7-e2g5', null, '2017-09-18 14:01:09', '2017-09-18 14:01:12');
INSERT INTO `form_item` VALUES ('38', '1', '党员数', 't1t7-e2g9', null, '2017-09-18 14:01:09', '2017-09-18 14:01:12');
INSERT INTO `form_item` VALUES ('39', '1', '党员数', 't1t7-e3g9', null, '2017-09-18 14:01:09', '2017-09-18 14:01:12');
INSERT INTO `form_item` VALUES ('40', '1', '党员数', 't1t7-e3w8', null, '2017-09-18 14:01:09', '2017-09-18 14:01:12');
INSERT INTO `form_item` VALUES ('41', '1', '党员数', 't1t7-e3w5', null, '2017-09-18 14:01:09', '2017-09-18 14:01:12');
INSERT INTO `form_item` VALUES ('42', '1', '党员数', 't1t7-e3w6', null, '2017-09-18 14:01:09', '2017-09-18 14:01:12');
INSERT INTO `form_item` VALUES ('43', '1', '党员数', 't1t7-e3w4', null, '2017-09-18 14:01:09', '2017-09-18 14:01:12');
INSERT INTO `form_item` VALUES ('44', '1', '党员数', 't1t7-e3w2', null, '2017-09-18 14:01:09', '2017-09-18 14:01:12');
INSERT INTO `form_item` VALUES ('45', '2', '回执信息', 'a1b1', null, '2017-09-29 09:54:35', '2017-09-29 09:54:35');
INSERT INTO `form_item` VALUES ('46', '2', '回执信息', 'a1b2', null, '2017-09-29 09:55:12', '2017-09-29 09:55:12');
INSERT INTO `form_item` VALUES ('47', '2', '回执信息', 'a1b3', null, '2017-09-29 09:55:12', '2017-09-29 09:55:12');
INSERT INTO `form_item` VALUES ('48', '2', '回执信息', 'a2b1', null, '2017-09-29 09:55:12', '2017-09-29 09:55:12');
INSERT INTO `form_item` VALUES ('49', '2', '回执信息', 'a3b2', null, '2017-09-29 09:55:12', '2017-09-29 09:55:12');
INSERT INTO `form_item` VALUES ('50', '2', '回执信息', 'a4b1', null, '2017-09-29 09:55:12', '2017-09-29 09:55:12');
INSERT INTO `form_item` VALUES ('51', '2', '回执信息', 'a4b2', null, '2017-09-29 09:55:12', '2017-09-29 09:55:12');
INSERT INTO `form_item` VALUES ('52', '2', '回执信息', 'a5b1', null, '2017-09-29 09:55:12', '2017-09-29 09:55:12');
INSERT INTO `form_item` VALUES ('53', '2', '回执信息', 'a6b1', null, '2017-09-29 09:55:12', '2017-09-29 09:55:12');
INSERT INTO `form_item` VALUES ('54', '2', '回执信息', 'a3b1', null, '2017-09-29 10:54:34', '2017-09-29 10:54:36');

-- ----------------------------
-- Table structure for form_item_user_value
-- ----------------------------
DROP TABLE IF EXISTS `form_item_user_value`;
CREATE TABLE `form_item_user_value` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fk_user_id` int(11) NOT NULL,
  `fk_form_item_id` int(11) NOT NULL,
  `value` varchar(200) DEFAULT NULL,
  `create_date` datetime NOT NULL,
  `update_date` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=382 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of form_item_user_value
-- ----------------------------
INSERT INTO `form_item_user_value` VALUES ('142', '2', '45', 'chenxiao', '2017-09-29 10:59:08', '2017-09-29 10:59:08');
INSERT INTO `form_item_user_value` VALUES ('143', '2', '46', '女', '2017-09-29 10:59:08', '2017-09-29 10:59:08');
INSERT INTO `form_item_user_value` VALUES ('144', '2', '47', '汗', '2017-09-29 10:59:08', '2017-09-29 10:59:08');
INSERT INTO `form_item_user_value` VALUES ('145', '2', '48', '北京网梯', '2017-09-29 10:59:08', '2017-09-29 10:59:08');
INSERT INTO `form_item_user_value` VALUES ('146', '2', '49', '7890', '2017-09-29 10:59:08', '2017-09-29 10:59:08');
INSERT INTO `form_item_user_value` VALUES ('147', '2', '50', '098767', '2017-09-29 10:59:08', '2017-09-29 10:59:08');
INSERT INTO `form_item_user_value` VALUES ('148', '2', '51', '54321', '2017-09-29 10:59:08', '2017-09-29 10:59:08');
INSERT INTO `form_item_user_value` VALUES ('149', '2', '52', '去玩儿', '2017-09-29 10:59:08', '2017-09-29 10:59:08');
INSERT INTO `form_item_user_value` VALUES ('150', '2', '53', '阿达', '2017-09-29 10:59:08', '2017-09-29 10:59:08');
INSERT INTO `form_item_user_value` VALUES ('151', '2', '54', '123456', '2017-09-29 10:59:08', '2017-09-29 10:59:08');
INSERT INTO `form_item_user_value` VALUES ('152', '1', '45', '陈亚峰', '2017-09-29 11:00:18', '2017-09-29 11:00:18');
INSERT INTO `form_item_user_value` VALUES ('153', '1', '46', '男', '2017-09-29 11:00:18', '2017-09-29 11:00:18');
INSERT INTO `form_item_user_value` VALUES ('154', '1', '47', '汉', '2017-09-29 11:00:18', '2017-09-29 11:00:18');
INSERT INTO `form_item_user_value` VALUES ('155', '1', '48', '工作单位及职务', '2017-09-29 11:00:18', '2017-09-29 11:00:18');
INSERT INTO `form_item_user_value` VALUES ('156', '1', '49', '手机', '2017-09-29 11:00:18', '2017-09-29 11:00:18');
INSERT INTO `form_item_user_value` VALUES ('157', '1', '50', '电子信箱', '2017-09-29 11:00:18', '2017-09-29 11:00:18');
INSERT INTO `form_item_user_value` VALUES ('158', '1', '51', '传真	', '2017-09-29 11:00:18', '2017-09-29 11:00:18');
INSERT INTO `form_item_user_value` VALUES ('159', '1', '52', '通讯地址', '2017-09-29 11:00:18', '2017-09-29 11:00:18');
INSERT INTO `form_item_user_value` VALUES ('160', '1', '53', '备注', '2017-09-29 11:00:18', '2017-09-29 11:00:18');
INSERT INTO `form_item_user_value` VALUES ('161', '1', '54', '办公电话', '2017-09-29 11:00:18', '2017-09-29 11:00:18');
INSERT INTO `form_item_user_value` VALUES ('338', '24', '1', 'ere', '2017-09-29 12:49:24', '2017-09-29 12:49:24');
INSERT INTO `form_item_user_value` VALUES ('339', '24', '2', 'te', '2017-09-29 12:49:24', '2017-09-29 12:49:24');
INSERT INTO `form_item_user_value` VALUES ('340', '24', '3', 'te', '2017-09-29 12:49:24', '2017-09-29 12:49:24');
INSERT INTO `form_item_user_value` VALUES ('341', '24', '4', 'e', '2017-09-29 12:49:24', '2017-09-29 12:49:24');
INSERT INTO `form_item_user_value` VALUES ('342', '24', '5', null, '2017-09-29 12:49:24', '2017-09-29 12:49:24');
INSERT INTO `form_item_user_value` VALUES ('343', '24', '6', null, '2017-09-29 12:49:24', '2017-09-29 12:49:24');
INSERT INTO `form_item_user_value` VALUES ('344', '24', '7', null, '2017-09-29 12:49:24', '2017-09-29 12:49:24');
INSERT INTO `form_item_user_value` VALUES ('345', '24', '8', null, '2017-09-29 12:49:24', '2017-09-29 12:49:24');
INSERT INTO `form_item_user_value` VALUES ('346', '24', '9', null, '2017-09-29 12:49:24', '2017-09-29 12:49:24');
INSERT INTO `form_item_user_value` VALUES ('347', '24', '10', null, '2017-09-29 12:49:24', '2017-09-29 12:49:24');
INSERT INTO `form_item_user_value` VALUES ('348', '24', '11', null, '2017-09-29 12:49:24', '2017-09-29 12:49:24');
INSERT INTO `form_item_user_value` VALUES ('349', '24', '12', null, '2017-09-29 12:49:24', '2017-09-29 12:49:24');
INSERT INTO `form_item_user_value` VALUES ('350', '24', '13', null, '2017-09-29 12:49:24', '2017-09-29 12:49:24');
INSERT INTO `form_item_user_value` VALUES ('351', '24', '14', null, '2017-09-29 12:49:24', '2017-09-29 12:49:24');
INSERT INTO `form_item_user_value` VALUES ('352', '24', '15', 'e', '2017-09-29 12:49:24', '2017-09-29 12:49:24');
INSERT INTO `form_item_user_value` VALUES ('353', '24', '16', 'e', '2017-09-29 12:49:24', '2017-09-29 12:49:24');
INSERT INTO `form_item_user_value` VALUES ('354', '24', '17', 'e', '2017-09-29 12:49:24', '2017-09-29 12:49:24');
INSERT INTO `form_item_user_value` VALUES ('355', '24', '18', null, '2017-09-29 12:49:24', '2017-09-29 12:49:24');
INSERT INTO `form_item_user_value` VALUES ('356', '24', '19', null, '2017-09-29 12:49:24', '2017-09-29 12:49:24');
INSERT INTO `form_item_user_value` VALUES ('357', '24', '20', null, '2017-09-29 12:49:24', '2017-09-29 12:49:24');
INSERT INTO `form_item_user_value` VALUES ('358', '24', '21', null, '2017-09-29 12:49:24', '2017-09-29 12:49:24');
INSERT INTO `form_item_user_value` VALUES ('359', '24', '22', null, '2017-09-29 12:49:24', '2017-09-29 12:49:24');
INSERT INTO `form_item_user_value` VALUES ('360', '24', '23', null, '2017-09-29 12:49:24', '2017-09-29 12:49:24');
INSERT INTO `form_item_user_value` VALUES ('361', '24', '24', null, '2017-09-29 12:49:24', '2017-09-29 12:49:24');
INSERT INTO `form_item_user_value` VALUES ('362', '24', '25', null, '2017-09-29 12:49:24', '2017-09-29 12:49:24');
INSERT INTO `form_item_user_value` VALUES ('363', '24', '26', null, '2017-09-29 12:49:24', '2017-09-29 12:49:24');
INSERT INTO `form_item_user_value` VALUES ('364', '24', '27', 'e', '2017-09-29 12:49:24', '2017-09-29 12:49:24');
INSERT INTO `form_item_user_value` VALUES ('365', '24', '28', 'e', '2017-09-29 12:49:24', '2017-09-29 12:49:24');
INSERT INTO `form_item_user_value` VALUES ('366', '24', '29', null, '2017-09-29 12:49:24', '2017-09-29 12:49:24');
INSERT INTO `form_item_user_value` VALUES ('367', '24', '30', null, '2017-09-29 12:49:24', '2017-09-29 12:49:24');
INSERT INTO `form_item_user_value` VALUES ('368', '24', '31', null, '2017-09-29 12:49:24', '2017-09-29 12:49:24');
INSERT INTO `form_item_user_value` VALUES ('369', '24', '32', null, '2017-09-29 12:49:24', '2017-09-29 12:49:24');
INSERT INTO `form_item_user_value` VALUES ('370', '24', '33', null, '2017-09-29 12:49:24', '2017-09-29 12:49:24');
INSERT INTO `form_item_user_value` VALUES ('371', '24', '34', null, '2017-09-29 12:49:24', '2017-09-29 12:49:24');
INSERT INTO `form_item_user_value` VALUES ('372', '24', '35', null, '2017-09-29 12:49:24', '2017-09-29 12:49:24');
INSERT INTO `form_item_user_value` VALUES ('373', '24', '36', null, '2017-09-29 12:49:24', '2017-09-29 12:49:24');
INSERT INTO `form_item_user_value` VALUES ('374', '24', '37', null, '2017-09-29 12:49:24', '2017-09-29 12:49:24');
INSERT INTO `form_item_user_value` VALUES ('375', '24', '38', null, '2017-09-29 12:49:24', '2017-09-29 12:49:24');
INSERT INTO `form_item_user_value` VALUES ('376', '24', '39', null, '2017-09-29 12:49:24', '2017-09-29 12:49:24');
INSERT INTO `form_item_user_value` VALUES ('377', '24', '40', null, '2017-09-29 12:49:24', '2017-09-29 12:49:24');
INSERT INTO `form_item_user_value` VALUES ('378', '24', '41', null, '2017-09-29 12:49:24', '2017-09-29 12:49:24');
INSERT INTO `form_item_user_value` VALUES ('379', '24', '42', null, '2017-09-29 12:49:24', '2017-09-29 12:49:24');
INSERT INTO `form_item_user_value` VALUES ('380', '24', '43', null, '2017-09-29 12:49:24', '2017-09-29 12:49:24');
INSERT INTO `form_item_user_value` VALUES ('381', '24', '44', null, '2017-09-29 12:49:24', '2017-09-29 12:49:24');

-- ----------------------------
-- Table structure for organization
-- ----------------------------
DROP TABLE IF EXISTS `organization`;
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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of organization
-- ----------------------------
INSERT INTO `organization` VALUES ('1', null, null, '高教社', '0', '高教社');
INSERT INTO `organization` VALUES ('2', null, null, '北师大', '0', '北师大');
INSERT INTO `organization` VALUES ('3', null, null, '北交大', '0', '北交大');

-- ----------------------------
-- Table structure for priority
-- ----------------------------
DROP TABLE IF EXISTS `priority`;
CREATE TABLE `priority` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fk_priority_group_id` int(11) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `url` varchar(500) NOT NULL COMMENT '权限对应的url地址',
  `method` varchar(10) DEFAULT NULL COMMENT '请求方式：GET、POST',
  PRIMARY KEY (`id`),
  KEY `priority_fk_priority_group_id` (`fk_priority_group_id`),
  CONSTRAINT `priority_fk_priority_group_id` FOREIGN KEY (`fk_priority_group_id`) REFERENCES `priority_group` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of priority
-- ----------------------------
INSERT INTO `priority` VALUES ('3', null, '添加权限', '/manage/priority/,/manage/priority/list/,/manage/priority/grid/,/manage/priority/detail/', 'POST');
INSERT INTO `priority` VALUES ('4', null, '管理权限', '/manage/priority/update,/manage/priority/save,/manage/priority/delete', 'POST');
INSERT INTO `priority` VALUES ('6', null, '查看角色', '/manage/role/,/manage/role/list/,/manage/role/grid/,/manage/role/detail/', null);
INSERT INTO `priority` VALUES ('7', null, '管理角色', '/manage/role/update,/manage/role/save,/manage/role/delete', null);
INSERT INTO `priority` VALUES ('8', null, '查看角色权限', '/manage/role/priority/,/manage/role/priority/list/,/manage/role/priority/grid/,/manage/role/priority/detail/', null);
INSERT INTO `priority` VALUES ('9', null, '管理角色权限', '/manage/role/priority/delete,/manage/role/priority/add/save,/manage/role/priority/add/list,/manage/role/priority/add/grid', null);
INSERT INTO `priority` VALUES ('10', null, '用户管理', '/manage/user/,/manage/user/list/,/manage/user/grid/,/manage/user/detail/,/manage/user/update,/manage/user/save,/manage/user/delete,/myList/role,/myList/org', null);
INSERT INTO `priority` VALUES ('11', null, '机构查看', '/manage/org/,/manage/org/list/,/manage/org/grid/,/manage/org/detail/', null);
INSERT INTO `priority` VALUES ('12', null, '机构管理', '/manage/org/update,/manage/org/save,/manage/org/delete', null);
INSERT INTO `priority` VALUES ('13', null, '公告查看', '/announcement/,/announcement/list/,/announcement/grid/,/announcement/detail/,/announcement/listByRole,/announcement/find,/announcement/listAnnouncements,/announcement/listAnnounceByRoleId', null);
INSERT INTO `priority` VALUES ('14', null, '公告管理', '/announcement/update,/announcement/save,/announcement/delete,/announcement/add,/announcement/deleteAnnouncement,/announcement/releaseAnnouncement,/announcement/updateAnnouncement,/announcement/save,/announcement/modify,/announcement/add,/announcement/testSms', null);

-- ----------------------------
-- Table structure for priority_group
-- ----------------------------
DROP TABLE IF EXISTS `priority_group`;
CREATE TABLE `priority_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL COMMENT '名称',
  `serial_number` int(11) DEFAULT NULL COMMENT '序列号',
  `fk_priority_group_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_priority_group_id` (`fk_priority_group_id`),
  CONSTRAINT `fk_priority_group_id` FOREIGN KEY (`fk_priority_group_id`) REFERENCES `priority_group` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of priority_group
-- ----------------------------

-- ----------------------------
-- Table structure for priority_menu
-- ----------------------------
DROP TABLE IF EXISTS `priority_menu`;
CREATE TABLE `priority_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fk_parent_id` int(11) DEFAULT NULL,
  `parent_ids` varchar(255) DEFAULT NULL COMMENT '所有父节点的id用处搜索所有子节点',
  `name` varchar(50) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  `url` varchar(200) DEFAULT NULL COMMENT '菜单链接地址',
  `code` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of priority_menu
-- ----------------------------
INSERT INTO `priority_menu` VALUES ('1', null, null, 'root', '1', null, null);
INSERT INTO `priority_menu` VALUES ('2', '1', ',1,', '权限管理', '2', null, null);
INSERT INTO `priority_menu` VALUES ('3', '1', ',1,', '用户管理', '1', null, null);
INSERT INTO `priority_menu` VALUES ('4', '1', ',1,', '自定义表单', '3', null, null);
INSERT INTO `priority_menu` VALUES ('5', '2', ',1,2,', '角色管理', '1', '/manage/role/', null);
INSERT INTO `priority_menu` VALUES ('6', '2', ',1,2,', '权限管理', '2', '/manage/priority/', null);
INSERT INTO `priority_menu` VALUES ('7', '4', ',1,4,', '模板管理', '1', null, null);
INSERT INTO `priority_menu` VALUES ('8', '4', ',1,4,', '数据统计', '2', null, null);
INSERT INTO `priority_menu` VALUES ('9', null, null, 'root', null, null, null);
INSERT INTO `priority_menu` VALUES ('10', '9', ',9,', '工作区', '1', '/manage/announcement/', 'gongzuo');
INSERT INTO `priority_menu` VALUES ('11', '9', ',9,', '服务区（开发中）', '2', null, 'fuwu');
INSERT INTO `priority_menu` VALUES ('12', '9', ',9,', '用户管理', '3', '/manage/user/', 'yonghuquanxian');
INSERT INTO `priority_menu` VALUES ('13', '9', ',9,', '权限管理', '4', '/manage/role/', 'yonghuquanxian');
INSERT INTO `priority_menu` VALUES ('14', '9', ',9,', '机构管理', '5', '/manage/org/', 'yonghuquanxian');

-- ----------------------------
-- Table structure for priority_role
-- ----------------------------
DROP TABLE IF EXISTS `priority_role`;
CREATE TABLE `priority_role` (
  `id` int(50) NOT NULL AUTO_INCREMENT,
  `code` varchar(10) DEFAULT NULL COMMENT '编号',
  `name` varchar(50) DEFAULT NULL COMMENT '名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of priority_role
-- ----------------------------
INSERT INTO `priority_role` VALUES ('1', '1', '超级管理员');
INSERT INTO `priority_role` VALUES ('2', '2', '综合1');
INSERT INTO `priority_role` VALUES ('24', '2', '综合2');
INSERT INTO `priority_role` VALUES ('26', '2', '组织');
INSERT INTO `priority_role` VALUES ('27', '2', '统战');
INSERT INTO `priority_role` VALUES ('28', '2', '宣传');
INSERT INTO `priority_role` VALUES ('30', '2', '学工');
INSERT INTO `priority_role` VALUES ('31', '2', '研工');
INSERT INTO `priority_role` VALUES ('32', '2', '马院');
INSERT INTO `priority_role` VALUES ('33', '2', '投稿');

-- ----------------------------
-- Table structure for priority_r_role_priority
-- ----------------------------
DROP TABLE IF EXISTS `priority_r_role_priority`;
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
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of priority_r_role_priority
-- ----------------------------
INSERT INTO `priority_r_role_priority` VALUES ('1', '1', '3', '1');
INSERT INTO `priority_r_role_priority` VALUES ('2', '1', '4', '1');
INSERT INTO `priority_r_role_priority` VALUES ('3', '1', '6', '1');
INSERT INTO `priority_r_role_priority` VALUES ('4', '1', '7', '1');
INSERT INTO `priority_r_role_priority` VALUES ('5', '1', '8', '1');
INSERT INTO `priority_r_role_priority` VALUES ('6', '1', '9', '1');
INSERT INTO `priority_r_role_priority` VALUES ('27', '1', '10', '1');
INSERT INTO `priority_r_role_priority` VALUES ('28', '1', '11', '1');
INSERT INTO `priority_r_role_priority` VALUES ('29', '1', '12', '1');
INSERT INTO `priority_r_role_priority` VALUES ('30', '1', '13', '1');
INSERT INTO `priority_r_role_priority` VALUES ('31', '1', '14', '1');
INSERT INTO `priority_r_role_priority` VALUES ('32', '2', '13', '1');

-- ----------------------------
-- Table structure for sso_user
-- ----------------------------
DROP TABLE IF EXISTS `sso_user`;
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
) ENGINE=InnoDB AUTO_INCREMENT=103 DEFAULT CHARSET=utf8 COMMENT='用户表存储登录相关信息';

-- ----------------------------
-- Records of sso_user
-- ----------------------------
INSERT INTO `sso_user` VALUES ('1', 'admin', '管理员', 'c4ca4238a0b923820dcc509a6f75849b', null, null, '0:0:0:0:0:0:0:1', '2017-09-30 18:26:34');
INSERT INTO `sso_user` VALUES ('101', 'cx', null, 'c4ca4238a0b923820dcc509a6f75849b', null, null, '0:0:0:0:0:0:0:1', '2017-09-30 17:16:29');
INSERT INTO `sso_user` VALUES ('102', 'cyf', null, 'c4ca4238a0b923820dcc509a6f75849b', null, null, '0:0:0:0:0:0:0:1', '2017-09-29 12:44:13');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
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
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin', '2017-09-26 16:09:12', '2017-09-26 16:09:09', '1', '1', '1', '1');
INSERT INTO `user` VALUES ('23', 'cx', '2017-09-29 11:57:56', '2017-09-29 11:57:56', null, '1', '1', '101');
INSERT INTO `user` VALUES ('24', 'cyf', '2017-09-29 11:58:03', '2017-09-29 11:58:03', null, '1', '2', '102');
