<#include "/common/components/select.ftl"/>
<div class="container-div">
    <div class="col-sm-12 search-collapse">
        <form id="grades-form">
            <div class="select-list">
                <ul>
                    <li>
                        作业名称：<input type="text" name="adjunctname"/>
                    </li>
                    <#--li class="select-time">
                        <label>创建时间： </label>
                        <input type="text" class="time-input" id="startTime" placeholder="开始时间"
                               name="params[beginTime]"/>
                        <span>-</span>
                        <input type="text" class="time-input" id="endTime" placeholder="结束时间"
                               name="params[endTime]"/>
                    </li-->
                    <li>
                        <a class="btn btn-primary btn-rounded btn-sm" onclick="$.table.search()"><i
                                    class="fa fa-search"></i>&nbsp;搜索</a>
                        <a class="btn btn-warning btn-rounded btn-sm" onclick="$.form.reset()"><i
                                    class="fa fa-refresh"></i>&nbsp;重置</a>
                    </li>
                </ul>
            </div>
        </form>
    </div>

    <div class="btn-group-sm hidden-xs" id="toolbar" role="group">
        <#--        <@shiro.hasPermission name="jiaowu:grades:add">-->
        <a class="btn btn-success" onclick="$.operate.addFull('',$.table.refresh)">
            <i class="fa fa-plus"></i> 新增
        </a>
        <#--        </@shiro.hasPermission>-->
        <#--        <@shiro.hasPermission name="jiaowu:grades:edit">-->
        <a class="btn btn-primary btn-edit disabled" onclick="$.operate.editFull()">
            <i class="fa fa-edit"></i> 修改
        </a>
        <#--        </@shiro.hasPermission>-->
        <#--        <@shiro.hasPermission name="jiaowu:grades:remove">-->
        <a class="btn btn-danger btn-del disabled" onclick="$.operate.removeAll()">
            <i class="fa fa-remove"></i> 删除
        </a>
        <#--        </@shiro.hasPermission>-->
<#--        <@shiro.hasPermission name="jiaowu:grades:export">-->
            <a class="btn btn-warning" onclick="$.table.exportExcel()">
                <i class="fa fa-download"></i> 导出
            </a>
<#--        </@shiro.hasPermission>-->
    </div>

    <div class="col-sm-12 select-table table-striped">
        <table id="bootstrap-table" data-mobile-responsive="true"></table>
    </div>
</div>
<script type="text/javascript">
    let prefix = "/teacher/adjunct/${tcid}";

    $(function () {
        let options = {
            url: prefix + "/list",
            createUrl: prefix + "/add",
            updateUrl: prefix + "/edit/{id}",
            removeUrl: prefix + "/remove",
            exportUrl: prefix + "/export",
            modalName: "年级",
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
                    field: 'adjunctname',
                    title: '作业名称'
                },
                {
                    field: 'filename',
                    title: '上机文件名称'
                },
                {
                    field: 'clbumName',
                    title: '班级'
                },
                {
                    field: 'deadline',
                    title: '截止时间',
                    sortable: true
                },
                // {
                //     field: 'states',
                //     title: '发布',
                //     align:'center',
                //     frozen: true ,
                //     editable : true,
                //     formatter: function(value, item, index) {
                //         if (item.states ==1 ) {
                //             // return '<a class="btn btn-warning btn-xs" href="#" onclick="release(\'' + item.id + '\',\'' + item.cid + '\')"><i class="fa fa-pause"></i>发布</a> ';
                //             return '<span class="">已发布</span>';
                //         } else {
                //             return '<a class="btn btn-warning btn-xs" href="#" onclick="fabub(\'' + item.id + '\')"><i class="fa fa-pause"></i>发布</a>';
                //         }
                //     }
                // },
                {
                    title: '操作',
                    align: 'center',
                    formatter: function (value, row, index) {
                        let actions = [];
                        <#--                        <@shiro.hasPermission name="jiaowu:grades:edit">-->
                        <#--                        </@shiro.hasPermission>-->
                        <#--                        <@shiro.hasPermission name="jiaowu:grades:remove">-->
                        // actions.push('<a class="btn btn-danger btn-xs " href="#" onclick="$.operate.remove(' + row.id + ', $.operate.ajaxSuccess)"><i class="fa fa-remove"></i>删除</a>');
                        <#--                        </@shiro.hasPermission>-->
                        actions.push('<a class="btn btn-warning btn-xs" href="#" onclick="fabub(\'' + row.id + '\')"><i class="fa fa-pause"></i>发布</a>&nbsp;&nbsp;&nbsp;');
                        actions.push('<a class="btn btn-success btn-xs " href="#" onclick="$.operate.edit(' + row.id + ')"><i class="fa fa-edit"></i>编辑</a>');
                        return actions.join('');
                    }
                }]
        };
        $.table.init(options);
        $.common.initBind();
    });

    function saveCallback() {
        console.log("");
    }
    /*调度任务-启用*/
    function fabub(id) {
        var adjid = "${id}";
        // var id = $.common.isEmpty($.table._option.uniqueId) ? $.table.selectFirstColumns() : $.table.selectColumns($.table._option.uniqueId);
        $.modal.open('发布', "/teacher/adjunct/fabu?id=" + id+"&&adjid="+adjid,saveCallback);
    }


    function detailFull(id) {
        var adjid = "${id}";
        $.modal.openFull('详细', prefix+"/detail/"+id, saveCallback);
    }

    function buEnd(id){
        $.post("/teacher/adjunct/buend",{
            "id":id,
        },function (data) {
            $.modal.alertSuccess(data.msg);
        },"json")

    }

</script>
