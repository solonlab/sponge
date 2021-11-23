
-- 2021.07.15

ALTER TABLE `appx_ex_i18n`
    ADD COLUMN `is_disabled` int(1) NOT NULL DEFAULT 0 COMMENT '禁用' AFTER `note`;


ALTER TABLE `appx_ex_code`
    ADD COLUMN `is_disabled` int(1) NOT NULL DEFAULT 0 COMMENT '禁用' AFTER `note`;


ALTER TABLE `appx_ex_setting`
    ADD COLUMN `is_disabled` int(1) NOT NULL DEFAULT 0 COMMENT '禁用' AFTER `note`;


-- 2021.07.22

ALTER TABLE `appx`
    ADD COLUMN `is_disabled` int(1) NOT NULL DEFAULT 0 COMMENT '禁用' AFTER `note`;

-- 2021.09.22
ALTER TABLE `appx_ex_code`
    MODIFY COLUMN `lang` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' AFTER `code`;

-- 2021.11.23 (添加密钥盐)
ALTER TABLE `appx`
    MODIFY COLUMN `app_key` varchar(40)  NULL DEFAULT NULL COMMENT '应用标识' AFTER `app_id`,
    MODIFY COLUMN `akey` varchar(40)  NULL DEFAULT NULL COMMENT '弃用-由app_key取代' AFTER `app_secret_key`;

ALTER TABLE `appx`
    ADD COLUMN `app_secret_salt` varchar(255) NULL COMMENT '应用密钥盐' AFTER `app_secret_key`;





