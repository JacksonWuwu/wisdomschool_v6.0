<#include "/common/style.ftl"/>
<body class="white-bg">
<div class="container-div clearfix">
    <div class="wrapper wrapper-content animated fadeInRight ibox-content">
        <form class="form-horizontal m" id="form-clbum-edit">
            <input id="id" name="id"
                   value="${id}" type="hidden">
<#--            <div class="form-group">-->
<#--                <label class="col-sm-3 control-label"><i class="text-danger">*</i> 班级：</label>-->
<#--                <div class="col-sm-8">-->
<#--                    <@selectPage id='clbum' name='clbum.id' init="false" selected="${attendance.clbum.id}:${attendance.clbum.name}" url=ctx+'/jiaowu/clbum/listpage/'/>-->
<#--                </div>-->
<#--            </div>-->
            <div class="form-group">
                <label class="col-sm-3 control-label"><i class="text-danger">*</i> 班级：</label>
                <div class="col-sm-8">
                    <select id="cid" class="form-control m-b "
                            name="cid">
                        <#list list as list>
                            <option value="${list.id}">${list.name}</option>
                        </#list>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label">状态:</label>
                <div class="col-sm-8">
                    <label class="radio-box"> <input type="radio" name="state" value="0" <#if attendance.state == "0">checked</#if> /> 未开启 </label>
<#--                    <label class="radio-box"> <input type="radio" name="state" value="1" <#if attendance.state == "1">checked</#if> /> 已开启 </label>-->
                    <label class="radio-box"> <input type="radio" name="state" value="2" <#if attendance.state == "2">checked</#if> /> 结束 </label>
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-3 control-label">签到方式：</label>
                <div class="col-sm-8">
                    <label class="radio-box"> <input type="radio" name="type" value="0" <#if attendance.type == "0">checked</#if> /> 数字 </label>
                    <label class="radio-box"> <input type="radio" name="type" value="1" <#if attendance.type == "1">checked</#if> /> 扫码 </label>
                    <label class="radio-box"> <input type="radio" name="type" value="2" <#if attendance.type == "2">checked</#if> /> 点名 </label>
                </div>
            </div>


        </form>
    </div>
</div><!--/.container-div-->
<#include "/common/stretch.ftl"/>
<script type="text/javascript">
    let prefix = "${ctx}/teacher/attendance";
    $(function () {
        $.common.initBind();
    });
    $("#form-clbum-edit").validate({
        rules: {
            "type": {
                required: true
            },
            "state": {
                required: true
            },
        },
        messages: {
            "type": {
                remote: "该账号已存在"
            },
            "state": {
                remote: "该账号已存在"
            },
        }
    });

    function submitHandler() {
        if ($.validate.form()) {
            $.operate.saveModal(prefix + "/edit", $('#form-clbum-edit').serialize());
        }
    }

</script>
</body>