<div class="container-div">
	<div class="row">
		<div class="col-sm-12 search-collapse">
			<form id="type-form">
				<div class="select-list">
					<ul>
						<li>
							字典名称：<input type="text" name="dictName"/>
						</li>
						<li>
							字典类型：<input type="text" name="dictType"/>
						</li>
						<li>
                            <div class="r">
                                <div class="col-md-4"><span>字典状态：</span></div>
                                <div class="col-md-8">
                                    <select name="status" class="form-control m-b">
                                        <option value="">所有</option>
<#--                                        <@dictionary dictType="sys_normal_disable">-->
                                            <#list dictData as row>
                                                <option value="${row.dictValue}">${row.dictLabel}</option>
                                            </#list>
<#--                                        </@dictionary>-->
                                    </select>
                                </div>
                            </div>
						</li>
						<li class="select-time">
							<label>创建时间： </label>
							<input type="text" class="time-input" id="startTime" placeholder="开始时间" name="params[beginTime]"/>
							<span>-</span>
							<input type="text" class="time-input" id="endTime" placeholder="结束时间" name="params[endTime]"/>
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
<#--		   <@shiro.hasPermission name="system:dict:add">-->
			   <a class="btn btn-success" onclick="$.operate.add()">
				   <i class="fa fa-plus"></i> 新增
			   </a>
<#--		   </@shiro.hasPermission>-->
<#--		   <@shiro.hasPermission name="system:dict:edit">-->
			   <a class="btn btn-primary btn-edit disabled" onclick="$.operate.edit()">
				   <i class="fa fa-edit"></i> 修改
			   </a>
<#--		   </@shiro.hasPermission>-->
<#--		   <@shiro.hasPermission name="system:dict:remove">-->
			   <a class="btn btn-danger btn-del disabled" onclick="$.operate.removeAll()">
				   <i class="fa fa-remove"></i> 删除
			   </a>
<#--		   </@shiro.hasPermission>-->
<#--		   <@shiro.hasPermission name="system:dict:export">-->
			   <a class="btn btn-warning" onclick="$.table.exportExcel()">
				   <i class="fa fa-download"></i> 导出
			   </a>
<#--		   </@shiro.hasPermission>-->
		</div>

		<div class="col-sm-12 select-table table-striped">
			<table id="bootstrap-table" data-mobile-responsive="true"></table>
		</div>
	</div>
</div>
<script type="text/javascript">
	let datas = [];
<#--	<@dictionary dictType="sys_normal_disable">-->
		<#list dictData as row>
			datas.push({"dictValue" : "${row.dictValue}", "listClass" : "${row.listClass}", "dictLabel" : "${row.dictLabel}"});
		</#list>
<#--	</@dictionary>-->
	let prefix = "${ctx}/system/dict";

	$(function() {
		var options = {
			url: prefix + "/list",
			createUrl: prefix + "/add",
			updateUrl: prefix + "/edit/{id}",
			removeUrl: prefix + "/remove",
			exportUrl: prefix + "/export",
			sortName: "createTime",
			sortOrder: "desc",
			modalName: "类型",
			search: false,
			showExport: false,
			columns: [{
				checkbox: true
			},
			{
				field: 'id',
                title: '编号'
			},
			{
				field: 'dictName',
				title: '字典名称',
				sortable: true
			},
			{
				field: 'dictType',
				title: '字典类型',
				sortable: true,
				formatter: function(value, row, index) {
					return '<a href="#" onclick="detail(\'' + row.dictId + '\')">' + value + '</a>';
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
				formatter: function(value, row, index) {
					var actions = [];
<#--					<@shiro.hasPermission name="system:dict:edit">-->
						actions.push('<a class="btn btn-success btn-xs " href="#" onclick="$.operate.edit(\'' + row.id + '\')"><i class="fa fa-edit"></i>编辑</a> ');
<#--					</@shiro.hasPermission>-->
<#--					<@shiro.hasPermission name="system:dict:list">-->
						actions.push('<a class="btn btn-info btn-xs " href="#" onclick="detail(\'' + row.id + '\')"><i class="fa fa-list-ul"></i>列表</a> ');
<#--					</@shiro.hasPermission>-->
<#--					<@shiro.hasPermission name="system:dict:remove">-->
                    actions.push('<a class="btn btn-danger btn-xs " href="#" onclick="$.operate.remove(\'' + row.id + '\', $.operate.ajaxSuccess)"><i class="fa fa-remove"></i>删除</a>');
<#--					</@shiro.hasPermission>-->
					return actions.join('');
				}
			}]
		};
		$.table.init(options);
        $.common.initBind();
	});

	/*字典列表-详细*/
	function detail(dictId) {
		var url = prefix + '/detail/' + dictId;
		createMenuItem(url, "字典数据");
	}
</script>
