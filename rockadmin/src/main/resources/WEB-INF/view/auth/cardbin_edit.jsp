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
        <c:if test="${bankCardbin.id==0}">新增</c:if>
        <c:if test="${bankCardbin.id>0}">编辑</c:if>
        卡bin</h2>
    <hr/>
    <form>
        <table>

            <tr>
                <td>图片：</td>
                <td id="upload1">
                    <input type="hidden" id="cp11" name="cp11" value="${bankCardbin.logo}"/>
                    <file>
                        <label>
                            <input type="file"/>
                            <a style="height: auto; width: auto; padding: 0px;">
                                <div>
                                    <label>点击或拖入上传</label>
                                    <block style="width: 120px; height: 120px;margin: 0 10px 10px 10px;">
                                        <img id="img11" alt="拖放到此处也可上传" src="${bankCardbin.logo}" style="width: 100%"/>
                                    </block>
                                </div>
                            </a>
                        </label>
                    </file>
                </td>
            </tr>

            <tr>
                <td style="width: 80px">银行名：</td>
                <td>
                    <input type="text" id="bank_name" value="${bankCardbin.bank_name}" placeholder="请输入银行名" style="width: 300px"/>
                </td>
            </tr>

            <tr>
                <td style="width: 80px">机构代码：</td>
                <td>
                    <input type="text" id="org_no" value="${bankCardbin.org_no}" placeholder="请输入机构代码" style="width: 300px"/>
                </td>
            </tr>
            <tr>
                <td style="width: 80px">卡类型：</td>
                <td>
                    <input type="text" id="card_type" value="${bankCardbin.card_type}" placeholder="请输入卡类型" style="width: 300px"/>
                </td>
            </tr>
            <tr>
                <td style="width: 80px">卡名称：</td>
                <td>
                    <input type="text" id="card_name" value="${bankCardbin.card_name}" placeholder="请输入卡名称" style="width: 300px"/>
                </td>
            </tr>
            <tr>
                <td style="width: 80px">卡号长度：</td>
                <td>
                    <input type="text" id="card_length" value="${bankCardbin.card_length}" placeholder="请输入卡号长度" style="width: 300px" onkeyup="this.value=this.value.replace(/[^\d]/g,'') "/>
                </td>
            </tr>
            <tr>
                <td style="width: 80px">卡bin长度：</td>
                <td>
                    <input type="text" id="bin_length" value="${bankCardbin.bin_length}" placeholder="请输入卡bin长度" style="width: 300px" onkeyup="this.value=this.value.replace(/[^\d]/g,'') "/>
                </td>
            </tr>
            <tr>
                <td style="width: 80px">卡bin：</td>
                <td>
                    <input type="text" id="bin" value="${bankCardbin.bin}" placeholder="请输入卡bin" style="width: 300px" onkeyup="this.value=this.value.replace(/[^\d]/g,'') "/>
                </td>
            </tr>
            <tr>
                <td style="width: 80px">银行编号：</td>
                <td>
                    <input type="text" id="bank_code" value="${bankCardbin.bank_code}" placeholder="请输入银行编号" style="width: 300px"/>
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
    $(function () {
        $('#upload1').dmUploader(getUploadHander(11));
    });

    function getUploadHander(type) {
        return {
            url: '/file/upload_banklogo',
            dataType: 'json',
            maxFileSize: 2000000,
            allowedTypes: "image/*",
            onNewFile: function (id, file) {
                $("#uploader_hint").text("开始上传文件");
            },
            onUploadSuccess: function (id, data) {
                if (data.code > 0) {
                    console.log(data.url);
                    $("#img" + type).attr("src", data.url);
                    $("#cp" + type).val(data.url);
                } else {
                    alert(data.msg);
                }
            },
            onUploadError: function (id, message) {
                alert('上传出错，请重试或联系管理员');
            },
            onFileTypeError: function (file) {
                alert("只能上传图片文件");
            },
            onFileSizeError: function (file) {
                alert("文件不能超过2MB");
            }
        }
    };

    function saveEdit() {
        var id = ${bankCardbin.id};
        var bank_name = $('#bank_name').val();
        var org_no = $("#org_no").val();
        var logo = $('#cp11').val();
        var card_type = $('#card_type').val();
        var card_name = $('#card_name').val();
        var card_length = $('#card_length').val();
        var bin_length = $('#bin_length').val();
        var bin = $('#bin').val();
        var bank_code = $('#bank_code').val();

        if(!bank_name){
            layer.msg('银行名称不能为空');
            return;
        }
        if(!org_no){
            layer.msg('机构代码不能为空');
            return;
        }
        if(!logo){
            layer.msg('请上传银行logo');
            return;
        }
        if(!card_type){
            layer.msg('卡类型不能为空');
            return;
        }
        if(!card_name){
            layer.msg('卡名称不能为空');
            return;
        }
        if(!card_length || card_length<=0){
            layer.msg('请输入正确的卡号长度');
            return;
        }
        if(!bin_length || bin_length<=0){
            layer.msg('请输入正确的卡bin长度');
            return;
        }
        if(!bin || bin<=0){
            layer.msg('请输入正确的卡bin');
            return;
        }
        if(!bank_code){
            layer.msg('银行编号不能为空');
            return;
        }

        top.layer.confirm('确认提交?', {
            btn: ['确定','取消'] //按钮
        }, function(){
            top.layer.close(layer.index);

            $.ajax({
                type:"POST",
                url:"/auth/cardbin/cardbin_edit/ajax/saveEdit",
                data:{
                    "id":id,
                    "bank_name":bank_name,
                    "org_no":org_no,
                    "logo":logo,
                    "card_type":card_type,
                    "card_name":card_name,
                    "card_length":card_length,
                    "bin_length":bin_length,
                    "bin":bin,
                    "bank_code":bank_code
                },
                success: function(data){
                    if(data.code == 1){
                        top.layer.msg('操作成功');
                        parent.location.href = "/auth/cardbin?bank_code="+data.bank_code;
                    } else {
                        top.layer.msg('操作失败');
                    }
                }
            });

        });
    };


</script>
</html>
