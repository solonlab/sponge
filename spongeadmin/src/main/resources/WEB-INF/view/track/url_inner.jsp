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
    <script src="${js}/clipboard.min.js"></script>
    <script src="${js}/layer.js"></script>
    <script>
        function toAddUrl() {
            location.href = "/track/url/add?tag_id=${tag_id}";
        };
        function toEditUrl(url_id) {
            location.href = "/track/url/edit?url_id="+url_id+"&tag_id=${tag_id}";
        };

        //复制功能
        $(document).ready(function(){
            var clipboard1 = new Clipboard('.a');
            clipboard1.on('success', function(e) {
                top.layer.msg("复制成功，去粘贴吧");
            });
        });
        function query() {
            UrlQueryBy('url_name',$('#url_name').val());
        };
    </script>
    <style>
        tbody td{text-align: left;}
    </style>
</head>
<body>
<toolbar>
    <flex>
        <left class="col-4">
            <c:if test="${tag_id>0 && isOperator==1}">
                <button onclick="toAddUrl()" class="edit">新建跟踪</button>
            </c:if>
        </left>
        <middle  class="col-4">
            <input type="text" class="w250" value="${url_name}" id="url_name" placeholder="名称"/>
            <button onclick="query()">查询</button>
        </middle>
        <right  class="col-4">
            <ct:stateselector items="启用中,禁用中"/>
        </right>
    </flex>
</toolbar>
<datagrid>
    <table>
        <thead>
        <tr>
            <td width="50px">外部<br/>标识</td>
            <td>名称</td>
            <td>源网址</td>
            <td>用户<br/>标识</td>
            <td>跟踪<br/>参数</td>
            <td>透传<br/>参数</td>
            <td>复制<br/>代码</td>
            <td width="40px">操作</td>
        </tr>
        </thead>
        <tbody id="tbody">
        <c:forEach var="url" items="${urls}">
            <tr>
                <td>${url.url_partner_key}</td>
                <td title="${url.url_id}">
                    <c:if test="${url.has_track == 1}">
                        <a href="/track/stat/url?url_id=${url.url_id}" target="_blank" style="cursor: pointer;text-decoration:none;color: blue">${url.url_name}</a>
                    </c:if>
                    <c:if test="${url.has_track == 0}">
                        ${url.url_name}
                    </c:if>
                </td>
                <td style="max-width: 450px;min-width:300px;word-wrap : break-word;word-break:break-all;">${url.url_val}</td>
                <td>${url.user_field}</td>
                <td>${url.track_params}</td>
                <td class="break">${url.trans_params}</td>
                <td style="text-align: center;">
                    <c:forEach var="copy" items="${url.hrefList}">
                        <c:if test="${empty copy.url}">
                            <a style="cursor: pointer;color: blue" class="a" data-clipboard-text="${track_uri}${url.url_key}" >${copy.name}</a>
                        </c:if>
                        <c:if test="${!empty copy.url}">
                            <span> | </span><a style="cursor: pointer;color: blue" class="a" data-clipboard-text="${track_uri}${url.url_key}?${copy.url}" >${copy.name}</a>
                        </c:if>
                    </c:forEach>
                </td>
                <td class="center"><a onclick="toEditUrl('${url.url_id}')" style="color: blue;cursor:pointer">编辑</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</datagrid>
</body>
</html>