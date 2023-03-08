<#include "/common/style.ftl"/>
<body class="white-bg">
<div class="container-div clearfix">
    <div class="wrapper wrapper-content animated fadeInRight ibox-content">
        <form class="form-horizontal m" id="form-clbum-edit">
            <input id="id" name="id"
                   value="${chapter.id}" type="hidden">
            <div class="form-group">
                <label class="col-sm-3 control-label">是否允许学生快进:</label>
                <div class="col-sm-8">
                    <label class="radio-box"> <input type="radio" name="state" value="0" <#if chapter.state == "0">checked</#if> /> 否 </label>
                    <label class="radio-box"> <input type="radio" name="state" value="1" <#if chapter.state == "1">checked</#if> /> 是 </label>
                </div>
            </div>
        </form>
    </div>
</div><!--/.container-div-->
<#include "/common/stretch.ftl"/>
<script type="text/javascript">
    let prefix = "/teacher/chapter";
    $(function () {
        $.common.initBind();
    });
    function submitHandler() {
        if ($.validate.form()) {
            $.operate.saveModal(prefix + "/editVideoChapter", $('#form-clbum-edit').serialize());
        }
    }

</script>
</body>
