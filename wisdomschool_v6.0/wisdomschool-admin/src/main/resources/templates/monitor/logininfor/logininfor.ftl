	<div class="container-div">
		<div class="row">
			<div class="col-sm-12 search-collapse">
				<form id="logininfor-form">
					<div class="select-list">
						<ul>
							<li>
								<label>登录地址：</label><input type="text" name="ipaddr"/>
							</li>
							<li>
								<label>登录名称：</label><input type="text" name="loginName"/>
							</li>
							<li>
								<label>登录状态：</label>
								<select name="status">
									<option value="">所有</option>
<#--									<@dictionary dictType="sys_common_status">-->
										<#list dictData as row>
											<option value="${row.dictValue}">${row.dictLabel}</option>
										</#list>
<#--									</@dictionary>-->
								</select>
							</li>
							<li class="select-time">
								<label>登录时间： </label>
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
				<a class="btn btn-danger btn-del disabled" onclick="$.operate.removeAll()" shiro:hasPermission="monitor:logininfor:remove">
		            <i class="fa fa-remove"></i> 删除
		        </a>
		        <a class="btn btn-danger" onclick="$.operate.clean()" shiro:hasPermission="monitor:logininfor:remove">
	                <i class="fa fa-trash"></i> 清空
	            </a>
	            <a class="btn btn-warning" onclick="$.table.exportExcel()" shiro:hasPermission="monitor:logininfor:export">
		            <i class="fa fa-download"></i> 导出
		        </a>
	        </div>

	        <div class="col-sm-12 select-table table-striped">
			    <table id="bootstrap-table" data-mobile-responsive="true"></table>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		let datas = [];
<#--		<@dictionary dictType="sys_common_status">-->
		<#list dictData as row>
		datas.push({"dictValue" : "${row.dictValue}", "listClass" : "${row.listClass}", "dictLabel" : "${row.dictLabel}"});
		</#list>
<#--		</@dictionary>-->
		let prefix = "${ctx}/monitor/logininfor";

		$(function() {
		    var options = {
		        url: prefix + "/list",
		        cleanUrl: prefix + "/clean",
		        removeUrl: prefix + "/remove",
		        exportUrl: prefix + "/export",
		        sortName: "loginTime",
		        sortOrder: "desc",
		        modalName: "登录日志",
		        search: false,
		        showExport: false,
		        columns: [{
		            checkbox: true
		        },
		        {
		            field: 'infoId',
		            title: '访问编号'
		        },
		        {
		            field: 'loginName',
		            title: '登录名称',
		            sortable: true
		        },
		        {
		            field: 'ipaddr',
		            title: '登录地址'
		        },
		        {
		            field: 'loginLocation',
		            title: '登录地点'
		        },
		        {
		            field: 'browser',
		            title: '浏览器'
		        },
		        {
		            field: 'os',
		            title: '操作系统'
		        },
		        {
		            field: 'status',
		            title: '登录状态',
		            align: 'center',
		            formatter: function(value, row, index) {
		            	return $.table.selectDictLabel(datas, value);
		            }
		        },
		        {
		            field: 'msg',
		            title: '操作信息'
		        },
		        {
		            field: 'loginTime',
		            title: '登录时间',
		            sortable: true
		        }]
		    };
		    $.table.init(options);
		});
	</script>