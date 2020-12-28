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
    <style>
        toolmenu{
            padding-top: 20px;
            padding-bottom: 0;
        }
    </style>
</head>
<body>
<ct:header/>
<ct:toolmenu pack="panda_user_d"></ct:toolmenu>
<iframe src="${url}" frameborder="0" id="table" width="100%" height="1150px"></iframe>
<iframe src="/auth/query_gps?open_id=${open_id}&agroup_id=1" frameborder="0" width="100%" height="500px"></iframe>
</body>
</html>
