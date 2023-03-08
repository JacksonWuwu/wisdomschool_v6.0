<div class="container-div">
    <div class="btn-group-sm hidden-xs" id="bootstrap-table-resource-toolbar" role="group">
<#--        <@shiro.hasPermission name="teacher:course:view">-->
            <a class="btn btn-success" onclick="$.operate.addFull()">
                <i class="fa fa-plus"></i> 新增
            </a>
<#--        </@shiro.hasPermission>-->
    </div>

    <div class="col-sm-12 select-table table-striped">
        <table id="bootstrap-table-resource-list" data-mobile-responsive="true"></table>
    </div>
</div>
<script type="text/javascript">
    let prefix = "/recourse/source/${id}";

    $(function () {
        let options = {
            url: prefix + "/list",
            createUrl: prefix + "/add",
            updateUrl: prefix + "/edit/{id}",
            removeUrl: prefix + "/remove/{id}",
            content: "bootstrap-table-resource-list",
            toolbar: "bootstrap-table-resource-toolbar",
            modalName: "课程资源",
            columns: [{
                checkbox: false
            },
                {
                    field: 'id',
                    title: '编号',
                    formatter: function (value, row, index) {
                        return index + 1;
                    }
                },

                {
                    field: 'name',
                    title: '资源名称',
                    formatter: function (value, row, index) {
                        // if (row.type == 1) {
                            return "<a onclick='window.open(\"${storage}/downloadFile?fileId=" + row.attrId + " \")'>" + value ;
                        // } else if (row.ext !== undefined && row.ext == 'pdf') {
                        //     return '<a onclick="pdfView(\'' + row.attrId + '\')">' + value + '</a>';
                        // } else {
                        //     return '<a onclick="downLoad(\'' + row.attrId + '\')">' + value + '</a>';
                        // }
                    }
                },
                {
                    field: 'category.name',
                    title: '资源类别'
                },
                {
                    field: 'ext',
                    title: '文件格式'
                },
                {
                    field: 'createTime',
                    title: '创建时间',
                    visible: false
                },
                {
                    field: 'remark',
                    title: '备注'
                },
                {
                    title: '操作',
                    align: 'center',
                    formatter: function (value, row, index) {
                        let actions = [];
                        <#--<@shiro.hasPermission name="teacher:course:edit">
                        actions.push('<a class="btn btn-success btn-xs " href="#" onclick="$.operate.editFull(' + row.id + ')"><i class="fa fa-edit"></i>编辑</a> ');
                        </@shiro.hasPermission>-->
<#--                        <@shiro.hasPermission name="teacher:course:view">-->
                        actions.push('<a class="btn btn-danger btn-xs " href="#" onclick="$.operate.remove(' + row.id + ', $.operate.ajaxSuccess)"><i class="fa fa-remove"></i>删除</a>');
<#--                        </@shiro.hasPermission>-->
                        return actions.join('');
                    }
                }]
        };
        $.table.init(options);
        $.common.initBind();
    });

    function pdfView(filePath) {
        window.open("/recourse/fileresource/review?file=${storage}/resource/handle/" + filePath);
    }

    function downLoad(fileId) {
        $.ajax({
            url: '/recourse/downloadFile/' + fileId,
            type: 'get',
            dataType: 'json',
            success: function (result) {
                if (result.code === 0) {
                    window.location.href = "${storage}/downloadFile?fileId=" + fileId;
                } else {
                    alert("错误：无法下载该文件。");
                }
            },
            error: function () {
                alert("错误：请求失败，请刷新重试。");
            }
        });
    }
</script>
