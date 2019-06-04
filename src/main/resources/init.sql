/*
 Navicat Premium Data Transfer

 Source Server         : liangbintao
 Source Server Type    : MySQL
 Source Server Version : 50725
 Source Host           : 47.98.118.202:3306
 Source Schema         : boot_security_01

 Target Server Type    : MySQL
 Target Server Version : 50725
 File Encoding         : 65001

 Date: 04/06/2019 15:18:45
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for authorities
-- ----------------------------
DROP TABLE IF EXISTS `authorities`;
CREATE TABLE `authorities` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` varchar(32) NOT NULL,
  `username` varchar(64) NOT NULL,
  `authority` varchar(32) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `idx_username` (`username`,`authority`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for mobile
-- ----------------------------
DROP TABLE IF EXISTS `mobile`;
CREATE TABLE `mobile` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `mobile` varchar(32) NOT NULL COMMENT '手机号码',
  `code` varchar(32) DEFAULT NULL COMMENT '验证码',
  `userid` varchar(32) DEFAULT NULL COMMENT '用户id',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '0 无效 1 有效',
  `verifytime` int(11) NOT NULL DEFAULT '0' COMMENT '验证次数',
  `sendtime` timestamp NULL DEFAULT NULL COMMENT '发送时间',
  `validtime` timestamp NULL DEFAULT NULL,
  `todaycount` int(1) NOT NULL DEFAULT '0' COMMENT '当日发送次数',
  `sendresult` text COMMENT '说明',
  `ip` varchar(128) DEFAULT NULL COMMENT 'ip地址',
  `machine` varchar(128) DEFAULT NULL COMMENT '设备号',
  `logintime` timestamp NULL DEFAULT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatetime` timestamp NULL DEFAULT NULL COMMENT '验证码生成时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `mobile` (`mobile`) USING BTREE,
  KEY `userid` (`userid`) USING BTREE,
  KEY `idx_sendtime` (`sendtime`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for session
-- ----------------------------
DROP TABLE IF EXISTS `session`;
CREATE TABLE `session` (
  `id` varchar(64) NOT NULL,
  `userid` varchar(32) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `failtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`userid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(64) NOT NULL,
  `password` varchar(128) NOT NULL,
  `status` int(1) NOT NULL DEFAULT '1',
  `nickname` varchar(32) NOT NULL COMMENT '花名',
  `avatar` varchar(256) DEFAULT NULL COMMENT '用户头像',
  `sex` int(1) NOT NULL DEFAULT '0',
  `age` int(2) NOT NULL DEFAULT '0',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatetime` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4;

SET FOREIGN_KEY_CHECKS = 1;
