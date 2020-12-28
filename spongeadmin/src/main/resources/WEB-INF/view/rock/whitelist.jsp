<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ct" uri="/tags" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>${app} - 应用白名单</title>
    <link rel="shortcut icon" type="image/x-icon" href="/favicon.ico"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8 "/>
    <link rel="stylesheet" href="${css}/main.css"/>
    <script src="/_session/domain.js"></script>
    <script src="${js}/lib.js"></script>
    <script>
        var tag = '${tag}';
        $(function () {
            if (${!empty tag}) {
                tag = escapeJquery(tag);
                $('#'+tag).addClass('sel');
            } else {
                $('tree li:first').addClass('sel');
            }
        });

        function node_onclick(agroup_id,obj) {
            tag = agroup_id
            $('li.sel').removeClass('sel');
            $(obj).addClass("sel");
            $("#table").attr('src',"/rock/whitelist/inner?tag="+tag);
        };


    </script>
</head>
<body>
<main>
    <middle>
        <tree id="tree">
            <ul>
                <c:forEach var="map" items="${tags}">
                    <li onclick="node_onclick('${map.tag}',this)" id="${map.tag}">${map.tag} (${map.count})</li>
                </c:forEach>
            </ul>
        </tree>
    </middle>
    <right class="frm">
        <iframe src="/rock/whitelist/inner?tag=${tag}" frameborder="0" id="table"></iframe>
    </right>
</main>
</body>
</html>