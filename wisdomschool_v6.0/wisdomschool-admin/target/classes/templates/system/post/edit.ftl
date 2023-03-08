<#include "/common/style.ftl"/>
<body class="white-bg">
<div class="container-div clearfix">
    <div class="wrapper wrapper-content animated fadeInRight ibox-content">
        <form class="form-horizontal m" id="form-student-edit">
            <input type="text" name="id" hidden value="${sdsadminVo.id}">
            <input type="text" name="sdsadminVo.id" hidden value="${sdsadminVo.sdsadmin.id}">
            <div class="row">
                <div class="col-sm-6">
                    <div class="form-group">
                        <label class="col-sm-4 control-label "><i class="text-danger">*</i> 帐号：</label>
                        <div class="col-sm-8">
                            <input class="form-control" value="${sdsadminVo.loginName}" disabled type="text"
                                   name="loginName" id="loginName"/>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6">
                    <div class="form-group">
                        <label class="col-sm-4 control-label"><i class="text-danger">*</i> 姓名：</label>
                        <div class="col-sm-8">
                            <input class="form-control" type="text" value="${sdsadminVo.userName}" name="userName"
                                   id="userName">
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
                                        <option value="${row.dictValue}"
                                                <#if (row.dictValue == sdsadminVo.sex)>selected</#if>>${row.dictLabel}</option>
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
        </form>
    </div>
</div><!--/.container-div-->
<#include "/common/stretch.ftl"/>
<script type="text/javascript">

    $(function () {
        $.common.initBind();
    });
    let prefix = "${ctx}/system/post"
    $("#form-student-edit").validate({
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
            "grades.id": {
                required: true
            },
            "department.id": {
                required: true
            },
            "sex": {
                required: true
            },
        },
        messages: {
            "loginName": {
                remote: "该账号已存在"
            },
            "userName": {
                required: "请输入姓名"
            },
            "sex": {
                required: "请选择性别"
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
