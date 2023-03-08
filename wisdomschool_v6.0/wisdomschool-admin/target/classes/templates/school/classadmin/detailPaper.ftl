<#include "/common/style.ftl"/>
<div class="container-div">
    <div class="btn-group-sm hidden-xs" id="toolbar" role="group">
        <#--<@shiro.hasPermission name="teacher:course:view">-->
        <#--<a class="btn btn-danger btn-del disabled" onclick="$.operate.removeAll()">-->
        <#--<i class="fa fa-remove"></i> 删除-->
        <#--</a>-->
        <#--</@shiro.hasPermission>-->
    </div>
    <div class="col-sm-12 select-table table-striped">
        <table id="bootstrap-table" data-mobile-responsive="true"></table>
    </div>
</div>
<#include "/common/stretch.ftl"/>
<script type="text/javascript">
    let prefix = "/school/onlineExam/testPaper";
    var id = "${id}";
    $(function () {
        let options = {
            url: "/classadmin/userTestDetail/list/${userId}",
            removeUrl: "/school/onlineExam/userTest/remove",
            modalName: "试卷",
            columns: [
                {
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
                    field: 'testName',
                    title: '试卷名称'
                },
                {
                    field: 'params.stuNum',
                    title: '学号'
                },
                {
                    field: 'params.stuName',
                    title: '姓名'
                },
                {
                    field: 'score',
                    title: '试卷总分'
                },
                {
                    title: '学生得分',
                    formatter: function (value, row, index) {
                        if (row.params.userTest !== null) {
                            return row.params.userTest.stuScore;
                        } else {
                            return "未作答";
                        }
                    }
                },
                {
                    title: '操作',
                    align: 'center',
                    formatter: function (value, row, index) {
                        let actions = [];
                        if (row.params.userTest !== null) {
<#--                            <@shiro.hasPermission name="teacher:course:view">-->
                            actions.push('<a class="btn btn-success btn-xs " href="#" onclick="showStuPaper(\'' + row.id + ',' + row.params.userId + '\')"><i class="fa fa-edit"></i>查看作答</a> ');
<#--                            </@shiro.hasPermission>-->
                        }
                        if (row.params.userTest !== null) {
<#--                            <@shiro.hasPermission name="teacher:course:view">-->
                            actions.push('<a class="btn btn-danger btn-xs " href="#" onclick="deletePaper(\'' + row.params.userTest.id + '\')"><i class="fa fa-edit"></i>删除</a> ');
<#--                            </@shiro.hasPermission>-->
                        }
                        return actions.join('');
                    }
                }]
        };
        $.table.init(options);
        $.common.initBind();
    });

    function showStuPaper(idAndNum) {
        let idAndStuNum = idAndNum.split(",");
        let id=idAndStuNum[0];
        let userId =idAndStuNum[1];
        $.modal.open('学生作业', "/school/onlineExam/testPaper/showStuPaper?id=" + id + "&&userId=" + userId,  saveCallback);

    }

    function deletePaper(id) {
        let data = {
            "ids": id
        };
        $.operate.submit("/school/onlineExam/userTest/remove", "post", "json", data, true, function (result) {
            if (result.code === web_status.SUCCESS) {
                $.modal.alertSuccess("操作成功！");
            } else {
                $.modal.alertError("操作失败:");
            }

        });
    }
    function saveCallback(context) {
        console.log("context:" + 123);
    }
</script>
