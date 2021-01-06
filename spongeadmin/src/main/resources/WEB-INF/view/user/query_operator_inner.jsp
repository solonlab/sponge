<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ct" uri="/tags" %>
<!DOCTYPE HTML>
<html class="frm10">
<head>
    <title>${app} - 运营商数据</title>
    <link rel="shortcut icon" type="image/x-icon" href="/favicon.ico"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8 "/>
    <link rel="stylesheet" href="${css}/main.css"/>
    <script src="/_session/domain.js"></script>
    <script src="${js}/lib.js"></script>
</head>
<body>

<toolbar>
    <cell>
        <form>
            <input type="text" value="${mobile}" id="mobile" name="mobile" placeholder="手机号"/>
            <input type="text" value="${open_id}" id="open_id" name="open_id" style="display: none"/>
            <button  type="submit">查询</button>
        </form>
    </cell>
</toolbar>
<datagrid>
    <table>
        <thead>
            <tr>
                <td width="150px">姓名</td>
                <td>电话</td>
                <td width="150px">主叫/被叫</td>
            </tr>
        </thead>
        <tbody class="list">
        <c:forEach var="m" items="${calllogs}">
            <tr>
                <td >
                    <c:if test="${is_hide==1}">${m.hideName(m.name)}</c:if>
                    <c:if test="${is_hide!=1}">${m.name}</c:if>
                </td>
                <td >
                    <c:if test="${is_hide==1}">${m.hideMobile(m.phone)}</c:if>
                    <c:if test="${is_hide!=1}">${m.phone}</c:if>
                </td>
                <td>${m.call_out_count}/${m.call_in_count}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</datagrid>
<ct:pagebar pageSize="${pageSize}" rowCount="${rowCount}"/>

</body>
</html>