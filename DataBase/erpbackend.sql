/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50527
Source Host           : localhost:3306
Source Database       : erpbackend

Target Server Type    : MYSQL
Target Server Version : 50527
File Encoding         : 65001

Date: 2020-06-15 16:05:07
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for bill
-- ----------------------------
DROP TABLE IF EXISTS `bill`;
CREATE TABLE `bill` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(256) NOT NULL,
  `opkind` int(2) NOT NULL,
  `estimatetime` int(11) NOT NULL,
  `status` int(2) NOT NULL,
  `description` varchar(256) NOT NULL,
  `stationid` int(11) DEFAULT NULL,
  `refusereason` varchar(256) DEFAULT NULL,
  `stoppedtime` datetime DEFAULT NULL,
  `stoppedreason` varchar(256) DEFAULT NULL,
  `completetime` datetime DEFAULT NULL,
  `distributetime` datetime DEFAULT NULL,
  `createdtime` datetime NOT NULL,
  `usebrief` varchar(512) DEFAULT NULL,
  `outbrief` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bill
-- ----------------------------
INSERT INTO `bill` VALUES ('10', '生产过程物料A', '2', '12', '3', '利用5:1的比例来使用原料A与原料B已生产过程物料A', '5', '原料不足', null, null, null, null, '2020-04-15 16:50:22', '100018100019', '100022');
INSERT INTO `bill` VALUES ('11', '生产过程物料B', '1', '12', '6', '利用5:1的比例来生成过程物料B', '6', null, '2020-05-05 18:23:17', 'Stopped', null, '2020-04-15 17:24:35', '2020-04-15 16:51:38', '100021100022', '100019');
INSERT INTO `bill` VALUES ('12', '生产最终物料A', '3', '12', '5', '5:1的比例来生产最终物料A', '7', null, null, null, null, '2020-04-23 13:31:02', '2020-04-15 16:57:14', '100023100022', '100024');
INSERT INTO `bill` VALUES ('13', '生产最终物料A--2', '3', '12', '5', '', '6', null, null, null, null, '2020-05-27 11:22:07', '2020-04-15 17:02:44', '100023100022', '100024');
INSERT INTO `bill` VALUES ('14', '生产过程物料A--2', '2', '12', '5', '', '6', null, null, null, null, '2020-05-23 13:42:42', '2020-04-15 17:03:31', '100018100019', '100022');
INSERT INTO `bill` VALUES ('15', '生产过程物料B--2', '2', '12', '7', '', '6', null, null, null, '2020-05-23 13:27:39', '2020-05-23 13:15:38', '2020-04-15 17:04:03', '100020100021', '100023');
INSERT INTO `bill` VALUES ('16', 'Test', '2', '12', '5', 'Just A Des', '5', null, null, null, null, '2020-05-23 13:29:45', '2020-04-30 15:43:55', '100018100019', '100022');
INSERT INTO `bill` VALUES ('17', 'Test22233333', '2', '12', '3', '', '5', '不足', null, null, null, null, '2020-04-30 16:13:13', '100018100019', '100022');
INSERT INTO `bill` VALUES ('18', 'AAA', '2', '12', '1', '', null, null, null, null, null, null, '2020-05-23 13:40:02', '100018100019', '100022');
INSERT INTO `bill` VALUES ('19', '测试工单A', '2', '12', '2', '', '6', null, null, null, null, null, '2020-05-27 11:24:51', '100018100019', '100022');

-- ----------------------------
-- Table structure for billoutput
-- ----------------------------
DROP TABLE IF EXISTS `billoutput`;
CREATE TABLE `billoutput` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `billid` int(11) NOT NULL,
  `opmid` int(11) NOT NULL,
  `mount` int(11) NOT NULL,
  `haveop` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of billoutput
-- ----------------------------
INSERT INTO `billoutput` VALUES ('19', '10', '100022', '5', '0');
INSERT INTO `billoutput` VALUES ('20', '11', '100019', '5', '0');
INSERT INTO `billoutput` VALUES ('21', '12', '100024', '1', '0');
INSERT INTO `billoutput` VALUES ('22', '13', '100024', '1', '0');
INSERT INTO `billoutput` VALUES ('23', '14', '100022', '5', '0');
INSERT INTO `billoutput` VALUES ('24', '15', '100023', '5', '0');
INSERT INTO `billoutput` VALUES ('25', '16', '100022', '5', '0');
INSERT INTO `billoutput` VALUES ('26', '17', '100022', '5', '0');
INSERT INTO `billoutput` VALUES ('27', '11', '100019', '1', '1');
INSERT INTO `billoutput` VALUES ('28', '18', '100022', '1', '0');
INSERT INTO `billoutput` VALUES ('29', '19', '100022', '1', '0');

-- ----------------------------
-- Table structure for billuse
-- ----------------------------
DROP TABLE IF EXISTS `billuse`;
CREATE TABLE `billuse` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `billid` int(11) NOT NULL,
  `usemid` int(11) NOT NULL,
  `mount` int(11) NOT NULL,
  `haveused` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of billuse
