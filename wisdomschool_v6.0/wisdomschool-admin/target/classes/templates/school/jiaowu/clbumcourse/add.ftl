<#include "/common/style.ftl"/>
<body class="white-bg">
<div class="container-div clearfix">
    <div class="wrapper wrapper-content animated fadeInRight ibox-content">
        <form class="form-horizontal m" id="form-clbumCourse-add">
            <div class="row">
                <div class="col-sm-6">
                    <div class="form-group">
                        <label class="col-sm-4 control-label"><i class="text-danger">*</i> 年级：</label>
                        <div class="col-sm-8">
                            <@selectPage id='grades' name='grades.id' init="true" url=ctx+'/jiaowu/grades/listpage' />
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
                        <label class="col-sm-4 control-label"><i class="text-danger">*</i> 课程：</label>
                        <div class="col-sm-8">
                            <@selectPage id='course' name='course.id' init="true" url=ctx+'/jiaowu/course/listpage' nextId='teacher'/>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6">
                    <div class="form-group">
                        <label class="col-sm-4 control-label"><i class="text-danger">*</i> 授课教师：</label>
                        <div class="col-sm-8">
                            <@selectPage id='teacher' attrName='sysUser.userName' name='teacherCourse.id' init="false" url=ctx+'/jiaowu/clbumcourse/tclistpage' />
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
    let prefix = "/jiaowu/clbumcourse"

    function submitHandler() {
        if ($("#grades").val() == ""||$("#grades").val() == null) {
            layer.alert('请选择年级', {icon : 5,shift : 6, time : 0});
            return;
        }
        if ($("#department").val() == ""||$("#department").val() == null) {
            layer.alert('请选择系别', {icon : 5,shift : 6, time : 0});
            return;
        }
        if ($("#major").val() == ""||$("#major").val() == null) {
            layer.alert('请选择专业', {icon : 5,shift : 6, time : 0});
            return;
        }
        if ($("#clbum").val() == ""||$("#clbum").val() == null) {
            layer.alert('请选择班级', {icon : 5,shift : 6, time : 0});
            return;
        }
        if ($("#course").val() == ""||$("#course").val() == null) {
            layer.alert('请选择课程', {icon : 5,shift : 6, time : 0});
            return;
        }
        if ($("#teacher").val() == ""||$("#teacher").val() == null) {
            layer.alert('请选择授课老师', {icon : 5,shift : 6, time : 0});
            return;
        }
        if ($.validate.form()) {
            $.operate.saveModal(prefix + "/add", $('#form-clbumCourse-add').serialize());
        }
    }
</script>
</body>
