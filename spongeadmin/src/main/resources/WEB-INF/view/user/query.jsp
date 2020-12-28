<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ct" uri="/tags" %>
<!DOCTYPE HTML>
<html class="frm10">
<head>
    <title>${app} - 用户列表</title>
    <link rel="shortcut icon" type="image/x-icon" href="/favicon.ico"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8 "/>
    <link rel="stylesheet" href="${css}/main.css"/>
    <script src="/_session/domain.js"></script>
    <script src="${js}/lib.js"></script>
    <script src="${js}/layer.js"></script>
    <style>
        datagrid b{color: #8D8D8D;font-weight: normal}
    </style>
</head>
<body>

<main>

        <toolbar>
            <cell>
                <form>
                <input type="text" value="${mobile}" name="mobile" id="mobile" placeholder="手机号"/>
                <button type="submit">查询</button>&nbsp;&nbsp;
                </form>
            </cell>
            <cell>
                <ct:stateselector items="已实名,未实名"/>
            </cell>
        </toolbar>

        <datagrid>
            <table>
                <thead>
                <tr>
                    <td width="120px">手机号</td>
                    <td>身份证识别</td>
                    <td width="75px">芝麻分</td>
                    <td width="75px">同盾分</td>
                    <td width="90px">橡皮分</td>
                    <td>借款记录</td>
                    <td>逾期记录</td>
                    <td>信用卡数</td>
                    <td>借记卡数</td>
                    <td>取现记录</td>
                </tr>
                </thead>
                <tbody>
                    <c:forEach var="user" items="${userList}">
                        <tr>
                            <td><a target="_blank" style="color: blue" href="/user/query_baseInfo?open_id=${user.open_id}">${user.mobile}</a></td>
                            <td>
                                <c:if test="${is_hide==1}">${user.hideName(user.id_name)}</c:if>
                                <c:if test="${is_hide!=1}">${user.id_name}</c:if>
                            </td>
                            <td>${user.zm_score}</td>
                            <td>${user.td_score}<a style="color: blue;cursor: pointer;" onclick="updateTD('${user.open_id}')"> (更新)</a></td>
                            <td>${user.rubber_score}<a style="color: blue;cursor: pointer;" onclick="reqRubberScore('${user.open_id}')"> (更新)</a></td>
                            <td>${user.borrow_record}</td>
                            <td>${user.overdue_record}</td>
                            <td>${user.credit_card}</td>
                            <td>${user.debit_card}</td>
                            <td>${user.cash_record}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </datagrid>
        <ct:pagebar pageSize="${pageSize}" rowCount="${rowCount}"/>

</main>

</body>
<script>
    //更新同盾分
    function updateTD(open_id) {
        top.layer.msg("test");
        top.layer.confirm('确定更新同盾分', {
            btn: ['确定','取消'] //按钮
        }, function(){
            $.ajax({
                type:"POST",
                url:"/user/updateTD",
                data:{"open_id":open_id},
                success:function(data){
                    if (data == true) {
                        top.layer.msg("更新成功");
                        location.reload();
                    } else {
                        top.layer.msg("更新失败");
                    }
                }
            })
            top.layer.close(top.layer.index);
        });
    };

    //请求rubber_score
    function reqRubberScore(open_id) {
        top.layer.confirm('确定更新橡皮分', {
            btn: ['确定','取消'] //按钮
        }, function(){
            $.ajax({
                type:"POST",
                url:"/user/reqRubberScore",
                data:{"open_id":open_id},
                success:function(data){
                    if(data.code==1){
                        top.layer.msg("更新成功");
                        location.reload();
                    } else {
                        top.layer.msg(data.msg);
                    }
                }
            })
            top.layer.close(top.layer.index);
        });
    };
</script>
</html>