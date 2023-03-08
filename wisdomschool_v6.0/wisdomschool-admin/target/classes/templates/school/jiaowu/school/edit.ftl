<#include "/common/style.ftl"/>
<body class="white-bg">
<div class="container-div clearfix">
    <div class="wrapper wrapper-content animated fadeInRight ibox-content">
        <form class="form-horizontal m" id="form-major-edit">
            <input id="id" name="id" value="${school.id}" type="hidden">
            <div class="form-group">
                <label class="col-sm-3 control-label"><i class="text-danger">*</i>学校名称：</label>
                <div class="col-sm-8">
                    <input id="name" name="name"  value="${school.name}" class="form-control" type="text">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label">类型：</label>
                <div class="col-sm-8">
                    <input id="type" name="type"   value="${school.type}" class="form-control" type="text">
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-3 control-label">网址：</label>
                <div class="col-sm-8">
                    <input id="website" name="website"  value="${school.website}"  class="form-control" type="text">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label">所在地：</label>
                <div class="col-sm-8">
                    <input id="location" name="location"   value="${school.location}" class="form-control" type="text">
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-3 control-label">性质：</label>
                <div class="col-sm-8">
                    <input id="nature" name="nature"  value="${school.nature}" class="form-control" type="text">
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-3 control-label">隶属：</label>
                <div class="col-sm-8">
                    <input id="affiliation" name="affiliation"  value="${school.affiliation}" class="form-control" type="text">
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-3 control-label "></i> 管理员：</label>
                <div class="col-sm-8">
                    <select id="administrator" name="administrator" class="form-control">
<#--                        <#if (school.administrator=="")><option selected="selected" disabled="disabled"  style='display: none' value=''></option> </#if>-->
                            <option value="">未知</option>
                            <#list sysUsers as sysUser>
                                <option value="${sysUser.id}"
                                  <#if (sysUser.id==school.administrator)>selected</#if> >${sysUser.userName}</option>
                            </#list>
                    </select>
                </div>
            </div>
        </form>
    </div>
</div><!--/.container-div-->
<#include "/common/stretch.ftl"/>
<script type="text/javascript">
    $(function () {
        $.common.initBind();
    });
    let prefix = "/jiaowu/school"
    // $("#form-major-edit").validate({
    //     rules: {
    //         "name": {
    //             required: true,
    //             remote: {
    //                 url: prefix+"/checkSchoolNameUnique",
    //                 type: "post",
    //                 dataType: "json",
    //                 data: {
    //                     "name": function () {
    //                         return $.common.trim($("#name").val());
    //                     }
    //                 }
    //             }
    //         }
    //     },
    //     messages: {
    //         "name": {
    //             remote: "该学校已存在"
    //         }
    //     }
    // });

    function submitHandler() {
        if ($.validate.form()) {
            $.operate.saveModal(prefix + "/edit", $('#form-major-edit').serialize());
        }
    }
</script>
</body>
