<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ct" uri="/tags" %>
<!DOCTYPE HTML>
<html class='frm10'>
<head>
    <title>${app} - 信用卡账单详情-消费</title>
    <link rel="shortcut icon" type="image/x-icon" href="/favicon.ico"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8 "/>
    <link rel="stylesheet" href="//static.kdz6.cn/lib/durian/main.css"/>
    <script src="/_session/domain.js"></script>
    <script src="${js}/lib.js"></script>
</head>
<body>
<datagrid>
    <table>
        <thead>
        <tr>
            <td width="100px">交易时间</td>
            <td width="150px">交易地点</td>
            <td width="80px">交易类型</td>
            <td width="100px" class="right">交易金额</td>
            <td width="150px">交易摘要</td>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="b" items="${list}">
            <tr>
                <td class="center">${fn:substring(b.trans_date, 0, 10)}</td>
                <td class="center break">${b.trans_addr}</td>
                <td class="center">
                    <c:if test="${b.trans_type=='WITHDRAW'}">取现</c:if>
                    <c:if test="${b.trans_type=='PAYMENTS'}">还款</c:if>
                    <c:if test="${b.trans_type=='INTEREST'}">循环利息</c:if>
                    <c:if test="${b.trans_type=='OVERDUEPAYMENT'}">延滞金</c:if>
                    <c:if test="${b.trans_type=='OVERDUEFEE'}">超额费</c:if>
                    <c:if test="${b.trans_type=='INSTALLMENT'}">分期</c:if>
                    <c:if test="${b.trans_type=='SHOPPING'}">消费</c:if>
                    <c:if test="${b.trans_type=='OTHERFEE'}">其他费用</c:if>
                </td>
                <td class="right">${b.amount_money}</td>
                <td class="center break">${b.description}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</datagrid>
<ct:pagebar pageSize="${pageSize}" rowCount="${rowCount}"/>
</body>
</html>