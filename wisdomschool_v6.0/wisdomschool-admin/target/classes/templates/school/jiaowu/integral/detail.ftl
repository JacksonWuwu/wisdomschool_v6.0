<div class="container-div">
    <div class="btn-group-sm hidden-xs" id="toolbar" role="group">
<#--        <@shiro.hasPermission name="jiaowu:integral:remove">-->
            <a class="btn btn-danger btn-del disabled" onclick="$.operate.removeAll()">
                <i class="fa fa-remove"></i> 删除
            </a>
<#--        </@shiro.hasPermission>-->
<#--        <@shiro.hasPermission name="jiaowu:integral:export">-->
            <a class="btn btn-warning" onclick="$.table.exportExcel()">
                <i class="fa fa-download"></i> 导出
            </a>
<#--        </@shiro.hasPermission>-->
    </div>

    <div class="col-sm-12 select-table table-striped">
        <table id="bootstrap-table" data-mobile-responsive="true"></table>
    </div>
</div>
<script type="text/javascript">
    let prefix = "/jiaowu/integral";

    $(function () {
        let options = {
            url: prefix + "/detail/list",
            removeUrl: prefix + "/detail/remove",
            exportUrl: prefix + "/export",
            modalName: "积分规则",
            columns: [{
                checkbox: true
            },
                {
                    field: 'id',
                    title: '编号'
                },
                {
                    field: 'userName',
                    title: '用户名'
                },
                {
                    field: 'type',
                    title: '类型',
                    formatter: function (value, row, index) {
                        if (value === 0) {
                            return "<span class='badge badge-primary'>奖励</span>";
                        } else {
                            return "<span class='badge badge-danger'>惩罚</span>";
                        }
                    }
                },
                {
                    field: 'credit',
                    title: '积分'
                },
                {
                    field: 'content',
                    title: '内容'
                },
                {
                    field: 'createTime',
                    title: '时间'
                },
                {
                    title: '操作',
                    align: 'center',
                    formatter: function (value, row, index) {
                        let actions = [];
<#--                        <@shiro.hasPermission name="jiaowu:grades:remove">-->
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
