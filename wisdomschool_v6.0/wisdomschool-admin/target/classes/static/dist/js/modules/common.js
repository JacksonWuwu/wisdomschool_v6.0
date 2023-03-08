define(function (require, exports, module) {
    require('plugins');
    require("typeahead");
    require("ckeditor");
    require("tags");
    require("fileInput");
    require("bootstrapValidator");
    require('authc');

    let initCkEditor = function () {
        CKEDITOR.replace('editor');
    };

    let initFileInput = function () {
        let url = $('input[name=thumbnail]').val();
        $("#multipartFile").fileinput({
            language: 'zh', //设置语言
            initialPreview: [url],
            initialPreviewAsData: true,
            browseClass: "btn btn-default btn-block",
            showCaption: false,
            enctype: 'multipart/form-data',
            allowedFileExtensions: ['jpg', 'png', 'bmp', 'jpeg'],//接收的文件后缀
            showUpload: false, //是否显示上传按钮
            showPreview: true, //展前预览
            maxFileSize: 500, //上传文件最大的尺寸
            maxFilesNum: 1,   //
            dropZoneEnabled: true,//是否显示拖拽区域
            uploadAsync: false,
            showClose: false,
            showRemove: false,
            autoReplace: true,
            dropZoneTitle: "拖拽图片到这里 &hellip;",
            msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！"
        });
    };

    //初始化标签输入
    let tagsInput = function () {
        let tag_input = $('#tags');
        try {
            tag_input.tag({
                placeholder: tag_input.attr('placeholder')
            });
        } catch (e) {
            tag_input.after('<textarea id="' + tag_input.attr('id') + '" name="' + tag_input.attr('name') + '" rows="3">' + tag_input.val() + '</textarea>').remove();
        }
    };

    let initValidator = function () {
        $('#postForm').bootstrapValidator({
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                title: {
                    validators: {
                        notEmpty: {
                            message: '标题不能为空'
                        },
                        stringLength: {
                            max: 100,
                            message: '标题不能超过100个字符'
                        }
                    }
                },
                channelId: {
                    validators: {
                        notEmpty: {
                            message: '请选择栏目'
                        }
                    }
                },
                content: {
                    validators: {
                        notEmpty: {
                            message: '文章不能为空'
                        }
                    }
                }
            }
        }).on('success.form.bv', function (e) {
            let $form = $(e.target);
            if (CKEDITOR.instances.editor.getData() == "") {
                e.preventDefault();
                layer.msg("文章内容不能为空", {icon: 5, time: 1000}, function () {
                    $form.bootstrapValidator('disableSubmitButtons', false);
                });
            }
        });
    };

    exports.init = function () {
        initCkEditor();
        initFileInput();
        tagsInput();
        initValidator();
    };
});