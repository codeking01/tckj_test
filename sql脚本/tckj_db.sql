/*
 Navicat Premium Data Transfer

 Source Server         : docker-mysql8_sec
 Source Server Type    : MySQL
 Source Server Version : 80027
 Source Host           : 192.168.1.109:3308
 Source Schema         : tckj_db

 Target Server Type    : MySQL
 Target Server Version : 80027
 File Encoding         : 65001

 Date: 26/05/2023 14:17:14
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for orm_user
-- ----------------------------
DROP TABLE IF EXISTS `orm_user`;
CREATE TABLE `orm_user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '加密后的密码',
  `salt` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '加密使用的盐',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
  `phone_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '手机号码',
  `status` int NULL DEFAULT NULL COMMENT '状态，-1：逻辑删除，0：禁用，1：启用',
  `frequency` bigint NULL DEFAULT NULL COMMENT '访问频率',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `last_login_time` datetime NULL DEFAULT NULL COMMENT '上次登录时间',
  `last_update_time` datetime NULL DEFAULT NULL COMMENT '上次更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of orm_user
-- ----------------------------
INSERT INTO `orm_user` VALUES (1, '熊嘉亮', '223', '123', 'jialiang_xiong@163.com', '15630138378', 1, 0, '2023-05-26 11:12:08', '2023-05-26 11:12:08', '2023-05-26 13:52:47');
INSERT INTO `orm_user` VALUES (5, '3333', '111', '123', 'jialiang_xionsg@163.com', '15573457937', 1, 0, '2023-05-26 12:32:15', '2023-05-26 12:32:15', '2023-05-26 13:52:49');
INSERT INTO `orm_user` VALUES (6, '熊嘉亮21', '', '123', 'jialiang_xionpg@163.com', '15573458937', 1, 0, '2023-05-26 13:17:30', '2023-05-26 13:17:30', '2023-05-26 13:52:56');

SET FOREIGN_KEY_CHECKS = 1;
