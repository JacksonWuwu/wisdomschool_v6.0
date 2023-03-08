<div class="container-div">
    <div class="btn-group-sm hidden-xs" id="toolbar" role="group">
<#--        <@shiro.hasPermission name="school:onlineExam:course:view">-->
            <a class="btn btn-success" onclick="$.operate.add()">
                <i class="fa fa-plus"></i> 新增
            </a>
<#--        </@shiro.hasPermission>-->
<#---->
<#--        <@shiro.hasPermission name="school:onlineExam:course:view">-->
            <a class="btn btn-primary btn-edit disabled" onclick="$.operate.edit()">
                <i class="fa fa-edit"></i> 修改
            </a>
<#--        </@shiro.hasPermission>-->
<#--        <@shiro.hasPermission name="school:onlineExam:course:view">-->
            <a class="btn btn-danger btn-del disabled" onclick="$.operate.removeAll()">
                <i class="fa fa-remove"></i> 删除
            </a>
<#--        </@shiro.hasPermission>-->
<#--        <@shiro.hasPermission name="school:onlineExam:course:view">-->
            <a class="btn btn-primary btn-edit disabled" onclick="AGABuileTest()">
                <i class="fa "></i> 智能组卷
            </a>
<#--        </@shiro.hasPermission>-->
<#--        <@shiro.hasPermission name="school:onlineExam:course:view">-->
            <a class="btn btn-primary btn-edit disabled" onclick="HumanBuileTest()">
                <i class="fa "></i> 手动组卷
            </a>
<#--        </@shiro.hasPermission>-->
<#--        <@shiro.hasPermission name="school:onlineExam:course:view">-->
            <a class="btn btn-primary btn-edit disabled" onclick="Coursebuild()">
                <i class="fa "></i> 新智能组卷
            </a>
<#--        </@shiro.hasPermission>-->
    </div>

    <div class="col-sm-12 select-table table-striped">
        <table id="bootstrap-table" data-mobile-responsive="true"></table>
    </div>
</div>
<script type="text/javascript">
    let prefix = "/school/onlineExam/course";
    let cid = "${id}";
    $(function () {
        let options = {
            url: prefix + "/PaperList/${id}",
            createUrl: prefix + "/Paperadd/${id}",
            updateUrl: prefix + "/Paperedit/{id}",
            removeUrl: prefix + "/remove",
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
                    title: '试卷名'
                },
                {
                    field: 'score',
                    title: '试卷总分'
                },
                {
                    field: 'state',
                    title: '是否已组卷'
                },
                {
                    field: 'createTime',
                    title: '创建时间',
                    sortable: true
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
                        actions.push('<a class="btn btn-success btn-xs " href="#" onclick="openwindow(\'' + row.id + '\')"><i class="fa fa-edit"></i>试卷分析</a> ');
<#--                        </@shiro.hasPermission>-->
<#--                        <@shiro.hasPermission name="teacher:course:view">-->
                        actions.push('<a class="btn btn-success btn-xs " href="#" onclick="showPaper(\'' + row.id + '\')"><i class="fa fa-edit"></i>预览</a> ');
<#--                        </@shiro.hasPermission>-->
<#--                        <@shiro.hasPermission name="teacher:course:view">-->
                        actions.push('<a class="btn btn-success btn-xs " href="#" onclick="selectStu(\'' + row.id + '\')"><i class="fa fa-edit"></i>发布</a>');
<#--                        </@shiro.hasPermission>-->

                        return actions.join('');
                    }
                }]
        };
        $.table.init(options);
        $.common.initBind();
    });
    function AGABuileTest() {
        let id = $.common.isEmpty($.table._option.uniqueId) ? $.table.selectFirstColumns() : $.table.selectColumns($.table._option.uniqueId);
        if (id.length == 0) {
            $.modal.alertWarning("请至少选择一条记录");
            return;
        }
        $.modal.openFull('智能组卷', "/school/onlineExam/course/AGABuileTest?id=" + id+"&&cid="+cid, saveCallback);

    }
    function HumanBuileTest() {
        let id = $.common.isEmpty($.table._option.uniqueId) ? $.table.selectFirstColumns() : $.table.selectColumns($.table._option.uniqueId);
        if (id.length == 0) {
            $.modal.alertWarning("请至少选择一条记录");
            return;
        }
        $.modal.open('手动组卷', "/school/onlineExam/course/buileTest?id=" + id+"&&cid="+cid, saveCallback);
    }
    function saveCallback(context) {
        console.log("context:" + 123);
    }
    function openwindow(id)
    {
        $.modal.open('试卷分析', "/school/onlineExam/course/analyzePaper?id=" + id+"&&cid="+cid, saveCallback);
    }
    function showPaper(id) {
        window.open('/school/onlineExam/course/showPaper?id='+id,'_blank','height=800, width=600');
    }
    function selectStu(id) {
        $.modal.open('试卷发布', "/school/onlineExam/course/show?id=" + id+"&&cid="+cid, saveCallback);
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
