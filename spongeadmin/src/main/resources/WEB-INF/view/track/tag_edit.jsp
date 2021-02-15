<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ct" uri="/tags" %>

<!DOCTYPE HTML>
<html class="frm10">
<head>
    <title>${app} - 跟踪管理-编辑标签</title>
    <link rel="shortcut icon" type="image/x-icon" href="/favicon.ico"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8 "/>
    <link rel="stylesheet" href="${css}/main.css"/>
    <script src="/_session/domain.js"></script>
    <script src="${js}/lib.js"></script>
    <script src="${js}/layer.js"></script>
    <script>
        $(function () {
            $("input[name='date']").val(getQueryString("date") ? getQueryString("date") : "<% out.print(new SimpleDateFormat("yyyy-MM-dd").format(new Date())); %>");
            $("input[name='key']").val(getQueryString("key"));
        });

        function save() {
            var tag_name = $("#tagName").val();
            var tag_host = $("#tagHost").val();
            var agroup_id = ${agroup_id};
            var note = $("#note").val();
            var tag_id = ${tag.tag_id};

            if(agroup_id=='0'){
                top.layer.msg("请选择应用组");
                return;
            }

            var t_user_field = $("#t_user_field").val();
            var t_track_params = $("#t_track_params").val();
            var t_trans_params = $("#t_trans_params").val();
            var t_build_link = $("#t_build_link").val();

            if(!tagName) {
                top.layer.msg("标签名不能为空");
            } else {
                top.layer.confirm("确定修改", {
                    btn: ['确定','取消'] //按钮
                }, function(){
                    $.ajax({
                        type:"POST",
                        url:"/track/tag/edit/ajax/save",
                        data:{
                            "tag_id":tag_id,
                            "tag_name":tag_name,
                            "tag_host":tag_host,
                            "agroup_id":agroup_id,
                            "note":note,
                            "t_user_field":t_user_field,
                            "t_track_params":t_track_params,
                            "t_trans_params":t_trans_params,
                            "t_build_link":t_build_link
                        },
                        success: function(data){
                            if(data.code == 1){
                                top.layer.msg(data.msg);
                                location.href = "/track/tag/inner?agroup_id="+agroup_id;
                            } else {
                                top.layer.msg(data.msg)
                            }
                        }
                    });
                    top.layer.close(top.layer.index);
                });
            }
        };
    </script>
    <style>
    </style>
</head>
<body>

<toolbar class="blockquote">
    <left class="ln30">
        <h2><a onclick="history.back(-1)" href="#" class="noline">标签配置</a></h2> /  编辑
    </left>
</toolbar>

<detail>
    <form>
        <table>

            <tr>
                <th>标签名称</th>
                <td><input type="text" id="tagName" value="${tag.tag_name}"/> *必填</td>
            </tr>
            <tr>
                <th>标签主域</th>
                <td><input type="text" id="tagHost" value="${tag.tag_host}"/> 例：https://x.x.x</td>
            </tr>
            <tr>
                <th>用户标识</th>
                <td><input type="text" id="t_user_field" value="${tag.t_user_field}"/></td>
            </tr>
            <tr>
                <th>跟踪参数</th>
                <td><input type="text" id="t_track_params" value="${tag.t_track_params}"/> 例：(c,f,ap,v)用逗号隔开；禁放无限值的参数</td>
            </tr>
            <tr>
                <th>透传参数</th>
                <td><input type="text" id="t_trans_params" value="${tag.t_trans_params}" /> 例：(c=c,ukey=uk)用逗号隔开</td>
            </tr>
            <tr>
                <th>构建链接<br/></th>
                <td><textarea  id="t_build_link" style="height: 80px;">${tag.t_build_link}</textarea> 例：(网站::f=web)</td>
            </tr>
            <tr>
                <th>备注</th>
                <td><input type="text" id="note" class="longtxt" /></td>
            </tr>
            <c:if test="${isOperator==1}">
                <tr>
                    <td></td>
                    <td><button type="button" onclick="save()">保存</button></td>
                </tr>
            </c:if>
        </table>
    </form>
</detail>

</body>
</html>