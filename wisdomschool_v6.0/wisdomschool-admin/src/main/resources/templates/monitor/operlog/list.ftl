	<div class="container-div">
		<div class="row">

			<div class="btn-group-sm hidden-xs" id="toolbar" role="group">
				<a class="btn btn-danger btn-del disabled" onclick="$.operate.removeAll()" >
		            <i class="fa fa-remove"></i> 删除
		        </a>
		        <a class="btn btn-danger" onclick="$.operate.clean()" >
	                <i class="fa fa-trash"></i> 清空
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

		<#--var detailFlag = [[${@permission.hasPermi('monitor:operlog:detail')}]];-->
		let datas = [];
<#--		<@dictionary dictType="sys_oper_type">-->
			<#list dictData as row>
				datas.push({"dictValue" : "${row.dictValue}", "listClass" : "${row.listClass}", "dictLabel" : "${row.dictLabel}"});
			</#list>
<#--		</@dictionary>-->
		var prefix = "${ctx}/monitor/operlog";

		$(function() {
		    var options = {
		        url: prefix + "/list",
		        cleanUrl: prefix + "/clean",
		        detailUrl: prefix + "/detail/{id}",
		        removeUrl: prefix + "/remove",
		        exportUrl: prefix + "/export",
		        sortName: "operTime",
		        sortOrder: "desc",
		        modalName: "操作日志",
		        search: false,
		        showExport: false,
		        columns: [{
		            checkbox: true
		        },
		        {
		            field: 'id',
		            title: '日志编号'
		        },
		        {
		            field: 'title',
		            title: '系统模块'
		        },
		        {
                    field: 'actionType',
		            title: '操作类型',
		            align: 'center',
		            formatter: function(value, row, index) {
		            	return $.table.selectDictLabel(datas, value);
		            }
		        },
		        {
		            field: 'operName',
		            title: '操作人员',
		            sortable: true
		        },
		        // {
		        //     field: 'deptName',
		        //     title: '部门名称'
		        // },
		        {
		            field: 'operIp',
		            title: '主机'
		        },
		        {
		            field: 'operLocation',
		            title: '操作地点'
		        },
		        {
		            field: 'status',
		            title: '操作状态',
		            align: 'center',
		            formatter: function(value, row, index) {
		                if (value == 0) {
		                    return '<span class="badge badge-primary">成功</span>';
		                } else if (value == 1) {
		                    return '<span class="badge badge-danger">异常</span>';
		                }
		            }
		        },
		        {
		            field: 'operTime',
		            title: '操作时间',
		            sortable: true
		        },
		        {
		            title: '操作',
		            align: 'center',
		            formatter: function(value, row, index) {
		                var actions = [];
		                actions.push('<a class="btn btn-warning btn-xs " href="#" onclick="$.operate.detail(\'' + row.operId + '\')"><i class="fa fa-search"></i>详细</a>');
		                return actions.join('');
		            }
		        }]
		    };
		    $.table.init(options);
		});
	</script>
