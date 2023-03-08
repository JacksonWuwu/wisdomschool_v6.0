<#include "/common/style.ftl"/>
<body class="white-bg">
<div class="container-div clearfix">
    <div class="wrapper wrapper-content animated fadeInRight ibox-content">
        <form class="form-horizontal m" id="form-chapter-add">
            <input id="pid" name="pid" type="hidden" value="${chapter.pid}"/>
            <input name="cid" type="hidden" value="${chapter.cid}"/>
            <div class="form-group">
                <label class="col-sm-3 control-label ">上级目录：</label>
                <div class="col-sm-8">
                    <input class="form-control" type="text" onclick="selectChapterTree()" id="pName" readonly="true"
                           value="${chapter.title}"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label"><i class="text-danger">*</i>章节标题：</label>
                <div class="col-sm-8">
                    <input class="form-control" type="text" name="title" id="title">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label"><i class="text-danger">*</i>章节名称：</label>
                <div class="col-sm-8">
                    <input class="form-control" type="text" name="name" id="name" maxlength="20" onkeyup="javascript:setShowLength1(this, 20, 'cost_tpl_title_length');">
                    <span class="red" id="cost_tpl_title_length">还可以输入20个字数</span>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label">章节说明：</label>
                <div class="col-sm-8">
                    <input id="remark" name="remark" class="form-control" type="text"  maxlength="100" onkeyup="javascript:setShowLength2(this, 100, 'cost_tp2_title_length');">
                    <span class="red" id="cost_tp2_title_length">还可以输入100个字数</span>
                </div>
            </div>
        </form>
    </div>
</div><!--/.container-div-->
<#include "/common/stretch.ftl"/>
<script type="text/javascript">
    let prefix = "/jiaowu/chapter";

    $("#form-menu-add").validate({
        rules: {},
        messages: {}
    });

    /**
     *
     * 表单处理函数
     * */
    function submitHandler() {
        if ($.validate.form()) {
            $.operate.saveModal(prefix + "/add", $('#form-chapter-add').serialize());
        }
    }

    /*选择章节树*/
    function selectChapterTree() {
        let treeId = $("#pid").val();
        let url = prefix + "/chapterTree/${cid}";
        let options = {
            title: '菜单选择',
            width: "380",
            url: url,
            callBack: doSubmit
        };
        $.modal.openOptions(options);
    }

    function doSubmit(index, layero) {
        let body = layer.getChildFrame('body', index);
        let rid = body.find('#rid').val();
        if (rid !== '0') {
            $.modal.alertError("不可选择二级目录！");
            return;
        }
        $("#pid").val(body.find('#pid').val());
        $("#pName").val(body.find('#title').val());
        layer.close(index);
    }

    function setShowLength1(obj, maxlength, id) {
        var rem = maxlength - obj.value.length;
        var wid = id;
        if (rem < 0){
            rem = 0;
        }
        document.getElementById(wid).innerHTML = "还可以输入" + rem + "字数";
    }

    function setShowLength2(obj, maxlength, id) {
        var rem = maxlength - obj.value.length;
        var wid = id;
        if (rem < 0){
            rem = 0;
        }
        document.getElementById(wid).innerHTML = "还可以输入" + rem + "字数";
    }

</script>
</body>
