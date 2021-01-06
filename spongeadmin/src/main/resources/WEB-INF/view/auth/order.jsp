<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ct" uri="/tags" %>
<!DOCTYPE HTML>
<html class="frm10">
<head>
    <title>${app} - 信用报告流水</title>
    <link rel="shortcut icon" type="image/x-icon" href="/favicon.ico"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8 "/>
    <link rel="stylesheet" href="${css}/main.css"/>
    <script src="/_session/domain.js"></script>
    <script src="${js}/lib.js"></script>
    <script src="${js}/laydate/laydate.js"></script>
    <script src="${js}/layer.js"></script>
    <style>
        datagrid b{color: #8D8D8D;font-weight: normal}
        .date-in{
            height: 27px;
            width:70px;
            padding-left: 2px;
        }
        .right-block{
            margin-right: 20px;
        }
        .mobile{
            width: 130px;
        }
        .order{
            width: 160px;
        }
    </style>
</head>
<body>
<main>
    <toolbar>
        <cell>
            <form>
                <input type="text" value="${mobile}" name="mobile" id="mobile" placeholder="手机号" class="right-block mobile"/>
                <input type="text" value="${order_no}" name="order_no" id="order_no" placeholder="订单编号" class="right-block order"/>
                <input type="text" value="${out_order_no}" name="out_order_no" id="out_order_no" placeholder="外部流水号" class="right-block"/>

                生成时间：
                <input  name="create_date_begin" id ="create_date_begin" placeholder="起始时间" class="date-in" value="${create_date_begin}"> -
                <input  name="create_date_end" id ="create_date_end" placeholder="结束时间" class="date-in right-block" value="${create_date_end}">

                完成时间：
                <input  name="finish_date_begin" id ="finish_date_begin" placeholder="起始时间"  class="date-in" value="${finish_date_begin}"> -
                <input  name="finish_date_end" id ="finish_date_end" placeholder="结束时间" class="date-in right-block" value="${finish_date_end}">

                <br/>
                支付方式：
                <select class="right-block" name="pay_type" id="pay_type">
                    <option value="0" >全部</option>
                    <option value="1" <c:if test="${pay_type==1}">selected</c:if>>支付宝</option>
                    <option value="2" <c:if test="${pay_type==2}">selected</c:if>>微信</option>
                </select>

                类别：
                <select class="right-block" name="type" id="type">
                    <option value="0">全部</option>
                    <option value="1" <c:if test="${type==1}">selected</c:if>>个人信用报告</option>
                    <option value="2" <c:if test="${type==2}">selected</c:if>>运营商数据报告</option>
                </select>

                订单状态：
                <select class="right-block" name="status" id="status">
                    <option value="0">全部</option>
                    <option value="1" <c:if test="${status==1}">selected</c:if>>待支付</option>
                    <option value="2" <c:if test="${status==2}">selected</c:if>>支付成功</option>
                    <option value="33" <c:if test="${status==33}">selected</c:if>>支付失败</option>
                </select>
                <button type="submit">查询</button>&nbsp;&nbsp;
            </form>
        </cell>
    </toolbar>

    <datagrid>
        <table>
            <thead>
            <tr>
                <td>订单编号</td>
                <td>外部流水号</td>
                <td>手机号</td>
                <td>支付方式</td>
                <td>类别</td>
                <td class="right">支付金额</td>
                <td width="90px">订单生成时间</td>
                <td width="90px">订单完成时间</td>
                <td>订单状态</td>
                <td>失败原因</td>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="m" items="${orders}">
                <tr>
                    <td>${m.order_no}</td>
                    <td>${m.out_order_no}</td>
                    <td>${m.mobile}</td>
                    <td>${m.getPayType()}</td>
                    <td>${m.getTypeStr()}</td>
                    <td class="right">${m.pay_amount}</td>
                    <td><fmt:formatDate value="${m.create_fulltime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    <td><fmt:formatDate value="${m.finish_fulltime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    <td>${m.getStatusStr()}</td>
                    <td>
                        <c:if test="${m.status==4||m.status==3}"><a style="color: red">${m.remark}</a></c:if>
                        <c:if test="${m.status!=4&&m.status!=3}">-</c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </datagrid>
    <ct:pagebar pageSize="${pageSize}" rowCount="${rowCount}"/>
</main>
</body>

<script>
    laydate.render({
        elem: '#create_date_begin',
        format:'yyyyMMdd',
        theme: '#189aca'
    });
    laydate.render({
        elem: '#create_date_end',
        format:'yyyyMMdd',
        theme: '#189aca'
    });
    laydate.render({
        elem: '#finish_date_begin',
        format:'yyyyMMdd',
        theme: '#189aca'
    });
    laydate.render({
        elem: '#finish_date_end',
        format:'yyyyMMdd',
        theme: '#189aca'
    });

</script>
</html>