<#include "/common/style.ftl"/>
<link rel="stylesheet" href="/js/plugins/jquery-layout/jquery.layout-latest.css">
<body class="white-bg">
<div class="container-div clearfix main-content-inner">
    <div class="wrapper wrapper-content animated fadeInRight ibox-content">
        <form class="form-horizontal m" id="form-teacher-add">
            <div class="row">
                <div class="col-sm-6">
                    <div class="form-group">
                        <label class="col-sm-4 control-label "><i class="text-danger">*</i> 职工号：</label>
                        <div class="col-sm-8">
                            <input class="form-control" type="text" name="loginName" id="loginName"/>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6">
                    <div class="form-group">
                        <label class="col-sm-4 control-label"><i class="text-danger">*</i> 教师姓名：</label>
                        <div class="col-sm-8">
                            <input class="form-control" type="text" name="userName" id="userName">
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6">
                    <div class="form-group">
                        <label class="col-sm-4 control-label"><i class="text-danger">*</i> 所属院系：</label>
                        <div class="col-sm-8">
                            <@selectPage id='department' name='department.id' init="true" url=ctx+'/jiaowu/department/listpage' nextId='major'/>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6">
                    <div class="form-group">
                        <label class="col-sm-4 control-label"><i class="text-danger">*</i> 专业：</label>
                        <div class="col-sm-8">
                            <@selectPage id='major' name='major.id' init="false" url=ctx+'/jiaowu/major/listpage' nextId='clbum'/>
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
                <label for="" class="col-sm-2 control-label"> 入职时间：</label>
                <div class="col-sm-10">
                    <div class="select-time">
                        <input type="text" class="time-input" id="onceTime" placeholder="入职时间"
                               name="teacher.workDate"/>
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
                <div class="col-sm-10">
                    <label class="col-sm-2 control-label">添加课程:</label>
                    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal">
                    添加
                    </button>
                </div>
                <div id="courseIds" data-name="courseIds" data-label="name"></div>
            </div>

            <div class="form-group">
                <label class="col-sm-2 control-label">已选课：</label>
                <div class="col-sm-10">
                   <h5 id="course">暂无课程</h5>
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



<!-- 模态框 -->
<div class="modal fade" id="myModal">
    <div class="modal-dialog">
        <div class="modal-content">

            <!-- 模态框头部 -->
            <div class="modal-header">
                <h4 class="modal-title">选择课程</h4>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>

            <!-- 模态框主体 -->
            <div class="modal-body">
                所有课程：
                <div id="menuTrees" class="ztree"></div>
            </div>

            <!-- 模态框底部 -->
            <div class="modal-footer">
                <button type="button" onclick="loadCourse()" class="btn btn-primary" data-dismiss="modal">确认</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">关闭</button>
            </div>

        </div>
    </div>
</div>

<#include "/common/stretch.ftl"/>
<script type="text/javascript">
    $(function () {
        loadztree();

        $.common.initBind();
    });
    let prefix = "/jiaowu/teacher";

    function loadztree() {
        let url = "/jiaowu/course/treelistpage2";
        let options = {
            id: "menuTrees",
            url: url,
            check: { enable: true, nocheckInherit: true, chkboxType: { "Y": "ps", "N": "ps" } },
            expandLevel: 0
        };
        $.tree.init(options);
    };

    $("#form-teacher-add").validate({
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
            "department.id": {
                required: "请选择系部"
            },
            "major.id": {
                required: "请选择专业"
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
            add();
        }
    }

    function add() {
        let courseIds = $.tree.getCheckedNodes();
        //let textid=$.tree.children();
        //alert(textid);
        $.operate.saveModal(prefix + "/add", $('#form-teacher-add').serialize()+"&courseIds="+courseIds);
    }

    function loadCourse() {
        var treeObj=$.fn.zTree.getZTreeObj("menuTrees");
        nodes=treeObj.getCheckedNodes(true);
        v="";
        for(var i=0;i<nodes.length;i++){
            v+=nodes[i].name + ",";
        }
        v = v.substring(0, v.lastIndexOf(','));
        document.getElementById("course").innerHTML=v;

    }

</script>
</body>

