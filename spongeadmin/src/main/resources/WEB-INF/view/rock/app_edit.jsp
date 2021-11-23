<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ct" uri="/tags" %>

<!DOCTYPE HTML>
<html class="frm10">
<head>
    <title>${app} - 编辑应用</title>
    <link rel="shortcut icon" type="image/x-icon" href="/favicon.ico"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8 "/>
    <link rel="stylesheet" href="${css}/main.css"/>
    <script src="/_session/domain.js"></script>
    <script src="${js}/lib.js"></script>
    <script src="${js}/layer.js"></script>
    <script>
        $(function () {
            document.getElementById('app_group').value="${appEdit.agroup_id}";
            document.getElementById('user_group').value="${appEdit.ugroup_id}";
        });
        var app_id = ${appEdit.app_id};
        function saveEdit() {
            var name = $('#name').val();
            var agroup_id = $('#app_group').val();
            var ugroup_id = $('#user_group').val();
            var app_key = $('#app_key').val();
            var app_secret_key = $('#app_secret_key').val();
            var app_secret_salt = $('#app_secret_salt').val();
            var ar_is_setting = $('#ar_is_setting').prop("checked")?1:0;
            var ar_is_examine = $('#ar_is_examine').prop("checked")?1:0;
            var note = $('#note').val();
            var ar_examine_ver = $('#ar_examine_ver').val();

            if (!name) {
                top.layer.msg("应用名称不能为空！");
                return;
            }
            if (!app_secret_key) {
                top.layer.msg("应用密钥不能为空！");
                return;
            }
            if(app_id==null){
                app_id=0
            };
            var reg = "^[0-9]*$";
            var re = new RegExp(reg);
            if (re.test(ar_examine_ver)) {
            }
            else{
                top.layer.msg('版本号应为纯数字！');
                return;
            }
            $.ajax({
                type:"POST",
                url:"/rock/app/edit/ajax/save",
                data:{
                    "app_id":app_id,
                    "name":name,
                    "agroup_id":agroup_id,
                    "ugroup_id":ugroup_id,
                    "app_key":app_key,
                    "app_secret_key":app_secret_key,
                    "app_secret_salt":app_secret_salt,
                    "ar_is_examine":ar_is_examine,
                    "ar_is_setting":ar_is_setting,
                    "note":note,
                    "ar_examine_ver":ar_examine_ver
                },
                success:function (data) {
                    if(data.code==1) {
                        top.layer.msg(data.msg);
                        setTimeout(function(){
                            parent.location.href="/rock/app?agroup_id="+agroup_id;
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
        <h2><a onclick="history.back(-1)" href="#" class="noline">应用</a></h2> /  编辑
    </left>
</toolbar>

<detail>
    <form>
        <table>
            <tr>
                <th>应用名称</th>
                <td><input type="text" id="name" value="${appEdit.name}"></td>
            </tr>
            <tr style="display: none;">
                <th>所属应用组</th>
                <td>
                    <select id="app_group" disabled="disabled">
                        <c:forEach var="app_group" items="${app_groups}">
                            <option value=${app_group.agroup_id}>${app_group.name}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <th>所属用户组</th>
                <td>
                    <select id="user_group">
                        <c:forEach var="user_group" items="${user_groups}">
                            <option value=${user_group.ugroup_id}>${user_group.name}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <th>应用标识</th>
                <td><input disabled="disabled" type="text" id="app_key"  value="${appEdit.app_key}"/>
                <n>（appKey）</n>
                </td>
            </tr>
            <tr>
                <th>密钥</th>
                <td><input disabled="disabled" type="text" id="app_secret_key"  value="${appEdit.app_secret_key}"/>
                    <n>（appSecretKey）</n>
                </td>
            </tr>
            <tr>
                <th>密钥-盐</th>
                <td><input disabled="disabled" type="text" id="app_secret_salt"  value="${appEdit.app_secret_salt}"/>
                    <n>（appSecretSalt）</n>
                </td>
            </tr>
            <tr style="display: none">
                <th>备注</th>
                <td><input type="text" id="note" class="longtxt" value="${appEdit.note}"/></td>
            </tr>
            <tr>
                <th>审核版本号</th>
                <td><input type="text" id="ar_examine_ver" value="${appEdit.ar_examine_ver}"/></td>
            </tr>
            <tr>
                <th>是否可设置</th>
                <td>
                    <switcher>
                        <label><input id="ar_is_setting" value="1" type="checkbox" ${appEdit.ar_is_setting == 1?"checked":""}><a></a></label>
                    </switcher>
                </td>
            </tr>
            <tr>
                <th>是否审核中</th>
                <td>
                    <switcher>
                        <label><input id="ar_is_examine" value="1" type="checkbox" ${appEdit.ar_is_examine == 1?"checked":""}><a></a></label>
                    </switcher>
                </td>
            </tr>
            <tr>
                <th></th>
                <td><button type="button" onclick="saveEdit()">保存</button></td>
            </tr>
        </table>
    </form>
</detail>

</body>
</html>