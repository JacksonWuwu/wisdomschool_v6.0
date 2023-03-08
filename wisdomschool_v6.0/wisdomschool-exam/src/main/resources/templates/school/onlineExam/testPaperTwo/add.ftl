<#include "/common/style.ftl"/>
<body class="white-bg">
<div class="container-div clearfix">
    <div class="wrapper wrapper-content animated fadeInRight ibox-content">
        <form class="form-horizontal m" id="form-testPaperOne-add">

            <div class="form-group">
                <label class="col-sm-3 control-label"><i class="text-danger">*</i>标题：</label>
                <div class="col-sm-8">
                    <input id="headline" name="headline" class="form-control" type="text">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label"><i class="text-danger">*</i>试卷名：</label>
                <div class="col-sm-8">
                    <input id="testName" name="testName" class="form-control" type="text">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label">备注：</label>
                <div class="col-sm-8">
                    <input id="beiZhu" name="beiZhu" class="form-control" type="text">
                    <input id="coursrId" name="coursrId" type="hidden" value="${id}">
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
    let prefix = "/school/onlineExam/testPaperOne";
    let coursrId="${id}";
    $("#form-testPaperOne-add").validate({
        rules: {
            testName: {
                required: true
            },

            headline: {
                required: true
            }
        }
    });

    function submitHandler() {
        if ($.validate.form()) {
            $.operate.saveModal(prefix + "/add", $('#form-testPaperOne-add').serialize());
        }
    }
</script>
</body>
