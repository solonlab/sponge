<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ct" uri="/tags" %>
<!DOCTYPE HTML>
<html class="frm10">
<head>
    <title>${app} - 应用</title>
    <link rel="shortcut icon" type="image/x-icon" href="/favicon.ico"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8 "/>
    <link rel="stylesheet" href="${css}/main.css"/>
    <script src="/_session/domain.js"></script>
    <script src="${js}/lib.js"></script>
    <script>
        function addApCode() {
            location.href="/rock/apcode/add?agroup_id=${agroup_id}&service=${service}";
        }
    </script>
    <style>
    </style>
</head>
<body>

        <toolbar>
            <left>
                <form>
                    <input type="text" value="${code_num}" name="code_num" placeholder="状态码"/>&nbsp;&nbsp;
                    <input type="hidden" value="${agroup_id}" name="agroup_id" />
                    <input type="hidden" value="${service}" name="service" />
                    <button type="submit">查询</button>&nbsp;&nbsp;
                    <c:if test="${agroup_id>0&&isOperator==1}">
                        <button type="button" onclick="addApCode()" class="edit">新增</button>
                    </c:if>
                </form>
            </left>
            <right>
                <selector>
                    <c:forEach var="l" items="${langs}">
                    <a class='noline ${l.tag == lang ? "sel":"" }' href="./inner?lang=${l.tag}&agroup_id=${agroup_id}&service=${service}">${l.tag}(${l.counts})</a>
                    </c:forEach>
                </selector>
            </right>
        </toolbar>
        <datagrid>
            <table>
                <thead>
                <tr>
                    <td width="100px">状态码</td>
                    <td width="100px">语言</td>
                    <td>对应信息</td>
                    <c:if test="${isOperator==1}">
                        <td width="50px">操作</td>
                    </c:if>
                </tr>
                </thead>
                <tbody>
                    <c:forEach var="code" items="${codes}">
                        <tr>
                            <td style="text-align: left">${code.code}</td>
                            <td style="text-align: left">${code.lang}</td>
                            <td style="text-align: left;word-break: break-all;word-wrap: break-word">${code.note}</td>
                            <c:if test="${isOperator==1}">
                                <td><a href="/rock/apcode/edit?row_id=${code.row_id}" style="color: blue;">编辑</a></td>
                            </c:if>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </datagrid>

</body>
</html>