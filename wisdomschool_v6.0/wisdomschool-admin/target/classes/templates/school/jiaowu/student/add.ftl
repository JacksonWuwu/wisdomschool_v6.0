<#include "/common/style.ftl"/>
<body class="white-bg">
<div class="container-div clearfix main-content-inner">
    <div class="wrapper wrapper-content animated fadeInRight ibox-content">
        <form class="form-horizontal m" id="form-student-add">
            <div class="row">
                <div class="col-sm-6">
                    <div class="form-group">
                        <label class="col-sm-4 control-label "><i class="text-danger">*</i> 学号：</label>
                        <div class="col-sm-8">
                            <input class="form-control" type="text" name="loginName" id="loginName"/>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6">
                    <div class="form-group">
                        <label class="col-sm-4 control-label"><i class="text-danger">*</i> 学生姓名：</label>
                        <div class="col-sm-8">
                            <input class="form-control" type="text" name="userName" id="userName">
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6">
                    <div class="form-group">
                        <label class="col-sm-4 control-label"><i class="text-danger">*</i> 年级：</label>
                        <div class="col-sm-8">
                            <@selectPage id="grades" name='grades.id' init="true" url=ctx+'/jiaowu/grades/listpage'  />
                        </div>
                    </div>
                </div>
                <div class="col-sm-6">
                    <div class="form-group">
                        <label class="col-sm-4 control-label"><i class="text-danger">*</i> 系别：</label>
                        <div class="col-sm-8">
                            <@selectPage id='department' name='department.id' init="true" url=ctx+'/jiaowu/department/listpage' nextId='major'/>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6">
                    <div class="form-group">
                        <label class="col-sm-4 control-label"><i class="text-danger">*</i> 专业：</label>
                        <div class="col-sm-8">
                            <@selectPage id='major' name='major.id' init="false" url=ctx+'/jiaowu/major/listpage' nextId='clbum'/>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6">
                    <div class="form-group">
                        <label class="col-sm-4 control-label"><i class="text-danger">*</i> 班级：</label>
                        <div class="col-sm-8">
                            <@selectPage id='clbum' name='clbum.id' init="false" url=ctx+'/jiaowu/clbum/listpage'/>
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
<#--                                <@dictionary dictType="sys_user_sex">-->
<#--                                    <#list dictData as row>-->
<#--                                        <option value="${row.dictValue}">${row.dictLabel}</option>-->
<#--                                    </#list>-->
<#--                                </@dictionary>-->
                                <option value="0" >男</option>
                                <option value="1" >女</option>
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
                <label for="" class="col-sm-2 control-label"> 入学时间：</label>
                <div class="col-sm-10">
                    <div class="select-time">
                        <input type="text" class="time-input" id="onceTime" placeholder="入学时间"
                               name="student.enrollmentDate"/>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label "> 角色：</label>
                <div class="col-sm-10">
                    <div class="check-context" data-name="roleIds" data-label="roleName"
                         data-url="/system/role/list"></div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">备注：</label>
                <div class="col-sm-10">
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
    let prefix = "/jiaowu/student";

    $("#form-student-add").validate({
        rules: {
            "loginName": {
                required: true,
                remote: {
                    url: "/system/user/checkLoginNameUnique",
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
            "major.id": {
                required: true
            },
            "clbum.id": {
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
            "department.id": {
                required: "请选择系部"
            },
            "major.id": {
                required: "请选择专业"
            },
            "clbum.id": {
                required: "请选择班级"
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

