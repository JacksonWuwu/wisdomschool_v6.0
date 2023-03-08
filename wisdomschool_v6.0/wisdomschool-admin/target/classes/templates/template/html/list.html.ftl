<div class="container-div">
    <div class="btn-group-sm hidden-xs" id="toolbar" role="group">
        ${r'<@shiro.hasPermission name="'}${moduleName}:${classname}${r':add">'}
        <a class="btn btn-success" onclick="$.operate.add()">
            <i class="fa fa-plus"></i> 新增
        </a>
        ${r'</@shiro.hasPermission>'}

        ${r'<@shiro.hasPermission name="'}${moduleName}:${classname}${r':edit">'}
        <a class="btn btn-primary btn-edit disabled" onclick="$.operate.edit()">
            <i class="fa fa-edit"></i> 修改
        </a>
        ${r'</@shiro.hasPermission>'}
        ${r'<@shiro.hasPermission name="'}${moduleName}:${classname}${r':remove">'}
        <a class="btn btn-danger btn-del disabled" onclick="$.operate.removeAll()">
            <i class="fa fa-remove"></i> 删除
        </a>
        ${r'</@shiro.hasPermission>'}
        ${r'<@shiro.hasPermission name="'}${moduleName}:${classname}${r':export">'}
        <a class="btn btn-warning" onclick="$.table.exportExcel()">
            <i class="fa fa-download"></i> 导出
        </a>
        ${r'</@shiro.hasPermission>'}


    </div>

    <div class="col-sm-12 select-table table-striped">
        <table id="bootstrap-table" data-mobile-responsive="true"></table>
    </div>
</div>
<script type="text/javascript">
    let prefix = "${r'${ctx}'}/${moduleName}/${classname}";

    $(function () {
        let options = {
            url: prefix + "/list",
            createUrl: prefix + "/add",
            updateUrl: prefix + "/edit/{id}",
            removeUrl: prefix + "/remove",
            modalName: "${tableComment}",
            columns: [{
                checkbox: true
            },
                <#list columns as column >
                {
                    field: '${column.attrname}',
                    title: '${column.columnComment}'
                },
                </#list>
                {
                    title: '操作',
                    align: 'center',
                    formatter: function (value, row, index) {
                        let actions = [];
                        ${r'<@shiro.hasPermission name="'}${moduleName}:${classname}${r':edit">'}
                        actions.push('<a class="btn btn-success btn-xs " href="#" onclick="$.operate.edit(' + row.${primaryKey.attrname} + ')"><i class="fa fa-edit"></i>编辑</a> ');
                        ${r'</@shiro.hasPermission>'}
                        ${r'<@shiro.hasPermission name="'}${moduleName}:${classname}${r':remove">'}
                        actions.push('<a class="btn btn-danger btn-xs " href="#" onclick="$.operate.remove(' + row.${primaryKey.attrname} + ', $.operate.ajaxSuccess)"><i class="fa fa-remove"></i>删除</a>');
                        ${r'</@shiro.hasPermission>'}
                        return actions.join('');
                    }
                }]
        };
        $.table.init(options);
        $.common.initBind();
    });
</script>