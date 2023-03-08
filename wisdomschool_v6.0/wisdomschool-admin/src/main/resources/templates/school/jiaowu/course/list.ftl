<div class="container-div" >
    <div class="row">
        <div class="col-sm-12 search-collapse">
            <form id="course-form">
                <div class="select-list">
                    <ul>
                        <li>
                            课程名称：<input type="text" name="name"/>
                        </li>
                        <li>
                            学时：<input type="text" name="period"/>
                        </li>
                        <li>
                            学分：<input type="text" name="credit"/>
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
<#--            <@shiro.hasPermission name="jiaowu:course:add">-->
                <a class="btn btn-success" onclick="$.operate.add()">
                    <i class="fa fa-plus"></i> 新增
                </a>
<#--            </@shiro.hasPermission>-->

<#--            <@shiro.hasPermission name="jiaowu:course:edit">-->
                <a class="btn btn-primary btn-edit disabled" onclick="$.operate.edit()">
                    <i class="fa fa-edit"></i> 修改
                </a>
<#--            </@shiro.hasPermission>-->
<#--            <@shiro.hasPermission name="jiaowu:course:remove">-->
                <a class="btn btn-danger btn-del disabled" onclick="$.operate.removeAll()">
                    <i class="fa fa-remove"></i> 删除
                </a>
<#--            </@shiro.hasPermission>-->
<#--            <@shiro.hasPermission name="jiaowu:course:import">-->
                <a class="btn btn-info" onclick="$.table.importExcel()">
                    <i class="fa fa-upload"></i> 导入
                </a>
<#--            </@shiro.hasPermission>-->

        </div>

        <div class="col-sm-12 select-table table-striped">
            <table id="bootstrap-table" data-mobile-responsive="true"></table>
        </div>
    </div>

    <form id="importForm" enctype="multipart/form-data" class="mt-20 mb-10" style="display: none;">
        <div class="col-xs-offset-1">
            <input type="file" id="file" name="file"/>
            <div class="mt-10 pt-5">
                <input type="checkbox" id="updateSupport" name="updateSupport" title="如果登录账户已经存在，更新这条数据。"> 是否更新已经存在的用户数据
                &nbsp; <a onclick="$.table.exportTemplate()" class="btn btn-default btn-xs"><i
                            class="fa fa-file-excel-o"></i> 下载模板</a>
            </div>
            <span class="text-danger pull-left mt-10">
            提示：仅允许导入“xls”或“xlsx”格式文件！
        </span>
        </div>
    </form>
</div>
    <script type="text/javascript">
        let datas = [];
<#--        <@dictionary dictType="sys_normal_disable">-->
        <#list dictData as row>
        datas.push({"dictValue": "${row.dictValue}", "listClass": "${row.listClass}", "dictLabel": "${row.dictLabel}"});
        </#list>
<#--        </@dictionary>-->
        let prefix = "/jiaowu/course";
        $(function () {
            let options = {
                url: prefix + "/list",
                createUrl: prefix + "/add",
                updateUrl: prefix + "/edit/{id}",
                removeUrl: prefix + "/remove",
                importUrl: prefix + "/importData",
                importTemplateUrl: prefix + "/importTemplate",
                search: false,
                modalName: "课程",
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
                        title: '课程名称'
                    },
                    {
                        field: 'period',
                        title: '学时'
                    },
                    {
                        field: 'credit',
                        title: '学分'
                    },
                    {
                        field: 'createTime',
                        title: '创建时间',
                        sortable: true
                    },
                    {
                        field: 'department.name',
                        title: '所属系部'
                    },
                    {
                        title: '操作',
                        align: 'center',
                        formatter: function (value, row, index) {
                            let actions = [];
<#--                            <@shiro.hasPermission name="jiaowu:course:edit">-->
                            actions.push('<a class="btn btn-success btn-xs " href="#" onclick="$.operate.edit(' + row.id + ')"><i class="fa fa-edit"></i>编辑</a> ');
<#--                            </@shiro.hasPermission>-->
<#--                            <@shiro.hasPermission name="jiaowu:course:remove">-->
                            actions.push('<a class="btn btn-danger btn-xs " href="#" onclick="$.operate.remove(' + row.id + ', $.operate.ajaxSuccess)"><i class="fa fa-remove"></i>删除</a>');
<#--                            </@shiro.hasPermission>-->
                            <#--<@shiro.hasPermission name="jiaowu:chapter:list">
                            actions.push('<a class="btn btn-success btn-xs " href="#" onclick="chapterlist(' + row.id + ')"><i class="fa fa-external-link"></i>查看章节</a> ');
                            </@shiro.hasPermission>-->
                            return actions.join('');

                        }
                    }]
            };
            $.table.init(options);
            $.common.initBind();
        });

    </script>
