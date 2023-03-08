define(function (require, exports, module) {
    require("layer");
    require("bootstrapValidator");

    $('#password-form').bootstrapValidator({
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            oldPassword: {
                validators: {
                    notEmpty: {
                        message: '当前密码不能为空'
                    }
                }
            },
            password: {
                validators: {
                    notEmpty: {
                        message: '密码不能为空'
                    },
                    stringLength: {
                        min: 6,
                        max: 30,
                        message: '长度必须在6-30之间'
                    },
                    regexp: {
                        regexp: /((?=.*[a-zA-Z])(?=.*\d)|(?=[a-zA-Z])(?=.*[#@!~%^&*$()])|(?=.*\d)(?=.*[#@!~%^&*$()]))[a-zA-Z\d#@!~%^&*$()]{6,30}$/,
                        message: '密码至少包含字母、数字和特殊字符中两种'
                    }
                }
            },
            confirmPassword: {
                validators: {
                    notEmpty: {
                        message: '确认密码不能为空'
                    },
                    identical: {
                        field: 'password',
                        message: '两次输入的密码不一致'
                    }
                }
            }
        }
    });
});