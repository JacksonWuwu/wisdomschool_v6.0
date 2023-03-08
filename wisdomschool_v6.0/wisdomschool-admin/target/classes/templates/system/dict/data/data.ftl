<div class="container-div">
    <div class="row">
        <div class="col-sm-12 search-collapse">
            <form id="data-form">
                <div class="select-list">
                    <ul>
                        <li>
                            字典名称：<select id="dictType" name="dictType">
                                <option th:each="dict : ${dictList}" th:text="${dict['dictName']}"
                                        th:value="${dict['dictType']}" th:field="*{dict.dictType}"></option>
                            </select>
                        </li>
                        <li>
                            字典标签：<input type="text" name="dictLabel"/>
                        </li>
                        <li>
                            数据状态：<select name="status" th:with="type=${@dict.getType('sys_normal_disable')}">
                                <option value="">所有</option>
                                <option th:each="dict : ${type}" th:text="${dict.dictLabel}"
                                        th:value="${dict.dictValue}"></option>
                            </select>
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
            <a class="btn btn-success" th:onclick="'javascript:$.operate.add(\''+${dict.dictType}+'\')'"
               <#--shiro:hasPermission="system:dict:add">-->
                <i class="fa fa-plus"></i> 新增
            </a>
            <a class="btn btn-primary btn-edit disabled" onclick="$.operate.edit()"
               <#--shiro:hasPermission="system:dict:edit">-->
                <i class="fa fa-edit"></i> 修改
            </a>
            <a class="btn btn-danger btn-del disabled" onclick="$.operate.removeAll()"
               <#--shiro:hasPermission="system:dict:remove">-->
                <i class="fa fa-remove"></i> 删除
            </a>
            <#--<a class="btn btn-warning" onclick="$.table.exportExcel()" shiro:hasPermission="system:dict:export">-->
            <a class="btn btn-warning" onclick="$.table.exportExcel()" >
                <i class="fa fa-download"></i> 导出
            </a>
        </div>

        <div class="col-sm-12 select-table table-striped">
            <table id="bootstrap-table" data-mobile-responsive="true"></table>
        </div>
    </div>
</div>
    <script type="text/javascript">
        let prefix = ctx + "system/dict/data";

        $(function () {
            let options = {
                url: prefix + "/list",
                createUrl: prefix + "/add/{id}",
                updateUrl: prefix + "/edit/{id}",
                removeUrl: prefix + "/remove",
                exportUrl: prefix + "/export",
                queryParams: queryParams,
                sortName: "createTime",
                sortOrder: "desc",
                modalName: "数据",
                search: false,
                showExport: false,
                columns: [{
                    checkbox: true
                },
                    {
                        field: 'dictCode',
                        title: '字典编码'
                    },
                    {
                        field: 'dictLabel',
                        title: '字典标签'
                    },
                    {
                        field: 'dictValue',
                        title: '字典键值'
                    },
                    {
                        field: 'dictSort',
                        title: '字典排序'
                    },
                    {
                        field: 'status',
                        title: '状态',
                        align: 'center',
                        formatter: function (value, row, index) {
                            return $.table.selectDictLabel(datas, value);
                        }
                    },
                    {
                        field: 'remark',
                        title: '备注'
                    },
                    {
                        field: 'createTime',
                        title: '创建时间',
                        sortable: true
                    },
                    {
                        title: '操作',
                        align: 'center',
                        formatter: function (value, row, index) {
                            let actions = [];
<#--                            <@shiro.hasPermission name="system:dict:edit">-->
                            actions.push('<a class="btn btn-success btn-xs" onclick="$.operate.edit(\'' + row.dictCode + '\')"> <i class="fa fa-edit"></i> 编辑 </a>');
<#--                            </@shiro.hasPermission>-->
<#--                            <@shiro.hasPermission name="system:dict:remove">-->
                            actions.push('<a class="btn btn-danger btn-xs" onclick="$.operate.remove(\'' + row.dictCode + '\', $.operate.ajaxSuccess)"><i class="fa fa-remove"></i>删除</a>');
<#--                            </@shiro.hasPermission>-->
                            return actions.join('');
                        }
                    }]
            };
            $.table.init(options);
        });

        function queryParams(params) {
            return {
                dictType: $("#dictType").val(),
                pageSize: params.limit,
                pageNum: params.offset / params.limit + 1,
                searchValue: params.search,
                orderByColumn: params.sort,
                isAsc: params.order
            };
        }
    </script>
