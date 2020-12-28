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
    <title>${app} - 信用卡账单详情-分期</title>
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
            <td width="150px" class="left">分期描述</td>
            <td width="150px" class="left">分期手续费描述</td>
            <td width="150px" class="left">分期转账<br/>手续费描述</td>
            <td width="100px">分期类型</td>
            <td width="60px">总分期数</td>
            <td width="100px">当前分期数</td>
            <td class="right"  width="100px">本期金额</td>
            <td class="right"  width="100px">本期手续费</td>
            <td class="right"  width="100px">转账手续费</td>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="b" items="${list}">
            <tr>
                <td class="center">${fn:substring(b.trans_date, 0, 10)}</td>
                <td class="left break">${b.installment_desc}</td>
                <td class="left break">${b.handingfee_desc}</td>
                <td class="left break">${b.transferfee_desc}</td>
                <td class="center">
                    <c:if test="${b.installment_type==0}">消费</c:if>
                    <c:if test="${b.installment_type==1}">现金</c:if>
                    <c:if test="${b.installment_type==2}">账单</c:if>
                </td>
                <td class="center">${b.total_month}</td>
                <td class="right">${b.current_month}</td>
                <td class="right">${b.amount_money}</td>
                <td class="right">${b.handing_fee}</td>
                <td class="right">${b.transfer_fee}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</datagrid>
<ct:pagebar pageSize="${pageSize}" rowCount="${rowCount}"/>
</body>
</html>