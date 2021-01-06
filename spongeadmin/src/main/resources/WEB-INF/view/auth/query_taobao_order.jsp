<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ct" uri="/tags" %>
<!DOCTYPE HTML>
<html class='frm10'>
<head>
    <title>${app} - 淘宝订单</title>
    <link rel="shortcut icon" type="image/x-icon" href="/favicon.ico"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8 "/>
    <link rel="stylesheet" href="//static.kdz6.cn/lib/durian/main.css"/>
    <script src="/_session/domain.js"></script>
    <script src="${js}/lib.js"></script>
    <script src="${js}/laydate/laydate.js"></script>
    <style type="text/css">
        a.popup{position:relative;text-decoration: none}
        a.popup span{display:none;position:absolute;top:-40px;left:150px;z-index: 99;border: 1px solid; padding:4px;width:200px;}
        a.popup:hover span{display:block;}

        a.popup1{position:relative;text-decoration: none}
        a.popup1 span{display:none;position:absolute;top:-30px;left:20px;z-index: 99;border: 1px solid; padding:4px;width:50px;}
        a.popup1:hover span{display:block;}

        a.popup2{position:relative;text-decoration: none}
        a.popup2 span{display:none;position:absolute;top:-30px;left:-170px;z-index: 99;border: 1px solid; padding:4px;width:50px;}
        a.popup2:hover span{display:block;}
    </style>
</head>
<body>
<toolbar>
    <cell>
        <form>
            时间：<input type="text" value="${date_start}" id="date_start" name="date_start" placeholder="开始时间" style="width: 153px;padding-left: 3px"/>
            - <input type="text" value="${date_end}" id="date_end" name="date_end" placeholder="结束时间" style="width: 153px;padding-left: 3px"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            订单状态：
            <select name="order_status">
                <option value="全部" <c:if test="${order_status=='全部'}">selected</c:if>>全部</option>
                <option value="交易已付款" <c:if test="${order_status=='交易已付款'}">selected</c:if> >交易已付款</option>
                <option value="交易未付款" <c:if test="${order_status=='交易未付款'}">selected</c:if>>交易未付款</option>
                <option value="售后退货" <c:if test="${order_status=='售后退货'}">selected</c:if>>售后退货</option>
                <option value="交易失败" <c:if test="${order_status=='交易失败'}">selected</c:if>>交易失败</option>
                <option value="交易成功" <c:if test="${order_status=='交易成功'}">selected</c:if>>交易成功</option>
                <option value="未知" <c:if test="${order_status=='未知'}">selected</c:if>>未知</option>
            </select>
            <input type="text" value="${mobile}" id="mobile" name="mobile" style="display: none"/>
            <input type="text" value="${ugroup}" id="ugroup" name="ugroup" style="display: none"/>&nbsp;&nbsp;
            <button type="submit" onclick="">查询</button>
        </form>
    </cell>
</toolbar>
<datagrid>
    <table>
        <thead>
        <tr>
            <td>订单号</td>
            <td class="left">商品名称</td>
            <td class="right">商品单价</td>
            <td>商品数量</td>
            <td class="right">订单金额</td>
            <td>订单类型</td>
            <td>订单时间</td>
            <td class="left">商铺名称</td>
            <td>收货人</td>
            <td>收货地址</td>
            <td>手机号/固话</td>
            <td>发票抬头</td>
            <td>发票类型</td>
            <td>发票内容</td>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="m" items="${orders}">
            <tr>
                <td>${m.order_id}</td>
                <td class="left">
                    <a href="#" class="popup" >${fn:substring(m.product_name, 0, 10)}...
                        <span style="background-color:#eaedf1;color:black;width: 340px;height:200px;">${m.productAll}</span>
                    </a>
                </td>
                <td class="right"><fmt:formatNumber type="number" value="${m.product_price/100}" pattern="0.00" maxFractionDigits="2"/></td>
                <td>${m.product_amount}</td>
                <td class="right"><fmt:formatNumber type="number" value="${m.order_amount/100}" pattern="0.00" maxFractionDigits="2"/></td>
                <td>${m.order_type}</td>
                <td>${fn:substring(m.order_time, 0, 10)}</td>
                <td class="left">
                    <c:if test="${!empty m.order_shop}">${fn:substring(m.order_shop, 0, 11)}</c:if>
                    <c:if test="${empty m.order_shop}">-</c:if>
                </td>
                <td>
                    <c:if test="${m.receiver_name!='未知'}">
                        ${fn:substring(m.receiver_name, 0, 1)}**
                    </c:if>
                    <c:if test="${m.receiver_name=='未知'}">-</c:if>
                </td>
                <td>
                    <c:if test="${!empty m.receiver_addr}">
                        ${fn:substring(m.receiver_addr, 0, 3)}***
                    </c:if>
                    <c:if test="${empty m.receiver_addr}">-</c:if>
                </td>
                <td>${fn:substring(m.receiver_mobile, 0, 3)}***</td>
                <td>
                    <a href="#" class="popup1" >${fn:substring(m.receipt_title, 0, 4)}
                        <span style="background-color:#eaedf1;color:black;width: 150px;height:20px;">${m.receipt_title}</span>
                    </a>
                </td>
                <td>${m.receipt_type}</td>
                <td>
                    <a href="#" class="popup2" >${fn:substring(m.receipt_content, 0, 4)}
                        <span style="background-color:#eaedf1;color:black;width: 340px;height:200px;">${m.receipt_content}</span>
                    </a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</datagrid>
<ct:pagebar pageSize="${pageSize}" rowCount="${rowCount}"/>
</body>
<script>
    laydate.render({
        elem: '#date_start',
        format:'yyyyMMdd',
        theme: '#189aca'
    });
    laydate.render({
        elem: '#date_end',
        format:'yyyyMMdd',
        theme: '#189aca'
    });
</script>
</html>