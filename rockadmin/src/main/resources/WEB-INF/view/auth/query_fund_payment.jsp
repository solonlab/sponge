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
    <title>${app} - 公积金缴存记录</title>
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
            <td width="100px">缴存时间</td>
            <td width="150px" class="right">公司缴存金额</td>
            <td width="100px" class="right">公司缴存比例</td>
            <td width="60px" class="right">个人缴存金额</td>
            <td width="100px" class="right">个人缴存比例</td>
            <td class="right"  width="100px">总缴存金额</td>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="b" items="${list}">
            <tr>
                <td class="center">${b.deal_time}</td>
                <td class="right"><fmt:formatNumber type="number" value="${b.corporation_income/100}" pattern="0.00" maxFractionDigits="2"/></td>
                <td class="right">${b.corporation_ratio}</td>
                <td class="right"><fmt:formatNumber type="number" value="${b.customer_income/100}" pattern="0.00" maxFractionDigits="2"/></td>
                <td class="right">${b.customer_ratio}</td>
                <td class="right"><fmt:formatNumber type="number" value="${b.income/100}" pattern="0.00" maxFractionDigits="2"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</datagrid>
<ct:pagebar pageSize="${pageSize}" rowCount="${rowCount}"/>
</body>
</html>