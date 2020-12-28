<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ct" uri="/tags" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>${app} - 卡bin管理</title>
    <link rel="shortcut icon" type="image/x-icon" href="/favicon.ico"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8 "/>
    <link rel="stylesheet" href="${css}/main.css"/>
    <script src="/_session/domain.js"></script>
    <script src="${js}/lib.js"></script>
    <script>
        var bank_name='${bank_name}';
        <%--$(function () {--%>
            <%--$('#${bank_name}').addClass('sel');--%>
        <%--});--%>

        function node_onclick(bankName,obj) {
            bank_name = bankName;
            $('li.sel').removeClass('sel');
            $(obj).addClass("sel");
            $("iframe").attr('src',"/auth/cardbin/cardbin_inner?bank_name="+bank_name);
        };
    </script>
</head>
<body>
<main>
    <middle>
        <tree id="tree">
            <ul>
                <c:forEach var="m" items="${banks}">
                    <li onclick="node_onclick('${m.bank_name}',this)" id="${m.bank_name}">${m.bank_name} </li>
                </c:forEach>
            </ul>
        </tree>
    </middle>
    <right class="frm">
        <iframe src="/auth/cardbin/cardbin_inner?bank_name=${bank_name}" frameborder="0"></iframe>
    </right>


</main>

</body>
<script>
</script>
</html>