${r'<#include "/common/style.ftl"/>'}
<body class="white-bg">
<div class="container-div clearfix">
    <div class="wrapper wrapper-content animated fadeInRight ibox-content">
        <form class="form-horizontal m" id="form-${classname}-add">
            <#list columns as column>
                <#if column.columnName != primaryKey.columnName>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">${column.columnComment}：</label>
                        <div class="col-sm-8">
                            <input id="${column.attrname}" name="${column.attrname}" class="form-control" type="text">
                        </div>
                    </div>
                </#if>
            </#list>
        </form>
    </div>
</div><!--/.container-div-->
${r'<#include "/common/stretch.ftl"/>'}
<script type="text/javascript">
    $(function () {
        $.common.initBind();
    });
    let prefix = "${r'${ctx}'}/${moduleName}/${classname}"
    $("#form-${classname}-add").validate({
        rules: {
            xxxx: {
                required: true,
            },
        }
    });

    function submitHandler() {
        if ($.validate.form()) {
            $.operate.saveModal(prefix + "/add", $('#form-${classname}-add').serialize());
        }
    }
</script>
</body>
