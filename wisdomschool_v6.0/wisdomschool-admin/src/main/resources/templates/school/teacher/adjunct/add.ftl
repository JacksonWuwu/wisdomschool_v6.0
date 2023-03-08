
<#include "/common/style.ftl"/>
<body class="white-bg" >
<div class="container-div clearfix">
    <div style="width: 1500px;height: 1px"></div>
    <div class="wrapper wrapper-content animated fadeInRight ibox-content">
        <form class="form-horizontal m" id="form-recourse-add">
            <input type="hidden" value="${tcid}" name="tcId">
            <input type="hidden" id="attrId" name="attrId">
            <input type="hidden" id="attrType" name="ext">
            <div class="form-group">
                <label for="attrFile" class="col-sm-3 control-label"><i class="text-danger">*</i>资源选择：</label>
                <div class="col-sm-6">
                    <input id="attrFile" name="fileName" readonly="readonly" class=" form-control" type="text" placeholder="点击选择所需上传资源即可">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label"><i class="text-danger">*</i>作业名称:</label>
                <div class="col-sm-8">
                    <input id="adjunctname" name="adjunctname" class="form-control" type="text">
                </div>
            </div>
            <div class="form-group">
                <label for="" class="col-sm-3 control-label"><i class="text-danger">*</i>资源名称：</label>
                <div class="col-sm-6">
                    <input id="fname" name="name" class=" form-control" type="text">
                </div>
            </div>
            <#--<div class="form-group">-->
                <#--<label for="attrFile" class="col-sm-3 control-label"><i class="text-danger">*</i>资源类型：</label>-->
                <#--<div class="col-sm-6">-->
                    <#--<select name="category.id" class="form-control m-b">-->
                        <#--<#list type as t>-->
                            <#--<option value="${t.id}">${t.name}</option>-->
                        <#--</#list>-->
                    <#--</select>-->
                <#--</div>-->
            <#--</div>-->
            <div class="form-group">
                <label for="attrFile" class="col-sm-3 control-label">备注：</label>
                <div class="col-sm-6">
                    <input id="remark" name="remark" class=" form-control" type="text">
                </div>
            </div>
        </form>
    </div>
</div><!--/.container-div-->
<#include "/common/stretch.ftl"/>
<#--<script src="/js/recourse/recourse.js"></script>-->
<script src="/js/borain-timeChoice.js"></script>
<script type="text/javascript">
    //便于检索，采用对象形式存储
    let allowFileExt = {'mp3': '', 'mp4': '', 'mov': '', 'flv': '', 'webm': ''};
    onLoadTimeChoiceDemo();
    borainTimeChoice({
        start:".start-two",
        end:"",
        level:"H",
        less:false
    });

    function checkType(type) {
        return allowFileExt[type] !== undefined;
    }

    $(function () {
        /*let options = {
            allowFileExt: ['mp4', 'flv', 'webm', 'mov'],
            content: 'file',
            maxFileSize: 2048
        };
        $.upload.init(options);*/

        $("#attrFile").on("click", function (e) {

            let height = ($(window).height() - 20) + 'px';
            layer.open({
                type: 2,
                area: ['1000px', height],
                fix: false,
                //不固定
                maxmin: true,
                shade: 0.8,
                title: "资源管理器",
                content: "/recourse/storageManager",
                btn: ['关闭'],
                // 弹层外区域关闭
                shadeClose: true,
                zIndex: layer.zIndex,
                success: function (layero) {
                    layer.setTop(layero);
                },
                cancel: function (index) {
                    return true;
                },
                end: function () {
                    $("#adjunctname").val($("#fname").val());
                }
            });
        });
        $.common.initBind();
    });
    let prefix = "/teacher/adjunct";
    $("#form-recourse-add").validate({
        rules: {
            xxxx: {
                required: true,
            },
        }
    });


    function submitHandler() {
        if ($.validate.form()) {
            $.operate.saveModal(prefix + "/add", $('#form-recourse-add').serialize());
        }
    }
</script>
</body>
