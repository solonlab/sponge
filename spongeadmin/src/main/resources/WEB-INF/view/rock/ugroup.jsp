<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ct" uri="/tags" %>
<!DOCTYPE HTML>
<html class="frm10">
<head>
    <title>${app} - 用户组</title>
    <link rel="shortcut icon" type="image/x-icon" href="/favicon.ico"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8 "/>
    <link rel="stylesheet" href="${css}/main.css"/>
    <script src="/_session/domain.js"></script>
    <script src="${js}/lib.js"></script>
    <script>
        function addUgroup() {
            location.href="/rock/ugroup/add";
        }
    </script>
    <style>
    </style>
</head>
<body>



<toolbar>
    <left>
        <form>
            <input type="text" class="w350" value="${name}" name="name" id="name" placeholder="用户组名称"/>
            <button type="submit">查询</button>&nbsp;&nbsp;
            <c:if test="${isOperator==1}">
                <button type="button" onclick="addUgroup()" class="edit">新增</button>
            </c:if>
        </form>
    </left>
    <right>
        <ct:stateselector items="启用,未启用"></ct:stateselector>
    </right>
</toolbar>

<datagrid>
    <table>
        <thead>
        <tr>
            <td width="100">用户组ID</td>
            <td>用户组名称</td>
            <c:if test="${isOperator==1}">
                <td width="50px">操作</td>
            </c:if>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="ugroup" items="${ugroupList}">
            <tr>
                <td style="text-align: left">${ugroup.ugroup_id}</td>
                <td style="text-align: left">${ugroup.name}</td>
                <c:if test="${isOperator==1}">
                    <td><a href="/rock/ugroup/edit?ugroup_id=${ugroup.ugroup_id}" style="color: blue;">编辑</a></td>
                </c:if>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</datagrid>


</body>
</html>