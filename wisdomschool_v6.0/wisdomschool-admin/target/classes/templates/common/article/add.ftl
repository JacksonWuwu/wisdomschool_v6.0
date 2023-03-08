<#include "/common/style.ftl"/>
<div class="container-div clearfix">
    <div class="wrapper wrapper-content animated fadeInRight ibox-content">
        <form class="form-horizontal m"
              id="form-outline-edit" method="post">
            <div class="content-list-item common-shadow wow fadeInUp">
                <div class="content">
                    <div class="form-group">
                        <label for="title" class="col-sm-3 control-label">标题：</label>
                        <div class="col-sm-6">
                            <input id="title" name="title" class=" form-control" type="text">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="title" class="col-sm-3 control-label">标签：</label>
                        <div class="col-sm-6">
                            <input id="tags" name="tags" class=" form-control" type="text">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-3 control-label">备注：</label>
                        <div class="col-sm-8">
                            <input id="remark" name="remark" class="form-control" type="text">
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="">
                            <textarea name="content" id="editor"
                                      rows="40"></textarea>
                        </div>
                    </div>
                    <div class="hr hr-18 dotted hr-double"></div>

                </div>
            </div>
        </form>
    </div>
</div><!--/.container-div-->
<#include "/common/stretch.ftl"/>
<script type="text/javascript" src="/js/plugins/ckeditor/ckeditor.js"></script>
<script type="text/javascript">
    let prefix = "${ctx}/jiaowu/article";
    $("#form-outline-edit").validate({
        rules: {
            name: {
                required: true,
            },
        }
    });

    $(function () {
        ckeditor1 = CKEDITOR.replace('editor', {
            height: 800,
            toolbar: [
                {name: 'document', items: ['Save', 'NewPage', 'DocProps', 'Preview', 'Print', '-', 'Templates']},
                {name: 'clipboard', items: ['Cut', 'Copy', 'Paste', 'PasteText', 'PasteFromWord', '-', 'Undo', 'Redo']},
                {name: 'editing', items: ['Find', 'Replace', '-', 'SelectAll', '-', 'SpellChecker', 'Scayt']},
                {
                    name: 'forms',
                    items: ['Form', 'Checkbox', 'Radio', 'TextField', 'Textarea', 'Select', 'Button', 'ImageButton',
                        'HiddenField']
                },
                {
                    name: 'basicstyles',
                    items: ['Bold', 'Italic', 'Underline', 'Strike', 'Subscript', 'Superscript', '-', 'RemoveFormat']
                },
                {
                    name: 'paragraph',
                    items: ['NumberedList', 'BulletedList', '-', 'Outdent', 'Indent', '-', 'Blockquote', 'CreateDiv',
                        '-', 'JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock', '-', 'BidiLtr', 'BidiRtl']
                },
                {name: 'links', items: ['Link', 'Unlink', 'Anchor']},
                {
                    name: 'insert',
                    items: ['Image', 'Flash', 'Table', 'HorizontalRule', 'Smiley', 'SpecialChar', 'PageBreak', 'Iframe']
                },
                {name: 'styles', items: ['Styles', 'Format', 'Font', 'FontSize']},
                {name: 'colors', items: ['TextColor', 'BGColor']},
                {name: 'tools', items: ['Maximize', 'ShowBlocks']}]

        });
    });

    function submitHandler() {
        submitArticleForm()
    }

    function submitArticleForm() {
        let content = CKEDITOR.instances.editor.document.getBody().getText();
        if (content === undefined || content.trim() === '') {
            $.modal.msgError("文章内容不能为空");
            return false;
        }

        if ($.validate.form()) {
            $.operate.postJson(prefix + "/add", {
                content: CKEDITOR.instances.editor.getData(),
                title: $("#title").val(),
                tags: $("#tags").val(),
                remark: $("#remark").val()
            }, true, function (result) {
                if (result.code === web_status.SUCCESS) {
                    $.modal.msgSuccess(result.msg);
                }
            }, null);
        }
    }


</script>