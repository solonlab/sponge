<%@ page import="sun.security.util.Length" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib prefix="ct" uri="/tags" %>
<!DOCTYPE HTML>
<html class='frm style2'>
<head>
    <title>${app} - 邮箱账单详情</title>
    <link rel="shortcut icon" type="image/x-icon" href="/favicon.ico"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8 "/>
    <link rel="stylesheet" href="//static.kdz6.cn/lib/durian/main.css"/>
    <script src="/_session/domain.js"></script>
    <script src="${js}/lib.js"></script>
</head>
<body>
<ct:header/>
<main class="sml">
    <detail>
        <section>
            <header>
                <h2>银行卡账单</h2>
            </header>
            <article>
                <datagrid>
                    <table>
                        <thead>
                        <tr>
                            <td width="130px">账单周期开始时间</td>
                            <td>是否原始账单</td>
                            <td width="100px">持卡人</td>
                            <td width="100px" class="right">信用卡额度</td>
                            <td>账单日</td>
                            <td>还款日</td>
                            <td class="right">本期账单金额</td>
                            <td class="right">最低还款额</td>
                            <td class="right" width="100px">上期账单金额</td>
                            <td class="right" width="100px">上期还款金额</td>
                            <td width="100px">可用积分</td>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="b" items="${list}">
                            <tr>
                                <td class="center">${fn:substring(b.bill_start_date, 0, 10)}</td>
                                <td class="center">${b.original}</td>
                                <td class="center">${b.name_on_card}</td>
                                <td class="right">${b.credit_limit}</td>
                                <td class="center">${fn:substring(b.bill_date, 0, 10)}</td>
                                <td class="center">${fn:substring(b.payment_due_date, 0, 10)}</td>
                                <td class="right">${b.new_balance}</td>
                                <td class="right">${b.min_payment}</td>
                                <td class="right">${b.last_balance}</td>
                                <td class="right">${b.last_payment}</td>
                                <td class="right">${b.point}</td>
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
                <iframe src="/auth/query_email_bill_expense?open_id=${open_id}&email=${email}&bank_id=${bank_id}" frameborder="0" width="100%" height="520px"></iframe>
            </article>
        </section>

        <section>
            <header>
                <h2>分期账单</h2>
            </header>
            <article>
                <iframe src="/auth/query_email_bill_installment?open_id=${open_id}&email=${email}&bank_id=${bank_id}" frameborder="0"  width="100%" height="520px"></iframe>
            </article>
        </section>
    </detail>
</main>
</body>
<script>
</script>
</html>
