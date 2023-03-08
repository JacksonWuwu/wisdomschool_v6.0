<div class="container-div">
	<div class="row">
		<div class="col-sm-12 search-collapse">
			<form id="role-form">
				<div class="select-list">
					<ul>
						<li>
							角色名称：<input type="text" name="roleName"/>
						</li>
						<li>
							权限字符：<input type="text" name="roleKey"/>
						</li>
						<li>
                            <div class="r">
                                <div class="col-md-4"><span>角色状态：</span></div>
                                <div class="col-md-8">
                                    <select name="status" class="form-control m-b">
                                        <option value="">所有</option>
                                        <@dictionary dictType="sys_normal_disable">
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
<#--			<@shiro.hasPermission name="system:role:add">-->
				<a class="btn btn-success" onclick="$.operate.add()">
					<i class="fa fa-plus"></i> 新增
				</a>
<#--			</@shiro.hasPermission>-->
<#--			<@shiro.hasPermission name="system:role:edit">-->
				<a class="btn btn-primary btn-edit disabled" onclick="$.operate.edit()">
					<i class="fa fa-edit"></i> 修改
				</a>
<#--			</@shiro.hasPermission>-->
			<#--<@shiro.hasPermission name="system:role:remove">
				<a class="btn btn-danger btn-del disabled" onclick="$.operate.removeAll()">
					<i class="fa fa-remove"></i> 删除
				</a>
			</@shiro.hasPermission>-->
<#--			<@shiro.hasPermission name="system:role:export">-->
				<a class="btn btn-warning" onclick="$.table.exportExcel()">
					<i class="fa fa-download"></i> 导出
				</a>
<#--			</@shiro.hasPermission>-->
		</div>

		<div class="col-sm-12 select-table table-striped">
			<table id="bootstrap-table" class="bootstrap-table" data-mobile-responsive="true"></table>
		</div>
	</div>
</div>
<script type="text/javascript">

	let datas = [];
	<@dictionary dictType="sys_normal_disable">
		<#list dictData as row>
			datas.push({"dictValue" : "${row.dictValue}", "listClass" : "${row.listClass}", "dictLabel" : "${row.dictLabel}"});
		</#list>
	</@dictionary>
	let prefix = "/system/role";


	$(function() {
		let options = {
			url: prefix + "/list",
			createUrl: prefix + "/add",
			updateUrl: prefix + "/edit/{id}",
			removeUrl: prefix + "/remove",
			exportUrl: prefix + "/export",
			sortName: "roleSort",
			modalName: "角色",
			search: false,
			showExport: false,
			columns: [{
				checkbox: true
			},
			{
				field: 'id',
				title: '角色编号'
			},
			{
				field: 'roleName',
				title: '角色名称',
				sortable: true
			},
			{
				field: 'roleKey',
				title: '权限字符',
				sortable: true
			},
			{
				field: 'roleSort',
				title: '显示顺序',
				sortable: true
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
				field: 'createTime',
				title: '创建时间',
				sortable: true
			},
			{
				title: '操作',
				align: 'center',
				formatter: function(value, row, index) {
					var actions = [];
<#--					<@shiro.hasPermission name="system:role:edit">-->
						actions.push('<a class="btn btn-success btn-xs " href="#" onclick="$.operate.edit(\'' + row.id + '\')"><i class="fa fa-edit"></i>编辑</a> ');
<#--					</@shiro.hasPermission>-->

					if (row.id > 1)
<#--					<@shiro.hasPermission name="system:role:remove">-->
						actions.push('<a class="btn btn-danger btn-xs " href="#" onclick="$.operate.remove(\'' + row.id + '\', $.operate.ajaxSuccess)"><i class="fa fa-remove"></i>删除</a>');
<#--					</@shiro.hasPermission>-->
					return actions.join('');
				}
			}]
		};
		$.table.init(options);
        $.common.initBind();
	});

	/*角色管理-分配数据权限*/
	/*function permission(roleId) {
		let url = prefix + '/permission/' + roleId;
		$.modal.open("分配数据权限", url);
	}*/
</script>
