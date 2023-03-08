<#include "/common/style.ftl"/>
<link rel="stylesheet" href="/js/plugins/jquery-layout/jquery.layout-latest.css">
<body class="white-bg">
<div class="container-div clearfix">
    <div class="wrapper wrapper-content animated fadeInRight ibox-content">
        <form class="form-horizontal m" id="form-teacher-edit">
            <input type="text" name="id" hidden value="${teacher.id}">
            <input type="text" name="teacher.id" hidden value="${teacher.teacher.id}">
            <div class="row">
                <div class="col-sm-6">
                    <div class="form-group">
                        <label class="col-sm-4 control-label "><i class="text-danger">*</i> 职工号：</label>
                        <div class="col-sm-8">
                            <input class="form-control" value="${teacher.loginName}" disabled type="text"
                                   name="loginName" id="loginName"/>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6">
                    <div class="form-group">
                        <label class="col-sm-4 control-label"><i class="text-danger">*</i> 教师姓名：</label>
                        <div class="col-sm-8">
                            <input class="form-control" type="text" value="${teacher.userName}" name="userName"
                                   id="userName">
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6">
                    <div class="form-group">
                        <label class="col-sm-4 control-label"><i class="text-danger">*</i> 所属院系：</label>
                        <div class="col-sm-8">
                            <@selectPage id='department' name='department.id' init="true" selected="${teacher.department.id}:${teacher.department.name}" url=ctx+'/jiaowu/department/list/' nextId='major'/>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6">
                    <div class="form-group">
                        <label class="col-sm-4 control-label"><i class="text-danger">*</i> 专业：</label>
                        <div class="col-sm-8">
                            <@selectPage id='major' name='major.id' init="false" selected="${teacher.major.id}:${teacher.major.name}" url=ctx+'/jiaowu/major/list/'/>
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
                                                <#if (row.dictValue == teacher.sex)>selected</#if>>${row.dictLabel}</option>
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
                <label for="" class="col-sm-2 control-label"> 入职时间：</label>
                <div class="col-sm-10">
                    <div class="select-time">
                        <input type="text" class="time-input"
                               value="${teacher.teacher.workDate?string('yyyy-MM-dd')}" id="onceTime"
                               placeholder="入职时间"
                               name="teacher.workDate"/>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label "> 角色：</label>
                <div class="col-sm-10">
                    <div class="check-context" data-name="roleIds" data-label="roleName"
                         data-selected="${selected}"
                         data-url="/system/role/list">
                    </div>
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-2 control-label">备注：</label>
                <div class="col-sm-10">
                    <input id="remark" name="remark" class="form-control" type="text" value="${teacher.remark}">
                </div>
            </div>

            <#--<div class="form-group">-->
            <#--    <label class="col-sm-2 control-label">菜单权限:</label>-->
            <#--    <div class="col-sm-8">-->
            <#--        &lt;#&ndash;                    <div id="menuTrees" class="ztree"></div>&ndash;&gt;-->
            <#--        <!-- 按钮：用于打开模态框 &ndash;&gt;-->
            <#--        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal">-->
            <#--            打开模态框-->
            <#--        </button>-->
            <#--    </div>-->
            <#--    <div id="courseIds" data-name="courseIds" data-label="name"></div>-->
            <#--</div>-->

            <div class="form-group">
                <label class="col-sm-2 control-label "> 已选课：</label>
                <div class="col-sm-8 select-table table-striped">
                    <table class="col-sm-8" id="already-table" data-mobile-responsive="true"></table>
                </div>
            </div>




<#--    <div class="form-group">-->
<#--        <label class="col-sm-2 control-label "> 选课：</label>-->
<#--        <div class="col-sm-8 select-table table-striped"">-->

<#--        <table class="col-sm-8" id="no-select-table" data-mobile-responsive="true"></table>-->
<#--    </div>-->
<#--</div>-->
</form>
</div>
</div><!--/.container-div-->
<!-- 模态框 -->
<div class="modal fade" id="myModal">
    <div class="modal-dialog">
        <div class="modal-content">

            <!-- 模态框头部 -->
            <div class="modal-header">
                <h4 class="modal-title">模态框头部</h4>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>

            <!-- 模态框主体 -->
            <div class="modal-body">
                模态框内容..
                <div id="menuTrees" class="ztree"></div>
            </div>

            <!-- 模态框底部 -->
            <div class="modal-footer">
                <button type="button" onclick="coursetree()" class="btn btn-primary" data-dismiss="modal">确认</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">关闭</button>
            </div>

        </div>
    </div>
