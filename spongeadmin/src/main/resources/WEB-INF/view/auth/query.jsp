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
    <script src="${js}/laydate/laydate.js"></script>
    <style>
        datagrid b{color: #8D8D8D;font-weight: normal}
        .gray{
            color: gray;
        }
        .date{
            width: 95px;
            padding-left: 3px;
        }
    </style>
</head>
<body>
<main>
    <toolbar>
        <cell>
            <form>
                <input type="text" value="${mobile}" name="mobile" id="mobile" placeholder="手机号"/>&nbsp;
                <input type="text" value="${date_start}" id="date_start" name="date_start" placeholder="实名开始日期" autocomplete="off" class="date"/>
                - <input type="text" value="${date_end}" id="date_end" name="date_end" placeholder="实名结束日期" autocomplete="off" class="date"/>&nbsp;&nbsp;
                <button type="submit">查询</button>&nbsp;&nbsp;
            </form>
        </cell>
        <cell>
            <ct:stateselector items="已实名,待补全,未实名"/>
        </cell>
    </toolbar>

    <datagrid>
        <table>
            <thead>
            <tr>
                <td width="120px">手机号</td>
                <td>身份证</td>
                <td width="90px">实名日期</td>
                <td width="75px">芝麻分</td>
                <td>运营商</td>
                <td>联系人</td>
                <td>淘宝</td>
                <td>基本信息</td>
                <td>工作信息</td>
                <td>信用卡</td>
                <td>公积金</td>
                <td>网银</td>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="user" items="${users}">
                <tr>
                    <td><a target="_blank" style="color: blue" href="/auth/query_base?open_id=${user.open_id}">${user.mobile}</a></td>
                    <td>
                        <c:if test="${is_hide==1}">${user.hideName(user.id_name)}</c:if>
                        <c:if test="${is_hide!=1}">${user.id_name}</c:if>
                    </td>
                    <td><fmt:formatDate value="${user.real_fulltime}" pattern="yyyy-MM-dd"/></td>
                    <td>${user.zm_score}</td>
                    <td class="<c:if test="${user.is_operator!=1}">gray</c:if>">${user.getStateName(user.is_operator)}</td>
                    <td class="<c:if test="${user.is_linkman!=1}">gray</c:if>">${user.getStateName(user.is_linkman)}</td>
                    <td class="<c:if test="${user.is_taobao!=1}">gray</c:if>">${user.getStateName(user.is_taobao)}</td>
                    <td class="<c:if test="${user.is_base!=1}">gray</c:if>">${user.getStateName(user.is_base)}</td>
                    <td class="<c:if test="${user.is_work!=1}">gray</c:if>">${user.getStateName(user.is_work)}</td>
                    <td class="<c:if test="${user.is_email_ccard!=1}">gray</c:if>">${user.getStateName(user.is_email_ccard)}</td>
                    <td class="<c:if test="${user.is_acum_fund!=1}">gray</c:if>">${user.getStateName(user.is_acum_fund)}</td>
                    <td class="<c:if test="${user.is_cyber_bank!=1}">gray</c:if>">${user.getStateName(user.is_cyber_bank)}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </datagrid>
    <ct:pagebar pageSize="${pageSize}" rowCount="${rowCount}"/>
</main>

</body>
<script>
    laydate.render({
        elem: '#date_start',
        format:'yyyyMMdd',
        theme: '#189aca'
    });
    laydate.render({
        elem: '#date_end',
        format:'yyyyMMdd',
        theme: '#189aca'
    });
</script>
</html>