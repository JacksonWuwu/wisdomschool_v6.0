<#include "/common/style.ftl"/>
<body class="white-bg">
<div class="container-div clearfix">
    <div class="wrapper wrapper-content animated fadeInRight ibox-content">
        <form class="form-horizontal m" id="form-testPaper-edit">
            <input id="id" name="id"
                   value="${id}" type="hidden">
            <div class="form-group">
                <label class="col-sm-3 control-label">标题：</label>
                <div class="col-sm-8">
                    <input id="headline" name="headline"
                           value="${testPaper.headline}" class="form-control"
                           type="text">
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-3 control-label">试卷名：</label>
                <div class="col-sm-8">
                    <input id="testName" name="testName"
                           value="${testPaper.testName}" class="form-control"
                           type="text">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label">备注：</label>
                <div class="col-sm-8">
                    <input id="beiZhu" name="beiZhu"
                           value="${testPaper.beiZhu}" class="form-control"
                           type="text">
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
    let prefix = "/school/onlineExam/testPaper"
    $("#form-testPaper-edit").validate({
        rules: {
            headline: {
                required: true
            },
            testName: {
                required: true
            }

        }
    });

    function submitHandler() {
        if ($.validate.form()) {
            $.operate.saveModal(prefix + "/edit", $('#form-testPaper-edit').serialize());
        }
    }
</script>
</body>
