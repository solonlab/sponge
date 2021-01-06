<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ct" uri="/tags" %>
<!DOCTYPE HTML>
<html class="frm10">
<head>
    <title>${app} - 黑名单管理</title>
    <link rel="shortcut icon" type="image/x-icon" href="/favicon.ico"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8 "/>
    <link rel="stylesheet" href="${css}/main.css"/>
    <script src="/_session/domain.js"></script>
    <script src="${js}/lib.js?v=2"></script>
    <script src="${js}/laydate/laydate.js"></script>
    <script src="${js}/layer.js"></script>
    <style>
        datagrid table tr td a{
            color: blue;
            cursor: pointer;
            text-decoration: underline;
        }
        .date-input{
            height: 26px;
            width: 75px;
            padding-left: 10px;
        }

        .btn-file {
            position: relative;
            display: inline-block;
            width: 125px;
            height: 24px;
            background: white  no-repeat  center;
            text-align: center;
            color: #fd6721;
            overflow: hidden;
            border: 1px solid #fd6721 ;
            padding-top: 0px;
            line-height:26px;
            font-size: 11px;
            top: 9px;
            margin-right: 10px;
        }

        .btn-file:hover {
            background: #fd7f38  no-repeat center;
            color: white;
        }

        .btn-file input {
            position: absolute;
            top: 0;
            left: 0;
            width: 125px;
            height: 26px;
            opacity: 0;
            filter: alpha(opacity: 0);
            cursor: pointer;
        }

    </style>
</head>
<body>
<main>
    <toolbar>
        <cell>
            <form id="query" name="query">
                <input type="text" value="${key}" name="key" id="key" placeholder="输入手机号、姓名、身份证查询" style="width: 200px"/>&nbsp;&nbsp;
                <input  name="date_begin" id ="date_begin" placeholder="起始时间" class="date-input" value="${date_begin}"> -
                <input  name="date_end" id ="date_end" placeholder="结束时间" class="date-input" value="${date_end}">&nbsp;&nbsp;
                作用应用：<select name="agroup_id" id="agroup_id">
                    <c:forEach items="${agroups}" var="agroup">
                        <option value="${agroup.agroup_id}" <c:if test="${agroup.agroup_id==agroup_id}">selected</c:if> >${agroup.agroup_name}</option>
                    </c:forEach>
                </select>&nbsp;&nbsp;
                来源：<select name="from_agroup_id" id="from_agroup_id">
                <c:forEach items="${from_agroups}" var="agroup">
                    <option value="${agroup.from_agroup_id}" <c:if test="${agroup.from_agroup_id==from_agroup_id}">selected</c:if> >${agroup.from_agroup_name}</option>
                </c:forEach>
            </select>&nbsp;&nbsp;
                <select name="reason_type" id="reason_type">
                    <option value="0">全部原因</option>
                    <option value="1" <c:if test="${reason_type==1}">selected</c:if>>调单</option>
                    <option value="2" <c:if test="${reason_type==2}">selected</c:if>>小贷</option>
                    <option value="3" <c:if test="${reason_type==3}">selected</c:if>>风控</option>
                    <option value="4" <c:if test="${reason_type==4}">selected</c:if>>用户要求</option>
                </select>
                &nbsp;&nbsp;
                <button type="submit">查询</button>&nbsp;&nbsp;&nbsp;
            </form>
            <form>
                <c:if test="${isOperator==1}">
                    <a class="btn-file" id="upload">导入(单次限1000条)<input type="file" name="file" accept=".xls"/></a>
                </c:if>
            </form>
            <form id="excel" name="form_date">
                <button type="submit" class="edit" onclick="downloadModel();">下载导入模版</button>
            </form>
        </cell>
    </toolbar>

    <datagrid>
        <table>
            <thead>
            <tr>
                <td width="50px">序号</td>
                <td width="140px">手机号</td>
                <td width="100xp">姓名</td>
                <td>身份证号码</td>
                <td>来源</td>
                <td>拉黑原因</td>
                <td width="170px">导入时间</td>
                <c:if test="${isOperator==1}"><td width="60px">操作</td></c:if>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="m" items="${blacklists}">
                <tr>
                    <td>${m.row_id}</td>
                    <td>${m.mobile}</td>
                    <td>${m.id_name}</td>
                    <td>${m.id_code}</td>
                    <td>${m.agroup_name}</td>
                    <td>${m.getReason()}</td>
                    <td><fmt:formatDate value="${m.log_fulltime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    <c:if test="${isOperator==1}"><td><a onclick="remove(${m.row_id},${m.mobile});">移除</a></td></c:if>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </datagrid>
    <ct:pagebar pageSize="${pageSize}" rowCount="${rowCount}"/>
</main>
</body>
<script>

    $(function() {
        var ua = window.navigator.userAgent;
        var isSafari = ua.indexOf("Safari") != -1 && ua.indexOf("Version") != -1;
        if(isSafari){
            $("#upload").css("top",0);
        }
    });

    laydate.render({
        elem: '#date_begin',
        format:'yyyyMMdd',
        theme: '#189aca'
    });
    laydate.render({
        elem: '#date_end',
        format:'yyyyMMdd',
        theme: '#189aca'
    });

    function remove(row_id,mobile) {
        //询问框
        top.layer.confirm('是否将 '+mobile+' 移出黑名单', {
            btn: ['确认移除','取消'] //按钮
        }, function(){
            $.ajax({
                type:"POST",
                url:"/auth/blacklist/ajax/deleteBlacklist",
                data:{
                    "row_id":row_id
                },
                success: function(data){
                    if(data.code == 1){
                        top.layer.msg('移除成功');
                        location.reload();
                    } else {
                        top.layer.msg('操作失败');
                    }
                }
            });
        });
    };

    function downloadModel() {
        document.form_date.action="/auth/blacklist/downloadExcel";
        $("#excle").submit();
    };

    $(function () {
        $('#upload').dmUploader(getUploadHander());
    });

    function getUploadHander() {
        return {
            url: '/auth/blacklist/uploadExcle',
            dataType: 'json',
            maxFileSize: 20000000,
            onNewFile: function (id, file) {
                var index = top.layer.load(100, {
                    shade: [0.2,'#fff'], //0.1透明度的白色背景
                    content:'解析中..'
                });
            },
            onUploadSuccess: function (id, data) {
                top.layer.close(top.layer.index);
                top.layer.msg(data.msg);
                location.reload();
            },
            onUploadError: function (id, message) {
                alert('上传出错，请重试或联系管理员');
                top.layer.close(top.layer.index);
            },
            onFileSizeError: function (file) {
                alert("文件不能超过20MB");
                top.layer.close(top.layer.index);
            }
        }
    };


</script>
</html>