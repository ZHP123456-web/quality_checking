/*
 Navicat Premium Dump SQL

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 80043 (8.0.43)
 Source Host           : localhost:3306
 Source Schema         : test

 Target Server Type    : MySQL
 Target Server Version : 80043 (8.0.43)
 File Encoding         : 65001

 Date: 29/01/2026 15:05:22
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for quality_inspection_rule
-- ----------------------------
DROP TABLE IF EXISTS `quality_inspection_rule`;
CREATE TABLE `quality_inspection_rule`  (
  `UNID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主键',
  `RULE_NAME` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '规则名称',
  `REL_COLUMN` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '关联字段',
  `RULE` json NULL COMMENT '校验规则',
  `RULE_MEMO` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '校验规则说明',
  `PUNID` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '所属方案UNID',
  `PNAME` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '所属方案名称',
  PRIMARY KEY (`UNID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '质检方案规则存储表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of quality_inspection_rule
-- ----------------------------
INSERT INTO `quality_inspection_rule` VALUES ('001', '手机号格式校验', 'phone', '{\"type\": \"regex\", \"pattern\": \"^1[3-9]\\\\d{9}$\"}', '校验手机号格式', 'P001', '基础数据校验方案');
INSERT INTO `quality_inspection_rule` VALUES ('002', '身份证号校验', 'idcard', '{\"type\": \"regex\", \"pattern\": \"^\\\\d{17}(\\\\d|X)$\"}', '校验身份证号格式', 'P001', '基础数据校验方案');
INSERT INTO `quality_inspection_rule` VALUES ('003', '邮箱格式校验', 'email', '{\"type\": \"regex\", \"pattern\": \"^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\\\.[a-zA-Z]{2,}$\"}', '校验邮箱格式', 'P001', '基础数据校验方案');
INSERT INTO `quality_inspection_rule` VALUES ('004', '年龄范围校验', 'age', '{\"max\": 120, \"min\": 0, \"type\": \"range\"}', '校验年龄范围', 'P001', '基础数据校验方案');
INSERT INTO `quality_inspection_rule` VALUES ('005', '日期格式校验', 'birth_date', '{\"type\": \"regex\", \"pattern\": \"^\\\\d{4}-\\\\d{2}-\\\\d{2}$\"}', '校验日期格式', 'P001', '基础数据校验方案');
INSERT INTO `quality_inspection_rule` VALUES ('006', '非空校验', 'username', '{\"type\": \"not_null\"}', '校验用户名非空', 'P002', '用户数据校验方案');
INSERT INTO `quality_inspection_rule` VALUES ('007', '唯一性校验', 'user_code', '{\"type\": \"unique\"}', '校验用户编码唯一', 'P002', '用户数据校验方案');
INSERT INTO `quality_inspection_rule` VALUES ('008', '长度校验', 'address', '{\"max\": 200, \"type\": \"length\"}', '校验地址长度', 'P002', '用户数据校验方案');
INSERT INTO `quality_inspection_rule` VALUES ('009', '数值范围校验', 'salary', '{\"max\": 999999.99, \"min\": 0, \"type\": \"range\"}', '校验工资范围', 'P003', '财务数据校验方案');
INSERT INTO `quality_inspection_rule` VALUES ('010', '枚举值校验', 'status', '{\"type\": \"enum\", \"values\": [\"active\", \"inactive\"]}', '校验状态值', 'P003', '财务数据校验方案');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `unid` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `age` int NULL DEFAULT NULL,
  `telephone` varchar(55) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `cardid` varchar(35) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`unid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '张三1', 15, '147', '350322200310184839', '11', '2026-01-28 11:25:08');
INSERT INTO `user` VALUES (2, '张三2', 151, '14749593636', '350322200310184839', '62', NULL);
INSERT INTO `user` VALUES (3, '张三3', 15, '147', '350322200310184839', '3373778530@qq.com', NULL);
INSERT INTO `user` VALUES (4, '张三4', 45, '14749593636', '350322200310184839', '3373778530@qq.com', NULL);
INSERT INTO `user` VALUES (5, '张三5', 45, '1474959363611', '350350322200310184839', 'aa', NULL);
INSERT INTO `user` VALUES (6, '张三', 25, '13800138000', '123456789012345678', 'zhangsan@example.com', '2026-01-28 15:56:05');
INSERT INTO `user` VALUES (7, '李四', 30, '14749591136', '350322200310184869', 'lisi@example.com', '2026-01-28 15:56:05');
INSERT INTO `user` VALUES (8, '王五', 200, '13800138002', '123456789012345680', 'wangwu@example.com', '2026-01-28 15:56:05');
INSERT INTO `user` VALUES (9, '赵六', 700, '13800138003', '123456789012345681', 'zhaoliu@example.com', '2026-01-28 15:56:05');
INSERT INTO `user` VALUES (10, '孙七', 35, '13800138004', '123456789012345682', 'sunqi@example.com', '2026-01-28 15:56:05');

SET FOREIGN_KEY_CHECKS = 1;
