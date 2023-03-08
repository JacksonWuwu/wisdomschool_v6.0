<link rel="stylesheet" href="/css/attendance/style.css"/>
<#include "/common/style.ftl"/>
<div class="container-div">
    <div class="btn-group-sm hidden-xs" id="toolbar" role="group">
<#--        <@shiro.hasPermission name="teacher:attendance:view">-->
            <a class="btn btn-success" onclick="$.operate.add()">
                <i class="fa fa-plus"></i> 新增
            </a>
<#--        </@shiro.hasPermission>-->
    </div>

    <div class="col-sm-12 select-table table-striped">
        <table id="bootstrap-table" data-mobile-responsive="true"></table>
    </div>
</div>
<!-- 二维码考勤modal -->
<div class="modal fade" id="erweimakaoqin" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenter" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">二维码考勤</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="conetentBox_00004">
                    <div class="wrapper_00010" >
<#--                        <img src="/img/erweima.jpg" style="width: 400px; height: 400px; flex: 0 0 auto;" />-->
                        <div id="qrcode" style="width: 400px; height: 400px; flex: 0 0 auto;"></div>
                    </div>
<#--                    <div class="wrapper_00016">-->
<#--                        <div class="wrapper_00017">-->
<#--                            <img  src="/img/setting.png" style="width: 25px; height: 25px; flex: 0 0 auto;" />-->
<#--                        </div>-->
<#--                        <div class="wrapper_00018">-->
<#--                            <div class="title_00017" onclick="setqrcodeupdatetime()">设置二维码变化时间</div>-->
<#--                        </div>-->
<#--                    </div>-->
                    <div class="wrapper_00011">
                        <div class="wrapper_00012">
                            <div class="title_00013">已考勤人数:</div>
                        </div>
                        <div class="wrapper_00012" >
                            <div class="wrapper_00013">
                                <div class="title_00014" id="studentokbyqrcode">99</div>
                            </div>
                            <div class="wrapper_00014">
                                <div class="title_00015">/</div>
                            </div>
                            <div class="wrapper_00015">
                                <div class="title_00016" id="studentsumbyqrcode">99</div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
<#--                <button onclick="stoperweimaattendance()" id="stopattendanceBtn" type="button" class="btn btn-secondary" style="background-color:#FFC938;border-color: #FFC938;">结束</button>-->
            </div>
        </div>
    </div>
</div>


<!-- 设置二维码变化时间Modal -->
<div class="modal fade" id="qrcodeupdatetime" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenter" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">二维码变化时间</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="conetentBox_00005">
                    <div class="title_00018">不变化</div>
                    <select class="form-control custom-select d-block w-15" id="updatetime" style="float: left;margin-top: 8px;">
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
                        <option value="6">6</option>
                        <option value="7">7</option>
                        <option value="8">8</option>
                        <option value="9">9</option>
                        <option value="10">10</option>
                    </select>
                    <div class="title_00019">秒变化一次</div>
                    <input type="hidden" id="nowqrcodeupdatetime">
                    <input type="hidden" id="nowattendanceid">
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="saveqrcodeupdatetimeBtn">保存</button>
            </div>
        </div>
    </div>
</div>


<!-- 数字考勤modal -->
<div class="modal fade" id="suzikaoqin" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenter" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">数字考勤</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="conetentBox_00011">
                    <div class="wrapper_00065" id="numbercode">
                        <div class="wrapper_00066">
                            <div class="title_00041">6</div>
                        </div>
                        <div class="wrapper_00066">
                            <div class="title_00041">6</div>
                        </div>
                        <div class="wrapper_00066">
                            <div class="title_00041">6</div>
                        </div>
                        <div class="wrapper_00066">
                            <div class="title_00041">6</div>
                        </div>

                    </div>
                    <div class="wrapper_00016">
                        <div class="wrapper_00017">
                            <img  src="/img/setting.png" style="width: 25px; height: 25px; flex: 0 0 auto;" />
                        </div>
                        <div class="wrapper_00018" id="updateanumberbtn">
                        </div>
                    </div>
                    <div class="wrapper_00011">
                        <div class="wrapper_00012">
                            <div class="title_00013">已考勤人数:</div>
                        </div>
                        <div class="wrapper_00012" >
                            <div class="wrapper_00013">
                                <div class="title_00014" id="studentokbysuzi">99</div>
                            </div>
                            <div class="wrapper_00014">
                                <div class="title_00015">/</div>
                            </div>
                            <div class="wrapper_00015">
                                <div class="title_00016" id="studentsumbysuzi">99</div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer" id="stopsuziattendance">
                <button id="stopsuziattendanceBtn" type="button" class="btn btn-secondary" style="background-color:#FFC938;border-color: #FFC938;">结束</button>
            </div>
        </div>
    </div>
