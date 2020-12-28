<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ct" uri="/tags" %>

<!DOCTYPE HTML>
<html class="frm10">
<head>
    <title>${app} - 版本发布编辑页</title>
    <link rel="shortcut icon" type="image/x-icon" href="/favicon.ico"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8 "/>
    <link rel="stylesheet" href="${css}/main.css"/>
    <script src="/_session/domain.js"></script>
    <script src="${js}/lib.js"></script>
    <script src="${js}/layer.js"></script>
    <script>
        $(function () {
            document.getElementById('type').value="${apver.type}";
            document.getElementById('platform').value="${apver.platform}";
        });
        var row_id = ${apver.row_id};
        var agroup_id = ${agroup_id};
        function saveEdit() {
            var app_id = $('#app_id').val();
            var ver = $('#ver').val();
            var content = $('#content').val();
            var type = $('#type').val();
            var platform = $('#platform').val();
            var url = $('#url').val();
            var is_enable = $('input[name="is_enable"]:checked').val();
            if (!ver || ver==null) {
                top.layer.msg("版本号不能为空！");
                return;
            };
            if(row_id==null){
                row_id=0
            };

            var reg = "^[0-9]*$";
            var re = new RegExp(reg);
            if (re.test(app_id)&&re.test(ver)) {
            }
            else{
                top.layer.msg('应用ID和版本号为纯数字！');
                return;
            }

            $.ajax({
                type:"POST",
                url:"/rock/apver/edit/ajax/save",
                data:{"app_id":app_id,"row_id":row_id,"ver":ver,"content":content,"type":type,
                       alert_ver:$('#alert_ver').val(),
                       force_ver:$('#force_ver').val(),
                      "platform":platform,"url":url,"is_enable":is_enable,"agroup_id":agroup_id},
                success:function (data) {
                    if(data.code==1) {
                        top.layer.msg(data.msg);
                            setTimeout(function(){
                                parent.location.href="/rock/apver?agroup_id="+agroup_id;
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
            <h2>编辑版本</h2>
            <hr/>
            <table>
                <tr>
                    <td>应用ID:</td>
                    <td><input type="text" id="app_id" value="${apver.app_id}" placeholder="请输入应用ID"></td>
                </tr>
                <tr>
                    <td>版本号:</td>
                    <td><input type="text" id="ver" value="${apver.ver}" placeholder="请输入版本号"></td>
                </tr>
                <tr>
                    <td>更新内容:</td>
                    <td><textarea id="content">${apver.content}</textarea></td>
                </tr>
                <tr>
                    <td>更新方式:</td>
                    <td>
                    <select id="type">
                        <option value="0">普通更新</option>
                        <option value="1">强制更新</option>
                    </select>（第三优先）
                    </td>
                </tr>
                <tr>
                    <td>定制更新:</td>
                    <td>
                        <input type="text" id="alert_ver" value="${apver.alert_ver}" class="txt"  placeholder="输入版本号"> 版本以下提示更新（第二优先；0表示忽略）
                        <br/>
                        <input style="margin-top: -1px;" value="${apver.force_ver}" type="text" id="force_ver" class="txt"  placeholder="输入版本号"> 版本以下强制更新（第一优先；0表示忽略）
                    </td>
                </tr>
                <tr>
                    <td>选择平台:</td>
                    <td>
                        <select id="platform">
                            <option value="1">IOS</option>
                            <option value="2">Android</option>
                            <option value="3">Web</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>更新地址:</td>
                    <td><input type="text" id="url" value="${apver.url}" class="longtxt" placeholder="请输入更新地址"></td>
                </tr>
                <tr>
                    <td>是否生效:</td>
                    <td>
                        <radio>
                        <c:if test="${apver.is_enable == 1}">
                            <label><input type="radio" name="is_enable" value="1" checked="checked"><a>是</a></label>&nbsp;&nbsp;&nbsp;
                            <label><input type="radio" name="is_enable" value="0"><a>否</a></label>
                        </c:if>
                        <c:if test="${apver.is_enable != 1}">
                            <label><input type="radio" name="is_enable" value="1"><a>是</a></label>&nbsp;&nbsp;&nbsp;
                            <label><input type="radio" name="is_enable" value="0" checked="checked"><a>否</a></label>
                        </c:if>
                        </radio>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td><button type="button" onclick="saveEdit()">保存</button></td>
                </tr>
            </table>
        </form>
        </detail>

</body>
</html>