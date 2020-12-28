<%@ page import="sun.security.util.Length" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib prefix="ct" uri="/tags" %>
<!DOCTYPE HTML>
<html class='frm style2'>
<head>
    <title>${app} - 用户详情</title>
    <link rel="shortcut icon" type="image/x-icon" href="/favicon.ico"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8 "/>
    <link rel="stylesheet" href="//static.kdz6.cn/lib/durian/main.css"/>
    <script src="/_session/domain.js"></script>
</head>
<body>
<main class="sml">
    <detail>
        <section>
            <header>
                <h2>登录、打开和GPS</h2>
            </header>
            <article>
                <datagrid>
                    <table>
                        <thead>
                        <tr>
                            <td width="160px">时间</td>
                            <td width="130px">行为</td>
                            <td width="200px">设备号</td>
                            <td width="200px">GPS</td>
                            <td width="200px">ip</td>
                        </tr>
                        </thead>
                        <c:forEach var="m" items="${list}">
                            <tr>
                                <td class="center"><fmt:formatDate value="${m.log_fulltime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                <td class="center">${m.getOperateName()}</td>
                                <td class="center">${m.device_udid}</td>
                                <td class="center">${m.lng}&nbsp;-&nbsp;${m.lat}</td>
                                <td class="center">${m.ip}</td>
                            </tr>
                        </c:forEach>
                    </table>
                </datagrid>
            </article>
        </section>
    </detail>

</main>

</body>
</html>
