<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="ct" uri="/tags" %>
<!DOCTYPE HTML>
<html class="frm10">
<head>
    <title>${app} - 用户列表</title>
    <link rel="shortcut icon" type="image/x-icon" href="/favicon.ico"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8 "/>
    <link rel="stylesheet" href="${css}/main.css"/>
    <script src="/_session/domain.js"></script>
    <script src="${js}/lib.js"></script>
    <script src="${js}/layer.js"></script>
    <script src="${js}/laydate/laydate.js"></script>
    <style>
        table a{
            color: blue;
        }
        .date{
            width: 95px;
            padding-left: 3px;
        }
    </style>
</head>
<body>
<main>
    <toolbar>
        <cell>
            <form>
                <input type="text" value="${mobile}" name="mobile" id="mobile" placeholder="输入手机号查询" style="width: 140px"/>
                <input type="text" value="${id_name}" name="id_name" id="id_name" placeholder="输入姓名查询" style="width: 115px"/>
                <select name="agroup_id">
                    <option value="0" <c:if test="${agroup_id==0}">selected</c:if> >全部平台</option>
                    <c:forEach var="m" items="${agroups}">
                        <option value="${m.value}" <c:if test="${agroup_id==m.value}">selected</c:if> >${m.key}</option>
                    </c:forEach>
                </select>
                <select name="is_real">
                    <option value="-1" <c:if test="${is_real==-1}">selected</c:if>>全部</option>
                    <option value="0" <c:if test="${is_real==0}">selected</c:if>>未实名</option>
                    <option value="1" <c:if test="${is_real==1}">selected</c:if>>已实名</option>
                    <option value="2" <c:if test="${is_real==2}">selected</c:if>>待补全</option>
                </select>
                <select name="has_face">
                    <option value="-1"  <c:if test="${has_face==-1}">selected</c:if>>全部</option>
                    <option value="1" <c:if test="${has_face==1}">selected</c:if>>有人脸</option>
                    <option value="0" <c:if test="${has_face==0}">selected</c:if>>无人脸</option>
                </select>&nbsp;&nbsp;
                <input type="text" value="${date_start}" id="date_start" name="date_start" placeholder="开始注册时间" autocomplete="off" class="date"/>
                - <input type="text" value="${date_end}" id="date_end" name="date_end" placeholder="结束注册时间" autocomplete="off" class="date"/>&nbsp;&nbsp;
                <input type="text" value="${real_date}" id="real_date" name="real_date" placeholder="实名日期" autocomplete="off" class="date"/>
                &nbsp;&nbsp;
                <button type="submit">查询</button>
            </form>
        </cell>
    </toolbar>

    <datagrid>
        <table>
            <thead>
            <tr>
                <td width="140px">手机号</td>
                <td width="130px">姓名</td>
                <td>平台</td>
                <td width="120px">身份证</td>
                <td width="120px">人脸</td>
                <td width="100px">最早注册日期</td>
                <td width="95px">实名日期</td>
                <td width="90px">操作</td>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="m" items="${list}" varStatus="stat">
                <tr>
                    <td>${m.mobile}</td>
                    <td>
                        <c:if test="${is_hide==1}">
                            <c:if test="${empty m.id_name}">-</c:if><c:if test="${!empty m.id_name}">${m.hideName(m.id_name)}</c:if>
                        </c:if>
                        <c:if test="${is_hide!=1}">
                            <c:if test="${empty m.id_name}">-</c:if><c:if test="${!empty m.id_name}">${m.id_name}</c:if>
                        </c:if>
                    </td>
                    <td>${m.platform}<a style="color: blue;cursor: pointer" onclick="morePlat('${m.mobile}','plat${stat.index}')" id="plat${stat.index}">&nbsp;&nbsp;(更多)</a></td>
                    <td>${m.getReal()}</td>
                    <td>
                        <c:if test="${m.similar_times>0}">有</c:if>
                        <c:if test="${m.similar_times<=0}">无</c:if>
                    </td>
                    <td>${fn:substring(m.register_date, 0, 4)}-${fn:substring(m.register_date, 4, 6)}-${fn:substring(m.register_date, 6, 8)}</td>
                    <td>
                        <c:if test="${empty m.real_fulltime}">-</c:if>
                        <c:if test="${!empty m.real_fulltime}"><fmt:formatDate value="${m.real_fulltime}" pattern="yyyy-MM-dd"/></c:if>
                    </td>
                    <td><a href="/auth/query_base?open_id=${m.open_id}&mobile=${m.mobile}&ugroup_id=${m.ugroup_id}" target="_blank">查看详情</a></td>
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
        elem: '#date_start',
        format:'yyyyMMdd',
        theme: '#189aca'
    });
    laydate.render({
        elem: '#date_end',
        format:'yyyyMMdd',
        theme: '#189aca'
    });
    laydate.render({
        elem: '#real_date',
        format:'yyyyMMdd',
        theme: '#189aca'
    });

    function morePlat(mobile,id) {
        $.ajax({
            type:"POST",
            url:"/auth/list/ajax/getPlatform",
            data:{
                "mobile":mobile
            },
            success: function(data){
                layer.tips(data, '#'+id);
            }
        });

    };
</script>
</html>