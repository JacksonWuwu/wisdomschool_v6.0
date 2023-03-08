<#include "/common/style.ftl"/>
<body class="white-bg">
<div class="container-div clearfix">
    <div class="wrapper wrapper-content animated fadeInRight ibox-content">
        <form class="form-horizontal m" id="form-testPaperTwo-edit">
            <input id="id" name="id"
                   value="${id}" type="hidden">
<#--            <div class="form-group">-->
<#--                <label class="col-sm-3 control-label">标题：</label>-->
<#--                <div class="col-sm-8">-->
<#--                    <input id="headline" name="headline"-->
<#--                           value="${testPaper.headline}" class="form-control"-->
<#--                           type="text">-->
<#--                </div>-->
<#--            </div>-->

            <div class="form-group">
                <label class="col-sm-3 control-label">试卷名：</label>
                <div class="col-sm-8">
                    <input id="name" name="name"
                           value="${testPaper.name}" class="form-control"
                           type="text" disabled>
                </div>
            </div>
            <div class="form-group">
                <label for="" class="col-sm-3 control-label"> 开始时间：</label>
                <div class="col-sm-8">
                    <div class="select-time">
                        <input type="text" class="time-input"
                               value="${(testPaper.createTime!'')?string("yyyy-MM-dd HH:mm:ss")}" id="createTime"
                               placeholder="开始时间"
                               name="createTime"/>

                    </div>
                </div>
            </div>
            <div class="form-group">
                <label for="" class="col-sm-3 control-label"> 结束时间：</label>
                <div class="col-sm-8">
                    <div class="select-time">
                        <input type="text" class="time-input"
                               value="${(testPaper.overTime!'')?string("yyyy-MM-dd HH:mm:ss")}" id="overTime"
                               placeholder="结束时间"
                               name="overTime"/>

                    </div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label">状态：</label>
                <div class="col-sm-8">
                    <input id="state" name="state"
                           value="${testPaper.state}" class="form-control"
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
    let prefix = "/school/onlineExam/testPaperTwo"
    $("#form-testPaperTwo-edit").validate({
        rules: {
            name: {
                required: true
            },
            createTime: {
                required: true
            },
            overTime: {
                required: true
            },
            state: {
                required: true
            },

        }
    });

    function submitHandler() {
        if ($.validate.form()) {
            $.operate.saveModal(prefix + "/edit", $('#form-testPaperTwo-edit').serialize());
        }
    }
</script>
</body>
