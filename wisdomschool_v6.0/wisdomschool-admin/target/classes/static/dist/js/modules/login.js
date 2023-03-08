define(function (require, exports, module) {
    require("layer");
    require("bootstrapValidator");

    $(function () {
        var materialForm;
        materialForm = function () {
            return $('.material-field').focus(function () {
                return $(this).closest('.form-group-material').addClass('focused has-value');
            }).focusout(function () {
                return $(this).closest('.form-group-material').removeClass('focused');
            }).blur(function () {
                if (!this.value) {
                    $(this).closest('.form-group-material').removeClass('has-value');
                }
                return $(this).closest('.form-group-material').removeClass('focused');
            });
        };
        return materialForm();
    });

    $('#login_form').bootstrapValidator({
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            username: {
                validators: {
                    notEmpty: {
                        message: '账号不能为空'
                    }
                }
            },
            password: {
                validators: {
                    notEmpty: {
                        message: '密码不能为空'
                    }
                }
            }
        }
    });

    $('#register_form').bootstrapValidator({
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            username: {
                validators: {
                    notEmpty: {
                        message: '用户名不能为空'
                    },
                    regexp: {
                        regexp: /^[a-zA-Z0-9_]+$/,
                        message: '账号只能包含数字字母下划线'
                    },
                    remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}
                        url: app.base + '/validUN',//验证地址
                        message: '该用户名已存在',//提示消息
                        delay: 1000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
                        type: 'POST'//请求方式
                    }
                }
            },
            email: {
                validators: {
                    notEmpty: {
                        message: '邮箱不能为空'
                    },
                    emailAddress: {
                        message: '请输入正确的邮箱'
                    },
                    remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}
                        url: app.base + '/validEM',//验证地址
                        message: '该邮箱已存在',//提示消息
                        delay: 1000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
                        type: 'POST'//请求方式
                    }
                }
            },
            name: {
                validators: {
                    notEmpty: {
                        message: '昵称不能为空'
                    },
                    stringLength: {
                        min: 3,
                        max: 10,
                        message: '长度必须在3-10之间'
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
            confirmPW: {
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