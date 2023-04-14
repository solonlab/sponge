<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ct" uri="/tags" %>
<table>
    <thead>
    <tr>
        <td>名称</td>
        <td></td>
        <td style="width: 80px">PV</td>
        <td style="width: 80px">UV</td>
        <td style="width: 80px">IP</td>
        <td style="width: 70px"></td>
        <td style="width: 80px">PV</td>
        <td style="width: 80px">UV</td>
        <td style="width: 80px">IP</td>
    </tr>
    </thead>
    <tbody id="tbody">
    <c:forEach var="url" items="${list}">
        <tr>
            <td>
                <c:if test="${type == 0}">
                    <c:if test="${url.has_track == 1}">
                        <a href="/track/stat/url?url_id=${url.url_id}" style="cursor: pointer;text-decoration:none;color: blue">${url.url_name}</a>
                    </c:if>
                    <c:if test="${url.has_track == 0}">
                        ${url.url_name}
                    </c:if>
                </c:if>
                <c:if test="${type == 1}">
                    ${url.url_name}
                </c:if>
            </td>

            <td style="text-align: center;width: 70px">前30日</td>
            <td>${url.pv_total}</td>
            <td>${url.uv_total}</td>
            <td>${url.ip_total}</td>
            <td style="text-align: center">今日<br/><b>昨日</b></td>
            <td>${url.pv_today}<br/><b>${url.pv_yesterday}</b></td>
            <td>${url.uv_today}<br/><b>${url.uv_yesterday}</b></td>
            <td>${url.ip_today}<br/><b>${url.ip_yesterday}</b></td>
        </tr>
    </c:forEach>
    </tbody>
</table>