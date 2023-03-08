<#include "/common/style.ftl"/>
<div class="container-div">
    <div class="btn-group-sm hidden-xs" id="toolbar" role="group">
<#--        <@shiro.hasPermission name="teacher:course:view">-->
            <a class="btn btn-danger btn-del disabled" onclick="$.operate.removeAll()">
                <i class="fa fa-remove"></i> 删除
            </a>
<#--        </@shiro.hasPermission>-->
    </div>
    <div class="col-sm-12 select-table table-striped">
        <table id="bootstrap-table" data-mobile-responsive="true"></table>
    </div>
</div>
<#include "/common/stretch.ftl"/>
<script type="text/javascript">
    let prefix = "/school/onlineExam/testPaperTwo";
    var id = "${id}";
    console.log("id:"+id);
    $(function () {
        let options = {
            url: prefix + "/detailList/${id}",
            removeUrl: prefix + "/showDetailRemove",
            sortName: "stuScore",
            sortOrder: "desc",
            modalName: "试卷",
            columns: [
                {
                    checkbox: true
                },
                {
                    field: 'id',
                    title: '编号'
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
                    field: 'paperScore',
                    title: '试卷总分'
                },
                {
                    field: 'stuScore',
                    title: '学生得分'
                },
                {
                    title: '操作',
                    align: 'center',
                    formatter: function (value, row, index) {
                        let actions = [];
<#--                        <@shiro.hasPermission name="teacher:course:view">-->
                        actions.push('<a class="btn btn-success btn-xs " href="#" onclick="showStuPaper(\'' + row.pageId +  ','+row.userId +  '\')"><i class="fa fa-edit"></i>查看作答</a> ');
<#--                        </@shiro.hasPermission>-->
                        return actions.join('');
                    }
                }]
        };
        $.table.init(options);
        $.common.initBind();
    });

    function showStuPaper(idAndNum) {
        console.log("id:"+idAndNum);
        let idAndStuNum = idAndNum.split(",");
        let id=idAndStuNum[0];
        let userId =idAndStuNum[1];
        $.modal.open('学生作业', "/school/onlineExam/testPaperTwo/showStuPaper?id=${id}&&userId=" + userId,  saveCallback);

    }
    function saveCallback(context) {
        console.log("context:" + 123);
    }
</script>
