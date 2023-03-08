<#include "/common/style.ftl"/>
<div class="container-div">
    <div class="col-sm-12 select-table table-striped">
        <table id="bootstrap-table" data-mobile-responsive="true"></table>
    </div>
</div>

<#include "/common/stretch.ftl"/>
<script type="text/javascript">
    let prefix = "/teacher/attendanceDetail/${id}";
    $(function () {
        let options = {
            url: prefix + "/list",
            updateUrl: prefix + "/edit/{id}",
            removeUrl: prefix + "/remove",
            modalName: "签到名单",
            showExport: true,
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
                    field: 'loginName',
                    title: '学号'
                },
                {
                    field: 'userName',
                    title: '姓名'
                },
                {
                    field: 'results',
                    title: '状态',
                    formatter: function(value, item, index) {
                        if (item.results == '0') {
                            return '<span class="label label-success">未签到</span>';
                        }
                        else if (item.results == '1') {
                            return '<span class="label label-primary">签到成功</span>';
                        }
                    }
                },
                {
                    title: '操作',
                    align: 'center',
                    formatter: function (value, row, index) {
                        let actions = [];
<#--                        <@shiro.hasPermission name="teacher:course:edit">-->
                        actions.push('<a class="btn btn-success btn-xs " href="#" onclick="$.operate.editFull(' + row.id + ')"><i class="fa fa-edit"></i>编辑</a> ');
<#--                        </@shiro.hasPermission>-->
                        return actions.join('');
                    }
                }]
        };
        $.table.init(options);
        $.common.initBind();
    });


</script>
