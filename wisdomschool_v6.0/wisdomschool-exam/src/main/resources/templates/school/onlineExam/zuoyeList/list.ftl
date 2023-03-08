<#include "/common/components/select.ftl"/>
<div class="container-div">
    <div class="btn-group-sm hidden-xs" id="toolbar" role="group">
<#--        <@shiro.hasPermission name="teacher:course:view">-->
            <a class="btn btn-success" onclick="$.operate.add()">
                <i class="fa fa-plus"></i> 新增
            </a>
<#--        </@shiro.hasPermission>-->

<#--        <@shiro.hasPermission name="teacher:course:view">-->
            <a class="btn btn-primary btn-edit disabled" onclick="$.operate.edit()">
                <i class="fa fa-edit"></i> 修改
            </a>
<#--        </@shiro.hasPermission>-->


<#--        <@shiro.hasPermission name="teacher:course:view">-->
            <a class="btn btn-primary btn-edit disabled" onclick="fabu()">
                <i class="fa fa-edit"></i> 发布
            </a>
<#--        </@shiro.hasPermission>-->

<#--        <@shiro.hasPermission name="teacher:course:view">-->
            <a class="btn btn-danger btn-del disabled" onclick="$.operate.removeAll()">
                <i class="fa fa-remove"></i> 删除
            </a>
<#--        </@shiro.hasPermission>-->
    </div>

    <div class="col-sm-12 select-table table-striped">
        <table id="bootstrap-table" data-mobile-responsive="true"></table>
    </div>
</div>
<script type="text/javascript">
    let prefix = "/school/onlineExam/zuoyeList";
    $(function () {
        let options = {
            url: prefix + "/list/${cid}",
            createUrl: prefix + "/add/${cid}",
            updateUrl: prefix + "/edit/{id}",
            removeUrl: prefix + "/remove",
            modalName: "试卷",
            columns: [{
                checkbox: true
            },
                {
                    field: 'id',
                    title: '编号',
                    formatter(value, row, index) {
                        return index+1;
                    }
                },
                {
                    field: 'headline',
                    title: '标题'
                },
                {
                    field: 'testName',
                    title: '作业名'
                },
                {
                    field: 'time',
                    title: '时长/分钟'
                },
                {
                    field: 'rule',
                    title: '组卷规则'
                },
                {
                    field: 'state',
                    title: '是否已组卷'
                },
                {
                    field: 'setExam',
                    title: '是否开始'
                },
                {
                    title: '操作',
                    align: 'center',
                    formatter: function (value, row, index) {
                        let actions = [];
<#--                        <@shiro.hasPermission name="teacher:course:view">-->
                        actions.push('<a class="btn btn-success btn-xs " href="#" onclick="Coursebuild(\'' + row.id + ',' + row.rule + '\')"><i class="fa fa-edit"></i>组卷</a> ');
<#--                        </@shiro.hasPermission>-->
                        <#--<@shiro.hasPermission name="teacher:course:view">-->
                        <#--actions.push('<a class="btn btn-success btn-xs " href="#" onclick="setExamY(\'' + row.id + '\')"><i class="fa fa-edit"></i>开始考试  </a> ');-->
                        <#--</@shiro.hasPermission>-->
                        <#--<@shiro.hasPermission name="teacher:course:view">-->
                        <#--actions.push('<a class="btn btn-success btn-xs " href="#" onclick="setExamN(\'' + row.id + '\')"><i class="fa fa-edit"></i>取消考试</a> ');-->
                        <#--</@shiro.hasPermission>-->
<#--                        <@shiro.hasPermission name="teacher:course:view">-->
                        actions.push('<a class="btn btn-success btn-xs " href="#" onclick="analyzeExam(\'' + row.id + '\')"><i class="fa fa-edit"></i>分析</a>');
<#--                        </@shiro.hasPermission>-->
                        return actions.join('');
                    }
                }]
        };
        $.table.init(options);
        $.common.initBind();
    });

    function buileTest() {
        let cid = "${id};"
        let id = $.common.isEmpty($.table._option.uniqueId) ? $.table.selectFirstColumns() : $.table.selectColumns($.table._option.uniqueId);
        if (id.length == 0) {
            $.modal.alertWarning("请至少选择一条记录");
            return;
        }
        $.modal.openFull('智能组卷', "/school/onlineExam/testPaper/buileTest?id=" + id, saveCallback);
    }

    function saveCallback(context) {
        console.log("context:" + 123);
    }

    function openwindow(id) {
        window.open('/school/onlineExam/testPaper/stuToTest?id=' + id, '_blank');

    }

    function Coursebuild(tid) {
        let cid = "${id}";
        var paper = tid.split(",");
        var pid = paper[0];
        var prule = paper[1];
        if (prule == "随机") {
            $.modal.openFull('随机组卷', "/school/onlineExam/courseExam/Coursebuild?id=" + pid, saveCallback);
        }else{
            console.log("固定");
            $.modal.openFull('固定组卷', "/school/onlineExam/testPaper/Humanbuild?id=" + pid+"&&cid="+cid, saveCallback);
        }
    }

    function Coursebuild2() {
        let cid = "${id}";
        let id = $.common.isEmpty($.table._option.uniqueId) ? $.table.selectFirstColumns() : $.table.selectColumns($.table._option.uniqueId);
        if (id.length == 0) {
            $.modal.alertWarning("请至少选择一条记录");
            return;
        }
        $.modal.openFull('课程组卷', "/school/onlineExam/courseExam/Coursebuild?id=" + id, saveCallback);

    }

    function setExamY(id) {
        // 移动
        $.modal.confirm("确认要开始?", function () {
            let data = {
                "ids": id
            };
            $.operate.submit("/school/onlineExam/courseExam/setExamY", "post", "json", data, true, function (result) {
                if (result.code === web_status.SUCCESS) {
                    $.modal.alertSuccess("操作成功！");
                } else {
                    $.modal.alertError("操作失败:");
                }

            });

        });
    }

    function setExamN(id) {
        // 移动
        $.modal.confirm("确认要取消?", function () {
            let data = {
                "ids": id
            };
            $.operate.submit("/school/onlineExam/courseExam/setExamN", "post", "json", data, true, function (result) {
                if (result.code === web_status.SUCCESS) {
                    $.modal.alertSuccess("操作成功！");
                } else {
                    $.modal.alertError("操作失败:");
                }

            });

        });
    }

    function analyzeExam(id) {
        var cid = "${id}";
        $.modal.open('试卷分析', "/school/onlineExam/courseExam/analyzePaper?id=" + id + "&&cid=" + cid, saveCallback);
    }

    function fabu() {
        <#--var cid = "${id}";-->
        <#--var id = $.common.isEmpty($.table._option.uniqueId) ? $.table.selectFirstColumns() : $.table.selectColumns($.table._option.uniqueId);-->
        <#--$.modal.open('发布', "/school/onlineExam/userTest/fabu?id=" + id+"&&cid="+cid, saveCallback);-->
        var cId = "${cid}";
        let prefix = "/school/onlineExam/userTest";
        $.modal.open('发布',  prefix + "/add/${cid}", saveCallback);

    }
</script>

<script>

</script>
