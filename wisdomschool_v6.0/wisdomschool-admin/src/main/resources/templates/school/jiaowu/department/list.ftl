<#include "/common/components/select.ftl"/>
<div class="container-div layout-container-div clearfix">
    <div class="row">
        <div class="btn-group-sm hidden-xs" id="toolbar" role="group">
<#--            <@shiro.hasPermission name="jiaowu:department:add">-->
                <a class="btn btn-success" onclick="$.operate.add()">
                    <i class="fa fa-plus"></i> 新增
                </a>
<#--            </@shiro.hasPermission>-->
<#--            <@shiro.hasPermission name="jiaowu:department:edit">-->
                <a class="btn btn-primary btn-edit disabled" onclick="$.operate.edit()">
                    <i class="fa fa-edit"></i> 修改
                </a>
<#--            </@shiro.hasPermission>-->
<#--            <@shiro.hasPermission name="jiaowu:department:remove">-->
                <a class="btn btn-danger btn-del disabled" onclick="$.operate.removeAll()">
                    <i class="fa fa-remove"></i> 删除
                </a>
<#--            </@shiro.hasPermission>-->
<#--            <@shiro.hasPermission name="jiaowu:department:export">-->
                <a class="btn btn-warning" onclick="$.table.exportExcel()">
                    <i class="fa fa-download"></i> 导出
                </a>
<#--            </@shiro.hasPermission>-->
        </div>

        <div class="col-sm-12 select-table table-striped">
            <table id="bootstrap-table" class="bootstrap-table" data-mobile-responsive="true"></table>
        </div>
    </div>
</div>
<script type="text/javascript">
    let prefix = "/jiaowu/department";

    $(function () {
        let options = {
            url: prefix + "/list",
            createUrl: prefix + "/add",
            updateUrl: prefix + "/edit/{id}",
            removeUrl: prefix + "/remove",
            exportUrl: prefix + "/export",
            modalName: "系部",
            search: false,
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
                    title: '系部名称'
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
<#--                        <@shiro.hasPermission name="jiaowu:department:edit">-->
                        actions.push('<a class="btn btn-success btn-xs " href="#" onclick="$.operate.edit(' + row.id + ')"><i class="fa fa-edit"></i>编辑</a> ');
<#--                        </@shiro.hasPermission>-->
<#--                        <@shiro.hasPermission name="jiaowu:department:remove">-->
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
