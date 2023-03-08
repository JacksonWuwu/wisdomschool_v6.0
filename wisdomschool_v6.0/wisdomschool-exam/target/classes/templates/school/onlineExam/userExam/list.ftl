<div class="container-div">
    <div class="col-sm-12 search-collapse">
        <form id="role-form">
            <div class="select-list">
                <ul>
                    <li>
                        学生姓名：<input type="text" name="sysName" id="sysName"/>
                    </li>
                    <li>
                        作业名：<select style="width:89px;" name="testPaperId" id="testPaperId">
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
<script type="text/javascript">
    var tid = "${tid}";
    var cId = "${cid}";
    let prefix = "/school/onlineExam/userExam";
    $(function () {
        let options = {
            url: prefix + "/list/${cid}",
            createUrl: prefix + "/add/${cid}",
            updateUrl: prefix + "/edit/{id}",
            removeUrl: prefix + "/remove",
            modalName: "测试关联",
            columns: [{
                checkbox: true
            },
                {
                    field: 'id',
                    title: '作业编号'
                },
                {
                    field: 'headLine',
                    title: '作业标题'
                },
                {
                    field: 'testName',
                    title: '作业名称'
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
                    field: 'sysName',
                    title: '学生名称'
                },
                {
                    field: 'stuStartTime',
                    title: '开始时间'
                },
                {
                    field: 'stuEndTime',
                    title: '结束时间'
                },
                {
                    field: 'sumbitState',
                    title: '提交状态'
                },
                {
                    field: 'sumscore',
                    title: '总分'
                },
                {
                    title: '操作',
                    align: 'center',
                    formatter: function (value, row, index) {
                        let actions = [];
<#--                        <@shiro.hasPermission name="teacher:course:view">-->
                        actions.push('<a class="btn btn-success btn-xs " href="#" onclick="$.operate.edit(' + row.id + ')"><i class="fa fa-edit"></i>编辑</a> ');
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


    $(document).ready(function () {
        $tgIdSelect = $("#tgId");
        $tcIdSelect = $("#tcId");
        $.ajax({
            type: 'POST',
            url: '/school/onlineExam/userExam/getStudents?cId=' + cId + "&&tid=" + tid,
            dataType: 'json',
            success: function (data) {
                for (var i = 0; i < data.length; i++) {
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

    (function () {
        $testNameSelect = $("#testPaperId");
        var cid2="${cid}";
        console.log("123////////");
        $.ajax({
            type: 'POST',
            url: '/school/onlineExam/userExam/getlist?cid='+cid2,
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

    function getTcoName(tgId) {
        console.log("tgId:"+tgId);
        $.ajax({
            type: 'POST',
            url: '/school/onlineExam/testPaperOne/getTcoName?cId=' + cId + "&&tid=" + tid+"&&tgId="+tgId,
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
</script>
