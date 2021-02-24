<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ct" uri="/tags" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>${app} - 应用设置</title>
    <link rel="shortcut icon" type="image/x-icon" href="/favicon.ico"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8 "/>
    <link rel="stylesheet" href="${css}/main.css"/>
    <script src="/_session/domain.js"></script>
    <script src="${js}/lib.js"></script>
    <script>


        $(function () {
            $('#app_group').val(${agroup_id});

            if (${!empty app_id}) {
                $('#${app_id}').addClass('sel');
            } else {
                $('tree li:first').addClass('sel');
            }
        });

        var appId = '${app_id}';

        function queryForm() {
            location.href = "/rock/apsets?agroup_id="+$('#app_group').val();
        }


        function node_onclick(app_id,obj) {
            appId = app_id;
            $('li.sel').removeClass('sel');
            $(obj).addClass("sel");
            $("#table").attr('src',"/rock/apsets/inner?app_id="+appId);
        };
    </script>
</head>
<body>
<main>
    <middle>
        <tree id="tree">
            <ul>
                <div style="margin: 5px;"><select style="width: 100%;" id="app_group" name="agroup_id" onchange="queryForm();">
                <option value=""></option>
                <c:forEach var="app_group" items="${app_groups}">
                    <option value=${app_group.agroup_id}>${app_group.name}</option>
                </c:forEach>
                </select>
                </div>
                <c:forEach var="app" items="${appList}">
                    <li onclick="node_onclick('${app.app_id}',this)" id="${app.app_id}">- ${app.name}</li>
                </c:forEach>
            </ul>
        </tree>
    </middle>
    <right class="frm">
        <iframe src="/rock/apsets/inner?app_id=${app_id}" frameborder="0" id="table"></iframe>
    </right>
</main>
</body>
</html>