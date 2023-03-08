<#include "/common/style.ftl"/>
<body class="white-bg">
<div class="container-div clearfix">
    <div class="wrapper wrapper-content animated fadeInRight ibox-content">
        <form class="form-horizontal m"<#-- action="/jiaowu/course/edit"--> enctype="multipart/form-data"
              id="form-course-edit" method="post">
            <input id="id" name="id"
                   value="${course.id}" type="hidden">
            <input name="review" id="thumbnail" type="hidden" value="${course.thumbnail}">
            <div class="form-group">
                <label for="name" class="col-sm-3 control-label">课程名称：</label>
                <div class="col-sm-8">
                    <input id="name" name="name"
                           value="${course.name}" class="form-control"
                           type="text">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label">学时：</label>
                <div class="col-sm-8">
                    <input id="period" name="period"
                           value="${course.period}" class="form-control"
                           type="number">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label">学分：</label>
                <div class="col-sm-8">
                    <input id="credit" name="credit"
                           value="${course.credit}" class="form-control"
                           type="number">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label"><i class="text-danger">*</i> 系别：</label>
                <div class="col-sm-8">
                    <@selectPage id='department' name='dept_id' init="true" selected="${course.department.id}:${course.department.name}" url=ctx+'/jiaowu/department/listpage' />
                </div>
            </div>
            <!--课程类型隐藏
            <div class="form-group">
                <label class="col-sm-3 control-label">课程类型：</label>
                <div class="col-sm-8">
                    <input id="courseType" name="courseType"
                           value="${course.courseType}" class="form-control"
                           type="text">
                </div>
            </div>-->
        </form>
    </div>
</div><!--/.container-div-->
<#include "/common/stretch.ftl"/>
<script type="text/javascript">
    let prefix = "/jiaowu/course";
    $(function () {
        let initReviewUrl = $("#thumbnail").val();
        let options = {
            initPreviewUrl: initReviewUrl,
            allowFileExt: ['jpg', 'png', 'bmp', 'jpeg'],
            content: 'multipartFile'
        };
        $.upload.init(options);
        $.common.initBind();
    });
    // $("#form-course-edit").validate({
    //     rules: {
    //         "name": {
    //             required: true,
    //             remote: {
    //                 url: prefix+"/checkCourseNameUnique",
    //                 type: "post",
    //                 dataType: "json",
    //                 data: {
    //                     "name": function () {
    //                         return $.common.trim($("#name").val());
    //                     }
    //                 }
    //             }
    //         }
    //     },
    //     messages: {
    //         "name": {
    //             remote: "该课程已存在"
    //         }
    //     }
    // });

    function submitHandler() {
        if ($.validate.form()) {
            $.operate.submitXHR(prefix + "/edit", "#form-course-edit", $.operate.ajaxModalSuccess);
        }
    }
</script>
</body>
