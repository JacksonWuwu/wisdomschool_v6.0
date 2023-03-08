<#include "/common/style.ftl"/>
<body class="white-bg">
<div class="container-div clearfix">
    <div class="wrapper wrapper-content animated fadeInRight ibox-content">
        <form class="form-horizontal m" id="form-myTitleType-edit" >
            <input id="id" name="id"
                   value="${id}" type="hidden">

                    <div class="form-group">
                        <label class="col-sm-3 control-label">课程名：</label>
                        <div class="col-sm-8">
                            <label class="col-sm-3 control-label">${myTitleType.cname}</label>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">题型名：</label>
                        <div class="col-sm-8">
                            <input id="titleTypeName" name="titleTypeName"
                                   value="${myTitleType.titleTypeName}" class="form-control"
                                   type="text">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label"><i class="text-danger">*</i> 平台题型：</label>
                        <div class="col-sm-8">
                            <@selectPage id='publicTitleId' name='publicTitleId' init="true" selected="${titleType.id}:${titleType.name}" url=ctx+'/school/onlineExam/titleType/list'/>
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
    let prefix = "/school/onlineExam/myTitleType"
    $("#form-myTitleType-edit").validate({
        titleTypeName: {
            titleTypeName: {
                required: true,
            },
        }
    });
    function submitHandler() {
        if ($.validate.form()) {
            $.operate.saveModal(prefix + "/edit", $('#form-myTitleType-edit').serialize());
        }
    }
</script>
</body>
