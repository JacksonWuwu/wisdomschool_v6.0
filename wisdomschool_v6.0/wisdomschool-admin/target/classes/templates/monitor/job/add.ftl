<!DOCTYPE html>
<html lang="zh" xmlns:th="http://www.thymeleaf.org" >
<meta charset="utf-8">
<head th:include="include :: header"></head>
<body class="white-bg">
	<div class="wrapper wrapper-content animated fadeInRight ibox-content">
		<form class="form-horizontal m" id="form-job-add">
			<div class="form-group">
				<label class="col-sm-3 control-label">任务名称：</label>
				<div class="col-sm-8">
					<input class="form-control" type="text" name="jobName" id="jobName">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">任务组名：</label>
				<div class="col-sm-8">
					<input class="form-control" type="text" name="jobGroup" id="jobGroup">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label ">方法名称：</label>
				<div class="col-sm-8">
					<input class="form-control" type="text" name="methodName" id="methodName"/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label ">方法参数：</label>
				<div class="col-sm-8">
					<input class="form-control" type="text" name="methodParams" id="methodParams"/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label ">cron表达式：</label>
				<div class="col-sm-8">
					<input class="form-control" type="text" name="cronExpression" id="cronExpression"/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">执行策略：</label>
				<div class="col-sm-8">
					<label class="radio-box"> <input type="radio" name="misfirePolicy" value="1" th:checked="true"/> 继续执行 </label> 
					<label class="radio-box"> <input type="radio" name="misfirePolicy" value="2" /> 一次执行 </label> 
					<label class="radio-box"> <input type="radio" name="misfirePolicy" value="3" /> 放弃执行 </label>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">状态：</label>
				<div class="col-sm-8">
				    <div class="radio-box" th:each="dict : ${@dict.getType('sys_job_status')}">
						<input type="radio" th:id="${dict.dictCode}" name="status" th:value="${dict.dictValue}" th:checked="${dict.isDefault == 'Y' ? true : false}">
						<label th:for="${dict.dictCode}" th:text="${dict.dictLabel}"></label>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">备注：</label>
				<div class="col-sm-8">
					<textarea id="remark" name="remark" class="form-control"></textarea>
				</div>
			</div>
		</form>
	</div>
	<div th:include="include::footer"></div>
	<script type="text/javascript">
		var prefix = ctx + "monitor/job";
	
		$("#form-job-add").validate({
			rules:{
				jobName:{
					required:true,
				},
				jobGroup:{
					required:true,
				},
				cronExpression:{
					required:true,
					remote: {
	                    url: prefix + "/checkCronExpressionIsValid",
	                    type: "post",
	                    dataType: "json",
	                    data: {
	                        "cronExpression": function() {
	                            return $.common.trim($("#cronExpression").val());
	                        }
	                    },
	                    dataFilter: function(data, type) {
	                    	return data;
	                    }
	                }
				},
			},
			messages: {
	            "cronExpression": {
	                remote: "表达式不正确"
	            }
	        }
		});
		
		function submitHandler() {
	        if ($.validate.form()) {
                $.operate.saveModal(prefix + "/add", $('#form-job-add').serialize());
	        }
	    }
	</script>
</body>
</html>
