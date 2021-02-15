<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ct" uri="/tags" %>

<!DOCTYPE HTML>
<html class="frm10">
<head>
    <title>${app} - 编辑应用组</title>
    <link rel="shortcut icon" type="image/x-icon" href="/favicon.ico"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8 "/>
    <link rel="stylesheet" href="${css}/main.css"/>
    <script src="/_session/domain.js"></script>
    <script src="${js}/lib.js"></script>
    <script src="${js}/layer.js"></script>
    <script>
        $(function () {
            document.getElementById('ugroup').value="${agroup.ugroup_id}";
        });

        var agroup_id = ${agroup.agroup_id};
        function saveEditAgroup() {
            var name = $('#name').val();
            var a_id = $('#agroup_id').val();
            var tag = $('#tag').val();
            var ugroup_id = $('#ugroup').val();
            var is_enabled = $('#is_enabled').prop("checked")?1:0;

            if (!name || name==null) {
                top.layer.msg("应用组名称不能为空！");
                return;
            }

            if(agroup_id==null){
                agroup_id=0
            }

            if(!a_id){
                a_id=0;
            }

            $.ajax({
                type:"POST",
                url:"/rock/agroup/edit/ajax/save",
                data:{
                    "agroup_id":agroup_id,
                    "new_agroup_id":a_id,
                    "name":name,
                    "tag":tag,
                    "ugroup_id":ugroup_id,
                    "is_enabled":is_enabled
                },
                success:function (data) {
                    if(data.code==1) {
                        top.layer.msg(data.msg);
                        setTimeout(function(){
                            location.href="/rock/agroup";
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

<toolbar class="blockquote">
    <left class="ln30">
        <h2><a onclick="history.back(-1)" href="#" class="noline">应用组</a></h2> /  编辑
    </left>
</toolbar>

<detail>
    <form>
        <table>
            <c:if test="${isAdmin==1}">
                <tr>
                    <th>应用组ID</th>
                    <td><input type="text" id="agroup_id" value="${agroup.agroup_id}" onkeyup="value=value.replace(/[^\d.]/g,'')"></td>
                </tr>
            </c:if>
            <tr>
                <th>应用组名称</th>
                <td><input type="text" id="name" value="${agroup.name}"></td>
            </tr>
            <tr>
                <th>默认用户组</th>
                <td>
                    <select id="ugroup">
                        <option value=""></option>
                        <c:forEach var="ugroup" items="${ugroupList}">
                            <option value="${ugroup.ugroup_id}">${ugroup.name}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <th>技术代号名</th>
                <td><input type="text" id="tag" value="${agroup.tag}"></td>
            </tr>
            <tr>
                <th>是否启用</th>
                <td>
                    <switcher>
                        <label><input id="is_enabled" value="1" type="checkbox" ${agroup.is_disabled == 0?"checked":""}><a></a></label>
                    </switcher>
                </td>
            </tr>

            <tr>
                <th></th>
                <td><button type="button" onclick="saveEditAgroup()">保存</button></td>
            </tr>
        </table>
    </form>
</detail>


</body>
</html>