	<div class="container-div">
		<div class="row">
			<div class="col-sm-12 search-collapse">
				<form id="jobLog-form">
					<div class="select-list">
						<ul>
							<li>
								<label>任务名称：</label><input type="text" name="jobName"/>
							</li>
							<li>
								<label>方法名称：</label><input type="text" name="methodName"/>
							</li>
							<li>
                                <label>执行状态：</label><select class="form-control m-b"
                                                            name="status"<#-- th:with="type=${@dict.getType('sys_common_status')}"-->>
									<option value="">所有</option>
									<#--<option th:each="dict : ${type}" th:text="${dict.dictLabel}" th:value="${dict.dictValue}"></option>-->
								</select>
							</li>
							<li class="select-time">
								<label>执行时间： </label>
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
				 <a class="btn btn-danger btn-del disabled" onclick="$.operate.removeAll()" shiro:hasPermission="monitor:job:remove">
	                <i class="fa fa-remove"></i> 删除
	            </a>
	            <a class="btn btn-danger" onclick="$.operate.clean()" shiro:hasPermission="monitor:job:remove">
	                <i class="fa fa-trash"></i> 清空
	            </a>
	            <a class="btn btn-warning" onclick="$.table.exportExcel()" shiro:hasPermission="monitor:job:export">
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
		let prefix = "${ctx}monitor/jobLog";

		$(function() {
		    let options = {
		        url: prefix + "/list",
		        cleanUrl: prefix + "/clean",
		        removeUrl: prefix + "/remove",
		        exportUrl: prefix + "/export",
		        sortName: "createTime",
		        sortOrder: "desc",
		        modalName: "调度日志",
		        search: false,
		        showExport: false,
		        columns: [{
		            checkbox: true
		        },
		        {
		            field: 'jobLogId',
		            title: '日志编号'
		        },
		        {
		            field: 'jobName',
		            title: '任务名称',
		            sortable: true
		        },
		        {
		            field: 'jobGroup',
		            title: '任务组名',
		            sortable: true
		        },
		        {
		            field: 'methodName',
		            title: '方法名称'
		        },
		        {
		            field: 'methodParams',
		            title: '方法参数'
		        },
		        {
		            field: 'jobMessage',
		            title: '日志信息'
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
		                let actions = [];
						<@shiro.hasPermission name="monitor:job:remove">
                        actions.push('<a class="btn btn-danger btn-xs" href="#" onclick="$.operate.remove(\'' + row.jobLogId + '\', $.operate.ajaxSuccess)"><i class="fa fa-remove"></i>删除</a>');
						</@shiro.hasPermission>
		                return actions.join('');
		            }
		        }]
		    };
		    $.table.init(options);
		});
	</script>
