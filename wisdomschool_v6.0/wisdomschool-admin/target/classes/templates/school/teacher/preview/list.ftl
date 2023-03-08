<style type="text/css">
    .table {table-layout:fixed;}
</style>
<div class="container-div">
    <div class="col-sm-12 search-collapse">
        <form id="grades-form">
            <div class="select-list">
                <ul>
                    <li>
                        名称：<input type="text" name="previewname"/>
                    </li>
<#--                    <li>-->
<#--                        班&nbsp;级：<select style="width:60px;" name="cid" id="cid">-->
<#--                            <option value="">所有</option>-->
<#--                        </select>-->
<#--                    </li>-->
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
        <a class="btn btn-success" onclick="$.operate.addFull()">
            <i class="fa fa-plus"></i> 新增
        </a>
        <#--        </@shiro.hasPermission>-->
        <#--        <@shiro.hasPermission name="jiaowu:grades:edit">-->
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
    let prefix = "/teacher/preview/${tcid}";

    $(function () {
        let options = {
            url: prefix + "/list",
            createUrl: prefix + "/add",
            updateUrl: prefix + "/edit/{id}",
            removeUrl: prefix + "/remove",
            exportUrl: prefix + "/export",
            modalName: "年级",
            columns: [
                {
                    field: 'id',
                    title: '编号',
                    formatter: function (value, row, index) {
                        return index + 1;
                    }
                },
                {
                    field: 'previewname',
                    title: '预习名称'
                },
                {
                    field: 'clbumName',

                    title: '班级'
                },
                {
                    field: 'chapterName',
                    title: '预习章节',
                    visible: true,
                    align: 'center',
                    valign: 'middle',
                    width:200,
                    formatter: function (value, row, index) {
                        return "<span style='display: block;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;' title='" + value + "'>" + value + "</span>";
                    }
                },
                {
                    field: 'state',
                    title: '发布',
                    sortable: true,
                    align:'center',
                    frozen: true ,
                    editable : true,

                    formatter: function(value, item, index) {
                        if (item.state==1) {
                            // return '<a class="btn btn-warning btn-xs" href="#" onclick="release(\'' + item.id + '\',\'' + item.cid + '\')"><i class="fa fa-pause"></i>发布</a> ';
                            return '<span class="label label-primary">已发布</span>';
                        } else{
                            return '<a class="btn btn-warning btn-xs" href="#" onclick="fabub(\'' + item.id + '\')"><i class="fa fa-pause"></i>发布</a> ';
                        }
                    }
                },
                {
                    title: '操作',
                    align: 'center',
                    formatter: function (value, row, index) {
                        let actions = [];
                        <#--                        <@shiro.hasPermission name="jiaowu:grades:edit">-->
                        // actions.push('<a class="btn btn-success btn-xs " href="#" onclick="$.operate.edit(' + row.id + ')"><i class="fa fa-edit"></i>编辑</a> ');
                        <#--                        </@shiro.hasPermission>-->
                        <#--                        <@shiro.hasPermission name="jiaowu:grades:remove">-->
                        actions.push('<a class="btn btn-danger btn-xs " href="#" onclick="$.operate.remove(' + row.id + ', $.operate.ajaxSuccess)"><i class="fa fa-remove"></i>删除</a>');
                        <#--                        </@shiro.hasPermission>-->
                        // actions.push('<a class="btn btn-info btn-xs" href="#" onclick="detail(\'' + row.id + '\')"><i class="fa fa-play"></i>详情</a>');
                        return actions.join('');
                    }
                }]
        };
        $.table.init(options);
        $.common.initBind();
    });

    /*调度任务-启用*/
    function fabub(id) {

        // var id = $.common.isEmpty($.table._option.uniqueId) ? $.table.selectFirstColumns() : $.table.selectColumns($.table._option.uniqueId);
        $.modal.open('发布', "/teacher/preview/fabu?id=" + id, saveCallback);
    }

    function detail(id) {
        // var id = $.common.isEmpty($.table._option.uniqueId) ? $.table.selectFirstColumns() : $.table.selectColumns($.table._option.uniqueId);
        $.modal.openFull('预习详情', "/teacher/preview/detail?id=" + id);
    }


    var adjid = "${id}";
    $(function () {
        $.ajax({
            type: 'POST',
            url: '/teacher/preview/findClbum?pid=' + adjid,
            dataType: 'json',
            success: function (data) {
                for (i = 0; i < data.length; i++) {
                    var cid = data[i].id;
                    var clbumname = data[i].name;//name="'+titName+'"
                    $cidSelect.append('<option value="' + cid + '">' + clbumname + '</option>');
                }
            },
            error: function () {
                alert("获取题型数据失败!");
            }

        });
    })
</script>
