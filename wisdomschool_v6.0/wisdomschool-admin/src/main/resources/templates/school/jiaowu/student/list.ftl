<#include "/common/components/select.ftl"/>
<div class="container-div layout-container-div clearfix">
    <div class="row">
        <div class="col-sm-12 search-collapse">
            <form id="sysUser-form">
                <input type="hidden" id="currentId" name="currentId">
                <input type="hidden" id="currentType" name="currentType">
                <input type="hidden" id="parentId" name="parentId">
                <div class="select-list">
                    <ul>
                        <li>
                            <div class="r">
                                学&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号：<input type="text" name="loginName"/>
                            </div>
                        </li>
                        <li>
                            <div class="r">
                                姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：<input type="text" name="userName"/>
                            </div>
                        </li>

                        <li>
                            <div class="r">
                                年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;级：<input type="text" name="gradesName"/>
                            </div>
                        </li>
                        <li>
                            <div class="r">
                                所属院系：<input type="text" name="departmentName"/>
                            </div>
                        </li>
                        <li>
                            <div class="r">
                                专&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;业：<input type="text" name="majorName"/>
                            </div>
                        </li>
                        <li>
                            <div class="r">
                                班&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;级：<input type="text" name="clbumName"/>
                            </div>
                        </li>
                        <#--li>
                            <div class="r">
                                <div class="col-md-4"><span>用户状态：</span></div>
                                <div class="col-md-8">
                                    <select name="status" class="form-control m-b">
                                        <option value="">所有</option>
                                        <@dictionary dictType="sys_normal_disable">
                                            <#list dictData as row>
                                                <option value="${row.dictValue}">${row.dictLabel}</option>
                                            </#list>
                                        </@dictionary>
                                    </select>
                                </div>
                            </div>
                        </li>
                        <li class="select-time">
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
<#--            <@shiro.hasPermission name="jiaowu:student:add">-->
                <a class="btn btn-success" onclick="$.operate.add()">
                    <i class="fa fa-plus"></i> 新增
                </a>
<#--            </@shiro.hasPermission>-->
<#--            <@shiro.hasPermission name="jiaowu:student:edit">-->
                <a class="btn btn-primary btn-edit disabled" onclick="$.operate.edit()">
                    <i class="fa fa-edit"></i> 修改
                </a>
<#--            </@shiro.hasPermission>-->
<#--            <@shiro.hasPermission name="jiaowu:student:remove">-->
                <a class="btn btn-danger btn-del disabled" onclick="$.operate.removeAll()">
                    <i class="fa fa-remove"></i> 删除
                </a>
<#--            </@shiro.hasPermission>-->
<#--            <@shiro.hasPermission name="jiaowu:student:import">-->
                <a class="btn btn-info" onclick="$.table.importExcel()">
                    <i class="fa fa-upload"></i> 导入
                </a>
<#--            </@shiro.hasPermission>-->
        </div>

        <div class="col-sm-12 select-table table-striped">
            <table id="bootstrap-table" data-mobile-responsive="true"></table>
        </div>
    </div>
</div><!--/.container-div-->
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
    let datas = [];
    <@dictionary dictType="sys_normal_disable">
    <#list dictData as row>
    datas.push({"dictValue": "${row.dictValue}", "listClass": "${row.listClass}", "dictLabel": "${row.dictLabel}"});
    </#list>
    </@dictionary>
    let datasex = [];
    <@dictionary dictType="sys_user_sex">
    <#list dictData as row>
    datasex.push({"dictValue": "${row.dictValue}", "listClass": "${row.listClass}", "dictLabel": "${row.dictLabel}"});
    </#list>
    </@dictionary>
    let prefix = "/jiaowu/student";

    $(function () {
        queryUserList();
    });

    function queryUserList() {
        let options = {
            url: prefix + "/list",
            createUrl: prefix + "/add",
            updateUrl: prefix + "/edit/{id}",
            removeUrl: prefix + "/remove",
            exportUrl: prefix + "/export",
            importUrl: prefix + "/importData",
            importTemplateUrl: prefix + "/importTemplate",
            sortName: "createTime",
            sortOrder: "desc",
            modalName: "用户",
            search: false,
            showExport: true,
            columns: [{
                checkbox: true
            },
                {
                    field: 'id',
                    title: '编号',
                    visible: false,
                    formatter: function (value, row, index) {
                        return index + 1;
                    }
                },
                {
                    field: 'loginName',
                    title: '学号',
                    sortable: true
                },
                {
                    field: 'userName',
                    title: '姓名',
                    sortable: true
                },
                {
                    field: 'sex',
                    title: '性别',
                    align: 'center',
                    sortable: true,
                    formatter: function (value, row, index) {
                        return $.table.selectDictLabel(datasex, value);
                    }
                },
                {
                    field: 'grades.name',
                    title: '年级',
                },
                {
                    field: 'department.name',
                    title: '所属院系',
                },
                {
                    field: 'major.name',
                    title: '专业',
                },
                {
                    field: 'clbum.name',
                    title: '班级',
                },
                {
                    field: 'email',
                    title: '邮箱',
                    visible: false
                },
                {
                    field: 'phoneNumber',
                    title: '手机',
                    visible: false
                },
                {
                    field: 'status',
                    title: '状态',
                    align: 'center',
                    sortable: true,
                    formatter: function (value, row, index) {
                        return $.table.selectDictLabel(datas, value);
                    }
                },
                {
                    field: 'createTime',
                    title: '创建时间',
                    sortable: true,
                    visible: false
                },
                {
                    title: '操作',
                    align: 'center',
                    formatter: function (value, row, index) {
                        let actions = [];
<#--                        <@shiro.hasPermission name="jiaowu:student:edit">-->
                        actions.push('<a class="btn btn-success btn-xs" href="#" onclick="$.operate.edit(\'' + row.id + '\')"><i class="fa fa-edit"></i>编辑</a> ');
<#--                        </@shiro.hasPermission>-->
<#--                        <@shiro.hasPermission name="jiaowu:student:remove">-->
                        actions.push('<a class="btn btn-danger btn-xs" href="#" onclick="$.operate.remove(\'' + row.id + '\', $.operate.ajaxSuccess)"><i class="fa fa-remove"></i>删除</a>');
<#--                        </@shiro.hasPermission>-->
                        <#--<@shiro.hasPermission name="system:sysUser:resetPwd">
                   actions.push('<a class="btn btn-info btn-xs " href="#" onclick="resetPwd(\'' + row.id + '\')"><i class="fa fa-key"></i>重置</a>');
<#--                        </@shiro.hasPermission>&ndash;&gt;-->
                        return actions.join('');
                    }
                }]
        };
        $.table.init(options);
        $.common.initBind();
    }

    /*用户管理-重置密码*/
    function resetPwd(userId) {
        let url = prefix + '/resetPwd/' + userId;
        $.modal.open("重置密码", url, '800', '300');
    }
</script>

