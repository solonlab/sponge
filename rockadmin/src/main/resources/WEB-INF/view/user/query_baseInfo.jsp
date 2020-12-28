<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ct" uri="/tags" %>
<!DOCTYPE HTML>
<html class="frm style2">
<head>
    <title>${app} - 基本信息</title>
    <link rel="shortcut icon" type="image/x-icon" href="/favicon.ico"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8 "/>
    <link rel="stylesheet" href="${css}/main.css"/>
    <script src="/_session/domain.js"></script>
    <script src="${js}/lib.js"></script>
    <script>
        function isShow() {
            $('#address_table').toggle();
            var text1 = $("#hidden1").text();
            if (text1 == '收起') {
                $("#hidden1").text("展开");
            } else {
                $("#hidden1").text("收起");
            }
        };
    </script>
</head>
<body>
<ct:header/>
<main class="sml">
    <ct:toolmenu pack="sponge_user_d"></ct:toolmenu>
    <detail>
        <section>
            <header>
            <h2>注册信息</h2>
            </header>
            <article>
            <table>
                <tr>
                    <th>用户ID</th>
                    <td>${userRegister.open_id}</td>
                </tr>
                <tr>
                    <th>手机号</th>
                    <td>${userRegister.mobile}</td>

                </tr>
                <tr>
                    <th>注册时间</th>
                    <td><fmt:formatDate value="${userRegister.create_fulltime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>

                </tr>
            </table>
            </article>
        </section>

        <section>
            <header>
            <h2>个人基本信息（<a class="t2">查看历史更新记录</a>）</h2>
            </header>
            <article>
            <table>
                <tr>
                    <th>QQ</th>
                    <td>${baseInfo.qq}</td>
                </tr>
                <tr>
                    <th>学历</th>
                    <td>${qualification}</td>
                </tr>
                <tr>
                    <th>婚姻状况</th>
                    <td>${mstatus}</td>
                </tr>
                <tr>
                    <th>房产情况</th>
                    <td>${house_status}</td>
                </tr>
                <tr>
                    <th>房产类型</th>
                    <td>${house_type}</td>
                </tr>
                <tr>
                    <th>家庭住址</th>
                    <td>
                        <c:if test="${is_hide==1}">${baseInfo.hideAddress(baseInfo.address)}</c:if>
                        <c:if test="${is_hide!=1}">${baseInfo.address}</c:if>
                    </td>
                </tr>
                <tr>
                    <th>最近更新时间</th>
                    <td><fmt:formatDate value="${baseInfo.update_fulltime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                </tr>
                <tr>
                    <th>首次提交时间</th>
                    <td><fmt:formatDate value="${baseInfo.create_fulltime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                </tr>
            </table>
            </article>
        </section>

        <section>
            <header>
            <h2>工作信息（<a class="t2">查看历史更新记录</a>）</h2>
            </header>
            <article>
            <table>
                <tr>
                    <th>职业</th>
                    <td>${career}</td>
                </tr>
                <tr>
                    <th>工作时间</th>
                    <td>${career_time}</td>
                </tr>
                <tr>
                    <th>公司行业</th>
                    <td>${co_industry}</td>
                </tr>
                <tr>
                    <th>公司性质</th>
                    <td>${conature}</td>
                </tr>
                <tr>
                    <th>职位</th>
                    <td>${position}</td>
                </tr>
                <tr>
                    <th>年收入</th>
                    <td>${salary}</td>
                </tr>
                <tr>
                    <th>工作单位</th>
                    <td>${workInfo.coname}</td>
                </tr>
                <tr>
                    <th>公司电话</th>
                    <td>${workInfo.cophone}</td>
                </tr>
                <tr>
                    <th>单位地址</th>
                    <td>${workInfo.coaddress}</td>
                </tr>
                <tr>
                    <th>最近更新时间</th>
                    <td><fmt:formatDate value="${workInfo.update_fulltime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                </tr>
                <tr>
                    <th>首次提交时间</th>
                    <td><fmt:formatDate value="${workInfo.create_fulltime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                </tr>
            </table>
            </article>
        </section>
    </detail>
</main>

</body>
</html>