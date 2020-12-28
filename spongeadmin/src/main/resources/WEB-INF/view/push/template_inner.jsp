<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ct" uri="/tags" %>
<!DOCTYPE HTML>
<html class="frm10">
<head>
    <title>${app} - 短信模板</title>
    <link rel="shortcut icon" type="image/x-icon" href="/favicon.ico"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8 "/>
    <link rel="stylesheet" href="${css}/main.css"/>
    <script src="/_session/domain.js"></script>
    <script src="${js}/lib.js"></script>
    <script>

    </script>
    <style>
    </style>
</head>
<body>
<c:if test="${isOperator==1}">
    <toolbar>
        <cell>
            <button type="button" onclick="location.href='/push/template/add/${agroup_id}'" class="edit">新建</button>
        </cell>
    </toolbar>
</c:if>

<datagrid>
    <table>
        <thead>
        <tr>
            <td width="70">ID</td>
            <td>模板名称</td>
            <td>模板内容</td>
            <td width="90">更新时间</td>
            <td width="60">操作人</td>
            <c:if test="${isOperator==1}">
                <td width="40">操作</td>
            </c:if>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="t" items="${tmps}">
            <tr>
                <td>${t.template_id}</td>
                <td class="left">${t.name}</td>
                <td class="left break">${t.content}</td>
                <td><fmt:formatDate value="${t.update_fulltime}" pattern="dd HH:mm:ss"/></td>
                <td>${t.operator}</td>
                <c:if test="${isOperator==1}">
                    <td class="op"><a href="/push/template/edit/${t.template_id}" class="t2">编辑</a></td>
                </c:if>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</datagrid>
<%--<ct:pagebar pageSize="${page_size}" rowCount="${row_count}"/>--%>
</body>
</html>