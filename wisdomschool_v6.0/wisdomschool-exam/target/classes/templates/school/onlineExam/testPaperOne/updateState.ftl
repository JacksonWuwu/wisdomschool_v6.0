<#include "/common/style.ftl"/>
<body class="white-bg">
<div class="container-div clearfix">
    <div class="wrapper wrapper-content animated fadeInRight ibox-content">
        <form class="form-horizontal m" id="form-testPaperOne-edit">
            <div class="form-group">
                <label class="col-sm-3 control-label">学号：</label>
                <div class="col-sm-8">
                    <input id="userId" name="userId" value="${userExam.userId}" class="form-control" type="text" readonly>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label">姓名：</label>
                <div class="col-sm-8">
                    <input id="stuName" name="stuName" value="${stuName}" class="form-control" type="text" readonly>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label">试卷名：</label>
                <div class="col-sm-8">
                    <input id="testName" name="testName" value="${testPaper.testName}" class="form-control" type="text" readonly />
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label">是否提交试卷：</label>
                <div class="col-sm-8">
                    <select style="width:220px;" id="sumScore" name="sumScore">
                        <option value="0">未提交</option>
                        <option value="1">已提交</option>
                    </select>
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
    let prefix = "/school/onlineExam/testPaperOne"
    $("#form-testPaperOne-edit").validate({
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
            $.operate.saveModal(prefix + "/updateStateEdit", $('#form-testPaperOne-edit').serialize());
        }
    }
</script>
</body>
