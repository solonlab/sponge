<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ct" uri="/tags" %>
<!DOCTYPE HTML>
<html class="frm10">
<head>
    <title>${app} - 应用</title>
    <link rel="shortcut icon" type="image/x-icon" href="/favicon.ico"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8 "/>
    <link rel="stylesheet" href="${css}/main.css"/>
    <script src="/_session/domain.js"></script>
    <script src="${js}/lib.js"></script>
    <script>
        function addApp() {
            location.href="/rock/app/add?agroup_id=${agroup_id}";
        }
    </script>
    <style>
    </style>
</head>
<body>

<toolbar>
    <left>
        <form>
            <input type="hidden" name="agroup_id" value="${agroup_id}">
            <input type="text" class="w250" value="${name}" name="name" id="name" placeholder="应用名称"/>
            <button type="submit">查询</button>
            <c:if test="${agroup_id>0&&isOperator==1}">
                <button type="button" onclick="addApp()" class="edit mar10-l">新增</button>
            </c:if>
        </form>
    </left>
    <right><ct:stateselector items="全部,已过审,审核中"/></right>
</toolbar>

<datagrid>
    <table>
        <thead>
        <tr>
            <td width="70px">应用ID</td>
            <td width="280px">应用标识（appKey）</td>
            <td >应用名称</td>
            <td width="60px">应用组ID</td>
            <td width="60px">用户组ID</td>
            <td width="60px">是否<br/>审核中</td>
            <td width="60px">审核<br/>版本号</td>
            <td width="60px">是否<br/>可设置</td>
            <c:if test="${isOperator==1}">
                <td width="50px">操作</td>
            </c:if>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="app" items="${appList}">
            <tr>
                <td style="text-align: left">${app.app_id}</td>
                <td style="text-align: left">${app.app_key}</td>
                <td style="text-align: left">${app.name}</td>
                <td>${app.agroup_id}</td>
                <td>${app.ugroup_id}</td>
                <td>${app.ar_is_examine}</td>
                <td>${app.ar_examine_ver}</td>
                <td >${app.ar_is_setting}</td>
                <c:if test="${isOperator==1}">
                    <td><a href="/rock/app/edit?app_id=${app.app_id}" style="color: blue;">编辑</a></td>
                </c:if>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</datagrid>

</body>
</html>