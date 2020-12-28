package org.noear.sponge.admin.controller.auth;

import org.apache.http.util.TextUtils;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.core.handle.ModelAndView;
import org.noear.sponge.admin.controller.ViewModel;
import org.noear.sponge.admin.dso.db.DbPandaApi;
import org.noear.sponge.admin.models.others.CountModel;
import org.noear.sponge.admin.models.panda.BankCardBinModel;
import org.noear.sponge.admin.controller.BaseController;

import java.sql.SQLException;
import java.util.List;

/**
 * @Author:Fei.chu
 * @Date:Created in 10:54 2019/04/01
 * @Description:卡bin管理
 */
@Controller
public class CardbinController extends BaseController {

    //卡bin列表
    @Mapping("auth/cardbin")
    public ModelAndView cardbin(String bank_name) throws SQLException{
        List<BankCardBinModel> banks = DbPandaApi.getBanks();
        if (TextUtils.isEmpty(bank_name)){
            if (banks.size()>0){
                viewModel.put("bank_name",banks.get(0).bank_name);
            }
        } else {
            viewModel.put("bank_name",bank_name);
        }
        viewModel.put("banks",banks);
        return view("auth/cardbin");
    }

    //卡bin列表
    @Mapping("auth/cardbin/cardbin_inner")
    public ModelAndView cardbinInner(Integer page, Integer pageSize,String bank_name,String card_type,String bin) throws SQLException{
        if (page == null) {
            page = 1; //从1开始（数据库那边要减1）
        }

        if (pageSize == null || pageSize == 0) {
            pageSize = 16;
        }
        CountModel count = new CountModel();

        List<BankCardBinModel> cards = DbPandaApi.getBankCardBins(page,pageSize,count,bank_name,card_type,bin);
        if (cards.size()>0){
            viewModel.put("logo",TextUtils.isEmpty(cards.get(0).logo)?"http://img.kdz6.cn/bank_icon/3eeff6d9-3e14-45a3-a0c0-67629056cf9b.png":cards.get(0).logo);
        }
        List<BankCardBinModel> types = DbPandaApi.getCardTypes(bank_name);

        viewModel.put("cards",cards);
        viewModel.put("pageSize", pageSize);
        viewModel.put("rowCount", count.getCount());
        viewModel.put("bank_name",bank_name);
        viewModel.put("bin",bin);
        viewModel.put("card_type",card_type);
        viewModel.put("types",types);

        return view("auth/cardbin_inner");
    }

    //卡bin编辑页
    @Mapping("auth/cardbin/cardbin_edit")
    public ModelAndView cardbin_edit(Long id) throws SQLException{
        if (id>0){
            BankCardBinModel bin = DbPandaApi.getBankCardBinById(id);
            viewModel.put("bankCardbin",bin);
        } else {
            viewModel.put("bankCardbin",new BankCardBinModel());
        }
        return view("auth/cardbin_edit");
    }

    //保存卡bin编辑
    @Mapping("auth/cardbin/cardbin_edit/ajax/saveEdit")
    public ViewModel saveEdit(Long id, String bank_name, String org_no, String logo, String card_type, String card_name, Integer card_length,
                              Integer bin_length, String bin, String bank_code) throws SQLException{

        boolean isOk = DbPandaApi.setBankCardBin(id, bank_name, org_no, logo, card_type, card_name, card_length, bin_length, bin, bank_code);
        if (isOk){
            viewModel.put("code",1);
            viewModel.put("bank_code",bank_code);
        } else {
            viewModel.put("code",0);
        }
        return viewModel;
    }
}
