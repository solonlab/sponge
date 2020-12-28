<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ct" uri="/tags" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>${app} - 跟踪管理</title>
    <link rel="shortcut icon" type="image/x-icon" href="/favicon.ico"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8 "/>
    <link rel="stylesheet" href="${css}/main.css"/>
    <script src="/_session/domain.js"></script>
    <script src="${js}/lib.js"></script>
    <script>
        function queryForm() {
            location.href = "/track/url?agroup_id="+$('#app_group').val();
        }

        $(function () {
            $('#app_group').val(${agroup_id});

            $("input[name='date']").val(getQueryString("date") ? getQueryString("date") : "<% out.print(new SimpleDateFormat("yyyy-MM-dd").format(new Date())); %>");
            $("input[name='key']").val(getQueryString("key"));

            $('tree li:first').addClass('sel');
        });

        var tagID = ${tag_id};
        function node_onclick(tag_id,obj) {
            tagID = tag_id
            $('li.sel').removeClass('sel');
            $(obj).addClass("sel");
            $("iframe").attr('src',"/track/url/inner?tag_id="+tagID);
        }
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
            <iframe src="/track/url/inner?tag_id=${tag_id}" frameborder="0"></iframe>
        </right>
    </main>
</body>
</html>