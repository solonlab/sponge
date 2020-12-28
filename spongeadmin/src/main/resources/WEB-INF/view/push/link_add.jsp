<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ct" uri="/tags" %>

<!DOCTYPE HTML>
<html class="frm10">
<head>
    <title>${app} - <c:choose><c:when test="${is_edit}">编辑链接</c:when><c:otherwise>新建链接</c:otherwise></c:choose></title>
    <link rel="shortcut icon" type="image/x-icon" href="/favicon.ico"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8 "/>
    <link rel="stylesheet" href="${css}/main.css"/>
    <script src="/_session/domain.js"></script>
    <script src="${js}/lib.js"></script>

    <script>
        $(function () {
            <c:if test="${is_edit}">
            $("#link_name").val("${link.name}");
            $("#link_link").val("${link.link}");
            </c:if>
        });

        function save() {
            $.ajax({
                type: "POST",
                url: "/push/link/ajax/save",
                data: {
                    <c:if test="${is_edit}">link_id: ${link.link_id},</c:if>
                    agroup_id: ${agroup_id},
                    name: $("#link_name").val(),
                    link: $("#link_link").val()
                },
                success: function (data) {
                    if (data.code === 1) {
                        top.layer.msg(data.msg);
                        location.href = "/push/link/inner/" + ${agroup_id};
                    } else {
                        top.layer.msg(data.msg);
                    }
                }
            });
        };

        function del() {
            top.layer.confirm('确定删除链接', {
                btn: ['确定','取消'] //按钮
            }, function(){
                $.ajax({
                    type: "POST",
                    url: "/push/link/ajax/del",
                    data: {
                        "link_id":${link.link_id}
                    },
                    success: function (data) {
                        if (data.code === 1) {
                            top.layer.msg(data.msg);
                            location.href = "/push/link/inner/" + ${agroup_id};
                        } else {
                            top.layer.msg(data.msg);
                        }
                    }
                });
                top.layer.close(top.layer.index);
            });
        };
    </script>
</head>
<body>

<main>
    <detail><form>
        <h2><c:choose><c:when test="${is_edit}">编辑链接</c:when><c:otherwise>新建链接</c:otherwise></c:choose>（<a class="t2" onclick="history.back(-1)">返回</a>）</h2>
        <hr/>
        <table>
            <tr>
                <td>链接名称</td>
                <td>
                    <input type="text" placeholder="输入链接名称" id="link_name" />
                </td>
            </tr>
            <tr>
                <td>链接地址</td>
                <td><input type="text" placeholder="输入链接地址" id="link_link" style="width: 500px;" /></td>
            </tr>
            <tr>
                <td></td>
                <td>
                    <button type="button" onclick="save()">保存</button>&nbsp;
                    <c:if test="${isAdmin==1}">
                        <button type="button" onclick="del()" class="minor">删除</button>
                    </c:if>
                </td>
            </tr>
        </table>
    </form></detail>
</main>

</body>
</html>