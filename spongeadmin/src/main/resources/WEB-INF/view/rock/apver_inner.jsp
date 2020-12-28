<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ct" uri="/tags" %>
<!DOCTYPE HTML>
<html class="frm10">
<head>
    <title>${app} - 应用版本发布</title>
    <link rel="shortcut icon" type="image/x-icon" href="/favicon.ico"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8 "/>
    <link rel="stylesheet" href="${css}/main.css"/>
    <script src="/_session/domain.js"></script>
    <script src="${js}/lib.js"></script>
    <script>
        function addApver() {
            location.href="/rock/apver/add?agroup_id=${agroup_id}";
        };

    </script>
    <style>
    </style>
</head>
<body>

        <toolbar>
            <cell>
                <c:if test="${agroup_id>0&&isOperator==1}">
                <button type="button" onclick="addApver()" class="edit">新建更新</button>
                </c:if>
            </cell>
            <cell><ct:stateselector state="1" items="全部,有效,无效"/></cell>
        </toolbar>

        <datagrid>
            <table>
                <thead>
                <tr>
                    <td width="60px">应用ID</td>
                    <td width="45px">版本号</td>
                    <td>更新内容</td>
                    <td width="70px">更新方式</td>
                    <td width="60px">平台</td>
                    <td width="90px">更新时间</td>
                    <td width="70px">是否有效</td>
                    <c:if test="${isOperator==1}">
                        <td width="50px">操作</td>
                    </c:if>
                </tr>
                </thead>
                <tbody>
                    <c:forEach var="apver" items="${apverList}">
                        <tr>
                            <td>${apver.app_id}</td>
                            <td>${apver.ver}</td>
                            <td style="text-align: left">${apver.content}</td>
                            <c:if test="${apver.type==0}"><td>普通更新</td></c:if>
                            <c:if test="${apver.type==1}"><td>强制更新</td></c:if>
                            <c:if test="${apver.platform==1}"><td>IOS</td></c:if>
                            <c:if test="${apver.platform==2}"><td>Android</td></c:if>
                            <c:if test="${apver.platform==3}"><td>Web</td></c:if>
                            <c:if test="${apver.platform==0||apver.platform==null}"><td>未知</td></c:if>
                            <td><fmt:formatDate value="${apver.log_fulltime}" pattern="yyyy-MM-dd HH:mm:dd"/></td>
                            <c:if test="${apver.is_enable==0}">
                            <td>无效</td>
                            </c:if>
                            <c:if test="${apver.is_enable==1}">
                            <td>有效</td>
                            </c:if>
                            <c:if test="${isOperator==1}">
                                <td><a href="/rock/apver/edit?row_id=${apver.row_id}&agroup_id=${agroup_id}" style="color: blue;">编辑</a></td>
                            </c:if>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </datagrid>

</body>
</html>