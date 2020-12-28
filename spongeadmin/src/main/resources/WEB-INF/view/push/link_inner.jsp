<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ct" uri="/tags" %>
<!DOCTYPE HTML>
<html class="frm10">
<head>
    <title>${app} - 链接配置</title>
    <link rel="shortcut icon" type="image/x-icon" href="/favicon.ico"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8 "/>
    <link rel="stylesheet" href="${css}/main.css"/>
    <script src="/_session/domain.js"></script>
    <script src="${js}/lib.js"></script>
    <script src="${js}/layer.js"></script>
    <script>

    </script>
    <style>
    </style>
</head>
<body>
<c:if test="${isOperator==1}">
    <toolbar>
        <cell>
            <button type="button" onclick="location.href='/push/link/add/${agroup_id}'" class="edit">新建</button>
        </cell>
    </toolbar>
</c:if>

<datagrid>
    <table>
        <thead>
        <tr>
            <td width="40">ID</td>
            <td>链接名称</td>
            <td>链接地址</td>
            <td width="60">操作人</td>
            <c:if test="${isOperator==1}">
                <td width="40">操作</td>
            </c:if>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="m" items="${links}">
            <tr>
                <td>${m.link_id}</td>
                <td class="left">${m.name}</td>
                <td class="left">${m.link}</td>
                <td>${m.operator}</td>
                <c:if test="${isOperator==1}">
                    <td class="op">
                        <a href="/push/link/edit/${m.link_id}" class="t2">编辑</a>
                    </td>
                </c:if>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</datagrid>
</body>
</html>