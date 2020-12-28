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
            var akey = $('#akey').val();
            var ar_is_setting = $('input[name="ar_is_setting"]:checked').val();
            var ar_is_examine = $('input[name="ar_is_examine"]:checked').val();
            var note = $('#note').val();
            var ar_examine_ver = $('#ar_examine_ver').val();

            if (!name || name==null) {
                top.layer.msg("应用名称不能为空！");
                return;
            }
            if (!akey || akey==null) {
                top.layer.msg("akey不能为空！");
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
                data:{"app_id":app_id,"name":name,"agroup_id":agroup_id,"ugroup_id":ugroup_id,"app_key":app_key,"akey":akey,"ar_is_examine":ar_is_examine,"ar_is_setting":ar_is_setting,"note":note,"ar_examine_ver":ar_examine_ver},
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

        <detail><form>
            <h2>编辑应用</h2>
            <hr/>
            <table>
                <tr>
                    <td>应用名称</td>
                    <td><input type="text" id="name" value="${appEdit.name}"></td>
                </tr>
                <tr style="display: none;">
                    <td>所属应用组</td>
                    <td>
                        <select id="app_group" disabled="disabled">
                            <c:forEach var="app_group" items="${app_groups}">
                            <option value=${app_group.agroup_id}>${app_group.name}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>所属用户组</td>
                    <td>
                        <select id="user_group">
                            <c:forEach var="user_group" items="${user_groups}">
                                <option value=${user_group.ugroup_id}>${user_group.name}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>签名密钥</td>
                    <td><input disabled="disabled" type="text" id="app_key"  value="${appEdit.app_key}"/></td>
                </tr>
                <tr>
                    <td>akey</td>
                    <td><input disabled="disabled" type="text" id="akey"  value="${appEdit.akey}"/>
                    </td>
                </tr>
                <tr style="display: none">
                    <td>备注</td>
                    <td><input type="text" id="note" class="longtxt" value="${appEdit.note}"/></td>
                </tr>
                <tr>
                    <td>是否可设置</td>
                    <td>
                        <radio>
                        <c:if test="${appEdit.ar_is_setting == 1}">
                            <label><input type="radio" name="ar_is_setting" value="1" checked="checked"><a>是</a></label>&nbsp;&nbsp;&nbsp;
                            <label><input type="radio" name="ar_is_setting" value="0"><a>否</a></label>
                        </c:if>
                        <c:if test="${appEdit.ar_is_setting == 0}">
                            <label><input type="radio" name="ar_is_setting" value="1"><a>是</a></label>&nbsp;&nbsp;&nbsp;
                            <label><input type="radio" name="ar_is_setting" value="0" checked="checked"><a>否</a></label>
                        </c:if>
                        </radio>
                    </td>
                </tr>
                <tr>
                    <td>是否审核中</td>
                    <td>
                        <radio>
                        <c:if test="${appEdit.ar_is_examine == 1}">
                            <label><input type="radio" name="ar_is_examine" value="1" checked="checked"><a>是</a></label>&nbsp;&nbsp;&nbsp;
                            <label><input type="radio" name="ar_is_examine" value="0"><a>否</a></label>
                        </c:if>
                        <c:if test="${appEdit.ar_is_examine == 0}">
                            <label><input type="radio" name="ar_is_examine" value="1"><a>是</a></label>&nbsp;&nbsp;&nbsp;
                            <label><input type="radio" name="ar_is_examine" value="0" checked="checked"><a>否</a></label>
                        </c:if>
                        </radio>
                    </td>
                </tr>
                <tr>
                    <td>审核版本号</td>
                    <td><input type="text" id="ar_examine_ver" value="${appEdit.ar_examine_ver}"/></td>
                </tr>
                <tr>
                    <td></td>
                    <td><button type="button" onclick="saveEdit()">保存</button></td>
                </tr>
            </table>
        </form></detail>

</body>
</html>