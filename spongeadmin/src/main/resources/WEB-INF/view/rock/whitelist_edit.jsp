<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ct" uri="/tags" %>

<!DOCTYPE HTML>
<html class="frm10">
<head>
    <title>${app} - 编辑应用白名单</title>
    <link rel="shortcut icon" type="image/x-icon" href="/favicon.ico"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8 "/>
    <link rel="stylesheet" href="${css}/main.css"/>
    <script src="/_session/domain.js"></script>
    <script src="${js}/lib.js"></script>
    <script src="${js}/layer.js"></script>
    <script>
        var row_id = ${whitelist.row_id};
        function saveEdit() {
            var type = $('#type').val();
            var value = $('#value').val();
            var note = $('#note').val();
            var tag = $('#tag').val();
            if(row_id==null){
                row_id=0
            };

            if (tag==null||tag==undefined||tag==""){
                top.layer.msg("tag不能为空")
                return;
            };

            if (value==null||value==undefined||value==""){
                top.layer.msg("ip或host值不能为空")
                return;
            };
            $.ajax({
                type:"POST",
                url:"/rock/whitelist/edit/ajax/save",
                data:{"row_id":row_id,"type":type,"value":value,"note":note,"tag":tag},
                success:function (data) {
                    if(data.code==1) {
                        top.layer.msg(data.msg);
                        setTimeout(function(){
                            parent.location.href="/rock/whitelist?tag="+tag;
                        },1000);
                    }else{
                        top.layer.msg(data.msg);
                    }
                }
            });
        }

    </script>
    <style>
    </style>
</head>
<body>

<detail><form>
    <h2>编辑应用白名单</h2>
    <hr/>
    <table>
        <tr>
            <td>tag</td>
            <td><input type="text" id="tag"  value="${tag}"/></td>
        </tr>
        <tr>
            <td>类型</td>
            <td>
                <select id="type">
                    <c:if test="${whitelist.type == 0}">
                        <option value="0" selected>IP</option>
                        <option value="1">host</option>
                    </c:if>
                    <c:if test="${whitelist.type == 1}">
                        <option value="0">IP</option>
                        <option value="1" selected>host</option>
                    </c:if>
                </select>
            </td>
        </tr>
        <tr>
            <td>值</td>
            <td><input type="text" id="value"  value="${whitelist.value}"/></td>
        </tr>
        <tr>
            <td>备注</td>
            <td><textarea id="note">${whitelist.note}</textarea></td>
        </tr>
        <tr>
            <td></td>
            <td><button type="button" onclick="saveEdit()">保存</button></td>
        </tr>
    </table>
</form></detail>

</body>
</html>