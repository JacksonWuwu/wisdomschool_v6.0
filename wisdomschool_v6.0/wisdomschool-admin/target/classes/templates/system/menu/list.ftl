<div class="container-div clearfix">
    <div class="col-sm-12 search-collapse">
        <form id="menu-form">
            <div class="select-list">
                <ul>
                    <li>
                        菜单名称：<input type="text" name="menuName"/>
                    </li>
                    <li>
                        <div class="r">
                            <div class="col-md-4"><span>菜单状态：</span></div>
                            <div class="col-md-8">
                                <select name="visible" class="form-control m-b">
                                    <option value="">所有</option>
<#--                                    <@dictionary dictType="sys_show_hide">-->
                                        <#list dictData as row>
                                            <option value="${row.dictValue}">${row.dictLabel}</option>
                                        </#list>
<#--                                    </@dictionary>-->
                                </select>
                            </div>
                        </div>
                    </li>
                    <li>
                        <a class="btn btn-primary btn-rounded btn-sm" onclick="$.treeTable.search()"><i class="fa fa-search"></i>&nbsp;搜索</a>
                        <a class="btn btn-warning btn-rounded btn-sm" onclick="$.form.reset()"><i class="fa fa-refresh"></i>&nbsp;重置</a>
                    </li>
                </ul>
            </div>
        </form>
    </div>

    <div class="btn-group-sm hidden-xs" id="toolbar" role="group">
<#--        <@shiro.hasPermission name="system:menu:add">-->
            <a class="btn btn-success" onclick="$.operate.add(0)">
                <i class="fa fa-plus"></i> 新增
            </a>
<#--        </@shiro.hasPermission>-->
<#--        <@shiro.hasPermission name="system:menu:edit">-->
            <a class="btn btn-primary" onclick="$.operate.editTree()">
                <i class="fa fa-edit"></i> 修改
            </a>
<#--        </@shiro.hasPermission>-->
        <a class="btn btn-info" onclick="$.treeTable.expandAllFlag()">
            <i class="fa fa-exchange"></i> 展开/折叠
        </a>
    </div>
    <div class="col-sm-12 select-table table-striped">
        <table id="bootstrap-table" class="bootstrap-table" data-mobile-responsive="true"></table>
    </div>
</div><!--/.container-div-->
<script type="text/javascript">

    let prefix = "${ctx}/system/menu";
    let dictData = [];
<#--    <@dictionary dictType="sys_show_hide">-->
        <#list dictData as row>
            dictData.push({"dictValue" : "${row.dictValue}", "listClass" : "${row.listClass}", "dictLabel" : "${row.dictLabel}"});
        </#list>
<#--    </@dictionary>-->


    $(function() {
        let options = {
            code: "id",
            parentCode: "parentId",
            uniqueId: "id",
            expandAll: false,
            expandFirst: true,
            url: prefix + "/list",
            createUrl: prefix + "/add/{id}",
            updateUrl: prefix + "/edit/{id}",
            removeUrl: prefix + "/remove/{id}",
            modalName: "菜单",
            columns: [{
                field: 'selectItem',
                radio: true
            },
                {
                    title: '菜单名称',
                    field: 'menuName',
                    width: '20%',
                    formatter: function(value, row, index) {
                        if ($.common.isEmpty(row.icon)) {
                            return row.menuName;
                        } else {
                            return '<i class="' + row.icon + '"></i><span class="nav-label">' + row.menuName + '</span>';
                        }
                    }
                },
                {
                    field: 'orderNum',
                    title: '排序',
                    width: '10%',
                    align: "left"
                },
                {
                    field: 'url',
                    title: '请求地址',
                    width: '15%',
                    align: "left"
                },
                {
                    title: '类型',
                    field: 'menuType',
                    width: '10%',
                    align: "left",
                    formatter: function(value, item, index) {
                        if (item.menuType == 'M') {
                            return '<span class="label label-success">目录</span>';
                        }
                        else if (item.menuType == 'C') {
                            return '<span class="label label-primary">菜单</span>';
                        }
                        else if (item.menuType == 'F') {
                            return '<span class="label label-warning">按钮</span>';
                        }
                    }
                },
                {
                    field: 'visible',
                    title: '可见',
                    width: '10%',
                    align: "left",
                    formatter: function(value, row, index) {
                        return $.table.selectDictLabel(dictData, row.visible);
                    }
                },
                {
                    field: 'perms',
                    title: '权限标识',
                    width: '15%',
                    align: "left",
                },
                {
                    title: '操作',
                    width: '20%',
                    align: "left",
                    formatter: function(value, row, index) {
                        let actions = [];
<#--                        <@shiro.hasPermission name="system:menu:edit">-->
                            actions.push('<a class="btn btn-success btn-xs" href="#" onclick="$.operate.edit(\'' + row.id + '\')"><i class="fa fa-edit"></i>编辑</a> ');
<#--                        </@shiro.hasPermission>-->
<#--                        <@shiro.hasPermission name="system:menu:remove">-->
                            actions.push('<a class="btn btn-danger btn-xs" href="#" onclick="$.operate.remove(\'' + row.id + '\', $.treeTable.refresh)"><i class="fa fa-remove"></i>删除</a>');
<#--                        </@shiro.hasPermission>-->
                        return actions.join('');
                    }
                }]
        };
        $.treeTable.init(options);
        $.common.initBind();
    });
</script>
