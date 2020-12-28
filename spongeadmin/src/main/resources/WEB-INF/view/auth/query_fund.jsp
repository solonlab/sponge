<%@ page import="sun.security.util.Length" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib prefix="ct" uri="/tags" %>
<!DOCTYPE HTML>
<html class='frm style2'>
<head>
    <title>${app} - 公积金</title>
    <link rel="shortcut icon" type="image/x-icon" href="/favicon.ico"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8 "/>
    <link rel="stylesheet" href="//static.kdz6.cn/lib/durian/main.css"/>
    <script src="/_session/domain.js"></script>
    <script src="${js}/lib.js"></script>
    <style>
        a.popup{position:relative;text-decoration: none;color: blue}
        a.popup span{display:none;position:absolute;top:-40px;left:150px;z-index: 99;border: 1px solid;border-radius: 5px; padding:4px;width:200px;}
        a.popup:hover span{display:block;}
    </style>
</head>
<body>
<ct:header/>
<main class="sml">
    <ct:toolmenu pack="panda_user_d"></ct:toolmenu>
    <detail>
        <section>
            <header>
                <h2>
                    <c:forEach var="m" items="${funds}">
                        <a style="<c:if test="${region!=m.region}">color:#666666;</c:if> cursor: pointer;text-decoration: underline" onclick="reload('${m.region}');" id="hidden">${m.region}公积金</a>&nbsp;&nbsp;
                    </c:forEach>
                </h2>
            </header>
        </section>

        <section>
            <header>
                <h2>基本信息</h2>
            </header>
            <article>
                <table>
                    <tr>
                        <td>姓名:</td>
                        <td>${fund.getHideName()}</td>
                    </tr>
                    <tr>
                        <td>身份证号:</td>
                        <td>${fund.getHideIdCode()}</td>
                    </tr>
                    <tr>
                        <td>手机号:</td>
                        <td>${fund.mobile}</td>
                    </tr>
                    <tr>
                        <td>单位名称:</td>
                        <td>${fund.corporation_name}</td>
                    </tr>
                    <tr>
                        <td>缴存基数:</td>
                        <td>${fund.base_number}</td>
                    </tr>
                    <tr>
                        <td>账户余额:</td>
                        <td>${fund.balance}</td>
                    </tr>
                    <tr>
                        <td>缴存状态:</td>
                        <td>${fund.getStatusName()}</td>
                    </tr>
                    <c:if test="${fund.row_id>0}">
                        <tr>
                            <td style="color: red">认证来源:</td>
                            <td style="color: red">${from}</td>
                        </tr>
                    </c:if>
                </table>
            </article>
        </section>

        <section>
            <header>
                <h2>缴存记录</h2>
            </header>
            <article>
                <iframe src="/auth/query_fund_payment?open_id=${open_id}&region=${region}" frameborder="0" width="100%" height="520px"></iframe>
            </article>
        </section>

        <section>
            <header>
                <h2>还款记录</h2>
            </header>
            <article>
                <iframe src="/auth/query_fund_repay?open_id=${open_id}&region=${region}" frameborder="0" width="100%" height="520px"></iframe>
            </article>
        </section>

    </detail>
</main>
</body>
<script>
    function reload(region) {
        window.location.href = changeURLArg(window.location.href,'region',region)
    };

    function changeURLArg(url,arg,arg_val){
        var pattern=arg+'=([^&]*)';
        var replaceText=arg+'='+arg_val;
        if(url.match(pattern)){
            var tmp='/('+ arg+'=)([^&]*)/gi';
            tmp=url.replace(eval(tmp),replaceText);
            return tmp;
        }else{
            if(url.match('[\?]')){
                return url+'&'+replaceText;
            }else{
                return url+'?'+replaceText;
            }
        }
    };
</script>
</html>
