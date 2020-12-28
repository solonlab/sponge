<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ct" uri="/tags" %>
<!DOCTYPE HTML>
<html class="frm style2">
<head>
    <title>${app} - 运营商认证</title>
    <link rel="shortcut icon" type="image/x-icon" href="/favicon.ico"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8 "/>
    <link rel="stylesheet" href="${css}/main.css"/>
    <script src="/_session/domain.js"></script>
    <script src="${js}/lib.js"></script>
    <script>
        function isShow() {
            $('#bill_table').toggle();
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
    <ct:toolmenu pack="panda_user_d"></ct:toolmenu>
    <detail>
        <section>
            <header>
                <h2>运营商认证</h2>
            </header>
            <article>
                <table>
                    <tr>
                        <th>手机号</th>
                        <td width="150px">${operatorBase.mobile}</td>
                        <th>身份证号</th>
                        <td width="150px;">${operatorBase.id_card}</td>
                        <th>最近更新时间</th>
                        <td><fmt:formatDate value="${operatorBase.update_fulltime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    </tr>
                    <tr>
                        <th>姓名</th>
                        <td >
                            <c:if test="${is_hide==1}">${operatorBase.hideName(operatorBase.name)}</c:if>
                            <c:if test="${is_hide!=1}">${operatorBase.name}</c:if>
                        </td>
                        <th>状态</th>
                        <td >${operatorBase.getState()}</td>
                        <th>首次提交时间</th>
                        <td><fmt:formatDate value="${operatorBase.create_fulltime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    </tr>
                    <tr>
                        <th>账单获取情况</th>
                        <td></td>
                        <th>运营商类型</th>
                        <c:if test="${operatorBase.phone_num_type==1}">
                            <td>联通</td>
                        </c:if>
                        <c:if test="${operatorBase.phone_num_type==2}">
                            <td>移动</td>
                        </c:if>
                        <c:if test="${operatorBase.phone_num_type==3}">
                            <td>电信</td>
                        </c:if>
                        <c:if test="${operatorBase.phone_num_type==0}">
                            <td></td>
                        </c:if>

                        <th style="color: red">认证来源</th>
                        <td id="from" style="color: red">${from}</td><td/>
                    </tr>
                </table>
            </article>
        </section>

        <section>
            <header>
                <h2>账单信息</h2>
            </header>
            <article>
                <table id="bill_table">
                    <thead>
                    <th>账单月份</th>
                    <th>账单起始日</th>
                    <th>账单结束日</th>
                    <th>账单消费(元)</th>
                    </thead>
                    <tbody>
                    <c:forEach var="bill" items="${operator_bill}">
                        <tr>
                            <td>${bill.bill_month}</td>
                            <td>${bill.bill_start_date}</td>
                            <td>${bill.bill_end_date}</td>
                            <td>${bill.bill_fee}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </article>
        </section>

        <section>
            <header><h2>征信数据:</h2></header>
            <article>
                <table>
                    <tr>
                        <th style="width: 150px;">注册时间</th>
                        <td width="90px">${operatorCredit.register_date}</td>
                        <th style="width: 150px;">有联系电话号码数</th>
                        <td width="90px">${operatorCredit.getFriendsTotalCount()}</td>
                        <th style="width: 150px;">有通话总天数</th>
                        <td width="70px">${operatorCredit.getTotalCallDayCount()}</td>
                    </tr>
                    <tr>
                        <th>通话总次数</th>
                        <td >${operatorCredit.contact_total_count}</td>
                        <th>通话总时长</th>
                        <td>${operatorCredit.contact_total_time}</td>
                        <th>通话平均时长</th>
                        <td >${operatorCredit.average_time}</td>
                    </tr>
                    <tr>
                        <th>一个月内通话总次数</th>
                        <td >${operatorCredit.call_cnt_one_month}</td>
                        <th>一个月内呼入总次数</th>
                        <td >${operatorCredit.call_cnt_in_one_month}</td>
                        <th>一个月内呼出总次数</th>
                        <td >${operatorCredit.call_cnt_out_one_month}</td>
                    </tr>
                    <tr>
                        <th>三个月内通话总次数</th>
                        <td>${operatorCredit.call_cnt_three_month}</td>
                        <th>呼入呼出匹配情况</th>
                        <td>${operatorCredit.getUsualPhoneEvalValue()}</td>
                        <th></th>
                        <td></td>
                    </tr>
                </table>
            </article>
        </section>
        <section>
            <header><h2>主要联系人:</h2></header>
            <article>
                <table>
                    <tr>
                        <th width="100px">关系</th>
                        <c:if test="${userLinkMan.contact_e1_type==0}">
                            <td width="100px">父亲</td>
                        </c:if>
                        <c:if test="${userLinkMan.contact_e1_type==1}">
                            <td width="100px">母亲</td>
                        </c:if>
                        <c:if test="${userLinkMan.contact_e1_type==2}">
                            <td width="100px">配偶</td>
                        </c:if>
                        <c:if test="${userLinkMan.contact_e1_type==3}">
                            <td width="100px">子女</td>
                        </c:if>
                        <th width="100px">联系人姓名</th>
                        <td width="100px">
                            <c:if test="${is_hide==1}">${userLinkMan.hideName(userLinkMan.contact_e1_name)}</c:if>
                            <c:if test="${is_hide!=1}">${userLinkMan.contact_e1_name}</c:if>
                        </td>
                        <th width="100px">手机号</th>
                        <td width="100px">
                            <c:if test="${is_hide==1}">${userLinkMan.hideMobile(userLinkMan.contact_e1_mobile)}</c:if>
                            <c:if test="${is_hide!=1}">${userLinkMan.contact_e1_mobile}</c:if>
                        </td>
                        <th width="100px">主叫/被叫</th>
                        <td width="100px">-</td>
                    </tr>
                </table>
            </article>
        </section>

        <section>
            <header><h2>其他联系人</h2></header>
            <article>
                <table>
                    <tr>
                        <th width="100px">关系</th>
                        <c:if test="${userLinkMan.contact_e2_type==0}">
                            <td width="100px">其他亲属</td>
                        </c:if>
                        <c:if test="${userLinkMan.contact_e2_type==1}">
                            <td width="100px">同事</td>
                        </c:if>
                        <c:if test="${userLinkMan.contact_e2_type==2}">
                            <td width="100px">朋友</td>
                        </c:if>

                        <th width="100px">联系人姓名</th>
                        <td width="100px">
                            <c:if test="${is_hide==1}">${userLinkMan.hideName(userLinkMan.contact_e2_name)}</c:if>
                            <c:if test="${is_hide!=1}">${userLinkMan.contact_e2_name}</c:if>
                        </td>
                        <th width="100px">手机号</th>
                        <td width="100px">
                            <c:if test="${is_hide==1}">${userLinkMan.hideMobile(userLinkMan.contact_e2_mobile)}</c:if>
                            <c:if test="${is_hide!=1}">${userLinkMan.contact_e2_mobile}</c:if>
                        </td>
                        <th width="100px">主叫/被叫</th>
                        <td width="100px">-</td>
                    </tr>
                </table>
                <br/>

            </article>
        </section>
        <section>
            <header><h2>通话记录</h2></header>
            <article>
                <iframe src="/auth/query_operator/query_operator_inner?open_id=${open_id}" frameborder="0" id="table" width="100%" height="550px"></iframe>
            </article>

        </section>
    </detail>
</main>
</body>
</html>