-- ----------------------------
INSERT INTO `billuse` VALUES ('52', '10', '100018', '25', '0');
INSERT INTO `billuse` VALUES ('53', '10', '100019', '25', '0');
INSERT INTO `billuse` VALUES ('54', '11', '100021', '25', '0');
INSERT INTO `billuse` VALUES ('55', '11', '100022', '25', '0');
INSERT INTO `billuse` VALUES ('56', '12', '100023', '5', '0');
INSERT INTO `billuse` VALUES ('57', '12', '100022', '5', '0');
INSERT INTO `billuse` VALUES ('58', '13', '100023', '5', '0');
INSERT INTO `billuse` VALUES ('59', '13', '100022', '5', '0');
INSERT INTO `billuse` VALUES ('60', '14', '100018', '25', '0');
INSERT INTO `billuse` VALUES ('61', '14', '100019', '25', '0');
INSERT INTO `billuse` VALUES ('62', '15', '100020', '25', '0');
INSERT INTO `billuse` VALUES ('63', '15', '100021', '25', '0');
INSERT INTO `billuse` VALUES ('64', '16', '100018', '50', '0');
INSERT INTO `billuse` VALUES ('65', '16', '100019', '50', '0');
INSERT INTO `billuse` VALUES ('68', '17', '100018', '50', '0');
INSERT INTO `billuse` VALUES ('69', '17', '100019', '50', '0');
INSERT INTO `billuse` VALUES ('70', '18', '100018', '10', '0');
INSERT INTO `billuse` VALUES ('71', '18', '100019', '10', '0');
INSERT INTO `billuse` VALUES ('72', '19', '100018', '10', '0');
INSERT INTO `billuse` VALUES ('73', '19', '100019', '10', '0');

