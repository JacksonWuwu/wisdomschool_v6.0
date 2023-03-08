define(function (require, exports, module) {
    let swal = require('swal');

    let path = {
        'loginPath': '/login'
    };

    module.exports = {
        isAuthced: function () {
            return (typeof (window.app.LOGIN_TOKEN.id) != 'undefined' && window.app.LOGIN_TOKEN.id.trim() !== '');
        },
        toLogin: function () {
            location.href = app.base + path.loginPath;
        },
        doPostLogin: function () {
            let username = $('#login_username').val();
            let password = $('#login_password').val();
            $.post(app.base + '/login', {'username': username, 'password': password}, function (result) {
                if (result && result.code == 0) {
                    window.location.reload();
                } else {
                    swal(
                        '登陆信息！',
                        '登陆成功！',
                        'success'
                    )
                }
            })
        }
    };
});