<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ct" uri="/tags" %>
<!DOCTYPE HTML>
<html class="frm10">
<head>
    <title>${app} - 卡bin编辑</title>
    <link rel="shortcut icon" type="image/x-icon" href="/favicon.ico" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8 "/>
    <link rel="stylesheet" href="//static.kdz6.cn/lib/durian/main.css"/>
    <script src="/_session/domain.js"></script>
    <script src="${js}/lib.js" ></script>
    <script src="${js}/layer.js"></script>
</head>
<body>
<detail>
    <h2>
        <c:if test="${branchbank.id==0}">新增</c:if>
        <c:if test="${branchbank.id>0}">编辑</c:if>
        支行</h2>
    <hr/>
    <form>
        <table>
            <tr>
                <td style="width: 100px">所属银行编号：</td>
                <td>
                    <input type="text" id="bank_code" value="${branchbank.bank_code}" placeholder="请输入所属银行编号" style="width: 300px"/>
                </td>
            </tr>

            <tr>
                <td style="width: 80px">支行编号：</td>
                <td>
                    <input type="text" id="paysys_bank" value="${branchbank.paysys_bank}" placeholder="请输入支行编号" style="width: 300px"/>
                </td>
            </tr>
            <tr>
                <td style="width: 80px">支行名称：</td>
                <td>
                    <input type="text" id="name" value="${branchbank.name}" placeholder="请输入支行名称" style="width: 300px"/>
                </td>
            </tr>
            <tr>
                <td style="width: 80px">行号：</td>
                <td>
                    <input type="text" id="bank_no" value="${branchbank.bank_no}" placeholder="请输入行号" style="width: 300px"/>
                </td>
            </tr>
            <tr>
                <td style="width: 80px">支行座机号码：</td>
                <td>
                    <input type="text" id="phone" value="${branchbank.phone}" placeholder="请输入支行座机号码" style="width: 300px" onkeyup="this.value=this.value.replace(/[^\d]/g,'') "/>
                </td>
            </tr>
            <tr>
                <td style="width: 80px">支行地址：</td>
                <td>
                    <input type="text" id="bank_addr" value="${branchbank.bank_addr}" placeholder="请输入支行地址" style="width: 300px" onkeyup="this.value=this.value.replace(/[^\d]/g,'') "/>
                </td>
            </tr>

            <tr>
                <td></td>
                <td>
                    <button type="button" onclick="saveEdit();">保存</button>
                </td>
            </tr>
        </table>
    </form>
</detail>
</body>
<script>

    function saveEdit() {
        var id = ${branchbank.id};
        var bank_code = $('#bank_code').val();
        var paysys_bank = $("#paysys_bank").val();
        var name = $('#name').val();
        var bank_no = $('#bank_no').val();

        var phone = $('#phone').val();
        var bank_addr = $('#bank_addr').val();

        if(!bank_code){
            layer.msg('所属银行编号不能为空');
            return;
        }
        if(!paysys_bank){
            layer.msg('支行编号不能为空');
            return;
        }
        if(!name){
            layer.msg('支行名称不能为空');
            return;
        }
        if(!bank_no){
            layer.msg('行号不能为空');
            return;
        }


        top.layer.confirm('确认提交?', {
            btn: ['确定','取消'] //按钮
        }, function(){
            top.layer.close(layer.index);

            $.ajax({
                type:"POST",
                url:"/auth/bankbranch/bankbranch_edit/ajax/saveEdit",
                data:{
                    "id":id,
                    "bank_code":bank_code,
                    "paysys_bank":paysys_bank,
                    "name":name,
                    "bank_no":bank_no,
                    "phone":phone,
                    "bank_addr":bank_addr
                },
                success: function(data){
                    if(data.code == 1){
                        top.layer.msg('操作成功');
                        location.href = "/auth/bankbranch";
                    } else {
                        top.layer.msg('操作失败');
                    }
                }
            });

        });
    };


</script>
</html>
