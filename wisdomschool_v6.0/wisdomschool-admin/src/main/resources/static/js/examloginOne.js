$(function() {
    validateRule();
    $('.imgCode').on('click', function() {
        let t = $(this).attr('t');
        let url = "captcha/captchaImage?t=" + t + "&s=" + Math.random();
        $(".imgCode").attr("src", url);
    });
});

$.validator.setDefaults({
    submitHandler: function() {
        login();
    }
});

function login() {
    $.modal.loading($("#btnSubmit").data("loading"));
    let username = $.common.trim($("input[name='username']").val());
    let password = $.common.trim($("input[name='password']").val());
    let validateCode = $("input[name='validateCode']").val();
    $.post("/publicKey", function (data) {
        let loginInfo = '{username:"' + username + '",password:"'
            + password + '",time:"' + data.t + '",rememberMe:false}';
        let encrypt = new JSEncrypt();// 加密插件对象bmf
        encrypt.setPublicKey(data.k);
        let encrypted = encrypt.encrypt(loginInfo);// 进行加密
        doLogin(encrypted, validateCode);
    }, "json");
}

function doLogin(encrypted, validateCode) {
    $.ajax({
        type: "post",
        url: "/student/login",
        dataType: "json",
        data: {
            "encrypted": encrypted,
            "validateCode": validateCode,
            "testPaperOneId":testPaperOneId
        },
        success: function(r) {
            if (r.code === 0) {
                location.href = "/exam?tcid="+tcidOne+"&&cid="+cidOne+"&&testPaperOneId="+testPaperOneId+"";
            } else {
                $.modal.closeLoading();
                $.modal.msg(r.msg, modal_status.FAIL);
            }
        }
    });
}

function validateRule() {
    let icon = "<i class='fa fa-times-circle'></i> ";
    $("#loginForm").validate({
        rules: {
            username: {
                required: true
            },
            password: {
                required: true
            },
            validateCode: {
                required: true
            }
        },
        messages: {
            username: {
                required: icon + "请输入用户名"
            },
            password: {
                required: icon + "请输入密码"
            },
            validateCode: {
                required: icon + "请输入验证码"
            }
        }
    })
}