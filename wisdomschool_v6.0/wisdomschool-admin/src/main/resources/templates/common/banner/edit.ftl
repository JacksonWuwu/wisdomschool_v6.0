<#include "/common/style.ftl"/>
<body class="white-bg">
<div class="container-div clearfix">
    <div class="wrapper wrapper-content animated fadeInRight ibox-content">
        <form class="form-horizontal m" id="form-recourse-add" enctype="multipart/form-data" method="post">

            <input id="id" name="id"
                   value="${banner.id}" type="hidden">
            <input id="cover"
                   value="/storage/showCondensedPicture?fileId=${banner.cover}"
                   type="hidden">

            <div class="form-group">
                <label for="title" class="col-sm-3 control-label">标题：</label>
                <div class="col-sm-6">
                    <input id="title" name="title" class=" form-control" type="text" value="${banner.title}">
                </div>
            </div>

            <div class="form-group">
                <label for="name" class="col-sm-3 control-label">外部链接：</label>
                <div class="col-sm-8">
                    <input id="url" name="url" class=" form-control" type="text" value="${banner.url}">
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-3 control-label">备注：</label>
                <div class="col-sm-8">
                    <input id="remark" name="remark" class="form-control" type="text" value="${banner.remark}">
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-3 control-label" for="multipartFile"> 预览图： </label>

                <div class="col-sm-6">
                    <input id="multipartFile" name="thumbnail" type="file">
                </div>
            </div>
        </form>
    </div>
</div><!--/.container-div-->
<#include "/common/stretch.ftl"/>
<#--<script src="/js/recourse/recourse.js"></script>-->
<script type="text/javascript">
    let prefix = "/jiaowu/banner";

    $(function () {
        let initReviewUrl = $("#cover").val();
        let options = {
            initPreviewUrl: initReviewUrl,
            allowFileExt: ['jpg', 'png', 'bmp', 'jpeg'],
            content: 'multipartFile'
        };
        $.upload.init(options);
        $.common.initBind();
    });

    $("#form-recourse-add").validate({
        rules: {
            name: {
                required: true,
            },
        }
    });

    function submitHandler() {
        if ($.validate.form()) {
            $.operate.submitXHR(prefix + "/edit", '#form-recourse-add', $.operate.ajaxModalSuccess);
        }
    }
</script>
</body>
