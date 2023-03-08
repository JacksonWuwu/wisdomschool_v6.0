<!DOCTYPE html>
<html lang="zh" xmlns:th="http://www.thymeleaf.org" >
<meta charset="utf-8">
<head th:include="include :: header"></head>
<body class="white-bg">
	<div class="wrapper wrapper-content animated fadeInRight ibox-content">
		<form class="form-horizontal m" id="form-sysUser-add">
			<input name="deptId"  type="hidden" id="treeId"/>
			<div class="form-group">
				<label class="col-sm-3 control-label ">登录名称：</label>
				<div class="col-sm-8">
					<input class="form-control" type="text" id="loginName" name="loginName"/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">部门名称：</label>
				<div class="col-sm-8">
					<input class="form-control" type="text" name="deptName" onclick="selectDeptTree()" readonly="true" id="treeName">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">用户名称：</label>
				<div class="col-sm-8">
					<input class="form-control" type="text" name="userName" id="userName">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">密码：</label>
				<div class="col-sm-8">
					<input class="form-control" type="password" name="password" id="password" th:value="${@config.getKey('sys.sysUser.initPassword')}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">邮箱：</label>
				<div class="col-sm-8">
					<input class="form-control" type="text" name="email" id="email">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">手机：</label>
				<div class="col-sm-8">
					<input class="form-control" type="text" name="phoneNumber" id="phoneNumber">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">性别：</label>
				<div class="col-sm-8">
					<select id="sex" class="form-control m-b" th:with="type=${@dict.getType('sys_user_sex')}">
	                    <option th:each="dict : ${type}" th:text="${dict.dictLabel}" th:value="${dict.dictValue}"></option>
	                </select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">状态：</label>
				<div class="col-sm-8">
					<div class="onoffswitch">
                         <input type="checkbox" th:checked="true" class="onoffswitch-checkbox" id="status" name="status">
                         <label class="onoffswitch-label" for="status">
                             <span class="onoffswitch-inner"></span>
                             <span class="onoffswitch-switch"></span>
                         </label>
                     </div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">岗位：</label>
				<div class="col-sm-8">
					<select id="post" name="post" class="form-control select2-hidden-accessible" multiple="">
						<option th:each="post:${posts}" th:value="${post.postId}" th:text="${post.postName}" th:disabled="${post.status == '1'}"></option>
					</select>
					
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">角色：</label>
				<div class="col-sm-8">
					<label th:each="role:${roles}" class="check-box">
						<input name="role" type="checkbox" th:value="${role.roleId}" th:text="${role.roleName}" th:disabled="${role.status == '1'}">
					</label>
				</div>
			</div>
		</form>
	</div>
	<div th:include="include::footer"></div>
	<script th:src="@{/ajax/libs/select/select2.js}"></script>
	<script>
        $("#form-sysUser-add").validate({
        	rules:{
        		loginName:{
        			required:true,
        			minlength: 2,
        			maxlength: 20,
        			remote: {
                        url: ctx + "system/sysUser/checkLoginNameUnique",
                        type: "post",
                        dataType: "json",
                        data: {
                        	name : function() {
                                return $.common.trim($("#loginName").val());
                            }
                        },
                        dataFilter: function(data, type) {
                        	return $.validate.unique(data);
                        }
                    }
        		},
        		userName:{
        			required:true,
        		},
        		deptName:{
        			required:true,
        		},
        		password:{
        			required:true,
        			minlength: 5,
        			maxlength: 20
        		},
        		email:{
        			required:true,
                    email:true,
                    remote: {
                        url: ctx + "system/sysUser/checkEmailUnique",
                        type: "post",
                        dataType: "json",
                        data: {
                            name: function () {
                                return $.common.trim($("#email").val());
                            }
                        },
                        dataFilter: function (data, type) {
                        	return $.validate.unique(data);
                        }
                    }
        		},
                phoneNumber:{
        			required:true,
        			isPhone:true,
                    remote: {
                        url: ctx + "system/sysUser/checkPhoneUnique",
                        type: "post",
                        dataType: "json",
                        data: {
                            name: function () {
                                return $.common.trim($("#phoneNumber").val());
                            }
                        },
                        dataFilter: function (data, type) {
                        	return $.validate.unique(data);
                        }
                    }
        		},
        	},
        	messages: {
                "loginName": {
                    remote: "用户已经存在"
                },
        		"email": {
                    remote: "Email已经存在"
                },
        		"phoneNumber":{
                	remote: "手机号码已经存在"
        		}
            }
        });
        
        function submitHandler() {
	        if ($.validate.form()) {
	        	add();
	        }
	    }

        function add() {
        	var userId = $("input[name='userId']").val();
        	var deptId = $("input[name='deptId']").val();
        	var loginName = $("input[name='loginName']").val();
        	var userName = $("input[name='userName']").val();
        	var password = $("input[name='password']").val();
        	var email = $("input[name='email']").val();
        	var phoneNumber = $("input[name='phoneNumber']").val();
        	var sex = $("#sex option:selected").val();
        	var status = $("input[name='status']").is(':checked') == true ? 0 : 1;
        	var roleIds = $.form.selectCheckeds("role");
        	var postIds = $.form.selectSelects("post");
        	$.ajax({
        		cache : true,
        		type : "POST",
        		url : ctx + "system/sysUser/add",
        		data : {
        			"userId": userId,
        			"deptId": deptId,
        			"loginName": loginName,
        			"userName": userName,
        			"password": password,
        			"email": email,
        			"phoneNumber": phoneNumber,
        			"sex": sex,
        			"status": status,
        			"roleIds": roleIds,
        			"postIds": postIds
        		},
        		async : false,
        		error : function(request) {
        			$.modal.alertError("系统错误");
        		},
        		success : function(data) {
                    $.operate.ajaxModalSuccess(data);
        		}
        	});
        }

        /*用户管理-新增-选择部门树*/
        function selectDeptTree() {
        	var treeId = $("#treeId").val();
        	var deptId = $.common.isEmpty(treeId) ? "100" : $("#treeId").val();
        	var url = ctx + "system/dept/selectDeptTree/" + deptId;
			var options = {
				title: '选择部门',
				width: "380",
				url: ctx + "system/dept/selectDeptTree/" + deptId,
				callBack: doSubmit
			};
			$.modal.openOptions(options);
		}
		
		function doSubmit(index, layero){
			var tree = layero.find("iframe")[0].contentWindow.$._tree;
			if ($.tree.notAllowParents(tree)) {
				var body = layer.getChildFrame('body', index);
    			$("#treeId").val(body.find('#treeId').val());
    			$("#treeName").val(body.find('#treeName').val());
    			layer.close(index);
			}
		}
    </script>
</body>
</html>
