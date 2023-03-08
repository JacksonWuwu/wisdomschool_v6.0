<div class="container-div container-file-div layout-container-div clearfix">
    <div class="row">
        <div class="btn-group-sm hidden-xs" id="file-toolbar" role="group">
<#--            <@shiro.hasPermission name="teacher:course:view">-->
                <a class="btn btn-success" onclick="$.operate.addFull(undefined, refreshTable)">
                    <i class="fa fa-plus"></i> 新增
                </a>
<#--            </@shiro.hasPermission>-->

<#--            <@shiro.hasPermission name="teacher:course:view">-->
                <a class="btn btn-primary btn-edit disabled" onclick="$.operate.edit()">
                    <i class="fa fa-edit"></i> 修改
                </a>
<#--            </@shiro.hasPermission>-->
<#--            <@shiro.hasPermission name="teacher:course:view">-->
                <a class="btn btn-danger btn-del disabled" onclick="$.operate.removeAll()">
                    <i class="fa fa-remove"></i> 删除
                </a>
<#--            </@shiro.hasPermission>-->
        </div>
        <div class="col-sm-12 select-table table-striped">
            <table id="bootstrap-file-table" data-mobile-responsive="true"></table>
        </div>
    </div>
</div><!--/.container-div-->

<script type="text/javascript">
    let prefix = "/recourse/fileresource";
    let allowOfficeExt = {
        'pptx': '', 'ppt': '', 'doc': '', 'docx': '', 'xlsx': '',
        'xls': ''
    };

    function refreshTable() {
        $("#bootstrap-table").bootstrapTable('refresh', {
            url: prefix + "/list/${id}",
            silent: true
        });
    }

    function officeView(filePath) {
        let path = "http://" + window.location.host + "/resource/handle/" + filePath;
        window.open("http://www.xdocin.com/xdoc?_func=to&_format=html&_cache=true&_source=true&_xdoc=" + path);
    }

    function pdfView(filePath) {
        window.open("${storage}/recourse/fileresource/review?file=${storage}/resource/handle/" + filePath);
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

    $(function () {
        let options = {
            url: prefix + "/list/${id}",
            content: "bootstrap-file-table",
            toolbar: "file-toolbar",
            createUrl: prefix + "/add/${id}",
            updateUrl: prefix + "/edit/{id}",
            removeUrl: prefix + "/remove",
            modalName: "课件资源管理",
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
                    field: 'name',
                    title: '课件名称',
                    formatter: function (value, row, index) {
                        if (row.ext === 'pdf') {
                            return '<a onclick="pdfView(\'' + row.attrId + '\')">' + value + '</a>';
                        }
                        return '<a onclick="downLoad(\'' + row.attrId + '\')">' + value + '</a>';
                    }
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
                    field: 'count',
                    title: '引用次数'
                },
                {
                    field: 'count',
                    title: '查看/下载次数'
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
<#--                        <@shiro.hasPermission name="teacher:course:view">-->
                        actions.push('<a class="btn btn-success btn-xs " href="#" onclick="$.operate.editFull(' + row.id + ')"><i class="fa fa-edit"></i>编辑</a> ');
<#--                        </@shiro.hasPermission>-->
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
</script>
