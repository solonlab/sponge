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
    <script src="${js}/jtadmin.js"></script>
    <script src="${js}/layer.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/vue@2"></script>
    <script>
        var agroup_id = "${model.agroup_id}";
        var nameOld = "${model.name}";
        var viewModel = {items: ${langs}};

        function saveEdit() {
            var service = $('#service').val().trim();
            var name = $('#name').val().trim();
            var items = JSON.stringify(viewModel.items);

            if(viewModel.items.length <1){
                top.layer.msg("描述配置不能为空！");
                return;
            }

            if (!service) {
                top.layer.msg("服务名不能为空！");
                return;
            }

            if (!name) {
                top.layer.msg("状态码不能为空！");
                return;
            }

            $.ajax({
                type:"POST",
                url:"/rock/api18n/edit/ajax/save",
                data:{"agroup_id":agroup_id, "service":service, "name":name, "nameOld":nameOld, "items":items},
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

        $(function (){
            ctl_s_save_bind(document, saveEdit);
        });
    </script>
    <style>
        #app li{margin-bottom: 4px;}
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
    <right class="form">
        <n>ctrl + s 可快捷保存</n>
        <button type="button" onclick="saveEdit()">保存</button>
    </right>
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
                <th class="top" style="padding-top: 45px;">描述配置</th>
                <td id="app">
                    <div>
                        <left><n class="w100" style="display: inline-block">语言</n></left>
                        <right><n class="longtxt" style="display: inline-block">描述</n></right>
                    </div>
                    <ul>
                        <li v-for="m in items">
                            <left><input class="w100" type="text" list="lang_list" autocomplete="off" v-model="m.lang"></left>
                            <right><input type="text" class="longtxt" v-model="m.note"/></right>
                        </li>
                    </ul>
                    <div>
                        <button type="button" @click="add" class="minor">添加</button>
                    </div>
                </td>
            </tr>
        </table>
    </form>
</detail>

<script>
    var app = new Vue({
        el: '#app',
        data: viewModel,
        methods:{
            add: function (){
                this.items.push({lang:"",note:""})
            }
        }
    })
</script>

</body>
</html>