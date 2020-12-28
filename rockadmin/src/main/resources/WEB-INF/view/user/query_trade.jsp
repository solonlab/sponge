<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ct" uri="/tags" %>
<!DOCTYPE HTML>
<html class="frm style2">
<head>
    <title>${app} 交易记录</title>
    <link rel="shortcut icon" type="image/x-icon" href="/favicon.ico"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8 "/>
    <link rel="stylesheet" href="${css}/main.css"/>
    <script src="/_session/domain.js"></script>
    <script src="${js}/lib.js"></script>
    <script src="${js}/layer.js"></script>
</head>
<body>
<ct:header/>
<main class="sml">
    <ct:toolmenu pack="sponge_user_d"></ct:toolmenu>
    <detail>
        <section>
            <header><h2>取现记录</h2></header>
            <article>
            <datagrid>
                <table>
                    <thead>
                    <tr>
                        <th width="200">订单号</th>
                        <th>支付类型</th>
                        <th>银行名称</th>
                        <th>取现额度</th>
                        <th width="200">记录时间</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="m" items="${hold}">
                        <tr>
                            <td>${m.order_no}</td>
                            <td>${m.pay_type}</td>
                            <td>${m.ccard_bank}</td>
                            <td>${m.order_pay_amount}</td>
                            <td>${m.log_fulltime}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </datagrid>
            </article>
        </section>

        <section>
            <header><h2>小贷记录</h2></header>
            <article>
            <datagrid>
                <table>
                    <thead>
                    <tr>
                        <th width="200">订单号</th>
                        <th>借贷额度</th>
                        <th>放款时间</th>
                        <th>订单状态</th>
                        <th width="200">创建时间</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="m" items="${beauty}">
                        <tr>
                            <td>${m.order_no}</td>
                            <td>${m.loan_val}</td>

                            <td>${m.give_time}</td>
                            <td>${m.stats}</td>
                            <td>${m.create_time}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </datagrid>
            </article>
        </section>

        <section>
            <header><h2>借条记录</h2></header>
            <article>
            <datagrid>
                <table>
                    <thead>
                    <tr>
                        <th width="200">订单号</th>
                        <th>借款金额<br/>(实际)</th>
                        <th>还款日期</th>
                        <th>逾期<br/>天数</th>
                        <th>逾期利息</th>
                        <th>应还金额</th>
                        <th>已还金额</th>
                        <th>剩余未还</th>
                        <th>订单状态</th>
                        <th width="200">确认时间</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="m" items="${beast}">
                        <tr>
                            <td>${m.iou_no}</td>
                            <td><fmt:formatNumber type="number" value="${m.amount/100}" pattern="0.00"/></td>
                            <td>${m.repay_date}</td>
                            <td>${m.overdue_days}</td>
                            <!--逾期利息-->
                            <td>${m.overdue_rate}%</td>
                            <td><fmt:formatNumber type="number" value="${m.shoupaymoney/100}" pattern="0.00"/></td>
                            <td><fmt:formatNumber type="number" value="${m.hasreurn/100}" pattern="0.00"/></td>
                            <td><fmt:formatNumber type="number" value="${m.repay_amount/100}" pattern="0.00"/></td>
                            <td>${m.statusName}</td>
                            <td>
                                <c:if test="${empty m.confirm_fulltime}"> - </c:if>
                                <c:if test="${!empty m.confirm_fulltime}">${m.confirm_fulltime}</c:if>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </datagrid>
            </article>
        </section>
    </detail>
</main>
<ct:footer/>
</body>
</html>