<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ct" uri="/tags" %>
<!DOCTYPE HTML>
<html class="frm10">
<head>
    <title>${app} - 应用组</title>
    <link rel="shortcut icon" type="image/x-icon" href="/favicon.ico"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8 "/>
    <link rel="stylesheet" href="${css}/main.css"/>
    <script src="/_session/domain.js"></script>
    <script src="${js}/lib.js"></script>
    <script>
        function addAgroup() {
            location.href="/rock/agroup/add";
        }
    </script>
    <style>
    </style>
</head>
<body>


<toolbar>
    <left>
        <form>
            <input type="text" class="w350" value="${name}" name="name" id="name" placeholder="应用组名称"/>
            <button type="submit">查询</button>&nbsp;&nbsp;
            <c:if test="${isOperator==1}">
                <button type="button" onclick="addAgroup()" class="edit">新增</button>
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
            <td width="100px">应用组ID</td>
            <td width="100px">技术代号</td>
            <td>应用组名称</td>
            <td width="100px">默认用户组ID</td>

            <c:if test="${isOperator==1}">
                <td width="50px">操作</td>
            </c:if>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="agroup" items="${agroupList}">
            <tr>
                <td style="text-align: left">${agroup.agroup_id}</td>
                <td style="text-align: left">${agroup.tag}</td>
                <td style="text-align: left">${agroup.name}</td>
                <td style="text-align: left">${agroup.ugroup_id}</td>
                <c:if test="${isOperator==1}">
                    <td><a href="agroup/edit?agroup_id=${agroup.agroup_id}" style="color: blue;">编辑</a></td>
                </c:if>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</datagrid>

</body>
</html>