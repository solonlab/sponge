<%@ page import="sun.security.util.Length" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib prefix="ct" uri="/tags" %>
<!DOCTYPE HTML>
<html class='frm style2'>
<head>
    <title>${app} - 网银报告(借记卡)</title>
    <link rel="shortcut icon" type="image/x-icon" href="/favicon.ico"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8 "/>
    <link rel="stylesheet" href="//static.kdz6.cn/lib/durian/main.css"/>
    <script src="/_session/domain.js"></script>
    <script src="${js}/lib.js"></script>
</head>
<body>
<ct:header/>
<br/>
<h2>&nbsp;&nbsp;网银借记卡报告</h2>
<hr/>
<div class="bg13">
    <flex>
        <tile class="col-6  mar10">
            <h2>借记卡</h2>
            <div>
                <datagrid>
                    <table>
                        <thead>
                        <tr>
                            <td class="right" width="260px">字段描述</td>
                            <td class="right" >值</td>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td class="right">近3月收入金额(元)</td>
                            <td class="right">${report.debitcard_detailses.debitcard_income_3}</td>
                        </tr>
                        <tr>
                            <td class="right">近6月收入金额(元)</td>
                            <td class="right">${report.debitcard_detailses.debitCard_Income_6}</td>
                        </tr>
                        <tr>
                            <td class="right">近12月收入金额(元)</td>
                            <td class="right">${report.debitcard_detailses.debitCard_Income_12}</td>
                        </tr>
                        <tr>
                            <td class="right">近6月月均收入金额(元)</td>
                            <td class="right">${report.debitcard_detailses.debitcard_income_6_avg}</td>
                        </tr><tr>
                            <td class="right">近12月月均收入金额(元)</td>
                            <td class="right">${report.debitcard_detailses.debitcard_income_12_avg}</td>
                        </tr>
                        <tr>
                            <td class="right">近3月工资收入金额(元)</td>
                            <td class="right">${report.debitcard_detailses.salary_income_amount_3m}</td>
                        </tr>
                        <tr>
                            <td class="right">近6月工资收入金额(元)</td>
                            <td class="right">${report.debitcard_detailses.salary_income_amount_6m}</td>
                        </tr>
                        <tr>
                            <td class="right">近12月工资收入金额(元)</td>
                            <td class="right">${report.debitcard_detailses.salary_income_amount_12m}</td>
                        </tr>
                        <tr>
                            <td class="right">近6月月均工资收入金额(元)</td>
                            <td class="right">${report.debitcard_detailses.salary_income_amount_average_6m}</td>
                        </tr>
                        <tr>
                            <td class="right">近12月月均工资收入金额(元)</td>
                            <td class="right">${report.debitcard_detailses.salary_income_amount_average_12m}</td>
                        </tr>
                        <tr>
                            <td class="right">近3月有工资收入月数</td>
                            <td class="right">${report.debitcard_detailses.salary_income_month_num_3}</td>
                        </tr>
                        <tr>
                            <td class="right">近6月有工资收入月数</td>
                            <td class="right">${report.debitcard_detailses.salary_income_month_num_6}</td>
                        </tr>
                        <tr>
                            <td class="right">近12月有工资收入月数</td>
                            <td class="right">${report.debitcard_detailses.salary_income_month_num_12}</td>
                        </tr>
                        <tr>
                            <td class="right">近3月贷款收入金额</td>
                            <td class="right">${report.debitcard_detailses.loan_in_amount_3}</td>
                        </tr>
                        <tr>
                            <td class="right">近6月贷款收入金额</td>
                            <td class="right">${report.debitcard_detailses.loan_in_amount_6}</td>
                        </tr><tr>
                            <td class="right">近12月贷款收入金额</td>
                            <td class="right">${report.debitcard_detailses.loan_in_amount_12}</td>
                        </tr>
                        <tr>
                            <td class="right">近6月月均贷款收入金额</td>
                            <td class="right">${report.debitcard_detailses.loan_in_amount_avg_6}</td>
                        </tr>
                        <tr>
                            <td class="right">近12月月均贷款收入金额</td>
                            <td class="right">${report.debitcard_detailses.loan_in_amount_avg_12}</td>
                        </tr>
                        <tr>
                            <td class="right">近3月有贷款收入月数</td>
                            <td class="right">${report.debitcard_detailses.loan_in_month_num_3}</td>
                        </tr>
                        <tr>
                            <td class="right">近6月有贷款收入月数</td>
                            <td class="right">${report.debitcard_detailses.loan_in_month_num_6}</td>
                        </tr>
                        <tr>
                            <td class="right">近12月有贷款收入月数</td>
                            <td class="right">${report.debitcard_detailses.loan_in_month_num_12}</td>
                        </tr>
                        <tr>
                            <td class="right">近3月支出金额(元)</td>
                            <td class="right">${report.debitcard_detailses.debitcard_outcome_3}</td>
                        </tr>
                        <tr>
                            <td class="right">近6月支出金额(元)</td>
                            <td class="right">${report.debitcard_detailses.debitcard_outcome_6}</td>
                        </tr>
                        <tr>
                            <td class="right">近12月支出金额(元)</td>
                            <td class="right">${report.debitcard_detailses.debitcard_outcome_12}</td>
                        </tr>
                        <tr>
                            <td class="right">近6月月均支出金额(元)</td>
                            <td class="right">${report.debitcard_detailses.debitcard_outcome_6_avg}</td>
                        </tr>
                        <tr>
                            <td class="right">近12月月均支出金额(元)</td>
                            <td class="right">${report.debitcard_detailses.debitcard_outcome_12_avg}</td>
                        </tr><tr>
                            <td class="right">近3月贷款还款金额(元)</td>
                            <td class="right">${report.debitcard_detailses.repany_loan_amount_3}</td>
                        </tr>
                        <tr>
                            <td class="right">近6月贷款还款金额(元)</td>
                            <td class="right">${report.debitcard_detailses.repany_loan_amount_6}</td>
                        </tr>
                        <tr>
                            <td class="right">近12月贷款还款金额(元)</td>
                            <td class="right">${report.debitcard_detailses.repany_loan_amount_12}</td>
                        </tr>
                        <tr>
                            <td class="right">近6月月均贷款还款金额(元)</td>
                            <td class="right">${report.debitcard_detailses.repany_loan_amount_avg_6}</td>
                        </tr>
                        <tr>
                            <td class="right">近12月月均贷款还款金额(元)</td>
                            <td class="right">${report.debitcard_detailses.repany_loan_amount_avg_12}</td>
                        </tr>
                        <tr>
                            <td class="right">近3月有贷款还款月数</td>
                            <td class="right">${report.debitcard_detailses.repany_loan_mon_num_3}</td>
                        </tr>
                        <tr>
                            <td class="right">近6月有贷款还款月数</td>
                            <td class="right">${report.debitcard_detailses.repany_loan_mon_num_6}</td>
                        </tr>
                        <tr>
                            <td class="right">近12月有贷款还款月数</td>
                            <td class="right">${report.debitcard_detailses.repany_loan_mon_num_12}</td>
                        </tr>
                        <tr>
                            <td class="right">近3月无收入的月数/有流水的月数</td>
                            <td class="right">${report.debitcard_detailses.debitcard_in_num_trans_num_ratio_3}</td>
                        </tr>
                        <tr>
                            <td class="right">近6月无收入的月数/有流水的月数</td>
                            <td class="right">${report.debitcard_detailses.debitcard_in_num_trans_num_ratio_6}</td>
                        </tr>
                        <tr>
                            <td class="right">近12月无收入的月数/有流水的月数</td>
                            <td class="right">${report.debitcard_detailses.debitcard_in_num_trans_num_ratio_12}</td>
                        </tr><tr>
                            <td class="right">近3月最大余额(元)</td>
                            <td class="right">${report.debitcard_detailses.debitcard_max_balance_3}</td>
                        </tr>
                        <tr>
                            <td class="right">近6月最大余额(元)</td>
                            <td class="right">${report.debitcard_detailses.debitcard_max_balance_6}</td>
                        </tr>
                        <tr>
                            <td class="right">近12月最大余额(元)</td>
                            <td class="right">${report.debitcard_detailses.debitcard_max_balance_12}</td>
                        </tr>
                        <tr>
                            <td class="right">近3月最近余额(元)</td>
                            <td class="right">${report.debitcard_detailses.debitcard_recently_balance_3}</td>
                        </tr>
                        <tr>
                            <td class="right">近6月最近余额(元)</td>
                            <td class="right">${report.debitcard_detailses.debitcard_recently_balance_6}</td>
                        </tr>
                        <tr>
                            <td class="right">近12月最近余额(元)</td>
                            <td class="right">${report.debitcard_detailses.debitcard_recently_balance_12}</td>
                        </tr>
                        <tr>
                            <td class="right">近3月消费金额(元)</td>
                            <td class="right">${report.debitcard_detailses.debitcard_consume_amount_3}</td>
                        </tr>
                        <tr>
                            <td class="right">近6月消费金额(元)</td>
                            <td class="right">${report.debitcard_detailses.debitcard_consume_amount_6}</td>
                        </tr>
                        <tr>
                            <td class="right">近12月消费金额(元)</td>
                            <td class="right">${report.debitcard_detailses.debitcard_consume_amount_12}</td>
                        </tr>
                        <tr>
                            <td class="right">近6月月均消费金额(元)</td>
                            <td class="right">${report.debitcard_detailses.debitcard_consume_amount_6_avg}</td>
                        </tr>
                        <tr>
                            <td class="right">近12月月均消费金额(元)</td>
                            <td class="right">${report.debitcard_detailses.debitcard_consume_amount_12_avg}</td>
                        </tr><tr>
                            <td class="right">近3月消费笔数</td>
                            <td class="right">${report.debitcard_detailses.debitcard_consume_count_3}</td>
                        </tr>
                        <tr>
                            <td class="right">近6月消费笔数</td>
                            <td class="right">${report.debitcard_detailses.debitcard_consume_count_6}</td>
                        </tr>
                        <tr>
                            <td class="right">近12月消费笔数</td>
                            <td class="right">${report.debitcard_detailses.debitcard_consume_count_12}</td>
                        </tr>
                        <tr>
                            <td class="right">近6月月均消费笔数</td>
                            <td class="right">${report.debitcard_detailses.debitcard_consume_count_6_avg}</td>
                        </tr>
                        <tr>
                            <td class="right">近12月月均消费笔数</td>
                            <td class="right">${report.debitcard_detailses.debitcard_consume_count_12_avg}</td>
                        </tr>
                        <tr>
                            <td class="right">近3月最大连续消费月数</td>
                            <td class="right">${report.debitcard_detailses.debitcard_max_continue_consume_month_3}</td>
                        </tr>
                        <tr>
                            <td class="right">近6月最大连续消费月数</td>
                            <td class="right">${report.debitcard_detailses.debitcard_max_continue_consume_month_6}</td>
                        </tr>
                        <tr>
                            <td class="right">近12月最大连续消费月数</td>
                            <td class="right">${report.debitcard_detailses.debitcard_max_continue_consume_month_12}</td>
                        </tr>
                        <tr>
                            <td class="right">近3月取现金额(元)</td>
                            <td class="right">${report.debitcard_detailses.debitcard_withdraw_amount_3}</td>
                        </tr>
                        <tr>
                            <td class="right">近6月取现金额(元)</td>
                            <td class="right">${report.debitcard_detailses.debitcard_withdraw_amount_6}</td>
                        </tr>
                        <tr>
                            <td class="right">近12月取现金额(元)</td>
                            <td class="right">${report.debitcard_detailses.debitcard_withdraw_amount_12}</td>
                        </tr><tr>
                            <td class="right">近6月月均取现金额(元)</td>
                            <td class="right">${report.debitcard_detailses.debitcard_withdraw_amount_6_avg}</td>
                        </tr>
                        <tr>
                            <td class="right">近12月月均取现金额(元)</td>
                            <td class="right">${report.debitcard_detailses.debitcard_withdraw_amount_12_avg}</td>
                        </tr>
                        <tr>
                            <td class="right">近3月取现笔数</td>
                            <td class="right">${report.debitcard_detailses.debitcard_withdraw_count_3}</td>
                        </tr>
                        <tr>
                            <td class="right">近6月取现笔数</td>
                            <td class="right">${report.debitcard_detailses.debitcard_withdraw_count_6}</td>
                        </tr>
                        <tr>
                            <td class="right">近12月取现笔数</td>
                            <td class="right">${report.debitcard_detailses.debitcard_withdraw_count_12}</td>
                        </tr>
                        <tr>
                            <td class="right">近6月月均取现笔数</td>
                            <td class="right">${report.debitcard_detailses.debitcard_withdraw_count_6_avg}</td>
                        </tr>
                        <tr>
                            <td class="right">近12月月均取现笔数</td>
                            <td class="right">${report.debitcard_detailses.debitcard_withdraw_count_12_avg}</td>
                        </tr>
                        <tr>
                            <td class="right">近3月其他费用金额(元)</td>
                            <td class="right">${report.debitcard_detailses.other_fee_3}</td>
                        </tr>
                        <tr>
                            <td class="right">近6月其他费用金额(元)</td>
                            <td class="right">${report.debitcard_detailses.other_fee_6}</td>
                        </tr>
                        <tr>
                            <td class="right">近12月其他费用金额(元)</td>
                            <td class="right">${report.debitcard_detailses.other_fee_12}</td>
                        </tr>
                        <tr>
                            <td class="right">近6月月均其他费用金额(元)</td>
                            <td class="right">${report.debitcard_detailses.other_fee_avg_6}</td>
                        </tr><tr>
                            <td class="right">近12月月均其他费用金额(元)</td>
                            <td class="right">${report.debitcard_detailses.other_fee_avg_12}</td>
                        </tr>
                        <tr>
                            <td class="right">近3月定期最近一次金额(元)</td>
                            <td class="right">${report.debitcard_detailses.regular_savings_recent_3}</td>
                        </tr>
                        <tr>
                            <td class="right">近6月定期最近一次金额(元)</td>
                            <td class="right">${report.debitcard_detailses.regular_savings_recent_6}</td>
                        </tr>
                        <tr>
                            <td class="right">近12月定期最近一次金额(元)</td>
                            <td class="right">${report.debitcard_detailses.regular_savings_recent_12}</td>
                        </tr>
                        <tr>
                            <td class="right">近3月定期最大金额(元)</td>
                            <td class="right">${report.debitcard_detailses.regular_savings_max_3}</td>
                        </tr>
                        <tr>
                            <td class="right">近6月定期最大金额(元)</td>
                            <td class="right">${report.debitcard_detailses.regular_savings_max_6}</td>
                        </tr>
                        <tr>
                            <td class="right">近12月定期最大金额(元)</td>
                            <td class="right">${report.debitcard_detailses.regular_savings_max_12}</td>
                        </tr>
                        <tr>
                            <td class="right">近3月未到期定期存款金额</td>
                            <td class="right">${report.debitcard_detailses.undue_fixed_deposit_amount_3}</td>
                        </tr>
                        <tr>
                            <td class="right">近6月未到期定期存款金额</td>
                            <td class="right">${report.debitcard_detailses.undue_fixed_deposit_amount_6}</td>
                        </tr>
                        <tr>
                            <td class="right">近12月未到期定期存款金额</td>
                            <td class="right">${report.debitcard_detailses.undue_fixed_deposit_amount_12}</td>
                        </tr>
                        <tr>
                            <td class="right">近6月月均未到期定期存款金额</td>
                            <td class="right">${report.debitcard_detailses.undue_fixed_deposit_amount_avg_6}</td>
                        </tr><tr>
                            <td class="right">近12月月均未到期定期存款金额</td>
                            <td class="right">${report.debitcard_detailses.undue_fixed_deposit_amount_avg_12}</td>
                        </tr>
                        <tr>
                            <td class="right">近3月定期存款金额</td>
                            <td class="right">${report.debitcard_detailses.fixed_deposit_amount_3}</td>
                        </tr>
                        <tr>
                            <td class="right">近6月定期存款金额</td>
                            <td class="right">${report.debitcard_detailses.fixed_deposit_amount_6}</td>
                        </tr>
                        <tr>
                            <td class="right">近12月定期存款金额</td>
                            <td class="right">${report.debitcard_detailses.fixed_deposit_amount_12}</td>
                        </tr>
                        <tr>
                            <td class="right">近6月月均定期存款金额</td>
                            <td class="right">${report.debitcard_detailses.fixed_deposit_amount_avg_6}</td>
                        </tr>
                        <tr>
                            <td class="right">近12月月均定期存款金额</td>
                            <td class="right">${report.debitcard_detailses.fixed_deposit_amount_avg_12}</td>
                        </tr>
                        </tbody>
                    </table>
                </datagrid>
            </div>
        </tile>
        <tile class="col-6 mar10">
            <h2>基本信息</h2>
            <div>
                <datagrid>
                    <table>
                        <thead>
                        <tr>
                            <td class="right" width="300px">字段描述</td>
                            <td class="right" >值</td>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td class="right">姓名</td>
                            <td class="right">${report.user_basic_info.name}</td>
                        </tr>
                        <tr>
                            <td class="right">性别</td>
                            <td class="right">${report.user_basic_info.gender}</td>
                        </tr>
                        <tr>
                            <td class="right">证件类型</td>
                            <td class="right">${report.user_basic_info.certify_type}</td>
                        </tr>
                        <tr>
                            <td class="right">证件号码</td>
                            <td class="right">${report.user_basic_info.certify_no}</td>
                        </tr>
                        <tr>
                            <td class="right">手机号</td>
                            <td class="right">${report.user_basic_info.mobile}</td>
                        </tr>
                        <tr>
                            <td class="right">家庭地址</td>
                            <td class="right">${report.user_basic_info.address}</td>
                        </tr>
                        <tr>
                            <td class="right">邮箱</td>
                            <td class="right">${report.user_basic_info.email}</td>
                        </tr>
                        <tr>
                            <td class="right">活跃银行卡数</td>
                            <td class="right">${report.user_basic_info.active_card_num}</td>
                        </tr>
                        <tr>
                            <td class="right">活跃银行数</td>
                            <td class="right">${report.user_basic_info.bank_num}</td>
                        </tr>
                        <tr>
                            <td class="right">当前工作单位名称</td>
                            <td class="right">${report.user_basic_info.last_company_name}</td>
                        </tr>
                        <tr>
                            <td class="right">近1年工作单位数量</td>
                            <td class="right">${report.user_basic_info.company_num_1y}</td>
                        </tr>

                        <tr>
                            <td class="right">近1年收入(元)</td>
                            <td class="right">${report.user_basic_info.income_amt_1y}</td>
                        </tr>
                        <tr>
                            <td class="right">近1年工资收入(元)</td>
                            <td class="right">${report.user_basic_info.salary_income_1y}</td>
                        </tr>
                        <tr>
                            <td class="right">近1年贷款收入(元)</td>
                            <td class="right">${report.user_basic_info.loan_in_1y}</td>
                        </tr>
                        <tr>
                            <td class="right">近1年支出(元)</td>
                            <td class="right">${report.user_basic_info.expense_1y}</td>
                        </tr>
                        <tr>
                            <td class="right">近1年消费支出(元)</td>
                            <td class="right">${report.user_basic_info.consumption_expense_1y}</td>
                        </tr>
                        <tr>
                            <td class="right">近1年还贷支出(元)</td>
                            <td class="right">${report.user_basic_info.loan_out_1y}</td>
                        </tr>
                        </tbody>
                    </table>
                </datagrid>
            </div>
            <h2>账户摘要</h2>
            <div>
                <datagrid>
                    <table>
                        <thead>
                        <tr>
                            <td class="right" width="300px">字段描述</td>
                            <td class="right" >值</td>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td class="right">借记卡数</td>
                            <td class="right">${report.account_summary.debitcard_num}</td>
                        </tr>
                        <tr>
                            <td class="right">借记卡余额(元)</td>
                            <td class="right">${report.account_summary.debitcard_balance}</td>
                        </tr>
                        </tbody>
                    </table>
                </datagrid>
            </div>

            <h2>借记卡摘要</h2>
            <div>
                <datagrid>
                    <table>
                        <thead>
                        <tr>
                            <td class="right" width="300px">字段描述</td>
                            <td class="right" >值</td>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td class="right">近1月消费金额(元)</td>
                            <td class="right">${report.debitcard_summary.debitcard_shopping_amount_1m}</td>
                        </tr>
                        <tr>
                            <td class="right">近12月消费金额(元)</td>
                            <td class="right">${report.debitcard_summary.debitcard_consume_amount_12m}</td>
                        </tr>

                        <tr>
                            <td class="right">近1月消费笔数</td>
                            <td class="right">${report.debitcard_summary.debitcard_shopping_num_1m}</td>
                        </tr>
                        <tr>
                            <td class="right">近12月消费笔数</td>
                            <td class="right">${report.debitcard_summary.debitcard_consume_count_12m}</td>
                        </tr>
                        <tr>
                            <td class="right">近1月取现金额(元)</td>
                            <td class="right">${report.debitcard_summary.debitcard_cash_withdrawal_amount_1m}</td>
                        </tr>
                        <tr>
                            <td class="right">近12月取现金额(元)</td>
                            <td class="right">${report.debitcard_summary.debitcard_cash_withdrawal_amount_12m}</td>
                        </tr>
                        <tr>
                            <td class="right">近1月取现笔数</td>
                            <td class="right">${report.debitcard_summary.debitcard_cash_withdrawal_num_1m}</td>
                        </tr>
                        <tr>
                            <td class="right">近12月取现笔数</td>
                            <td class="right">${report.debitcard_summary.debitcard_cash_withdrawal_count_12m}</td>
                        </tr><tr>
                            <td class="right">近1月定期记录数</td>
                            <td class="right">${report.debitcard_summary.debitcard_fixed_deposit_num_1m}</td>
                        </tr>
                        <tr>
                            <td class="right">近12月定期记录数</td>
                            <td class="right">${report.debitcard_summary.debitcard_fixed_deposit_num_12m}</td>
                        </tr>
                        <tr>
                            <td class="right">近1月定期最大金额(元)</td>
                            <td class="right">${report.debitcard_summary.debitcard_lagest_fixed_deposit_amount_1m}</td>
                        </tr>
                        <tr>
                            <td class="right">近12月定期最大金额(元)</td>
                            <td class="right">${report.debitcard_summary.debitcard_lagest_fixed_deposit_amount_12m}</td>
                        </tr>
                        <tr>
                            <td class="right">近1月其他消费金额(元)</td>
                            <td class="right">${report.debitcard_summary.debitcard_other_consumption_amount_1m}</td>
                        </tr>
                        <tr>
                            <td class="right">近12月其他消费金额(元)</td>
                            <td class="right">${report.debitcard_summary.debitcard_other_consumption_amount_12m}</td>
                        </tr>
                        <tr>
                            <td class="right">近1月其他消费笔数</td>
                            <td class="right">${report.debitcard_summary.debitcard_other_consumption_num_1m}</td>
                        </tr>
                        <tr>
                            <td class="right">近12月其他消费笔数</td>
                            <td class="right">${report.debitcard_summary.debitcard_other_consumption_num_12m}</td>
                        </tr>
                        </tbody>
                    </table>
                </datagrid>
            </div>

            <h2>借记卡未到期定期详情</h2>
            <div>
                <datagrid>
                    <table>
                        <thead>
                        <tr>
                            <td class="right" width="300px">字段描述</td>
                            <td class="right" >值</td>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="m" items="${report.debitcard_undue_regular_basis_list}">
                            <tr>
                                <td class="right">卡号</td>
                                <td class="right">${m.cardId}</td>
                            </tr>
                            <tr>
                                <td class="right">金额</td>
                                <td class="right">${m.balance}</td>
                            </tr>
                            <tr>
                                <td class="right">到期日期</td>
                                <td class="right">${m.duedate}</td>
                            </tr>
                            <tr>
                                <td class="right">期数</td>
                                <td class="right">${m.period}</td>
                            </tr>
                        </c:forEach>

                        </tbody>
                    </table>
                </datagrid>
            </div>

            <h2>工作单位详情</h2>
            <div>
                <datagrid>
                    <table>
                        <thead>
                        <tr>
                            <td class="right" width="300px">字段描述</td>
                            <td class="right" >值</td>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="m" items="${report.work_details_list}">
                            <tr>
                                <td class="right">单位名称</td>
                                <td class="right">${m.company_name}</td>
                            </tr>
                            <tr>
                                <td class="right">首次工资收入时间</td>
                                <td class="right">${m.first_salary_time}</td>
                            </tr>
                            <tr>
                                <td class="right">最近工资收入时间</td>
                                <td class="right">${m.last_salary_time}</td>
                            </tr>
                            <tr>
                                <td class="right">连续有工资收入月数</td>
                                <td class="right">${m.continuous_salary_mon_num}</td>
                            </tr>
                        </c:forEach>

                        </tbody>
                    </table>
                </datagrid>
            </div>
        </tile>
    </flex>
</div>
</body>
<script>

</script>
</html>
