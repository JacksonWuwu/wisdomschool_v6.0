<div class="container-div layout-container-div clearfix">
    <div class="row">
        <div class="ibox float-e-margins">
            <div class="ibox-title">
                <h3 class="pull-left">课程信息</h3>
                <button class="btn btn-default pull-right pb-5" onclick="toEdit()"><i class="fa fa-pencil"></i> 编辑
                </button>
            </div>
            <div class="ibox-content">
                <div class="ibox-item">
                    <span class="ibox-item-title"><i class="fa fa-tags"></i> 课程名称：</span>
                    <div class="well"><span class="courseName">${teacherCourse.course.name}</span></div>
                </div>
                <div class="ibox-item">
                    <span class="ibox-item-title"><i class="fa fa-file-text"></i> 课程简介：</span>
                    <div class="well"><p>${teacherCourse.courseBriefIntroduction}</div>
                    </p>
                </div>
                <div class="ibox-item">
                    <span class="ibox-item-title"><i class="fa fa-picture-o"></i> 预览图：</span>
                    <div class="well"><img
                                src="${storage}/showCondensedPicture?fileId=${teacherCourse.thumbnailPath}"
                                alt="预览图"></div>
                </div>
            </div>
        </div>
    </div>
</div><!--/.container-div-->
<script>
    let prefix = "/teacher/course";
    $(function () {
    })

    function toEdit() {

        $.modal.open("修改【${teacherCourse.course.name}】课程", prefix + "/edit/${teacherCourse.course.id}", function (result) {
            console.log(result);
        });
    }
</script>
