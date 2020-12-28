<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ct" uri="/tags" %>

<!DOCTYPE HTML>
<html class="frm10">
<head>
    <title>${app} - <c:choose><c:when test="${is_edit}">查看消息</c:when><c:otherwise>新建消息</c:otherwise></c:choose></title>
    <link rel="shortcut icon" type="image/x-icon" href="/favicon.ico"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8 "/>
    <link rel="stylesheet" href="${css}/main.css"/>
    <style>
        .m_textarea {
            width: 555px;
        }

        .small_select {
            width: 150px;
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

            <c:if test="${!is_edit}">
            addAction();
            </c:if>

            <c:if test="${is_edit}">
            $("#msg_model").val(${msg.model_id});
            $("#msg_title").val("${msg.title}");
            $("#msg_content").val("${msg.content}");
            <c:choose>
            <c:when test="${0 == msg.push_link.length()}">
//            $("#need_push_url_0").attr("checked", true);
            </c:when>
            <c:otherwise>
            $("#push_url").val("${msg.push_link}");
            </c:otherwise>
            </c:choose>
            <c:choose>
            <c:when test="${0 == msg.msg_link.length()}">
//            $("#need_msg_url_0").attr("checked", true);
            </c:when>
            <c:otherwise>
            $("#msg_url").val("${msg.msg_link}");
            </c:otherwise>
            </c:choose>
            <c:choose>
            <c:when test="${0 == msg.mobile.length()}">
            $("#is_target_mobile_0").attr("checked", true);
            $("#target_rule").val("${msg.rule_id}");
            </c:when>
            <c:otherwise>
            $("#target_mobile").val("${msg.mobile}");
            </c:otherwise>
            </c:choose>
            <%--$("#push_type").val("${msg.type}");--%>
            <c:if test="${msg.target_time.getYear() > 100}">
            $("#is_target_time").attr("checked", true);
            $("#target_time").val('${msg.target_time.toString().replace(" ", "T")}');
            </c:if>
            <c:choose>
            <c:when test="${0 == msg.actions.length()}">
            addAction();
            </c:when>
            <c:otherwise>
            var actions = "${msg.actions}".split(',');
            for (idx in actions) {
                addAction(undefined, actions[idx]);
            }
            </c:otherwise>
            </c:choose>
            $("button[name='add_action']").hide();
            $('table').find('input, textarea, button, select').attr('disabled','disabled');
            </c:if>

        });


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

        function addAction(btn, value) {

            if (btn) {
                $(btn).hide();
            }

            var slct = $('<tr><td><select name="msg_action"><option value="0"></option><c:forEach var="a" items="${actions}"><option value="${a.action_id}">${a.name_display}</option></c:forEach></select></td><td><button type="button" class="button" name="add_action" onclick="addAction(this)">添加</button></td></tr>');
            $("#container_action").append(slct);

            if (value) {
                $("select[name='msg_action']").last().val(value);
            }

        }

        function getActions() {
            var rst = "";
            $("select[name='msg_action']").each(function () {
                rst += $(this).val() + ",";
            });
            return rst.slice(0, -1);
        }
        
        <c:if test="${!is_edit}">

        function save() {

            <%--var agroup_id = $("input:radio[name='msg_agroup']:checked").val();--%>
            var agroup_id = ${agroup_id};
            var target_time = $("#target_time").val();

            $.ajax({
                type: "POST",
                url: "/push/msg/ajax/save",
                data: {
                    model_id: $("#msg_model").val(),
                    title: $("#msg_title").val(),
                    content: $("#msg_content").val(),
                    agroup_id: agroup_id,
                    push_link: $("#push_url").val(),
                    msg_link: $("#msg_url").val(),
                    mobile: $("#target_mobile").val(),
                    rule_id: $("#target_rule").val(),
                    actions: getActions(),
                    target_time: target_time.length > 0 ? new Date(Date.parse(target_time.replace("T", " "))) : new Date(0)
                },
                success: function (data) {
                    if (data.code === 1) {
                        top.layer.msg(data.msg);
                        location.href = "/push/msg/inner/" + agroup_id;
                    } else {
                        top.layer.msg(data.msg);
                    }
                }
            });
        };

        </c:if>
    </script>
</head>
<body>

<main>
    <detail><form>
        <h2><c:choose><c:when test="${is_edit}">查看消息</c:when><c:otherwise>新建消息</c:otherwise></c:choose>（<a class="t2" onclick="history.back(-1)">返回</a>）</h2>
        <hr/>

        <datalist id="msg_link_list">
            <c:forEach var="l" items="${links}">
                <option value="${l.link}" label="${l.name}"></option>
            </c:forEach>
        </datalist>

        <table>
            <tr>
                <td>关联模型</td>
                <td><select id="msg_model">
                    <option value="0"></option>
                    <c:forEach items="${models}" var="m">
                        <option value="${m.model_id}">${m.name_display}</option>
                    </c:forEach>
                </select></td>
            </tr>
            <tr>
                <td>消息标题</td>
                <td><input type="text" placeholder="请输入推送标题" id="msg_title"/></td>
            </tr>
            <tr>
                <td style="vertical-align: top;">消息内容</td>
                <td><textarea id="msg_content" class="m_textarea" placeholder="{{m.user_name}}，你好"></textarea></td>
            </tr>
            <tr>
                <td>消息链接</td>
                <td>
                    <input type="text" placeholder="请输入或选择消息链接" list="msg_link_list" id="msg_url" style="width: 560px;" />
                </td>
            </tr>
            <tr>
                <td>推送链接</td>
                <td>
                    <input type="text" placeholder="请输入或选择推送链接" list="msg_link_list" id="push_url" style="width: 560px;" />
                </td>
            </tr>
            <tr>
                <td style="vertical-align: top; padding-top: 20px;">推送对象</td>
                <td>
                    <table>
                        <tr>
                            <td>
                                <radio>
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
                    <input type="datetime-local" id="target_time" style="width: 285px;" disabled/>
                </td>
            </tr>
            <tr>
                <td style="vertical-align: top; padding-top: 18px;">推送动作</td>
                <td style="padding-left: 0;">
                    <table id="container_action"></table>
                </td>
            </tr>
            <c:if test="${!is_edit}">
                <tr>
                    <td></td>
                    <td>
                        <button type="button" onclick="save()">保存</button>
                    </td>
                </tr>
            </c:if>
        </table>
    </form></detail>
</main>

</body>
</html>