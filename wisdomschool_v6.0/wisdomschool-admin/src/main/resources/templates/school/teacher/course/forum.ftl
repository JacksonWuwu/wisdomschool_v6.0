<div class="container-div">
    <div class="btn-group-sm hidden-xs" id="bootstrap-table-forum-toolbar" role="group">
<#--        <@shiro.hasPermission name="teacher:course:view">-->
            <a class="btn btn-success" onclick="$.operate.add()">
                <i class="fa fa-plus"></i> 新增
            </a>
            <a class="btn btn-success" href="/user/courseTopicList/${tcid}">
                <i class="fa fa-archive"></i> 进入讨论
            </a>
<#--        </@shiro.hasPermission>-->
    </div>

    <div class="col-sm-12 select-table table-striped">
        <table id="bootstrap-table-forum-list" data-mobile-responsive="true"></table>
    </div>
</div>
<script type="text/javascript">
    let prefix = "/teacher/course/forum";

    $(function () {
        let options = {
            url: prefix + "/list?cid=${id}",
            createUrl: prefix + "/add?cid=${id}",
            updateUrl: prefix + "/edit/{id}",
            removeUrl: prefix + "/remove",
            content: "bootstrap-table-forum-list",
            toolbar: "bootstrap-table-forum-toolbar",
            modalName: "讨论板块",
            columns: [{
                checkbox: true
            },
                {
                    field: 'id',
                    title: '编号'
                },

                {
                    field: 'name',
                    title: '板块名称',
                },
                {
                    field: 'description',
                    title: '描述',
                    width: 400
                },
                {
                    field: 'createTime',
                    title: '创建时间',
                    visible: false
                },
                {
                    field: 'remark',
                    title: '备注'
                },
                {
                    title: '操作',
                    align: 'center',
                    formatter: function (value, row, index) {
                        let actions = [];
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
