<#include "/common/style.ftl"/>
<link rel="stylesheet" href="/js/onlineExam/css/demo.css" type="text/css">
<link rel="stylesheet" href="/js/onlineExam/css/zTreeStyle/zTreeStyle.css" type="text/css">
<link href="/js/plugins/layui/css/modules/laydate/default/laydate.css" rel="stylesheet" type="text/css" />
<link href="/js/plugins/layui/css/layui.css" rel="stylesheet" type="text/css" />
<body class="white-bg">
<div class="container-div clearfix">
    <div class="wrapper wrapper-content animated fadeInRight ibox-content">
        <form class="form-horizontal m" id="form-userTest-add">

            <div class="row">
                <div class="form-group">
                    <label class="col-sm-3 control-label">设置时间</label>
                          <input type="text" class="time-input"  style="width:215px;" id="startTime" name="startTime" placeholder="开始时间"/>
                            <span>-</span>
                            <input type="text" class="time-input"  style="width:215px;" id="endTime" name="endTime" placeholder="结束时间"/>
                </div>
            </div>
        </form>
    </div>
</div>
<!--/.container-div-->
<#include "/common/stretch.ftl"/>
<script src="/js/plugins/layui/layui.js"></script>
<script type="text/javascript">

    layui.use('laydate', function(){
        var laydate = layui.laydate;
        laydate.render({
            elem: '#startTime' //指定元素
            ,type: 'datetime'
        });
        laydate.render({
            elem: '#endTime' //指定元素
            ,type: 'datetime'
        });
    });

    var cId = "${cid}";
    var tid = "${tid}";
    var stuId = "${stuId}";
    let prefix = "/school/onlineExam/userTest";

    /**
     * 时间选择变量
     */
    var startTime;
    var endTime;

    function submitHandler() {
        let cId = "${cid}";
        let userId = "${userId}";

        if ($.validate.form()) {
            submitTime();
        }
    }


    /*
     * 保存时间
     */

    function submitTime() {
        let startTime = $("input[name='startTime']").val();
        let endTime = $("input[name='endTime']").val();
        $.ajax({
            type: 'POST',
            url: '/school/onlineExam/courseExam/setExamTime/' + ${paperId},
            dataType: 'json',
            data: {
                "startTime": startTime,
                "endTime": endTime
            },
            success: function (data) {
                if (data.code == web_status.SUCCESS) {
                    $.operate.alertSuccess("更新成功");
                    $.treeTable.refresh();
                }
            },
            error: function () {
                console.log("设置考试时间失败!");
            }
        });
        // $.operate.postJson("/school/onlineExam/courseExam/setExamTime", {
        //     "startTime": startTime,
        //     "endTime": endTime,
        //     "id": testPaperId
        // }, true, $.operate.ajaxModalSuccess)
    }

    (function () {
        var rule="${rule}";
        console.log("11111"+rule);
        if(rule=="1"){
            console.log("11111");
            $('#form-userTest-add').empty();
            $('#form-userTest-add').append("<div>" +
                    "<h3>改试卷的组卷模式为[随机]模式不需要发布</h3>" +
                    "</div>");
        }
    })();

</script>

</body>
