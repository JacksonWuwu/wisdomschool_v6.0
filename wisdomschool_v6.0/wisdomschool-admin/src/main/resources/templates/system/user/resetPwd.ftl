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
				<label class="col-sm-3 control-label">输入密码：</label>
				<div class="col-sm-8">
					<input class="form-control" type="password" name="password" id="password" th:value="${@config.getKey('sys.sysUser.initPassword')}">
				</div>
			</div>
		</form>
	</div>
	<div th:include="include :: footer"></div>
	<script type="text/javascript">
		$("#form-sysUser-resetPwd").validate({
			rules:{
				password:{
					required:true,
					minlength: 5,
					maxlength: 20
				},
			}
		});
		
		function submitHandler() {
	        if ($.validate.form()) {
                $.operate.saveModal(ctx + "system/sysUser/resetPwd", $('#form-sysUser-resetPwd').serialize());
	        }
	    }
	</script>
</body>

</html>
