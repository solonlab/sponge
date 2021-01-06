<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ct" uri="/tags" %>

<!DOCTYPE HTML>
<html class="frm10">
<head>
    <title>${app} - <c:choose><c:when test="${is_edit}">编辑短信</c:when><c:otherwise>新建短信</c:otherwise></c:choose></title>
    <link rel="shortcut icon" type="image/x-icon" href="/favicon.ico"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8 "/>
    <link rel="stylesheet" href="${css}/main.css"/>
    <style>
        #sms_content {
            width: 555px;
            border: 1px solid;
            padding: 2px;
        }
        #sms_content p {
            word-wrap: break-word;
            word-break: break-all;
        }
        .m_textarea {
            width: 555px;
        }
    </style>
    <script src="/_session/domain.js"></script>
    <script src="${js}/lib.js"></script>
    <script src="${js}/layer.js"></script>
    <script>
        $(function () {
            $("#is_target_mobile_0").change(on_is_target_mobile_change);
            $("#is_target_mobile_1").change(on_is_target_mobile_change);
            $("#is_target_time").change(function () {
                var target_time = $("#target_time");
                if (!$("#is_target_time").is(":checked")) {
                    target_time.val("");
                    target_time.attr("disabled", "disabled");
                } else {
                    target_time.removeAttr("disabled");
                }
            });
            $("#sms_template").change(function () {
                var rst = "";
                switch ($("#sms_template").val() - 0) {
                    <c:forEach var="t" items="${tmps}">
                    case ${t.template_id}: {
                        rst = '${t.content}';
                    } break;
                    </c:forEach>
                }
                $("#sms_content").html('<p>' + rst + '</p>');
                do_count();
            });
            $("#sms_template").change();

            <c:if test="${!is_edit}">
            addChannel();
            </c:if>

            <c:if test="${is_edit}">
            $("#sms_model").val("${sms.model_id}");
            $("#sms_var").val("${sms.var_setting}");
            <c:choose>
            <c:when test="${0 == sms.mobile.length()}">
            $("#is_target_mobile_0").attr("checked", true);
            $("#target_rule").val("${sms.rule_id}");
            </c:when>
            <c:otherwise>
            $("#target_mobile").val("${sms.mobile}");
            </c:otherwise>
            </c:choose>
            $("#sms_template").val("${sms.template_id}");
            $("#sms_template").change();
            $("#target_rule").val("${sms.rule_id}");
            <c:if test="${sms.target_time.getYear() > 100}">
            $("#is_target_time").attr("checked", true);
            $("#target_time").val('${sms.target_time.toString().replace(" ", "T")}');
            </c:if>
            <c:choose>
            <c:when test="${0 == sms.channels.length()}">
            addChannel();
            </c:when>
            <c:otherwise>
            var channels = JSON.parse('${sms.channels}');
            for (idx in channels) {
                addChannel(undefined, channels[idx].channel_id, channels[idx].size);
            }
            </c:otherwise>
            </c:choose>
            $("button[name='add_channel']").hide();
            $('table').find('input, textarea, button, select').attr('disabled','disabled');
            </c:if>
        });

        function do_count() {

            var length = $("#sms_content>p:first").html().length;
            $("#count_word").html(length);
            $("#count_sms").html(Math.ceil(length / 70));

        }

        function on_is_target_mobile_change() {
            var nd = $("input:radio[name='is_target_mobile']:checked").val();
            var ipt = $("#target_mobile");
            var slct = $("#target_rule");
            if ('0' === nd) {
                ipt.val('');
                ipt.attr("disabled", "disabled");
                slct.removeAttr("disabled");
            } else {
                ipt.removeAttr("disabled");
                slct.attr("disabled", "disabled");
            }
        }

        function addChannel(btn, value1, value2) {

            if (btn) {
                $(btn).hide();
            }

            var slct = $('<tr name="sms_channel"><td><select name="sms_channel_id"><option value="0"></option><c:forEach var="a" items="${cnls}"><option value="${a.channel_id}">${a.name_display}</option></c:forEach></select></td><td><input name="sms_channel_size" placeholder="请输入配额权重" /></td><td><button type="button" class="button" name="add_channel" onclick="addChannel(this)">添加</button></td></tr>');
            $("#container_channel").append(slct);

            if (value1) {
                $("select[name='sms_channel_id']").last().val(value1);
            }

            if (value2) {
                $("input[name='sms_channel_size']").last().val(value2);
            }

        }

        function getChannels() {
            var rst = [];
            $("tr[name='sms_channel']").each(function () {
                var tmp = {};
                tmp.channel_id = $(this).find("select[name='sms_channel_id']").val();
                tmp.size = $(this).find("input[name='sms_channel_size']").val();
                rst.push(tmp);
            });
            return rst;
        }

        <c:if test="${!is_edit}">
        function check_save() {
            if (0 >= $("#target_time").val().length) {
                top.layer.confirm('<p style="text-align:center;width:250px;">未选择定时发送，提交后将会立即执行该发送任务，是否立即发送</p>', {
                    btn: ['立即发送', '取消']
                }, function () {
                    save();
                }, function () {
                    //
                });
            } else {
                save();
            }
        }

        function save() {

            var target_time = $("#target_time").val();

            $.ajax({
                type: "POST",
                url: "/push/sms/ajax/save",
                data: {
                    agroup_id: ${agroup_id},
                    model_id: $("#sms_model").val(),
                    template_id: $("#sms_template").val(),
                    var_setting: $("#sms_var").val(),
                    mobile: $("#target_mobile").val(),
                    rule_id: $("#target_rule").val(),
                    channels: JSON.stringify(getChannels()),
                    target_time: target_time.length > 0 ? new Date(Date.parse(target_time.replace("T", " "))) : new Date(0)
                },
                success: function (data) {
                    if (data.code === 1) {
                        top.layer.msg(data.msg);
                        location.href = "/push/sms/inner/" + ${agroup_id};
                    } else {
                        top.layer.msg(data.msg);
                    }
                }
            });

        }
        </c:if>
    </script>
