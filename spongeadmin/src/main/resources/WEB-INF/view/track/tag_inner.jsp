<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ct" uri="/tags" %>
<!DOCTYPE HTML>
<html class="frm10">
<head>
    <title>${app} - 跟踪管理</title>
    <link rel="shortcut icon" type="image/x-icon" href="/favicon.ico"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8 "/>
    <link rel="stylesheet" href="${css}/main.css"/>
    <script src="/_session/domain.js"></script>
    <script src="${js}/lib.js"></script>
    <script>
        $(function () {
            $("input[name='date']").val(getQueryString("date") ? getQueryString("date") : "<% out.print(new SimpleDateFormat("yyyy-MM-dd").format(new Date())); %>");
            $("input[name='key']").val(getQueryString("key"));
        });

        function toAddTag() {
            location.href = "/track/tag/add?agroup_id=${agroup_id}"
        }
    </script>
    <style>
        datagrid b{color: #8D8D8D;font-weight: normal}
    </style>
</head>
<body>

<main>
    <c:if test="${agroup_id>0 && isOperator==1}">
        <toolbar>
            <cell>
                <button onclick="toAddTag()" class="edit">新建标签</button>
            </cell>
        </toolbar>
    </c:if>

        <datagrid>
            <table>
                <thead>
                <tr>
                    <td width="40">ID</td>
                    <td width="120">标签名称</td>
                    <td>用户标识</td>
                    <td>跟踪参数</td>
                    <td>透传参数</td>
                    <td width="30"></td>
                    <td width="80">浏览次数<br/>PV</td>
                    <td width="80">独立访客<br/>UV</td>
                    <td width="80">IP</td>
                    <td width="40"></td>
                </tr>
                </thead>
                <tbody>
                    <c:forEach var="tag" items="${tags}">
                        <tr>
                            <td>${tag.tag_id}</td>
                            <td>${tag.tag_name}</td>
                            <td>${tag.t_user_field}</td>
                            <td>${tag.t_track_params}</td>
                            <td>${tag.t_trans_params}</td>
                            <td>今日<br/><b>昨日</b></td>
                            <td style="text-align: right">
                                    ${tag.pv_today}
                                <br/>
                                        <b>${tag.pv_yesterday}</b>
                            </td>
                            <td style="text-align: right">
                                    ${tag.uv_today}
                                <br/>
                                    <b>${tag.uv_yesterday}</b>
                            </td>
                            <td style="text-align: right">
                                    ${tag.ip_today}
                                <br/>
                                    <b>${tag.ip_yesterday}</b>
                            </td>
                            <td><a href="/track/tag/edit?tag_id=${tag.tag_id}" style="color: blue">编辑</a></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </datagrid>

</main>

</body>
</html>