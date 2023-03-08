<div class="container-div container-video-div layout-container-div clearfix">
    <div class="row">
        <div class="btn-group-sm hidden-xs" id="video-toolbar" role="group">
<#--            <@shiro.hasPermission name="teacher:course:view">-->
                <a class="btn btn-success" onclick="$.operate.addFull()">
                    <i class="fa fa-plus"></i> 新增
                </a>
<#--            </@shiro.hasPermission>-->

<#--            <@shiro.hasPermission name="teacher:course:view">-->
                <a class="btn btn-danger btn-del disabled" onclick="$.operate.removeAll()">
                    <i class="fa fa-remove"></i> 删除
                </a>
<#--            </@shiro.hasPermission>-->
        </div>
        <div class="col-sm-12 select-table table-striped">
            <table id="bootstrap-video-table" data-mobile-responsive="true"></table>
        </div>
    </div>
</div><!--/.container-div-->

<script type="text/javascript">
    let prefix = "/recourse/videoresource";

    $(function () {
        let options = {
            url: prefix + "/list/${id}",
            content: "bootstrap-video-table",
            toolbar: "video-toolbar",
            createUrl: prefix + "/add/${id}",
            updateUrl: prefix + "/edit/{id}",
            removeUrl: prefix + "/remove",
            modalName: "视频资源管理",
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
                    title: '视频名称',
                    formatter: function (value, row, index) {
                        return "<a onclick='window.open(\"/recourse/video/" + row.attrId + " \")'>" + value + "</a>";
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
                    title: '观看次数'
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
<#--                        <@shiro.hasPermission name="teacher:course:edit">-->
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
