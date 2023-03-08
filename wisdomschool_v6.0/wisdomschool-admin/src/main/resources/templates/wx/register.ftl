<!DOCTYPE html>
<html lang="en">
<head>
    <#--    设定界面大小，取消用户缩放   -->
    <meta charset="UTF-8" name="viewport" content="width=device-width,user-scalable=no">
    <title>注册页面</title>
    <link rel="stylesheet" href="/css/bootstrap.min.css">
</head>
<body>
    <div class="container">
        <h2 class="form-signin-heading">账号绑定</h2>

        <div class="login-content ">
            <form action="" method="post" id="loginForm" class="w-75" align="center">
                <div class="form-group">
                    <input type="text" id="inputLoginName" name="loginName" class="form-control" placeholder="请输入学号">
                </div>
                <div class="form-group">
                    <input type="password" id="inputPassword" name="password" class="form-control" placeholder="请输入密码">
                </div>

                <div>
                    <input type="submit" id="btnSubmit" class="btn-login btn btn-primary btn-block" value="提交">
                </div>
            </form>
        </div>

        <input hidden id="returnUrl" value="${returnUrl}">
        <input hidden id="openId" value="${openId}">

    </div>
</body>

<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/js/plugins/jquery-validation/jquery.validate.min.js"></script>
<script type="text/javascript" src="/js/plugins/jquery-validation/messages_zh.min.js"></script>
<script type="text/javascript" src="/js/plugins/blockUI/jquery.blockUI.min.js"></script>
<script type="text/javascript" src="/js/plugins/layer/layer.js"></script>
<script type="text/javascript" src="/js/plugins/jsencrypt/jsencrypt.min.js"></script>

<script>
    $(function() {
        validateRule();
    });

    $.validator.setDefaults({
        submitHandler: function() {
            login();
        }
    });

    function login() {
        var loginName = $("#inputLoginName").val();
        var password = $("#inputPassword").val();
        var encrypt = new JSEncrypt();// 加密插件对象
        //  加密
        $.ajax({
            type: "post",
            url: "/publicKey",
            async: false,
            dataType: "json",
            success : function (data) {
                encrypt.setPublicKey(data.k);
                var loginInfo = '{username:"' + loginName + '",password:"' + password
                        + '",time:"' + data.t + '",rememberMe:false}';
                // 进行加密
                doLogin(encrypt.encrypt(loginInfo));
            }
        });
    }

    //
    var returnUrl = $("#returnUrl").val() + '?openId=' + $("#openId").val();

    //  登陆请求
    function doLogin(encrypted) {
        $.ajax({
            type: "post",
            url: "/wx/account/register",
            async: false,
            dataType: "json",
            data: {
                "encrypted" : encrypted
            },
            success: function(result) {
                if (1 === result.code) {
                    alert(result.data);
                    window.location.replace(returnUrl);
                } else {
                    alert(result.data);
                }
            }
        });
    }

    function validateRule() {
        var icon = "<i class='fa fa-times-circle'></i> ";
        $("#loginForm").validate({
            rules: {
                username: {
                    required: true
                },
                password: {
                    required: true
                }
            },
            messages: {
                username: {
                    required: icon + "请输入用户名"
                },
                password: {
                    required: icon + "请输入密码"
                }
            }
        })
    }
</script>
</html>