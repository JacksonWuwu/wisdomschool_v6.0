<div class="container-div">
    <div class="col-sm-12 select-table table-striped">
        <table id="bootstrap-table" data-mobile-responsive="true"></table>
    </div>
</div>
<script type="text/javascript">
    let prefix = "/school/onlineExam/testPaperTwo";

    $(function () {
        let options = {
            url: prefix + "/list/${id}",
            removeUrl: prefix + "/remove/{id}",
            updateUrl: prefix + "/edit/{id}",
            sortName: "id",
            sortOrder: "desc",
            modalName: "试卷",
            columns: [{
                checkbox: true
            },
                {
                    field: 'id',
                    title: '编&nbsp;号',
                    align: 'center',
                    sortable: true,
                    formatter(value, row, index) {
                        return index+1;
                    }
                },
                {
                    field: 'name',
                    align: 'center',
                    title: '试卷名'
                },
                {
                    field: 'createTime',
                    align: 'center',
                    title: '开始时间'
                },
                {
                    field: 'overTime',
                    align: 'center',
                    title: '结束时间'
                },
                {
                    field: 'state',
                    title: '状态'
                },
                {
                    title: '操作',
                    align: 'center',
                    formatter: function (value, row, index) {
                        let actions = [];
                        <#--<@shiro.hasPermission name="teacher:course:view">
                        actions.push('<a class="btn btn-success btn-xs " href="#" onclick="showPaper(\'' + row.id + '\')"><i class="fa fa-edit"></i>预览</a> ');
                        </@shiro.hasPermission>-->
<#--                        <@shiro.hasPermission name="teacher:course:view">-->
                        actions.push('<a class="btn btn-success btn-xs " href="#" onclick="detail(\'' + row.id + '\')"><i class="fa fa-edit"></i>详情</a> ');
<#--                        </@shiro.hasPermission>-->
<#--                        <@shiro.hasPermission name="teacher:course:view">-->
                        actions.push('<a class="btn btn-success btn-xs" onclick="$.operate.edit(\'' + row.id + '\')"> <i class="fa fa-edit"></i> 修改 </a>');
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



    function detail(id) {
        $.modal.openFull( '详情', '/school/onlineExam/testPaperTwo/showDetail?id=' + id, saveCallback);
    }

    function saveCallback(context) {
        console.log("context:" + 123);
    }

    function openwindow(id)
    {
        window.open('/school/onlineExam/testPaperTwo/stuToTest?id='+id,'_blank');

    }

    function showPaper(id) {
        window.open('/school/onlineExam/course/showPaper?id='+id,'_blank');
    }

    function analyze(id) {
        var cid = "${id}";
        $.modal.open('作业分析', "/school/onlineExam/testPaperTwo/analyzePaper?id=" + id+"&&cid="+cid, saveCallback);
    }

</script>
