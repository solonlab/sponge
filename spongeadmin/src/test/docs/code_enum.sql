
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for code_enum
-- ----------------------------
DROP TABLE IF EXISTS `code_enum`;
CREATE TABLE `code_enum`  (
  `row_id` int(11) NOT NULL AUTO_INCREMENT,
  `type` int(11) NOT NULL DEFAULT 0 COMMENT '类型',
  `value` int(11) NOT NULL DEFAULT 0 COMMENT '值',
  `title` varchar(99) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '标题',
  `keyword` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '关键字',
  PRIMARY KEY (`row_id`) USING BTREE,
  INDEX `IX_key`(`value`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Records of code_enum
-- ----------------------------
INSERT INTO `code_enum` VALUES (1, 1, 1, 'Window', '%Windows%');
INSERT INTO `code_enum` VALUES (2, 1, 2, 'Mac', '%Mac%');
INSERT INTO `code_enum` VALUES (3, 1, 3, 'Andorid', '%Android%');
INSERT INTO `code_enum` VALUES (4, 1, 4, 'iPhone', '%iPhone%');
INSERT INTO `code_enum` VALUES (5, 1, 5, 'iPad', '%iPad%');
INSERT INTO `code_enum` VALUES (6, 2, 1, 'Chrome', '%Chrome%');
INSERT INTO `code_enum` VALUES (7, 2, 2, 'Edge', '%Edg/%');
INSERT INTO `code_enum` VALUES (8, 2, 3, 'Safari', '%Safari/%');
INSERT INTO `code_enum` VALUES (9, 2, 4, 'IE', '%Trident/%');
INSERT INTO `code_enum` VALUES (10, 2, 5, 'Firefox', '%Firefox/%');

SET FOREIGN_KEY_CHECKS = 1;
