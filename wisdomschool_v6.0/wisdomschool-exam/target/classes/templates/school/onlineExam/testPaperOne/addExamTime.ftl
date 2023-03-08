<#include "/common/style.ftl"/>

<div class="container-div">
    <div class="col-sm-12 select-table table-striped">
        <form class="form-horizontal">
            <div class="form-group">
                <input type="hidden" value="${testPaperOneid}" id="testPaperOneid">
                <label for="inputTime" class="col-sm-2 control-label">增加考试时长/分钟</label>
                <div class="col-sm-6">
                    <input type="text" class="form-control" id="addTime">
                </div>
            </div>
        </form>
    </div>
</div>
<#include "/common/stretch.ftl"/>
<script src="/js/plugins/layui/layui.js"></script>
<script>
    function submitHandler () {
        var testPaperOneid=$("#testPaperOneid").val().trim();
        var addTime=$("#addTime").val().trim();
        option={
            'addTime':addTime,
            'testPaperOneid':testPaperOneid
        };
        $.operate.saveModal("/school/onlineExam/testPaperOne/addExamTime",$.param(option));
    }
</script>
