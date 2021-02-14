package org.noear.sponge.admin.model.others.ienum;

/**
 *省份
 */
public enum  ProvinceEnum {
    BEIJING(110000, "北京"),
    TIANJIN(120000, "天津"),
    SHAGNHAI(310000, "上海"),
    CHONGQING(500000, "重庆"),
    HEBEI(130000, "河北"),
    HENAN(410000, "河南"),
    YUNNAN(530000, "云南"),
    LIAONING(210000, "辽宁"),
    HEILONGJIANG(230000, "黑龙江"),
    HUNAN(430000, "湖南"),
    ANHUI(340000, "安徽"),
    SHANDONG(370000, "山东"),
    XINJIANG(650000, "新疆"),
    JIANGSU(320000, "江苏"),
    ZHEJIANG(330000, "浙江"),
    JIANGXI(360000, "江西"),
    HUBEI(420000, "湖北"),
    GUANGXI(450000, "广西"),
    GANSU(620000, "甘肃"),
    SHANXI(140000, "山西"),
    NEIMENGGU(150000, "内蒙古"),
    SHAANXI(610000, "陕西"),
    JILIN(220000, "吉林"),
    FUJIAN(350000, "福建"),
    GUIZHOU(520000, "贵州"),
    GUANGDONG(440000, "广东"),
    QINGHAI(630000, "青海"),
    XIZANG(540000, "西藏"),
    SICHUAN(510000, "四川"),
    NINGXIA(640000, "宁夏"),
    HAINAN(460000, "海南"),
    TAIWAN(710000, "台湾"),
    XIANGGANG(810000, "香港"),
    AOMEN(820000, "澳门"),
    NONE(0,"");

    private final Integer code;
    public String value;

    ProvinceEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public Integer getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public static ProvinceEnum getEnumByCode(Integer code) {
        switch (code) {
            case 110000:
                return BEIJING;
            case 120000:
                return TIANJIN;
            case 310000:
                return SHAGNHAI;
            case 500000:
                return CHONGQING;
            case 130000:
                return HEBEI;
            case 410000:
                return HENAN;
            case 530000:
                return YUNNAN;
            case 210000:
                return LIAONING;
            case 230000:
                return HEILONGJIANG;
            case 430000:
                return HUNAN;
            case 340000:
                return ANHUI;
            case 370000:
                return SHANDONG;
            case 650000:
                return XINJIANG;
            case 320000:
                return JIANGSU;
            case 330000:
                return ZHEJIANG;
            case 360000:
                return JIANGXI;
            case 420000:
                return HUBEI;
            case 450000:
                return GUANGXI;
            case 620000:
                return GANSU;
            case 140000:
                return SHANXI;
            case 150000:
                return NEIMENGGU;
            case 610000:
                return SHAANXI;
            case 220000:
                return JILIN;
            case 350000:
                return FUJIAN;
            case 520000:
                return GUIZHOU;
            case 440000:
                return GUANGDONG;
            case 630000:
                return QINGHAI;
            case 540000:
                return XIZANG;
            case 510000:
                return SICHUAN;
            case 640000:
                return NINGXIA;
            case 460000:
                return HAINAN;
            case 710000:
                return TAIWAN;
            case 810000:
                return XIANGGANG;
            case 820000:
                return AOMEN;
            default:
                return NONE;
        }
    }
}
