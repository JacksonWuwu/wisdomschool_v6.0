<#include "/common/style.ftl"/>
<body class="white-bg">
<div class="container-div clearfix">
    <div class="wrapper wrapper-content animated fadeInRight ibox-content">
        <form class="form-horizontal m" id="form-myTitleType-add">

                    <div class="form-group">
                        <label class="col-sm-3 control-label">课程名称：</label>
                        <div class="col-sm-8">
                            <label class="col-sm-3 control-label">${course.name}</label>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-3 control-label"><i class="text-danger">*</i>题型名：</label>
                        <div class="col-sm-8">
                            <input id="titleTypeName" name="titleTypeName" class="form-control" type="text">
                        </div>
                    </div>


                    <div class="form-group">
                        <label class="col-sm-3 control-label"><i class="text-danger">*</i> 平台题型：</label>
                        <div class="col-sm-8">
                            <@selectPage id='publicTitleId' name='publicTitleId' init="true" url=ctx+'/school/onlineExam/titleType/list'/>
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
    $("#form-myTitleType-add").validate({
        rules: {
            "titleTypeName": {
                required: true,
            },
            "publicTitleId": {
                required: true
            },
        }
    });

    function submitHandler() {
        let titleTypeName = document.getElementById("titleTypeName").value;
        let publicTitleId = document.getElementById("publicTitleId").value;
        let cid = ${course.id};
        if ($.validate.form()) {
            $.operate.saveModal(prefix + "/add", $.param({
                "titleTypeName":titleTypeName,
                "publicTitleId":publicTitleId,
                "cid":cid
                }));
        }
    }
</script>
</body>
