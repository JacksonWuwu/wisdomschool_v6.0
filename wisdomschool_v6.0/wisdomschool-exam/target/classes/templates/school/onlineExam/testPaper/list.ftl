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

    </div>

    <div class="col-sm-12 select-table table-striped">
        <table id="bootstrap-table" data-mobile-responsive="true"></table>
    </div>
</div>
<script type="text/javascript">
    let prefix = "/school/onlineExam/testPaper";

    $(function () {
        let options = {
            url: prefix + "/list/${cid}",
            createUrl: prefix + "/add/${cid}",
            updateUrl: prefix + "/edit/{id}",
            removeUrl: prefix + "/remove",
            sortName: "id",
            sortOrder: "desc",
            modalName: "试卷",
            columns: [{
                checkbox: true
            },
                {
                    field: 'id',
                    title: '编号',
                    sortable: true,
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
                    field: 'score',
                    title: '试卷总分'
                },
                {
                    field: 'state',
                    title: '是否已组卷',
                    formatter: function (value, row, index) {
                        if (row.state == 1) {
                            return '否';
                        } else return '是';
                    }
                },
                {
                    field: 'beiZhu',
                    title: '备注'
                },
                {
                    title: '操作',
                    align: 'center',
                    formatter: function (value, row, index) {
                        let actions = [];
                        <#--<@shiro.hasPermission name="teacher:course:view">
                        actions.push('<a class="btn btn-success btn-xs " href="#" onclick="showPaper(\'' + row.id + '\')"><i class="fa fa-edit"></i>预览</a> ');
                        </@shiro.hasPermission>-->

<#--                        <@shiro.hasPermission name="teacher:course:view">-->
                        if (row.state !== 2)
                            actions.push('<a class="btn btn-success btn-xs " href="#" onclick="Humanbuild(\'' + row.id + '\')"><i class="fa fa-edit"></i>组卷</a> ');
<#--                        </@shiro.hasPermission>-->
<#--                        <@shiro.hasPermission name="teacher:course:view">-->
                        actions.push('<a class="btn btn-success btn-xs " href="#" onclick="detail(\'' + row.id + '\')"><i class="fa fa-edit"></i>详情</a> ');
<#--                        </@shiro.hasPermission>-->
<#--                        <@shiro.hasPermission name="teacher:course:view">-->
                        actions.push('<a class="btn btn-success btn-xs " href="#" onclick="analyze(\'' + row.id + '\')"><i class="fa fa-edit"></i>分析</a>');
<#--                        </@shiro.hasPermission>-->
                        return actions.join('');
                    }
                }]
        };
        $.table.init(options);
        $.common.initBind();
    });

    function AGAbuild() {
        let cid = "${cid}";
        let id = $.common.isEmpty($.table._option.uniqueId) ? $.table.selectFirstColumns() : $.table.selectColumns($.table._option.uniqueId);
        if (id.length == 0) {
            $.modal.alertWarning("请至少选择一条记录");
            return;
        }
        $.modal.openFull('智能组卷', "/school/onlineExam/testPaper/AGAbuild?id=" + id+"&&cid="+cid, saveCallback);

    }


    function Humanbuild(id) {
        let cid = "${cid}";
        let result = $.modal.open('组卷', "/school/paper/build?id=" + id+"&&cid="+cid, function () {
            $.modal.alertSuccess("组卷成功");
        });
        if (result) {
            $.modal.alertSuccess("已更新试题列表");
        }
    }

    function detail(id) {
        $.modal.openFull( '详情', '/school/onlineExam/testPaper/showDetail?id=' + id, saveCallback);
    }

    function saveCallback(context) {
        console.log("context:" + 123);
    }

    function openwindow(id)
    {
        window.open('/school/onlineExam/testPaper/stuToTest?id='+id,'_blank');

    }

    function showPaper(id) {
        window.open('/school/onlineExam/course/showPaper?id='+id,'_blank');
    }

    function analyze(id) {
        var cid = "${cid}";
        $.modal.open('作业分析', "/school/onlineExam/testPaper/analyzePaper?id=" + id+"&&cid="+cid, saveCallback);
    }

</script>
