<#include "/common/style.ftl"/>
<body class="white-bg">
<div class="container-div clearfix">
    <div class="wrapper wrapper-content animated fadeInRight ibox-content">
        <form class="form-horizontal m" id="form-question-add">

            <div class="form-group">
                <label class="col-sm-3 control-label">标题：</label>
                <div class="col-sm-8">
                    <input id="title" name="title" class="form-control" type="text" value="${question.title}" readonly>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label">题型：</label>
                <div class="col-sm-8">
                    <input id="titleTypeName" name="titleTypeName" class="form-control" type="text" value="${question.titleTypeName}" readonly>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label">难度：</label>
                <div class="col-sm-8">
                    <input id="difficulty" name="difficulty" class="form-control" type="text" value="${question.difficulty}" readonly>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label">引用次数：</label>
                <div class="col-sm-8">
                    <input id="qexposed" name="qexposed" class="form-control" type="text" value="${question.qexposed}" readonly>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label"><i class="text-danger">*</i>分值：</label>
                <div class="col-sm-8">
                    <input id="questionscore" name="questionscore" class="form-control" value="${question.questionscore}" type="text">
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
    let prefix = "/school/paper/question";
    let coursrId="${id}";
    $("#form-question-add").validate({
        rules: {
            questionscore: {
                required: true
            },
            title: {
                required: true
            },
            titleTypeName: {
                required: true
            },
            difficulty: {
                required: true
            },
            qexposed: {
                required: true
            }
        }
    });

    function submitHandler() {
        if ($.validate.form()) {
            $.operate.saveModal(prefix + "/addTwo/${paperId}/${question.id}", $('#form-question-add').serialize());
        }
    }
</script>

</body>
