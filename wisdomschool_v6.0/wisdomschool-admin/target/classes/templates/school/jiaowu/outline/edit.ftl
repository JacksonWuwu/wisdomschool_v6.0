<div class="container-div clearfix">
    <div class="wrapper wrapper-content animated fadeInRight ibox-content">
        <form class="form-horizontal m"
              id="form-outline-edit" method="post">
            <div class="content-list-item common-shadow wow fadeInUp">
                <div class="content">
                    <!-- #section:elements.form -->
                    <input type="hidden" id="tcId" name="tcId" value="${tcId}">
                    <div class="form-group">
                        <div class="">
                            <textarea name="content" id="editor"
                                      rows="40">${outline.content}</textarea>
                        </div>
                    </div>
                    <div class="hr hr-18 dotted hr-double"></div>
                    <div class="clearfix">
                        <div class="col-md-offset-4 col-md-8">
                            <a class="btn btn-sm btn-default" href="javascript:void(0);" onclick="submitOutlineForm()">
                                <i class="ace-icon fa fa-check"></i>
                                保存
                            </a>
                            &nbsp; &nbsp; &nbsp;
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div><!--/.container-div-->
<script type="text/javascript" src="/js/plugins/ckeditor/ckeditor.js"></script>
<script type="text/javascript">
    let prefix = "/teacher/outline";
    $("#fform-outline-edit").validate({
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

    function submitOutlineForm() {
        let content = CKEDITOR.instances.editor.document.getBody().getText();
        if (content === undefined || content.trim() === '') {
            $.modal.msgError("大纲内容不能为空");
            return false;
        }

        if ($.validate.form()) {
            $.operate.postJson(prefix + "/save/${tcId}", {
                tcId: $("#tcId").val(),
                content: CKEDITOR.instances.editor.getData()
            }, true, function (result) {
                if (result.code === web_status.SUCCESS) {
                    $.modal.msgSuccess(result.msg);
                }
            }, null);
        }
    }


</script>
