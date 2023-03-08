<#include "/common/style.ftl"/>
<body class="white-bg">
<div class="container-div clearfix">
    <div class="wrapper wrapper-content animated fadeInRight ibox-content">
        <form class="form-horizontal m" id="form-clbum-add">
            <div class="form-group">
                <label class="col-sm-3 control-label"><i class="text-danger">*</i> 系别：</label>
                <div class="col-sm-8">
                    <@selectPage id='department' name='department.id' init="true" url=ctx+'/jiaowu/department/listpage' nextId='major'/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label"><i class="text-danger">*</i> 专业：</label>
                <div class="col-sm-8">
                    <@selectPage id='major' name='major.id' init="false" url=ctx+'/jiaowu/major/listpage' />
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label"><i class="text-danger">*</i> 班级名称：</label>
                <div class="col-sm-8">
                    <input id="name" name="name" class="form-control" type="text">
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-3 control-label"><i class="text-danger">*</i> 班主任：</label>
                <div class="col-sm-8">
                    <select id="tid" class="form-control m-b "
                            name="tid">
                        <option value="">请选择班主任</option>
                        <#list list as list>
                            <option value="${list.userAttrId}">${list.userName}</option>
                        </#list>
                    </select>
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-3 control-label">备注：</label>
                <div class="col-sm-8">
                    <input id="remark" name="remark" class="form-control" type="text">
                </div>
            </div>
        </form>
    </div>
</div><!--/.container-div-->
<#include "/common/stretch.ftl"/>
<script type="text/javascript">
    $(function () {
        $.common.initBind();
    });
    let prefix = "/jiaowu/clbum"
    $("#form-clbum-add").validate({
        rules: {
            "name": {
                required: true,
                remote: {
                    url: prefix+"/checkClbumNameUnique",
                    type: "post",
                    dataType: "json",
                    data: {
                        "name": function () {
                            return $.common.trim($("#name").val());
                        },
                        "major.id": function () {
                            return $.common.trim($("#major").val());
                        }
                    }
                }
            }
        },
        messages: {
            "name": {
                remote: "该班级已存在"
            }
        }
    });

    function submitHandler() {
        if ($.validate.form()) {
            $.operate.saveModal(prefix + "/add", $('#form-clbum-add').serialize());
        }
    }
</script>
</body>
