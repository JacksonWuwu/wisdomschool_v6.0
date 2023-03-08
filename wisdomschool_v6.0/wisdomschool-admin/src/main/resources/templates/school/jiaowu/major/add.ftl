<#include "/common/style.ftl"/>
<body class="white-bg">
<div class="container-div clearfix">
    <div class="wrapper wrapper-content animated fadeInRight ibox-content">
        <form class="form-horizontal m" id="form-major-add">
            <div class="form-group">
                <label class="col-sm-3 control-label"><i class="text-danger">*</i> 系别：</label>
                <div class="col-sm-8">
                    <@selectPage id='department' name='department.id' init="true" url=ctx+'/jiaowu/department/listpage' />
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label"><i class="text-danger">*</i>专业名称：</label>
                <div class="col-sm-8">
                    <input id="name" name="name" class="form-control" type="text">
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
    let prefix = "/jiaowu/major"
    $("#form-major-add").validate({
        rules: {
            "name": {
                required: true,
                remote: {
                    url: prefix+"/checkMajorNameUnique",
                    type: "post",
                    dataType: "json",
                    data: {
                        "name": function () {
                            return $.common.trim($("#name").val());
                        },
                        "department.id": function () {
                            return $.common.trim($("#department").val());
                        }
                    }
                }
            }
        },
        messages: {
            "name": {
                remote: "该专业已存在"
            }
        }
    });

    function submitHandler() {
        if ($.validate.form()) {
            $.operate.saveModal(prefix + "/add", $('#form-major-add').serialize());
        }
    }
</script>
</body>
