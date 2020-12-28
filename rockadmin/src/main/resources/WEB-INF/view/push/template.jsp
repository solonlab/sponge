<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ct" uri="/tags" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>${app} - 短信模板</title>
    <link rel="shortcut icon" type="image/x-icon" href="/favicon.ico"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8 "/>
    <link rel="stylesheet" href="${css}/main.css"/>
    <script src="/_session/domain.js"></script>
    <script src="${js}/lib.js"></script>
    <script src="${js}/layer.js"></script>
    <script>
        $(function () {
            if (${!empty agroup_id}) {
                $('#${agroup_id}').addClass('sel');
            } else {
                $('tree li:first').addClass('sel');
            }
        });

        function node_onclick(agroup_id, obj) {
            $('li.sel').removeClass('sel');
            $(obj).addClass("sel");
            $("iframe").attr('src', "/push/template/inner/" + agroup_id);
        }
    </script>
</head>
<body>
<main>
    <middle>

        <tree id="tree">
            <ul>
                <c:forEach var="m" items="${agroups}">
                    <li onclick="node_onclick('${m.agroup_id}',this)" id="${m.agroup_id}">${m.name}</li>
                </c:forEach>
            </ul>
        </tree>
    </middle>
    <right class="frm">
        <iframe src="/push/template/inner/${agroup_id}" frameborder="0"></iframe>
    </right>
</main>
</body>
</html>