<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ct" uri="/tags" %>
<!DOCTYPE HTML>
<html class="frm10">
<head>
    <title>${app} - 应用白名单</title>
    <link rel="shortcut icon" type="image/x-icon" href="/favicon.ico"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8 "/>
    <link rel="stylesheet" href="${css}/main.css"/>
    <script src="/_session/domain.js"></script>
    <script src="${js}/lib.js"></script>
    <script>
        function addWhitelist() {
            location.href="/rock/whitelist/add?tag=${tag}";
        }
    </script>
    <style>
    </style>
</head>
<body>

<toolbar>
    <cell>
        <form>
            <input type="text" value="${value}" name="value" placeholder="请输入ip或host值"/>&nbsp;&nbsp;
            <input type="text" value="${tag}" name="tag" style="display: none"/>
            <button type="submit">查询</button>&nbsp;&nbsp;
            <c:if test="${isOperator==1}">
                <button type="button" onclick="addWhitelist()" class="edit">新增</button>
            </c:if>
        </form>
    </cell>
</toolbar>

<datagrid>
    <table>
        <thead>
        <tr>
            <td width="100px">类型</td>
            <td>值</td>
            <td>备注</td>
            <c:if test="${isOperator==1}">
                <td width="50px">操作</td>
            </c:if>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="m" items="${whitelists}">
            <tr>
                <td>
                    <c:if test="${m.type == 0}">IP</c:if>
                    <c:if test="${m.type == 1}">host</c:if>
                </td>
                <td style="text-align: left">${m.value}</td>
                <td>${m.note}</td>
                <c:if test="${isOperator==1}">
                    <td><a href="/rock/whitelist/edit?row_id=${m.row_id}" style="color: blue;">编辑</a></td>
                </c:if>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</datagrid>

</body>
</html>