<%@ page import="sun.security.util.Length" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib prefix="ct" uri="/tags" %>
<!DOCTYPE HTML>
<html class='frm style2'>
<head>
    <title>${app} - 网银报告(信用卡)</title>
    <link rel="shortcut icon" type="image/x-icon" href="/favicon.ico"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8 "/>
    <link rel="stylesheet" href="//static.kdz6.cn/lib/durian/main.css"/>
    <script src="/_session/domain.js"></script>
    <script src="${js}/lib.js"></script>
</head>
<body>
<ct:header/>
<br/>
<h2>&nbsp;&nbsp;网银信用卡报告</h2>
<hr/>
<div class="bg13">
    <flex>
        <tile class="col-6  mar10">
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
                            <td class="right">邮箱</td>
                            <td class="right">${report.user_basic_info.email}</td>
                        </tr>
                        <tr>
                            <td class="right">活跃卡数</td>
                            <td class="right">${report.user_basic_info.active_card_num}</td>
                        </tr>
                        <tr>
                            <td class="right">银行数</td>
                            <td class="right">${report.user_basic_info.bank_num}</td>
                        </tr>
                        <tr>
                            <td class="right">最初账单日</td>
                            <td class="right">${report.user_basic_info.bill_start_date}</td>
                        </tr>

                        <tr>
                            <td class="right">最早一期账单距今月份数（MOB）</td>
                            <td class="right">${report.user_basic_info.bill_start_date_month}</td>
                        </tr>
                        <tr>
                            <td class="right">客户族群标志</td>
                            <td class="right">${report.user_basic_info.pvcu_customer_group_tag}</td>
                        </tr>
                        <tr>
                            <td class="right">客户套现标志</td>
                            <td class="right">${report.user_basic_info.pvcu_cashouts_tag}</td>
                        </tr>
                        <tr>
                            <td class="right">账单最新认证时间</td>
                            <td class="right">${report.user_basic_info.latest_certification_time}</td>
                        </tr>
                        </tbody>
                    </table>
                </datagrid>
            </div>
        </tile>
        <tile class="col-6 mar10">
            <h2>授信情况</h2>
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
                            <td class="right">总信用额(元)</td>
                            <td class="right">${report.account_summary.creditcard_limit}</td>
                        </tr>
                        <tr>
                            <td class="right">总可用信用额（元)</td>
                            <td class="right">${report.account_summary.creditcard_balance}</td>
                        </tr>
                        <tr>
                            <td class="right">可用消费信用额（元)</td>
                            <td class="right">${report.account_summary.total_can_use_consume_limit_1}</td>
                        </tr>
                        <tr>
                            <td class="right">提现信用额(元)</td>
                            <td class="right">${report.account_summary.creditcard_cash_limit}</td>
                        </tr>
                        <tr>
                            <td class="right">可用提现信用额(元)</td>
                            <td class="right">${report.account_summary.creditcard_cash_balance}</td>
                        </tr>
                        <tr>
                            <td class="right">消费信用额(元)</td>
                            <td class="right">${report.account_summary.consume_credit_limit}</td>
                        </tr>
                        <tr>
                            <td class="right">单一银行最高授信额度(元)</td>
                            <td class="right">${report.account_summary.single_bank_max_limit}</td>
                        </tr>
                        <tr>
                            <td class="right">单一银行最低授信额度(元)</td>
                            <td class="right">${report.account_summary.single_bank_min_limit}</td>
                        </tr>
                        </tbody>
                    </table>
                </datagrid>
            </div>
        </tile>
    </flex>

    <flex>
        <tile class="col-6  mar10">
            <h2>逾期信息</h2>
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
                            <td class="right">逾期标志</td>
                            <td class="right">${report.overdue_information.delay_tag_1}</td>
                        </tr>
                        <tr>
                            <td class="right">逾期状态</td>
                            <td class="right">${report.overdue_information.delay_status_1}</td>
                        </tr>
                        <tr>
                            <td class="right">未还金额(元)</td>
                            <td class="right">${report.overdue_information.delay_amount_1}</td>
                        </tr>
                        <tr>
                            <td class="right">未还金额占比</td>
                            <td class="right">${report.overdue_information.delay_amount_per_1}</td>
                        </tr>
                        <tr>
                            <td class="right">逾期账单数量</td>
                            <td class="right">${report.overdue_information.delay_bill_num_1}</td>
                        </tr>
                        </tbody>
                    </table>
                </datagrid>
            </div>
        </tile>
        <tile class="col-6 mar10">
            <h2>利费信息</h2>
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
                            <td class="right">总利息(元)</td>
                            <td class="right">${report.interest_information.delay_interest_1}</td>
                        </tr>
                        <tr>
                            <td class="right">延滞金(元)</td>
                            <td class="right">${report.interest_information.overdue_amount_1}</td>
                        </tr>
                        <tr>
                            <td class="right">超额金(元)</td>
                            <td class="right">${report.interest_information.overdue_pay_1}</td>
                        </tr>
                        <tr>
                            <td class="right">其他费用(元)</td>
                            <td class="right">${report.interest_information.other_fee_1}</td>
                        </tr>
                        <tr>
                            <td class="right">收入</td>
                            <td class="right">${report.interest_information.income_amt_1}</td>
                        </tr>
                        </tbody>
                    </table>
                </datagrid>
            </div>
        </tile>
    </flex>

    <flex>
        <tile class="col-6  mar10">
            <h2>交易行为</h2>
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
                            <td class="right">当期总透支余额(元)</td>
                            <td class="right">${report.credit_card_summary.total_new_balance_1}</td>
                        </tr>
                        <tr>
                            <td class="right">上期应还总额(元)</td>
                            <td class="right">${report.credit_card_summary.total_last_balance_1}</td>
                        </tr>
                        <tr>
                            <td class="right">当期最低还款总额(元)</td>
                            <td class="right">${report.credit_card_summary.total_min_payment_1}</td>
                        </tr>
                        <tr>
                            <td class="right">当期新发生消费总金额(元)</td>
                            <td class="right">${report.credit_card_summary.total_consume_amount_1}</td>
                        </tr>
                        <tr>
                            <td class="right">当期新发生消费总笔数</td>
                            <td class="right">${report.credit_card_summary.total_consume_num_1}</td>
                        </tr>
                        <tr>
                            <td class="right">上期未还总额(元)</td>
                            <td class="right">${report.credit_card_summary.last_unrepay_amount}</td>
                        </tr>
                        <tr>
                            <td class="right">当期新发生消费平均金额(元)</td>
                            <td class="right">${report.credit_card_summary.cur_consume_avg_amount}</td>
                        </tr>
                        <tr>
                            <td class="right">当期新发生提现总金额(元)</td>
                            <td class="right">${report.credit_card_summary.cur_cash_amount}</td>
                        </tr>
                        <tr>
                            <td class="right">当期新发生提现总笔数</td>
                            <td class="right">${report.credit_card_summary.cur_cash_num}</td>
                        </tr>
                        <tr>
                            <td class="right">当期新发生提现平均金额(元)</td>
                            <td class="right">${report.credit_card_summary.cur_cash_avg_amount}</td>
                        </tr>
                        </tbody>
                    </table>
                </datagrid>
            </div>
        </tile>
        <tile class="col-6 mar10">
            <h2>余额</h2>
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
                            <td class="right">近1月零售余额/近3月均零售余额(元)</td>
                            <td class="right">${report.balance.retail_balance_1_per_3}</td>
                        </tr>
                        <tr>
                            <td class="right">近1月零售余额/近6月均零售余额(元)</td>
                            <td class="right">${report.balance.retail_balance_1_per_6}</td>
                        </tr>
                        <tr>
                            <td class="right">近1月零售余额/近12月均零售余额(元)</td>
                            <td class="right">${report.balance.retail_balance_1_per_12}</td>
                        </tr>
                        <tr>
                            <td class="right">近3月均销售占近3月均余额比率</td>
                            <td class="right">${report.balance.avg_amount_per_avg_balance_3}</td>
                        </tr>
                        <tr>
                            <td class="right">近6月均销售占近3月均余额比率</td>
                            <td class="right">${report.balance.avg_amount_per_avg_balance_6}</td>
                        </tr>
                        <tr>
                            <td class="right">近12月均销售占近3月均余额比率</td>
                            <td class="right">${report.balance.avg_amount_per_avg_balance_12}</td>
                        </tr>
                        <tr>
                            <td class="right">近3月最高余额距今的月数</td>
                            <td class="right">${report.balance.max_balance_month_num_3}</td>
                        </tr>
                        <tr>
                            <td class="right">近6月最高余额距今的月数</td>
                            <td class="right">${report.balance.max_balance_month_num_6}</td>
                        </tr>
                        <tr>
                            <td class="right">近12月最高余额距今的月数</td>
                            <td class="right">${report.balance.max_balance_month_num_12}</td>
                        </tr>
                        </tbody>
                    </table>
                </datagrid>
            </div>
        </tile>
    </flex>

    <flex>
        <tile class="col-6  mar10">
            <h2>利息</h2>
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
                            <td class="right">近3月有利息的月份数</td>
                            <td class="right">${report.interest.interest_mons_3}</td>
                        </tr>
                        <tr>
                            <td class="right">近6月有利息的月份数</td>
                            <td class="right">${report.interest.interest_mons_6}</td>
                        </tr>
                        <tr>
                            <td class="right">近12月有利息的月份数</td>
                            <td class="right">${report.interest.interest_mons_12}</td>
                        </tr>
                        <tr>
                            <td class="right">近3月有利息月数占比</td>
                            <td class="right">${report.interest.interest_mon_ratio_3}</td>
                        </tr>
                        <tr>
                            <td class="right">近6月有利息月数占比</td>
                            <td class="right">${report.interest.interest_mon_ratio_6}</td>
                        </tr>
                        <tr>
                            <td class="right">近12月有利息月数占比</td>
                            <td class="right">${report.interest.interest_mon_ratio_12}</td>
                        </tr>
                        </tbody>
                    </table>
                </datagrid>
            </div>
        </tile>
        <tile class="col-6 mar10">
            <h2>超额</h2>
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
                            <td class="right">近3月超额的月份数</td>
                            <td class="right">${report.overrun.beyond_quota_month_num_3m}</td>
                        </tr>
                        <tr>
                            <td class="right">近6月超额的月份数</td>
                            <td class="right">${report.overrun.beyond_quota_month_num_6m}</td>
                        </tr>
                        <tr>
                            <td class="right">近12月超额的月份数</td>
                            <td class="right">${report.overrun.beyond_quota_month_num_12m}</td>
                        </tr>
                        <tr>
                            <td class="right">近3月最高超额费(元)</td>
                            <td class="right">${report.overrun.beyond_quota_max_amount_3m}</td>
                        </tr>
                        <tr>
                            <td class="right">近6月最高超额费(元)</td>
                            <td class="right">${report.overrun.beyond_quota_max_amount_6m}</td>
                        </tr>
                        <tr>
                            <td class="right">近12月最高超额费(元)</td>
                            <td class="right">${report.overrun.beyond_quota_max_amount_12m}</td>
                        </tr>
                        </tbody>
                    </table>
                </datagrid>
            </div>
        </tile>
    </flex>

    <flex>
        <tile class="col-6  mar10">
            <h2>还款</h2>
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
                            <td class="right">近3月平均还款金额(元)</td>
                            <td class="right">${report.repayment.avg_pay_amount_3}</td>
                        </tr>
                        <tr>
                            <td class="right">近6月平均还款金额(元)</td>
                            <td class="right">${report.repayment.avg_pay_amount_6}</td>
                        </tr>
                        <tr>
                            <td class="right">近12月平均还款金额(元)</td>
                            <td class="right">${report.repayment.avg_pay_amount_12}</td>
                        </tr>
                        <tr>
                            <td class="right">近3月平均还款率</td>
                            <td class="right">${report.repayment.repay_ratio_avg_3}</td>
                        </tr>
                        <tr>
                            <td class="right">近6月平均还款率</td>
                            <td class="right">${report.repayment.repay_ratio_avg_6}</td>
                        </tr>
                        <tr>
                            <td class="right">近12月平均还款率</td>
                            <td class="right">${report.repayment.repay_ratio_avg_12}</td>
                        </tr>
                        <tr>
                            <td class="right">近3月最近一次产生还款金额距今的月数</td>
                            <td class="right">${report.repayment.last_repay_now_num_3}</td>
                        </tr>
                        <tr>
                            <td class="right">近6月最近一次产生还款金额距今的月数</td>
                            <td class="right">${report.repayment.last_repay_now_num_6}</td>
                        </tr>
                        <tr>
                            <td class="right">近12月最近一次产生还款金额距今的月数</td>
                            <td class="right">${report.repayment.last_repay_now_num_12}</td>
                        </tr>
                        <tr>
                            <td class="right">近3月有MINPAY且不足全额的月数</td>
                            <td class="right">${report.repayment.minpay_mons_3}</td>
                        </tr>
                        <tr>
                            <td class="right">近6月有MINPAY且不足全额的月数</td>
                            <td class="right">${report.repayment.minpay_mons_6}</td>
                        </tr>
                        <tr>
                            <td class="right">近12月有MINPAY且不足全额的月数</td>
                            <td class="right">${report.repayment.minpay_mons_12}</td>
                        </tr>
                        </tbody>
                    </table>
                </datagrid>
            </div>
        </tile>
        <tile class="col-6 mar10">
            <h2>收入</h2>
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
                            <td class="right">近3月平均收入(元)</td>
                            <td class="right">${report.income.credit_incom_avg_3}</td>
                        </tr>
                        <tr>
                            <td class="right">近6月平均收入(元)</td>
                            <td class="right">${report.income.credit_incom_avg_6}</td>
                        </tr>
                        <tr>
                            <td class="right">近12月平均收入(元)</td>
                            <td class="right">${report.income.credit_incom_avg_12}</td>
                        </tr>
                        <tr>
                            <td class="right">近3个月平均收入与近6个月平均比</td>
                            <td class="right">${report.income.income_avg_3_div_6}</td>
                        </tr>
                        <tr>
                            <td class="right">近6个月平均收入与近12个月平均比</td>
                            <td class="right">${report.income.income_avg_6_div_12}</td>
                        </tr>
                        <tr>
                            <td class="right">近3月最低收入距今月份数</td>
                            <td class="right">${report.income.min_income_now_mons_3}</td>
                        </tr>
                        <tr>
                            <td class="right">近6月最低收入距今月份数</td>
                            <td class="right">${report.income.min_income_now_mons_6}</td>
                        </tr>
                        <tr>
                            <td class="right">近12月最低收入距今月份数</td>
                            <td class="right">${report.income.min_income_now_mons_12}</td>
                        </tr>
                        </tbody>
                    </table>
                </datagrid>
            </div>
        </tile>
    </flex>

    <flex>
        <tile class="col-6  mar10">
            <h2>额度</h2>
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
                            <td class="right">近3月平均余额占额度比例</td>
                            <td class="right">${report.quota.average_balance_per_quota_nearly_3m}</td>
                        </tr>
                        <tr>
                            <td class="right">近6月平均余额占额度比例</td>
                            <td class="right">${report.quota.average_balance_per_quota_nearly_6m}</td>
                        </tr>
                        <tr>
                            <td class="right">近12月平均余额占额度比例</td>
                            <td class="right">${report.quota.average_balance_per_quota_nearly_12m}</td>
                        </tr>
                        <tr>
                            <td class="right">近3月销售金额占额度比</td>
                            <td class="right">${report.quota.propertion_of_sales_amount_quota_3}</td>
                        </tr>
                        <tr>
                            <td class="right">近6月销售金额占额度比</td>
                            <td class="right">${report.quota.propertion_of_sales_amount_quota_6}</td>
                        </tr>
                        <tr>
                            <td class="right">近12月销售金额占额度比</td>
                            <td class="right">${report.quota.propertion_of_sales_amount_quota_12}</td>
                        </tr>
                        <tr>
                            <td class="right">近3月中最大销售金额占额度比</td>
                            <td class="right">${report.quota.propertion_of_max_sales_amount_in_quota_3}</td>
                        </tr>
                        <tr>
                            <td class="right">近6月中最大销售金额占额度比</td>
                            <td class="right">${report.quota.propertion_of_max_sales_amount_in_quota_6}</td>
                        </tr>
                        <tr>
                            <td class="right">近12月中最大销售金额占额度比</td>
                            <td class="right">${report.quota.propertion_of_max_sales_amount_in_quota_12}</td>
                        </tr>
                        <tr>
                            <td class="right">近3月中最小销售金额占额度比</td>
                            <td class="right">${report.quota.propertion_of_min_sales_amount_in_quota_3}</td>
                        </tr>
                        <tr>
                            <td class="right">近6月中最小销售金额占额度比</td>
                            <td class="right">${report.quota.propertion_of_min_sales_amount_in_quota_6}</td>
                        </tr>
                        <tr>
                            <td class="right">近12月中最小销售金额占额度比</td>
                            <td class="right">${report.quota.propertion_of_min_sales_amount_in_quota_12}</td>
                        </tr>
                        <tr>
                            <td class="right">近3月最大额度使用率</td>
                            <td class="right">${report.quota.useage_of_max_quota_3}</td>
                        </tr>
                        <tr>
                            <td class="right">近6月最大额度使用率</td>
                            <td class="right">${report.quota.useage_of_max_quota_6}</td>
                        </tr>
                        <tr>
                            <td class="right">近12月最大额度使用率</td>
                            <td class="right">${report.quota.useage_of_max_quota_12}</td>
                        </tr>
                        <tr>
                            <td class="right">近3月最近一次额度使用率>0的值</td>
                            <td class="right">${report.quota.last_useage_more_than_0_nearly_3}</td>
                        </tr>
                        <tr>
                            <td class="right">近6月最近一次额度使用率>0的值</td>
                            <td class="right">${report.quota.last_useage_more_than_0_nearly_6}</td>
                        </tr>
                        <tr>
                            <td class="right">近12月最近一次额度使用率>0的值</td>
                            <td class="right">${report.quota.last_useage_more_than_0_nearly_12}</td>
                        </tr>
                        <tr>
                            <td class="right">近3月平均（消费+提现）金额/信用额度(元)</td>
                            <td class="right">${report.quota.avg_propertion_of_consume_withdrawal_in_quota_3}</td>
                        </tr>
                        <tr>
                            <td class="right">近6月平均（消费+提现）金额/信用额度(元)</td>
                            <td class="right">${report.quota.avg_propertion_of_consume_withdrawal_in_quota_6}</td>
                        </tr>
                        <tr>
                            <td class="right">近12月平均（消费+提现）金额/信用额度(元)</td>
                            <td class="right">${report.quota.avg_propertion_of_consume_withdrawal_in_quota_12}</td>
                        </tr>
                        <tr>
                            <td class="right">近3月中销售金额占额度比大于0.5的月数</td>
                            <td class="right">${report.quota.propertion_of_sales_amnt_in_quota_more_0_5_num_3}</td>
                        </tr>
                        <tr>
                            <td class="right">近6月中销售金额占额度比大于0.5的月数</td>
                            <td class="right">${report.quota.propertion_of_sales_amnt_in_quota_more_0_5_num_6}</td>
                        </tr>
                        <tr>
                            <td class="right">近12月中销售金额占额度比大于0.5的月数</td>
                            <td class="right">${report.quota.propertion_of_sales_amnt_in_quota_more_0_5_num_12}</td>
                        </tr>
                        <tr>
                            <td class="right">近3月中销售金额占额度比大于0.9的月数</td>
                            <td class="right">${report.quota.propertion_of_sales_amnt_in_quota_more_0_9_num_3}</td>
                        </tr>
                        <tr>
                            <td class="right">近6月中销售金额占额度比大于0.9的月数</td>
                            <td class="right">${report.quota.propertion_of_sales_amnt_in_quota_more_0_9_num_6}</td>
                        </tr>
                        <tr>
                            <td class="right">近12月中销售金额占额度比大于0.9的月数</td>
                            <td class="right">${report.quota.propertion_of_sales_amnt_in_quota_more_0_9_num_12}</td>
                        </tr>
                        <tr>
                            <td class="right">最近一期额度使用率与近3月最大比</td>
                            <td class="right">${report.quota.max_propertion_of_last_useage_3}</td>
                        </tr>
                        <tr>
                            <td class="right">最近一期额度使用率与近6月最大比</td>
                            <td class="right">${report.quota.max_propertion_of_last_useage_6}</td>
                        </tr>
                        <tr>
                            <td class="right">最近一期额度使用率与近12月最大比</td>
                            <td class="right">${report.quota.max_propertion_of_last_useage_12}</td>
                        </tr>
                        <tr>
                            <td class="right">近3月平均额度(元)</td>
                            <td class="right">${report.quota.avg_quota_3}</td>
                        </tr>
                        <tr>
                            <td class="right">近6月平均额度(元)</td>
                            <td class="right">${report.quota.avg_quota_6}</td>
                        </tr>
                        <tr>
                            <td class="right">近12月平均额度(元)</td>
                            <td class="right">${report.quota.avg_quota_12}</td>
                        </tr>
                        <tr>
                            <td class="right">近3月最高额度(元)</td>
                            <td class="right">${report.quota.max_quota_3}</td>
                        </tr>
                        <tr>
                            <td class="right">近6月最高额度(元)</td>
                            <td class="right">${report.quota.max_quota_6}</td>
                        </tr>
                        <tr>
                            <td class="right">近12月最高额度(元)</td>
                            <td class="right">${report.quota.max_quota_12}</td>
                        </tr>
                        <tr>
                            <td class="right">近3月最低额度(元)</td>
                            <td class="right">${report.quota.min_quota_3}</td>
                        </tr>
                        <tr>
                            <td class="right">近6月最低额度(元)</td>
                            <td class="right">${report.quota.min_quota_6}</td>
                        </tr>
                        <tr>
                            <td class="right">近12月最低额度(元)</td>
                            <td class="right">${report.quota.min_quota_12}</td>
                        </tr>
                        <tr>
                            <td class="right">近3月最小额度使用率</td>
                            <td class="right">${report.quota.useage_of_min_quota_3}</td>
                        </tr>
                        <tr>
                            <td class="right">近6月最小额度使用率</td>
                            <td class="right">${report.quota.useage_of_min_quota_6}</td>
                        </tr>
                        <tr>
                            <td class="right">近12月最小额度使用率</td>
                            <td class="right">${report.quota.useage_of_min_quota_12}</td>
                        </tr>
                        <tr>
                            <td class="right">近3月平均额度使用率</td>
                            <td class="right">${report.quota.average_useage_rate_of_quota_nearly_3m}</td>
                        </tr>
                        <tr>
                            <td class="right">近6月平均额度使用率</td>
                            <td class="right">${report.quota.average_useage_rate_of_quota_nearly_6m}</td>
                        </tr>
                        <tr>
                            <td class="right">近12月平均额度使用率</td>
                            <td class="right">${report.quota.average_useage_rate_of_quota_nearly_12m}</td>
                        </tr>
                        </tbody>
                    </table>
                </datagrid>
            </div>
        </tile>
        <tile class="col-6 mar10">
            <h2>逾期</h2>
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
                            <td class="right">近3月非延滞期数</td>
                            <td class="right">${report.overdue_creditcard.non_delayed_periods_num_3}</td>
                        </tr>
                        <tr>
                            <td class="right">近6月非延滞期数</td>
                            <td class="right">${report.overdue_creditcard.non_delayed_periods_num_6}</td>
                        </tr>
                        <tr>
                            <td class="right">近12月非延滞期数</td>
                            <td class="right">${report.overdue_creditcard.non_delayed_periods_num_12}</td>
                        </tr>
                        <tr>
                            <td class="right">近3月延滞期数</td>
                            <td class="right">${report.overdue_creditcard.delayed_periods_num_3}</td>
                        </tr>
                        <tr>
                            <td class="right">近6月延滞期数</td>
                            <td class="right">${report.overdue_creditcard.delayed_periods_num_6}</td>
                        </tr>
                        <tr>
                            <td class="right">近12月延滞期数</td>
                            <td class="right">${report.overdue_creditcard.delayed_periods_num_12}</td>
                        </tr>
                        <tr>
                            <td class="right">近3月延滞金银行机构数</td>
                            <td class="right">${report.overdue_creditcard.delayed_bank_num_3}</td>
                        </tr>
                        <tr>
                            <td class="right">近6月延滞金银行机构数</td>
                            <td class="right">${report.overdue_creditcard.delayed_bank_num_6}</td>
                        </tr>
                        <tr>
                            <td class="right">近12月延滞金银行机构数</td>
                            <td class="right">${report.overdue_creditcard.delayed_bank_num_12}</td>
                        </tr>
                        <tr>
                            <td class="right">近3月延滞金卡片数</td>
                            <td class="right">${report.overdue_creditcard.delayed_card_num_3}</td>
                        </tr>
                        <tr>
                            <td class="right">近6月延滞金卡片数</td>
                            <td class="right">${report.overdue_creditcard.delayed_card_num_6}</td>
                        </tr>
                        <tr>
                            <td class="right">近12月延滞金卡片数</td>
                            <td class="right">${report.overdue_creditcard.delayed_card_num_12}</td>
                        </tr>
                        <tr>
                            <td class="right">近3月首次延滞金额(元)</td>
                            <td class="right">${report.overdue_creditcard.delayed_amnt_first_3}</td>
                        </tr>
                        <tr>
                            <td class="right">近6月首次延滞金额(元)</td>
                            <td class="right">${report.overdue_creditcard.delayed_amnt_first_6}</td>
                        </tr>
                        <tr>
                            <td class="right">近12月首次延滞金额(元)</td>
                            <td class="right">${report.overdue_creditcard.delayed_amnt_first_12}</td>
                        </tr>
                        <tr>
                            <td class="right">近3月最高（超额金+延滞金）金额(元)</td>
                            <td class="right">${report.overdue_creditcard.max_amnt_of_beyond_delayed_3}</td>
                        </tr>
                        <tr>
                            <td class="right">近6月最高（超额金+延滞金）金额(元)</td>
                            <td class="right">${report.overdue_creditcard.max_amnt_of_beyond_delayed_6}</td>
                        </tr>
                        <tr>
                            <td class="right">近12月最高（超额金+延滞金）金额(元)</td>
                            <td class="right">${report.overdue_creditcard.max_amnt_of_beyond_delayed_12}</td>
                        </tr>
                        <tr>
                            <td class="right">近3月最高延滞金额(元)</td>
                            <td class="right">${report.overdue_creditcard.max_beyond_amnt_3}</td>
                        </tr>
                        <tr>
                            <td class="right">近6月最高延滞金额(元)</td>
                            <td class="right">${report.overdue_creditcard.max_beyond_amnt_6}</td>
                        </tr>
                        <tr>
                            <td class="right">近12月最高延滞金额(元)</td>
                            <td class="right">${report.overdue_creditcard.max_beyond_amnt_12}</td>
                        </tr>
                        <tr>
                            <td class="right">近3月最高延滞状态</td>
                            <td class="right">${report.overdue_creditcard.highest_delayed_3}</td>
                        </tr>
                        <tr>
                            <td class="right">近6月最高延滞状态</td>
                            <td class="right">${report.overdue_creditcard.highest_delayed_6}</td>
                        </tr>
                        <tr>
                            <td class="right">近12月最高延滞状态</td>
                            <td class="right">${report.overdue_creditcard.highest_delayed_12}</td>
                        </tr>
                        <tr>
                            <td class="right">近3月最近产生延滞金额距今的月份数</td>
                            <td class="right">${report.overdue_creditcard.last_delayed_mon_num_3}</td>
                        </tr>
                        <tr>
                            <td class="right">近6月最近产生延滞金额距今的月份数</td>
                            <td class="right">${report.overdue_creditcard.last_delayed_mon_num_6}</td>
                        </tr>
                        <tr>
                            <td class="right">近12月最近产生延滞金额距今的月份数</td>
                            <td class="right">${report.overdue_creditcard.last_delayed_mon_num_12}</td>
                        </tr>
                        <tr>
                            <td class="right">近3月逾期期数为1的月份数</td>
                            <td class="right">${report.overdue_creditcard.case_delayed_period_equals_one_mon_num_3}</td>
                        </tr>
                        <tr>
                            <td class="right">近6月逾期期数为1的月份数</td>
                            <td class="right">${report.overdue_creditcard.case_delayed_period_equals_one_mon_num_6}</td>
                        </tr>
                        <tr>
                            <td class="right">近12月逾期期数为1的月份数</td>
                            <td class="right">${report.overdue_creditcard.case_delayed_period_equals_one_mon_num_12}</td>
                        </tr>
                        <tr>
                            <td class="right">近3个月内最长连续逾期期数</td>
                            <td class="right">${report.overdue_creditcard.max_delay_month_3}</td>
                        </tr>
                        <tr>
                            <td class="right">近6个月内最长连续逾期期数</td>
                            <td class="right">${report.overdue_creditcard.max_delay_month_6}</td>
                        </tr>
                        <tr>
                            <td class="right">近12个月内最长连续逾期期数</td>
                            <td class="right">${report.overdue_creditcard.max_delay_month_12}</td>
                        </tr>
                        </tbody>
                    </table>
                </datagrid>
            </div>
            <h2>固定属性</h2>
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
                            <td class="right">近3月有账单的月份数</td>
                            <td class="right">${report.other_attribute.have_bill_month_num_nearly_3}</td>
                        </tr>
                        <tr>
                            <td class="right">近6月有账单的月份数</td>
                            <td class="right">${report.other_attribute.have_bill_month_num_nearly_6}</td>
                        </tr>
                        <tr>
                            <td class="right">近12月有账单的月份数</td>
                            <td class="right">${report.other_attribute.have_bill_month_num_nearly_12}</td>
                        </tr>
                        <tr>
                            <td class="right">近3月连续账单的最大月份数</td>
                            <td class="right">${report.other_attribute.longest_month_of_continuous_bill_nearly_3}</td>
                        </tr>
                        <tr>
                            <td class="right">近6月连续账单的最大月份数</td>
                            <td class="right">${report.other_attribute.longest_month_of_continuous_bill_nearly_6}</td>
                        </tr>
                        <tr>
                            <td class="right">近12月连续账单的最大月份数</td>
                            <td class="right">${report.other_attribute.longest_month_of_continuous_bill_nearly_12}</td>
                        </tr>
                        <tr>
                            <td class="right">近3月无账单月数</td>
                            <td class="right">${report.other_attribute.none_bill_month_num_nearly_3}</td>
                        </tr>
                        <tr>
                            <td class="right">近6月无账单月数</td>
                            <td class="right">${report.other_attribute.none_bill_month_num_nearly_6}</td>
                        </tr>
                        <tr>
                            <td class="right">近12月无账单月数</td>
                            <td class="right">${report.other_attribute.none_bill_month_num_nearly_12}</td>
                        </tr>
                        <tr>
                            <td class="right">近3月无账单月数/可统计的月数</td>
                            <td class="right">${report.other_attribute.none_bill_month_num_per_all_3}</td>
                        </tr>
                        <tr>
                            <td class="right">近6月无账单月数/可统计的月数</td>
                            <td class="right">${report.other_attribute.none_bill_month_num_per_all_6}</td>
                        </tr>
                        <tr>
                            <td class="right">近12月无账单月数/可统计的月数</td>
                            <td class="right">${report.other_attribute.none_bill_month_num_per_all_12}</td>
                        </tr>
                        <tr>
                            <td class="right">近3月单卡最长连续账单月数</td>
                            <td class="right">${report.other_attribute.longest_num_of_continuous_bill_nearly_3}</td>
                        </tr>
                        <tr>
                            <td class="right">近6月单卡最长连续账单月数</td>
                            <td class="right">${report.other_attribute.longest_num_of_continuous_bill_nearly_6}</td>
                        </tr>
                        <tr>
                            <td class="right">近12月单卡最长连续账单月数</td>
                            <td class="right">${report.other_attribute.longest_num_of_continuous_bill_nearly_12}</td>
                        </tr>
                        <tr>
                            <td class="right">近3月单卡最大账单断开月数</td>
                            <td class="right">${report.other_attribute.single_card_disconnect_month_num_3}</td>
                        </tr>
                        <tr>
                            <td class="right">近6月单卡最大账单断开月数</td>
                            <td class="right">${report.other_attribute.single_card_disconnect_month_num_6}</td>
                        </tr>
                        <tr>
                            <td class="right">近12月单卡最大账单断开月数</td>
                            <td class="right">${report.other_attribute.single_card_disconnect_month_num_12}</td>
                        </tr>
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
