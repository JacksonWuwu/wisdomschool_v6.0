<#include "/common/style.ftl"/>
<body class="white-bg">
<div class="container-div clearfix">
    <div class="wrapper wrapper-content animated fadeInRight ibox-content">
        <form class="form-horizontal m" id="form-recourse-add">
            <input type="hidden" value="${tcid}" name="tcid">
            <div class="form-group">
                <label for="name" class="col-sm-3 control-label"><i class="text-danger">*</i>板块名称：</label>
                <div class="col-sm-6">
                    <input id="name" name="name" class=" form-control" type="text">
                </div>
            </div>
            <div class="form-group">
                <label for="description" class="col-sm-3 control-label">描述：</label>
                <div class="col-sm-6">
                    <input id="description" name="description" class=" form-control" type="text">
                </div>
            </div>
        </form>
    </div>
</div><!--/.container-div-->
<#include "/common/stretch.ftl"/>
<#--<script src="/js/recourse/recourse.js"></script>-->
<script type="text/javascript">

    $(function () {

        $.common.initBind();
    });
    let prefix = "/teacher/course/forum";
    $("#form-recourse-add").validate({
        rules: {
            xxxx: {
                required: true,
            },
        }
    });

    function submitHandler() {
        if ($.validate.form()) {
            $.operate.saveModal(prefix + "/add", $('#form-recourse-add').serialize());
        }
    }
</script>
</body>
