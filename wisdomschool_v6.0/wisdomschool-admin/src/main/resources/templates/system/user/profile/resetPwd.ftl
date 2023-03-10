<!DOCTYPE html>
<html lang="zh" xmlns:th="http://www.thymeleaf.org" >
<meta charset="utf-8">
<head th:include="include :: header"></head>
<body class="white-bg">
	<div class="wrapper wrapper-content animated fadeInRight ibox-content">
		<form class="form-horizontal m" id="form-sysUser-resetPwd">
			<input name="userId"  type="hidden"  th:value="${sysUser.userId}" />
			<div class="form-group">
				<label class="col-sm-3 control-label ">登录名称：</label>
				<div class="col-sm-8">
					<input class="form-control" type="text" readonly="true" name="loginName" th:value="${sysUser.loginName}"/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">旧密码：</label>
				<div class="col-sm-8">
					<input class="form-control" type="password" name="oldPassword" id="oldPassword">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">新密码：</label>
				<div class="col-sm-8">
					<input class="form-control" type="password" name="password" id="password">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">再次确认：</label>
				<div class="col-sm-8">
					<input class="form-control" type="password" name="confirm" id="confirm">
					<span class="help-block m-b-none"><i class="fa fa-info-circle"></i> 请再次输入您的密码</span>
				</div>
			</div>
		</form>
	</div>
	<div th:include="include :: footer"></div>

	<script>
		$("#form-sysUser-resetPwd").validate({
			rules:{
				oldPassword:{
					required:true,
					remote: {
	                    url: ctx + "system/sysUser/profile/checkPassword",
	                    type: "get",
	                    dataType: "json",
	                    data: {
	                        password: function() {
	                            return $("input[name='oldPassword']").val();
	                        }
	                    }
	                }
				},
				password: {
	                required: true,
	                minlength: 5,
	    			maxlength: 20
	            },
	            confirm: {
	                required: true,
	                equalTo: "#password"
	            }
			},
			messages: {
	            oldPassword: {
	                required: "请输入原密码",
	                remote: "原密码错误"
	            },
	            password: {
	                required: "请输入新密码",
	                minlength: "密码不能小于6个字符",
	                maxlength: "密码不能大于20个字符"
	            },
	            confirm: {
	                required: "请再次输入新密码",
	                equalTo: "两次密码输入不一致"
	            }

	        }
		});
		
		function submitHandler() {
	        if ($.validate.form()) {
                $.operate.saveModal(ctx + "system/sysUser/profile/resetPwd", $('#form-sysUser-resetPwd').serialize());
	        }
	    }
	</script>
</body>

</html>
