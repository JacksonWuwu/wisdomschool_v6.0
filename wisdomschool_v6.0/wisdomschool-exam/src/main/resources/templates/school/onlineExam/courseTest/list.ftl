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
            <a class="btn btn-danger btn-del disabled" onclick="$.operate.removeAll()">
                <i class="fa fa-remove"></i> 删除
            </a>
<#--        </@shiro.hasPermission>-->
<#--        <@shiro.hasPermission name="teacher:course:view">-->
            <a class="btn btn-primary btn-edit disabled" onclick="Coursebuild()">
                <i class="fa "></i> 课程考试组卷
            </a>
<#--        </@shiro.hasPermission>-->
    </div>

    <div class="col-sm-12 select-table table-striped">
        <table id="bootstrap-table" data-mobile-responsive="true"></table>
    </div>
</div>
<script type="text/javascript">
    let prefix = "/school/onlineExam/courseTest";
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
                    title: '试卷名'
                },
                {
                    field: 'startTime',
                    title: '开始时间'
                },
                {
                    field: 'endTime',
                    title: '结束时间'
                },
                {
                    title: '操作',
                    align: 'center',
                    formatter: function (value, row, index) {
                        let actions = [];
<#--                        <@shiro.hasPermission name="teacher:course:view">-->
                        actions.push('<a class="btn btn-success btn-xs " href="#" onclick="openwindow(\'' + row.id + '\')"><i class="fa fa-edit"></i>开始考试</a> ');
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

    function openwindow(id)
    {
        window.open('/school/onlineExam/testPaper/stuToTest?id='+id,'_blank');

    }
    function Coursebuild() {
        let cid = "${id}";
        let id = $.common.isEmpty($.table._option.uniqueId) ? $.table.selectFirstColumns() : $.table.selectColumns($.table._option.uniqueId);
        if (id.length == 0) {
            $.modal.alertWarning("请至少选择一条记录");
            return;
        }
        $.modal.openFull('课程组卷',"/school/onlineExam/testPaper/Coursebuild?id="+ id , saveCallback);
    }
</script>
