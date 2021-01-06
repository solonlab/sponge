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
    <title>${app} - 网银借记卡交易记录</title>
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
                <h2>交易记录<a style="color: #00CC99;font-weight: lighter;font-size: small">&nbsp;&nbsp;&nbsp;${bank_name}(尾号${card_num})</a></h2>
            </header>
            <article>
                <datagrid>
                    <table>
                        <thead>
                        <tr>
                            <td width="100px">交易时间</td>
                            <td class="right" width="80px">交易金额</td>
                            <td width="150px">交易地点</td>
                            <td width="70px">交易方式</td>
                            <td class="center" width="90px">交易通道</td>
                            <td class="left" width="150px">描述</td>
                            <td class="left" width="150px">备注</td>
                            <td class="center" width="140px">对方卡号</td>
                            <td class="left" width="130px">对方持卡人</td>
                            <td width="100px">对方银行</td>
                            <td class="right" width="80px">余额</td>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="b" items="${shops}">
                            <tr>
                                <td class="center">${b.post_date}</td>
                                <td class="right">${b.amount_money}</td>
                                <td class="center">${b.trans_addr}</td>
                                <td class="center">${b.trans_method}</td>
                                <td class="center">${b.trans_channel}</td>
                                <td class="left break">${b.description}</td>
                                <td class="left break">${b.remark}</td>
                                <td class="center">${b.opposite_card_no}</td>
                                <td class="left break">${b.name_on_opposite_card}</td>
                                <td class="center break">${b.opposite_bank}</td>
                                <td class="right">${b.balance}</td>
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