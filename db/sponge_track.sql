
CREATE TABLE IF NOT EXISTS `_tmp_total_pv_uv_ip`  (
  `obj_id` bigint(20) NOT NULL,
  `pv` bigint(20) NOT NULL DEFAULT 0,
  `uv` bigint(20) NOT NULL DEFAULT 0,
  `ip` bigint(20) NOT NULL DEFAULT 0,
  `uk` bigint(20) NOT NULL DEFAULT 0,
  PRIMARY KEY (`obj_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

CREATE TABLE IF NOT EXISTS `_tmp_track_total_pv_uv_ip`  (
  `obj_id` bigint(20) NOT NULL,
  `vi` int(11) NOT NULL DEFAULT 0,
  `vd` varchar(100) NOT NULL DEFAULT '',
  `pv` bigint(20) NOT NULL DEFAULT 0,
  `uv` bigint(20) NOT NULL DEFAULT 0,
  `ip` bigint(20) NOT NULL DEFAULT 0,
  `uk` bigint(20) NOT NULL DEFAULT 0,
  `ua` bigint(20) NOT NULL DEFAULT 0,
  PRIMARY KEY (`obj_id`, `vi`, `vd`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

CREATE TABLE IF NOT EXISTS `code_city`  (
  `city_code` int(11) NOT NULL COMMENT 'xxyy00',
  `city_name` varchar(50)  NOT NULL DEFAULT '',
  PRIMARY KEY (`city_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

CREATE TABLE IF NOT EXISTS `code_enum`  (
  `row_id` int(11) NOT NULL,
  `type` int(11) NOT NULL DEFAULT 0 COMMENT '类型',
  `value` int(11) NOT NULL DEFAULT 0 COMMENT '值',
  `title` varchar(99)  NOT NULL DEFAULT '' COMMENT '标题',
  `keyword` varchar(40)  NOT NULL DEFAULT '' COMMENT '关键字',
  PRIMARY KEY (`row_id`) USING BTREE,
  INDEX `IX_key`(`value`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 45 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

CREATE TABLE IF NOT EXISTS `code_ip`  (
  `ip_id` bigint(20) NOT NULL,
  `ip_val` varchar(100)  NOT NULL DEFAULT '',
  `net_segment` int(11) NOT NULL DEFAULT 0,
  `city_code` int(11) NOT NULL DEFAULT 0,
  `isp_code` int(11) NOT NULL DEFAULT 0 COMMENT '互联网服务提供商',
  `city_name` varchar(50)  NOT NULL DEFAULT '',
  `is_checked` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`ip_id`) USING BTREE,
  UNIQUE INDEX `IX_ip_val`(`ip_val`) USING BTREE,
  INDEX `IX_city_code`(`city_code`) USING BTREE,
  INDEX `IX_isp_code`(`isp_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

CREATE TABLE IF NOT EXISTS `code_ua`  (
  `ua_id` bigint(20) NOT NULL,
  `ua_key` varchar(40)  NOT NULL DEFAULT '',
  `ua_val` varchar(999)  NULL DEFAULT NULL,
  `platform` int(11) NOT NULL DEFAULT 0 COMMENT '平台。0=未知；101=IPhone；102=iPad；111=Android；201=Win；211=Mac；221=linux',
  `client` int(11) NOT NULL DEFAULT 0 COMMENT '客户端。0=未知；1001=支付宝；1002=微信；',
  `maker` int(11) NOT NULL DEFAULT 0 COMMENT '设备产商',
  PRIMARY KEY (`ua_id`) USING BTREE,
  UNIQUE INDEX `IX_ua_key`(`ua_key`) USING BTREE,
  INDEX `IX_platform`(`platform`) USING BTREE,
  INDEX `IX_client`(`client`) USING BTREE,
  INDEX `IX_maker`(`maker`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

CREATE TABLE IF NOT EXISTS `short_redirect_log_30d`  (
  `log_id` bigint(20) NOT NULL,
  `url_id` bigint(20) NULL DEFAULT NULL,
  `tag_id` int(11) NULL DEFAULT NULL,
  `user_id` bigint(20) NOT NULL DEFAULT 0,
  `user_key` varchar(40)  NOT NULL DEFAULT '',
  `v1` varchar(100)  NOT NULL DEFAULT '',
  `v2` varchar(100)  NOT NULL DEFAULT '',
  `v3` varchar(100)  NOT NULL DEFAULT '',
  `v4` varchar(100)  NOT NULL DEFAULT '',
  `v5` varchar(100)  NOT NULL DEFAULT '',
  `log_ip_id` bigint(20) NOT NULL DEFAULT 0,
  `log_city_code` int(11) NOT NULL DEFAULT 0,
  `log_ua_id` bigint(20) NOT NULL DEFAULT 0,
  `log_date` int(11) NOT NULL DEFAULT 0,
  `log_hour` int(11) NOT NULL DEFAULT 0,
  `log_fulltime` datetime NULL DEFAULT NULL,
  `admin_group` int(11) NOT NULL DEFAULT 0 COMMENT '管理组（同一管理组可见）',
  `referer_url` varchar(255)  NULL DEFAULT NULL,
  PRIMARY KEY (`log_id`) USING BTREE,
  INDEX `IX_log_date`(`log_date`) USING BTREE,
  INDEX `IX_user_id`(`user_id`) USING BTREE,
  INDEX `IX_v1`(`v1`) USING BTREE,
  INDEX `IX_v2`(`v2`) USING BTREE,
  INDEX `IX_v3`(`v3`) USING BTREE,
  INDEX `IX_v4`(`v4`) USING BTREE,
  INDEX `IX_v5`(`v5`) USING BTREE,
  INDEX `IX_admin_group`(`admin_group`) USING BTREE,
  INDEX `IX_user_key`(`user_key`) USING BTREE,
  INDEX `IX_url_id`(`url_id`, `log_date`) USING BTREE,
  INDEX `IX_tag_id`(`tag_id`, `log_date`) USING BTREE,
  INDEX `IX_city_code`(`log_city_code`) USING BTREE,
  INDEX `IX_log_ip_id`(`log_ip_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

CREATE TABLE IF NOT EXISTS `short_url`  (
  `url_id` bigint(20) NOT NULL COMMENT 'URL ID',
  `tag_id` int(11) NOT NULL DEFAULT 0 COMMENT '标签ID',
  `url_partner_key` varchar(40)  NOT NULL DEFAULT '' COMMENT '合作方的url_key（tag下唯一）',
  `url_key` varchar(40)  NOT NULL DEFAULT '',
  `url_name` varchar(50)  NULL DEFAULT NULL,
  `url_val_md5` varchar(40)  NOT NULL DEFAULT '',
  `url_val` varchar(500)  NULL DEFAULT NULL,
  `user_field` varchar(40)  NOT NULL DEFAULT '' COMMENT '用户主键字段的参数名',
  `track_params` varchar(100)  NOT NULL DEFAULT '' COMMENT '监听的参数（记入log表，最多支持5个）例：c,b,d',
  `track_params_num` int(11) NOT NULL DEFAULT 0,
  `trans_params` varchar(100)  NOT NULL DEFAULT '' COMMENT '例：c=x,b=y',
  `build_link` varchar(500)  NOT NULL DEFAULT '' COMMENT '构建连接代码（例：网页::f=web）',
  `url_redirect_count` bigint(20) NOT NULL DEFAULT 0,
  `note` varchar(200)  NULL DEFAULT NULL COMMENT '备注',
  `admin_group` int(11) NOT NULL DEFAULT 0 COMMENT '管理组（同一管理组可见）',
  `is_disable` int(11) NOT NULL DEFAULT 0,
  `create_fulltime` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`url_id`) USING BTREE,
  UNIQUE INDEX `IX_url_key`(`url_key`) USING BTREE,
  INDEX `IX_url_val_md5`(`url_val_md5`) USING BTREE,
  INDEX `IX_tag_key`(`tag_id`, `url_partner_key`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'URL' ROW_FORMAT = DYNAMIC;

CREATE TABLE IF NOT EXISTS `short_url_ex_stat`  (
  `url_id` bigint(20) NOT NULL COMMENT 'URL ID',
  `tag_id` int(11) NOT NULL DEFAULT 0 COMMENT '标签ID',
  `admin_group` int(11) NOT NULL DEFAULT 0 COMMENT '管理组（同一管理组可见）',
  `uv_total` bigint(20) NOT NULL DEFAULT 0,
  `pv_total` bigint(20) NOT NULL DEFAULT 0,
  `ip_total` bigint(20) NOT NULL DEFAULT 0,
  `uk_total` bigint(20) NOT NULL DEFAULT 0,
  `uv_today` bigint(20) NOT NULL DEFAULT 0,
  `pv_today` bigint(20) NOT NULL DEFAULT 0,
  `ip_today` bigint(20) NOT NULL DEFAULT 0,
  `uk_today` bigint(20) NOT NULL DEFAULT 0,
  `uv_yesterday` bigint(20) NOT NULL DEFAULT 0,
  `pv_yesterday` bigint(20) NOT NULL DEFAULT 0,
  `ip_yesterday` bigint(20) NOT NULL DEFAULT 0,
  `uk_yesterday` bigint(20) NOT NULL DEFAULT 0,
  PRIMARY KEY (`url_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '标签' ROW_FORMAT = DYNAMIC;

CREATE TABLE IF NOT EXISTS `short_url_ex_track_stat`  (
  `row_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `url_id` bigint(20) NOT NULL,
  `tag_id` int(11) NOT NULL COMMENT '标签ID',
  `admin_group` int(11) NOT NULL DEFAULT 0 COMMENT '管理组（同一管理组可见）',
  `vi` int(11) NOT NULL DEFAULT 0,
  `vd` varchar(100)  NOT NULL DEFAULT '',
  `uv_total` bigint(20) NOT NULL DEFAULT 0,
  `pv_total` bigint(20) NOT NULL DEFAULT 0,
  `ip_total` bigint(20) NOT NULL DEFAULT 0,
  `uk_total` bigint(20) NOT NULL DEFAULT 0,
  `uv_today` bigint(20) NOT NULL DEFAULT 0,
  `pv_today` bigint(20) NOT NULL DEFAULT 0,
  `ip_today` bigint(20) NOT NULL DEFAULT 0,
  `uk_today` bigint(20) NOT NULL DEFAULT 0,
  `uv_yesterday` bigint(20) NOT NULL DEFAULT 0,
  `pv_yesterday` bigint(20) NOT NULL DEFAULT 0,
  `ip_yesterday` bigint(20) NOT NULL DEFAULT 0,
  `uk_yesterday` bigint(20) NOT NULL DEFAULT 0,
  PRIMARY KEY (`row_id`) USING BTREE,
  INDEX `IX_track_key`(`url_id`, `vi`, `vd`) USING BTREE,
  INDEX `IX_uv`(`uv_today`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '标签' ROW_FORMAT = DYNAMIC;

CREATE TABLE IF NOT EXISTS `stat_city_date_pv_uv_ip`  (
  `row_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `url_id` bigint(20) NOT NULL DEFAULT 0,
  `tag_id` int(11) NOT NULL DEFAULT 0,
  `province_code` int(11) NOT NULL DEFAULT 0 COMMENT 'xx0000',
  `city_code` int(11) NOT NULL DEFAULT 0 COMMENT 'xxyy00',
  `uv` bigint(20) NOT NULL DEFAULT 0,
  `pv` bigint(20) NOT NULL DEFAULT 0,
  `ip` bigint(20) NOT NULL DEFAULT 0,
  `uk` bigint(20) NOT NULL DEFAULT 0,
  `uv2` bigint(20) NOT NULL DEFAULT 0 COMMENT '汇总url的uv，仅用于tag',
  `log_date` int(11) NOT NULL DEFAULT 0 COMMENT 'yyyyMMdd',
  `admin_group` int(11) NOT NULL DEFAULT 0 COMMENT '管理组（同一组内可见）',
  PRIMARY KEY (`row_id`) USING BTREE,
  INDEX `IX_url_id`(`url_id`) USING BTREE,
  INDEX `IX_tag_id`(`tag_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

CREATE TABLE IF NOT EXISTS `stat_date_hour_pv_uv_ip`  (
  `row_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `url_id` bigint(20) NOT NULL DEFAULT 0,
  `tag_id` int(11) NOT NULL DEFAULT 0,
  `log_date` int(11) NOT NULL DEFAULT 0,
  `log_hour` int(11) NOT NULL DEFAULT 0,
  `pv` bigint(20) NOT NULL DEFAULT 0,
  `uv` bigint(20) NOT NULL DEFAULT 0,
  `ip` bigint(20) NOT NULL DEFAULT 0,
  `uk` bigint(20) NOT NULL DEFAULT 0,
  `uv2` bigint(20) NOT NULL DEFAULT 0 COMMENT '汇总url的uv，仅用于tag',
  `admin_group` int(11) NOT NULL DEFAULT 0 COMMENT '管理组（同一组内可见）',
  PRIMARY KEY (`row_id`) USING BTREE,
  UNIQUE INDEX `IX_url_key`(`url_id`, `tag_id`, `log_date`, `log_hour`) USING BTREE,
  INDEX `IX_tag_key`(`tag_id`, `log_date`, `log_hour`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

CREATE TABLE IF NOT EXISTS `stat_track_date_hour_pv_uv_ip`  (
  `row_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `url_id` bigint(20) NOT NULL DEFAULT 0,
  `tag_id` int(11) NOT NULL DEFAULT 0,
  `vi` int(11) NOT NULL DEFAULT 0,
  `vd` varchar(100)  NOT NULL DEFAULT '',
  `log_date` int(11) NOT NULL DEFAULT 0,
  `log_hour` int(11) NOT NULL DEFAULT 0,
  `uv` bigint(20) NOT NULL DEFAULT 0,
  `pv` bigint(20) NOT NULL DEFAULT 0,
  `ip` bigint(20) NOT NULL DEFAULT 0,
  `uk` bigint(20) NOT NULL DEFAULT 0,
  `admin_group` int(11) NOT NULL DEFAULT 0 COMMENT '管理组（同一组内可见）',
  PRIMARY KEY (`row_id`) USING BTREE,
  UNIQUE INDEX `IX_track_key`(`url_id`, `tag_id`, `vi`, `vd`, `log_date`, `log_hour`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

CREATE TABLE IF NOT EXISTS `stat_ua_client_date_pv_uv_ip`  (
  `row_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `url_id` bigint(20) NOT NULL DEFAULT 0,
  `tag_id` int(11) NOT NULL DEFAULT 0,
  `ua_client` int(11) NOT NULL DEFAULT 0 COMMENT '客户端。0=未知；1001支付宝，1002微信',
  `uv` bigint(20) NOT NULL DEFAULT 0,
  `pv` bigint(20) NOT NULL DEFAULT 0,
  `ip` bigint(20) NOT NULL DEFAULT 0,
  `uk` bigint(20) NOT NULL DEFAULT 0,
  `uv2` bigint(20) NOT NULL DEFAULT 0 COMMENT '汇总url的uv，仅用于tag',
  `log_date` int(11) NOT NULL DEFAULT 0 COMMENT 'yyyyMMdd',
  `admin_group` int(11) NOT NULL DEFAULT 0 COMMENT '管理组（同一组内可见）',
  PRIMARY KEY (`row_id`) USING BTREE,
  INDEX `IX_url_id`(`url_id`) USING BTREE,
  INDEX `IX_tag_id`(`tag_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

CREATE TABLE IF NOT EXISTS `stat_ua_platform_date_pv_uv_ip`  (
  `row_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `url_id` bigint(20) NOT NULL DEFAULT 0,
  `tag_id` int(11) NOT NULL DEFAULT 0,
  `ua_platform` int(11) NOT NULL DEFAULT 0 COMMENT '平台。0=未知；101=IPhone；102=iPad；111=Android；201=Win；211=Mac',
  `uv` bigint(20) NOT NULL DEFAULT 0,
  `pv` bigint(20) NOT NULL DEFAULT 0,
  `ip` bigint(20) NOT NULL DEFAULT 0,
  `uk` bigint(20) NOT NULL DEFAULT 0,
  `uv2` bigint(20) NOT NULL DEFAULT 0 COMMENT '汇总url的uv，仅用于tag',
  `log_date` int(11) NOT NULL DEFAULT 0 COMMENT 'yyyyMMdd',
  `admin_group` int(11) NOT NULL DEFAULT 0 COMMENT '管理组（同一组内可见）',
  PRIMARY KEY (`row_id`) USING BTREE,
  INDEX `IX_url_id`(`url_id`) USING BTREE,
  INDEX `IX_tag_id`(`tag_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

CREATE TABLE IF NOT EXISTS `track_tag`  (
  `tag_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '标签ID',
  `agroup_id` int(11) NOT NULL DEFAULT 0 COMMENT '标签父ID',
  `tag_name` varchar(50)  NULL DEFAULT NULL COMMENT '标签名称',
  `tag_access_key` varchar(40)  NOT NULL DEFAULT '',
  `t_user_field` varchar(40)  NOT NULL DEFAULT '' COMMENT '用户主键字段的参数名（在新建短地址时显示）',
  `t_track_params` varchar(100)  NOT NULL DEFAULT '' COMMENT '监听的参数（最多5个.例：c,b,d）（在新建短地址时显示）',
  `t_track_params_num` int(11) NOT NULL DEFAULT 0,
  `t_trans_params` varchar(100)  NOT NULL DEFAULT '' COMMENT '透传参数（例：c=x,b=y）（在新建短地址时显示）',
  `t_build_link` varchar(100)  NOT NULL DEFAULT '',
  `note` varchar(200)  NULL DEFAULT NULL,
  `admin_group` int(11) NOT NULL DEFAULT 0 COMMENT '管理组（同一管理组可见）',
  `tag_host` varchar(100)  NULL DEFAULT NULL COMMENT '标签主机',
  PRIMARY KEY (`tag_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '标签' ROW_FORMAT = DYNAMIC;

CREATE TABLE IF NOT EXISTS `track_tag_ex_stat`  (
  `tag_id` int(11) NOT NULL COMMENT '标签ID',
  `tag_pid` int(11) NOT NULL DEFAULT 0 COMMENT '标签父ID',
  `admin_group` int(11) NOT NULL DEFAULT 0 COMMENT '管理组（同一管理组可见）',
  `uv_total` bigint(20) NOT NULL DEFAULT 0,
  `pv_total` bigint(20) NOT NULL DEFAULT 0,
  `ip_total` bigint(20) NOT NULL DEFAULT 0,
  `uk_total` bigint(20) NOT NULL DEFAULT 0,
  `uv_today` bigint(20) NOT NULL DEFAULT 0,
  `pv_today` bigint(20) NOT NULL DEFAULT 0,
  `ip_today` bigint(20) NOT NULL DEFAULT 0,
  `uk_today` bigint(20) NOT NULL DEFAULT 0,
  `uv_yesterday` bigint(20) NOT NULL DEFAULT 0,
  `pv_yesterday` bigint(20) NOT NULL DEFAULT 0,
  `ip_yesterday` bigint(20) NOT NULL DEFAULT 0,
  `uk_yesterday` bigint(20) NOT NULL DEFAULT 0,
  PRIMARY KEY (`tag_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '标签' ROW_FORMAT = DYNAMIC;

CREATE TABLE IF NOT EXISTS `track_tag_ex_track_stat`  (
  `row_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tag_id` int(11) NOT NULL COMMENT '标签ID',
  `tag_pid` int(11) NOT NULL DEFAULT 0 COMMENT '标签父ID',
  `admin_group` int(11) NOT NULL DEFAULT 0 COMMENT '管理组（同一管理组可见）',
  `vi` int(11) NOT NULL DEFAULT 0,
  `vd` varchar(100)  NOT NULL DEFAULT '',
  `uv_total` bigint(20) NOT NULL DEFAULT 0,
  `pv_total` bigint(20) NOT NULL DEFAULT 0,
  `ip_total` bigint(20) NOT NULL DEFAULT 0,
  `uk_total` bigint(20) NOT NULL DEFAULT 0,
  `uv_today` bigint(20) NOT NULL DEFAULT 0,
  `pv_today` bigint(20) NOT NULL DEFAULT 0,
  `ip_today` bigint(20) NOT NULL DEFAULT 0,
  `uk_today` bigint(20) NOT NULL DEFAULT 0,
  `uv_yesterday` bigint(20) NOT NULL DEFAULT 0,
  `pv_yesterday` bigint(20) NOT NULL DEFAULT 0,
  `ip_yesterday` bigint(20) NOT NULL DEFAULT 0,
  `uk_yesterday` bigint(20) NOT NULL DEFAULT 0,
  PRIMARY KEY (`row_id`) USING BTREE,
  INDEX `IX_track_key`(`tag_id`, `vi`, `vd`) USING BTREE,
  INDEX `IX_uv`(`uv_today`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '标签' ROW_FORMAT = DYNAMIC;

