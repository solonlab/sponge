<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ct" uri="/tags" %>

<!DOCTYPE HTML>
<html class="frm10">
<head>
    <title>${app} - 编辑用户组</title>
    <link rel="shortcut icon" type="image/x-icon" href="/favicon.ico"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8 "/>
    <link rel="stylesheet" href="${css}/main.css"/>
    <script src="/_session/domain.js"></script>
    <script src="${js}/lib.js"></script>
    <script src="${js}/layer.js"></script>
    <script>
        var ugroup_id = ${ugroup.ugroup_id};
        function saveEdit() {
            var name = $('#name').val();
            var u_id = $('#ugroup_id').val();
            var is_enabled = $('#is_enabled').prop("checked")?1:0;

            if (!name || name==null) {
                top.layer.msg("应用组名称不能为空！");
                return;
            }

            if(ugroup_id==null){
                ugroup_id=0
            }
            if(!u_id){
                u_id = 0;
            }
            $.ajax({
                type:"POST",
                url:"/rock/ugroup/edit/ajax/save",
                data:{
                    "ugroup_id":ugroup_id,
                    "name":name,
                    "new_ugroup_id":u_id,
                    "is_enabled":is_enabled
                },
                success:function (data) {
                    if(data.code==1) {
                        top.layer.msg(data.msg);
                            setTimeout(function(){
                                location.href="/rock/ugroup";
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

<main>
        <detail><form>
            <h2>编辑用户组</h2>
            <hr/>
            <table>
                <c:if test="${isAdmin==1}">
                    <tr>
                        <td>用户组ID</td>
                        <td><input type="text" id="ugroup_id" value="${ugroup.ugroup_id}" onkeyup="value=value.replace(/[^\d.]/g,'')"></td>
                    </tr>
                </c:if>
                <tr>
                    <td>用户组名称</td>
                    <td><input type="text" id="name" value="${ugroup.name}"></td>
                </tr>
                <tr>
                    <td>是否启用</td>
                    <td>
                        <switcher>
                            <label><input id="is_enabled" value="1" type="checkbox" ${ugroup.is_disabled == 0?"checked":""}><a></a></label>
                        </switcher>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td><button type="button" onclick="saveEdit()">保存</button></td>
                </tr>
            </table>
        </form></detail>
</main>

</body>
</html>