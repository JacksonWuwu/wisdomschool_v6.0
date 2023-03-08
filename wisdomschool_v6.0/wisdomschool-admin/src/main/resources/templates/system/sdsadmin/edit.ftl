<#include "/common/style.ftl"/>
<body class="white-bg">
<div class="container-div clearfix">
    <div class="wrapper wrapper-content animated fadeInRight ibox-content">
        <form class="form-horizontal m" id="form-student-edit">
            <input type="text" name="sid" hidden value="${sdsadminRole.sid}">
            <input type="text" name="sdsadminRole.sid" hidden value="${sdsadminRole.sid}">
            <div class="row">
                <div class="col-sm-6">
                    <div class="form-group">
                        <label class="col-sm-4 control-label"><i class="text-danger">*</i> 年级：</label>
                        <div class="col-sm-8">
                            <@selectPage name='grades' id='grades' selected="${sdsadminRole.gradess.id}:${sdsadminRole.gradess.name}"url=ctx+'/jiaowu/grades/listpage/'/>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6">
                    <div class="form-group">
                        <label class="col-sm-4 control-label"><i class="text-danger">*</i> 系别：</label>
                        <div class="col-sm-8">
                            <@selectPage id='department' name='department' init="true" selected="${sdsadminRole.departments.id}:${sdsadminRole.departments.name}" url=ctx+'/jiaowu/department/listpage/'nextId='major'/>
                        </div>
                    </div>
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
    let prefix = "${ctx}/system/sdsadmin"
    $("#form-student-edit").validate({
        rules: {

            "grades.id": {
                required: true
            },
            "department.id": {
                required: true
            },
        },
        messages: {

            "grades.id": {
                required: "请选择年级"
            },
            "department.id": {
                required: "请选择系部"
            },

        }
    });

    function submitHandler() {
        if ($.validate.form()) {
            $.operate.saveModal(prefix + "/edit", $('#form-student-edit').serialize());
        }
    }
</script>
</body>