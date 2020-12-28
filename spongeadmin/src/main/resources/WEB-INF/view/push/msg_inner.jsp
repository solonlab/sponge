<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ct" uri="/tags" %>
<!DOCTYPE HTML>
<html class="frm10">
<head>
    <title>${app} - 消息推送</title>
    <link rel="shortcut icon" type="image/x-icon" href="/favicon.ico"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8 "/>
    <link rel="stylesheet" href="${css}/main.css"/>
    <script src="/_session/domain.js"></script>
    <script src="${js}/lib.js"></script>
    <script src="${js}/layer.js"></script>
    <script>
        function cancel(msg_id) {

            $.ajax({
                type: "POST",
                url: "/push/msg/ajax/cancel",
                data: {
                    msg_id: msg_id
                },
                success: function (data) {
                    if (data.code === 1) {
                        top.layer.msg(data.msg);
                        location.reload(true);
                    } else {
                        top.layer.msg(data.msg);
                    }
                }
            });
        }
    </script>
    <style>
    </style>
</head>
<body>
<c:if test="${isOperator==1}">
    <toolbar>
        <cell>
            <button type="button" onclick="location.href='/push/msg/add/${agroup_id}'" class="edit">新建</button>
        </cell>
        <cell>
            <ct:stateselector items="待推送,推送中,已推送,已取消"/>
        </cell>
    </toolbar>
</c:if>

<datagrid>
    <table>
        <thead>
        <tr>
            <td width="40">ID</td>
            <td>消息内容</td>
            <td width="160">推送规则</td>
            <td width="90">推送时间</td>
            <td width="60">操作人</td>
            <c:if test="${isOperator==1}">
                <td width="40">操作</td>
            </c:if>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="m" items="${msgs}">
            <tr>
                <td>${m.msg_id}</td>
                <td class="left break">标题：${m.title}<br/><note>内容：${m.content}</note>
                </td>
                <td class="left">${m.mobile.length() > 0 ? m.mobile : m.rule_name_disp}</td>
                <td><fmt:formatDate value="${m.create_fulltime}" pattern="dd HH:mm:ss"/></td>
                <td>${m.operator}</td>
                <c:if test="${isOperator==1}">
                    <td class="op">
                        <c:choose>
                            <c:when test="${1 == m.status}">
                                <a href="javascript:void(0);" class="t2" onclick="cancel(${m.msg_id});">取消</a>
                            </c:when>
                            <c:otherwise>
                                <a href="/push/msg/edit/${m.msg_id}" class="t2">查看</a>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </c:if>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</datagrid>
<ct:pagebar pageSize="${page_size}" rowCount="${row_count}"/>
</body>
</html>