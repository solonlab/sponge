<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ct" uri="/tags" %>

<!DOCTYPE HTML>
<html class="frm10">
<head>
    <title>${app} - 编辑应用组设置</title>
    <link rel="shortcut icon" type="image/x-icon" href="/favicon.ico"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8 "/>
    <link rel="stylesheet" href="${css}/main.css"/>
    <script src="/_session/domain.js"></script>
    <script src="${js}/lib.js"></script>
    <script src="${js}/layer.js"></script>
    <script>
        $(function () {
            document.getElementById('type').value="${agsets.type}";
        });

        var row_id = ${agsets.row_id}
        var agroup_id = ${agroup_id};
        function saveEdit() {
            var name = $('#name').val();
            var type = $('#type').val();
            var value = $('#value').val();
            var note = $('#note').val();
            var is_client = $('input[name="is_client"]:checked').val();
            var ver_start =$('#ver_start').val();
            if (!name || name==null) {
                top.layer.msg("配置项名称不能为空！");
                return;
            }
            if(row_id==null){
                row_id=0
            }
            if(agroup_id==null){
                agroup_id=0
            }
            $.ajax({
                type:"POST",
                url:"/rock/agsets/edit/ajax/save",
                data:{"row_id":row_id,"name":name,"type":type,"value":value, "note":note,"is_client":is_client,"ver_start":ver_start,"agroup_id":agroup_id},
                success:function (data) {
                    if(data.code==1) {
                        top.layer.msg(data.msg);
                            setTimeout(function(){
                                parent.location.href="/rock/agsets?agroup_id="+agroup_id;
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
            <h2>编辑应用组设置</h2>
            <hr/>
            <table>
                <tr>
                    <td>项名称</td>
                    <td><input type="text" id="name" value="${agsets.name}"></td>
                </tr>
                <tr>
                    <td>项值类型</td>
                    <td>
                        <select id="type">
                            <option value="0">文字</option>
                            <option value="1">数字</option>
                            <option value="9">JSON</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>项值</td>
                    <td><textarea id="value" style="height: 230px;">${agsets.value}</textarea></td>
                </tr>
                <tr>
                    <td>备注</td>
                    <td><input type="text" id="note" class="longtxt" value="${agsets.note}"/></td>
                </tr>
                <tr>
                    <td>开始支持的版本</td>
                    <td><input type="text" id="ver_start" value="${agsets.ver_start}"/></td>
                </tr>
                <tr>
                    <td>是否输出到客户端</td>
                    <td>
                        <radio>
                        <c:if test="${agsets.is_client == 1}">
                            <label><input type="radio" name="is_client" value="1" checked="checked"><a>是</a></label>&nbsp;&nbsp;&nbsp;
                            <label><input type="radio" name="is_client" value="0"><a>否</a></label>
                        </c:if>
                        <c:if test="${agsets.is_client == 0}">
                            <label><input type="radio" name="is_client" value="1"><a>是</a></label>&nbsp;&nbsp;&nbsp;
                            <label><input type="radio" name="is_client" value="0" checked="checked"><a>否</a></label>
                        </c:if>
                        </radio>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td><button type="button" onclick="saveEdit()">保存</button></td>
                </tr>
            </table>

        </form></detail>

</body>
</html>