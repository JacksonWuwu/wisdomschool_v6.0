<div class="container-div">
    <div class="row">
        <div class="col-sm-12 search-collapse">
            <form id="clbumCourse-form">
                <div class="select-list">
                    <ul>
                        <li>
                            <div class="r">
                                年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;级：<input type="text" name="grades.name"/>
                            </div>
                        </li>
                        <li>
                            <div class="r">
                                系&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;部：<input type="text" name="department.name"/>
                            </div>
                        </li>
                        <li>
                            <div class="r">
                                专&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;业：<input type="text" name="major.name"/>
                            </div>
                        </li>
                        <li>
                            <div class="r">
                                班&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;级：<input type="text" name="clbum.name"/>
                            </div>
                        </li>
                        <#--li>
                            <div class="r">
                            授课老师：<input type="text" name="sysUser.userName"/>
                            </div>
                        </li>
                        <li>
                            <div class="r">
                            课&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;程：<input type="text" name="course.name"/>
                            </div>
                        </li>
                        <<li class="select-time">
                            <label>创建时间： </label>
                            <input type="text" class="time-input" id="startTime" placeholder="开始时间"
                                   name="params[beginTime]"/>
                            <span>-</span>
                            <input type="text" class="time-input" id="endTime" placeholder="结束时间"
                                   name="params[endTime]"/>
                        </li-->
                        <li>
                            <a class="btn btn-primary btn-rounded btn-sm" onclick="$.table.search()">
                                <i lass="fa fa-search"></i>&nbsp;搜索</a>
                            <a class="btn btn-warning btn-rounded btn-sm" onclick="$.form.reset()">
                                <i class="fa fa-refresh"></i>&nbsp;重置</a>
                        </li>
                    </ul>
                </div>
            </form>
        </div>
    </div>
    <div class="btn-group-sm hidden-xs" id="toolbar" role="group">
<#--        <@shiro.hasPermission name="jiaowu:clbumCourse:add">-->
            <a class="btn btn-success" onclick="$.operate.add()">
                <i class="fa fa-plus"></i> 新增
            </a>
<#--        </@shiro.hasPermission>-->

<#--        <@shiro.hasPermission name="jiaowu:clbumCourse:edit">-->
            <a class="btn btn-primary btn-edit disabled" onclick="$.operate.edit()">
                <i class="fa fa-edit"></i> 修改
            </a>
<#--        </@shiro.hasPermission>-->
<#--        <@shiro.hasPermission name="jiaowu:clbumCourse:remove">-->
            <a class="btn btn-danger btn-del disabled" onclick="$.operate.removeAll()">
                <i class="fa fa-remove"></i> 删除
            </a>
<#--        </@shiro.hasPermission>-->
<#--        <@shiro.hasPermission name="jiaowu:course:import">-->
            <a class="btn btn-info" onclick="$.table.importExcel()">
                <i class="fa fa-upload"></i> 导入
            </a>
<#--        </@shiro.hasPermission>-->


    </div>

    <div class="col-sm-12 select-table table-striped">
        <table id="bootstrap-table" data-mobile-responsive="true"></table>
    </div>
</div>
<form id="importForm" enctype="multipart/form-data" class="mt-20 mb-10" style="display: none;">
    <div class="col-xs-offset-1">
        <input type="file" id="file" name="file"/>
        <div class="mt-10 pt-5">
            <input type="checkbox" id="updateSupport" name="updateSupport" title="如果登录账户已经存在，更新这条数据。"> 是否更新已经存在的用户数据
            &nbsp; <a onclick="$.table.exportTemplate()" class="btn btn-default btn-xs"><i
                        class="fa fa-file-excel-o"></i> 下载模板</a>
        </div>
        <span class="text-danger pull-left mt-10">
            提示：仅允许导入“xls”或“xlsx”格式文件！
        </span>
    </div>
</form>
<script type="text/javascript">
    let prefix = "/jiaowu/clbumcourse";

    $(function () {
        let options = {
            url: prefix + "/list",
            createUrl: prefix + "/add",
            updateUrl: prefix + "/edit/{id}",
            removeUrl: prefix + "/remove",
            importUrl: prefix + "/importData",
            importTemplateUrl: prefix + "/importTemplate",
            search: false,
            modalName: "班级课程",
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
                    field: 'department.name',
                    title: '系部',
                    sortable: true
                },
                {
                    field: 'major.name',
                    title: '专业',
                    sortable: true
                },
                {
                    field: 'gradesname',
                    title: '年级',
                    sortable: true
                },
                {
                    field: 'gname',
                    title: '年级',
                    sortable: true
                },
                {
                    field: 'clbum.name',
                    title: '班级',
                    sortable: true
                },
                {
                    field: 'sysUser.userName',
                    title: '授课教师',
                },
                {
                    field: 'course.name',
                    title: '课程',
                },
                {
                    title: '操作',
                    align: 'center',
                    formatter: function (value, row, index) {
                        let actions = [];
<#--                        <@shiro.hasPermission name="jiaowu:clbumCourse:edit">-->
                        actions.push('<a class="btn btn-success btn-xs " href="#" onclick="$.operate.edit(' + row.id + ')"><i class="fa fa-edit"></i>编辑</a> ');
<#--                        </@shiro.hasPermission>-->
<#--                        <@shiro.hasPermission name="jiaowu:clbumCourse:remove">-->
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
