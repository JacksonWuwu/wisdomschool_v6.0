<#include "/common/style.ftl"/>
<link rel="stylesheet" href="/js/plugins/jquery-layout/jquery.layout-latest.css">
<body class="white-bg">
<div class="container-div layout-container-div clearfix">
    <div class="wrapper wrapper-content animated fadeInRight ibox-content">
        <form class="form-horizontal m" id="form-teacher-info">
            <div class="form-group">
                <label class="col-sm-3 control-label">登录账户：</label>
                <div class="col-sm-8">
                    <input type="text" value="${info.loginName}" name="loginName" readonly="true">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label">用户名称：</label>
                <div class="col-sm-8">
                    <input type="text" value="${info.userName}" name="userName">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label">邮箱：</label>
                <div class="col-sm-8">
                    <input type="text" value="${info.email}" name="email">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label">手机：</label>
                <div class="col-sm-8">
                    <input type="text" value="${info.phoneNumber}" name="phoneNumber">
                </div>
            </div>
        </form>
    </div>
</div><!--/.container-div-->
<#include "/common/stretch.ftl"/>
<script type="text/javascript">
    $("#form-teacher-info").validate({
        rules:{
            userName:{
                required:true,
            },
            deptName:{
                required:true,
            },
            email:{
                required:true,
                email:true,
            },
            phoneNumber:{
                required:true,
                isPhone:true,
                remote: {
                    url: ctx + "system/sysUser/checkPhoneUnique",
                    type: "post",
                    dataType: "json",
                    data: {
                        "userId": function() {
                            return $("#userId").val();
                        },
                        "phoneNumber": function() {
                            return $.common.trim($("#phoneNumber").val());
                        }
                    },
                    dataFilter: function (data, type) {
                        return $.validate.unique(data);
                    }
                }
            },
        },
        messages: {
            "email": {
                remote: "Email已经存在"
            },
            "phoneNumber":{
                remote: "手机号码已经存在"
            }
        },
        submitHandler:function(form){
            edit();
        }
    });

    function submitHandler() {
        if ($.validate.form()) {
            edit();
        }
    }

    function edit() {
        var userId = $("input[name='userId']").val();
        var userName = $("input[name='userName']").val();
        var email = $("input[name='email']").val();
        var phoneNumber = $("input[name='phoneNumber']").val();
        var sex = $("#sex option:selected").val();
        $.ajax({
            cache : true,
            type : "POST",
            url : ctx + "/teacher/account/edit",
            data : {
                "userId": userId,
                "userName": userName,
                "email": email,
                "phoneNumber": phoneNumber,
                "sex": sex
            },
            async : false,
            error : function(request) {
                $.modal.alertError("系统错误");
            },
            success : function(data) {
                $.operate.ajaxSuccess;
            }
        });
    }
</script>

</body>