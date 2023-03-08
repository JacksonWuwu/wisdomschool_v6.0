<div class="container-div clearfix">
    <div class="row">
        <div class="col-sm-12 search-collapse">
            <form id="gen-form">
                <div class="select-list">
                    <ul>
                        <li>
                            表名称：<input type="text" name="tableName"/>
                        </li>
                        <li>
                            表描述：<input type="text" name="tableComment"/>
                        </li>
                        <li class="select-time">
                            <label>表时间： </label>
                            <input type="text" class="time-input" id="startTime" placeholder="开始时间"
                                   name="params[beginTime]"/>
                            <span>-</span>
                            <input type="text" class="time-input" id="endTime" placeholder="结束时间"
                                   name="params[endTime]"/>
                        </li>
                        <li>
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
<#--            <@shiro.hasPermission name="tool:gen:code">-->
                <a class="btn btn-success" onclick="javascript:batchGenCode()">
                    <i class="fa fa-download"></i> 批量生成
                </a>
<#--            </@shiro.hasPermission>-->
        </div>

        <div class="col-sm-12 select-table table-striped">
            <table id="bootstrap-table" data-mobile-responsive="true"></table>
        </div>
    </div>
</div>
<script type="text/javascript">
    let prefix = "${ctx}/tool/gen";

    $(function () {
        let options = {
            url: prefix + "/list",
            sortName: "createTime",
            sortOrder: "desc",
            search: false,
            columns: [{
                checkbox: true
            },
                {
                    field: 'tableName',
                    title: '表名称',
                    width: '20%',
                    sortable: true
                },
                {
                    field: 'tableComment',
                    title: '表描述',
                    width: '20%',
                    sortable: true
                },
                {
                    field: 'createTime',
                    title: '创建时间',
                    width: '20%',
                    sortable: true
                },
                {
                    field: 'updateTime',
                    title: '更新时间',
                    width: '20%',
                    sortable: true
                },
                {
                    title: '操作',
                    width: '20%',
                    align: 'center',
                    formatter: function (value, row, index) {
                        let msg = '<a class="btn btn-primary btn-xs" href="#" onclick="genCode(\'' + row.tableName + '\')"><i class="fa fa-bug"></i>生成代码</a> ';
                        return msg;
                    }
                }]
        };
        $.table.init(options);
    });

    // 生成代码
    function genCode(tableName) {
        $.modal.confirm("确定要生成" + tableName + "表代码吗？", function () {
            location.href = prefix + "/genCode/" + tableName;
            layer.msg('执行成功,正在生成代码请稍后…', {icon: 1});
        })
    }

    //批量生成代码
    function batchGenCode() {
        let rows = $.table.selectColumns("tableName");
        if (rows.length == 0) {
            $.modal.alertWarning("请选择要生成的数据");
            return;
        }
        $.modal.confirm("确认要生成选中的" + rows.length + "条数据吗?", function () {
            location.href = prefix + "/batchGenCode?tables=" + rows;
            layer.msg('执行成功,正在生成代码请稍后…', {icon: 1});
        });
    }
</script>
