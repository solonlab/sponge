ALTER TABLE `appx_ex_i18n`
    ADD COLUMN `is_disabled` int(1) NOT NULL DEFAULT 0 COMMENT '禁用' AFTER `note`;


ALTER TABLE `appx_ex_code`
    ADD COLUMN `is_disabled` int(1) NOT NULL DEFAULT 0 COMMENT '禁用' AFTER `note`;