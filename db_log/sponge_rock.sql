
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