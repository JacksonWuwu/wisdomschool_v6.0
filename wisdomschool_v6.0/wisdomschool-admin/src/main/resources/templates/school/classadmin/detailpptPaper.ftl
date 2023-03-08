<#include "/common/style.ftl"/>
<div class="container-div container-file-div layout-container-div clearfix">
    <div class="btn-group-sm hidden-xs" id="toolbar" role="group">
        <#--<@shiro.hasPermission name="teacher:course:view">-->
        <#--<a class="btn btn-danger btn-del disabled" onclick="$.operate.removeAll()">-->
        <#--<i class="fa fa-remove"></i> 删除-->
        <#--</a>-->
        <#--</@shiro.hasPermission>-->
    </div>
    <div class="row">
        <div class="col-sm-12 select-table table-striped">
            <table id="bootstrap-table" data-mobile-responsive="true">

            </table>
        </div>
    </div>
</div><!--/.container-div-->
<#include "/common/stretch.ftl"/>
<script type="text/javascript">
    let prefix = "/classadmin";

    function refreshTable() {
        $("#bootstrap-table").bootstrapTable('refresh', {
            url: prefix + "/PPT/list/${userId}",
            silent: true
        });
    }
    $(function () {
        let options = {
            url: prefix + "/PPT/list/${userId}",
            removeUrl: prefix + "/remove",
            modalName: "用户观看PPT",
            columns: [{
                checkbox: true
            },
                {
                    field: 'id',
                    title: '编号',
                    formatter: function (value, row, index) {
                        return index + 1;
                    }
                },
                {
                    field: 'params.chapterName',
                    title: '章节名称'
                },
                {
                    field: 'params.resourceName',
                    title: '资源名称'
                },
                {
                    field: 'params.studentName',
                    title: '姓名'
                },
                {
                    field: 'params.cout',
                    title: '观看次数'
                }]
                <#--,-->
                <#--{-->
                <#--    title: '操作',-->
                <#--    align: 'center',-->
                <#--    formatter: function (value, row, index) {-->
                <#--        let actions = [];-->
                <#--        if (row.params.videoChapterUser != null) {-->
                <#--            <@shiro.hasPermission name="teacher:course:view">-->
                <#--            actions.push('<a class="btn btn-danger btn-xs " href="#" onclick="deleteInfo(\'' + row.params.videoChapterUser.id + '\')"><i class="fa fa-edit"></i>删除</a> ');-->
                <#--            </@shiro.hasPermission>-->
                <#--        }-->
                <#--        return actions.join('');-->
                <#--    }-->
                <#--}]-->
        };
        $.table.init(options);
        $.common.initBind();
    });


    function deleteInfo(id) {
        let data = {
            "ids": id
        };
        $.operate.submit("/teacher/videoChapterUser/remove", "post", "json", data, true, function (result) {
            if (result.code === web_status.SUCCESS) {
                $.modal.alertSuccess("操作成功！");
            } else {
                $.modal.alertError("操作失败:");
            }

        });
    }
</script>