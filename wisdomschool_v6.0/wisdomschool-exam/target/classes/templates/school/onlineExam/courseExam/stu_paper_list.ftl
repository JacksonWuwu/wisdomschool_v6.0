<div class="container-div">

    <div class="col-sm-12 search-collapse">
        <form id="role-form">
            <div class="select-list">
                <ul>
                    <li>
                        学生姓名：<input type="text" name="stuName" id="stuName"/>
                    </li>
                    <!--li>
                        试卷：<select style="width:89px;" name="testPaperId" id="testPaperId">
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
                    </li-->
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
<#--        <@shiro.hasPermission name="teacher:course:view">-->
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
    var tid = "${tid}";
    var cId = "${courseId}";
    var pageId = "${pageId}";
    var clbumId = "${clbumId}";
    let prefix = "/school/onlineExam/courseExam";
    $(function () {
        let options = {
            url: prefix + "/studentPaper/list/${courseId}/${pageId}/${clbumId}",
            createUrl: prefix + "/studentPaper/add/${courseId}",
            updateUrl: prefix + "/studentPaper/edit/{id}",
            removeUrl: prefix + "/studentPaper/remove",
            modalName: "试卷",
            uniqueId: "stuNum",
            columns: [{
                checkbox: true
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
                    field: 'stuScore',
                    title: '成绩'
                },
                {
                    field: 'sumbitState',
                    title: '是否已交卷',
                    formatter: function (value, row, index) {
                        if (row.sumbitState == 1)
                            return "是";
                        else return "否";
                    }
                },
                {
                    field: 'madeScore',
                    title: '是否已改卷',
                    formatter: function (value, row, index) {
                        if (row.madeScore == 1)
                            return "是";
                        else return "否";
                    }
                },
                {
                    title: '操作',
                    align: 'center',
                    formatter: function (value, row, index) {
                        let actions = [];
<#--                        <@shiro.hasPermission name="teacher:course:view">-->
                        if (row.sumbitState == 1)
                            actions.push('<a class="btn btn-success btn-xs " href="#" onclick="openwindow(\'' + row.id +  ','+row.stuNum +  '\')"><i class="fa fa-edit"></i>改卷</a> ');
<#--                        </@shiro.hasPermission>-->

<#--                        <@shiro.hasPermission name="teacher:course:view">-->
                        if (row.sumbitState == 1)
                            actions.push('<a class="btn btn-success btn-xs " href="#" onclick="showPaper(\'' + row.id +  ','+row.stuNum + '\')"><i class="fa fa-edit"></i>预览</a> ');
<#--                        </@shiro.hasPermission>-->

<#--                        <@shiro.hasPermission name="teacher:course:view">-->
                        actions.push('<a class="btn btn-danger btn-xs  " href="#" onclick="removePaper(\'' + row.id +  ','+row.stuNum + '\')"><i class="fa fa-remove"></i>删除</a> ');
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
        var idAndStuNum = idAndNum.split(",");
        var id=idAndStuNum[0];
        var stuNum =idAndStuNum[1];
        window.open('/school/onlineExam/courseExam/makeScore?id=' + id + '&&stuNum=' + stuNum, '_blank');
    }

    function showPaper(content) {
        var idAndStuNum = content.split(",");
        var id=idAndStuNum[0];
        var stuNum =idAndStuNum[1];
        window.open('/school/onlineExam/courseExam/showPaper?id='+id+'&&stuNum='+stuNum,'_blank');
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
            $.operate.submit("/school/onlineExam/courseExam/studentPaper/remove", "post", "json", data, true, function (result) {
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
            url: '/school/onlineExam/userTest/getStudents?cId=' + cId + "&&tid=" + tid,
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
        console.log("tgId:"+tgId);
        $.ajax({
            type: 'POST',
            url: '/school/onlineExam/courseExam/getTcoName?cId=' + cId + "&&tid=" + tid+"&&tgId="+tgId,
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
        $testNameSelect = $("#testPaperId");
        var cid2="${courseId}";
        console.log("123////////");
        $.ajax({
            type: 'POST',
            url: '/school/onlineExam/userTest/getlistExam?cid='+cid2,
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
