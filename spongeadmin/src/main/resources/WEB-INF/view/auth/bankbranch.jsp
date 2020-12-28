<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ct" uri="/tags" %>
<!DOCTYPE HTML>
<html class="frm10">
<head>
    <title>${app} - 支行管理</title>
    <link rel="shortcut icon" type="image/x-icon" href="/favicon.ico"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8 "/>
    <link rel="stylesheet" href="${css}/main.css"/>
    <script src="/_session/domain.js"></script>
    <script src="${js}/lib.js"></script>
</head>
<body>
<main>
    <toolbar>
        <cell>
            <form>
                <button type="button" onclick="editBrankBank(0)" class="edit">新增支行</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                银行编码：
                <select name="bank_code" style="width: 80px">
                    <option value="" <c:if test="${bank_code == null}">selected</c:if> >全部</option>
                    <c:forEach var="m" items="${bankCodes}">
                        <option <c:if test="${bank_code eq m.bank_code}">selected</c:if>>${m.bank_code}</option>
                    </c:forEach>
                </select>&nbsp;&nbsp;&nbsp;
                支行名称：<input type="text" value="${name}" name="name" id="name" placeholder="请输入支行名称"/>
                &nbsp;&nbsp;
                <button type="submit">查询</button>&nbsp;&nbsp;
            </form>
        </cell>
    </toolbar>

    <datagrid>
        <table>
            <thead>
            <tr>
                <td width="120px">支行码</td>
                <td class="left">支行名称</td>
                <td width="120px">行号</td>
                <td width="100px">所属银行编码</td>
                <td width="120px">支行座机</td>
                <td>支行地址</td>
                <td width="60px">操作</td>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="m" items="${branchs}">
                <tr>
                    <td>${m.paysys_bank}</td>
                    <td class="left">${m.name}</td>
                    <td>${m.bank_no}</td>
                    <td>${m.bank_code}</td>
                    <td>
                        <c:if test="${empty m.phone}">-</c:if>
                        <c:if test="${!empty m.phone}">${m.phone}</c:if>
                    </td>
                    <td class="break">
                        <c:if test="${empty m.bank_addr}">-</c:if>
                        <c:if test="${!empty m.bank_addr}">${m.bank_addr}</c:if>
                    </td>
                    <td>
                        <c:if test="${m.id>150000}">
                            <a style="cursor: pointer;color: blue;text-decoration: underline" onclick="editBrankBank('${m.id}');">编辑</a>
                        </c:if>
                        <c:if test="${m.id<=150000}">
                            <a style="color: gray;">编辑</a>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </datagrid>
    <ct:pagebar pageSize="${pageSize}" rowCount="${rowCount}"/>
</main>

</body>
<script>
    function editBrankBank(id) {
        window.location.href = "/auth/bankbranch/bankbranch_edit?id="+id;
    };
</script>
</html>