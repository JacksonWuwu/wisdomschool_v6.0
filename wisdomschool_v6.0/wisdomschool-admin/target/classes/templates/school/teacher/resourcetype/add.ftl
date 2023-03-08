<#include "/common/style.ftl"/>
<body class="white-bg">
<div class="container-div clearfix">
    <div class="wrapper wrapper-content animated fadeInRight ibox-content">
        <form class="form-horizontal m" id="form-recourse-add">
            <input type="hidden" value="${tcid}" name="tcId">
            <div class="form-group">
                <label for="fname" class="col-sm-3 control-label"><i class="text-danger">*</i>名称：</label>
                <div class="col-sm-6">
                    <input id="fname" name="name" class=" form-control" type="text">
                </div>
            </div>
            <div class="form-group">
                <label for="order" class="col-sm-3 control-label"><i class="text-danger">*</i>排序：</label>
                <div class="col-sm-6">
                    <input id="order" name="order" class=" form-control" type="number">
                </div>
            </div>
            <div class="form-group">
                <label for="remark" class="col-sm-3 control-label">备注：</label>
                <div class="col-sm-6">
                    <input id="remark" name="remark" class=" form-control" type="text">
                </div>
            </div>
        </form>
    </div>
</div><!--/.container-div-->
<#include "/common/stretch.ftl"/>
<#--<script src="/js/recourse/recourse.js"></script>-->
<script type="text/javascript">
    //便于检索，采用对象形式存储

    function checkType(type) {
        return true;
    }

    $(function () {
        $.common.initBind();
    });
    let prefix = "/recourse/type/${id}";
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
