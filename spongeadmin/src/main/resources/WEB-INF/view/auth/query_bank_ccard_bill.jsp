<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ct" uri="/tags" %>
<!DOCTYPE HTML>
<html class='frm style2'>
<head>
    <title>${app} - 网银信用卡账单记录</title>
    <link rel="shortcut icon" type="image/x-icon" href="/favicon.ico"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8 "/>
    <link rel="stylesheet" href="//static.kdz6.cn/lib/durian/main.css"/>
    <script src="/_session/domain.js"></script>
</head>
<body>
<ct:header/>
<main class="sml">
    <detail>
        <section>
            <header>
                <h2>银行卡账单<a style="color: #00CC99;font-weight: lighter;font-size: small">&nbsp;&nbsp;&nbsp;${bank_name}(尾号${card_num})</a></h2>
            </header>
            <article>
                <datagrid>
                    <table>
                        <thead>
                        <tr>
                            <td width="100px">账单周期开始时间</td>
                            <td width="80px">持卡人</td>
                            <td class="right" width="150px">信用卡额度</td>
                            <td width="90px">账单日</td>
                            <td class="center" width="90px">还款日</td>
                            <td class="right" width="100px">本期账单金额</td>
                            <td class="right" width="100px">最低还款额</td>
                            <td class="right" width="100px">上期账单金额</td>
                            <td class="right" width="100px">上期还款金额</td>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="b" items="${list.bills}">
                            <tr>
                                <td class="center">${total.getBillStartDate(b.payment_due_date)}</td>
                                <td class="center">${total.hideName(list.name_on_card)}</td>
                                <td class="right">${b.credit_limit}</td>
                                <td class="center">${b.bill_date}</td>
                                <td class="center">${b.payment_due_date}</td>
                                <td class="right">${b.new_balance}</td>
                                <td class="right">${b.min_payment}</td>
                                <td class="right">${b.last_balance}</td>
                                <td class="right">${b.last_payment}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </datagrid>
            </article>
        </section>

        <section>
            <header>
                <h2>分期账单</h2>
            </header>
            <article>
                <datagrid>
                    <table>
                        <thead>
                        <tr>
                            <td width="70px">交易时间</td>
                            <td class="left" width="120px">分期描述</td>
                            <td class="left" width="120px">分期手续费描述</td>
                            <td class="left" width="120px">分期转账手续费描述</td>
                            <td class="center" width="50px">分期类型</td>
                            <td width="60px">总分期数</td>
                            <td width="60px">当前分期数</td>
                            <td class="right" width="60px">本期金额</td>
                            <td class="right" width="60px">本期手续费</td>
                            <td class="right" width="60px">转账手续费</td>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="b" items="${list.installments}">
                            <tr>
                                <td class="center">${fn:substring(b.trans_date, 0, 11)}</td>
                                <td class="left break">${b.installment_desc}</td>
                                <td class="left break">${b.handingfee_desc}</td>
                                <td class="left break">${b.transferfee_desc}</td>
                                <td class="center">${total.getInstallmentType(b.installment_type)}</td>
                                <td class="center">${b.total_month}</td>
                                <td class="center">${b.current_month}</td>
                                <td class="right">${b.amount_money}</td>
                                <td class="right">${b.handing_fee}</td>
                                <td class="right">${b.transfer_fee}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </datagrid>
            </article>
        </section>

        <section>
            <header>
                <h2>消费记录</h2>
            </header>
            <article>
                <datagrid>
                    <table>
                        <thead>
                        <tr>
                            <td width="100px">交易时间</td>
                            <td width="80px">交易地点</td>
                            <td width="150px">交易类型</td>
                            <td class="right" width="70px">交易金额</td>
                            <td class="left" width="90px">交易摘要</td>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="b" items="${list.shopping_sheets}">
                            <tr>
                                <td class="center">${fn:substring(b.trans_date, 0, 11)}</td>
                                <td class="center">${b.trans_addr}</td>
                                <td class="center">${b.trans_method}</td>
                                <td class="right">${b.amount_money}</td>
                                <td class="left break">${b.description}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </datagrid>
            </article>
        </section>
    </detail>
</main>
</body>
</html>