<#include "/common/style.ftl"/>

<div class="container-div">
        <div class="col-sm-12 select-table table-striped">
            <div style="color: red; font-size: 25px">请复制以下链接给监考老师，默认密码为：123456</div>
            <div style="font-size: 20px">${url}</div>
        </div>
</div><!--/.container-div-->

<script type="text/javascript">
    let prefix = "/school/onlineExam/testPaperWork";

    $(function () {
        queryUserList();
    });

    function queryUserList() {
        let options = {
            url: prefix + "/detailList/${id}",
            removeUrl: prefix + "/showDetailRemove",
            exportNullExamUrl: prefix + "/exportNullExamUrl",
            exportExamAll: prefix + "/exportExamAll",
            exportExamUrl: prefix + "/exportExamUrlTest",
            exportUrl: prefix + "/export?paperOneId=${id}&&courseId=${cid}",
            uniqueId: "id",
            sortName: "stuScore",
            sortOrder: "desc",
            modalName: "试卷",
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
                    title: '学生得分',
                    formatter: function (value, row, index) {
                        if (row.sumbitState == '1') {
                            return row.stuScore;
                        } else {
                            return "未作答";
                        }
                    }
                },
                {
                    title: '是否已交卷',
                    formatter: function (value, row, index) {
                        if (row.sumbitState == '1') {
                            return "是";
                        } else {
                            return "否";
                        }
                    }
                },
                {
                    title: '是否已改卷',
                    formatter: function (value, row, index) {
                        if (row.madeScore == '1') {
                            return "是";
                        } else {
                            return "否";
                        }
                    }
                },
                {
                    title: '操作',
                    align: 'center',
                    formatter: function (value, row, index) {
                        let actions = [];
                        if (row.sumbitState == '1') {
<#--                            <@shiro.hasPermission name="teacher:course:view">-->
                            actions.push('<a class="btn btn-success btn-xs " href="#" onclick="checkPaper(\'' + row.id +  ','+row.stuNum +  '\')"><i class="fa fa-edit"></i>改卷</a> ');
<#--                            </@shiro.hasPermission>-->
                        }
                        if (${PaperRule} == '1') {
<#--                            <@shiro.hasPermission name="teacher:course:view">-->
                            actions.push('<a class="btn btn-success btn-xs " href="#" onclick="showStuPaperRandom(\'' + row.id +  ','+row.stuNum + '\')"><i class="fa fa-edit"></i>预览</a> ');
<#--                            </@shiro.hasPermission>-->
                        }
                        if (${PaperRule} == '0'){
<#--                            <@shiro.hasPermission name="teacher:course:view">-->
                            actions.push('<a class="btn btn-success btn-xs " href="#" onclick="showStuPaper(\'' + row.id +  ','+row.stuNum + '\')"><i class="fa fa-edit"></i>预览</a> ');
<#--                            </@shir/o.hasPermission>-->
                        }
<#--                        <@shiro.hasPermission name="teacher:course:view">-->
<#--                        actions.push('<a class="btn btn-edit btn-xs " href="#" onclick="updateState(\'' + row.id +  ','+row.stuNum + '\')"><i class="fa fa-edit"></i>修改状态</a> ');-->
<#--                        </@shiro.hasPermission>-->
<#--                        <@shiro.hasPermission name="teacher:course:view">-->
<#--                        actions.push('<a class="btn btn-warning btn-xs" href="#" onclick="exportWord(\'' + row.id +  ','+row.stuNum + '\')"><i class="fa fa-download"></i>导出</a> ');-->
<#--                        </@shiro.hasPermission>-->

<#--                        <@shiro.hasPermission name="teacher:course:view">-->
                        actions.push('<a class="btn btn-danger btn-xs " href="#" onclick="deletePaper(\'' + row.id + '\')"><i class="fa fa-edit"></i>删除</a> ');
<#--                        </@shiro.hasPermission>-->
                        return actions.join('');
                    }
                }]
        };
        $.table.init(options);
        $.common.initBind();
    }

