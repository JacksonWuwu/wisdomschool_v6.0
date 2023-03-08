<#include "/common/style.ftl"/>
<body class="white-bg">
<div class="container-div clearfix">
    <div class="wrapper wrapper-content animated fadeInRight ibox-content">
        <form class="form-horizontal m"
              id="form-course-edit" method="post">
            <input id="id" name="id"
                   value="${teacherCourse.id}" type="hidden">
            <div class="form-group">
                <label for="name" class="col-sm-3 control-label">课程名称：</label>
                <div class="col-sm-8">
                    <input id="name"
                           value="${teacherCourse.course.name}" readonly class="form-control"
                           type="text">
                </div>
            </div>
<#--            <div class="form-group">-->
<#--                <label class="col-sm-3 control-label">课程简介：</label>-->
<#--                <div class="col-sm-8">-->
<#--                    <input id="courseBriefIntroduction" name="courseBriefIntroduction"-->
<#--                           value="${teacherCourse.courseBriefIntroduction}" class="form-control"-->
<#--                           type="text" maxlength="100" onkeyup="javascript:setShowLength2(this, 100, 'cost_tp2_title_length');">-->
<#--                    <span class="red" id="cost_tp2_title_length">还可以输入100个字数</span>-->
<#--                </div>-->
<#--            </div>-->
            <div class="form-group">
                <label class="col-sm-3 control-label" for="addBtn"> 考试任务： </label>

                <div class="col-sm-6">
<#--                    <button  onclick='addExam()' style='margin:3px 5px 0 5px;float:right;'>添加考试</button>-->


                        <input id="addBtn" onclick="addExam()" name="testPaperOne"  value="添加考试" type="button" >
                </div>
            </div>
        </form>
    </div>
</div><!--/.container-div-->
<#include "/common/stretch.ftl"/>
<script type="text/javascript">
    let prefix = "/teacher/course";
    $(function () {
        let initReviewUrl = $("#testPaperOneId").val();
        let options = {
            initPreviewUrl: initReviewUrl,
            content: 'addBtn'
        };
        $.upload.init(options);
        $.common.initBind();
    });
    $("#form-course-edit").validate({
        rules: {
            name: {
                required: true,
            },
        }
    });
    function addExam() {

        $.modal.open("添加课程考试", '/teacher/chapter/resource/listOne/${course.id}/4')

    }
    function submitHandler() {
        if ($.validate.form()) {
            $.operate.submitXHR(prefix + "/editexam", "#form-course-edit", $.operate.ajaxModalSuccess);
        }
    }

    // function setShowLength2(obj, maxlength, id) {
    //     var rem = maxlength - obj.value.length;
    //     var wid = id;
    //     if (rem < 0){
    //         rem = 0;
    //     }
    //     document.getElementById(wid).innerHTML = "还可以输入" + rem + "字数";
    // }
</script>
</body>
