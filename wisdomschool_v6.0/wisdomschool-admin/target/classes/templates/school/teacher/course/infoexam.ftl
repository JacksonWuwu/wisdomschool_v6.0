<div class="container-div layout-container-div clearfix">
    <div class="row">
        <div class="ibox float-e-margins">
            <div class="ibox-title">
                <h3 class="pull-left">考试分配</h3>
                <button class="btn btn-default pull-right pb-5" onclick="toEdit()"><i class="fa fa-pencil"></i> 添加
                </button>
            </div>
            <div class="ibox-content">
                <div class="ibox-item">
                    <span class="ibox-item-title"><i class="fa fa-tags"></i> 课程名称：</span>
                    <div class="well"><span class="courseName">${teacherCourse.course.name}</span></div>
                </div>
                <div class="ibox-item">
                    <span class="ibox-item-title"><i class="fa fa-picture-o"></i> 考试任务：</span>
                        <div class="well">

                        </div>
            </div>
        </div>
    </div>
</div><!--/.container-div-->
<script>
    let prefix = ctx + "/teacher/course";

    $(function () {

    })
    function toEdit() {
        $.modal.openFull("分配【${teacherCourse.course.name}】课程考试", prefix + "/editexam/${course.id}", function (result) {
            console.log(result);
        });
    }
</script>