</div>
<#include "/common/stretch.ftl"/>
<script type="text/javascript">
    $(function () {
        loadAlreadyTable();
        loadSelectTable();
        loadztree();
        $.common.initBind();
    });
    let prefix = "/jiaowu/teacher"

    function loadztree() {
        let url = "/jiaowu/course/treelistpage?id=" + ${teacher.teacher.id};
        let options = {
            id: "menuTrees",
            url: url,
            check: { enable: true, nocheckInherit: true, chkboxType: { "Y": "ps", "N": "ps" } },
            expandLevel: 0
        };
        $.tree.init(options);
    };


    function coursetree(){

        let childNodes = $.tree.getCheckedNodes();
        console.log()
        $.ajax({
            cache : true,
            type : "POST",
            url : ctx + "/jiaowu/teacherCourse/add/" + ${teacher.teacher.id}+"/"+childNodes,
            data : {
                "cId": childNodes
            },
            async : false,
            error : function(request) {
                $.modal.alertError("系统错误");
            },
            success : function(data) {
                $('#already-table').bootstrapTable('refresh');
            }
        });
    }



    $("#form-teacher-edit").validate({
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
            "department.id": {
                required: true
            },
            "major.id": {
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
            "department.id": {
                required: "请选择系部"
            },
            "major.id": {
                required: "请选择专业"
            },
            "sex": {
                required: "请选择性别"
            },
        }
    });

    function loadAlreadyTable() {
        $('#already-table').bootstrapTable('destroy');

        let options = {
            url: '/jiaowu/teacherCourse/already/' + ${teacher.teacher.id},
            content: 'already-table',
            search: false,
            columns: [{
                field: 'params.name',
                title: '课程名称'
            }, {
                title: '操作',
                align: 'center',
                formatter: function (value, row, index) {
                    let actions = [];
                    actions.push('<a class="btn btn-danger btn-xs " href="#" onclick="remove(' + row.params.tcId + ', $.operate.ajaxSuccess)"><i class="fa fa-edit"></i>删除</a>');
                    return actions.join('');
                }
            }]
        }

        $.table.init(options);
        $.common.initBind();
    }

    <#--function loadSelectTable() {-->
    <#--    $('#no-select-table').bootstrapTable('destroy');-->

    <#--    let options = {-->
    <#--        url: '/jiaowu/teacherCourse/noSelect/' + ${teacher.teacher.id},-->
    <#--        content: 'no-select-table',-->
    <#--        search: false,-->
    <#--        columns: [{-->
    <#--            field: 'name',-->
    <#--            title: '课程名称'-->
    <#--        }, {-->
    <#--            title: '操作',-->
    <#--            align: 'center',-->
    <#--            formatter: function (value, row, index) {-->
    <#--                let actions = [];-->
    <#--                actions.push('<a class="btn btn-primary btn-edit btn-xs " href="#" onclick="add(' + row.id + ', $.operate.ajaxSuccess)"><i class="fa fa-remove"></i>添加</a>');-->
    <#--                return actions.join('');-->
    <#--            }-->
    <#--        }]-->
    <#--    }-->

    <#--    $.table.init(options);-->
    <#--    $.common.initBind();-->
    <#--}-->

    function submitHandler() {
        if ($.validate.form()) {
            $.operate.saveModal(prefix + "/edit", $('#form-teacher-edit').serialize());
        }
    }

    function remove(id) {
        $.ajax({
            cache : true,
            type : "POST",
            url : ctx + "/jiaowu/teacherCourse/remove",
            data : {
                "tcId": id
            },
            async : false,
            error : function(request) {
                $.modal.alertError("系统错误");
            },
            success : function(data) {
                loadztree();
                $('#already-table').bootstrapTable('refresh');
            }
        });
    }

    function add(id) {
        $.ajax({
            cache : true,
            type : "POST",
            url : ctx + "/jiaowu/teacherCourse/add/" + ${teacher.teacher.id},
            data : {
                "cId": id
            },
            async : false,
            error : function(request) {
                $.modal.alertError("系统错误");
            },
            success : function(data) {
                $('#no-select-table').bootstrapTable('refresh');
                $('#already-table').bootstrapTable('refresh');
            }
        });
    }

</script>
</body>