</div>
<#include "/common/stretch.ftl"/>
<#--<script src="/js/dropify/dropify.min.js"></script>-->
<#--<script type="text/javascript" src="/js/jquery.min.js"></script>-->
<#--<script src="/js/qrcode/qrcode.js"></script>-->
<script type="text/javascript">
    let prefix = "/teacher/attendance/${tcid}";
    var myStu;
    var myQar;
    var myVar;
    $(function () {
        load()
        //初始化变化时间保持按钮
        $("#saveqrcodeupdatetimeBtn").click(function () {
            saveqrcodeupdatetime();
        });
    })

    function load() {
        let options = {
            url: prefix + "/list",
            createUrl: prefix + "/add",
            detailUrl: prefix + "/detail/{id}",
            updateUrl: prefix + "/edit/{id}",
            removeUrl: prefix + "/remove",
            modalName: "课程考勤",
            showExport: true,
            columns: [{
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
                    field: 'title',
                    title: '名称',
                    sortable: true
                },
                {
                    field: 'createTime',
                    title: '创建时间',
                    sortable: true
                },
                {
                    field: 'clbum.name',
                    title: '班级'
                },
                {
                    field: 'type',
                    title: '签到方式',
                    formatter: function(value, item, index) {
                        if (item.type == '0') {
                            return '<span class="label label-success">数字</span>';
                        }
                        else if (item.type == '1') {
                            return '<span class="label label-primary">扫码</span>';
                        }
                        else if (item.type == '2') {
                            return '<span class="label label-warning">点名</span>';
                        }
                    }
                },
                {
                    field: 'state',
                    title: '状态',
                    sortable:true,
                    align:'center',
                    frozen: true ,
                    editable : true,
                    formatter: function(value, item, index) {
                        if (item.type=='0'||item.type=='1'){
                        if (item.state == '1') {
                            return '<a class="btn btn-warning btn-xs" href="#" onclick="stop(\'' + item.id + '\')"><i class="fa fa-pause"></i>停止</a> '+'<a class="btn btn-warning btn-xs" href="#" onclick="show(\'' + item.id + '\',\'' + item.type + '\')"><i class="fa fa-pause"></i>展示</a> ';

                        }
                        else if (item.state == '2') {
                            return '<span class="label label-primary">已结束</span>';
                        }
                        else if (item.state == '0') {
                            return '<a class="btn btn-info btn-xs" href="#" onclick="start(\'' + item.id + '\',\'' + item.cid + '\',\'' + item.type + '\')"><i class="fa fa-play"></i>启用</a> ';
                        }
                    }else {
                            if (item.state == '1') {
                                return '<a class="btn btn-warning btn-xs" href="#" onclick="stop(\'' + item.id + '\')"><i class="fa fa-pause"></i>停止</a> ';
                            }
                            else if (item.state == '2') {
                                return '<span class="label label-primary">已结束</span>';
                            }
                            else if (item.state == '0') {
                                return '<a class="btn btn-info btn-xs" href="#" onclick="start(\'' + item.id + '\',\'' + item.cid + '\',\'' + item.type + '\')"><i class="fa fa-play"></i>启用</a> ';
                            }
                        }
                }
                },
                {
                    title: '操作',
                    align: 'center',
                    formatter: function (value, row, index) {
                        let actions = [];
                        <!-- actions.push(statusTools(row));-->
<#--                        <@shiro.hasPermission name="teacher:course:edit">-->
                        actions.push('<a class="btn btn-success btn-xs " href="#" onclick="$.operate.edit(' + row.id + ')"><i class="fa fa-edit"></i>编辑</a> ');
<#--                        </@shiro.hasPermission>-->
<#--                        <@shiro.hasPermission name="teacher:attendance:view">-->
                        actions.push('<a class="btn btn-danger btn-xs " href="#" onclick="$.operate.remove(' + row.id + ', $.operate.ajaxSuccess)"><i class="fa fa-remove"></i>删除</a>');
<#--                        </@shiro.hasPermission>-->
                        actions.push('<a class="btn btn-warning btn-xs " href="#" onclick="$.operate.detail(' + row.id +')"><i class="fa fa-search"></i>详细</a>');
                        return actions.join('');
                    }
                }]
        };
        $.table.init(options);
        $.common.initBind();
        stopupdatesuzistu()
        stopupdateerweimastu()
    }

    //数字签到
    function show(attendanceid,type) {
        if (type== '0') {
            $.ajax({
                type: "GET",
                url: "/teacher/attendance/startsuziattendance?attendanceid="+attendanceid,
                dataType: "JSON",
                success: function(data) {
                    $("#studentokbysuzi").empty();
                    $("#studentokbysuzi").prepend(0);
                    $("#studentsumbysuzi").empty();
                    $("#studentsumbysuzi").prepend(data[0]);
                    $("#numbercode").empty();
                    $("#numbercode").append(
                        '<div class="wrapper_00066"><div class="title_00041">'+data[1][0]+'</div></div>'+
                        '<div class="wrapper_00066"><div class="title_00041">'+data[1][1]+'</div></div>'+
                        '<div class="wrapper_00066"><div class="title_00041">'+data[1][2]+'</div></div>'+
                        '<div class="wrapper_00066"><div class="title_00041">'+data[1][3]+'</div></div>'
                    );
                    $("#updateanumberbtn").empty();
                    $("#updateanumberbtn").append(
                        '<div class="title_00017" onclick="updateanumbercode('+attendanceid+')">更换一个</div>'
                    );
                    $("#stopsuziattendance").empty();
                    $("#stopsuziattendance").append(
                        // '<button  type="button" class="btn btn-secondary" onclick="stopsuziattendance('+attendanceid+')" style="background-color:#FFC938;border-color: #FFC938;">结束</button>'
                    );
                    $('#suzikaoqin').modal("show");
                    updatesuzimodalokstu(attendanceid);
                },
                error: function() {
                    alert("服务器连接失败");
                }
            });
        }
        if (type== '1'){
            $.ajax({
                type: "GET",
                url: "/teacher/attendance/startqrcodeattendance?attendanceid="+attendanceid,
                dataType: "JSON",
                success: function(data) {
                    $("#studentokbyqrcode").empty();
                    $("#studentokbyqrcode").prepend(0);
                    $("#studentsumbyqrcode").empty();
                    $("#studentsumbyqrcode").prepend(data[0]);
                    qrcode.makeCode(data[1]);
                    $('#xinjiankaoqin').modal("hide");
                    $('#erweimakaoqin').modal("show");
                    $("#nowattendanceid").val(attendanceid);
                    $("#nowqrcodeupdatetime").val(10);
                    updateyesstudentnumber(attendanceid);
                },
                error: function() {
                    alert("服务器连接失败");
                }
            });
        }
    }

    /*调度任务-启用*/
    function start(id,cid,type) {
        $.modal.confirm("确认要启用任务吗？", function() {
            $.operate.submitPost(prefix + "/changeStatusStart", {"id": id, "cid": cid,"status": 1});
            if (type=='0'){
                startsuziattendance(id);
            }
            if (type=='1'){
                startqrcodeattendance(id);
            }
        })
    }

    /*调度任务-停用*/
    function stop(id) {
        $.modal.confirm("确认要停用任务吗？", function() {
            $.operate.submitPost(prefix + "/changeStatusStop", {"id": id, "status": 2});
            $("#bootstrap-table").bootstrapTable('refresh');
        })

    }
    //开始数字考勤
    function startsuziattendance(attendanceid) {
        $.ajax({
            type: "GET",
            url: "/teacher/attendance/startsuziattendance?attendanceid="+attendanceid,
            dataType: "JSON",
            success: function(data) {
                $("#studentokbysuzi").empty();
                $("#studentokbysuzi").prepend(0);
                $("#studentsumbysuzi").empty();
                $("#studentsumbysuzi").prepend(data[0]);
                $("#numbercode").empty();
                $("#numbercode").append(
                    '<div class="wrapper_00066"><div class="title_00041">'+data[1][0]+'</div></div>'+
                    '<div class="wrapper_00066"><div class="title_00041">'+data[1][1]+'</div></div>'+
                    '<div class="wrapper_00066"><div class="title_00041">'+data[1][2]+'</div></div>'+
                    '<div class="wrapper_00066"><div class="title_00041">'+data[1][3]+'</div></div>'
                );
                $("#updateanumberbtn").empty();
                $("#updateanumberbtn").append(
                    '<div class="title_00017" onclick="updateanumbercode('+attendanceid+')">更换一个</div>'
                );
                $("#stopsuziattendance").empty();
                $("#stopsuziattendance").append(
                    '<button  type="button" class="btn btn-secondary" onclick="stopsuziattendance('+attendanceid+')" style="background-color:#FFC938;border-color: #FFC938;">结束</button>'
                );
                $('#suzikaoqin').modal("show");
                updatesuzimodalokstu(attendanceid);
            },
            error: function() {
                alert("服务器连接失败");
            }
        });
    }

    //更新一次数字考勤码
    function updateanumbercode(attendanceid) {
        $.ajax({
            type: "GET",
            url: "/teacher/attendance/updateanumbercode?attendanceid="+attendanceid,
            dataType: "JSON",
            success: function(data) {
                $("#numbercode").empty();
                $("#numbercode").append(
                    '<div class="wrapper_00066"><div class="title_00041">'+data[0]+'</div></div>'+
                    '<div class="wrapper_00066"><div class="title_00041">'+data[1]+'</div></div>'+
                    '<div class="wrapper_00066"><div class="title_00041">'+data[2]+'</div></div>'+
                    '<div class="wrapper_00066"><div class="title_00041">'+data[3]+'</div></div>'
                );
            },
            error: function() {
                alert("服务器连接失败");
            }
        });
    }

    function updatesuzimodalokstu(attendanceid) {
        myStu = setInterval(function(){updatesuziokstuonce(attendanceid) }, 5*1000);
    }

    //更新一次数字考勤modal签到人数
    function updatesuziokstuonce(attendanceid) {
        $.ajax({
            type: "GET",
            url: "/teacher/attendance/updatesuziokstuonce?attendanceid="+attendanceid,
            dataType: "JSON",
            success: function(data) {
                $("#studentokbysuzi").empty();
                $("#studentokbysuzi").prepend(data);
            },
            error: function() {
                alert("服务器连接失败");
            }
        });
    }

    //当数字考勤窗口关闭时停止更新人数
    function stopupdatesuzistu() {
        $('#suzikaoqin').on('hidden.bs.modal', function () {
            console.log("数字窗口被隐藏");
            clearInterval(myStu);
            load()
        })
    }


    //初始化结束数字考勤
    function stopsuziattendance(attendanceid) {
        clearInterval(myStu);
        $('#suzikaoqin').modal("hide");
        $.operate.submitPost(prefix + "/changeStatusStop", {"id": id, "status": 2});
        $("#bootstrap-table").bootstrapTable('refresh');
    }


    var qrcode = new QRCode(document.getElementById("qrcode"), {
        text: "https://www.baidu.com/?tn=87048150_dg",
        width: 400,
        height: 400,
        colorDark: "#000000",
        colorLight: "#ffffff",
        correctLevel: QRCode.CorrectLevel.H
    });

    //初始化设置二维码变化时间按钮
    function setqrcodeupdatetime(){
        $('#qrcodeupdatetime').modal("show");
    };
    //开始二维码考勤
    function startqrcodeattendance(attendanceid) {
        $.ajax({
            type: "GET",
            url: "/teacher/attendance/startqrcodeattendance?attendanceid="+attendanceid,
            dataType: "JSON",
            success: function(data) {
                $("#studentokbyqrcode").empty();
                $("#studentokbyqrcode").prepend(0);
                $("#studentsumbyqrcode").empty();
                $("#studentsumbyqrcode").prepend(data[0]);
                qrcode.makeCode(data[1]);
                $('#xinjiankaoqin').modal("hide");
                $('#erweimakaoqin').modal("show");
                $("#nowattendanceid").val(attendanceid);
                updateyesstudentnumber(attendanceid);
            },
            error: function() {
                alert("服务器连接失败");
            }
        });
    }

    //保存二维码变化时间更改
    function saveqrcodeupdatetime(){
        var time=$("#updatetime").val();
        console.log("二维码变化时间更改为"+time);
        $("#nowqrcodeupdatetime").val(time);
        clearInterval(myVar);
        updateupdatebytime($("#nowattendanceid").val());
        $('#qrcodeupdatetime').modal("hide");
    }

    function updateupdatebytime(attendanceid) {
        myVar = setInterval(function(){updateupdateonce(attendanceid) }, $("#nowqrcodeupdatetime").val()*1000);
    }

    //更新一次二维码
    function updateupdateonce(attendanceid) {
        <#--$.ajax({-->
        <#--    type: "GET",-->
        <#--    // url: "/front/attendance/updateqrcodeonce?attendanceid="+attendanceid,-->
        <#--    url: "/teacher/attendance/updateyesstudent?attendanceid="+attendanceid,-->
        <#--    dataType: "JSON",-->
        <#--    success: function(data) {-->
        <#--        qrcode.makeCode(data);-->
        <#--        console.log("二维码更新");-->
        <#--    },-->
        <#--    error: function() {-->
        <#--        alert("服务器连接失败");-->
        <#--    }-->
        <#--});-->
    }

    function updateyesstudentnumber(attendanceid) {
        myQar = setInterval(function(){updateyesstudentonce(attendanceid) }, 5*1000);
    }


    //更新一次二维码考勤modal签到人数
    function updateyesstudentonce(attendanceid) {
        $.ajax({
            type: "GET",
            url: "/teacher/attendance/updateerweiokstuonce?attendanceid="+attendanceid,
            dataType: "JSON",
            success: function(data) {
                $("#studentokbyqrcode").empty();
                $("#studentokbyqrcode").prepend(data);
            },
            error: function() {
                alert("服务器连接失败");
            }
        });

    }

    //当二维码考勤窗口关闭时停止更新人数
    function stopupdateerweimastu() {
        $('#erweimakaoqin').on('hidden.bs.modal', function () {
            console.log("二维码被隐藏，停止");
            clearInterval(myStu);
            $("#bootstrap-table").bootstrapTable('refresh');
        })
    }
    //结束二维码考勤
    function stoperweimaattendance(attendanceid) {
        clearInterval(myStu);
        $('#erweimakaoqin').modal("hide");
        $.operate.submitPost(prefix + "/changeStatusStop", {"id": id, "status": 2});
        $("#bootstrap-table").bootstrapTable('refresh');
    }

    <#--// 每段时间更新考勤人数-->
    <#--function updateyesstudentnumber(attendanceid) {-->
    <#--    myQar = setInterval(function(){updateyesstudentonce(attendanceid) }, 2*1000);-->
    <#--}-->
    <#--//更新一次二维码modal考勤人数-->
    <#--function updateyesstudentonce(attendanceid) {-->
    <#--    $.ajax({-->
    <#--        type: "GET",-->
    <#--        url: "/teacher/attendance/updateyesstudent?attendanceid="+attendanceid,-->
    <#--        dataType: "JSON",-->
    <#--        success: function(data) {-->
    <#--            $("#studentokbyqrcode").empty();-->
    <#--            $("#studentokbyqrcode").prepend(data);-->
    <#--            console.log("已考勤人数更新");-->
    <#--        },-->
    <#--        error: function() {-->
    <#--            alert("服务器连接失败");-->
    <#--        }-->
    <#--    });-->
    <#--}-->

</script>
