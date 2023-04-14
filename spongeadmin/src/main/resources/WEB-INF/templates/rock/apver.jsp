<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ct" uri="/tags" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>${app} - 应用版本发布</title>
    <link rel="shortcut icon" type="image/x-icon" href="/favicon.ico"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8 "/>
    <link rel="stylesheet" href="${css}/main.css"/>
    <script src="/_session/domain.js"></script>
    <script src="${js}/lib.js"></script>
    <script>
        $(function () {
            if (${!empty agroup_id}) {
                $('#${agroup_id}').addClass('sel');
            } else {
                $('tree li:first').addClass('sel');
            }

        });
        var agroupId = '${agroup_id}';
        function node_onclick(agroup_id,obj) {
            agroupId = agroup_id
            $('li.sel').removeClass('sel');
            $(obj).addClass("sel");
            $("#table").attr('src',"/rock/apver/inner?agroup_id="+agroupId);
        };
    </script>
</head>
<body>
<main>
    <middle>
        <tree id="tree">
            <ul>
                <c:forEach var="map" items="${apGmap}">
                    <li onclick="node_onclick('${map.key}',this)" id="${map.key}">${map.value.name} (${map.value.counts})</li>
                </c:forEach>
            </ul>
        </tree>
    </middle>
    <right class="frm">
        <iframe src="/rock/apver/inner?agroup_id=${agroup_id}&_state=${_state}" frameborder="0" id="table"></iframe>
    </right>
</main>
</body>
</html>