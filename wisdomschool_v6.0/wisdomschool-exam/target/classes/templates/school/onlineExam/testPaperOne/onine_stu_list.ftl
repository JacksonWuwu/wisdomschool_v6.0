<#include "/common/components/select.ftl"/>
<div class="container-div">

    <div class="col-sm-12 search-collapse">
        <form id="role-form">
            <div class="select-list">
                <ul>
                    <li>
                        学生姓名：<input type="text" name="stuName" id="stuName"/>
                    </li>
                    <li>
                        试卷：<select style="width:89px;" name="testPaperOneId" id="testPaperOneId">
                            <option value="">所有</option>
                        </select>
                    </li>
                    <li>
                        年级：<select style="width:89px;" name="tgId" id="tgId"
                                        onchange="getTcoName(this.value)">
                            <option value="">所有</option>
                        </select>
                    </li>
                    <li>
                        班级：<select style="width:89px;" name="tcId" id="tcId">
                            <option value="">所有</option>
                        </select>
                    </li>
                    <li>
                        <a class="btn btn-primary btn-rounded btn-sm" onclick="$.table.search()"><i
                                    class="fa fa-search"></i>&nbsp;搜索</a>
                        <a class="btn btn-warning btn-rounded btn-sm" onclick="$.form.reset()"><i
                                    class="fa fa-refresh"></i> &nbsp;重置</a>
                    </li>
                </ul>
            </div>
        </form>
    </div>

    <div class="btn-group-sm hidden-xs" id="toolbar" role="group">
        <a class="btn btn-warning" onclick="$.table.exportExcel()">
            <i class="fa fa-download"></i> 导出
        </a>
        <a class="btn btn-danger btn-del" onclick="$.operate.removeAll()">
            <i class="fa fa-remove"></i> 删除
        </a>

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
    var tid = "${tid}";
    var cId = "${cid}";
    let prefix = "/school/onlineExam/testPaperOne";
    $(function () {
        let options = {
            url: prefix + "/onlineStudent/list/${cid}",
            createUrl: prefix + "/studentPaper/add/${cid}",
            updateUrl: prefix + "/studentPaper/edit/{id}",
            removeUrl: prefix + "/showDetailRemove",
            exportUrl: prefix + "/export",
            modalName: "试卷",
            uniqueId: "id",
            uniqueIdplus:"stuNum",
            removeUrlplus:prefix+ "/studentPaper/removeAll",
            columns: [{
                checkbox: true
            },
                {
                    field: 'id',
                    title: '编号',
                    formatter(value, row, index) {
                        return index+1;
                    }
                },
                {
                    field: 'tgName',
                    title: '年级'
                },
                {
                    field: 'tcName',
                    title: '班级'
                },
                {
                    field: 'stuNum',
                    title: '学号'
                },
                {
                    field: 'sysName',
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
                    field: 'sumbitState',
                    title: '是否已交卷'
                },
                {
                    field: 'browser',
                    title: '浏览器'
                },
                {
                    field: 'loginLocation',
                    title: '登录地点'
                },
                {
                    field: 'status',
                    title: '登录状态',
                    align: 'center',
                    formatter: function(value, row, index) {
                        if (value == 'ON_LINE') {
                            return '<span class="badge badge-primary">在线</span>';
                        } else if (value == 'OFF_LINE') {
                            return '<span class="badge badge-danger">离线</span>';
                        }
                    }
                },
                {
                    field: 'startTimestamp',
                    title: '开考时间'
                }
                <#--{-->
                <#--    title: '操作',-->
                <#--    align: 'center',-->
                <#--    formatter: function (value, row, index) {-->
                <#--        let actions = [];-->
                <#--        <@shiro.hasPermission name="teacher:course:view">-->
                <#--        actions.push('<a class="btn btn-success btn-xs " href="#" onclick="checkPaper(\'' + row.id +  ','+row.stuNum +  '\')"><i class="fa fa-edit"></i>改卷</a> ');-->
                <#--        </@shiro.hasPermission>-->

                <#--        <@shiro.hasPermission name="teacher:course:view">-->
                <#--        actions.push('<a class="btn btn-success btn-xs " href="#" onclick="showPaper(\'' + row.id +  ','+row.stuNum + '\')"><i class="fa fa-edit"></i>预览</a> ');-->
                <#--        </@shiro.hasPermission>-->

                <#--        <@shiro.hasPermission name="teacher:course:view">-->
                <#--        actions.push('<a class="btn btn-danger btn-xs  " href="#" onclick="removePaper(\'' + row.id +  ','+row.stuNum + '\')"><i class="fa fa-remove"></i>删除</a> ');-->
                <#--        </@shiro.hasPermission>-->
                <#--        return actions.join('');-->
                <#--    }-->
                <#--}-->
                ]
        };
        $.table.init(options);
        $.common.initBind();
    });

    function saveCallback(context) {
        ;
    }
    function showStuPaper(idAndNum) {
        console.log("id:"+idAndNum);
        let idAndStuNum = idAndNum.split(",");
        let id=idAndStuNum[0];
        let userId =idAndStuNum[1];
        $.modal.open('学生考试', "/school/onlineExam/testPaperOne/showStuPaper?id=${id}&&userId=" + userId,  saveCallback);

    }
    function openwindow(idAndNum) {
        var idAndStuNum = idAndNum.split(",");
        var id=idAndStuNum[0];
        var stuNum =idAndStuNum[1];
        window.open('/school/onlineExam/testPaperOne/makeScore?id=' + id + '&&stuNum=' + stuNum, '_blank');
    }

    function checkPaper(content) {
        var idAndStuNum = content.split(",");
        var id=idAndStuNum[0];
        var stuNum =idAndStuNum[1];
        window.open('/school/onlineExam/testPaperOne/checkStuPaper?id='+id+'&&stuNum='+stuNum,'_blank');

    }
    function showPaper(content) {
        var idAndStuNum = content.split(",");
        var id=idAndStuNum[0];
        var stuNum =idAndStuNum[1];
        window.open('/school/onlineExam/testPaperOne/showStuPaper?id='+id+'&&stuNum='+stuNum,'_blank');
    }
    function removePaper(con){
        var idAndStuNum = con.split(",");
        var id=idAndStuNum[0];
        var stuNum =idAndStuNum[1];
        $.modal.confirm("确认要删除?", function () {
            let data = {
                "id": id,
                "stuNum":stuNum
            };
            $.operate.submit("/school/onlineExam/testPaperOne/studentPaper/remove", "post", "json", data, true, function (result) {
                if (result.code === web_status.SUCCESS) {
                    $.modal.alertSuccess("操作成功！");
                } else {
                    $.modal.alertError("操作失败:");
                }

            });

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

    function getTcoName(tgId) {
        var cid3="${cid}";
        console.log("tgId:"+tgId);
        $.ajax({
            type: 'POST',
            url: '/school/onlineExam/testPaperOne/getTcoName?cId=' + cid3 + "&&tid=" + tid+"&&tgId="+tgId,
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

    (function () {
        $testNameSelect = $("#testPaperOneId");
        var cid2="${cid}";
        console.log("123////////");
        $.ajax({
            type: 'POST',
            url: '/school/onlineExam/userExam/getlistExam?cid='+cid2,
            dataType: 'json',
            success: function (data) {
                for (var i = 0; i < data.length; i++) {
                    console.log(data[i].testName);
                    var tid = data[i].id;
                    var tname = data[i].testName;
                    $testNameSelect.append('<option value="' + tid + '" >' + tname + '</option>');
                }
            },
            error: function () {
                console.log("获取数据失败!");
            }
        });

    })();
</script>
