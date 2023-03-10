    <div class="container-div">
		<div class="row">
			<div class="col-sm-12 search-collapse">
				<form id="online-form">
					<div class="select-list">
						<ul>
							<li>
								登录地址：<input type="text" name="ipaddr"/>
							</li>
							<li>
								操作人员：<input type="text" name="loginName"/>
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
	            <a class="btn btn-danger btn-del disabled" onclick="javascript:batchForceLogout()" shiro:hasPermission="monitor:online:batchForceLogout">
	                <i class="fa fa-sign-out"></i> 强退
	            </a>
	        </div>

	        <div class="col-sm-12 select-table table-striped">
			    <table id="bootstrap-table" data-mobile-responsive="true"></table>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		let prefix = "${ctx}/monitor/online";

		$(function() {
		    let options = {
		        url: prefix + "/list",
		        exportUrl: prefix + "/export",
		        sortName: "lastAccessTime",
		        sortOrder: "desc",
		        search: false,
		        columns: [{
		            checkbox: true
		        },
		        {
		            field: 'id',
		            title: '编号'
		        },
		        {
		            field: 'loginName',
		            title: '登录名称',
		            sortable: true
		        },
		        {
		            field: 'ipaddr',
		            title: '主机'
		        },
		        {
		            field: 'longinLocation',
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
		            title: '会话状态',
		            align: 'center',
		            formatter: function(value, row, index) {
		                if (value == 'on_line') {
		                    return '<span class="badge badge-primary">在线</span>';
		                } else if (value == 'off_line') {
		                    return '<span class="badge badge-danger">离线</span>';
		                }
		            }
		        },
		        {
		            field: 'startTimestamp',
		            title: '登录时间',
		            sortable: true
		        },
		        {
		            field: 'lastAccessTime',
		            title: '最后访问时间',
		            sortable: true
		        },
		        {
		            title: '操作',
		            align: 'center',
		            formatter: function(value, row, index) {
						let msg;
<#--						<@shiro.hasPermission name="monitor:online:forceLogout">-->
							msg = '<a class="btn btn-danger btn-xs" href="#" onclick="forceLogout(\'' + row.id + '\')"><i class="fa fa-sign-out"></i>强退</a> ';
<#--						</@shiro.hasPermission>-->
		                return msg;
		            }
		        }]
		    };
		    $.table.init(options);
		});

		// 单条强退
		function forceLogout(sessionId) {
		    $.modal.confirm("确定要强制选中用户下线吗？", function() {
		    	let data = { "": sessionId };
                $.operate.submitPost(prefix + "/forceLogout", data);
		    })
		}

		// 批量强退
		function batchForceLogout() {
		    let rows = $.table.selectColumns("id");
		    if (rows.length === 0) {
		        $.modal.alertWarning("请选择要强退的用户");
		        return;
		    }
		    $.modal.confirm("确认要强退选中的" + rows.length + "条数据吗?", function() {
		        let url = prefix + "/batchForceLogout";
		        let data = { "ids": rows };
                $.operate.submitPost(url, data);
		    });
		}
	</script>
