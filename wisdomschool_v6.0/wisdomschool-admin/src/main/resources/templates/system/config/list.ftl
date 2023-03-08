 <div class="container-div">
	<div class="row">
		<div class="col-sm-12 search-collapse">
			<form id="config-form">
				<div class="select-list">
					<ul>
						<li>
							参数名称：<input type="text" name="configName"/>
						</li>
						<li>
							参数键名：<input type="text" name="configKey"/>
						</li>
						<li>
                            <div class="r">
                                <div class="col-md-4"><span>系统内置：</span></div>
                                <div class="col-md-8">
                                    <select name="configType" class="form-control m-b">
                                        <option value="">所有</option>
                                        <@dictionary dictType="sys_yes_no">
                                            <#list dictData as row>
                                                <option value="${row.dictValue}">${row.dictLabel}</option>
                                            </#list>
                                        </@dictionary>
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
			<a class="btn btn-success" onclick="$.operate.add()" >
				<i class="fa fa-plus"></i> 新增
			</a>
			<a class="btn btn-primary btn-edit disabled" onclick="$.operate.edit()" >
				<i class="fa fa-edit"></i> 修改
			</a>
			<a class="btn btn-danger btn-del disabled" onclick="$.operate.removeAll()" >
				<i class="fa fa-remove"></i> 删除
			</a>
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
	<#--var editFlag = [[${@permission.hasPermi('system:config:edit')}]];-->
	<#--var removeFlag = [[${@permission.hasPermi('system:config:remove')}]];-->
	let datas = [];
<#--	<@dictionary dictType="sys_yes_no">-->
		<#list dictData as row>
			datas.push({"dictValue" : "${row.dictValue}", "listClass" : "${row.listClass}", "dictLabel" : "${row.dictLabel}"});
		</#list>
<#--	</@dictionary>-->
	let prefix = "${ctx}/system/config";

	$(function() {
		let options = {
			url: prefix + "/list",
			createUrl: prefix + "/add",
			updateUrl: prefix + "/edit/{id}",
			removeUrl: prefix + "/remove",
			exportUrl: prefix + "/export",
			sortName: "createTime",
			sortOrder: "desc",
			modalName: "系统参数",
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
				field: 'configName',
				title: '参数名称'
			},
			{
				field: 'configKey',
				title: '参数键名'
			},
			{
				field: 'configValue',
				title: '参数键值'
			},
			{
				field: 'configType',
				title: '系统内置',
				align: 'center',
				formatter: function(value, row, index) {
					return $.table.selectDictLabel(datas, value);
				}
			},
			{
				field: 'createTime',
				title: '创建时间'
			},
			{
				title: '操作',
				align: 'center',
				formatter: function(value, row, index) {
					let actions = [];
					actions.push('<a class="btn btn-success btn-xs" href="#" onclick="$.operate.edit(\'' + row.configId + '\')"><i class="fa fa-edit"></i>编辑</a> ');
                    actions.push('<a class="btn btn-danger btn-xs" href="#" onclick="$.operate.remove(\'' + row.configId + '\', $.operate.ajaxSuccess)"><i class="fa fa-remove"></i>删除</a>');
					return actions.join('');
				}
			}]
		};
		$.table.init(options);
        $.common.initBind();
	});
</script>
