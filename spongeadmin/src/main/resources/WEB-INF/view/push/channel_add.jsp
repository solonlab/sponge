<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ct" uri="/tags" %>

<!DOCTYPE HTML>
<html class="frm10">
<head>
    <title>${app} - <c:choose><c:when test="${is_edit}">编辑渠道</c:when><c:otherwise>新建渠道</c:otherwise></c:choose></title>
    <link rel="shortcut icon" type="image/x-icon" href="/favicon.ico"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8 "/>
    <link rel="stylesheet" href="${css}/main.css"/>
    <link rel="stylesheet" href="${css}/codemirror/defined.css"/>
    <link rel="stylesheet" href="https://static.kdz6.cn/lib/codemirror.min.css"/>
    <link rel="stylesheet" href="${css}/codemirror/show-hint.css"/>
    <link rel="stylesheet" href="${css}/codemirror/fullscreen.css"/>
    <script src="/_session/domain.js"></script>
    <script src="${js}/lib.js"></script>
    <script src="https://static.kdz6.cn/lib/codemirror.min.js"></script>
    <script src="${js}/codemirror/jstl.js"></script>
    <script src="${js}/codemirror/anyword-hint.js"></script>
    <script src="${js}/codemirror/show-hint.js"></script>
    <script src="${js}/codemirror/jstl-hint.js"></script>
    <script src="${js}/codemirror/fullscreen.js"></script>
    <script src="${js}/codemirror/matchbrackets.js"></script>
    <script>
        $(function () {
            <c:if test="${is_edit}">
            $("#name").val("${cnl.name}");
            $("#name_display").val("${cnl.name_display}");
            </c:if>
        });

        function save() {
            var name = $('#name').val();
            var name_display = $('#name_display').val();
            if (!name){
                top.layer.msg("渠道代号不能为空");
                return;
            }
            if (!name_display){
                top.layer.msg("渠道名称不能为空");
                return;
            }

            $.ajax({
                type: "POST",
                url: "/push/channel/ajax/save",
                data: {
                    <c:if test="${is_edit}">channel_id: ${cnl.channel_id}, </c:if>
                    agroup_id: ${agroup_id},
                    name: name,
                    name_display: name_display,
                    code: editor.getValue()
                },
                success: function (data) {
                    if (data.code === 1) {
                        top.layer.msg(data.msg);
                        location.href = "/push/channel/inner/" + ${agroup_id};
                    } else {
                        top.layer.msg(data.msg);
                    }
                }
            });
        };

        function del() {
            top.layer.confirm('确定删除渠道', {
                btn: ['确定','取消'] //按钮
            }, function(){
                $.ajax({
                    type: "POST",
                    url: "/push/channel/ajax/del",
                    data: {
                        "channel_id":${cnl.channel_id}
                    },
                    success: function (data) {
                        if (data.code === 1) {
                            top.layer.msg(data.msg);
                            location.href = "/push/channel/inner/" + ${agroup_id};
                        } else {
                            top.layer.msg(data.msg);
                        }
                    }
                });
                top.layer.close(top.layer.index);
            });
        };
    </script>
    <style type="text/css">
        .CodeMirror {
            border: 1px solid #C9C9C9;
            font-size: 14px;
        }
    </style>
</head>
<body>

