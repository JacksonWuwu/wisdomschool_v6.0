<#include "/common/style.ftl"/>
<body class="white-bg">
<div class="container-div clearfix">
    <div class="wrapper wrapper-content animated fadeInRight ibox-content">
        <form class="form-horizontal m" id="form-clbum-add">
            <div class="form-group">
                <label class="col-sm-3 control-label"><i class="text-danger">*</i> 积分规则编码：</label>
                <div class="col-sm-8">
                    <input type="text" name="code">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label"><i class="text-danger">*</i> 积分规则名称：</label>
                <div class="col-sm-8">
                    <input type="text" name="name">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label"><i class="text-danger">*</i> 序号：</label>
                <div class="col-sm-8">
                    <input type="text" name="orderNum">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label" for="rewardType"><i class="text-danger">*</i> 类型：</label>
                <div class="col-sm-8">
                    <label class="radio-inline">
                        <input type="radio" name="rewardType" id="rewardType" value="0"> 奖励
                    </label>
                    <label class="radio-inline">
                        <input type="radio" name="rewardType" value="1"> 惩罚
                    </label>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label"><i class="text-danger">*</i> 奖励周期：</label>
                <div class="col-sm-8">
                    <select name="cycleType" onchange="cycleTypeChange(this)" class="form-control m-b">
                        <option value="0"> 一次性</option>
                        <option value="1"> 每天一次</option>
                        <option value="2"> 按间隔时间</option>
                        <option value="3"> 不限制</option>
                    </select>
                </div>
            </div>
            <div class="form-group" id="cycleTimeCtr" style="display: none;">
                <label class="col-sm-3 control-label" id="cycleTime"><i class="text-danger">*</i> 间隔时间(分)：</label>
                <div class="col-sm-8">
                    <input type="number" name="cycleTime">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label"><i class="text-danger">*</i> 最多奖励次数：</label>
                <div class="col-sm-8">
                    <input type="number" name="rewardNum">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label"><i class="text-danger">*</i> 积分值：</label>
                <div class="col-sm-8">
                    <input type="number" name="credit">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label">备注：</label>
                <div class="col-sm-8">
                    <input id="remark" name="remark" class="form-control" type="text">
                </div>
            </div>
        </form>
    </div>
</div><!--/.container-div-->
<#include "/common/stretch.ftl"/>
<script type="text/javascript">
    $(function () {
        $.common.initBind();
    });
    let prefix = "/jiaowu/integral";
    $("#form-clbum-add").validate({
        rules: {
            "name": {
                /*required: true,
                remote: {
                    url: prefix+"/checkClbumNameUnique",
                    type: "post",
                    dataType: "json",
                    data: {
                        "name": function () {
                            return $.common.trim($("#name").val());
                        },
                        "major.id": function () {
                            return $.common.trim($("#major").val());
                        }
                    }
                }*/
            }
        },
        messages: {
            "name": {
                remote: "该积分已存在"
            }
        }
    });

    function submitHandler() {
        if ($.validate.form()) {
            $.operate.saveModal(prefix + "/add", $('#form-clbum-add').serialize());
        }
    }

    function cycleTypeChange(type) {
        console.log(type.value === '2');
        if ('2' === type.value) {
            $("#cycleTimeCtr").fadeIn("slow");
        } else {
            $("#cycleTimeCtr").fadeOut("slow");
        }
    }
</script>
</body>
