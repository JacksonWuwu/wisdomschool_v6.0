<div class="container-div">
    <div class="btn-group-sm hidden-xs" id="toolbar" role="group">
<#--        <@shiro.hasPermission name="jiaowu:integral:add">-->
            <a class="btn btn-success" onclick="$.operate.add()">
                <i class="fa fa-plus"></i> 新增
            </a>
<#--        </@shiro.hasPermission>-->
<#--        <@shiro.hasPermission name="jiaowu:integral:remove">-->
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
    let prefix = "/jiaowu/integral";

    $(function () {
        let options = {
            url: prefix + "/list",
            createUrl: prefix + "/add",
            updateUrl: prefix + "/edit/{id}",
            removeUrl: prefix + "/remove",
            modalName: "积分规则",
            columns: [{
                checkbox: true
            },
                {
                    field: 'code',
                    title: '编号',
                    formatter: function (value, row, index) {
                        return index + 1;
                    }
                },
                {
                    field: 'name',
                    title: '名称'
                },
                {
                    field: 'rewardType',
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
                    field: 'cycleType',
                    title: '周期'
                },
                {
                    field: 'cycleTime',
                    title: '间隔时间'
                },
                {
                    field: 'rewardNum',
                    title: '最多奖/罚次数'
                },
                {
                    field: 'credit',
                    title: '积分'
                },
                {
                    field: 'orderNum',
                    title: '序号'
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
