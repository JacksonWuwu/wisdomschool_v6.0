<#include "/common/style.ftl"/>
<body class="white-bg">
<div class="container-div clearfix">
	<div class="wrapper wrapper-content animated fadeInRight ibox-content">
		<form class="form-horizontal m" id="form-dict-edit">
			<input id="dictId" name="dictId"  type="hidden" value="${dict.dictId}" />
			<div class="form-group">
				<label class="col-sm-3 control-label ">字典名称：</label>
				<div class="col-sm-8">
					<input class="form-control" type="text" name="dictName" id="dictName" value="${dict.dictName}"/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">字典类型：</label>
				<div class="col-sm-8">
					<input class="form-control" type="text" name="dictType" id="dictType" value="${dict.dictType}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">状态：</label>
				<div class="col-sm-8">
<#--					<@dictionary dictType="sys_normal_disable">-->
						<#list dictData as row>
							<div class="radio-box" <#if row.dictValue == dict.status>checked</#if>>
								<input type="radio" id="${row.dictCode}" name="status" value="${row.dictValue}"
									   <#if row.dictValue == dict.status>checked</#if> />
								<label for="${row.dictCode}">${row.dictLabel}</label>
							</div>
						</#list>
<#--					</@dictionary>-->
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">备注：</label>
				<div class="col-sm-8">
					<textarea id="remark" name="remark" class="form-control">${dict.remark}</textarea>
				</div>
			</div>
		</form>
	</div>
</div><!--/.container-div-->
<#include "/common/stretch.ftl"/>
	<script type="text/javascript">
		let prefix = "${ctx}/system/dict";

		$("#form-dict-edit").validate({
			rules:{
				dictName:{
					required:true,
				},
				dictType:{
					required:true,
					minlength: 5,
					remote: {
		                url: prefix + "/checkDictTypeUnique",
		                type: "post",
		                dataType: "json",
		                data: {
		                	dictId : function() {
		                        return $("#dictId").val();
		                    },
		                	dictType : function() {
		                		return $.common.trim($("#dictType").val());
		                    }
		                },
		                dataFilter: function(data, type) {
		                	return $.validate.unique(data);
		                }
		            }
				},
			},
			messages: {
		        "dictType": {
		            remote: "该字典类型已经存在"
		        }
		    }
		});

		function submitHandler() {
	        if ($.validate.form()) {
                $.operate.saveModal(prefix + "/edit", $('#form-dict-edit').serialize());
	        }
	    }

        $(function () {
            $.common.initBind();
        })
	</script>
</body>
