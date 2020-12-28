<%@ page import="sun.security.util.Length" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib prefix="ct" uri="/tags" %>
<!DOCTYPE HTML>
<html class='frm style2'>
<head>
    <title>${app} - 邮箱账单</title>
    <link rel="shortcut icon" type="image/x-icon" href="/favicon.ico"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8 "/>
    <link rel="stylesheet" href="//static.kdz6.cn/lib/durian/main.css"/>
    <script src="/_session/domain.js"></script>
    <script src="${js}/lib.js"></script>
    <style>
        a.popup{position:relative;text-decoration: none;color: blue}
        a.popup span{display:none;position:absolute;top:-40px;left:150px;z-index: 99;border: 1px solid;border-radius: 5px; padding:4px;width:200px;}
        a.popup:hover span{display:block;}
    </style>
</head>
<body>
<ct:header/>
<main class="sml">
    <ct:toolmenu pack="panda_user_d"></ct:toolmenu>
    <detail>
        <section>
            <header>
                <h2>
                    <c:forEach var="m" items="${emails}">
                        <a style="<c:if test="${email!=m.email}">color:#666666;</c:if> cursor: pointer;text-decoration: underline" onclick="reload('${m.email}');" id="hidden">${m.hide_email}</a>&nbsp;&nbsp;
                    </c:forEach>
                </h2>
            </header>
        </section>

        <c:forEach items="${report}" var="m" varStatus="cursor">
            <hr/>
            <section>
                <header>
                    <h2>基本信息${cursor.index+1}</h2>
                </header>
                <article>
                    <table>
                        <tr>
                            <td>持卡人:</td>
                            <c:if test="${identification.id_name==m.user_basic_information.name}">
                                <td style="color: #00CC99">${m.name_hide}(本人)</td>
                            </c:if>
                            <c:if test="${identification.id_name!=m.user_basic_information.name}">
                                <td style="color: red">${m.name_hide}(非本人)</td>
                            </c:if>
                        </tr>
                        <tr>
                            <td>客户族群标识:</td>
                            <td>
                                <a href="#" class="popup" >${m.user_basic_information.customer_group_tag}
                                    <span style="background-color:#eaedf1;color:black;width: 330px;height:200px;">
                                            若近3月无账单，则为“非活跃用户”；<br/>
                                            若仅有近3月有账单则为“新用户”；<br/>
                                            若近6月有消费或提现且没有利息与延滞记录，则为“老客户刷卡族”；<br/>
                                            若近6月延滞记录 >=2条，则为“老客户迟缴族”；<br/>
                                            若近6月利息记录 >= 2条且延滞记录 < 2条，则为“老客户循环族”；<br/>
                                            若近6月利息记录 < 2条且延滞记录 < 2条且无消费和提现记录，则为“老客户静止族”；<br/>
                                            其他情况为“老客户_其他”。
                                    </span>
                                </a>
                            </td>
                        </tr>
                        <tr>
                            <td>客户套现标识:</td>
                            <td>
                                <a href="#" class="popup" >${m.user_basic_information.cash_out_tag}
                                    <span style="background-color:#eaedf1;color:black;width: 340px;height:80px;">
                                            若某月的额度使用率>80且还款率>=90，则该月套现。<br/>
                                            若近12月套现月数<3，则套现标志为0；<br/>
                                            若3<=近12月套现月数<6，则套现标志为1；<br/>
                                            若近12月套现月数>=6，则套现标志为2。<br/>
                                    </span>
                                </a>
                            </td>
                        </tr>
                        <tr>
                            <td>最新一期账单日期:</td>
                            <td>${m.user_basic_information.latest_authentication_time}</td>
                        </tr>
                        <tr>
                            <td>最后更新时间:</td>
                            <td>${emailInfo.update_fulltime}</td>
                        </tr>
                        <tr>
                            <td style="color: red">认证来源:</td>
                            <td style="color: red">${from}</td>
                        </tr>
                    </table>
                </article>
            </section>

            <section>
                <header>
                    <h2>邮箱报告</h2>
                </header>
                <article>
                    <datagrid>
                        <table>
                            <thead>
                            <tr>
                                <td class="right" width="100px">总信用额</td>
                                <td class="right" width="100px">消费信用额</td>
                                <td class="right" width="130px">总可用信用额</td>
                                <td class="right" width="200px">单一银行最高/最低授信额度</td>
                                <td class="right">逾期账单数量</td>
                                <td class="right">未还金额</td>
                                <td class="right">总利息</td>
                                <td class="right">滞纳金</td>
                                <td class="right">分期手续费</td>
                                <td class="right" width="150px">近3/6/12月非延滞期数</td>
                                <td width="90px">操作</td>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td class="right">${m.credit_limit_informatin.total_credit_limit}</td>
                                <td class="right">${m.credit_limit_informatin.total_consume_credit_limit}</td>
                                <td class="right">${m.credit_limit_informatin.total_aviable_credit_limit}</td>
                                <td class="right">${m.credit_limit_informatin.max_total_credit_limit}/${m.credit_limit_informatin.min_total_credit_limit}</td>
                                <td class="right">${m.overdue_information.total_overdue_bill_count}</td>
                                <td class="right">${m.overdue_information.total_overdue_amount}</td>
                                <td class="right">${m.interest_fee_information.total_interest_amount}</td>
                                <td class="right">${m.interest_fee_information.total_overdue_fine_amount}</td>
                                <td class="right">${m.interest_fee_information.total_installment_fee_amount}</td>
                                <td class="right">${m.overdue_analyze_information.months_not_overdue_l3m}/${m.overdue_analyze_information.months_not_overdue_l6m}/${m.overdue_analyze_information.months_not_overdue_l12m}</td>
                                <td class="center"><a style="color: blue;cursor: pointer" href="/auth/query_email_report?open_id=${open_id}&name=${m.base64_name}" target="_blank">查看详细报告</a></td>
                            </tr>
                            </tbody>
                        </table>
                    </datagrid>
                </article>
            </section>

            <section>
                <header>
                    <h2>账单信息</h2>
                </header>
                <article>
                    <datagrid>
                        <table>
                            <thead>
                            <tr>
                                <td>银行卡</td>
                                <td class="right">银行卡额度</td>
                                <td width="100px">账单日</td>
                                <td width="100px">还款日</td>
                                <td class="right">本期账单金额</td>
                                <td class="right">最低还款额</td>
                                <td class="right">上期账单金额</td>
                                <td class="right">上期还款金额</td>
                                <td width="100px">操作</td>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="b" items="${m.bankList}">
                                <tr>
                                    <td class="center">${b.bankName}(尾号${b.card_number})</td>
                                    <td class="right">${b.credit_limit}</td>
                                    <td class="center">${fn:substring(b.bill_date, 0, 10)}</td>
                                    <td class="center">${fn:substring(b.payment_due_date, 0, 10)}</td>
                                    <td class="right">${b.new_balance}</td>
                                    <td class="right">${b.min_payment}</td>
                                    <td class="right">${b.last_balance}</td>
                                    <td class="right">${b.last_payment}</td>
                                    <td class="center"><a style="color: blue;cursor: pointer" href="/auth/query_email_bill?open_id=${open_id}&bank_id=${b.bank_id}" target="_blank">查看详细账单</a></td>
                                </tr>
                            </c:forEach>

                            </tbody>
                        </table>
                    </datagrid>
                </article>
            </section>

            <section>
                <header>
                    <h2>消费信息</h2>
                </header>
                <article>
                    <datagrid>
                        <table>
                            <thead>
                            <tr>
                                <td width="130px">交易时间</td>
                                <td class="right" width="200px">交易地点</td>
                                <td width="150px">银行卡</td>
                                <td width="80px">交易类型</td>
                                <td class="right" width="150px">交易金额</td>
                                <td class="right">交易摘要</td>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="b" items="${m.shopping_sheets}">
                                <tr>
                                    <td class="center">${fn:substring(b.trans_date, 0, 10)}</td>
                                    <td class="center">${b.trans_addr}</td>
                                    <td class="center">${b.bank_id}(尾号${b.card_no})</td>
                                    <td class="center">${b.trans_type}</td>
                                    <td class="right">${b.trans_org_amount}</td>
                                    <td class="left break">${b.description}</td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </datagrid>
                </article>
            </section>

        </c:forEach>
    </detail>
</main>
</body>
<script>
    function reload(email) {
        window.location.href = changeURLArg(window.location.href,'email',email)
    };

    function changeURLArg(url,arg,arg_val){
        var pattern=arg+'=([^&]*)';
        var replaceText=arg+'='+arg_val;
        if(url.match(pattern)){
            var tmp='/('+ arg+'=)([^&]*)/gi';
            tmp=url.replace(eval(tmp),replaceText);
            return tmp;
        }else{
            if(url.match('[\?]')){
                return url+'&'+replaceText;
            }else{
                return url+'?'+replaceText;
            }
        }
    };
</script>
</html>
