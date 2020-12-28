<%@ page import="sun.security.util.Length" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib prefix="ct" uri="/tags" %>
<!DOCTYPE HTML>
<html class='frm style2'>
<head>
    <title>${app} - 网银</title>
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
                    <c:forEach var="m" items="${banks}">
                        <a style="<c:if test="${bank_name!=m.bank_name}">color:#666666;</c:if> cursor: pointer;text-decoration: underline" onclick="reload('${m.bank_name}');" id="hidden">${m.bank_name}</a>&nbsp;&nbsp;
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
                        <td>${report.name}</td>
                    </tr>
                    <tr>
                        <td>证件号码:</td>
                        <td>${report.certify_no}</td>
                    </tr>
                    <tr>
                        <td>手机号:</td>
                        <td>${report.mobile}</td>
                    </tr>
                    <tr>
                        <td>家庭地址:</td>
                        <td>${report.address}</td>
                    </tr>
                    <tr>
                        <td>邮箱:</td>
                        <td>${report.email}</td>
                    </tr>
                    <tr>
                        <td>工作单位:</td>
                        <td>${report.last_company_name}</td>
                    </tr>
                    <c:if test="${!empty report.name}">
                        <tr>
                            <td style="color: red">认证来源:</td>
                            <td style="color: red">${from}</td>
                        </tr>
                    </c:if>
                    <tr>
                        <td>
                            <a style="color: #00CC99;cursor:pointer;text-decoration: underline" href="/auth/query_bank_dcard_report?open_id=${open_id}&bank_name=${bank_name}" target="_blank">详细报告(借记卡)</a>
                        </td>
                        <td>
                            <a style="color: #00CC99;cursor:pointer;text-decoration: underline" href="/auth/query_bank_ccard_report?open_id=${open_id}&bank_name=${bank_name}" target="_blank">详细报告(信用卡)</a>
                        </td>
                    </tr>
                </table>
            </article>
        </section>

        <section>
            <header>
                <h2>借记卡信息</h2>
            </header>
            <article>
                <datagrid>
                    <table>
                        <thead>
                        <tr>
                            <td width="150px">银行卡</td>
                            <td width="100px">持卡人</td>
                            <td width="100px" class="right">借记卡余额</td>
                            <td width="100px">定期类型</td>
                            <td width="150px">计息日期</td>
                            <td width="150px">到期日期</td>
                            <td width="100px">存期(月)</td>
                            <td width="150px" class="right">利率</td>
                            <td width="150px">交易记录</td>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="m" items="${bills.dcard}">
                            <tr>
                                <td class="center">${m.bank_name}(尾号${m.card_num})</td>
                                <td class="center">${m.name_on_card}</td>
                                <td class="right">${m.balance}</td>
                                <td class="center">${m.deposit_type}</td>
                                <td class="center">${m.deposit_date}</td>
                                <td class="center">${m.due_date}</td>
                                <td class="center">${m.period}</td>
                                <td class="right">${m.interest}</td>
                                <td class="center"><a style="color: blue;cursor: pointer" href="/auth/query_bank_dcard_shop?open_id=${open_id}&bank_name=${m.bank_name}&card_num=${m.card_num}">查看</a></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </datagrid>
            </article>
        </section>

        <section>
            <header>
                <h2>信用卡信息</h2>
            </header>
            <article>
                <datagrid>
                    <table>
                        <thead>
                        <tr>
                            <td width="150px">银行卡</td>
                            <td width="150px" class="right">信用卡额度</td>
                            <td width="150px">账单日</td>
                            <td width="150px">还款日</td>
                            <td width="150px" class="right">本期账单金额</td>
                            <td width="150px" class="right">最低还款额</td>
                            <td width="150px" class="right">上期账单金额</td>
                            <td width="150px" class="right">上期还款金额</td>
                            <td width="150px">操作</td>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="m" items="${bills.ccard}">
                            <tr>
                                <td class="center">${m.bank_name}(尾号${m.card_num})</td>
                                <td class="right">${m.balance}</td>
                                <td class="center">${m.bill_date}</td>
                                <td class="center">${m.payment_due_date}</td>
                                <td class="right">${m.current_bill_amt}</td>
                                <td class="right">${m.current_bill_remain_min_payment}</td>
                                <td class="right">${m.last_balance}</td>
                                <td class="right">${m.last_payment}</td>
                                <td class="center"><a style="color: blue;cursor: pointer" href="/auth/query_bank_ccard_bill?open_id=${open_id}&bank_name=${m.bank_name}&card_num=${m.card_num}">查看详细账单</a></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </datagrid>
            </article>
        </section>

    </detail>
</main>
</body>
<script>
    function reload(bank_name) {
        window.location.href = changeURLArg(window.location.href,'bank_name',bank_name)
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
