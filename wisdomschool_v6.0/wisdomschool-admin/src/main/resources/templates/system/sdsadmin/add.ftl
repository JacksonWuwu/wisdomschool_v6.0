<#include "/common/style.ftl"/>
<body class="white-bg">
<div class="container-div clearfix main-content-inner">
    <div class="wrapper wrapper-content animated fadeInRight ibox-content">
        <form class="form-horizontal m" id="form-student-add">
            <div class="row">
                <div class="col-sm-6">
                    <div class="form-group">
                        <label class="col-sm-4 control-label"><i class="text-danger">*</i> 管理年级：</label>
                        <div class="col-sm-8">
                            <@selectPage id="grades" name='grades' init="true" url=ctx+'/jiaowu/grades/listpage'  />
                        </div>
                    </div>
                </div>

                <div class="col-sm-6">
                    <div class="form-group">
                        <label class="col-sm-4 control-label"><i class="text-danger">*</i> 管理系别：</label>
                        <div class="col-sm-8">
                            <@selectPage id='department' name='department' init="true" url=ctx+'/jiaowu/department/listpage'/>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row" >
                <div  id="div1" class="col-sm-6">
                    <div class="form-group">
                        <label class="col-sm-4 control-label"><i class="text-danger"></i> 管理员：</label>
                        <div class="col-sm-8">
                            <@selectPage id='rid'  name='rid' init="true" url=ctx+'/system/post/listpage' />
                        </div>
                    </div>
                </div>

<#--                <div style="display:none" id="div2" class="col-sm-6">-->
<#--                    <div class="form-group">-->
<#--                        <label class="col-sm-4 control-label"><i class="text-danger"></i> 管理员2：</label>-->
<#--                        <div class="col-sm-8">-->
<#--                            <@selectPage id='rid'  name='rid' init="true" url=ctx+'/system/post/listpage' />-->
<#--                        </div>-->
<#--                    </div>-->
<#--                </div>-->
<#--            </div>-->

        </form>
    </div>
</div><!--/.container-div-->
<#include "/common/stretch.ftl"/>
<script type="text/javascript">
    $(function () {
        $.common.initBind();
    });
    let prefix = "${ctx}/system/sdsadmin";

    $("#form-student-add").validate({
        rules: {
            "grades": {
                required: true
            },
            "department": {
                required: true
            },
            "rid": {
                required: true
            }
        },
        messages: {
            "grades.id": {
                required: "请选择年级"
            },
            "department": {
                required: "请选择系部"
            },
            "rid": {
                required: "请选择管理人员"
            }
        }
    });

    function submitHandler() {
        if ($.validate.form()) {
            $.operate.saveModal(prefix + "/add", $('#form-student-add').serialize());
        }
    };
    // $("#button1").click(function() {
    //     document.getElementById("div1").style.display="block";
    //     document.getElementById("div2").style.display="none";
    // });
    //
    // $("#button2").click(function() {
    //     document.getElementById("div2").style.display="block";
    //     document.getElementById("div1").style.display="none";
    // })

</script>
</body>
<#--<script>-->
<#--    function show1(){-->
<#--        document.getElementById("div1").style.display="block";-->
<#--        document.getElementById("div2").style.display="none";-->
<#--    }-->

<#--    function show2()-->
<#--    {-->
<#--        document.getElementById("div2").style.display="block";-->
<#--        document.getElementById("div1").style.display="none";-->
<#--    }-->
<#--</script>-->
