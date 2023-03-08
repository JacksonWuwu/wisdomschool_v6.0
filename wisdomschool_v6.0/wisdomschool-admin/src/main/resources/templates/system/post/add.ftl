<#include "/common/style.ftl"/>
<body class="white-bg">
<div class="container-div clearfix main-content-inner">
    <div class="wrapper wrapper-content animated fadeInRight ibox-content">
        <form class="form-horizontal m" id="form-student-add">
            <div class="row">
                <div class="col-sm-6">
                    <div class="form-group">
                        <label class="col-sm-4 control-label "><i class="text-danger">*</i> 账号：</label>
                        <div class="col-sm-8">
                            <input class="form-control" type="text" name="loginName" id="loginName"/>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6">
                    <div class="form-group">
                        <label class="col-sm-4 control-label"><i class="text-danger">*</i> 姓名：</label>
                        <div class="col-sm-8">
                            <input class="form-control" type="text" name="userName" id="userName">
                        </div>
                    </div>
                </div>
            </div>


            <div class="row">
                <div class="col-sm-6">
                    <div class="form-group">
                        <label class="col-sm-4 control-label "><i class="text-danger">*</i> 性别：</label>
                        <div class="col-sm-8">
                            <select id="sex" name="sex" class="form-control m-b">
                                <@dictionary dictType="sys_user_sex">
                                    <#list dictData as row>
                                        <option value="${row.dictValue}">${row.dictLabel}</option>
                                    </#list>
                                </@dictionary>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6">
                    <div class="form-group">
                        <label class="col-sm-4 control-label"><i class="text-danger">*</i> 密码：</label>
                        <div class="col-sm-8">
                            <input class="form-control" type="password" name="password" id="password">
                        </div>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label "> 角色：</label>
                <div class="col-sm-10">
                    <div class="check-context" data-name="roleIds" data-label="roleName"
                         data-url="${ctx}/system/role/list"></div>
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
    let prefix = "${ctx}/system/post";

    $("#form-student-add").validate({
        rules: {
            "loginName": {
                required: true,
                remote: {
                    url: "${ctx}/system/user/checkLoginNameUnique",
                    type: "post",
                    dataType: "json",
                    data: {
                        "loginName": function () {
                            return $.common.trim($("#loginName").val());
                        }
                    },
                }
            },
            "userName": {
                required: true
            },
            "grades": {
                required: true
            },
            "department": {
                required: true
            },
            "sex": {
                required: true
            },
            "password": {
                required: true
            }
        },
        messages: {
            "loginName": {
                remote: "该账号已存在"
            },
            "userName": {
                required: "请输入学生姓名"
            },
            "grades.id": {
                required: "请选择年级"
            },
            "department": {
                required: "请选择系部"
            },
            "sex": {
                required: "请选择性别"
            },
            "password": {
                required: "请输入密码"
            }
        }
    });

    function submitHandler() {
        if ($.validate.form()) {
            $.operate.saveModal(prefix + "/add", $('#form-student-add').serialize());
        }
    }

</script>
</body>

