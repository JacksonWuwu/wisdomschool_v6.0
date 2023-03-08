<#include "/common/style.ftl"/>
<link rel="stylesheet" href="/js/onlineExam/css/demo.css" type="text/css">
<link rel="stylesheet" href="/js/onlineExam/css/zTreeStyle/zTreeStyle.css" type="text/css">
<body class="white-bg">
<form class="form-horizontal m" id="form-userTest-add">
    <!--学生树-->
    <div class="row" id="buileStu">
        <div class="col-xs-12">
            <div class="widget-box">
                <div class="widget-body">
                    <div class="widget-main">
                        <!-- #section:plugins/fuelux.wizard -->
                        <div id="fuelux-wizard-container">
                            <br>
                            <!-- 输出信息-->
                            <div class="row">
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">时间设置：</label>
                                    <input type="text" name="createTime" style="width:255px;" class="time-input"
                                           id="startTime" placeholder="开始时间"/>
                                    <span>-</span>
                                    <input type="text" name="stuEndTime" style="width:255px;" class="time-input"
                                           id="endTime" placeholder="结束时间"/>
                              </div>
                            </div>
                            <div class="row">
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">作业：</label>
                                    <div class="col-sm-8">
                                        <@selectPage id='testPaperId' init="true" name='testPaper.id' url=ctx+'/school/onlineExam/userTest/testCoursePaperlist/${cid}'/>
                                    </div>
                                </div>
                            </div>
                            <br>
                            <!-- #section:plugins/fuelux.wizard.container -->
                            <div class="step-content pos-rel">
                                <div class="step-pane active" data-step="1">
                                    <div class="container">
                                        <a style="margin-left:50px"
                                           class="btn btn-primary radius-4"
                                           onclick="savebt()">
                                            添加学生
                                        </a>
                                        <div class="row">
                                            <ul id="treeDemo" class="ztree"
                                                style="margin-top: 10px;margin-left: 60px;float:left; "></ul>
                                            <ul class="list">
                                                <li style="list-style-type:none;"><p>
                                                    <ul id="log" class="log"
                                                        style="height:410px; overflow:auto;float:left; ">
                                                        <li>当前被选择的学生共 <span id="checkCount"
                                                                            class="highlight_red"></span> 个
                                                        </li>
                                                        <li>当前未被选择的学生共 <span id="nocheckCount"
                                                                             class="highlight_red"></span> 个
                                                        </li>
                                                        <li> 所选学生有：
                                                            <div id="stuName"></div>
                                                        </li>
                                                    </ul>
                                                    </p>
                                                </li>
                                            </ul>
                                            </li>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- /section:plugins/fuelux.wizard.container -->
                        </div>
                    </div>
                    <!-- /.widget-main -->
                </div>
                <!-- /.widget-body -->
            </div>
        </div>
    </div>
</form>
<!--/.container-div-->
<#include "/common/stretch.ftl"/>
<script type="text/javascript">
    $(function () {
        $.common.initBind();
    });
