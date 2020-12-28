package webapp.dso;

/**
 *
 *
 *根据code获得对应值。
 */
public class SwichUtil {
    //学历
    public static String getQualification(int qualificationCode){
        String qualification = "";
        switch (qualificationCode){
            case 0:qualification = "高中以下";
                break;
            case 1:qualification = "高中/中专";
                break;
            case 2:qualification = "大专";
                break;
            case 3:qualification = "本科";
                break;
            case 4:qualification = "研究生";
                break;
                default:break;
        }
        return qualification;
    }
    //婚姻状态
    public static String getMstatus(int mstatusCode){
        String mstatus ="";
        switch (mstatusCode){
            case 0:mstatus = "未婚";
                break;
            case 1:mstatus = "已婚";
                break;
            case 2:mstatus = "离异";
                break;
            case 3:mstatus = "丧偶";
                break;
            case 4:mstatus = "再婚";
                break;
            case 5:mstatus = "复婚";
                break;
            default:break;
        }
        return mstatus;
    }

    //房产状态
    public static String getHouseStatus(int houseStatusCode){
        String house_status = "";
        switch (houseStatusCode){
            case 0:house_status = "无房";
                break;
            case 1:house_status = "有房有贷款";
                break;
            case 2:house_status = "有房无房贷";
                break;
            case 3:house_status = "其他";
                break;
            default:break;
        }
        return house_status;
    }

    //房产类型
    public static String getHouseType(int houseTypeCode){
        String house_type = "";
        switch (houseTypeCode){
            case 0:house_type = "商品房";
                break;
            case 1:house_type = "经济适用房";
                break;
            case 2:house_type = "商住两用房";
                break;
            case 3:house_type = "回迁房";
                break;
            case 4:house_type = "拆迁房";
                break;
            case 5:house_type = "自建房";
                break;
            case 6:house_type = "宅基地";
                break;
            case 7:house_type = "临建房";
                break;
            case 8:house_type = "期房";
                break;
            case 9:house_type = "预售房";
                break;
            case 10:house_type = "其他";
                break;
            default:break;
        }
        return house_type;
    }

    //职业类型
    public static String getCareer(int careerCode){
        String co_industry = "";
        switch (careerCode){
            case 0:co_industry = "自由职业";
                break;
            case 1:co_industry = "学生";
                break;
            case 2:co_industry = "家庭主妇";
                break;
            case 3:co_industry = "退休";
                break;
            case 4:co_industry = "实习生";
                break;
            case 5:co_industry = "摊贩";
                break;
            case 6:co_industry = "清洁人员";
                break;
            case 7:co_industry = "农林牧业";
                break;
            case 8:co_industry = "保安/司机/服务/外送人员";
                break;
            case 9:co_industry = "无技术工人";
                break;
            case 10:co_industry = "收获人员";
                break;
            case 11:co_industry = "业务人员";
                break;
            case 12:co_industry = "一般工人";
                break;
            case 13:co_industry = "个体商店老板";
                break;
            case 14:co_industry = "技能工作/文艺工作者";
                break;
            case 15:co_industry = "现役军人";
                break;
            case 16:co_industry = "操作人员";
                break;
            case 17:co_industry = "警察/消防员";
                break;
            case 18:co_industry = "企业基层主管/半专业人员";
                break;
            case 19:co_industry = "企业负责人、股东";
                break;
            case 20:co_industry = "军官";
                break;
            case 21:co_industry = "企业高中级主管";
                break;
            case 22:co_industry = "专业人员";
                break;
            case 23:co_industry = "政府官员/公务员";
                break;
            default:break;
        }
        return co_industry;
    }

    //工作时间
    public static String getCareer_time(int careerTimeCode) {
        String career_time = "";
        switch (careerTimeCode) {
            case 0:
                career_time = "1年以下";
                break;
            case 1:
                career_time = "1年";
                break;
            case 2:
                career_time = "2年";
                break;
            case 3:
                career_time = "3-4年";
                break;
            case 4:
                career_time = "5-7年";
                break;
            case 5:
                career_time = "8-9年";
                break;
            case 6:
                career_time = "10年以上";
                break;
            default:break;
        }
        return career_time;
    }

    //年收入
    public static String getSalary(int salaryCode) {
        String salary = "";
        switch (salaryCode) {
            case 0:
                salary = "10000以下";
                break;
            case 1:
                salary = "10000-50000";
                break;
            case 2:
                salary = "50000-100000";
                break;
            case 3:
                salary = "100000-200000";
                break;
            case 4:
                salary = "200000以上";
                break;
            default:break;
        }
        return salary;
    }
    //公司行业
    public static String getCo_industry(int coIndustryCode) {
        String co_industry = "";
        switch (coIndustryCode){
            case 0:co_industry = "农、林、牧、渔业";
                break;
            case 1:co_industry = "采掘业";
                break;
            case 2:co_industry = "制造业";
                break;
            case 3:co_industry = "电力、仓储和邮政业";
                break;
            case 4:co_industry = "信息传输、计算机服务和软件业";
                break;
            case 5:co_industry = "批发和零售业";
                break;
            case 6:co_industry = "住宿和餐饮业";
                break;
            case 7:co_industry = "金融业";
                break;
            case 8:co_industry = "房地产业";
                break;
            case 9:co_industry = "租赁和商务服务业";
                break;
            case 10:co_industry = "科学研究、技术服务业和地址勘察业";
                break;
            case 11:co_industry = "水里、环境和公共设施管理业";
                break;
            case 12:co_industry = "居民服务和其他服务业";
                break;
            case 13:co_industry = "教育";
                break;
            case 14:co_industry = "卫生、社会保障和社会福利业";
                break;
            case 15:co_industry = "文化、体育和娱乐业";
                break;
            case 16:co_industry = "公共管理和社会组织";
                break;
            case 17:co_industry = "国际组织";
                break;
            case 18:co_industry = "未知";
                break;
            default:break;
        }
        return co_industry;
    }
    //公司性质
    public static String getConature(int conatureCode) {
        String conature = "";
        switch (conatureCode){
            case 0:conature = "国有股份";
            break;
            case 1:conature = "合资";
                break;
            case 2:conature = "私营";
                break;
            case 3:conature = "个体";
                break;
            case 4:conature = "其他";
                break;
            default:break;
        }
        return conature;
    }
    //职位
    public static String getPosition(int positionCode) {
        String position = "";
        switch (positionCode){
            case 0:position = "高级资深人员";
            break;
            case 1:position = "中级技术人员";
            break;
            case 2:position = "初级/助理人员";
                break;
            case 3:position = "见习专员";
                break;
            case 4:position = "高层管理人员";
                break;
            case 5:position = "中层管理人员";
                break;
            case 6:position = "基层管理人员";
                break;
            case 7:position = "普通员工";
                break;
            default:break;
        }
        return position;
    }
}
