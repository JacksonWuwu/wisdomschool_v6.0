	<div class="container-div">
		<div class="row">
			<div class="col-sm-12 search-collapse">
				<form id="job-form">
					<div class="select-list">
						<ul>
							<li>
								任务名称：<input type="text" name="jobName"/>
							</li>
							<li>
								方法名称：<input type="text" name="methodName"/>
							</li>
							<li>
								任务状态：<select name="status">
									<option value="">所有</option>
<#--									<@dictionary dictType="sys_job_status">-->
										<#list dictData as row>
											<option value="${dict.dictValue}">${dict.dictLabel}</option>
										</#list>
<#--									</@dictionary>-->
								</select>
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

<#--				<@shiro.hasPermission name="monitor:job:add">-->
					<a class="btn btn-success" onclick="$.operate.add()">
						<i class="fa fa-plus"></i> 新增
					</a>
<#--				</@shiro.hasPermission>-->
<#--				<@shiro.hasPermission name="system:job:edit">-->
					<a class="btn btn-primary btn-edit disabled" onclick="$.operate.edit()">
						<i class="fa fa-edit"></i> 修改
					</a>
<#--				</@shiro.hasPermission>-->
<#--				<@shiro.hasPermission name="monitor:job:remove">-->
					<a class="btn btn-danger btn-del disabled" onclick="$.operate.removeAll()">
						<i class="fa fa-remove"></i> 删除
					</a>
<#--				</@shiro.hasPermission>-->
<#--				<@shiro.hasPermission name="monitor:job:list">-->
					<a class="btn btn-success" onclick="javascript:jobLog()">
						<i class="fa fa-list"></i> 日志
					</a>
<#--				</@shiro.hasPermission>-->
<#--				<@shiro.hasPermission name="monitor:job:export">-->
					<a class="btn btn-warning" onclick="$.table.exportExcel()">
						<i class="fa fa-download"></i> 导出
					</a>
<#--				</@shiro.hasPermission>-->

	        </div>

	        <div class="col-sm-12 select-table table-striped">
			    <table id="bootstrap-table" data-mobile-responsive="true"></table>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		let datas = [];
<#--		<@dictionary dictType="sys_job_status">-->
			<#list dictData as row>
				datas.push({"dictValue" : "${row.dictValue}", "listClass" : "${row.listClass}", "dictLabel" : "${row.dictLabel}"});
			</#list>
<#--		</@dictionary>-->
		let prefix = "${ctx}/monitor/job";

		$(function() {
		    let options = {
		        url: prefix + "/list",
		        createUrl: prefix + "/add",
		        updateUrl: prefix + "/edit/{id}",
		        removeUrl: prefix + "/remove",
		        exportUrl: prefix + "/export",
		        sortName: "createTime",
		        sortOrder: "desc",
		        modalName: "任务",
		        search: false,
		        showExport: false,
		        columns: [{
		            checkbox: true
		        },
		        {
		            field: 'jobId',
		            title: '任务编号'
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
		            field: 'cronExpression',
		            title: '执行表达式'
		        },
		        {
		            field: 'status',
		            title: '任务状态',
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
		                	actions.push(statusTools(row));
<#--						<@shiro.hasPermission name="monitor:job:edit">-->
							actions.push('<a class="btn btn-success btn-xs" href="#" onclick="$.operate.edit(\'' + row.jobId + '\')"><i class="fa fa-edit"></i>编辑</a> ');
<#--						</@shiro.hasPermission>-->
<#--						<@shiro.hasPermission name="monitor:job:remove">-->
                        actions.push('<a class="btn btn-danger btn-xs" href="#" onclick="$.operate.remove(\'' + row.jobId + '\', $.operate.ajaxSuccess)"><i class="fa fa-remove"></i>删除</a>');
<#--						</@shiro.hasPermission>-->
<#--						<@shiro.hasPermission name="monitor:job:changeStatus">-->
							actions.push('<a class="btn btn-primary btn-xs" href="#" onclick="run(\'' + row.jobId + '\')"><i class="fa fa-play-circle-o"></i> 执行</a> ');
<#--						</@shiro.hasPermission>-->
		                return actions.join('');
		            }
		        }]
		    };
		    $.table.init(options);
		});

		function statusTools(row) {
<#--			<@shiro.hasPermission name="monitor:job:changeStatus">-->
				if (row.status == 1) {
					return '<a class="btn btn-info btn-xs" href="#" onclick="start(\'' + row.jobId + '\')"><i class="fa fa-play"></i>启用</a> ';
				} else {
					return '<a class="btn btn-warning btn-xs" href="#" onclick="stop(\'' + row.jobId + '\')"><i class="fa fa-pause"></i>暂停</a> ';
				}
<#--			</@shiro.hasPermission>-->
		}

		/*立即执行一次*/
		function run(jobId) {
			$.modal.confirm("确认要立即执行任务吗？", function() {
                $.operate.submitPost(prefix + "/run", {"jobId": jobId});
		    })
		}

		/*调度任务-停用*/
		function stop(jobId) {
			$.modal.confirm("确认要停用任务吗？", function() {
                $.operate.submitPost(prefix + "/changeStatus", {"jobId": jobId, "status": 1});
		    })
		}

		/*调度任务-启用*/
		function start(jobId) {
			$.modal.confirm("确认要启用任务吗？", function() {
                $.operate.submitPost(prefix + "/changeStatus", {"jobId": jobId, "status": 0});
		    })
		}

		//调度日志查询
		function jobLog(id) {
			let url = '${ctx}/monitor/jobLog';
			$.common.loadPage("main-page-container", url);
		}
	</script>
