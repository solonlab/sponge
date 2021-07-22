
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