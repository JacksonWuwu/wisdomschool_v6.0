<#include "/common/style.ftl"/>
<body class="white-bg">
<div class="container-div clearfix">
    <div class="wrapper wrapper-content animated fadeInRight ibox-content">
        <form class="form-horizontal m" id="form-testPaper-add">

            <div class="form-group">
                <label class="col-sm-3 control-label">标题：</label>
                <div class="col-sm-8">
                    <input id="headline" name="headline" class="form-control" type="text">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label">试卷名：</label>
                <div class="col-sm-8">
                    <input id="testName" name="testName" class="form-control" type="text">
                </div>
            </div>
            <div class="row">
                <div class="form-group">
                    <label class="col-sm-3 control-label">时间设置：</label>
                    <input type="text" name="startTime" style="width:255px;" class="time-input"
                           id="startTime" placeholder="开始时间"/>
                    <span>-</span>
                    <input type="text" name="endTime" style="width:255px;" class="time-input"
                           id="endTime" placeholder="结束时间"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label">组卷规则：</label>
                <div class="col-sm-8">
                    <select style="width:220px;" id="rule" name="rule">
                        <option value="0">固定</option>
                        <option value="1">随机</option>
                    </select>
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
    let prefix = "/school/onlineExam/frontExam";
    let coursrId="${id}";
    $("#form-testPaper-add").validate({
        rules: {
            testName: {
                required: true
            },
            headline: {
                required: true
            },
            startTime: {
                required: true,
            },
            endTime: {
                required: true,
            },
            rule: {
                required: true
            }
        }
    });

    function submitHandler() {
        if ($.validate.form()) {
            $.operate.saveModal(prefix + "/add", $('#form-testPaper-add').serialize());
        }
    }
</script>
</body>
