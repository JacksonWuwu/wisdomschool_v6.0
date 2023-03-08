<div class="container-div">
    <div class="btn-group-sm hidden-xs" id="toolbar" role="group">
<#--        <@shiro.hasPermission name="teacher:course:view">-->
        <a class="btn btn-success" onclick="$.operate.add(${cid})">
            <i class="fa fa-plus"></i> 新增
        </a>
<#--        </@shiro.hasPermission>-->

<#--        <@shiro.hasPermission name="teacher:course:view">-->
<#--&lt;#&ndash;        <a class="btn btn-primary btn-edit disabled" onclick="$.operate.edit()">&ndash;&gt;-->
<#--&lt;#&ndash;            <i class="fa fa-edit"></i> 修改&ndash;&gt;-->
<#--&lt;#&ndash;        </a>&ndash;&gt;-->
<#--        </@shiro.hasPermission>-->
<#--        <@shiro.hasPermission name="teacher:course:view">-->
<#--&lt;#&ndash;        <a class="btn btn-danger btn-del disabled" onclick="$.operate.removeAll()">&ndash;&gt;-->
<#--&lt;#&ndash;            <i class="fa fa-remove"></i> 删除&ndash;&gt;-->
<#--&lt;#&ndash;        </a>&ndash;&gt;-->
<#--        </@shiro.hasPermission>-->
<#--        <@shiro.hasPermission name="teacher:course:view">-->
<#--&lt;#&ndash;        <a class="btn btn-warning" onclick="$.table.exportExcel()">&ndash;&gt;-->
<#--&lt;#&ndash;            <i class="fa fa-download"></i> 导出&ndash;&gt;-->
<#--&lt;#&ndash;        </a>&ndash;&gt;-->
<#--        </@shiro.hasPermission>-->

    </div>

    <div class="col-sm-12 select-table table-striped">
        <table id="bootstrap-table" data-mobile-responsive="true"></table>
    </div>
</div>
<script type="text/javascript">
    let prefix = "/school/onlineExam/myTitleType";

    $(function () {
        let options = {
            url: prefix + "/list?cid=${cid}",
            createUrl: prefix + "/add/{id}",
            updateUrl: prefix + "/edit/{id}",
            removeUrl: prefix + "/remove",
            modalName: "我的题型",
            columns: [{
                checkbox: true
            },
                {
                    field: 'id',
                    title: '编号',
                    formatter(value, row, index) {
                        return index+1;
                    }
                },
                {
                    field: 'titleTypeName',
                    title: '题型名'
                },
                {
                    field: 'publicTitleName',
                    title: '平台题型'
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
