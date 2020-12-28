<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ct" uri="/tags" %>

<!DOCTYPE HTML>
<html class="frm10">
<head>
    <title>${app} - <c:choose><c:when test="${is_edit}">编辑模板</c:when><c:otherwise>新建模板</c:otherwise></c:choose></title>
    <link rel="shortcut icon" type="image/x-icon" href="/favicon.ico"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8 "/>
    <link rel="stylesheet" href="${css}/main.css"/>
    <script src="/_session/domain.js"></script>
    <script src="${js}/lib.js"></script>
    <script>
        $(function () {
            <c:if test="${is_edit}">
            $("#tmp_content_simple").val("${tmp.name}");
            $("#tmp_content").val("${tmp.content}");
            $("#out_id").val("${tmp.out_id}");
            </c:if>
        });

        function save() {
            $.ajax({
                type: "POST",
                url: "/push/template/ajax/save",
                data: {
                    <c:if test="${is_edit}">template_id: ${tmp.template_id},</c:if>
                    agroup_id: ${agroup_id},
                    name: $("#tmp_content_simple").val(),
                    out_id: $("#out_id").val(),
                    content: $("#tmp_content").val()
                },
                success: function (data) {
                    if (data.code === 1) {
                        top.layer.msg(data.msg);
                        location.href = "/push/template/inner/" + ${agroup_id};
                    } else {
                        top.layer.msg(data.msg);
                    }
                }
            });
        };

        function del() {
            top.layer.confirm('确定删除模版', {
                btn: ['确定','取消'] //按钮
            }, function(){
                $.ajax({
                    type: "POST",
                    url: "/push/template/ajax/del",
                    data: {
                        "template_id":${tmp.template_id}
                    },
                    success: function (data) {
                        if (data.code === 1) {
                            top.layer.msg(data.msg);
                            location.href = "/push/template/inner/" + ${agroup_id};
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
        <h2><c:choose><c:when test="${is_edit}">编辑模板</c:when><c:otherwise>新建模板</c:otherwise></c:choose>（<a class="t2" onclick="history.back(-1)">返回</a>）</h2>
        <hr/>
        <table>
            <tr>
                <td>模板名称</td>
                <td>
                    <input type="text" placeholder="输入标题" id="tmp_content_simple" />
                </td>
            </tr>
            <tr>
                <td>模板标识</td>
                <td>
                    <input type="text" id="out_id" />
                </td>
            </tr>
            <tr>
                <td>模板内容</td>
                <td><textarea id="tmp_content"></textarea></td>
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