<#include "/common/style.ftl"/>
<link rel="stylesheet" href="/js/onlineExam/css/demo.css" type="text/css">
<link rel="stylesheet" href="/js/onlineExam/css/zTreeStyle/zTreeStyle.css" type="text/css">

<body class="white-bg">
<div class="container-div layout-container-div clearfix">
    <form class="form-horizontal m" id="form-userExam-add">
        <!--学生树-->
        <div class="row" id="buileStu">
            <div class="col-xs-12">
                <div class="widget-box">
                    <div class="widget-body">
                        <div class="widget-main">
                            <!-- #section:plugins/fuelux.wizard -->
                            <div id="fuelux-wizard-container">
                                <br>

                                <!-- #section:plugins/fuelux.wizard.container -->
                                <div class="step-content pos-rel">
                                    <div class="step-pane active" data-step="1">
                                        <div class="container">
                                            <a style="margin-left:50px"
                                               class="btn btn-primary radius-4"
                                               onclick="savebt()">
                                                添加班级
                                            </a>
                                            <div class="row">
                                                <ul id="treeDemo" class="ztree"
                                                    style="margin-top: 10px;margin-left: 60px;float:left; "></ul>
                                                <ul class="list">
                                                    <li style="list-style-type:none;"><p>
                                                        <ul id="log" class="log"
                                                            style="height:410px; overflow:auto;float:left; ">
                                                            <li>当前被选择的班级共 <span id="checkCount"
                                                                                class="highlight_red"></span> 个
                                                            </li>
                                                            <li>当前未被选择的班级共 <span id="nocheckCount"
                                                                                 class="highlight_red"></span> 个
                                                            </li>
                                                            <li> 所选班级有：
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
<#--                                <div class="form-group">-->
<#--                                    <label for="" class="col-sm-3 control-label"> 有效时段：</label>-->
<#--                                    <div class="col-sm-8">-->
<#--                                        <input type="datetime-local" class="time-input" id="stuStartTime" placeholder="有效时段"-->
<#--                                        />到-->
<#--                                        <input type="datetime-local" class="time-input" id="stuEndTime" placeholder="有效时段"-->
<#--                                        />-->
<#--                                    </div>-->
<#--                                </div>-->
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
</div>

<#include "/common/stretch.ftl"/>
<script src="/js/plugins/layui/layui.js"></script>
<script type="text/javascript">
    /**
     * 学生选择变量
     */
    var cId = "${cid}";
    var tid = "${tid}";
    var stuUserId = "";
    var prefix = "/teacher/vote";
    var setting = {
        view: {
            selectedMulti: false,
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
            onCheck: onCheck
        },
    };
    var zNodes = [];
    var clearFlag = false;

    /**
     * 树形结构构造
     */
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

    (function () {
        createTree();
        $("#init").bind("change", createTree);
        $("#last").bind("change", createTree);
        // console.log("cid:"+cId+"tid:"+tid);
        $.ajax({
            type: 'POST',
            url: '/school/onlineExam/userExam/getStudents?cId=' + cId + "&&tid=" + tid,
            dataType: 'json',
            success: function (data) {
                put(data);
            },
            error: function () {
                console.log("获取数据失败!");
            }
        });
    })();
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
            url: '/school/onlineExam/userExam/getTcoName?cId=' + cId + "&&tid=" + tid,
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
                $.fn.zTree.init($("#treeDemo"), setting, zNodes);
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
            url: '/school/onlineExam/userExam/getTcoStu?cId=' + cId + "&&tid=" + tid,
            dataType: 'json',
            success: function (data) {
                for (let i = 0; i < data.length; i++) {
                    let datavalue = {};
                    datavalue["id"] = data[i].userId;
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
                stuUserId += nodes[i - 1].id + ",";
            }
        }
        if (stuUserId == "") {
            $.modal.alertError("请选好班级后点击添加班级");
        } else {
            $.modal.alertSuccess("添加班级成功");
        }
    }

    /**
     * 提交
     */
    function submitHandler() {
        let cId = "${cid}";
        let userId = stuUserId;
        // let time = document.getElementById("time").value;
        let voteId = "${id}";
        // let stuStart = document.getElementById("stuStartTime").value;
        // let stuStartTime = Date.parse(stuStart);
        // let stuEnd = document.getElementById("stuEndTime").value;
        // let stuEndTime = Date.parse(stuEnd);
        // if(time == ""){
        //     $.modal.alertError("请填写考试时长");
        // }
        // else
        if (stuUserId == "") {
            $.modal.alertError("请选好班级后点击添加班级");
        } else if ($.validate.form()) {
            //  更新每个学生试卷
            $.operate.saveModal(prefix + "/addfabu", $.param({
                "id": voteId,
                // "time": time,
                // "starline": stuStartTime,
                // "deadline": stuEndTime,
                // "voteId": voteId,
                "userId": userId,
            }));
        }
    }
</script>
</body>
