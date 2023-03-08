<div class="container-div">
    <div class="col-sm-12 search-collapse">
        <form id="chapter-form">
            <div class="select-list">
                <ul>
                    <li>
                        标题：<input type="text" name="title"/>
                    </li>
                    <li>
                        名称：<input type="text" name="name"/>
                    </li>
                    <li class="select-time">
                        <label>创建时间： </label>
                        <input type="text" class="time-input" id="startTime" placeholder="开始时间"
                               name="params[beginTime]"/>
                        <span>-</span>
                        <input type="text" class="time-input" id="endTime" placeholder="结束时间"
                               name="params[endTime]"/>
                    </li>
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
<#--        <@shiro.hasPermission name="module:chapter:view">-->
            <a class="btn btn-success" onclick="chapter(${id})">
                <i class="fa fa-plus"></i> 新增
            </a>
<#--        </@shiro.hasPermission>-->

    </div>

    <div class="col-sm-12 select-table table-striped">
        <table id="bootstrap-table" data-mobile-responsive="true"></table>
    </div>
</div>
<script type="text/javascript">
    let prefix = "/jiaowu/chapter";
    $(function() {
        let options = {
            code: "id",
            parentCode: "pid",
            uniqueId: "id",
            expandAll: false,
            expandFirst: true,
            url: prefix + "/list/${id}",
            createUrl: prefix + "/add",
            updateUrl: prefix + "/edit/{id}",
            removeUrl: prefix + "/remove",
            modalName: "课程章节",
            columns: [{
                field: 'selectItem',
                radio: true
            },

                {
                    title: '标题',
                    field: 'title',
                    width: '10%',
                    align: "left"
                },
                {
                    title: '名称',
                    field: 'name',
                    align: "left"
                },
                {
                    title: '操作',
                    width: '50%',
                    align: 'left',
                    formatter: function (value, row, index) {
                        let actions = [];
<#--                        <@shiro.hasPermission name="module:chapter:view">-->
                        actions.push('<a class="btn btn-success btn-xs " href="#" onclick="$.operate.edit(' + row.id + ')"><i class="fa fa-edit"></i>编辑</a> ');
<#--                        </@shiro.hasPermission>-->
<#--                        <@shiro.hasPermission name="module:chapter:view">-->
                        actions.push('<a class="btn btn-danger btn-xs " href="#" onclick="remove(' + row.id + ')"><i class="fa fa-remove"></i>删除</a>');
<#--                        </@shiro.hasPermission>-->
                        return actions.join('');
                    }
                }]
        };
        $.treeTable.init(options);
        $.common.initBind();
    });
    // 添加章节信息
    function chapter(cid) {
        let url = prefix + '/add/' + cid;
        $.modal.open("添加章节", url, saveCallback);
    }

    function saveCallback(context) {
        $.modal.alertSuccess("操作成功！");
        $.treeTable.refresh();
    }
    //  删除章节
    function remove(id) {
        $.modal.confirm("删除章节会影响该门课所有教师的章节，请谨慎删除",  function() {
            $.operate.remove(id, function () {
                $.treeTable.refresh();
            })
        })
    }
</script>
