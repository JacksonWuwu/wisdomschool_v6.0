<#include "/common/style.ftl"/>
<body class="white-bg">
	<div class="container-div clearfix">
		<div class="wrapper wrapper-content animated fadeInRight ibox-content">
			<form class="form-horizontal m" id="form-menu-edit">
				<input name="id"   type="hidden" value="${menu.id}"   />
				<input id="treeId" name="parentId" type="hidden" value="${menu.parentId}" />
				<div class="form-group">
					<label class="col-sm-3 control-label ">上级菜单：</label>
					<div class="col-sm-8">
						<input class="form-control" type="text" onclick="selectMenuTree()" id="treeName" readonly="true" value="${menu.parentName}"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">菜单类型：</label>
					<div class="col-sm-8">
						<label class="radio-box"> <input type="radio" name="menuType" value="M" <#if menu.menuType == "M">checked</#if> /> 目录 </label>
						<label class="radio-box"> <input type="radio" name="menuType" value="C" <#if menu.menuType == "C">checked</#if> /> 菜单 </label>
						<label class="radio-box"> <input type="radio" name="menuType" value="F" <#if menu.menuType == "F">checked</#if> /> 按钮 </label>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">菜单名称：</label>
					<div class="col-sm-8">
						<input class="form-control" type="text" name="menuName" id="menuName" value="${menu.menuName}">
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">请求地址：</label>
					<div class="col-sm-8">
						<input id="url" name="url" class="form-control" type="text" value="${menu.url}">
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">权限标识：</label>
					<div class="col-sm-8">
						<input id="perms" name="perms" class="form-control" type="text" value="${menu.perms}">
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">显示排序：</label>
					<div class="col-sm-8">
						<input class="form-control" type="text" name="orderNum" value="${menu.orderNum}">
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">图标：</label>
					<div class="col-sm-8">
						<input id="icon" data-load-action="load-content" name="icon" class="form-control" type="text" placeholder="选择图标" value="${menu.icon}">
						<div class="ms-parent" style="width: 100%;">
							<div class="panel-drop animated flipInX" style="display: none;max-height:200px;overflow-y:auto">
								<div id="load-panel" data-include="/icon"></div>
							</div>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">菜单状态：</label>
					<div class="col-sm-8">
<#--						<@dictionary dictType="sys_show_hide">-->
							<#list dictData as row>
								<div class="radio-box" <#if row.dictValue == menu.visible>checked</#if>>
									<input type="radio" id="${row.dictCode}" name="visible" value="${row.dictValue}"
										   <#if row.dictValue == menu.visible>checked</#if> />
									<label for="${row.dictCode}">${row.dictLabel}</label>
								</div>
							</#list>
<#--						</@dictionary>-->
					</div>
				</div>
			</form>
		</div>
	</div><!--/.container-div-->
	<#include "/common/stretch.ftl"/>
	 <script>
		 let prefix = "${ctx}/system/menu";

		$("#form-menu-edit").validate({
			rules:{
				menuType:{
					required:true,
				},
				menuName:{
					required:true,
					remote: {
						url: prefix + "/checkMenuNameUnique",
						type: "post",
						dataType: "json",
						data: {
							"menuId": function() {
								return $("#menuId").val();
							},
							"parentId": function() {
								return $("input[name='parentId']").val();
							},
							"menuName": function() {
								return $.common.trim($("#menuName").val());
							}
						},
						dataFilter: function(data, type) {
							return $.validate.unique(data);
						}
					}
				},
				orderNum:{
					required:true,
					digits:true
				},
			},
			messages: {
				"menuName": {
					remote: "菜单已经存在"
				}
			}
		});

		function submitHandler() {
			if ($.validate.form()) {
                $.operate.saveModal(prefix + "/edit", $('#form-menu-edit').serialize());
			}
		}
		 function iconCallBack() {
			 $(".panel-drop").find(".ico-list i").on("click", function() {
				 let iconClazz = $(this).attr('class');
				 $('#icon').val(iconClazz);
				 $('#icon-show').attr('class', iconClazz);
				 $(".panel-drop").hide();
			 });
		 }

		$(function() {
            $.common.initBind();
			/*
			初始化菜单类型
			*/
			let menuType = $('input[name="menuType"]:checked').val();
			menuVisible(menuType);
			/*
            加载图标
             */
			$.form.loadContent(iconCallBack);
			$("#form-menu-edit").click(function(event) {
				let obj = event.srcElement || event.target;
				if (!$(obj).is("input[name='icon']")) {
					$(".icon-drop").hide();
				}
			});
			$('input').on('ifChecked',
			function(event) {
				let menuType = $(event.target).val();
				menuVisible(menuType);
			});
		});

		function menuVisible(menuType) {
			if (menuType == "M") {
				$("#url").parents(".form-group").hide();
				$("#perms").parents(".form-group").hide();
				$("#icon").parents(".form-group").show();
			} else if (menuType == "C") {
				$("#url").parents(".form-group").show();
				$("#perms").parents(".form-group").show();
				$("#icon").parents(".form-group").hide();
			} else if (menuType == "F") {
				$("#url").parents(".form-group").hide();
				$("#perms").parents(".form-group").show();
				$("#icon").parents(".form-group").hide();
			}
		}

		/*菜单管理-修改-选择菜单树*/
		function selectMenuTree() {
			let menuId = $("#treeId").val();
			if(menuId > 0) {
				let url = prefix + "/selectMenuTree/" + menuId;
				$.modal.open("选择菜单", url, '380', '380');
			} else {
				$.modal.alertError("主菜单不能选择");
			}
		}

		function selectMenuTree() {
			let menuId = $("#treeId").val();
			if(menuId > 0) {
				let url = prefix + "/selectMenuTree/" + menuId;
				let options = {
					title: '菜单选择',
					width: "380",
					url: url,
					callBack: doSubmit
				};
				$.modal.openOptions(options);
			} else {
				$.modal.alertError("主菜单不能选择");
			}
		}

		function doSubmit(index, layero){
			let body = layer.getChildFrame('body', index);
			$("#treeId").val(body.find('#treeId').val());
			$("#treeName").val(body.find('#treeName').val());
			layer.close(index);
		}
	</script>
</body>