</script>
<SCRIPT type="text/javascript">
    var cId = "${cid}";
    var tid = "${tid}";
    var stuId = "";
    var setting = {
        view: {
            selectedMulti: false
        },
        check: {
            enable: true
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        callback: {
            onCheinitck: onCheck
        }
    };
    var zNodes = [];

    var clearFlag = false;
    let prefix = "/school/onlineExam/userTest";
    $("#form-userTest-add").validate({
        rules: {
            "loginName": {
                required: true,
                remote: {
                    url: "system/sysUser/checkLoginNameUnique",
                    type: "post",
                    dataType: "json",
                    data: {
                        "loginName": function () {
                            return $.common.trim($("#loginName").val());
                        }
                    },
                    dataFilter: function (data, type) {
                        return $.validate.unique(data);
                    }
                }
            },
            "startTime": {
                required: true,
            },
            "endTime": {
                required: true,
            },

            "testPaperId": {
                required: true,
            }
        },
        messages: {
            "startTime": {
                remote: "请选择开始时间"
            },
            "endTime": {
                required: "请选择结束时间"
            },
            "testPaperId": {
                required: "请选择试卷"
            }
        }
    });

    function submitHandler() {
        let cId = "${cid}";
        let userId = stuId;
        let stuStartTime = document.getElementById("startTime").value;
        let stuEndTime = document.getElementById("endTime").value;
        let testPaperId = document.getElementById("testPaperId").value;
        if (stuId == "") {
            $.modal.alertError("请选好学生后点击添加学生");
        } else if ($.validate.form()) {
            $.operate.saveModal(prefix + "/add", $.param({
                "cId": cId,
                "stuEndTime": stuEndTime,
                "testPaperId": testPaperId,
                "userId": userId,
                "stuStartTime": stuStartTime
            }));
        }
    }
    function onCheck(e, treeId, treeNode) {
        count();
        if (clearFlag) {
            clearCheckedOldNodes();
        }
    }

    function clearCheckedOldNodes() {
        var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
            nodes = zTree.getChangeCheckedNodes();
        for (var i = 0, l = nodes.length; i < l; i++) {
            nodes[i].checkedOld = nodes[i].checked;
        }
    }

    function count() {
        var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
            checkCount = zTree.getCheckedNodes(true).length,
            nocheckCount = zTree.getCheckedNodes(false).length,
            changeCount = zTree.getChangeCheckedNodes().length;
        $("#checkCount").text(checkCount);
        $("#nocheckCount").text(nocheckCount);
        $("#changeCount").text(changeCount);

    }

    function createTree() {
        $.fn.zTree.init($("#treeDemo"), setting, zNodes);
        count();
        clearFlag = $("#last").attr("checked");
    }

    $(document).ready(function () {
        createTree();
        $("#init").bind("change", createTree);
        $("#last").bind("change", createTree);

        $.ajax({
            type: 'POST',
            url: '/school/onlineExam/userTest/getStudents?cId=' + cId + "&&tid=" + tid,
            dataType: 'json',
            success: function (data) {
                put(data);
            },
            error: function () {
                console.log("获取数据失败!");
            }
        });

    });
//年级
    function put(data) {
        for (let i = 0; i < data.length; i++) {
            let datavalue = {};
            datavalue["id"] = data[i].tgId;
            datavalue["pId"] = 0;
            datavalue["name"] = data[i].tgName;
            datavalue["open"] = "true";
            zNodes.push(datavalue);
        }
        getTcoName();
    }

    function getTcoName() {
        $.ajax({
            type: 'POST',
            url: '/school/onlineExam/userTest/getTcoName?cId=' + cId + "&&tid=" + tid,
            dataType: 'json',
            success: function (data) {
                for (let i = 0; i < data.length; i++) {
                    let datavalue = {};
                    datavalue["id"] = data[i].tcId;
                    datavalue["pId"] = data[i].tgId;
                    datavalue["name"] = data[i].tcName;
                    datavalue["open"] = "true";
                    zNodes.push(datavalue);
                }
                getTcoStu();

            },
            error: function () {
                alert("获取数据失败!");
            }
        });
    }
//获取学生
    function getTcoStu() {
        $.ajax({
            type: 'POST',
            url: '/school/onlineExam/userTest/getTcoStu?cId=' + cId + "&&tid=" + tid,
            dataType: 'json',
            success: function (data) {
                for (let i = 0; i < data.length; i++) {
                    let datavalue = {};
                    datavalue["id"] = data[i].tsId;
                    datavalue["pId"] = data[i].tcId;
                    datavalue["name"] = data[i].sysName;
                    console.log(data[i].sysName);
                    zNodes.push(datavalue);
                }
                $.fn.zTree.init($("#treeDemo"), setting, zNodes);

            },
            error: function () {
                alert("获取数据失败!");
            }
        });
    }

    function savebt() {
        var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
        var sNodes = treeObj.getSelectedNodes();
        var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
        var nodes = treeObj.getCheckedNodes(true);
        document.getElementById("stuName").innerHTML = '';
        for (let i = nodes.length; i > 0; i--) {
            if(nodes[i - 1].isParent!=true){
                document.getElementById("stuName").innerHTML += nodes[i - 1].name + '<br>';
                stuId += nodes[i - 1].id + ",";
            }
        }
        if (stuId == "") {
            $.modal.alertError("请选好学生后点击添加学生");
        } else {
            $.modal.alertSuccess("添加学生成功");
        }
    }
</SCRIPT>
</body>
