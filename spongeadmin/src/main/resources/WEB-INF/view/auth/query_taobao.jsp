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
</head>
<body>
<ct:header/>
<main class="sml">
    <ct:toolmenu pack="panda_user_d"></ct:toolmenu>
    <detail>
        <section>
            <header>
                <h2>基本信息</h2>
            </header>
            <article>
                <table>
                    <tr>
                        <th>手机号：</th>
                        <td width="150px">${base.mobile}</td>
                        <th>姓名：</th>
                        <td width="150px;">${base.name}</td>
                        <th>用户名：</th>
                        <td width="150px;">${base.user_name}</td>
                        <c:if test="${!empty base.mobile}">
                            <th style="color: red">认证来源：</th>
                            <td width="150px;" style="color: red">${from}</td>
                        </c:if>

                    </tr>
                    <tr>
                        <th>邮箱：</th>
                        <td>${base.email}</td>
                        <th>用户级别：</th>
                        <td>${base.user_level}</td>
                        <th>淘气值：</th>
                        <td width="150px;">${base.vip_count}</td>
                    </tr>
                    <tr>
                        <th>账户余额：</th>
                        <td><fmt:formatNumber type="number" value="${account.account_balance/100}" pattern="0.00" maxFractionDigits="2"/></td>
                        <th>金融账户余额：</th>
                        <td><fmt:formatNumber type="number" value="${account.financial_balance/100}" pattern="0.00" maxFractionDigits="2"/></td>
                        <th style="color: #00CC99">芝麻分：</th>
                        <td width="150px;" style="color: #00CC99">${account.zhima_point}</td>
                    </tr>
                </table>
            </article>
        </section>

        <section>
            <header>
                <h2>收货地址</h2>
            </header>
            <article>
                <datagrid>
                    <table>
                        <thead>
                        <tr>
                            <td>收货人</td>
                            <td>地区</td>
                            <td>地址</td>
                            <td>手机号</td>
                            <td>固定电话</td>
                            <td>邮编</td>
                        </tr>
                        </thead>
                        <c:forEach var="m" items="${data.receiver_list}">
                            <tr>
                                <td class="center">${fn:substring(m.name, 0, 1)}**</td>
                                <td class="center">${m.area}</td>
                                <td class="center">***</td>
                                <td class="center">${fn:substring(m.mobile, 0, 3)}********</td>
                                <td class="center">
                                    <c:if test="${m.telephone=='未知'}">-</c:if>
                                    <c:if test="${m.telephone!='未知'}">${m.telephone}</c:if>
                                </td>
                                <td class="center">${m.zip_code}</td>
                            </tr>
                        </c:forEach>
                    </table>
                </datagrid>
            </article>
        </section>

        <section>
            <header>
                <h2>花呗信息</h2>
            </header>
            <article>
                <datagrid>
                    <table>
                        <thead>
                        <tr>
                            <th>花呗信息用额度</th>
                            <th>花呗已消费额度</th>
                            <th>花呗可用额度</th>
                            <th>花呗当前逾期状态</th>
                            <th>花呗当前逾期罚息</th>
                            <th>花呗历史逾期次数</th>
                            <th>花呗历史逾期罚息</th>
                        </tr>
                        </thead>
                        <tr>
                            <td class="center"><fmt:formatNumber type="number" value="${account.credit_quota/100}" pattern="0.00" maxFractionDigits="2"/></td>
                            <td class="center"><fmt:formatNumber type="number" value="${account.consume_quota/100}" pattern="0.00" maxFractionDigits="2"/></td>
                            <td class="center"><fmt:formatNumber type="number" value="${account.available_quota/100}" pattern="0.00" maxFractionDigits="2"/></td>
                            <td class="center" <c:if test="${account.getHuabeiOverdueStatus()=='逾期'}">style="color: red;"</c:if>>${account.getHuabeiOverdueStatus()}</td>
                            <td class="center" <c:if test="${account.huabei_curreny_penalty>0}">style="color: red;"</c:if>>
                                <fmt:formatNumber type="number" value="${account.huabei_curreny_penalty/100}" pattern="0.00" maxFractionDigits="2"/>
                            </td>
                            <td class="center" <c:if test="${account.huabei_overdue_history>0}">style="color: red;"</c:if>>${account.huabei_overdue_history}</td>
                            <td class="center" <c:if test="${account.huabei_history_penalty>0}">style="color: red;"</c:if>>
                                <fmt:formatNumber type="number" value="${account.huabei_history_penalty/100}" pattern="0.00" maxFractionDigits="2"/>
                            </td>
                        </tr>
                    </table>
                </datagrid>
                <br/>

                <datagrid>
                    <table>
                        <thead>
                        <tr>
                            <th>账单年月</th>
                            <th>账单总额</th>
                            <th>账单状态</th>
                            <th>记账周期</th>
                            <th>出账日期</th>
                            <th>剩余应还</th>
                            <th>最低应还</th>
                            <th>退款金额</th>
                            <th>已还金额</th>
                            <th>最后还款日期</th>
                        </tr>
                        </thead>
                        <tr>
                            <c:forEach items="${data.huabei_bill_list}" var="m">
                                <td class="center">${m.quato}</td>
                                <td class="center">${m.quato}</td>
                                <td class="center">${m.quato}</td>
                                <td class="center">${m.quato}</td>
                                <td class="center">${m.quato}</td>
                                <td class="center">${m.quato}</td>
                                <td class="center">${m.quato}</td>
                                <td class="center">${m.quato}</td>
                                <td class="center">${m.quato}</td>
                                <td class="center">${m.quato}</td>
                            </c:forEach>
                        </tr>
                    </table>
                </datagrid>

            </article>
        </section>

        <section>
            <header>
                <h2>借呗信息</h2>
            </header>
            <article>
                <datagrid>
                    <table>
                        <thead>
                        <tr>
                            <th>借呗额度</th>
                            <th>借呗已用额度</th>
                            <th>借呗可借额度</th>
                            <th>借呗有效额度</th>
                            <th>借呗当前逾期状态</th>
                            <th>借呗历史逾期未还清次数</th>
                        </tr>
                        </thead>
                        <tr>
                            <td class="center"><fmt:formatNumber type="number" value="${account.jiebei_quota/100}" pattern="0.00" maxFractionDigits="2"/></td>
                            <td class="center"><fmt:formatNumber type="number" value="${account.jiebei_consume_quota/100}" pattern="0.00" maxFractionDigits="2"/></td>
                            <td class="center"><fmt:formatNumber type="number" value="${account.jiebei_available_quota/100}" pattern="0.00" maxFractionDigits="2"/></td>
                            <td class="center"><fmt:formatNumber type="number" value="${account.jiebei_valid_quota/100}" pattern="0.00" maxFractionDigits="2"/></td>
                            <td class="center" <c:if test="${account.getJiebeiOverdueStatus()=='逾期'}">style="color: red;"</c:if>>${account.getJiebeiOverdueStatus()}</td>
                            <td class="center" <c:if test="${account.jiebei_overdue_history>0}">style="color: red;"</c:if>>${account.jiebei_overdue_history}</td>
                        </tr>
                    </table>
                </datagrid>
            </article>
        </section>

        <section >
            <header>
                <h2>我的收藏<a style="color: blue;cursor: pointer" onclick="isShow(0);" id="hidden">（展开）</a></h2>
            </header>
            <article id="collection" style="display: none">
                <h3 style="color:#00c1de">店铺列表</h3>
                <datagrid>
                    <table>
                        <thead>
                        <tr>
                            <th>店铺名称</th>
                            <th>店铺分类</th>
                            <th>店铺标签</th>
                        </tr>
                        </thead>
                        <c:forEach items="${data.favor_shop_list}" var="m">
                            <tr>
                                <td class="center">${m.shop_name}</td>
                                <td class="center">${m.shop_type}</td>
                                <td class="center">${m.shop_label}</td>
                            </tr>
                        </c:forEach>
                    </table>
                </datagrid>
                <br/>

                <h3 style="color:#00c1de">宝贝列表<a style="color: blue;cursor: pointer" onclick="isShow(3);" id="hidden3">（展开）</a></h3>
                <datagrid id="goods" style="display: none">
                    <table>
                        <thead>
                        <tr>
                            <td class="left">宝贝名称</td>
                            <td class="right">宝贝价格</td>
                            <td>宝贝标签</td>
                        </tr>
                        </thead>
                        <c:forEach items="${data.favor_good_list}" var="m">
                            <tr>
                                <td class="left">${m.good_name}</td>
                                <td class="right" width="100px"><fmt:formatNumber type="number" value="${m.good_price/100}" pattern="0.00" maxFractionDigits="2"/></td>
                                <td class="center" width="100px">${m.good_label}</td>
                            </tr>
                        </c:forEach>
                    </table>
                </datagrid>
                <br/>

                <h3 style="color:#00c1de">我的足迹<a style="color: blue;cursor: pointer" onclick="isShow(2);" id="hidden2">（展开）</a></h3>
                <datagrid id="foot" style="display: none">
                    <table>
                        <thead>
                        <tr>
                            <td class="left">宝贝名称</td>
                            <td class="right">宝贝价格</td>
                            <td>足迹日期</td>
                        </tr>
                        </thead>
                        <c:forEach items="${data.my_footprint}" var="m">
                            <tr>
                                <td class="left">${m.good_name}</td>
                                <td class="right" width="100px"><fmt:formatNumber type="number" value="${m.good_price/100}" pattern="0.00" maxFractionDigits="2"/></td>
                                <td class="center" width="100px">${m.foot_date}</td>
                            </tr>
                        </c:forEach>
                    </table>
                </datagrid>
            </article>
        </section>

        <section>
            <header>
                <h2>购物车<a style="color: blue;cursor: pointer" onclick="isShow(1);" id="hidden1">（展开）</a></h2>
            </header>
            <article id="shoppingcar" style="display: none">
                <datagrid>
                    <table>
                        <thead>
                        <tr>
                            <td class="left">商品名称</td>
                            <td class="left">商品型号</td>
                            <td class="right">商品单价</td>
                            <td class="right">商品数量</td>
                            <td class="left">店铺名称</td>
                        </tr>
                        </thead>
                        <c:forEach items="${data.shopping_cart}" var="m">
                            <tr>
                                <td class="left" width="500px">${m.good_name}</td>
                                <td>${m.good_model}</td>
                                <td class="right" width="100px"><fmt:formatNumber type="number" value="${m.good_price/100}" pattern="0.00" maxFractionDigits="2"/></td>
                                <td class="right" width="70px">${m.good_number}</td>
                                <td class="left">${m.shop_name}</td>
                            </tr>
                        </c:forEach>
                    </table>
                </datagrid>
            </article>
        </section>

        <section>
            <header>
                <h2>订单列表</h2>
            </header>
            <article>
                <iframe src="/auth/query_taobao_order?open_id=${open_id}" frameborder="0" id="table" width="100%" height="570px"></iframe>
            </article>
        </section>

    </detail>
</main>

</body>
<script>
    function isShow(type) {
        if (type==0){
            $('#collection').toggle();
            var text = $("#hidden").text();
            if (text == '（收起）') {
                $("#hidden").text("（展开）");
            } else {
                $("#hidden").text("（收起）");
            }
        }
        if (type==1){
            $('#shoppingcar').toggle();
            var text = $("#hidden1").text();
            if (text == '（收起）') {
                $("#hidden1").text("（展开）");
            } else {
                $("#hidden1").text("（收起）");
            }
        }

        if (type==2){
            $('#foot').toggle();
            var text = $("#hidden2").text();
            if (text == '（收起）') {
                $("#hidden2").text("（展开）");
            } else {
                $("#hidden2").text("（收起）");
            }
        }

        if (type==3){
            $('#goods').toggle();
            var text = $("#hidden3").text();
            if (text == '（收起）') {
                $("#hidden3").text("（展开）");
            } else {
                $("#hidden3").text("（收起）");
            }
        }


    };

</script>
</html>
