<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ct" uri="/tags" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>${app} - ${fun_name}</title>
    <link rel="shortcut icon" type="image/x-icon" href="/favicon.ico"/>
    <link rel="stylesheet" href="//cdn.jsdelivr.net/npm/font-awesome@4.7.0/css/font-awesome.min.css" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8 "/>
    <link rel="stylesheet" href="${css}/main.css"/>
    <script src="/_session/domain.js"></script>
    <script src="${js}/jtadmin.js"></script>
    <script src="${js}/layer.js"></script>
    <style>
        body > header aside a{display:inline-block; height:100%; padding:0 15px; }
        body > header aside .split{border-left:1px solid #444;}
    </style>
</head>
<body>
<ct:header/>
<main>
    <c:if test="${fun_type == 0}">
        <left>
            <ct:leftmenu/>
        </left>
        <right class="frm">
            <iframe src="${fun_url}" frameborder="0" name="dock"></iframe>
        </right>
    </c:if>
    <c:if test="${fun_type == 1}">
        <iframe src="${fun_url}" frameborder="0" name="dock"></iframe>
    </c:if>
</main>
<ct:footer/>
</body>
</html>






