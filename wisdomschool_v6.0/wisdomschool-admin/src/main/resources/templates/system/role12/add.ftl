<#include "/common/style.ftl"/>
<body class="white-bg">
<div class="container-div clearfix">
	<div class="wrapper wrapper-content animated fadeInRight ibox-content">
		<form class="form-horizontal m" id="form-role-add">
			<div class="form-group">
				<label class="col-sm-3 control-label ">角色名称：</label>
				<div class="col-sm-8">
					<input class="form-control" type="text" name="roleName" id="roleName"/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">权限字符：</label>
				<div class="col-sm-8">
					<input class="form-control" type="text" name="roleKey" id="roleKey">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">显示顺序：</label>
				<div class="col-sm-8">
					<input class="form-control" type="text" name="roleSort" id="roleSort">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">状态：</label>
				<div class="col-sm-8">
					<div class="onoffswitch">
						<input type="checkbox" checked="true" class="onoffswitch-checkbox" id="status" name="status">
						<label class="onoffswitch-label" for="status">
							<span class="onoffswitch-inner"></span>
							<span class="onoffswitch-switch"></span>
						</label>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">备注：</label>
				<div class="col-sm-8">
					<input id="remark" name="remark" class="form-control" type="text">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">菜单权限</label>
				<div class="col-sm-8">
					<div id="menuTrees" class="ztree"></div>
				</div>
			</div>
		</form>
	</div>
</div><!--/.container-div-->
<#include "/common/stretch.ftl"/>
<script type="text/javascript">
	$(function () {
		$.common.initBind();
	});
	let prefix = "${ctx}/system/role";
	$(function() {
		let url = "${ctx}/system/menu/roleMenuTreeData";
		let options = {
			id: "menuTrees",
			url: url,
			check: { enable: true, nocheckInherit: true, chkboxType: { "Y": "ps", "N": "ps" } },
			expandLevel: 0
		};
		$.tree.init(options);
	});

	$("#form-role-add").validate({
		rules:{
			roleName:{
				required:true,
				remote: {
					url: prefix + "/checkRoleNameUnique",
					type: "post",
					dataType: "json",
					data: {
						"roleName" : function() {
							return $.common.trim($("#roleName").val());
						}
					},
					dataFilter: function(data, type) {
						return $.validate.unique(data);
					}
				}
			},
			roleKey:{
				required:true,
				remote: {
					url: prefix + "/checkRoleKeyUnique",
					type: "post",
					dataType: "json",
					data: {
						"roleName" : function() {
							return $.common.trim($("#roleName").val());
						}
					},
					dataFilter: function(data, type) {
						return $.validate.unique(data);
					}
				}
			},
			roleSort:{
				required:true,
				digits:true
			},
		},
		messages: {
			"roleName": {
				remote: "角色名称已经存在"
			},
			"roleKey": {
				remote: "角色权限已经存在"
			}
		}
	});

	function submitHandler() {
		if ($.validate.form()) {
			add();
		}
	}

	function add() {
		let roleName = $("input[name='roleName']").val();
		let roleKey = $("input[name='roleKey']").val();
		let roleSort = $("input[name='roleSort']").val();
		let status = $("input[name='status']").is(':checked') == true ? 0 : 1;
		let remark = $("input[name='remark']").val();
		let menuIds = $.tree.getCheckedNodes();
		$.operate.postJson(prefix + "/add", {
			"roleName": roleName,
			"roleKey": roleKey,
			"roleSort": roleSort,
			"status": status,
			"remark": remark,
			"menuIds": menuIds
		}, true, $.operate.ajaxModalSuccess)
	}
</script>
</body>