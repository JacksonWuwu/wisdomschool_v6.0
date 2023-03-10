<!DOCTYPE html>
<html lang="zh" xmlns:th="http://www.thymeleaf.org" >
<meta charset="utf-8">
<head th:include="include :: header"></head>
<body class="white-bg">
	<div class="wrapper wrapper-content animated fadeInRight ibox-content">
		<form class="form-horizontal m" id="form-sysUser-edit" th:object="${sysUser}">
			<input name="userId"  type="hidden"  th:field="*{userId}" />
			<div class="form-group">
				<label class="col-sm-3 control-label ">登录名称：</label>
				<div class="col-sm-8">
					<input class="form-control" type="text" readonly="true" th:field="*{loginName}"/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">部门名称：</label>
				<div class="col-sm-8">
					<input class="form-control" type="text" readonly="true" th:field="*{dept.deptName}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">用户名称：</label>
				<div class="col-sm-8">
					<input class="form-control" type="text" name="userName" id="userName" th:field="*{userName}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">邮箱：</label>
				<div class="col-sm-8">
					<input class="form-control" type="text" name="email" th:field="*{email}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">手机：</label>
				<div class="col-sm-8">
					<input class="form-control" type="text" name="phoneNumber" id="phoneNumber" th:field="*{phoneNumber}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">性别：</label>
				<div class="col-sm-8">
					<div class="radio-box">
						<input type="radio" id="radio1" th:field="*{sex}" name="sex" value="0">
						<label for="radio1">男</label>
					</div>
					<div class="radio-box">
						<input type="radio" id="radio2" th:field="*{sex}" name="sex" value="1">
						<label for="radio2">女</label>
					</div>
				</div>
			</div>
			
		</form>
	</div>
	<div th:include="include::footer"></div>
	<script>
		$("#form-sysUser-edit").validate({
			rules:{
				userName:{
					required:true,
				},
				email:{
					required:true,
		            email:true,
		            remote: {
		                url: ctx + "system/sysUser/checkEmailUnique",
		                type: "post",
		                dataType: "json",
		                data: {
		                	"userId": function() {
		                        return $("#userId").val();
		                    },
		        			"email": function() {
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
		                	"userId": function() {
		                		return $("#userId").val();
		                    },
		        			"phoneNumber": function() {
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
                $.operate.saveModal(ctx + "system/sysUser/profile/update", $('#form-sysUser-edit').serialize());
	        }
	    }
	</script>
</body>
</html>
