package webapp.models.others.ienum;

/**
 * @Author:Fei.chu
 * @Description: 芝麻信用风险说明(欺诈关注)
 */
public enum ZMRiskDescEnum {

    R_CN_001("R_CN_001","身份证号击中网络欺诈风险名单"),
    R_CN_002("R_CN_002","身份证号曾经被泄露"),
    R_CN_003("R_CN_003","身份证号曾经被冒用"),
    R_CN_004("R_CN_004","身份证号出现在风险关联网络"),
    R_CN_JJ_01("R_CN_JJ_01","身份证号当天在多个商户申请"),
    R_CN_JJ_02("R_CN_JJ_02","身份证近一周（不包含当天）在多个商户申请"),
    R_CN_JJ_03("R_CN_JJ_03","身份证近一月（不包含当天）在多个商户申请"),
    R_PH_001("R_PH_001","手机号击中网络欺诈风险名单"),
    R_PH_002("R_PH_002","手机号疑似多个用户共用"),
    R_PH_003("R_PH_003","手机号疑似虚拟运营商小号"),
    R_PH_004("R_PH_004","手机号疑似二次放号"),
    R_PH_005("R_PH_005","手机号失联风险高"),
    R_PH_006("R_PH_006","手机号稳定性弱"),
    R_PH_JJ_01("R_PH_JJ_01","手机号当天在多个商户申请"),
    R_PH_JJ_02("R_PH_JJ_02","手机号近一周（不包含当天）在多个商户申请"),
    R_PH_JJ_03("R_PH_JJ_03","手机号近一月（不包含当天）在多个商户申请"),
    R_BC_001("R_BC_001","银行卡击中网络欺诈风险名单"),
    R_BC_002("R_BC_002","银行卡曾经被泄露"),
    R_BC_003("R_BC_003","银行卡曾经被冒用"),
    R_AD_001("R_AD_001","疑似虚假地址"),
    R_MC_001("R_MC_001","疑似篡改的MAC"),
    R_MC_002("R_MC_002","MAC击中网络欺诈风险名单"),
    R_MC_003("R_MC_003","手机MAC近期内不活跃"),
    R_MC_004("R_MC_004","手机MAC较新"),
    R_MC_005("R_MC_005","恶意攻击MAC"),
    R_MC_006("R_MC_006","疑似中介MAC"),
    R_MC_JJ_01("R_MC_JJ_01","当天多个用户通过该MAC申请"),
    R_MC_JJ_02("R_MC_JJ_02","近一周（不包含当天）多个用户通过该MAC申请"),
    R_MC_JJ_03("R_MC_JJ_03","近一月（不包含当天）多个用户通过该MAC申请"),
    R_IP_001("R_IP_001","代理IP"),
    R_IP_002("R_IP_002","服务器IP"),
    R_IP_003("R_IP_003","热点IP"),
    R_IP_004("R_IP_004","IP近期不活跃"),
    R_IP_005("R_IP_005","IP较新"),
    R_IP_006("R_IP_006","IP上聚集多个设备"),
    R_IP_007("R_IP_007","IP上设备分布异常"),
    R_IP_008("R_IP_008","疑似中介IP"),
    R_IP_JJ_01("R_IP_JJ_01","当天多个用户通过该IP申请"),
    R_IP_JJ_02("R_IP_JJ_02","近一周（不包含当天）多个用户通过该IP申请"),
    R_IP_JJ_03("R_IP_JJ_03","近一月（不包含当天）多个用户通过该IP申请"),
    R_IM_001("R_IM_001","IMEI击中网络欺诈风险名单"),
    R_IM_002("R_IM_002","IMEI近期不活跃"),
    R_IM_003("R_IM_003","IMEI较新"),
    R_IM_004("R_IM_004","疑似虚假IMEI"),
    R_IM_JJ_01("R_IM_JJ_01","当天多个用户通过该IMEI申请"),
    R_IM_JJ_02("R_IM_JJ_02","近一周（不包含当天）多个用户通过该IMEI申请"),
    R_IM_JJ_03("R_IM_JJ_03","近一月（不包含当天）多个用户通过该IMEI申请");


    private final String code;
    public String value;

    ZMRiskDescEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public static ZMRiskDescEnum getEnumByCode(String code){
        switch (code){
            case "R_CN_001":
                return R_CN_001;
            case "R_CN_002":
                return R_CN_002;
            case "R_CN_003":
                return R_CN_003;
            case "R_CN_004":
                return R_CN_004;
            case "R_CN_JJ_01":
                return R_CN_JJ_01;
            case "R_CN_JJ_02":
                return R_CN_JJ_02;
            case "R_CN_JJ_03":
                return R_CN_JJ_03;
            case "R_PH_001":
                return R_PH_001;
            case "R_PH_002":
                return R_PH_002;
            case "R_PH_003":
                return R_PH_003;
            case "R_PH_004":
                return R_PH_004;
            case "R_PH_005":
                return R_PH_005;
            case "R_PH_006":
                return R_PH_006;
            case "R_PH_JJ_01":
                return R_PH_JJ_01;
            case "R_PH_JJ_02":
                return R_PH_JJ_02;
            case "R_PH_JJ_03":
                return R_PH_JJ_03;
            case "R_BC_001":
                return R_BC_001;
            case "R_BC_002":
                return R_BC_002;
            case "R_BC_003":
                return R_BC_003;
            case "R_AD_001":
                return R_AD_001;
            case "R_MC_001":
                return R_MC_001;
            case "R_MC_002":
                return R_MC_002;
            case "R_MC_003":
                return R_MC_003;
            case "R_MC_004":
                return R_MC_004;
            case "R_MC_005":
                return R_MC_005;
            case "R_MC_006":
                return R_MC_006;
            case "R_MC_JJ_01":
                return R_MC_JJ_01;
            case "R_MC_JJ_02":
                return R_MC_JJ_02;
            case "R_MC_JJ_03":
                return R_MC_JJ_03;
            case "R_IP_001":
                return R_IP_001;
            case "R_IP_002":
                return R_IP_002;
            case "R_IP_003":
                return R_IP_003;
            case "R_IP_004":
                return R_IP_004;
            case "R_IP_005":
                return R_IP_005;
            case "R_IP_006":
                return R_IP_006;
            case "R_IP_007":
                return R_IP_007;
            case "R_IP_008":
                return R_IP_008;
            case "R_IP_JJ_01":
                return R_IP_JJ_01;
            case "R_IP_JJ_02":
                return R_IP_JJ_02;
            case "R_IP_JJ_03":
                return R_IP_JJ_03;
            case "R_IM_001":
                return R_IM_001;
            case "R_IM_002":
                return R_IM_002;
            case "R_IM_003":
                return R_IM_003;
            case "R_IM_004":
                return R_IM_004;
            case "R_IM_JJ_01":
                return R_IM_JJ_01;
            case "R_IM_JJ_02":
                return R_IM_JJ_02;
            case "R_IM_JJ_03":
                return R_IM_JJ_03;
            default:
                return R_CN_001;
        }
    }
}
