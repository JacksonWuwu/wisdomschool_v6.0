<#include "/common/style.ftl"/>
<body class="white-bg">
<div class="container-div clearfix">
    <div class="wrapper wrapper-content animated fadeInRight ibox-content">
        <form class="form-horizontal m" id="form-menu-add">
            <input id="tcid" name="tcid" type="hidden" value="${tcid}"/>
            <div class="form-group">
                <label class="col-sm-3 control-label"><i class="text-danger">*</i>签到名称:</label>
                <div class="col-sm-8">
                    <input class="form-control" type="text" name="title" id="title">
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-3 control-label">签到方式:</label>
                <div class="col-sm-8">
                    <label class="radio-box" for="radioM"><input id="radioM" type="radio" name="type" value="0" /> 数字 </label>
                    <label class="radio-box" for="radioC"><input id="radioC" type="radio" name="type" value="1" /> 扫码 </label>
                    <label class="radio-box" for="radioF"><input id="radioF" type="radio" name="type" value="2" /> 点名 </label>
                </div>
            </div>

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


    let prefix = "/teacher/attendance/${tcid}";

    $("#form-menu-add").validate({
        rules: {
            "type": {
                required: true
            },
            "state": {
                required: true
            },
        },
        messages: {
            "type": {
                remote: "该账号已存在"
            },
            "state": {
                remote: "该账号已存在"
            },
        }
    });


    function submitHandler() {
        if ($.validate.form()) {
            $.operate.saveModal(prefix + "/add", $('#form-menu-add').serialize());
        }
    }
    $(function() {
        $.common.initBind();
        $('input').on('ifChecked', function(event){
            let menuType = $(event.target).val();
            if (menuType == "0") {
                $("#password").parents(".form-group").show();
            } else if (menuType == "1") {
                $("#password").parents(".form-group").hide();
            } else if (menuType == "2") {
                $("#password").parents(".form-group").hide();

            }
        });

    });
</script>
</body>
