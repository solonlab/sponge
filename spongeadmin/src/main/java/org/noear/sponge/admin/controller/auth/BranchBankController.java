package org.noear.sponge.admin.controller.auth;

import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.core.handle.ModelAndView;
import org.noear.sponge.admin.controller.ViewModel;
import org.noear.sponge.admin.models.others.CountModel;
import org.noear.sponge.admin.controller.BaseController;
import org.noear.sponge.admin.dso.db.DbPandaApi;
import org.noear.sponge.admin.models.panda.BranchBankModel;

import java.sql.SQLException;
import java.util.List;

/**
 * @Author:Fei.chu
 * @Date:Created in 10:16 2019/04/02
 * @Description:
 */
@Controller
public class BranchBankController extends BaseController {

    //支行管理列表
    @Mapping("auth/bankbranch")
    public ModelAndView branchBank(Integer page, Integer pageSize,String bank_code,String name) throws SQLException{
        if (page == null) {
            page = 1; //从1开始（数据库那边要减1）
        }

        if (pageSize == null || pageSize == 0) {
            pageSize = 16;
        }
        CountModel count = new CountModel();

        List<BranchBankModel> bankCodes = DbPandaApi.getBranchBankCodes();
        List<BranchBankModel> branchs = DbPandaApi.getBranchBanks(page, pageSize, count, bank_code, name);

        viewModel.put("bankCodes",bankCodes);
        viewModel.put("branchs",branchs);
        viewModel.put("pageSize", pageSize);
        viewModel.put("rowCount", count.getCount());
        viewModel.put("bank_code",bank_code);
        viewModel.put("name",name);

        return view("auth/bankbranch");
    }

    //支行编辑页
    @Mapping("auth/bankbranch/bankbranch_edit")
    public ModelAndView bankbranchEdit(Long id) throws SQLException{
        if (id==0){
            viewModel.put("branchbank",new BranchBankModel());
        } else {
            BranchBankModel branch = DbPandaApi.getBranchBankById(id);
            viewModel.put("branchbank",branch);
        }
        return view("auth/bankbranch_edit");
    }

    //支行编辑保存
    @Mapping("auth/bankbranch/bankbranch_edit/ajax/saveEdit")
    public ViewModel saveEdit(Long id, String bank_code, String paysys_bank, String name, String bank_no, String phone, String bank_addr) throws SQLException{

        boolean isOk = DbPandaApi.setBranchBank(id, bank_code, paysys_bank, name, bank_no, phone, bank_addr);
        if (isOk) {
            viewModel.put("code",1);
        } else {
            viewModel.put("code",0);
        }
        return viewModel;
    }

}
