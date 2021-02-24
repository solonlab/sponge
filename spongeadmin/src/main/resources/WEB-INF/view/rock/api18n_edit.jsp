<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ct" uri="/tags" %>

<!DOCTYPE HTML>
<html class="frm10">
<head>
    <title>${app} - 编辑状态码</title>
    <link rel="shortcut icon" type="image/x-icon" href="/favicon.ico"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8 "/>
    <link rel="stylesheet" href="${css}/main.css"/>
    <script src="/_session/domain.js"></script>
    <script src="${js}/lib.js"></script>
    <script src="${js}/layer.js"></script>
    <script>
        $(function () {
            document.getElementById('agroup_id').value="${model.agroup_id}";
        });
        var row_id = ${model.row_id};
        function saveEdit() {
            var service = $('#service').val();
            var name = $('#name').val();
            var agroup_id = $('#agroup_id').val();
            var note = $('#note').val();
            var lang = $('#lang').val();

            if (!service) {
                top.layer.msg("服务名不能为空！");
                return;
            }

            if (!name) {
                top.layer.msg("状态码不能为空！");
                return;
            }

            if(row_id==null){
                row_id=0
            }


            $.ajax({
                type:"POST",
                url:"/rock/api18n/edit/ajax/save",
                data:{"row_id":row_id,"agroup_id":agroup_id,"service":service,"note":note,"name":name,"lang":lang},
                success:function (data) {
                    if(data.code==1) {
                        top.layer.msg(data.msg);
                        setTimeout(function(){
                            parent.location.href="/rock/api18n?agroup_id="+agroup_id;
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

<datalist id="lang_list">
    <option value="en">en 英语</option>
    <option value="en_US">en_US 英语(美国)</option>
    <option value="en_IN">en_IN 英语(印度)</option>
    <option value="hi_IN">hi_IN 印度语</option>
    <option value="zh_CN">zh_CN 中文(中国)</option>
    <option value="zh_HK">zh_HK 中文(香港地区)</option>
    <option value="zh_TW">zh_TW 中文(台湾地区)</option>
</datalist>

<toolbar class="blockquote">
    <left class="ln30">
        <h2><a onclick="history.back(-1)" href="#" class="noline">应用状态码</a></h2> /  编辑
    </left>
</toolbar>

<detail>
    <form>
        <table>
            <tr>
                <th>服务</th>
                <td><input type="text" id="service" value="${model.service}"></td>
            </tr>
            <tr>
                <th>键值</th>
                <td><input type="text" id="name" value="${model.name}"></td>
            </tr>
            <tr>
                <th>语言</th>
                <td><input type="text" id="lang" list="lang_list" autocomplete="off" value="${model.lang}">
                <n>（空表示默认语言）</n>
                </td>
            </tr>
            <tr style="display: none;">
                <th>所属应用组</th>
                <td>
                    <select id="agroup_id" disabled="disabled">
                        <c:forEach var="app_group" items="${app_groups}">
                            <option value=${app_group.agroup_id}>${app_group.name}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <th>描述信息</th>
                <td><input type="text" id="note" class="longtxt" value="${model.note}"/></td>
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