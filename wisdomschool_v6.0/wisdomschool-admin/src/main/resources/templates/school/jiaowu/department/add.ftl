<#include "/common/style.ftl"/>
<body class="white-bg">
<div class="container-div clearfix">
    <div class="wrapper wrapper-content animated fadeInRight ibox-content">
        <form class="form-horizontal m" id="form-department-add">
            <div class="row">
                <div class="form-group">
                    <label class="col-sm-3 control-label "><i class="text-danger">*</i> 系部名称：</label>
                    <div class="col-sm-8">
                        <input class="form-control" type="text" name="name" id="name"/>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="form-group">
                    <label class="col-sm-3 control-label">备注：</label>
                    <div class="col-sm-8">
                        <input id="remark" name="remark" class="form-control" type="text">
                    </div>
                </div>
            </div>
        </form>
    </div>
</div><!--/.container-div-->
<#include "/common/stretch.ftl"/>
<
<script type="text/javascript">
    $(function () {
        $.common.initBind();
    });
    let prefix = "/jiaowu/department"
    $("#form-department-add").validate({
        rules: {
            "name": {
                required: true,
                remote: {
                    url: prefix+"/checkDepartmentNameUnique",
                    type: "post",
                    dataType: "json",
                    data: {
                        "name": function () {
                            return $.common.trim($("#name").val());
                        }
                    }
                }
            }
        },
        messages: {
            "name": {
                remote: "该系部已存在"
            }
        }
    });

    function submitHandler() {
        if ($.validate.form()) {
            $.operate.saveModal(prefix + "/add", $('#form-department-add').serialize());
        }
    }
</script>
</body>
