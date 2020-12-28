<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ct" uri="/tags" %>
<!DOCTYPE HTML>
<html class="frm10">
<head>
    <title>${app} - 运营管理</title>
    <link rel="shortcut icon" type="image/x-icon" href="/favicon.ico"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8 "/>
    <link rel="stylesheet" href="${css}/main.css"/>
    <script src="/_session/domain.js"></script>
    <script src="${js}/lib.js"></script>
    <script src="${js}/laydate/laydate.js"></script>
    <script src="${js}/layer.js"></script>
    <style>
        .date-input{
            height: 26px;
            width: 80px;
            padding-left: 10px;
        }
    </style>
</head>
<body>
<main>
    <ct:toolmenu pack="panda_operate"></ct:toolmenu>

    <detail>
        <form name="form_date" id="excel_form">
        <table>
            <tr>
                <td>认证项：</td>
                <td>
                    <boxlist>
                        <label><input type="checkbox" id="identification" name="identification"/><a>身份证</a></label>
                        <label><input type="checkbox" id="operator" name="operator" /><a>运营商</a></label>
                        <label><input type="checkbox" id="taobao" name="taobao"/><a>淘宝</a></label>
                        <label><input type="checkbox" id="base" name="base"/><a>基本信息</a></label>
                        <label><input type="checkbox" id="work" name="work"/><a>工作信息</a></label>
                        <label><input type="checkbox" id="linkman" name="linkman" /><a>联系人</a></label>
                    </boxlist>
                </td>
            </tr>
            <tr>
                <td></td>
                <td></td>
            </tr>
            <tr>
                <td>认证完成时间：</td>
                <td>
                    <input  name="date_begin" id ="date_begin" placeholder="起始时间" class="date-input"> -
                    <input  name="date_end" id ="date_end" placeholder="结束时间" class="date-input">
                </td>
            </tr>
            <tr>
                <td></td>
                <td></td>
            </tr>
            <tr>
                <td></td>
                <td><button type="button" onclick="exportExcel();">导出</button></td>
            </tr>
        </table>
        </form>
    </detail>
</main>
</body>
<script>
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

    function exportExcel() {
        var identification = document.getElementById("identification").checked;
        var operator = document.getElementById("operator").checked;
        var taobao = document.getElementById("taobao").checked;
        var base = document.getElementById("base").checked;
        var work = document.getElementById("work").checked;
        var linkman = document.getElementById("linkman").checked;
        var date_begin = $("#date_begin").val();
        var date_end = $("#date_end").val();

        var flag = true;
        if (!identification&&!operator&&!taobao&&!base&&!work&&!linkman){
            layer.msg("请至少选择一个认证项");
            flag = false;
            return;

        }
        if (!date_begin||!date_end){
            layer.msg("请选择正确的认证时间段");
            flag = false;
            return;
        }

        if (date_end<date_begin){
            layer.msg("开始时间不能大于结束时间");
            flag = false;
            return;
        }
        if (flag){
            document.form_date.action="/auth/operate_export/doExport";
            $("#excel_form").submit();
        }
    };
</script>
</html>