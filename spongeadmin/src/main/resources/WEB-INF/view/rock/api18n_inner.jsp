<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ct" uri="/tags" %>
<!DOCTYPE HTML>
<html class="frm10">
<head>
    <title>${app} - 应用</title>
    <link rel="shortcut icon" type="image/x-icon" href="/favicon.ico"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8 "/>
    <link rel="stylesheet" href="${css}/main.css"/>
    <script src="/_session/domain.js"></script>
    <script src="${js}/jtadmin.js"></script>
    <script src="${js}/layer.js"></script>
    <script>
        function add() {
            location.href="/rock/api18n/add?agroup_id=${agroup_id}&service=${service}";
        }

        function imp(file) {
            if(confirm("确定要导入吗？") == false){
                return;
            }

            layer.load(2);

            var fromData = new FormData();
            fromData.append("file", file);
            fromData.append("service","${service}");
            fromData.append("agroup_id","${agroup_id}");

            $.ajax({
                type:"POST",
                url:"./ajax/import",
                data:fromData,
                processData: false,
                contentType: false,
                success:function (data) {
                    layer.closeAll();

                    if(data.code==1) {
                        top.layer.msg('操作成功');
                        setTimeout(function(){
                            location.reload();
                        },800);
                    }else{
                        top.layer.msg(data.msg);
                    }
                }
            });
        }

        function exp(fmt) {
            let vm = formToMap(".sel_from");
            if(!vm.sel_id){
                alert("请选择..");
                return;
            }

            let baseUrl = "./ajax/export?service=${service}&agroup_id=${agroup_id}";
            window.open(baseUrl + "&fmt=" + fmt + "&ids=" + vm.sel_id, "_blank");
        }

        $(function(){
            $('#sel_all').change(function(){
                var ckd= $(this).prop('checked');
                $('[name=sel_id]').prop('checked',ckd);
            });

            $("#imp_file").change(function () {
                imp(this.files[0]);
            })
        });
    </script>
    <style>
        .btn-link:hover{text-decoration: underline;}
    </style>
</head>
<body>

<toolbar>
    <div class="center">
        <form>
            <input type="text" class="w250" value="${name}" name="name" placeholder="键值"/>
            <input type="hidden" value="${agroup_id}" name="agroup_id" />
            <input type="hidden" value="${service}" name="service" />
            <button type="submit">查询</button>
            <c:if test="${agroup_id>0&&isOperator==1}">
                <button type="button" onclick="add()" class="edit mar10-l">新增</button>
            </c:if>
        </form>
    </div>
    <div>
        <left>
            <c:if test="${agroup_id>0&&isOperator==1}">
                <file>
                    <label><input id="imp_file" type="file" accept=".yml,.json,.properties,.jsond"/><a class="btn minor">导入</a></label>
                </file>
                <div class="btn-group">
                    <a class="btn minor mar10-l" >导出</a>
                    <div class="btn-dropdown mar10-l w150">
                        <a class="btn-link mar10" onclick="exp('yml')">导出为 Yml</a>
                        <a class="btn-link mar10" onclick="exp('json')">导出为 Json</a>
                        <a class="btn-link mar10" onclick="exp('properties')">导出为 Properties</a>
                        <hr class="mar10-l mar10-r"/>
                        <a class="btn-link mar10" onclick="exp('jsond')">导出为 JsonD</a>
                    </div>
                </div>
            </c:if>
        </left>
        <right>
            <selector>
                <c:forEach var="l" items="${langs}">
                    <a class='noline ${l.tag == lang ? "sel":"" }' href="./inner?lang=${l.tag}&agroup_id=${agroup_id}&service=${service}">${l.tag}(${l.counts})</a>
                </c:forEach>
            </selector>
        </right>
    </div>
</toolbar>
<datagrid>
    <table>
        <thead>
        <tr>
            <td width="20px"><checkbox><label><input type="checkbox" id="sel_all" /><a></a></label></checkbox></td>
            <td width="150px">键值</td>
            <td width="80px">语言</td>
            <td>描述信息</td>
            <c:if test="${isOperator==1}">
                <td width="50px">操作</td>
            </c:if>
        </tr>
        </thead>
        <tbody class="sel_from">
        <c:forEach var="m1" items="${list}">
            <tr>
                <td><checkbox><label><input type="checkbox" name="sel_id" value="${m1.row_id}" /><a></a></label></checkbox></td>
                <td class="left">${m1.name}</td>
                <td class="left">${m1.lang}</td>
                <td class="left break">${m1.note}</td>
                <c:if test="${isOperator==1}">
                    <td><a href="/rock/api18n/edit?row_id=${m1.row_id}" style="color: blue;">编辑</a></td>
                </c:if>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</datagrid>

</body>
</html>