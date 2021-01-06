<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ct" uri="/tags" %>
<!DOCTYPE HTML>
<html class="frm style2">
<head>
    <title>${app} - 身份证识别</title>
    <link rel="shortcut icon" type="image/x-icon" href="/favicon.ico"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8 "/>
    <link rel="stylesheet" href="${css}/main.css"/>
    <script src="/_session/domain.js"></script>
    <script src="${js}/lib.js"></script>
</head>
<body>
    <ct:header/>
    <main class="sml">
        <ct:toolmenu pack="sponge_user_d"></ct:toolmenu>
        <detail>
            <section>
                <header>

                <h2>身份证识别（<a class="t2">查看历史更新记录</a>）</h2>
                </header>
                <article>
                <table>
                    <tr>
                        <th>姓名</th>
                        <td>
                            <c:if test="${is_hide==1}">${user.hideName(user.id_name)}</c:if>
                            <c:if test="${is_hide!=1}">${user.id_name}</c:if>
                        </td>
                    </tr>
                    <tr>
                        <th>性别</th>
                        <td>
                            <c:if test="${user.id_sex == 1}">男</c:if>
                            <c:if test="${user.id_sex == 0}">女</c:if>
                        </td>
                    </tr>
                    <tr>
                        <th>民族</th>
                        <td>${user.id_nation}</td>
                    </tr>
                    <tr>
                        <th>出生</th>
                        <td>${user.id_birthday}</td>
                    </tr>
                    <tr>
                        <th>住址</th>
                        <td>
                            <c:if test="${is_hide==1}">${user.hideAddress(user.id_address)}</c:if>
                            <c:if test="${is_hide!=1}">${user.id_address}</c:if>
                        </td>
                    </tr>
                    <tr>
                        <th>身份证号</th>
                        <td>
                            <c:if test="${is_hide==1}">${user.hideIdCode(user.id_code)}</c:if>
                            <c:if test="${is_hide!=1}">${user.id_code}</c:if>
                        </td>
                        <%--<td>${user.id_code}</td><td style="width: 500px"/>--%>

                    </tr>
                    <tr>
                        <th>发证机关</th>
                        <td>${user.id_police}</td><td/>
                    </tr>
                    <tr>
                        <th>有效期限</th>
                        <td id="date">${user.id_date_start} 至 ${user.id_date_end}</td><td/>
                    </tr>
                    <tr>
                        <th>最近更新时间</th>
                        <td><fmt:formatDate value="${user.update_fulltime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    </tr>
                    <tr>
                        <th>首次提交时间</th>
                        <td><fmt:formatDate value="${user.create_fulltime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    </tr>
                </table>
                </article>
            </section>

            <section>
                <article>
                <table>
                    <tr>
                        <td><h2>身份证正面</h2></td>
                        <td><h2>身份证反面</h2></td>
                        <td><h2>人脸识别照片</h2></td>
                    </tr>
                    <tr>
                        <c:if test="${is_hide!=1}">
                            <td><img style="width: 200px;height: 140px" src="${user.card_img_url}"/></td>
                            <td><img style="width: 200px;height: 140px" src="${user.card_back_img_url}"/></td>
                            <td><img style="width: 200px;" src="${user.face_img}"/></td>
                        </c:if>
                        <c:if test="${is_hide==1}">
                            <td>
                                <span>${user.hasCardImgUrl(user.card_img_url)}</span>
                            </td>
                            <td>
                                <span>${user.hasCardImgUrl(user.card_back_img_url)}</span>
                            </td>
                            <td>
                                <span>${user.hasCardImgUrl(user.face_img)}</span>
                            </td>
                        </c:if>
                    </tr>
                </table>
                </article>
            </section>

        </detail>
    </main>
</body>
</html>