</head>
<body>

<main>
    <detail><form>
        <h2><c:choose><c:when test="${is_edit}">查看短信</c:when><c:otherwise>新建短信</c:otherwise></c:choose>（<a class="t2" onclick="history.back(-1)">返回</a>）</h2>
        <hr/>
        <table>
            <tr>
                <td>关联模型</td>
                <td><select id="sms_model">
                    <option value="0"></option>
                    <c:forEach items="${models}" var="m">
                        <option value="${m.model_id}">${m.name_display}</option>
                    </c:forEach>
                </select></td>
            </tr>
            <tr>
                <td>选择模板</td>
                <td><select id="sms_template">
                    <c:forEach var="t" items="${tmps}"><option value="${t.template_id}">${t.name}</option></c:forEach>
                </select></td>
            </tr>
            <tr>
                <td></td>
                <td><div id="sms_content"></div></td>
                <td>共<span id="count_word">0</span>字;<span id="count_sms">0</span>条</td>
            </tr>
            <tr>
                <td>变量设定</td>
                <td><textarea id="sms_var" class="m_textarea" placeholder="#name#={{m.user_name}},#code#=1"></textarea></td>
            </tr>
            <tr>
                <td style="vertical-align: top; padding-top: 20px;">推送对象</td>
                <td>
                    <table>
                        <tr>
                            <td><radio>
                                <label><input type="radio" name="is_target_mobile" value="1" id="is_target_mobile_1"
                                              checked/><a>个人用户</a></label>
                            </radio>
                            </td>
                            <td>
                                <input type="text" placeholder="请输入手机号(多个以,号隔开)" id="target_mobile" style="width: 465px;" />
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <radio>
                                <label><input type="radio" name="is_target_mobile" value="0" id="is_target_mobile_0"/><a>规则</a></label>
                                </radio>
                            </td>
                            <td>
                                <select id="target_rule" style="width: 470px;" disabled>
                                    <c:forEach var="r" items="${rules}">
                                        <option value="${r.scheme_id}">${r.name_display}</option>
                                    </c:forEach>
                                </select>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td>定时推送</td>
                <td>
                    <checkbox>
                        <label><input type="checkbox" id="is_target_time"/><a></a></label>
                    </checkbox>
                    <input type="datetime-local" id="target_time" style="width: 283px;" disabled/>
                </td>
            </tr>
            <tr>
                <td style="vertical-align: top; padding-top: 18px;">发送渠道</td>
                <td style="padding-left: 0;">
                    <table id="container_channel"></table>
                </td>
            </tr>
            <c:if test="${!is_edit}">
            <tr>
                <td></td>
                <td>
                    <button type="button" onclick="check_save()">保存</button>
                </td>
            </tr>
            </c:if>
        </table>
    </form></detail>
</main>

</body>
</html>