<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ct" uri="/tags" %>
<!DOCTYPE HTML>
<html class="frm10">
<head>
    <title>${app} - 卡bin管理</title>
    <link rel="shortcut icon" type="image/x-icon" href="/favicon.ico"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8 "/>
    <link rel="stylesheet" href="${css}/main.css"/>
    <script src="/_session/domain.js"></script>
    <script src="${js}/lib.js"></script>
    <style>
        form img{
            width: 25px;
            height: 25px;
            position: absolute;
            margin-top: 7px;
        }
    </style>
</head>
<body>

<main>
    <toolbar>
        <cell>
            <form>
                <button type="button" onclick="editCardBin(0);" class="edit">新增卡bin</button>&nbsp;&nbsp;&nbsp;&nbsp;
                <a style="color: #00CC99;">[${bank_name}]</a>&nbsp;&nbsp;

                <c:if test="${logo!=null}">
                    <img src="${logo}"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                </c:if>
                卡类型：
                <select name="card_type">
                    <option value="" <c:if test="${card_type == null}">selected</c:if>>全部</option>
                    <c:forEach var="m" items="${types}">
                        <option value="${m.card_type}" <c:if test="${card_type eq m.card_type}">selected</c:if>>${m.card_type}</option>
                    </c:forEach>
                </select>
                &nbsp;&nbsp;&nbsp;&nbsp;
                卡bin：<input type="text" value="${bin}" name="bin" id="bin" placeholder="请输入卡bin"/>&nbsp;&nbsp;
                <input value="${bank_name}" name="bank_name" id="bank_name" style="display: none"/>
                <button type="submit">查询</button>
            </form>
        </cell>
    </toolbar>

    <datagrid>
        <table>
            <thead>
            <tr>
                <td>机构代码</td>
                <td>卡类型</td>
                <td>卡名称</td>
                <td>卡号长度</td>
                <td>卡bin长度</td>
                <td class="left">卡bin</td>
                <td>银行编号</td>
                <td width="50px">操作</td>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="m" items="${cards}">
                <tr>
                    <td>${m.org_no}</td>
                    <td>${m.card_type}</td>
                    <td>${m.card_name}</td>
                    <td>${m.card_length}</td>
                    <td>${m.bin_length}</td>
                    <td class="left">${m.bin}</td>
                    <td>${m.bank_code}</td>
                    <td>
                        <c:if test="${m.id<=10000}">
                            <a style="color: gray">编辑</a>
                        </c:if>
                        <c:if test="${m.id>10000}">
                            <a style="cursor: pointer;text-decoration: underline;color: blue;" onclick="editCardBin(${m.id})">编辑</a>
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
    function editCardBin(id) {
        window.location.href = "/auth/cardbin/cardbin_edit?id="+id;
    };
</script>
</html>