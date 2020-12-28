package webapp.models.others.ienum;

/**
 * @Author:Fei.chu
 * @Description: 芝麻信用风险等级(欺诈关注)
 */
public enum ZMRiskLevelEnum {

    R_CN_001("R_CN_001","中风险"),
    R_CN_002("R_CN_002","低风险"),
    R_CN_003("R_CN_003","低风险"),
    R_CN_004("R_CN_004","低风险"),
    R_CN_JJ_01("R_CN_JJ_01","中风险"),
    R_CN_JJ_02("R_CN_JJ_02","中风险"),
    R_CN_JJ_03("R_CN_JJ_03","中风险"),
    R_PH_001("R_PH_001","中风险"),
    R_PH_002("R_PH_002","低风险"),
    R_PH_003("R_PH_003","中风险"),
    R_PH_004("R_PH_004","高风险"),
    R_PH_005("R_PH_005","高风险"),
    R_PH_006("R_PH_006","低风险"),
    R_PH_JJ_01("R_PH_JJ_01","中风险"),
    R_PH_JJ_02("R_PH_JJ_02","中风险"),
    R_PH_JJ_03("R_PH_JJ_03","中风险"),
    R_BC_001("R_BC_001","中风险"),
    R_BC_002("R_BC_002","低风险"),
    R_BC_003("R_BC_003","低风险"),
    R_AD_001("R_AD_001","低风险"),
    R_MC_001("R_MC_001","中风险"),
    R_MC_002("R_MC_002","中风险"),
    R_MC_003("R_MC_003","低风险"),
    R_MC_004("R_MC_004","低风险"),
    R_MC_005("R_MC_005","中风险"),
    R_MC_006("R_MC_006","中风险"),
    R_MC_JJ_01("R_MC_JJ_01","中风险"),
    R_MC_JJ_02("R_MC_JJ_02","中风险"),
    R_MC_JJ_03("R_MC_JJ_03","中风险"),
    R_IP_001("R_IP_001","中风险"),
    R_IP_002("R_IP_002","低风险"),
    R_IP_003("R_IP_003","低风险"),
    R_IP_004("R_IP_004","低风险"),
    R_IP_005("R_IP_005","低风险"),
    R_IP_006("R_IP_006","低风险"),
    R_IP_007("R_IP_007","低风险"),
    R_IP_008("R_IP_008","中风险"),
    R_IP_JJ_01("R_IP_JJ_01","中风险"),
    R_IP_JJ_02("R_IP_JJ_02","中风险"),
    R_IP_JJ_03("R_IP_JJ_03","中风险"),
    R_IM_001("R_IM_001","中风险"),
    R_IM_002("R_IM_002","低风险"),
    R_IM_003("R_IM_003","低风险"),
    R_IM_004("R_IM_004","中风险"),
    R_IM_JJ_01("R_IM_JJ_01","中风险"),
    R_IM_JJ_02("R_IM_JJ_02","中风险"),
    R_IM_JJ_03("R_IM_JJ_03","中风险");


    private final String code;
    public String value;

    ZMRiskLevelEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public static ZMRiskLevelEnum getEnumByCode(String code){
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
