<#include "/common/style.ftl"/>

<div class="container-div">
    <div class="btn-group-sm hidden-xs" id="toolbar" role="group">
            <a class="btn btn-danger btn-del" onclick="$.operate.removeAll()">
                <i class="fa fa-remove"></i> 删除
            </a>


            <a class="btn btn-warning" onclick="selectNullExamAll()">
                <i class="fa fa-download"></i> 导出空白试卷
            </a>
        <a class="btn btn-primary btn-edit" onclick="selectExamAll()">
            <i class="fa fa-download"></i> 导出作答试卷
        </a>
        <a class="btn btn-export" onclick="exportExamAll()">
            <i class="fa fa-download"></i> 导出已修改试卷
        </a>
<#--        <a class="btn btn-warning" onclick="$.table.exportExcel()">-->
<#--            <i class="fa fa-download"></i> 导出学生成绩-->
<#--        </a>-->
    </div>
        <div class="col-sm-12 select-table table-striped">
            <table id="bootstrap-table" data-mobile-responsive="true"></table>
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
<#include "/common/stretch.ftl"/>
<script type="text/javascript">
    let prefix = "/school/onlineExam/testPaperOne";

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
            // uniqueId: "id",
            // sortName: "stuScore",
            // sortOrder: "desc",
            modalName: "试卷",
            columns: [{
                checkbox: true
            },
                {
                    field: 'id',
                    title: '编号',
                    visible: true,
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
                    field: 'stuEndTime',
                    title: '交卷时间',
                },
                {
                    title: '学生得分',
                    formatter: function (value, row, index) {
                        if (row.sumbitState != '0') {
                            if (row.madeScore=='1'){
                                return row.stuScore;
                            }
                            if (row.stuScore>0&&row.mark=="false")
                            {
                                return " ";
                            }
                            else if(row.stuScore>0)
                            {
                                return row.stuScore;
                            }
                            return "0";
                        } else {
                            return " ";
                        }
                    }
                },
                {
                    title: '状态',
                    formatter: function (value, row, index) {
                        if (row.sumbitState != '0') {
                            if (row.madeScore=='1'){
                                return "已批阅";
                            }
                            else if (row.stuScore>=0&&row.mark=="false"){
                                return "待批阅";
                            }
                            else if(row.stuScore>=0){
                                return "已批阅";
                            }
                            return "待批阅";
                        } else {
                            return "未交卷";
                        }
                    }
                },
                // {
                //     title: '是否已改卷',
                //     formatter: function (value, row, index) {
                //         if (row.madeScore == '1') {
                //             return "是";
                //         } else {
                //             return "否";
                //         }
                //     }
                // },
                {
                    title: '操作',
                    align: 'center',
                    formatter: function (value, row, index) {
                        let actions = [];
                        if (row.sumbitState != '0') {
                          <#--/  <@shiro.hasPermission name="teacher:course:view">-->
                            actions.push('<a class="btn btn-success btn-xs " href="#" onclick="checkPaper(\'' + row.id +  ','+row.stuNum +  '\')"><i class="fa fa-edit"></i>改卷</a> ');
<#--                            </@shiro.hasPermission>-->


                        }
<#--                        <@shiro.hasPermission name="teacher:course:view">-->
                        actions.push('<a class="btn btn-success btn-xs " href="#" onclick="showStuPaper(' + row.id +','+row.stuNum + ')"><i class="fa fa-edit"></i>预览</a> ');
<#--                        </@shiro.hasPermission>-->
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
            $.operate.submitPost(url, data);
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
                };
            });
        });
    }

    $(document).ready(function () {
        //获取老师和老师绑定的课程来获取年级，此处是做测试使用
        $tgIdSelect = $("#tgId");
        $tcIdSelect = $("#tcId");
        $.ajax({
            type: 'POST',
            url: '/school/onlineExam/userExam/getStudents?cId='+${cid}+"&tid="+${tid},
            dataType: 'json',
            success: function (data) {
                for (i = 0; i < data.length; i++) {
                    var tgId = data[i].tgId;
                    var tgName = data[i].tgName;//name="'+titName+'"
                    $tgIdSelect.append('<option value="' + tgId + '" >' + tgName + '</option>');
                }
            }, error: function () {
                console.log("获取数据失败!");
            }
        });

    });
    var token=localStorage.getItem("token")
    function checkPaper(content) {
        var idAndStuNum = content.split(",");
        var id=idAndStuNum[0];
        var stuNum =idAndStuNum[1];
        window.open('/school/onlineExam/testPaperOne/checkStuPaper?id='+id+'&stuNum='+stuNum+"&token="+token,'_blank');
    }
    function exportWord(idAndNum) {
        let idAndStuNum = idAndNum.split(",");
        let id=idAndStuNum[0];
        let userId =idAndStuNum[1];
        $.modal.openFull('导出学生试卷', "/school/onlineExam/testPaperOne/PoiWord?id="+id+"&&stuNum=" + userId+"&&paperId="+${id},  '_blank');
    }
    function showStuPaper(id,userId) {
        // let idAndStuNum = idAndNum.split(",");
        // let id=idAndStuNum[0];
        // let userId =idAndStuNum[1];
        // console.log("userId:"+userId);
        // console.log("id:"+id);
        window.open("/school/onlineExam/testPaperOne/showStuPaper?id="+id+"&stuNum="+userId+"&token="+token,"_blank");
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
        $.operate.submit("/school/onlineExam/testPaperOne/updateStateEdit", "post", "json", data, true, function (result) {
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