-- ----------------------------
-- Table structure for material
-- ----------------------------
DROP TABLE IF EXISTS `material`;
CREATE TABLE `material` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(256) NOT NULL,
  `allmount` int(11) NOT NULL,
  `leftmount` int(11) NOT NULL,
  `distributedmount` int(11) NOT NULL,
  `kind` int(2) NOT NULL,
  `description` varchar(256) NOT NULL,
  `lasttransport` date NOT NULL,
  `warnthreshold` int(11) NOT NULL,
  `dangerthreshold` int(11) NOT NULL,
  `status` int(2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100026 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of material
-- ----------------------------
INSERT INTO `material` VALUES ('100018', '基础原料A', '100', '25', '75', '1', '这是基础原料A', '2020-04-15', '60', '20', '3');
INSERT INTO `material` VALUES ('100019', '基础原料B', '101', '26', '75', '1', '这是基础原料B', '2020-04-15', '60', '20', '3');
INSERT INTO `material` VALUES ('100020', '基础原料C', '75', '75', '0', '1', '这是基础原料C', '2020-04-15', '60', '20', '1');
INSERT INTO `material` VALUES ('100021', '基础原料D', '100', '75', '25', '1', '这是基础原料D', '2020-04-28', '60', '20', '1');
INSERT INTO `material` VALUES ('100022', '过程物料A', '100', '65', '35', '2', '这是过程物料A', '2020-04-15', '60', '20', '1');
INSERT INTO `material` VALUES ('100023', '过程物料B', '105', '95', '10', '2', '这是过程物料B', '2020-04-15', '60', '20', '1');
INSERT INTO `material` VALUES ('100024', '最终产品A', '100', '100', '0', '3', '这是最终产品A', '2020-04-15', '80', '20', '1');
INSERT INTO `material` VALUES ('100025', '测试物料A', '80', '80', '0', '2', 'Des', '2020-05-23', '90', '10', '1');

-- ----------------------------
-- Table structure for materialproduce
-- ----------------------------
DROP TABLE IF EXISTS `materialproduce`;
CREATE TABLE `materialproduce` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `time` date NOT NULL,
  `mid` int(11) NOT NULL,
  `way` int(1) NOT NULL,
  `mount` int(11) NOT NULL,
  `stationid` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of materialproduce
-- ----------------------------
INSERT INTO `materialproduce` VALUES ('40', '2020-04-15', '100022', '2', '25', '6');
INSERT INTO `materialproduce` VALUES ('41', '2020-04-23', '100023', '2', '5', '7');
INSERT INTO `materialproduce` VALUES ('42', '2020-04-23', '100022', '2', '5', '7');
INSERT INTO `materialproduce` VALUES ('43', '2020-05-05', '100019', '1', '1', '6');
INSERT INTO `materialproduce` VALUES ('44', '2020-05-23', '100020', '2', '25', '6');
INSERT INTO `materialproduce` VALUES ('45', '2020-05-23', '100021', '2', '25', '6');
INSERT INTO `materialproduce` VALUES ('46', '2020-05-23', '100023', '1', '5', '6');
INSERT INTO `materialproduce` VALUES ('47', '2020-05-23', '100018', '2', '50', '5');
INSERT INTO `materialproduce` VALUES ('48', '2020-05-23', '100019', '2', '50', '5');
INSERT INTO `materialproduce` VALUES ('49', '2020-05-23', '100018', '2', '25', '6');
INSERT INTO `materialproduce` VALUES ('50', '2020-05-23', '100019', '2', '25', '6');
INSERT INTO `materialproduce` VALUES ('51', '2020-05-27', '100023', '2', '5', '6');
INSERT INTO `materialproduce` VALUES ('52', '2020-05-27', '100022', '2', '5', '6');

-- ----------------------------
-- Table structure for refusebill
-- ----------------------------
DROP TABLE IF EXISTS `refusebill`;
CREATE TABLE `refusebill` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `billid` int(11) NOT NULL,
  `refusekind` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of refusebill
-- ----------------------------
INSERT INTO `refusebill` VALUES ('1', '10', '1');
INSERT INTO `refusebill` VALUES ('2', '17', '1');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL,
  `nameZh` varchar(32) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', 'admin', '管理员');

-- ----------------------------
-- Table structure for station
-- ----------------------------
DROP TABLE IF EXISTS `station`;
CREATE TABLE `station` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(256) NOT NULL,
  `status` int(11) NOT NULL,
  `lastmaintain` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of station
-- ----------------------------
INSERT INTO `station` VALUES ('5', '模拟工位A', '1', '2020-04-15');
INSERT INTO `station` VALUES ('6', '模拟工位B', '1', '2020-04-15');
INSERT INTO `station` VALUES ('7', '模拟工位C', '1', '2020-04-15');
INSERT INTO `station` VALUES ('8', '模拟工位D', '1', '2020-04-15');

-- ----------------------------
-- Table structure for transport
-- ----------------------------
DROP TABLE IF EXISTS `transport`;
CREATE TABLE `transport` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `time` date NOT NULL,
  `mid` int(11) NOT NULL,
  `mount` int(11) NOT NULL,
  `way` int(11) NOT NULL,
  `description` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of transport
-- ----------------------------
INSERT INTO `transport` VALUES ('9', '2020-04-15', '100024', '100', '1', '');
INSERT INTO `transport` VALUES ('10', '2020-04-15', '100023', '100', '1', '');
INSERT INTO `transport` VALUES ('11', '2020-04-15', '100022', '100', '1', '');
INSERT INTO `transport` VALUES ('12', '2020-04-15', '100021', '100', '1', '');
INSERT INTO `transport` VALUES ('13', '2020-04-15', '100020', '100', '1', '');
INSERT INTO `transport` VALUES ('14', '2020-04-15', '100019', '100', '1', '');
INSERT INTO `transport` VALUES ('15', '2020-04-15', '100018', '100', '1', '');
INSERT INTO `transport` VALUES ('16', '2020-04-28', '100021', '25', '1', '');
INSERT INTO `transport` VALUES ('17', '2020-05-23', '100025', '50', '1', '');
INSERT INTO `transport` VALUES ('18', '2020-05-23', '100025', '30', '1', '');

-- ----------------------------
-- Table structure for tree
-- ----------------------------
DROP TABLE IF EXISTS `tree`;
CREATE TABLE `tree` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `targetmid` int(11) NOT NULL,
  `targetname` varchar(256) NOT NULL,
  `opmount` int(11) NOT NULL,
  `status` int(11) NOT NULL,
  `name` varchar(256) NOT NULL,
  `description` varchar(256) DEFAULT NULL,
  `needbrief` varchar(256) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tree
-- ----------------------------
INSERT INTO `tree` VALUES ('3', '100022', '过程物料A', '1', '1', 'Test', 'TestDesEidt', '基础原料A基础原料B');

-- ----------------------------
-- Table structure for treebasic
-- ----------------------------
DROP TABLE IF EXISTS `treebasic`;
CREATE TABLE `treebasic` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mid` int(11) NOT NULL,
  `tid` int(11) NOT NULL,
  `mount` int(11) NOT NULL,
  `mname` varchar(256) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of treebasic
-- ----------------------------
INSERT INTO `treebasic` VALUES ('11', '100018', '3', '10', '基础原料A');
INSERT INTO `treebasic` VALUES ('12', '100019', '3', '10', '基础原料B');

-- ----------------------------
-- Table structure for treetimes
-- ----------------------------
DROP TABLE IF EXISTS `treetimes`;
CREATE TABLE `treetimes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tid` int(11) NOT NULL,
  `times` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of treetimes
-- ----------------------------
INSERT INTO `treetimes` VALUES ('1', '3', '4');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL,
  `password` varchar(255) NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  `locked` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100002 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('100001', 'tom', '$2a$10$U4hvQ6/dv1LHHvpbPiib0eTATM0zTA76QF6CHRRy9rJ2svHJ33or2', '1', '0');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL,
  `rid` int(11) NOT NULL,
  `roleId` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('1', '100001', '1', '0', '0');
