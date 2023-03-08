<div class="container-div">
    <div class="btn-group-sm hidden-xs" id="toolbar" role="group">
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
    let prefix = "/school/onlineExam/exam";

    $(function () {
        let options = {
            url: prefix + "/list/${cid}",
            createUrl: prefix + "/add/${cid}",
            updateUrl: prefix + "/edit/{id}",
            removeUrl: prefix + "/remove",
            modalName: "测试关联",
            columns: [{
                checkbox: true
            },
                {
                    field: 'id',
                    title: '测试编号',
                    formatter(value, row, index) {
                        return index+1;
                    }
                },
                {
                    field: 'testName',
                    title: '试卷名称'
                },

                {
                    field: 'sysName',
                    title: '学生名称'
                },
                {
                    field: 'stuStartTime',
                    title: '开始时间'
                },
                {
                    field: 'stuEndTime',
                    title: '结束时间'
                },
                {
                    field: 'sumbitState',
                    title: '提交状态'
                },

                {
                    title: '操作',
                    align: 'center',
                    formatter: function (value, row, index) {
                        let actions = [];
<#--                        <@shiro.hasPermission name="school:onlineExam:course:view">-->
                        actions.push('<a class="btn btn-success btn-xs " href="#" onclick="$.operate.edit(' + row.id + ')"><i class="fa fa-edit"></i>编辑</a> ');
<#--                        </@shiro.hasPermission>-->
<#--                        <@shiro.hasPermission name="school:onlineExam:course:view">-->
                        actions.push('<a class="btn btn-danger btn-xs " href="#" onclick="$.operate.remove(' + row.id + ', $.operate.ajaxSuccess)"><i class="fa fa-remove"></i>删除</a>');
<#--                        </@shiro.hasPermission>-->
                        return actions.join('');
                    }
                }]
        };
        $.table.init(options);
        $.common.initBind();
    });
</script>
