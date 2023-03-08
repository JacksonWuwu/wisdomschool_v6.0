<div class="container-div">
    <div class="btn-group-sm hidden-xs" id="toolbar" role="group">
<#--        </@shiro.hasPermission name="teacher:course:view">-->
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
            <a class="btn btn-primary btn-edit disabled" onclick="Humanbuild()">
                <i class="fa "></i> 手动组卷
            </a>
<#--        </@shiro.hasPermission>-->
        <#-- <@shiro.hasPermission name="teacher:course:view">
             <a class="btn btn-primary btn-edit disabled" onclick="Coursebuild()">
                 <i class="fa "></i> 课程考试组卷
             </a>
         </@shiro.hasPermission>-->
    </div>

    <div class="col-sm-12 select-table table-striped">
        <table id="bootstrap-table" data-mobile-responsive="true"></table>
    </div>
</div>
<script type="text/javascript">
    let prefix = "/school/onlineExam/testPaper";
    var  cid="${id}";
    $(function () {
        console.log("huangzhihao+"+cid);
        let options = {
            url: prefix + "/homework/list/${id}",
            createUrl: prefix + "/homework/add/${id}",
            updateUrl: prefix + "/homework/edit/{id}",
            removeUrl: prefix + "/homework/remove",
            sortName: "id",
            sortOrder: "desc",
            modalName: "试卷",
            columns: [{
                checkbox: true
            },
                {
                    field: 'id',
                    title: '编号'
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
                    field: 'score',
                    title: '作业总分'
                },
                {
                    field: 'state',
                    title: '是否已组卷'
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
<#--                        <@shiro.hasPermission name="teacher:course:view">-->
                        actions.push('<a class="btn btn-success btn-xs " href="#" onclick="showPaper(\'' + row.id + '\')"><i class="fa fa-edit"></i>预览</a> ');
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
        let cid = "${id}";
        let id = $.common.isEmpty($.table._option.uniqueId) ? $.table.selectFirstColumns() : $.table.selectColumns($.table._option.uniqueId);
        if (id.length == 0) {
            $.modal.alertWarning("请至少选择一条记录");
            return;
        }
        $.modal.openFull('智能组卷', "/school/onlineExam/testPaper/AGAbuild?id=" + id+"&&cid="+cid, saveCallback);

    }


    function Humanbuild() {
        let cid = "${id}";
        let id = $.common.isEmpty($.table._option.uniqueId) ? $.table.selectFirstColumns() : $.table.selectColumns($.table._option.uniqueId);
        if (id.length == 0) {
            $.modal.alertWarning("请至少选择一条记录");
            return;
        }
        $.modal.openFull('手动组卷', "/school/onlineExam/testPaper/Humanbuild?id=" + id+"&&cid="+cid, saveCallback);
    }
    function detail(id) {
        window.open( '/school/onlineExam/testPaper/showDetail?id=' + id, '_blank');

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
        var cid = "${id}";
        $.modal.open('作业分析', "/school/onlineExam/testPaper/analyzePaper?id=" + id+"&&cid="+cid, saveCallback);
    }

</script>
