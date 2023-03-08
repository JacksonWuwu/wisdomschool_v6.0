    <div class="container-div">
		<div class="row">
			<div class="col-sm-12 search-collapse">
				<form id="notice-form">
					<div class="select-list">
						<ul>
							<li>
								公告标题：<input type="text" name="noticeTitle"/>
							</li>
							<li>
								操作人员：<input type="text" name="createBy"/>
							</li>
							<li>
                                <div class="r">
                                    <div class="col-md-4"><span>公告类型：</span></div>
                                    <div class="col-md-8">
                                        <select name="noticeType" class="form-control m-b">
                                            <option value="">所有</option>
<#--                                            <@dictionary dictType="sys_notice_type">-->
                                                <#list dictData as row>
                                                    <option value="${row.dictValue}">${row.dictLabel}</option>
                                                </#list>
<#--                                            </@dictionary>-->
                                        </select>
                                    </div>
                                </div>
							</li>
							<li>
								<a class="btn btn-primary btn-rounded btn-sm" onclick="$.table.search()"><i class="fa fa-search"></i>&nbsp;搜索</a>
								<a class="btn btn-warning btn-rounded btn-sm" onclick="$.form.reset()"><i class="fa fa-refresh"></i>&nbsp;重置</a>
							</li>
						</ul>
					</div>
				</form>
			</div>

	        <div class="btn-group-sm hidden-xs" id="toolbar" role="group">
		        <#--<a class="btn btn-success" onclick="$.operate.addFull()" shiro:hasPermission="system:notice:add">-->
		        <a class="btn btn-success" onclick="$.operate.addFull()" >
		            <i class="fa fa-plus"></i> 新增
		        </a>
		        <#--<a class="btn btn-primary btn-edit disabled" onclick="$.operate.editFull()" shiro:hasPermission="system:notice:edit">-->
		        <a class="btn btn-primary btn-edit disabled" onclick="$.operate.editFull()" >
		            <i class="fa fa-edit"></i> 修改
		        </a>
		        <#--<a class="btn btn-danger btn-del disabled" onclick="$.operate.removeAll()" shiro:hasPermission="system:notice:remove">-->
		        <a class="btn btn-danger btn-del disabled" onclick="$.operate.removeAll()" >
		            <i class="fa fa-remove"></i> 删除
		        </a>
	        </div>

	        <div class="col-sm-12 select-table table-striped">
	            <table id="bootstrap-table" data-mobile-responsive="true"></table>
	        </div>
    	</div>
    </div>
    <script type="text/javascript">
        <#--var editFlag = [[${@permission.hasPermi('system:notice:edit')}]];-->
        <#--var removeFlag = [[${@permission.hasPermi('system:notice:remove')}]];-->
		let datas = [];
<#--		<@dictionary dictType="sys_notice_status">-->
			<#list dictData as row>
				datas.push({"dictValue" : "${row.dictValue}", "listClass" : "${row.listClass}", "dictLabel" : "${row.dictLabel}"});
			</#list>
<#--		</@dictionary>-->
		let types = [];
<#--		<@dictionary dictType="sys_notice_type">-->
			<#list dictData as row>
				types.push({"dictValue" : "${row.dictValue}", "listClass" : "${row.listClass}", "dictLabel" : "${row.dictLabel}"});
			</#list>
<#--		</@dictionary>-->
        var prefix = "${ctx}/system/notice";

        $(function() {
            var options = {
                url: prefix + "/list",
                createUrl: prefix + "/add",
                updateUrl: prefix + "/edit/{id}",
                removeUrl: prefix + "/remove",
                modalName: "公告",
                search: false,
                columns: [{
		            checkbox: true
		        },
				{
					field : 'noticeId',
					title : '序号'
				},
				{
					field : 'noticeTitle',
					title : '公告标题'
				},
				{
		            field: 'noticeType',
		            title: '公告类型',
		            align: 'center',
		            formatter: function(value, row, index) {
		            	return $.table.selectDictLabel(types, value);
		            }
		        },
				{
		            field: 'status',
		            title: '状态',
		            align: 'center',
		            formatter: function(value, row, index) {
		            	return $.table.selectDictLabel(datas, value);
		            }
		        },
				{
					field : 'createBy',
					title : '创建者'
				},
				{
		            field: 'createTime',
		            title: '创建时间',
		            sortable: true
		        },
		        {
		            title: '操作',
		            align: 'center',
		            formatter: function(value, row, index) {
		            	var actions = [];
		            	actions.push('<a class="btn btn-success btn-xs " href="#" onclick="$.operate.editFull(\'' + row.noticeId + '\')"><i class="fa fa-edit"></i>编辑</a> ');
                        actions.push('<a class="btn btn-danger btn-xs " href="#" onclick="$.operate.remove(\'' + row.noticeId + '\', $.operate.ajaxSuccess)"><i class="fa fa-remove"></i>删除</a>');
						return actions.join('');
		            }
		        }]
            };
            $.table.init(options);
            $.common.initBind();
        });
    </script>
