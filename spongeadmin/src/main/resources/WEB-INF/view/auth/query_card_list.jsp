<%@ page import="sun.security.util.Length" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib prefix="ct" uri="/tags" %>
<!DOCTYPE HTML>
<html class='frm style2'>
<head>
    <title>${app} - 用户详情</title>
    <link rel="shortcut icon" type="image/x-icon" href="/favicon.ico"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8 "/>
    <link rel="stylesheet" href="//static.kdz6.cn/lib/durian/main.css"/>
    <script src="/_session/domain.js"></script>
    <script src="${js}/lib.js"></script>
    <style>
        .card-img{
            width: 50px;
            height: 30px;
            cursor: pointer;
        }
        .card-ico{
            width: 30px;
            height: 30px;
        }
    </style>
</head>
<body>
<ct:header/>
<main class="sml">
    <ct:toolmenu pack="panda_user_d"></ct:toolmenu>
    <detail>
        <section>
            <header>
                <h2>借记卡</h2>
            </header>
            <article>
                <datagrid>
                    <table>
                        <thead>
                        <tr>
                            <td width="60px">姓名</td>
                            <td width="100px">片号</td>
                            <td width="80px" class="left">卡名称</td>
                            <td width="90px">手机号</td>
                            <td>银行<br/>名称</td>
                            <td width="150px" class="left">支行名称</td>
                            <td>支行码</td>
                            <td width="130px">身份证号</td>
                            <td>银行卡<br/>图片</td>
                            <td>卡片<br/>图标</td>
                            <td width="30px">是否<br/>主卡</td>
                            <td>状态</td>
                            <td width="83px">更新时间</td>
                        </tr>
                        </thead>
                        <c:forEach var="m" items="${dcardList}">
                            <tr>
                                <td class="center">
                                    <c:if test="${is_hide==0}">${m.holder_name}</c:if>
                                    <c:if test="${is_hide==1}">${m.getHideIdName()}</c:if>
                                </td>
                                <td class="center">
                                    <c:if test="${is_hide==0}">${m.card_code}</c:if>
                                    <c:if test="${is_hide==1}">${m.getHideCardCode()}</c:if>
                                </td>
                                <td class="left break">${m.card_name}</td>
                                <td class="center">${m.card_mobile}</td>
                                <td class="center">${m.bank_name}</td>
                                <td class="left break">${m.bank_branch}</td>
                                <td class="center">${m.paysys_bank}</td>
                                <td class="center">
                                    <c:if test="${is_hide==0}">${m.id_code}</c:if>
                                    <c:if test="${is_hide==1}">${m.getHideIdCode()}</c:if>
                                </td>
                                <td class="center">
                                    <c:if test="${is_hide==0}"><img src="${m.card_img}" class="card-img" onclick="window.open('${m.card_img}','target')"/></c:if>
                                    <c:if test="${is_hide==1}">***</c:if>
                                </td>
                                <td class="center"><img src="${m.icon}" class="card-ico"/></td>
                                <td class="center">${m.isMainCard()}</td>
                                <c:if test="${m.status==0}">
                                    <td class="center" style="color: red">${m.statusName()}</td>
                                </c:if>
                                <c:if test="${m.status!=0}">
                                    <td class="center">${m.statusName()}</td>
                                </c:if>
                                <td class="center"><fmt:formatDate value="${m.update_fulltime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                            </tr>
                        </c:forEach>
                    </table>
                </datagrid>
            </article>
        </section>

        <section>
            <header>
                <h2>信用卡</h2>
            </header>
            <article>
                <datagrid>
                    <table>
                        <thead>
                        <tr>
                            <td>姓名</td>
                            <td width="100px">卡号</td>
                            <td width="130px" class="left">卡名称</td>
                            <td width="90px">手机号</td>
                            <td>银行名称</td>
                            <td width="150px" class="left">支行名称</td>
                            <td width="150px">身份证号</td>
                            <td>信用卡过期时间</td>
                            <td>状态</td>
                            <td width="140px">更新时间</td>
                        </tr>
                        </thead>
                        <c:forEach var="m" items="${ccardList}">
                            <tr>
                                <td class="center">
                                    <c:if test="${is_hide==0}">${m.holder_name}</c:if>
                                    <c:if test="${is_hide==1}">${m.getHideIdName()}</c:if>
                                </td>
                                <td class="center">
                                    <c:if test="${is_hide==0}">${m.card_code}</c:if>
                                    <c:if test="${is_hide==1}">${m.getHideCardCode()}</c:if>
                                </td>
                                <td class="left break">${m.card_name}</td>
                                <td class="center">${m.card_mobile}</td>
                                <td class="center">${m.bank_name}</td>
                                <td class="left break">${m.bank_branch}</td>
                                <td class="center">
                                    <c:if test="${is_hide==0}">${m.id_code}</c:if>
                                    <c:if test="${is_hide==1}">${m.getHideIdCode()}</c:if>
                                </td>
                                <td class="center">${m.expiration_date}</td>
                                <c:if test="${m.status==0}">
                                    <td class="center" style="color: red">${m.statusName()}</td>
                                </c:if>
                                <c:if test="${m.status!=0}">
                                    <td class="center">${m.statusName()}</td>
                                </c:if>
                                <td class="center"><fmt:formatDate value="${m.update_fulltime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                            </tr>
                        </c:forEach>
                    </table>
                </datagrid>
            </article>
        </section>

        <section>
            <header>
                <h2>交易信息（收乎）</h2>
            </header>
            <article>
                <iframe src="${url}" frameborder="0" id="table" width="100%" height="100px"></iframe>
            </article>
        </section>

    </detail>
</main>

</body>
</html>
