<#include "/common/style.ftl"/>
<body class="white-bg">
<div class="container-div clearfix">
    <div class="wrapper wrapper-content animated fadeInRight ibox-content">
        <form class="form-horizontal m" id="form-clbum-edit">
            <div class="form-group">
                <label class="col-sm-3 control-label">是否允许全部视频可快进:</label>
                <div class="col-sm-8">
                    <label class="radio-box"> <input type="radio" name="state" value="0" <#if state =="0" >checked</#if> /> 否</label>
                    <label class="radio-box"> <input type="radio" name="state" value="1" <#if state =="1">checked</#if> /> 是 </label>
                </div>
            </div>
        </form>
    </div>
</div><!--/.container-div-->
<#include "/common/stretch.ftl"/>
<script type="text/javascript">
    let prefix = "/teacher/chapter";
    var tcid="${tcid}"
    $(function () {
        $.common.initBind();
    });
    function submitHandler() {
        if ($.validate.form()) {
            $.operate.saveModal(prefix + "/editAllVideoChapter/"+tcid, $('#form-clbum-edit').serialize());
        }
    }

</script>
</body>
