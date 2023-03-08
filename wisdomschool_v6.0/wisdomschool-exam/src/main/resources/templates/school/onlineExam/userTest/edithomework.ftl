<#include "/common/style.ftl"/>
<body class="white-bg">
<div class="container-div clearfix">
    <div class="wrapper wrapper-content animated fadeInRight ibox-content">
        <form class="form-horizontal m" id="form-userTest-edit">
            <input id="id" name="id"
                   value="${userTest.id}" type="hidden">
            <div class="row">
                <div class="form-group">
                    <label class="col-sm-3 control-label">创建时间：</label>
                    <input type="text" name="stuStartTime" style="width:255px;" class="time-input"
                           id="startTime" placeholder="开始时间" value="${userTest.stuStartTime}"/>
                    <span>-</span>
                    <input type="text" name="stuEndTime" style="width:255px;" class="time-input"
                           id="endTime" placeholder="结束时间" value="${userTest.stuEndTime}"/>
                </div>
            </div>
            <div class="row">
                <div class="form-group">
                    <label class="col-sm-3 control-label">作业：</label>
                    <div class="col-sm-8">
                        <@selectPage id='testPaperId' init="true" name='testPaperId'  selected=testPaper url=ctx+'/school/onlineExam/testPaper/list/${cid}'/>
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
    let prefix = "/school/onlineExam/userTest/homework";
    $("#form-userTest-edit").validate({
        rules: {
        }
    });
    function submitHandler() {
        if ($.validate.form()) {
            $.operate.saveModal(prefix + "/edit", $('#form-userTest-edit').serialize());
        }
    }
</script>
</body>
