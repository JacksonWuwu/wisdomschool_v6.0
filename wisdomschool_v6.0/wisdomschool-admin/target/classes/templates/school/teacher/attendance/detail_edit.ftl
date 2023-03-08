<#include "/common/style.ftl"/>
<body class="white-bg">
<div class="container-div clearfix">
    <div class="wrapper wrapper-content animated fadeInRight ibox-content">
        <form class="form-horizontal m" id="form-clbum-edit">
            <input id="id" name="id"
                   value="${id}" type="hidden">
            <div class="form-group">
                <label class="col-sm-3 control-label"><i class="text-danger">*</i> 学号：</label>
                <div class="col-sm-8">
                    <input value="${attendanceDetail.loginName}" class="form-control" type="text" readonly="readonly">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label"><i class="text-danger">*</i> 姓名：</label>
                <div class="col-sm-8">
                    <input value="${attendanceDetail.userName}" class="form-control" type="text" readonly="readonly">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label">状态:</label>
                <div class="col-sm-8">
                    <label class="radio-box"> <input type="radio" name="results" value="0" <#if attendanceDetail.results == "0">checked</#if> /> 未签到 </label>
                    <label class="radio-box"> <input type="radio" name="results" value="1" <#if attendanceDetail.results == "1">checked</#if> /> 已签到 </label>
                </div>
            </div>


        </form>
    </div>
</div><!--/.container-div-->
<#include "/common/stretch.ftl"/>
<script type="text/javascript">
    let prefix = "/teacher/attendanceDetail";
    $(function () {
        $.common.initBind();
    });
    // $("#form-clbum-edit").validate({
    //     rules: {
    //         "type": {
    //             required: true
    //         },
    //         "state": {
    //             required: true
    //         },
    //     },
    //     messages: {
    //         "type": {
    //             remote: "该账号已存在"
    //         },
    //         "state": {
    //             remote: "该账号已存在"
    //         },
    //     }
    // });

    function submitHandler() {
        if ($.validate.form()) {
            $.operate.saveModal(prefix + "/edit", $('#form-clbum-edit').serialize());
        }
    }

</script>
</body>
