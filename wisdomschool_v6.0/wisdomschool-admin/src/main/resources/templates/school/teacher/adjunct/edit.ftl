<#include "/common/style.ftl"/>
<body class="white-bg">
<div class="container-div clearfix">
    <div class="wrapper wrapper-content animated fadeInRight ibox-content">
        <form class="form-horizontal m" id="form-clbum-edit">
            <input id="id" name="id"
                   value="${id}" type="hidden">
            <div class="form-group">
                <label class="col-sm-3 control-label">*作业名称:</label>
                <div class="col-sm-8">
                    <input id="adjunctname" name="adjunctname"
                           value="${adjunct.adjunctname}" class="form-control"
                           type="text">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label"><i class="text-danger">*</i>结束时间:</label>
                <div class="col-sm-8">
                    <#--                    <input type="datetime-local" class="time-input" id="deadline" placeholder="有效时段" name="deadline"/>-->
                    <input class="start-two" type="text" value="${adjunct.deadline}" name="deadline"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label"><i class="text-danger">*</i>作业内容:</label>
                <div class="col-sm-8">
                   <textarea id="jobcontent" class="form-control " name="jobcontent" value="${adjunct.jobcontent}"
                             rows="10" placeholder="作业详细内容"
                   ></textarea>
                </div>
            </div>

        </form>
    </div>
</div><!--/.container-div-->
<#include "/common/stretch.ftl"/>
<script src="/js/borain-timeChoice.js"></script>
<script type="text/javascript">
    let prefix = "/teacher/adjunct";
    $(function () {
        document.getElementById("jobcontent").value="${adjunct.jobcontent}"
        $.common.initBind();
    });
    onLoadTimeChoiceDemo();
    borainTimeChoice({
        start:".start-two",
        end:"",
        level:"H",
        less:false
    });
    $("#form-clbum-edit").validate({
        rules: {
            "type": {
                required: true
            },
            "state": {
                required: true
            },
        },
        messages: {
            "type": {
                remote: "该账号已存在"
            },
            "state": {
                remote: "该账号已存在"
            },
        }
    });

    function submitHandler() {
        if ($.validate.form()) {
            $.operate.saveModal(prefix + "/edit", $('#form-clbum-edit').serialize());
        }
    }

</script>
</body>