<main>
    <detail><form>
        <h2><c:choose><c:when test="${is_edit}">编辑渠道</c:when><c:otherwise>新建渠道</c:otherwise></c:choose>（<a class="t2" onclick="history.back(-1)">返回</a>）</h2>
        <hr/>
        <table>
            <tr>
                <td>渠道名称</td>
                <td>
                    <input type="text" placeholder="输入渠道名称" id="name_display"/>
                </td>
            </tr>
            <tr>
                <td>渠道代号</td>
                <td>
                    <input type="text" id="name"/>
                </td>
            </tr>
            <tr>
                <td>适配代码</td>
                <td><p><textarea id="code"><c:if test="${is_edit}">${cnl.code}</c:if></textarea></p></td>
            </tr>
            <tr>
                <td></td>
                <td>
                    <button type="button" onclick="save()">保存</button>&nbsp;
                    <c:if test="${isAdmin==1}">
                        <button type="button" onclick="del()" class="minor">删除</button>
                    </c:if>
                </td>
            </tr>
        </table>
    </form></detail>

    <script>
        CodeMirror.commands.autocomplete = function (cm) {
            cm.showHint({hint: CodeMirror.hint.javascript});
        };
        var editor = CodeMirror.fromTextArea(document.getElementById("code"), {
            //高亮显示
            mode: "text/javascript",

            //显示行号
            lineNumbers: true,
            //在缩进时，是否需要把 n*tab宽度个空格替换成n个tab字符，默认为false 。
            indentWithTabs: true,
            //自动缩进
            smartIndent: true,
            //光标高度，占满整行为1。（数值范围0~1）
            cursorHeight: 1,


            //设置主题 css文件为defined
            theme: "eclipse",

            //绑定Vim
            // keyMap:"vim",

            //代码折叠
            lineWrapping: true,
            foldGutter: true,
            gutters: ["CodeMirror-linenumbers", "CodeMirror-foldgutter"],

            //括号匹配
            matchBrackets: true,

            //智能提示
            extraKeys: {
                "'a'": completeAfter,
                "'b'": completeAfter,
                "'c'": completeAfter,
                "'d'": completeAfter,
                "'e'": completeAfter,
                "'f'": completeAfter,
                "'g'": completeAfter,
                "'h'": completeAfter,
                "'i'": completeAfter,
                "'j'": completeAfter,
                "'k'": completeAfter,
                "'l'": completeAfter,
                "'m'": completeAfter,
                "'n'": completeAfter,
                "'o'": completeAfter,
                "'p'": completeAfter,
                "'q'": completeAfter,
                "'r'": completeAfter,
                "'s'": completeAfter,
                "'t'": completeAfter,
                "'u'": completeAfter,
                "'v'": completeAfter,
                "'w'": completeAfter,
                "'x'": completeAfter,
                "'y'": completeAfter,
                "'z'": completeAfter,
                "'A'": completeAfter,
                "'B'": completeAfter,
                "'C'": completeAfter,
                "'D'": completeAfter,
                "'E'": completeAfter,
                "'F'": completeAfter,
                "'G'": completeAfter,
                "'H'": completeAfter,
                "'I'": completeAfter,
                "'J'": completeAfter,
                "'K'": completeAfter,
                "'L'": completeAfter,
                "'M'": completeAfter,
                "'N'": completeAfter,
                "'O'": completeAfter,
                "'P'": completeAfter,
                "'Q'": completeAfter,
                "'R'": completeAfter,
                "'S'": completeAfter,
                "'T'": completeAfter,
                "'U'": completeAfter,
                "'V'": completeAfter,
                "'W'": completeAfter,
                "'X'": completeAfter,
                "'Y'": completeAfter,
                "'Z'": completeAfter,
                "'.'": completeAfter,
                "'='": completeIfInTag,
                // ,
                // "Ctrl-Space": "autocomplete",
                // "Ctrl-Enter": "autocomplete",

                Tab: function (cm) {
                    var spaces = Array(cm.getOption("indentUnit") + 1).join(" ");
                    cm.replaceSelection(spaces);
                },
                "Esc": function (cm) {
                    cm.setOption("fullScreen", !cm.getOption("fullScreen"));
                    top.layer.msg("按Esc即可退出或进入编辑！")
                },
                "Ctrl-Enter": function () {
                    saveEdit();
                }
            }
        });

        function completeIfInTag(cm) {
            return completeAfter(cm, function () {
                var tok = cm.getTokenAt(cm.getCursor());
                if (tok.type == "string" && (!/['"]/.test(tok.string.charAt(tok.string.length - 1)) || tok.string.length == 1)) return false;
                var inner = CodeMirror.innerMode(cm.getMode(), tok.state).state;
                return inner.tagName;
            });
        }

        function completeAfter(cm, pred) {
            var cur = cm.getCursor();
            if (!pred || pred()) setTimeout(function () {
                if (!cm.state.completionActive)
                    cm.showHint({
                        completeSingle: false
                    });
            }, 100);
            return CodeMirror.Pass;
        };
        //设置宽高,默认为自动根据每行长度来调整。
        //'width','height'
        editor.setSize('700px', '350px');
    </script>
</main>

</body>
</html>