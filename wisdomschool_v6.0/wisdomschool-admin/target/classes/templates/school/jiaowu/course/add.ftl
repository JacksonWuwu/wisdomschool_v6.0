<#include "/common/style.ftl"/>
<body class="white-bg">
<div class="container-div clearfix">
    <div class="wrapper wrapper-content animated fadeInRight ibox-content">
        <form class="form-horizontal m" id="form-course-add">
            <div class="form-group">
                <label class="col-sm-3 control-label">课程名称：</label>
                <div class="col-sm-8">
                    <input id="name" name="name" class="form-control" type="text">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label">学时：</label>
                <div class="col-sm-8">
                    <input id="period" name="period" class="form-control" type="number">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label">学分：</label>
                <div class="col-sm-8">
                    <input id="credit" name="credit" class="form-control" type="number">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label"><i class="text-danger">*</i> 系别：</label>
                <div class="col-sm-8">
                    <@selectPage id='department' name='department.id' init="true" url=ctx+'/jiaowu/department/listpage' />
                </div>
            </div>
        </form>
    </div>
</div><!--/.container-div-->
<#include "/common/stretch.ftl"/>
<script type="text/javascript">
    let prefix = "/jiaowu/course";
    $(function () {
        let options = {
            allowFileExt: ['jpg', 'png', 'bmp', 'jpeg'],
            content: 'multipartFile'
        };
        $.upload.init(options);
        $.common.initBind();
    });
    $("#form-course-add").validate({
        rules: {
            "name": {
                required: true,
                remote: {
                    url: prefix+"/checkCourseNameUnique",
                    type: "post",
                    dataType: "json",
                    data: {
                        "name": function () {
                            return $.common.trim($("#name").val());
                        }
                    }
                }
            }
        },
        messages: {
            "name": {
                remote: "该课程已存在"
            }
        }
    });

    function submitHandler() {
        if ($("#period").val() == "") {
            layer.alert('请填写学时', {icon : 5,shift : 6, time : 0});
            return;
        }
        if ($("#credit").val() == "") {
            layer.alert('请填写学分', {icon : 5,shift : 6, time : 0});
            return;
        }
        if ($.validate.form()) {
            $.operate.saveModal(prefix + "/add", $('#form-course-add').serialize());
        }
    }
</script>
</body>
