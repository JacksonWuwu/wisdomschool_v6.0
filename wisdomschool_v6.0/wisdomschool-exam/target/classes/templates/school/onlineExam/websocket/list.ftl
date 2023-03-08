<#include "/common/components/select.ftl"/>
<div class="container-div">
    <div class="btn-group-sm hidden-xs" id="toolbar" role="group">

<#--        <@shiro.hasPermission name="school:onlineExam:websocket:remove">-->
            <a class="btn btn-danger btn-del disabled" onclick="$.operate.removeAll()">
                <i class="fa fa-remove"></i> 删除
            </a>
<#--        </@shiro.hasPermission>-->
<#--        <@shiro.hasPermission name="school:onlineExam:websocket:export">-->
            <a class="btn btn-info" onclick="$.table.importExcel()">
                <i class="fa fa-upload"></i> 导入
            </a>
      <#--/  </@shiro.hasPermission>-->


    </div>

    <div class="col-sm-12 select-table table-striped">
        <table id="bootstrap-table" data-mobile-responsive="true"></table>
    </div>
</div>
<form id="importForm" enctype="multipart/form-data" class="mt-20 mb-10" style="display: none;">
    <div class="col-xs-offset-1">
        <input type="file" id="file" name="file"/>
        <div class="mt-10 pt-5">
            &nbsp; <a onclick="$.table.exportTemplate()" class="btn btn-default btn-xs">
                <i class="fa fa-file-excel-o"></i> 下载模板</a>
        </div>
        <span class="text-danger pull-left mt-10">
            提示：仅允许导入“xls”或“xlsx”格式文件！
        </span>
    </div>
</form>
<script type="text/javascript" src="/js/onlineExam/js/reconnecting-websocket.min.js"></script>
<script type="text/javascript" src="/js/onlineExam/js/offline.min.js"></script>
<script type="text/javascript">
    let prefix = "/school/onlineExam/websocket";
    $(function () {
        let options = {
            url: prefix + "/list",
            createUrl: prefix + "/add",
            updateUrl: prefix + "/edit/{id}",
            removeUrl: prefix + "/remove",
            importUrl: prefix + "/importData",
            importTemplateUrl: prefix + "/importTemplate",
            modalName: "存放考试的临时",
            columns: [{
                checkbox: true
            }, {
                field: 'id',
                title: '编号'
            },
                {
                    field: 'name',
                    title: '考试名称'
                },
                {
                    field: 'stuNum',
                    title: '考生学号'
                },
                {
                    field: 'createBy',
                    title: '学生姓名'
                },
                {
                    field: 'createTime',
                    title: '开始时间'
                },
                {
                    title: '操作',
                    align: 'center',
                    formatter: function (value, row, index) {
                        let actions = [];
<#--                        <@shiro.hasPermission name="school:onlineExam:websocket:remove">-->
                        actions.push('<a class="btn btn-danger btn-xs " href="#" onclick="$.operate.remove(' + row.id + ', $.operate.ajaxSuccess)"><i class="fa fa-remove"></i>删除</a>');
<#--                        </@shiro.hasPermission>-->
                        return actions.join('');
                    }
                }]
        };
        $.table.init(options);
        $.common.initBind();
    });
</script>

