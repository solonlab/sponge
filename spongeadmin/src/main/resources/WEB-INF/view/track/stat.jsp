<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ct" uri="/tags" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>${app} - 数据统计</title>
    <link rel="shortcut icon" type="image/x-icon" href="/favicon.ico"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8 "/>
    <link rel="stylesheet" href="${css}/main.css"/>
    <script src="/_session/domain.js"></script>
    <script src="${js}/lib.js"></script>
    <script>
        function queryForm() {
            location.href = "/track/stat?agroup_id="+$('#app_group').val();
        }

        $(function () {
            $('#app_group').val(${agroup_id});
            $('tree li:first').addClass('sel');
        });
        var stateVal = 0;
        var tagID = ${tag_id};
        function node_onclick(tag_id,obj) {
            tagID = tag_id
            $('li.sel').removeClass('sel');
            $(obj).addClass("sel");
            $("#table").attr('src',"/track/stat/inner?tag_id="+tagID+"&stateVal="+stateVal);
        };
    </script>
</head>
<body>

<main>
    <middle>
        <tree id="tree">
            <ul>
                <div style="margin: 5px;"><select style="width: 100%;" id="app_group" name="agroup_id" onchange="queryForm();">
                    <c:forEach var="m" items="${app_groups}">
                        <option value=${m.agroup_id}>${m.name}</option>
                    </c:forEach>
                </select>
                </div>
                <c:forEach var="m" items="${tagList}">
                    <li onclick="node_onclick('${m.tag_id}',this)">- ${m.tag_name}</li>
                </c:forEach>
            </ul>
        </tree>
    </middle>
    <right class="frm">
        <iframe src="/track/stat/inner?tag_id=${tag_id}" frameborder="0" id="table"></iframe>
    </right>
</main>

</body>
</html>