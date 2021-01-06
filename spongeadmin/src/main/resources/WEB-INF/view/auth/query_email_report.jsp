<%@ page import="sun.security.util.Length" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib prefix="ct" uri="/tags" %>
<!DOCTYPE HTML>
<html class='frm style2'>
<head>
    <title>${app} - 信用卡邮箱报告</title>
    <link rel="shortcut icon" type="image/x-icon" href="/favicon.ico"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8 "/>
    <link rel="stylesheet" href="//static.kdz6.cn/lib/durian/main.css"/>
    <script src="/_session/domain.js"></script>
    <script src="${js}/lib.js"></script>
</head>
<body>
<ct:header/>
<br/>
<h2>&nbsp;&nbsp;信用卡邮箱报告</h2>
<hr/>
<div class="bg13">
    <flex>
        <tile class="col-6 mar10">
            <h2>用户基本信息</h2>
            <div>
                <datagrid>
                    <table>
                        <thead>
                        <tr>
                            <td class="center" width="100px">字段描述</td>
                            <td class="center" >值</td>
                            <td class="center" width="450px">备注</td>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td class="center">姓名</td>
                            <td class="center">${report.name_hide}</td>
                            <td class="center"></td>
                        </tr>
                        <tr>
                            <td class="center">邮箱</td>
                            <td class="center">${report.user_basic_information.email}</td>
                            <td class="center"></td>
                        </tr>
                        <tr>
                            <td class="center">活跃卡数</td>
                            <td class="center">${report.user_basic_information.active_cards}</td>
                            <td class="center"></td>
                        </tr>
                        <tr>
                            <td class="center">银行数</td>
                            <td class="center">${report.user_basic_information.bank_nums}</td>
                            <td class="center"></td>
                        </tr>
                        <tr>
                            <td class="center">最初账单日</td>
                            <td class="center">${fn:substring(report.user_basic_information.first_bill_date, 0, 10)}</td>
                            <td class="center"></td>
                        </tr>
                        <tr>
                            <td class="center">最早一期账单<br/>距今月份数</td>
                            <td class="center">${report.user_basic_information.first_bill_months_from_now}</td>
                            <td class="center"></td>
                        </tr>
                        <tr>
                            <td class="center">客户族群标志</td>
                            <td class="center">${report.user_basic_information.customer_group_tag}</td>
                            <td class="left">
                                若近3月无账单，则为“非活跃用户”；<br/>
                                若仅有近3月有账单则为“新用户”；<br/>
                                若近6月有消费或提现且没有利息与延滞记录，则为“老客户刷卡族”；<br/>
                                若近6月延滞记录 >=2条，则为“老客户迟缴族”；<br/>
                                若近6月利息记录 >= 2条且延滞记录 < 2条，则为“老客户循环族”；<br/>
                                若近6月利息记录 < 2条且延滞记录 < 2条且无消费和提现记录，则为“老客户静止族”；<br/>
                                其他情况为“老客户_其他”。</td>
                        </tr>
                        </tbody>
                    </table>
                </datagrid>
            </div>
        </tile>

        <tile class="col-6  mar10">
            <h2>授信情况</h2>
            <div>
                <datagrid>
                    <table>
                        <thead>
                        <tr>
                            <td class="center" width="260px">字段描述</td>
                            <td class="right" >值</td>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td class="center">总信用额</td>
                            <td class="right">${report.credit_limit_informatin.total_credit_limit}</td>
                        </tr>
                        <tr>
                            <td class="center">消费信用额</td>
                            <td class="right">${report.credit_limit_informatin.total_consume_credit_limit}</td>
                        </tr>
                        <tr>
                            <td class="center">提现信用额</td>
                            <td class="right">${report.credit_limit_informatin.total_cash_limit}</td>
                        </tr>
                        <tr>
                            <td class="center">可用提现信用额</td>
                            <td class="right">${report.credit_limit_informatin.aviable_cash_limit}</td>
                        </tr>
                        <tr>
                            <td class="center">可用消费信用额</td>
                            <td class="right">${report.credit_limit_informatin.aviable_consume_credit_limit}</td>
                        </tr>
                        <tr>
                            <td class="center">总可用信用额</td>
                            <td class="right">${report.credit_limit_informatin.total_aviable_credit_limit}</td>
                        </tr>
                        <tr>
                            <td class="center">单一银行最高授信额度</td>
                            <td class="right">${report.credit_limit_informatin.max_total_credit_limit}</td>
                        </tr>
                        <tr>
                            <td class="center">单一银行最低授信额度</td>
                            <td class="right">${report.credit_limit_informatin.min_total_credit_limit}</td>
                        </tr>
                        </tbody>
                    </table>
                </datagrid>
            </div>
        </tile>
    </flex>

    <flex>
        <tile class="col-6 mar10">
            <h2>交易行为</h2>
            <div>
                <datagrid>
                    <table>
                        <thead>
                        <tr>
                            <td class="center" width="300px">字段描述</td>
                            <td class="right" >值</td>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td class="center">当期新发生消费总金额</td>
                            <td class="right">${report.transaction_information.total_current_consume}</td>
                        </tr>
                        <tr>
                            <td class="center">当期总透支余额</td>
                            <td class="right">${report.transaction_information.total_current_new_balance}</td>
                        </tr>
                        <tr>
                            <td class="center">上期应还总额</td>
                            <td class="right">${report.transaction_information.total_last_new_balance}</td>
                        </tr>
                        <tr>
                            <td class="center">当期最低还款总额</td>
                            <td class="right">${report.transaction_information.current_total_min_payment}</td>
                        </tr>
                        <tr>
                            <td class="center">上期未还总额</td>
                            <td class="right">${report.transaction_information.last_no_payment_total}</td>
                        </tr>
                        <tr>
                            <td class="center">当期新发生消费总笔数</td>
                            <td class="right">${report.transaction_information.current_total_consume_count}</td>
                        </tr>
                        <tr>
                            <td class="center">当期新发生消费平均金额</td>
                            <td class="right">${report.transaction_information.current_consume_average_amount}</td>
                        </tr>
                        <tr>
                            <td class="center">当期新发生提现总金额</td>
                            <td class="right">${report.transaction_information.current_withdraw_amount}</td>
                        </tr>
                        <tr>
                            <td class="center">当期新发生提现总笔数</td>
                            <td class="right">${report.transaction_information.current_withdraw_count}</td>
                        </tr>
                        <tr>
                            <td class="center">当期新发生提现平均金额</td>
                            <td class="right">${report.transaction_information.current_withdraw_average_amount}</td>
                        </tr>
                        </tbody>
                    </table>
                </datagrid>
            </div>
        </tile>

        <tile class="col-6  mar10">
            <h2>超额</h2>
            <div>
                <datagrid>
                    <table>
                        <thead>
                        <tr>
                            <td class="center" width="260px">字段描述</td>
                            <td class="right" >值</td>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td class="center">近3月超额的月数</td>
                            <td class="right">${report.excess_information.excess_months_l3m}</td>
                        </tr>
                        <tr>
                            <td class="center">近6月超额的月数</td>
                            <td class="right">${report.excess_information.excess_months_l6m}</td>
                        </tr>
                        <tr>
                            <td class="center">近12月超额的月数</td>
                            <td class="right">${report.excess_information.excess_months_l12m}</td>
                        </tr>
                        <tr>
                            <td class="center">近3月最高超额费</td>
                            <td class="right">${report.excess_information.max_excess_fee_l3m}</td>
                        </tr>
                        <tr>
                            <td class="center">近6月最高超额费</td>
                            <td class="right">${report.excess_information.max_excess_fee_l6m}</td>
                        </tr>
                        <tr>
                            <td class="center">近12月最高超额费</td>
                            <td class="right">${report.excess_information.max_excess_fee_l12m}</td>
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
                            <td class="center" width="300px">字段描述</td>
                            <td class="right" >值</td>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td class="center">逾期账单数量</td>
                            <td class="right">${report.overdue_information.total_overdue_bill_count}</td>
                        </tr>
                        <tr>
                            <td class="center">未还金额</td>
                            <td class="right">${report.overdue_information.total_overdue_amount}</td>
                        </tr>
                        <tr>
                            <td class="center">未还金额占比</td>
                            <td class="right">${report.overdue_information.total_overdue_amount_div_new_balance}</td>
                        </tr>
                        <tr>
                            <td class="center">逾期标志</td>
                            <td class="right">${report.overdue_information.overdue_flag}</td>
                        </tr>
                        <tr>
                            <td class="center">逾期状态</td>
                            <td class="right">${report.overdue_information.overdue_status}</td>
                        </tr>
                        </tbody>
                    </table>
                </datagrid>
            </div>
        </tile>

        <tile class="col-6  mar10">
            <h2>利费信息</h2>
            <div>
                <datagrid>
                    <table>
                        <thead>
                        <tr>
                            <td class="center" width="260px">字段描述</td>
                            <td class="right" >值</td>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td class="center">总利息</td>
                            <td class="right">${report.interest_fee_information.total_interest_amount}</td>
                        </tr>
                        <tr>
                            <td class="center">滞纳金</td>
                            <td class="right">${report.interest_fee_information.total_overdue_fine_amount}</td>
                        </tr>
                        <tr>
                            <td class="center">超额金</td>
                            <td class="right">${report.interest_fee_information.total_excess_amount}</td>
                        </tr>
                        <tr>
                            <td class="center">其他费用</td>
                            <td class="right">${report.interest_fee_information.total_extra_amount}</td>
                        </tr>
                        <tr>
                            <td class="center">收入</td>
                            <td class="right">${report.interest_fee_information.total_revenue_amount}</td>
                        </tr>
                        <tr>
                            <td class="center">分期手续费</td>
                            <td class="right">${report.interest_fee_information.total_installment_fee_amount}</td>
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
                            <td class="center" width="300px">字段描述</td>
                            <td class="right" >值</td>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td class="center">近3月可用额度</td>
                            <td class="right">${report.credit_limit_analyze_informatin.total_credit_limit_l3m}</td>
                        </tr>
                        <tr>
                            <td class="center">近3月均余额占额度比例</td>
                            <td class="right">${report.credit_limit_analyze_informatin.new_balance_div_credit_limit_l3m}</td>
                        </tr>
                        <tr>
                            <td class="center">近3月销售金额占额度比</td>
                            <td class="right">${report.credit_limit_analyze_informatin.new_charge_div_credit_limit_l3m}</td>
                        </tr>
                        <tr>
                            <td class="center">近3月中最大销售金额占额度比</td>
                            <td class="right">${report.credit_limit_analyze_informatin.max_sell_div_credit_limit_l3m}</td>
                        </tr>
                        <tr>
                            <td class="center">近3月中最小销售金额占额度比</td>
                            <td class="right">${report.credit_limit_analyze_informatin.min_sell_div_credit_limit_l3m}</td>
                        </tr>
                        <tr>
                            <td class="center">近3月平均额度</td>
                            <td class="right">${report.credit_limit_analyze_informatin.average_credit_limit_l3m}</td>
                        </tr>

                        <tr>
                            <td class="center">近3月最大额度使用率</td>
                            <td class="right">${report.credit_limit_analyze_informatin.max_new_balance_div_credit_limit_l3m}</td>
                        </tr>
                        <tr>
                            <td class="center">近3月最高额度</td>
                            <td class="right">${report.credit_limit_analyze_informatin.max_credit_limit_l3m}</td>
                        </tr>
                        <tr>
                            <td class="center">近3月最低额度</td>
                            <td class="right">${report.credit_limit_analyze_informatin.min_credit_limit_l3m}</td>
                        </tr>
                        <tr>
                            <td class="center">近6月均余额占额度比例</td>
                            <td class="right">${report.credit_limit_analyze_informatin.average_new_balance_div_credit_limit_l6m}</td>
                        </tr>
                        <tr>
                            <td class="center">近12月均余额占额度比例</td>
                            <td class="right">${report.credit_limit_analyze_informatin.average_new_balance_div_credit_limit_l12m}</td>
                        </tr>
                        <tr>
                            <td class="center">近6月可用额度</td>
                            <td class="right">${report.credit_limit_analyze_informatin.total_credit_limit_l6m}</td>
                        </tr>
                        <tr>
                            <td class="center">近12月可用额度</td>
                            <td class="right">${report.credit_limit_analyze_informatin.total_credit_limit_l12m}</td>
                        </tr>
                        <tr>
                            <td class="center">近6月平均额度</td>
                            <td class="right">${report.credit_limit_analyze_informatin.average_credit_limit_l6m}</td>
                        </tr>
                        <tr>
                            <td class="center">近12月平均额度</td>
                            <td class="right">${report.credit_limit_analyze_informatin.average_credit_limit_l12m}</td>
                        </tr>
                        <tr>
                            <td class="center">近6月销售金额占额度比</td>
                            <td class="right">${report.credit_limit_analyze_informatin.sell_div_credit_limit_l6m}</td>
                        </tr>
                        <tr>
                            <td class="center">近12月销售金额占额度比</td>
                            <td class="right">${report.credit_limit_analyze_informatin.sell_div_credit_limit_l12m}</td>
                        </tr>
                        <tr>
                            <td class="center">近6月最高额度</td>
                            <td class="right">${report.credit_limit_analyze_informatin.max_credit_limit_l6m}</td>
                        </tr>
                        <tr>
                            <td class="center">近12月最高额度</td>
                            <td class="right">${report.credit_limit_analyze_informatin.max_credit_limit_l12m}</td>
                        </tr>
                        <tr>
                            <td class="center">近6月最低额度</td>
                            <td class="right">${report.credit_limit_analyze_informatin.min_credit_limit_l6m}</td>
                        </tr>
                        <tr>
                            <td class="center">近12月最低额度</td>
                            <td class="right">${report.credit_limit_analyze_informatin.min_credit_limit_l12m}</td>
                        </tr>
                        <tr>
                            <td class="center">近6月中最大销售金额占额度比</td>
                            <td class="right">${report.credit_limit_analyze_informatin.max_sell_div_credit_limit_l6m}</td>
                        </tr>
                        <tr>
                            <td class="center">近12月中最大销售金额占额度比</td>
                            <td class="right">${report.credit_limit_analyze_informatin.max_sell_div_credit_limit_l12m}</td>
                        </tr>
                        <tr>
                            <td class="center">近6月中最小销售金额占额度比</td>
                            <td class="right">${report.credit_limit_analyze_informatin.min_sell_div_credit_limit_l6m}</td>
                        </tr>
                        <tr>
                            <td class="center">近12月中最小销售金额占额度比</td>
                            <td class="right">${report.credit_limit_analyze_informatin.min_sell_div_credit_limit_12m}</td>
                        </tr>
                        <tr>
                            <td class="center">近6月最大额度使用率</td>
                            <td class="right">${report.credit_limit_analyze_informatin.max_new_balance_div_credit_limit_l6m}</td>
                        </tr>
                        <tr>
                            <td class="center">近12月最大额度使用率</td>
                            <td class="right">${report.credit_limit_analyze_informatin.max_new_balance_div_credit_limit_l12m}</td>
                        </tr>
                        <tr>
                            <td class="center">近3月中销售金额占额度比大于0.5的月数</td>
                            <td class="right">${report.credit_limit_analyze_informatin.months_sell_div_credit_limit_above_half_l3m}</td>
                        </tr>
                        <tr>
                            <td class="center">最近一月额度使用率与近6月最大比</td>
                            <td class="right">${report.credit_limit_analyze_informatin.max_ratio_latest_month_usage_ratio_div_l6m}</td>
                        </tr>
                        <tr>
                            <td class="center">最近一月额度使用率与近3期最大比</td>
                            <td class="right">${report.credit_limit_analyze_informatin.max_ratio_latest_month_usage_ratio_div_l3m}</td>
                        </tr>
                        <tr>
                            <td class="center">近3月平均销售金额／信用额度</td>
                            <td class="right">${report.credit_limit_analyze_informatin.average_sell_div_credit_limit_l3m}</td>
                        </tr>
                        <tr>
                            <td class="center">最近一月额度使用率与近12期最大比</td>
                            <td class="right">${report.credit_limit_analyze_informatin.max_ratio_latest_month_usage_ratio_div_l12m}</td>
                        </tr>
                        <tr>
                            <td class="center">近3月最小额度使用率</td>
                            <td class="right">${report.credit_limit_analyze_informatin.min_credit_limit_usage_ratio_l3m}</td>
                        </tr>
                        <tr>
                            <td class="center">近6月最小额度使用率</td>
                            <td class="right">${report.credit_limit_analyze_informatin.min_credit_limit_usage_ratio_l6m}</td>
                        </tr>
                        <tr>
                            <td class="center">近12月最小额度使用率</td>
                            <td class="right">${report.credit_limit_analyze_informatin.min_credit_limit_usage_ratio_l12m}</td>
                        </tr>
                        <tr>
                            <td class="center">近6月中销售金额占额度比大于0.5的月数</td>
                            <td class="right">${report.credit_limit_analyze_informatin.months_new_sell_div_credit_limit_above_half_l6m}</td>
                        </tr>
                        <tr>
                            <td class="center">近12月中销售金额占额度比大于0.5的月数</td>
                            <td class="right">${report.credit_limit_analyze_informatin.months_new_sell_div_credit_limit_above_half_l12m}</td>
                        </tr>
                        <tr>
                            <td class="center">近6月均额度使用率</td>
                            <td class="right">${report.credit_limit_analyze_informatin.average_credit_limit_usage_ratio_l6m}</td>
                        </tr>
                        <tr>
                            <td class="center">近3月中销售金额占额度比大于0.9的月数</td>
                            <td class="right">${report.credit_limit_analyze_informatin.monhts_new_balance_div_credit_limit_above_90pct_l3m}</td>
                        </tr>
                        <tr>
                            <td class="center">近12月均额度使用率</td>
                            <td class="right">${report.credit_limit_analyze_informatin.average_credit_limit_usage_ratio_l12m}</td>
                        </tr>
                        <tr>
                            <td class="center">近3月均额度使用率</td>
                            <td class="right">${report.credit_limit_analyze_informatin.average_credit_limit_usage_ratio_l3m}</td>
                        </tr>
                        <tr>
                            <td class="center">近3月最近一次额度使用率>0的值</td>
                            <td class="right">${report.credit_limit_analyze_informatin.latest_ratio_above_zero_l3m}</td>
                        </tr>
                        <tr>
                            <td class="center">近6月中销售金额占额度比大于0.9的月数</td>
                            <td class="right">${report.credit_limit_analyze_informatin.months_new_sell_div_credit_limit_above_90pct_l6m}</td>
                        </tr>
                        <tr>
                            <td class="center">近12月中销售金额占额度比大于0.9的月数</td>
                            <td class="right">${report.credit_limit_analyze_informatin.months_new_sell_div_credit_limit_above_90pct_l12m}</td>
                        </tr>
                        <tr>
                            <td class="center">近6月均销售金额/信用额度</td>
                            <td class="right">${report.credit_limit_analyze_informatin.average_sell_div_credit_limit_l6m}</td>
                        </tr>
                        <tr>
                            <td class="center">近12月均销售金额/信用额度</td>
                            <td class="right">${report.credit_limit_analyze_informatin.average_sell_div_credit_limit_l12m}</td>
                        </tr>
                        <tr>
                            <td class="center">近6月最近一次额度使用率>0的值</td>
                            <td class="right">${report.credit_limit_analyze_informatin.latest_ratio_above_zero_l6m}</td>
                        </tr>
                        <tr>
                            <td class="center">近12月最近一次额度使用率>0的值</td>
                            <td class="right">${report.credit_limit_analyze_informatin.latest_ratio_above_zero_l12m}</td>
                        </tr>

                        </tbody>
                    </table>
                </datagrid>
            </div>
        </tile>
        <tile class="col-6  mar10">
            <h2>销售金额</h2>
            <div>
                <datagrid>
                    <table>
                        <thead>
                        <tr>
                            <td class="center" width="260px">字段描述</td>
                            <td class="right" >值</td>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td class="center">近3月销售金额大于0的月份占比</td>
                            <td class="right">${report.new_charge_information.ratio_sell_l3m}</td>
                        </tr>
                        <tr>
                            <td class="center">近6月销售金额大于0的月份占比</td>
                            <td class="right">${report.new_charge_information.ratio_sell_l6m}</td>
                        </tr>
                        <tr>
                            <td class="center">近12月销售金额大于0的月份占比</td>
                            <td class="right">${report.new_charge_information.ratio_sell_l12m}</td>
                        </tr>
                        <tr>
                            <td class="center">近3月月均消费金额</td>
                            <td class="right">${report.new_charge_information.average_consume_l3m}</td>
                        </tr>
                        <tr>
                            <td class="center">近6月月均消费金额</td>
                            <td class="right">${report.new_charge_information.average_consume_l6m}</td>
                        </tr>
                        <tr>
                            <td class="center">近6月月均消费金额</td>
                            <td class="right">${report.new_charge_information.average_consume_l6m}</td>
                        </tr>
                        <tr>
                            <td class="center">近12月月均消费金额</td>
                            <td class="right">${report.new_charge_information.average_consume_l12m}</td>
                        </tr>
                        <tr>
                            <td class="center">近3月最高消费金额</td>
                            <td class="right">${report.new_charge_information.max_consume_l3m}</td>
                        </tr>
                        <tr>
                            <td class="center">近6月最高消费金额</td>
                            <td class="right">${report.new_charge_information.max_consume_l6m}</td>
                        </tr>
                        <tr>
                            <td class="center">近12月最高消费金额</td>
                            <td class="right">${report.new_charge_information.max_consume_l12m}</td>
                        </tr>
                        <tr>
                            <td class="center">近3月最低消费金额</td>
                            <td class="right">${report.new_charge_information.min_consume_l3m}</td>
                        </tr>
                        <tr>
                            <td class="center">近6月最低消费金额</td>
                            <td class="right">${report.new_charge_information.min_consume_l6m}</td>
                        </tr>
                        <tr>
                            <td class="center">近12月最低消费金额</td>
                            <td class="right">${report.new_charge_information.min_consume_l12m}</td>
                        </tr>
                        <tr>
                            <td class="center">近3月月均提现金额</td>
                            <td class="right">${report.new_charge_information.average_withdraw_amount_l3m}</td>
                        </tr>
                        <tr>
                            <td class="center">近6月月均提现金额</td>
                            <td class="right">${report.new_charge_information.average_withdraw_amount_l6m}</td>
                        </tr>
                        <tr>
                            <td class="center">近12月月均提现金额</td>
                            <td class="right">${report.new_charge_information.average_withdraw_amount_l12m}</td>
                        </tr>
                        <tr>
                            <td class="center">近3月最近一次提现距今月数</td>
                            <td class="right">${report.new_charge_information.months_from_last_withdraw_l3m}</td>
                        </tr>
                        <tr>
                            <td class="center">近6月最近一次提现距今月数</td>
                            <td class="right">${report.new_charge_information.months_from_last_withdraw_l6m}</td>
                        </tr>
                        <tr>
                            <td class="center">近12月最近一次提现距今月数</td>
                            <td class="right">${report.new_charge_information.months_from_last_withdraw_l12m}</td>
                        </tr>
                        <tr>
                            <td class="center">近3月首次销售金额</td>
                            <td class="right">${report.new_charge_information.first_sell_amount_l3m}</td>
                        </tr>
                        <tr>
                            <td class="center">近6月首次销售金额</td>
                            <td class="right">${report.new_charge_information.first_sell_amount_l6m}</td>
                        </tr>
                        <tr>
                            <td class="center">近12月首次销售金额</td>
                            <td class="right">${report.new_charge_information.first_sell_amount_l12m}</td>
                        </tr>
                        <tr>
                            <td class="center">近3月首次消费金额</td>
                            <td class="right">${report.new_charge_information.first_consume_amount_l3m}</td>
                        </tr>
                        <tr>
                            <td class="center">近6月首次消费金额</td>
                            <td class="right">${report.new_charge_information.first_consume_amount_l6m}</td>
                        </tr>
                        <tr>
                            <td class="center">近12月首次消费金额</td>
                            <td class="right">${report.new_charge_information.first_consume_amount_l12m}</td>
                        </tr>
                        <tr>
                            <td class="center">近3月单期最大销售笔数</td>
                            <td class="right">${report.new_charge_information.max_sell_count_l3m}</td>
                        </tr>
                        <tr>
                            <td class="center">近6月单期最大销售笔数</td>
                            <td class="right">${report.new_charge_information.max_sell_count_l6m}</td>
                        </tr>
                        <tr>
                            <td class="center">近12月单期最大销售笔数</td>
                            <td class="right">${report.new_charge_information.max_sell_count_l12m}</td>
                        </tr>
                        <tr>
                            <td class="center">近3月有销售的月数</td>
                            <td class="right">${report.new_charge_information.months_have_sell_l3m}</td>
                        </tr>
                        <tr>
                            <td class="center">近6月有销售的月数</td>
                            <td class="right">${report.new_charge_information.months_have_sell_l6m}</td>
                        </tr>

                        <tr>
                            <td class="center">近12月有销售的月数</td>
                            <td class="right">${report.new_charge_information.months_have_sell_l12m}</td>
                        </tr>
                        <tr>
                            <td class="center">近3月月均消费笔数</td>
                            <td class="right">${report.new_charge_information.average_sell_count_l3m}</td>
                        </tr>
                        <tr>
                            <td class="center">近6月月均消费笔数</td>
                            <td class="right">${report.new_charge_information.average_sell_count_l6m}</td>
                        </tr>
                        <tr>
                            <td class="center">近12月月均消费笔数</td>
                            <td class="right">${report.new_charge_information.average_sell_count_l12m}</td>
                        </tr>
                        <tr>
                            <td class="center">近3月提现次数</td>
                            <td class="right">${report.new_charge_information.times_withdraw_deposit_l3m}</td>
                        </tr>
                        <tr>
                            <td class="center">近3月提现金额</td>
                            <td class="right">${report.new_charge_information.withdraw_amount_l3m}</td>
                        </tr>
                        <tr>
                            <td class="center">近3月有提现月数占比</td>
                            <td class="right">${report.new_charge_information.ratio_withdraw_months_div_all_months_l3m}</td>
                        </tr>
                        <tr>
                            <td class="center">近6月有提现月数占比</td>
                            <td class="right">${report.new_charge_information.ratio_withdraw_months_div_all_months_l6m}</td>
                        </tr>
                        <tr>
                            <td class="center">近12月有提现月数占比</td>
                            <td class="right">${report.new_charge_information.ratio_withdraw_months_div_all_months_l12m}</td>
                        </tr>
                        <tr>
                            <td class="center">近6月提现次数</td>
                            <td class="right">${report.new_charge_information.total_withdraw_count_l6m}</td>
                        </tr>
                        <tr>
                            <td class="center">近12月提现次数</td>
                            <td class="right">${report.new_charge_information.total_withdraw_count_l12m}</td>
                        </tr>
                        <tr>
                            <td class="center">近6月提现金额</td>
                            <td class="right">${report.new_charge_information.withdraw_amount_l6m}</td>
                        </tr>
                        <tr>
                            <td class="center">近12月提现金额</td>
                            <td class="right">${report.new_charge_information.withdraw_amount_l12m}</td>
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
                            <td class="center" width="300px">字段描述</td>
                            <td class="right" >值</td>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td class="center">近3月均还款金额</td>
                            <td class="right">${report.payment_analyze_information.average_payment_amount_l3m}</td>
                        </tr>
                        <tr>
                            <td class="center">近3月均还款率</td>
                            <td class="right">${report.payment_analyze_information.average_payment_ratio_l3m}</td>
                        </tr>
                        <tr>
                            <td class="center">近6个月均还款金额</td>
                            <td class="right">${report.payment_analyze_information.average_payment_amount_l6m}</td>
                        </tr>
                        <tr>
                            <td class="center">近6月均还款率</td>
                            <td class="right">${report.payment_analyze_information.average_payment_ratio_l6m}</td>
                        </tr>
                        <tr>
                            <td class="center">近12月均还款率</td>
                            <td class="right">${report.payment_analyze_information.average_payment_ratio_l12m}</td>
                        </tr>
                        <tr>
                            <td class="center">近12个月均还款金额</td>
                            <td class="right">${report.payment_analyze_information.average_payment_amount_l12m}</td>
                        </tr>
                        <tr>
                            <td class="center">近3月有MINPAY且不足全额的月份数</td>
                            <td class="right">${report.payment_analyze_information.months_min_payment_less_new_balance_l3m}</td>
                        </tr>
                        <tr>
                            <td class="center">近3月最近一次产生还款金额距今的月数</td>
                            <td class="right">${report.payment_analyze_information.months_last_payment_from_now_l3m}</td>
                        </tr>
                        <tr>
                            <td class="center">近6月最近一次产生还款金额距今的月数</td>
                            <td class="right">${report.payment_analyze_information.months_last_payment_from_now_l6m}</td>
                        </tr>
                        <tr>
                            <td class="center">近12月最近一次产生还款金额距今的月数</td>
                            <td class="right">${report.payment_analyze_information.months_last_payment_from_now_l12m}</td>
                        </tr>
                        <tr>
                            <td class="center">近6月有MINPAY且不足全额的月份数</td>
                            <td class="right">${report.payment_analyze_information.months_min_payment_less_new_balance_l6m}</td>
                        </tr>
                        <tr>
                            <td class="center">近12月有MINPAY且不足全额的月份数</td>
                            <td class="right">${report.payment_analyze_information.months_min_payment_less_new_balance_l12m}</td>
                        </tr>

                        </tbody>
                    </table>
                </datagrid>
            </div>
        </tile>

        <tile class="col-6  mar10">
            <h2>余额</h2>
            <div>
                <datagrid>
                    <table>
                        <thead>
                        <tr>
                            <td class="center" width="260px">字段描述</td>
                            <td class="right" >值</td>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td class="center">近1月零售余额/近3月均零售余额</td>
                            <td class="right">${report.new_balance_information.new_balance_l1m_div_l3m}</td>
                        </tr>
                        <tr>
                            <td class="center">近1月零售余额/近6月均零售余额</td>
                            <td class="right">${report.new_balance_information.new_balance_l1m_div_l6m}</td>
                        </tr>
                        <tr>
                            <td class="center">近1月零售余额/近12月均零售余额</td>
                            <td class="right">${report.new_balance_information.new_balance_l1m_div_l12m}</td>
                        </tr>
                        <tr>
                            <td class="center">近3月均销售金额占近3月均余额比率</td>
                            <td class="right">${report.new_balance_information.l3m_sell_div_l3m_new_balance}</td>
                        </tr>
                        <tr>
                            <td class="center">近6月均销售金额占近6月均余额比率</td>
                            <td class="right">${report.new_balance_information.l6m_sell_div_l6m_new_balance}</td>
                        </tr>

                        <tr>
                            <td class="center">近12月均销售金额占近12月均余额比率</td>
                            <td class="right">${report.new_balance_information.l12m_sell_div_l12m_new_balance}</td>
                        </tr>
                        <tr>
                            <td class="center">近3期最高余额距今的月数</td>
                            <td class="right">${report.new_balance_information.months_max_balance_from_now_l3m}</td>
                        </tr>
                        <tr>
                            <td class="center">近6期最高余额距今的月数</td>
                            <td class="right">${report.new_balance_information.months_max_balance_from_now_l6m}</td>
                        </tr>
                        <tr>
                            <td class="center">近12期最高余额距今的月数</td>
                            <td class="right">${report.new_balance_information.monts_max_balance_from_now_l12m}</td>
                        </tr>
                        </tbody>
                    </table>
                </datagrid>
            </div>
        </tile>
    </flex>

    <flex>
        <tile class="col-6  mar10">
            <h2>收入</h2>
            <div>
                <datagrid>
                    <table>
                        <thead>
                        <tr>
                            <td class="center" width="300px">字段描述</td>
                            <td class="right" >值</td>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td class="center">近3月平均收入与近6期平均比</td>
                            <td class="right">${report.incoming_information.average_income_l3m_div_average_income_l6m}</td>
                        </tr>
                        <tr>
                            <td class="center">近3月平均收入</td>
                            <td class="right">${report.incoming_information.average_income_l3m}</td>
                        </tr>
                        <tr>
                            <td class="center">近6月平均收入</td>
                            <td class="right">${report.incoming_information.average_income_l6m}</td>
                        </tr>
                        <tr>
                            <td class="center">近12月平均收入</td>
                            <td class="right">${report.incoming_information.average_income_l12m}</td>
                        </tr>
                        <tr>
                            <td class="center">近6月平均收入与近12期平均比</td>
                            <td class="right">${report.incoming_information.ratio_l6m_average_income_div_l12M_average_income}</td>
                        </tr>

                        <tr>
                            <td class="center">近3月最低收入距今月份数</td>
                            <td class="right">${report.incoming_information.months_min_income_from_now_l3m}</td>
                        </tr>
                        <tr>
                            <td class="center">近6月最低收入距今月份数</td>
                            <td class="right">${report.incoming_information.months_min_income_from_now_l6m}</td>
                        </tr>
                        <tr>
                            <td class="center">近12月最低收入距今月份数</td>
                            <td class="right">${report.incoming_information.months_min_income_from_now_l12m}</td>
                        </tr>
                        </tbody>
                    </table>
                </datagrid>
            </div>
        </tile>
        <tile class="col-6  mar10">
            <h2>利息</h2>
            <div>
                <datagrid>
                    <table>
                        <thead>
                        <tr>
                            <td class="center" width="260px">字段描述</td>
                            <td class="right" >值</td>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td class="center">近3月有利息的月份数</td>
                            <td class="right">${report.interest_fee_analyze_information.months_with_interest_l3m}</td>
                        </tr>
                        <tr>
                            <td class="center">近6月有利息的月份数</td>
                            <td class="right">${report.interest_fee_analyze_information.months_with_interest_l6m}</td>
                        </tr>
                        <tr>
                            <td class="center">近12月有利息的月份数</td>
                            <td class="right">${report.interest_fee_analyze_information.months_with_interest_l12m}</td>
                        </tr>
                        <tr>
                            <td class="center">近6月有利息的月数占比</td>
                            <td class="right">${report.interest_fee_analyze_information.interest_months_ratio_l6m}</td>
                        </tr>
                        <tr>
                            <td class="center">近12月有利息的月数占比</td>
                            <td class="right">${report.interest_fee_analyze_information.interest_months_ratio_l12m}</td>
                        </tr>
                        <tr>
                            <td class="center">近3月有利息的月数占比</td>
                            <td class="right">${report.interest_fee_analyze_information.interest_months_ratio_l3m}</td>
                        </tr>
                        </tbody>
                    </table>
                </datagrid>
            </div>
        </tile>
    </flex>

    <flex>
        <tile class="col-6  mar10">
            <h2>逾期</h2>
            <div>
                <datagrid>
                    <table>
                        <thead>
                        <tr>
                            <td class="center" width="300px">字段描述</td>
                            <td class="right" >值</td>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td class="center">近3月非延滞期数</td>
                            <td class="right">${report.overdue_analyze_information.months_not_overdue_l3m}</td>
                        </tr>
                        <tr>
                            <td class="center">近6月非延滞期数</td>
                            <td class="right">${report.overdue_analyze_information.months_not_overdue_l6m}</td>
                        </tr>
                        <tr>
                            <td class="center">近12月非延滞期数</td>
                            <td class="right">${report.overdue_analyze_information.months_not_overdue_l12m}</td>
                        </tr>
                        <tr>
                            <td class="center">近3月首次延滞金额</td>
                            <td class="right">${report.overdue_analyze_information.first_overdue_amount_l3m}</td>
                        </tr>
                        <tr>
                            <td class="center">近6月首次延滞金额</td>
                            <td class="right">${report.overdue_analyze_information.first_overdue_amount_l6m}</td>
                        </tr>
                        <tr>
                            <td class="center">近3月最高（超额金+滞纳金）金额</td>
                            <td class="right">${report.overdue_analyze_information.max_excess_and_overdue_fine_amount}</td>
                        </tr>
                        <tr>
                            <td class="center">近12月首次延滞金额</td>
                            <td class="right">${report.overdue_analyze_information.first_overdue_amount_l12m}</td>
                        </tr>
                        <tr>
                            <td class="center">近6月最高（超额金+滞纳金）金额</td>
                            <td class="right">${report.overdue_analyze_information.max_excess_and_overdue_fine_amount_l6m}</td>
                        </tr>

                        <tr>
                            <td class="center">近12月最高（超额金+滞纳金）金额</td>
                            <td class="right">${report.overdue_analyze_information.max_excess_and_overdue_fine_amount_l12m}</td>
                        </tr>
                        <tr>
                            <td class="center">近3月最高延滞金额</td>
                            <td class="right">${report.overdue_analyze_information.max_overdue_fine_l3m}</td>
                        </tr>
                        <tr>
                            <td class="center">近6月最高延滞金额</td>
                            <td class="right">${report.overdue_analyze_information.max_overdue_fine_l6m}</td>
                        </tr>
                        <tr>
                            <td class="center">近12月最高延滞金额</td>
                            <td class="right">${report.overdue_analyze_information.max_overdue_fine_l12m}</td>
                        </tr><tr>
                            <td class="center">近3月最近产生延滞金额距今的月数</td>
                            <td class="right">${report.overdue_analyze_information.months_last_overdue_months_from_now_l3m}</td>
                        </tr>
                        <tr>
                            <td class="center">近6月最近产生延滞金额距今的月数</td>
                            <td class="right">${report.overdue_analyze_information.months_last_overdue_months_from_now_l6m}</td>
                        </tr>
                        <tr>
                            <td class="center">近12月最近产生延滞金额距今的月数</td>
                            <td class="right">${report.overdue_analyze_information.months_last_overdue_months_from_now_l12m}</td>
                        </tr>
                        <tr>
                            <td class="center">近3期逾期期数为1的月份数</td>
                            <td class="right">${report.overdue_analyze_information.months_overdue_1_months_l3m}</td>
                        </tr>
                        <tr>
                            <td class="center">近6期逾期期数为1的月份数</td>
                            <td class="right">${report.overdue_analyze_information.months_overdue_1_months_l6m}</td>
                        </tr>
                        <tr>
                            <td class="center">近12期逾期期数为1的月份数</td>
                            <td class="right">${report.overdue_analyze_information.months_overdue_1_months_l12m}</td>
                        </tr>
                        <tr>
                            <td class="center">近6月最高延滞状态</td>
                            <td class="right">${report.overdue_analyze_information.max_overdue_status_l6m}</td>
                        </tr>
                        <tr>
                            <td class="center">近12月最高延滞状态</td>
                            <td class="right">${report.overdue_analyze_information.max_overdue_status_l12m}</td>
                        </tr>
                        <tr>
                            <td class="center">近3月最高延滞状态</td>
                            <td class="right">${report.overdue_analyze_information.max_overdue_status_l3m}</td>
                        </tr><tr>
                            <td class="center">近3月延滞期数</td>
                            <td class="right">${report.overdue_analyze_information.months_ovderdue_l3m}</td>
                        </tr>
                        <tr>
                            <td class="center">近6月延滞期数</td>
                            <td class="right">${report.overdue_analyze_information.months_ovderdue_l6m}</td>
                        </tr>
                        <tr>
                            <td class="center">近12月延滞期数</td>
                            <td class="right">${report.overdue_analyze_information.months_ovderdue_l12m}</td>
                        </tr><tr>
                            <td class="center">近6月包含延滞金银行机构数</td>
                            <td class="right">${report.overdue_analyze_information.banks_withdraw_overdue_fine_l6m}</td>
                        </tr><tr>
                            <td class="center">近12月包含延滞金银行机构数</td>
                            <td class="right">${report.overdue_analyze_information.banks_withdraw_overdue_fine_l12m}</td>
                        </tr>
                        <tr>
                            <td class="center">近6月包含延滞金卡片数</td>
                            <td class="right">${report.overdue_analyze_information.card_nums_with_overdue_fine_l6m}</td>
                        </tr>
                        <tr>
                            <td class="center">近12月包含延滞金卡片数</td>
                            <td class="right">${report.overdue_analyze_information.card_nums_with_overdue_fine_l12m}</td>
                        </tr>
                        <tr>
                            <td class="center">近3月包含延滞金银行机构数</td>
                            <td class="right">${report.overdue_analyze_information.banks_withdraw_overdue_fine_l3m}</td>
                        </tr><tr>
                            <td class="center">近3月包含延滞金卡片数</td>
                            <td class="right">${report.overdue_analyze_information.card_nums_with_overdue_fine_l3m}</td>
                        </tr>
                        </tbody>
                    </table>
                </datagrid>
            </div>
        </tile>
        <tile class="col-6  mar10">
            <h2>还款</h2>
            <div>
                <datagrid>
                    <table>
                        <thead>
                        <tr>
                            <td class="center" width="260px">字段描述</td>
                            <td class="right" >值</td>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td class="center">还款金额</td>
                            <td class="right">${report.payment_information.total_payment_amount}</td>
                        </tr>
                        <tr>
                            <td class="center">还款笔数</td>
                            <td class="right">${report.payment_information.total_payments_times}</td>
                        </tr>
                        <tr>
                            <td class="center">还款率</td>
                            <td class="right">${report.payment_information.repay_ratio}</td>
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
                            <td class="center" width="260px">字段描述</td>
                            <td class="right" >值</td>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td class="center">近3月有账单的月数</td>
                            <td class="right">${report.fix_attribute_information.months_have_bill_l3m}</td>
                        </tr>
                        <tr>
                            <td class="center">近6月有账单的月数</td>
                            <td class="right">${report.fix_attribute_information.months_have_bill_l6m}</td>
                        </tr>
                        <tr>
                            <td class="center">近12月有账单的月数</td>
                            <td class="right">${report.fix_attribute_information.months_have_bill_l12m}</td>
                        </tr>
                        <tr>
                            <td class="center">近3月连续账单最长月数</td>
                            <td class="right">${report.fix_attribute_information.max_months_have_succession_bills_l3m}</td>
                        </tr>
                        <tr>
                            <td class="center">近6月连续账单最长月数</td>
                            <td class="right">${report.fix_attribute_information.max_months_have_succession_bills_l6m}</td>
                        </tr>
                        <tr>
                            <td class="center">近12月连续账单最长月数</td>
                            <td class="right">${report.fix_attribute_information.max_months_have_succession_bills_l12m}</td>
                        </tr>

                        <tr>
                            <td class="center">近6月单卡最长连续账单期数</td>
                            <td class="right">${report.fix_attribute_information.max_months_single_card_succession_bills_l6m}</td>
                        </tr>
                        <tr>
                            <td class="center">近6月无账单的月数</td>
                            <td class="right">${report.fix_attribute_information.months_without_bill_l6m}</td>
                        </tr>
                        <tr>
                            <td class="center">近12月无账单的月数</td>
                            <td class="right">${report.fix_attribute_information.months_without_bill_l12m}</td>
                        </tr>
                        <tr>
                            <td class="center">近12月单卡最长连续账单期数</td>
                            <td class="right">${report.fix_attribute_information.max_months_single_card_succession_bill_l12m}</td>
                        </tr>
                        <tr>
                            <td class="center">近6月无账单的月数/可统计的月数</td>
                            <td class="right">${report.fix_attribute_information.months_without_bills_l6m_div_all_months_have_bill}</td>
                        </tr>
                        <tr>
                            <td class="center">近6月单卡最大账单断开期数</td>
                            <td class="right">${report.fix_attribute_information.max_months_single_card_break_l6m}</td>
                        </tr>
                        <tr>
                            <td class="center">近12月无账单的月数/可统计的月数</td>
                            <td class="right">${report.fix_attribute_information.months_without_bills_l12m_div_all_months_have_bill}</td>
                        </tr>
                        <tr>
                            <td class="center">近12月单卡最大账单断开期数</td>
                            <td class="right">${report.fix_attribute_information.max_months_single_card_break_l12m}</td>
                        </tr>
                        <tr>
                            <td class="center">近3月无账单的月数</td>
                            <td class="right">${report.fix_attribute_information.months_without_bill_l3m}</td>
                        </tr>
                        <tr>
                            <td class="center">近3月无账单的月数/可统计的月数</td>
                            <td class="right">${report.fix_attribute_information.months_without_bills_l3m_div_all_months_have_bill}</td>
                        </tr>
                        <tr>
                            <td class="center">近3月单卡最长连续账单期数</td>
                            <td class="right">${report.fix_attribute_information.max_months_single_card_succession_bills_l3m}</td>
                        </tr>
                        <tr>
                            <td class="center">近3月单卡最大账单断开期数</td>
                            <td class="right">${report.fix_attribute_information.max_months_single_card_break_l3m}</td>
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
