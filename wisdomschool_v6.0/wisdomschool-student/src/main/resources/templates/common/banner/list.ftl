<div class="container-div">
    <div class="btn-group-sm hidden-xs" id="toolbar" role="group">
        <@shiro.hasPermission name="jiaowu:clbum:add">
            <a class="btn btn-success" onclick="$.operate.add()">
                <i class="fa fa-plus"></i> 新增
            </a>
        </@shiro.hasPermission>

        <@shiro.hasPermission name="jiaowu:clbum:edit">
            <a class="btn btn-primary btn-edit disabled" onclick="$.operate.edit()">
                <i class="fa fa-edit"></i> 修改
            </a>
        </@shiro.hasPermission>
        <@shiro.hasPermission name="jiaowu:clbum:remove">
            <a class="btn btn-danger btn-del disabled" onclick="$.operate.removeAll()">
                <i class="fa fa-remove"></i> 删除
            </a>
        </@shiro.hasPermission>
    </div>

    <div class="col-sm-12 select-table table-striped">
        <table id="bootstrap-table" data-mobile-responsive="true"></table>
    </div>
</div>
<script type="text/javascript">
    let prefix = "/jiaowu/banner";

    $(function () {
        let options = {
            url: prefix + "/list",
            createUrl: prefix + "/add",
            updateUrl: prefix + "/edit/{id}",
            removeUrl: prefix + "/remove",
            search: false,
            modalName: "轮播图",
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
                    field: 'title',
                    title: '标题'
                },
                {
                    field: '',
                    title: ''
                },
                {
                    field: '',
                    title: ''
                },
                {
                    field: 'remark',
                    title: '备注'
                },
                {
                    field: 'createTime',
                    title: '创建时间'
                },
                {
                    title: '操作',
                    align: 'center',
                    formatter: function (value, row, index) {
                        let actions = [];
                        <@shiro.hasPermission name="module:clbum:edit">
                        actions.push('<a class="btn btn-success btn-xs " href="#" onclick="$.operate.edit(' + row.id + ')"><i class="fa fa-edit"></i>编辑</a> ');
                        </@shiro.hasPermission>
                        <@shiro.hasPermission name="module:clbum:remove">
                        actions.push('<a class="btn btn-danger btn-xs " href="#" onclick="$.operate.remove(' + row.id + ', $.operate.ajaxSuccess)"><i class="fa fa-remove"></i>删除</a>');
                        </@shiro.hasPermission>
                        return actions.join('');
                    }
                }]
        };
        $.table.init(options);
        $.common.initBind();
    });
</script>
