<div class="container-div">
    <div class="col-sm-12 search-collapse">
        <form id="grades-form">
            <div class="select-list">
                <ul>
                    <li>

                    </li>
                    <li>
                        评论者：<input type="text" name="createName">
                    </li>
                    <li>
                        评论内容：<input type="text" name="content"/>
                    </li>
                    <li>
                        <input type="hidden" name="topicIds" value="">
                        <a class="btn btn-primary btn-rounded btn-sm" onclick="$.table.search()"><i
                                class="fa fa-search"></i>&nbsp;搜索</a>
                        <a class="btn btn-warning btn-rounded btn-sm" onclick="$.form.reset()"><i
                                class="fa fa-refresh"></i>&nbsp;重置</a>
                    </li>
                </ul>
            </div>
        </form>
    </div>
    <div class="btn-group-sm hidden-xs" id="toolbar" role="group">
<#--        <@shiro.hasPermission name="teacher:course:view">-->
            <a class="btn btn-danger btn-del disabled" onclick="$.operate.removeAll()">
                <i class="fa fa-remove"></i> 删除
            </a>
<#--        </@shiro.hasPermission>-->
    <#--<@shiro.hasPermission name="jiaowu:grades:export">
        <a class="btn btn-warning" onclick="$.table.exportExcel()">
            <i class="fa fa-download"></i> 导出
        </a>
    </@shiro.hasPermission>-->
    </div>

    <div class="col-sm-12 select-table table-striped">
        <table id="bootstrap-table" data-mobile-responsive="true"></table>
    </div>
</div>
<script type="text/javascript">
    let prefix = "/teacher/course/comment";

    $(function () {
        let options = {
            url: prefix + "/list",
            removeUrl: prefix + "/remove",
            exportUrl: prefix + "/export",
            modalName: "评论",
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


    function remove (id, type, callback) {
        $.modal.confirm("确定删除该条" + $.table._option.modalName + "数据吗？", function () {
            let url = $.common.isEmpty(id) ? $.table._option.removeUrl : $.table._option.removeUrl.replace("{id}", id);
            let data = {
                "ids": id,
                "type": type
            };
            $.operate.submit(url, "post", "json", data, true, callback);
        });
    }
</script>
