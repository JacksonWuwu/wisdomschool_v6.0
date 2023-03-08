<div class="container-div">
    <div class="btn-group-sm hidden-xs" id="bootstrap-table-type-toolbar" role="group">
<#--        <@shiro.hasPermission name="teacher:course:view">-->
            <a class="btn btn-success" onclick="$.operate.add()">
                <i class="fa fa-plus"></i> 新增
            </a>
<#--        </@shiro.hasPermission>-->
    </div>

    <div class="col-sm-12 select-table table-striped">
        <table id="bootstrap-table-type" data-mobile-responsive="true"></table>
    </div>
</div>
<script type="text/javascript">
    let prefix = "/recourse/type/${id}";

    $(function () {
        let options = {
            url: prefix + "/list",
            createUrl: prefix + "/add",
            updateUrl: prefix + "/edit/{id}",
            removeUrl: prefix + "/remove",
            content: "bootstrap-table-type",
            modalName: "资源类型",
            toolbar: "bootstrap-table-type-toolbar",
            columns: [{
                checkbox: true
            },
                {
                    field: 'id',
                    title: '编号',
                    formatter: function (value, row, index) {
                        return index + 1;
                    }
                },
                {
                    field: 'name',
                    title: '类型名称'
                },
                {
                    field: 'createTime',
                    title: '创建时间'
                },
                {
                    field: 'remark',
                    title: '备注'
                },
                {
                    field: 'order',
                    title: '排序'
                },
                {
                    title: '操作',
                    align: 'center',
                    formatter: function (value, row, index) {
                        let actions = [];
<#--                        <@shiro.hasPermission name="teacher:course:view">-->
                        actions.push('<a class="btn btn-success btn-xs " href="#" onclick="$.operate.edit(' + row.id + ')"><i class="fa fa-edit"></i>编辑</a> ');
<#--                        </@shiro.hasPermission>-->
<#--                        <@shiro.hasPermission name="teacher:course:view">-->
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
