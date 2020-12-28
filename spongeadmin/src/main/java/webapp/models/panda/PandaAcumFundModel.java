package webapp.models.panda;

import lombok.Data;
import org.noear.weed.GetHandlerEx;
import org.noear.weed.IBinder;
import org.apache.http.util.TextUtils;
import webapp.utils.StringUtil;

import java.util.Date;

@Data
public class PandaAcumFundModel implements IBinder {

    public long row_id;
    public long open_id;
    public String real_name;
    public int certificate_type;
    public String certificate_number;
    public String mobile;
    public String corporation_name;
    public long base_number;
    public long balance;
    public int pay_status;
    public String region;
    public int type;
    public String details_url_basic;
    public String details_url_loan;
    public String details_url_payment;
    public String details_url_repay;
    public String details_url_report;
    public String details_url_total;
    public int is_delete;
    public Date create_fulltime;
    public int create_date;
    public Date update_fulltime;
    public int update_date;

    public void bind(GetHandlerEx s) {
        row_id = s.get("row_id").value(0l);
        open_id = s.get("open_id").value(0l);
        real_name = s.get("real_name").value(null);
        certificate_number = s.get("certificate_number").value(null);
        certificate_type = s.get("certificate_type").value(0);
        mobile = s.get("mobile").value(null);
        corporation_name = s.get("corporation_name").value(null);
        base_number = s.get("base_number").value(0l);
        balance = s.get("balance").value(0l);
        pay_status = s.get("pay_status").value(0);
        region = s.get("region").value(null);
        type = s.get("type").value(0);
        details_url_basic = s.get("details_url_basic").value(null);
        details_url_loan = s.get("details_url_loan").value(null);
        details_url_payment = s.get("details_url_payment").value(null);
        details_url_repay = s.get("details_url_repay").value(null);
        details_url_report = s.get("details_url_report").value(null);
        details_url_total = s.get("details_url_total").value(null);
        is_delete = s.get("is_delete").value(0);
        create_fulltime = s.get("create_fulltime").value(null);
        create_date = s.get("create_date").value(0);
        update_fulltime = s.get("update_fulltime").value(null);
        update_date = s.get("update_date").value(0);
    }

    public IBinder clone() {
        return new PandaAcumFundModel();
    }

    public String getStatusName() {
        String stateName = "";
        switch (pay_status){
            case 1:stateName = "未缴存";break;
            case 2:stateName = "正常";break;
            case 3:stateName = "停缴";break;
            case 4:stateName = "注销";break;
        }
        return stateName;
    }

    public String getHideName() {
        if (TextUtils.isEmpty(real_name)){
            return "";
        }
        return StringUtil.hideName(real_name);
    }

    public String getHideIdCode() {
        if (TextUtils.isEmpty(certificate_number)){
            return "";
        }
        return StringUtil.getHideIdCode(certificate_number);
    }
}