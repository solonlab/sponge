<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ct" uri="/tags" %>
<!DOCTYPE HTML>
<html class="frm10">
<head>
    <title>${app} - 跟踪管理</title>
    <link rel="shortcut icon" type="image/x-icon" href="/favicon.ico"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8 "/>
    <link rel="stylesheet" href="${css}/main.css"/>
    <script src="/_session/domain.js"></script>
    <script src="${js}/lib.js"></script>
    <script src="${js}/layer.js"></script>
    <script>

        function saveAddUrl() {
            var tagID = ${tag_id};
            var url_name = $("#url_name").val();
            var url_partner_key = $("#url_partner_key").val();
            var url_val = $("#url_val").val();
            var user_field = $("#user_field").val();
            var track_params = $("#track_params").val();
            var trans_params = $("#trans_params").val();
            var build_link = $("#build_link").val();
            var note = $("#note").val();
            if (url_name==null||url_name==undefined||url_name==""){
                top.layer.msg("名称不能为空")
                return;
            };
            $.ajax({
                type:"POST",
                url:"/track/url/add/ajax/save",
                data:{"tag_id":tagID,"url_name":url_name,"url_partner_key":url_partner_key,"url_val":url_val,"track_params":track_params,"trans_params":trans_params,"note":note,"user_field":user_field,"build_link":build_link},
                success:function(data){
                    if (data.code == 1){
                        top.layer.msg("创建成功");
                        setTimeout(function(){
                            location.href = "/track/url/inner?tag_id="+tagID;
                        },1000);

                    } else {
                        top.layer.msg(data.msg);
                    }
                }
            })
        };
    </script>
</head>
<body>
<toolbar class="blockquote">
    <left class="ln30">
        <h2><a onclick="history.back(-1)" href="#" class="noline">跟踪配置</a></h2> /  新建
    </left>
</toolbar>

<detail>
    <form>
        <table>
            <tr>
                <th>名称</th>
                <td><input type="text" id="url_name"/> *必填</td>
            </tr>
            <tr>
                <th>外部标识</th>
                <td><input type="text" id="url_partner_key"/></td>
            </tr>
            <tr>
                <th>原网址</th>
                <td><input type="text" id="url_val" class="longtxt"/></td>
            </tr>
            <tr>
                <th>用户标识</th>
                <td>
                    <c:if test="${!empty tag.t_user_field}">
                        <input type="text" id="user_field" value="${tag.t_user_field}" disabled="disabled"/>
                    </c:if>
                    <c:if test="${empty tag.t_user_field}">
                        <input type="text" id="user_field" value="${tag.t_user_field}"/>
                    </c:if>
                </td>
            </tr>
            <tr>
                <th>跟踪参数</th>
                <td>
                    <c:if test="${!empty tag.t_track_params}">
                        <input type="text" id="track_params" value="${tag.t_track_params}" disabled="disabled"/> 例：(c,f,ap,v)用逗号隔开；禁放无限值的参数
                    </c:if>
                    <c:if test="${empty tag.t_track_params}">
                        <input type="text" id="track_params" value="${tag.t_track_params}" /> 例：(c,f,ap,v)用逗号隔开；禁放无限值的参数
                    </c:if>
                </td>
            </tr>
            <tr>
                <th>透传参数</th>
                <td>
                    <input type="text" id="trans_params" value="${tag.t_trans_params}" /> 例：(c=c,ukey=uk)用逗号隔开（即，将获取的参数值附加给源网址）
                </td>
            </tr>
            <tr>
                <th>构建链接<br/></th>
                <td><textarea  id="build_link" style="height:80px">${tag.t_build_link}</textarea> 例：(网站::f=web)</td>
            </tr>
            <tr>
                <th>备注</th>
                <td><input type="text" id="note" class="longtxt" /></td>
            </tr>
            <tr>
                <th></th>
                <td><button type="button" onclick="saveAddUrl()">保存</button></td>

            </tr>
        </table>
    </form>
</detail>
</body>
</html>