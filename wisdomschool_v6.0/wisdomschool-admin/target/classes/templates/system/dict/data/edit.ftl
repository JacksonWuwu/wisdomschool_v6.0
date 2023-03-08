<div class="wrapper wrapper-content animated fadeInRight ibox-content">
    <form class="form-horizontal m" id="form-dict-edit">
        <input name="dictCode" type="hidden" value="${dict.dictCode}"/>
        <div class="form-group">
            <label class="col-sm-3 control-label ">字典标签：</label>
            <div class="col-sm-8">
                <input class="form-control" type="text" name="dictLabel" id="dictLabel" value="${dict.dictLabel}"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label ">字典键值：</label>
            <div class="col-sm-8">
                <input class="form-control" type="text" name="dictValue" id="dictValue" value="${dict.dictValue}"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label">字典类型：</label>
            <div class="col-sm-8">
                <input class="form-control" type="text" readonly="true" value="${dict.dictType}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label">样式属性：</label>
            <div class="col-sm-8">
                <input class="form-control" type="text" id="cssClass" name="cssClass" value="${dict.cssClass}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label">字典排序：</label>
            <div class="col-sm-8">
                <input class="form-control" type="text" name="dictSort" value="${dictSort}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label">回显样式：</label>
            <div class="col-sm-8">
                <select name="listClass" class="form-control m-b">
                    <option value="">---请选择---</option>
                    <option value="default" <#if (listClass == default)>selected</#if>>默认</option>
                    <option value="primary" <#if (listClass == primary)>selected</#if>>主要</option>
                    <option value="success" <#if (listClass == success)>selected</#if>>成功</option>
                    <option value="info" <#if (listClass == info)>selected</#if>>信息</option>
                    <option value="warning" <#if (listClass == warning)>selected</#if>>警告</option>
                    <option value="danger" <#if (listClass == danger)>selected</#if>>危险</option>

                </select>
                <span class="help-block m-b-none"><i class="fa fa-info-circle"></i> table表格字典列显示样式属性</span>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label">系统默认：</label>
            <div class="col-sm-8">
<#--                <@dictRadio "sys_yes_no"/>-->
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label">状态：</label>
            <div class="col-sm-8">
<#--                <@dictRadio "sys_normal_disable"/>-->
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label">备注：</label>
            <div class="col-sm-8">
                <textarea id="remark" name="remark" class="form-control">[[*{remark}]]</textarea>
            </div>
        </div>
    </form>
</div>
<script type="text/javascript">
    var prefix = ctx + "system/dict/data";

    $("#form-dict-edit").validate({
        rules: {
            dictLabel: {
                required: true,
            },
            dictValue: {
                required: true,
            },
            dictSort: {
                required: true,
                digits: true
            },
        }
    });

    function submitHandler() {
        if ($.validate.form()) {
            $.operate.saveModal(prefix + "/edit", $('#form-dict-edit').serialize());
        }
    }
</script>
