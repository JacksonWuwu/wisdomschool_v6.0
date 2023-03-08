<#include "/common/style.ftl"/>
<body class="white-bg">
<div class="container-div clearfix">
    <div class="wrapper wrapper-content animated fadeInRight ibox-content">
        <form class="form-horizontal m" id="form-student-edit">
            <input type="text" name="id" hidden value="${student.id}">
            <input type="text" name="student.id" hidden value="${student.student.id}">
            <div class="row">
                <div class="col-sm-6">
                    <div class="form-group">
                        <label class="col-sm-4 control-label "><i class="text-danger">*</i> 学号：</label>
                        <div class="col-sm-8">
                            <input class="form-control" value="${student.loginName}" disabled type="text"
                                   name="loginName" id="loginName"/>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6">
                    <div class="form-group">
                        <label class="col-sm-4 control-label"><i class="text-danger">*</i> 学生姓名：</label>
                        <div class="col-sm-8">
                            <input class="form-control" type="text" value="${student.userName}" name="userName"
                                   id="userName">
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6">
                    <div class="form-group">
                        <label class="col-sm-4 control-label"><i class="text-danger">*</i> 年级：</label>
                        <div class="col-sm-8">
                            <@selectPage name='grades.id' id='grades' selected="${student.grades.id}:${student.grades.name}" url='/jiaowu/grades/listpage/'/>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6">
                    <div class="form-group">
                        <label class="col-sm-4 control-label"><i class="text-danger">*</i> 系别：</label>
                        <div class="col-sm-8">
                            <@selectPage id='department' name='department.id' init="true" selected="${student.department.id}:${student.department.name}" url='/jiaowu/department/listpage/'nextId='major'/>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6">
                    <div class="form-group">
                        <label class="col-sm-4 control-label"><i class="text-danger">*</i> 专业：</label>
                        <div class="col-sm-8">
                            <@selectPage id='major' name='major.id' init="false" selected="${student.major.id}:${student.major.name}" url='/jiaowu/major/listpage/' nextId='clbum'/>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6">
                    <div class="form-group">
                        <label class="col-sm-4 control-label"><i class="text-danger">*</i> 班级：</label>
                        <div class="col-sm-8">
                            <@selectPage id='clbum' name='clbum.id' init="false" selected="${student.clbum.id}:${student.clbum.name}" url='/jiaowu/clbum/listpage'/>
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
                                                <#if (row.dictValue == student.sex)>selected</#if>>${row.dictLabel}</option>
                                    </#list>
                                </@dictionary>
<#--                                <option value="男" <#if (0 == student.sex)>selected</#if>>男</option>-->
<#--                                <option value="女" <#if (1 == student.sex)>selected</#if>>女</option>-->
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
                        <input type="text" class="time-input"
                               value="${student.enrollmentDate!""}" id="onceTime"
                               placeholder="入学时间"
                               name="student.enrollmentDate"/>

                    </div>
                </div>
            </div>


            <div class="form-group">
                <label class="col-sm-2 control-label">备注：</label>
                <div class="col-sm-10">
                    <input id="remark" name="remark" class="form-control" type="text" value="${student.remark}">
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
    let prefix = "/jiaowu/student"
    $("#form-student-edit").validate({
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
        }
    });

    function submitHandler() {
        if ($.validate.form()) {
            $.operate.saveModal(prefix + "/edit", $('#form-student-edit').serialize());
        }
    }

    // $(document).click(function(e) { // 在页面任意位置点击而触发此事件
    //     if($(e.target).is('select')){
    //         var href= $(e.target).attr("data-url")
    //         if(href!=="#"&&href!==""&&href!=undefined) {
    //             if (href.indexOf("?") === -1) {
    //                 href = href + "?token=" + token
    //             } else {
    //                 href = href + "&token=" + token
    //             }
    //         }
    //         $(e.target).attr("data-url",href)
    //     };       // e.target表示被点击的目标
    // });

</script>
</body>
