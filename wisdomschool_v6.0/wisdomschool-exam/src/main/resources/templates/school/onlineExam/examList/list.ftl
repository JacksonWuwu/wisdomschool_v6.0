<div class="container-div">
    <div class="btn-group-sm hidden-xs" id="toolbar" role="group">
    </div>
    <div class="btn-group-sm hidden-xs" id="toolbar" role="group">
<#--        <@shiro.hasPermission name="school:onlineExam:course:view">-->
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
    let prefix = "/school/onlineExam/examList";

    $(function () {
        let options = {
            url: prefix + "/list/${cid}",
            createUrl: prefix + "/add/${cid}",
            updateUrl: prefix + "/edit/{id}",
            removeUrl: prefix + "/remove",
            modalName: "试卷",
            uniqueId: "stuNum",
            columns: [{
                checkbox: true
            },
                {
                    field: 'id',
                    title: '试卷编号',
                    formatter(value, row, index) {
                        return index+1;
                    }
                },
                {
                    field: 'stuNum',
                    title: '学号'
                },
                {
                    field: 'stuName',
                    title: '姓名'
                },
                {
                    field: 'headLine',
                    title: '标题'
                },
                {
                    field: 'testName',
                    title: '试卷名'
                },
                {
                    field: 'stuScore',
                    title: '成绩'
                },

                {
                    field: 'madeScore',
                    title: '是否已改卷'
                },
                {
                    title: '操作',
                    align: 'center',
                    formatter: function (value, row, index) {
                        let actions = [];
<#--                        <@shiro.hasPermission name="school:onlineExam:course:view">-->
                        actions.push('<a class="btn btn-success btn-xs " href="#" onclick="openwindow(\'' + row.id +  ','+row.stuNum +  '\')"><i class="fa fa-edit"></i>改卷</a> ');
<#--                        </@shiro.hasPermission>-->
                        return actions.join('');
                    }
                }]
        };
        $.table.init(options);
        $.common.initBind();
    });


    function saveCallback(context) {
        console.log("context:" + 123);
    }

    function openwindow(idAndNum) {
        console.log("id:"+idAndNum);
        let idAndStuNum = idAndNum.split(",");
        let id=idAndStuNum[0];
        let stuNum =idAndStuNum[1];
        window.open('/school/onlineExam/examList/makeScore?id=' + id + '&&stuNum=' + stuNum, '_blank');
    }




</script>
