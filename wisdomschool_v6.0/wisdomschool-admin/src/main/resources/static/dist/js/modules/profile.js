define(function (require, exports, module) {
    require('bootstrapValidator');
    require('datePicker');
    require("plugins");
    require("jcrop");
    require("layer");

    var initValidator = function () {
        $('#user-profile-form').bootstrapValidator({
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                name: {
                    validators: {
                        notEmpty: {
                            message: '昵称不能为空'
                        },
                        stringLength: {
                            max: 30,
                            message: '昵称不能超过30个字符'
                        }
                    }
                },
                email: {
                    validators: {
                        notEmpty: {
                            message: '邮箱不能为空'
                        },
                        email: {
                            message: '请输入正确的邮箱'
                        }
                    }
                },
                address: {
                    validators: {
                        stringLength: {
                            max: 100,
                            message: '地址不能超过100个字符'
                        }
                    }
                },
                signature: {
                    validators: {
                        stringLength: {
                            max: 100,
                            message: '个性签名不能超过100个字符'
                        }
                    }
                }
            }
        });
        $('#avatar-form').bootstrapValidator({
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            }
        }).on('success.form.bv', function (e) {
            // Prevent form submission

            e.preventDefault();
            // Get the form instance
            var $form = $(e.target);
            if ($('#path').val() === undefined || $('#path').val() === '') {
                layer.msg("请选择图片", {icon: 5, time: 1000}, function () {
                    $form.bootstrapValidator('disableSubmitButtons', false);
                });
                return;
            } else {
                $.ajax({
                    type: "post",
                    url: app.base + "/user/avatar/update",
                    data: $('#avatar-form').serialize(),
                    dataType: 'json',
                    success: function (result) {
                        if (result.code >= 0) {
                            if (!!result.message) {
                                layer.msg(result.message, {icon: 6, time: 1000}, function () {
                                    var wp = window.parent;
                                    var submitPath = app.base + '/user/profile';
                                    if (wp != null) {
                                        while (wp.parent && wp.parent !== wp) {
                                            wp = wp.parent;
                                        }
                                        wp.location.href = submitPath;
                                    } else {
                                        window.location.href = submitPath;
                                    }
                                });
                            }
                        } else {
                            layer.msg(result.message, {icon: 5})
                        }
                    }
                });
            }
        });
    };

    function initModal() {
        $('#modal-profile').on('show.bs.modal', function () {
            initDate();
            var modal = $(this);
            modal.find('input[name=username]').val($('[data-username]').data('username'));
            modal.find('input[name=name]').val($('[data-name]').data('name'));
            modal.find('input[name=email]').val($('[data-email]').data('email'));
            if ($('[data-gender]').data('gender') == 0) {
                modal.find('input[name=gender]')[0].checked = true;
            } else {
                modal.find('input[name=gender]')[1].attr('checked')[1].checked = true;
            }
            modal.find('input[name=birthday]').val($('[data-birthday]').data('birthday'));
            modal.find('input[name=address]').val($('[data-address]').data('address'));
            modal.find('[name=signature]').html($('[data-signature]').data('signature'));

        })
    }

    function initDate() {
        $('#birthday').datetimepicker({
            minView: 'month',
            language: 'zh-CN',
            format: "yyyy-mm-dd",
            autoclose: true,
            todayBtn: true,
            minuteStep: 10
        });
    }

    //返回顶部
    var toTop = function () {
        var $window = $(window);
        var $scrollTopLink = $('a.scroll-to-top');
        $window.scroll(function () {
            if ($(this).scrollTop() > 100) {
                $scrollTopLink.fadeIn();
            } else {
                $scrollTopLink.fadeOut();
            }
        });
        $scrollTopLink.on('click', function (e) {
            $('html, body').animate({scrollTop: 0}, 400);
            return false;
        })
    };

    var jscrop;
    var init = false;
    var baseUploadUrl = app.base + '/user/avatar/upload';
    var baseUrl = app.base;

    function getRandom() {
        var bounds = jscrop.getBounds();
        return [
            Math.round(Math.random() * bounds[0]),
            Math.round(Math.random() * bounds[1]),
            Math.round(Math.random() * bounds[0]),
            Math.round(Math.random() * bounds[1])
        ];
    }

    function saveScrop(s) {
        $('#x').val(s.x);
        $('#y').val(s.y);
        $('#width').val(s.w);
        $('#height').val(s.h);
    }

    function initJscrop() {
        $('#target').Jcrop({
            boxWidth: 240,
            aspectRatio: 100 / 100,
            onChange: saveScrop,
            onSelect: saveScrop,
            allowSelect: false
        }, function () {
            jscrop = this;
            jscrop.animateTo([100, 100, 300, 300]);
        })
    }

    function initAvatarUpload() {
        $('#btn-select').change(function () {
            var oUrl = $('#path').val();
            var uploadUrl = oUrl == undefined ? baseUploadUrl : baseUploadUrl + '?o=' + oUrl;
            $(this).upload(uploadUrl, function (result) {
                if (result.code >= 0) {
                    var path = baseUrl + result.data.path;
                    $('#target').attr('src', path);
                    $('#path').val(result.data.path);

                    if (!init) {
                        initJscrop();
                        init = true;
                    } else {
                        jscrop.setImage(path, function () {
                            this.animateTo(getRandom());
                        })
                    }
                } else {
                    layer.msg(result.message, {icon: 5});
                }
            })
        });
    }

    exports.init = function () {
        toTop();
        initValidator();
        initModal();
        initAvatarUpload();
    }
})