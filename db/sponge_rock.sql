SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for appx
-- ----------------------------
DROP TABLE IF EXISTS `appx`;
CREATE TABLE `appx`  (
  `app_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '应用ID',
  `app_key` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '应用唯一标识',
  `app_secret_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '应用密钥',
  `akey` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '（用于取代app id 形成的唯一key） //一般用于推广注册之类',
  `ugroup_id` int(11) NULL DEFAULT 0 COMMENT '加入的用户组ID',
  `agroup_id` int(11) NULL DEFAULT NULL COMMENT '加入的应用组ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '应用名称',
  `note` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '应用备注',
  `is_disabled` int(1) NOT NULL DEFAULT 0 COMMENT '禁用',
  `ar_is_setting` int(11) NOT NULL DEFAULT 0 COMMENT '是否开放设置',
  `ar_is_examine` int(11) NOT NULL DEFAULT 0 COMMENT '是否审核中(0: 没审核 ；1：审核中)',
  `ar_examine_ver` int(11) NOT NULL DEFAULT 0 COMMENT '审核 中的版本号',
  `log_fulltime` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`app_id`) USING BTREE,
  UNIQUE INDEX `IX_app_key`(`app_key`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '应用表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for appx_agroup
-- ----------------------------
DROP TABLE IF EXISTS `appx_agroup`;
CREATE TABLE `appx_agroup`  (
  `agroup_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '应用组ID',
  `name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '应用组名称',
  `tag` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '技术代号',
  `ugroup_id` int(11) NOT NULL DEFAULT 0 COMMENT '默认用户组ID',
  `is_disabled` int(11) NOT NULL DEFAULT 0 COMMENT '禁用',
  `enable_track` int(11) NOT NULL DEFAULT 1 COMMENT '启用跟踪',
  `enable_setting` int(11) NOT NULL DEFAULT 1 COMMENT '启用设置',
  `log_fulltime` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`agroup_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1000000 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for appx_ex_code
-- ----------------------------
DROP TABLE IF EXISTS `appx_ex_code`;
CREATE TABLE `appx_ex_code`  (
  `row_id` int(11) NOT NULL AUTO_INCREMENT,
  `agroup_id` int(11) NOT NULL DEFAULT 0,
  `service` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '服务名',
  `code` int(11) NOT NULL DEFAULT 0,
  `lang` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '',
  `note` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `is_disabled` int(1) NOT NULL DEFAULT 0 COMMENT '禁用',
  `log_fulltime` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`row_id`) USING BTREE,
  UNIQUE INDEX `IX_key`(`agroup_id`, `service`, `code`, `lang`) USING BTREE,
  INDEX `IX_agroup_id`(`agroup_id`) USING BTREE,
  INDEX `IX_service`(`service`, `lang`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = 'dwd' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for appx_ex_i18n
-- ----------------------------
DROP TABLE IF EXISTS `appx_ex_i18n`;
CREATE TABLE `appx_ex_i18n`  (
  `row_id` int(11) NOT NULL AUTO_INCREMENT,
  `agroup_id` int(11) NOT NULL DEFAULT 0,
  `service` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '服务名',
  `name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '0',
  `lang` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '',
  `note` varchar(5000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `is_disabled` int(1) NOT NULL DEFAULT 0 COMMENT '禁用',
  `log_fulltime` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`row_id`) USING BTREE,
  UNIQUE INDEX `IX_key`(`agroup_id`, `service`, `name`, `lang`) USING BTREE,
  INDEX `IX_agroup_id`(`agroup_id`) USING BTREE,
  INDEX `IX_service`(`service`, `lang`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = 'dwd' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for appx_ex_setting
-- ----------------------------
DROP TABLE IF EXISTS `appx_ex_setting`;
CREATE TABLE `appx_ex_setting`  (
  `row_id` int(11) NOT NULL AUTO_INCREMENT,
  `agroup_id` int(11) NOT NULL DEFAULT 0 COMMENT '应用组ID',
  `app_id` int(11) NOT NULL DEFAULT 0 COMMENT '应用ID',
  `is_client` int(11) NOT NULL DEFAULT 1 COMMENT '是否输出到客户端',
  `name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '设置项名称',
  `type` int(11) NULL DEFAULT 0 COMMENT '设置项值类型：0,文本；1,数字; 9,JSON',
  `value` varchar(4000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '设置项值',
  `note` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '备注',
  `is_disabled` int(1) NOT NULL DEFAULT 0 COMMENT '禁用',
  `ver_start` int(11) NOT NULL DEFAULT 0 COMMENT '从哪个版本开始支持',
  `log_fulltime` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`row_id`) USING BTREE,
  UNIQUE INDEX `IX_key`(`agroup_id`, `app_id`, `name`) USING BTREE,
  INDEX `IX_agroup`(`agroup_id`) USING BTREE,
  INDEX `IX_app`(`app_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '应用组设置表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for appx_ex_version
-- ----------------------------
DROP TABLE IF EXISTS `appx_ex_version`;
CREATE TABLE `appx_ex_version`  (
  `row_id` int(11) NOT NULL AUTO_INCREMENT,
  `agroup_id` int(11) NOT NULL DEFAULT 0 COMMENT 'agroup_id',
  `app_id` int(11) NOT NULL DEFAULT 0,
  `ver` int(11) NOT NULL DEFAULT 0 COMMENT '版本号',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '更新内容',
  `type` int(11) NOT NULL DEFAULT 0 COMMENT '更新方式（0：普通更新， 1：强制更新）',
  `alert_ver` int(11) NOT NULL DEFAULT 0 COMMENT '版本以下提示更新',
  `force_ver` int(11) NOT NULL DEFAULT 0 COMMENT '版本以下强制更新',
  `platform` int(11) NOT NULL DEFAULT 0 COMMENT '1=iOS, 2=Android,3=web',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '更新地址',
  `is_disabled` int(11) NOT NULL DEFAULT 1 COMMENT '禁用',
  `log_fulltime` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`row_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = 'app版本更新记录' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for appx_ugroup
-- ----------------------------
DROP TABLE IF EXISTS `appx_ugroup`;
CREATE TABLE `appx_ugroup`  (
  `ugroup_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `is_disabled` int(11) NOT NULL DEFAULT 0 COMMENT '禁用',
  `log_fulltime` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ugroup_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1000000 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
