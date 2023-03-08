<#include "/common/style.ftl"/>
<body class="white-bg">
<div class="container-div clearfix">
	<div class="wrapper wrapper-content animated fadeInRight ibox-content">
		<form class="form-horizontal m" id="form-dict-add">
			<div class="form-group">
				<label class="col-sm-3 control-label ">字典名称：</label>
				<div class="col-sm-8">
					<input class="form-control" type="text" name="dictName" id="dictName"/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">字典类型：</label>
				<div class="col-sm-8">
					<input class="form-control" type="text" name="dictType" id="dictType">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">状态：</label>
				<div class="col-sm-8">
<#--					<@dictRadio "sys_normal_disable"/>-->
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
</div><!--/.container-div-->
<#include "/common/stretch.ftl"/>
	<script type="text/javascript">
        let prefix = "${ctx}/system/dict";

		$("#form-dict-add").validate({
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
		                	name : function() {
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
                $.operate.saveModal(prefix + "/add", $('#form-dict-add').serialize());
	        }
	    }

        $(function () {
            $.common.initBind();
        })

	</script>
</body>
