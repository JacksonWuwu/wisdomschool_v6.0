<#include "/common/style.ftl"/>
<body class="white-bg">
<div class="container-div clearfix">
    <div class="wrapper wrapper-content animated fadeInRight ibox-content">
        <form class="form-horizontal m" id="form-clbum-edit">
            <div class="form-group">
                <label class="col-sm-3 control-label"><i class="text-danger">*</i> 班级：</label>
                <div class="col-sm-8">
                    <select id="cid" class="form-control m-b "
                            name="cid">
                        <#list list as list>
                            <option value="${list.id}">${list.name}</option>
                        </#list>
                    </select>
                </div>
            </div>
        </form>
    </div>
</div><!--/.container-div-->
<#include "/common/stretch.ftl"/>
<script type="text/javascript">
    let prefix = "/teacher/study";
    $(function () {
        $.common.initBind();
    });
    function submitHandler() {
        if ($.validate.form()) {
            $.operate.saveModal(prefix + "/luck", $('#form-clbum-edit').serialize());
        }
    }

</script>
</body>
