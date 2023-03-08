<#include "/common/components/select.ftl"/>
<div class="container-div layout-container-div clearfix" >
    <div class="row">
        <div class="col-sm-12 search-collapse">
            <form id="role-form">
                <div class="select-list">
                    <ul>
                        <li>
                            题目：<input type="text" name="title"/>
                        </li>

                        <li>
                            <a class="btn btn-primary btn-rounded btn-sm" onclick="$.table.search()"><i
                                        class="fa fa-search"></i>&nbsp;搜索</a>
                            <a class="btn btn-warning btn-rounded btn-sm" onclick="$.form.reset()"><i
                                        class="fa fa-refresh"></i> &nbsp;重置</a>
                        </li>
                    </ul>
                </div>
            </form>
        </div>
        <div class="btn-group-sm hidden-xs" id="toolbar" role="group">
            <@shiro.hasPermission name="teacher:course:view">
                <a class="btn btn-success" onclick="$.operate.addFull()">
                    <i class="fa fa-plus"></i> 新增
                </a>
            </@shiro.hasPermission>
            <a class="btn btn-success" onclick="suiji(1)">
                 随机提问
            </a>
            <@shiro.hasPermission name="teacher:course:view">
                <a class="btn btn-danger btn-del disabled" onclick="$.operate.removeAll()">
                    <i class="fa fa-remove"></i> 删除
                </a>
            </@shiro.hasPermission>


        </div>
        <div class="col-sm-12 select-table table-striped">
            <table id="bootstrap-table" class="bootstrap-table" data-mobile-responsive="true"></table>
        </div>
    </div>
</div>
<script type="text/javascript">
    let prefix = "/teacher/vote";
    $(function () {
        let tid1 = ${tid};
        let cid1= ${id};
        let options = {
            url: prefix + "/list/${id}",
            parentCode: "parentId",
            createUrl: prefix + "/add/${id}",
            updateUrl: prefix + "/edit/{id}",
            removeUrl: prefix + "/remove",
            exportUrl: prefix + "/export",
            importUrl: prefix + '/importData?cid=' + cid1+"&tid="+tid1,
            importTemplateUrl: prefix + "/importTemplate",
            sortName: "createTime",
            sortOrder: "desc",
            modalName: "我的题目",
            search: false,
            showExport: true,
            columns: [
                {
                    checkbox: true
                },
                {
                    field: 'id',
                    title: '编号',
                    sortable: true,
                    formatter(value, row, index) {
                        return index+1;
                    }
                },
                {
                    field: 'title',
                    title: '试题题目',
                    sortable: true
                },
                {
                    field: 'createTime',
                    title: '创建时间',
                    sortable: true
                }, {
                    title: '操作',
                    align: 'center',
                    formatter: function (value, row, index) {
                        let actions = [];
<#--                        <@shiro.hasPermission name="teacher:course:view">-->
                        actions.push('<a class="btn btn-success btn-xs " href="#" onclick="$.operate.editFull(\'' + row.id + '\')"><i class="fa fa-edit"></i>编辑</a> ');
                        actions.push('<a class="btn btn-danger btn-xs " href="#" onclick="$.operate.remove(' + row.id + ', $.operate.ajaxSuccess)"><i class="fa fa-remove"></i>删除</a>');
<#--                        </@shiro.hasPermission>-->
                        if (row.ststatus!=1){actions.push('<a class="btn btn-warning btn-xs " href="#" onclick="fabub(\'' + row.id + '\')"><i class="fa fa-edit"></i>发布</a> ');

                        }else {
                            actions.push('<a class="btn btn-warning btn-xs " href="#" onclick="detail(\'' + row.id + '\')"><i class="fa fa-search"></i>详情</a> ');
                        }
                        // actions.push('<a class="btn btn-success btn-xs " href="#" onclick="suiji(\'' + row.id + '\')"><i class="fa fa-edit"></i>随机抽点</a> ');
                        return actions.join('');
                    }
                }
            ]
        };
        $.table.init(options);
        $.common.initBind();

    });

</script>
<script type="text/javascript">
    /*调度任务-启用*/
    function fabub(id) {
        // var id = $.common.isEmpty($.table._option.uniqueId) ? $.table.selectFirstColumns() : $.table.selectColumns($.table._option.uniqueId);
        $.modal.open('发布', "/teacher/vote/fabu/${tcid}?id=" + id, saveCallback);
    }

    function detail(id) {
        var adjid = "${id}";
        // var id = $.common.isEmpty($.table._option.uniqueId) ? $.table.selectFirstColumns() : $.table.selectColumns($.table._option.uniqueId);
        $.modal.open('详情', "/teacher/vote/detail/${tcid}?id=" + id, saveCallback);
    }

    function suiji(id) {
        var adjid = "${id}";
        // var id = $.common.isEmpty($.table._option.uniqueId) ? $.table.selectFirstColumns() : $.table.selectColumns($.table._option.uniqueId);
        $.modal.open('详情', "/teacher/vote/luck/${tcid}?id=" + id, saveCallback);
    }
</script>
