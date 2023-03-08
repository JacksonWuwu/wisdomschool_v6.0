<#include "/common/style.ftl"/>
<body class="white-bg">
<div class="container-div clearfix">
    <div class="wrapper wrapper-content animated fadeInRight ibox-content">
        <form class="form-horizontal m" id="form-clbumCourse-edit">
            <input id="id" name="id" value="${clbumCourse.id}" type="hidden">
            <div class="row">
                <div class="col-sm-6">
                    <div class="form-group">
                        <label class="col-sm-4 control-label"><i class="text-danger">*</i> 年级：</label>
                        <div class="col-sm-8">
                            <@selectPage id='grades' name='grades.id' init="true" selected="${clbumCourse.grades.id}:${clbumCourse.grades.name}" url=ctx+'/jiaowu/grades/listpage' />
                        </div>
                    </div>
                </div>
                <div class="col-sm-6">
                    <div class="form-group">
                        <label class="col-sm-4 control-label"><i class="text-danger">*</i> 系别：</label>
                        <div class="col-sm-8">
                            <@selectPage id='department' name='department.id' init="true" selected="${clbumCourse.department.id}:${clbumCourse.department.name}" url=ctx+'/jiaowu/department/listpage' nextId='major'/>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6">
                    <div class="form-group">
                        <label class="col-sm-4 control-label"><i class="text-danger">*</i> 专业：</label>
                        <div class="col-sm-8">
                            <@selectPage id='major' name='major.id' init="false" selected="${clbumCourse.major.id}:${clbumCourse.major.name}" url=ctx+'/jiaowu/major/listpage' nextId='clbum'/>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6">
                    <div class="form-group">
                        <label class="col-sm-4 control-label"><i class="text-danger">*</i> 班级：</label>
                        <div class="col-sm-8">
                            <@selectPage id='clbum' name='clbum.id' init="false" selected="${clbumCourse.clbum.id}:${clbumCourse.clbum.name}" url=ctx+'/jiaowu/clbum/listpage'/>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6">
                    <div class="form-group">
                        <label class="col-sm-4 control-label"><i class="text-danger">*</i> 课程：</label>
                        <div class="col-sm-8">
                            <@selectPage id='course' name='course.id' init="true" selected="${clbumCourse.course.id}:${clbumCourse.course.name}" url=ctx+'/jiaowu/course/listpage' nextId='teacher'/>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6">
                    <div class="form-group">
                        <label class="col-sm-4 control-label"><i class="text-danger">*</i> 授课教师：</label>
                        <div class="col-sm-8">
                            <@selectPage id='teacher' attrName='sysUser.userName' name='teacherCourse.id' init="false" selected="${clbumCourse.teacherCourse.id}:${clbumCourse.sysUser.userName}" url=ctx+'/jiaowu/clbumcourse/tclistpage' />
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
        if ($.validate.form()) {
            $.operate.saveModal(prefix + "/edit", $('#form-clbumCourse-edit').serialize());
        }
    }
</script>
</body>
