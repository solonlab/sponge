SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for code_enum
-- ----------------------------
DROP TABLE IF EXISTS `code_enum`;
CREATE TABLE `code_enum`  (
                              `row_id` int(11) NOT NULL,
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
INSERT INTO `code_enum` VALUES (1001, 1, 1, 'Window', '%Windows%');
INSERT INTO `code_enum` VALUES (1002, 1, 3, 'Andorid', '%Android%');
INSERT INTO `code_enum` VALUES (1003, 1, 4, 'iPhone', '%iPhone%');
INSERT INTO `code_enum` VALUES (1004, 1, 5, 'iPad', '%iPad%');
INSERT INTO `code_enum` VALUES (1005, 1, 2, 'Mac', '%Mac%');
INSERT INTO `code_enum` VALUES (2001, 2, 101, 'QQ', '%QQ/%');
INSERT INTO `code_enum` VALUES (2101, 2, 1, 'Edge', '%Edg/%');
INSERT INTO `code_enum` VALUES (2102, 2, 2, 'MSIE', '%Trident/%');
INSERT INTO `code_enum` VALUES (2103, 2, 3, 'Firefox', '%Firefox/%');
INSERT INTO `code_enum` VALUES (2104, 2, 4, 'Opera', '%Opera/%');
INSERT INTO `code_enum` VALUES (2105, 2, 5, 'Chrome', '%Chrome%');
INSERT INTO `code_enum` VALUES (2106, 2, 6, 'Safari', '%Safari/%');

SET FOREIGN_KEY_CHECKS = 1;