</script>
<script type="text/javascript">
    // 批量查询信息
    function selectNullExamAll() {
        let rows = $.common.isEmpty($.table._option.uniqueId) ? $.table.selectFirstColumns() : $.table.selectColumns($.table._option.uniqueId);
        if (rows.length <= 0) {
            $.modal.alertWarning("请至少选择一条记录");
            return;
        }
        $.modal.confirm("确认要导出选中的" + rows.length + "条数据吗?", function () {
            let url = $.table._option.exportNullExamUrl;
            let data = {
                "ids": rows.join(),
                "paperId": ${id}
            };
            $.operate.submit(url, "get", "json", data, true, function (result) {

                if (result.code === web_status.SUCCESS) {
                    $.modal.alertSuccess("操作成功！");
                } else {
                    $.modal.alertError("操作失败:");
                }
        });
    });
    }
    function selectExamAll() {
        let rows = $.common.isEmpty($.table._option.uniqueId) ? $.table.selectFirstColumns() : $.table.selectColumns($.table._option.uniqueId);
        if (rows.length <= 0) {
            $.modal.alertWarning("请至少选择一条记录");
            return;
        }
        $.modal.confirm("确认要导出选中的" + rows.length + "条数据吗?", function () {
            let url = $.table._option.exportExamUrl;
            let data = {
                "ids": rows.join(),
                "paperId": ${id}
            };
            $.operate.submit(url, "get", "json", data, true, function (result) {
                if (result.code === web_status.SUCCESS) {
                    $.modal.alertSuccess("操作成功！");
                } else {
                    $.modal.alertError("操作失败:");
                }
            });
        });
    }
    function exportExamAll() {
        let rows = $.common.isEmpty($.table._option.uniqueId) ? $.table.selectFirstColumns() : $.table.selectColumns($.table._option.uniqueId);
        if (rows.length <= 0) {
            $.modal.alertWarning("请至少选择一条记录");
            return;
        }
        $.modal.confirm("确认要导出选中的" + rows.length + "条数据吗?", function () {
            let url = $.table._option.exportExamAll;
            let data = {
                "ids": rows.join(),
                "paperId": ${id}
            };
            $.operate.submit(url, "get", "json", data, true, function (result) {
                if (result.code === web_status.SUCCESS) {
                    $.modal.alertSuccess("操作成功！");
                } else {
                    $.modal.alertError("操作失败:");
                }
            });
        });
    }
    function getTcoName(tgId) {
        var cid3="${cid}";
        console.log("tgId:"+tgId);
        $.ajax({
            type: 'POST',
            url: '/school/onlineExam/testPaperWork/getTcoName?cId=' + cid3 + "&&tid=" + tid+"&&tgId="+tgId,
            dataType: 'json',
            success: function (data) {
                for (i = 0; i < data.length; i++) {
                    var tcId = data[i].tcId;
                    var tcName = data[i].tcName;//name="'+titName+'"
                    $tcIdSelect.append('<option value="' + tcId + '" >' + tcName + '</option>');
                }

            },
            error: function () {
                alert("获取数据失败!");
            }
        });
    }
    $(document).ready(function () {

        $tgIdSelect = $("#tgId");
        $tcIdSelect = $("#tcId");
        $.ajax({
            type: 'POST',
            url: '/school/onlineExam/userExam/getStudents?cId=' + cId + "&&tid=" + tid,
            dataType: 'json',
            success: function (data) {
                for (i = 0; i < data.length; i++) {
                    var tgId = data[i].tgId;
                    var tgName = data[i].tgName;//name="'+titName+'"
                    $tgIdSelect.append('<option value="' + tgId + '" >' + tgName + '</option>');
                }
            },
            error: function () {
                console.log("获取数据失败!");
            }
        });

    });

    function checkPaper(content) {
        var idAndStuNum = content.split(",");
        var id=idAndStuNum[0];
        var stuNum =idAndStuNum[1];
        window.open('/school/onlineExam/testPaperWork/checkStuPaper?id='+id+'&&stuNum='+stuNum,'_blank');
    }
    function exportWord(idAndNum) {

        let idAndStuNum = idAndNum.split(",");
        let id=idAndStuNum[0];
        let userId =idAndStuNum[1];
        $.modal.openFull('导出学生试卷', "/school/onlineExam/testPaperWork/PoiWord?id="+id+"&&stuNum=" + userId+"&&paperId="+${id},  '_blank');
    }
    function showStuPaperRandom(idAndNum) {
        console.log("id:"+idAndNum);
        let idAndStuNum = idAndNum.split(",");
        let id=idAndStuNum[0];
        let userId =idAndStuNum[1];
        $.modal.openFull('学生考试', "/school/onlineExam/testPaperWork/showStuPaper?id="+id+"&&stuNum=" + userId,  saveCallback);
    }
    function showStuPaper(idAndNum) {
        console.log("id:"+idAndNum);
        let idAndStuNum = idAndNum.split(",");
        let id=idAndStuNum[0];
        let userId =idAndStuNum[1];
        $.modal.openFull('学生考试', "/school/onlineExam/testPaperWork/showStuPaper?id="+id+"&&stuNum=" + userId,  saveCallback);
    }
    function updateState(idAndNum) {
        console.log("id:"+idAndNum);
        let idAndStuNum = idAndNum.split(",");
        let id=idAndStuNum[0];
        let userId =idAndStuNum[1];
        let data={
            "id": id,
            "userId": "userId",
            "testPpaerOneId": ${id}
        }
        $.operate.submit("/school/onlineExam/testPaperWork/updateStateEdit", "post", "json", data, true, function (result) {
            if (result.code === web_status.SUCCESS) {
                $.modal.alertSuccess("状态修改成功，请让该学生继续作答！");
            } else {
                $.modal.alertError("状态修改失败！");
            }

        });
    }
    function deletePaper(id) {
        let data = {
            "ids": id
        };
        $.operate.submit("/school/onlineExam/userExam/remove", "post", "json", data, true, function (result) {
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

