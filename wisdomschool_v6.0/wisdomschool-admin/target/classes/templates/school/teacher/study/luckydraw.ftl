<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <title>随机抽点</title>
    <meta name="description" content="A responsive bootstrap 4 admin dashboard template by hencework" />
<#--    <link rel="shortcut icon" href="@{/static/ui2/dist/img/pagelogo.ico}">-->
<#--    <!-- Toggles CSS &ndash;&gt;-->
<#--    <link href="@{/static/ui2/vendors4/jquery-toggles/css/toggles.css}" rel="stylesheet" type="text/css">-->
<#--    <link href="@{/static/ui2/vendors4/jquery-toggles/css/themes/toggles-light.css}" rel="stylesheet" type="text/css">-->
    <!-- Custom CSS -->
<#--    <link href="@{/static/ui2/dist/css/style.css}" rel="stylesheet" type="text/css">-->
<#--    <!-- ION CSS &ndash;&gt;-->
<#--    <link href="@{/static/ui2/vendors4/ion-rangeslider/css/ion.rangeSlider.css}" rel="stylesheet" type="text/css">-->
<#--    <link href="@{/static/ui2/vendors4/ion-rangeslider/css/ion.rangeSlider.skinHTML5.css}"  rel="stylesheet" type="text/css">-->
    <link rel="stylesheet" type="text/css" href="/css/attendance/style.css">
    <!-- select2 CSS -->
<#--    <link href="@{/static/ui2/vendors4/select2/dist/css/select2.min.css}" rel="stylesheet" type="text/css" />-->

<#--    <!-- Pickr CSS &ndash;&gt;-->
<#--    <link href="@{/static/ui2/vendors4/pickr-widget/dist/pickr.min.css}"  rel="stylesheet" type="text/css" />-->

<#--    <!-- Daterangepicker CSS &ndash;&gt;-->
<#--    <link href="@{/static/ui2/vendors4/daterangepicker/daterangepicker.css}"  rel="stylesheet" type="text/css" />-->


<#--    <!-- Bootstrap table CSS &ndash;&gt;-->
<#--    <link href="@{/static/ui2/vendors4/bootstrap-table/dist/bootstrap-table.min.css}" rel="stylesheet" type="text/css" />-->

</head>

<body style="background: rgb(30,34,35);">
<div class="hk-wrapper hk-vertical-nav" style="background: rgb(30,34,35);">
    <div class="conetentBox_00008">
        <div class="wrapper_00042">
            <div class="title_00030">点名提问</div>
        </div>
    </div>
    <div class="conetentBox_00009">
        <div class="wrapper_00043" id="userform">
            <div class="wrapper_00044">
                <div class="avatar avatar-xxl" id="userimg">
                    <img src="/img/artsix.png" alt="user" class="avatar-img rounded-circle" >
                </div>
            </div>
            <div class="wrapper_00045">
                <div class="title_00031" id="name">姓名</div>
            </div>
            <div class="wrapper_00046">
                <div class="title_00032" id="number">学号</div>
            </div>
        </div>
        <div class="wrapper_00047" id="btn">
            <button id="startLuckyBtn" type="button" class="btn btn-primary btn-block btn-rounded" style="font-size: 30px;">点击随机</button>
        </div>
    </div>
</div>
<#--    <!-- jQuery &ndash;&gt;-->
<#--    <script src="@{/static/ui2/vendors4/jquery/dist/jquery.min.js}"></script>-->

<#--    <!-- Bootstrap Core JavaScript &ndash;&gt;-->
<#--    <script src="@{/static/ui2/vendors4/popper.js/dist/umd/popper.min.js}"></script>-->
<#--    <script src="@{/static/ui2/vendors4/bootstrap/dist/js/bootstrap.min.js}"></script>-->

<#--    <!-- Slimscroll JavaScript &ndash;&gt;-->
<#--    <script src="@{/static/ui2/dist/js/jquery.slimscroll.js}"></script>-->

<#--    <!-- Fancy Dropdown JS &ndash;&gt;-->
<#--    <script src="@{/static/ui2/dist/js/dropdown-bootstrap-extended.js}"></script>-->

<#--    <!-- FeatherIcons JavaScript &ndash;&gt;-->
<#--    <script src="@{/static/ui2/dist/js/feather.min.js}"></script>-->

<#--    <!-- Toggles JavaScript &ndash;&gt;-->
<#--    <script src="@{/static/ui2/vendors4/jquery-toggles/toggles.min.js}"></script>-->
<#--    <script src="@{/static/ui2/dist/js/toggle-data.js}"></script>-->

<#--    <!-- Init JavaScript &ndash;&gt;-->
<#--    <script src="@{/static/ui2/dist/js/init.js}"></script>-->
<#--    <script src="@{/static/layer/layer.js}"></script>-->


<#--    <!-- Bootstrap-table JavaScript &ndash;&gt;-->
<#--    <script src="@{/static/ui2/vendors4/bootstrap-table/dist/bootstrap-table.min.js}"></script>-->

<#--    <!-- Peity JavaScript &ndash;&gt;-->
<#--    <script src="@{/static/ui2/vendors4/peity/jquery.peity.min.js}"></script>-->
<#--    <script src="@{/static/ui2/dist/js/peity-data.js}"></script>-->
<#--    <!-- Easy pie chart JS &ndash;&gt;-->
<#--    <script src="@{/static/ui2/vendors4/easy-pie-chart/dist/jquery.easypiechart.min.js}"></script>-->
<#--    <!--<script th:src="@{/static/ui2/dist/js/easypiechart-data.js}"></script>&ndash;&gt;-->
<#--    <!-- Qrcode JavaScript &ndash;&gt;-->
<#--    <script src="@{/static/qrcode/qrcode.js}"></script>-->


</body>
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script>
    var courseid= "${courseid}";
    var studentform;
    var howmanypeople;
    var dongtai=0;
    var myVar=0;
    //页面初始化
    $(function () {

        luckypeopleform();
        <#--var ahref="/teacher/study"-->
        <#--$("#courseDetailhref").attr('href',ahref);-->
        //初始化删除章节确认按钮
        $("#startLuckyBtn").click(function () {
            startlucky();
        });
        $("#stopLuckyBtn").click(function () {
            stoplucky();
        });
    });

    function luckypeopleform() {
        console.log("aaaaa");
        $.ajax({
            type: "GET",
            url: "/teacher/study/drawform?courseid="+courseid,
            dataType: "JSON",
            success: function(data) {
                studentform=data;
                howmanypeople=data.length-1;
                dongtai=data.length-1;
            },
            error: function() {
                alert("服务器连接失败");
            }
        });
    }
    function stoplucky() {
        clearInterval(myVar);
        $("#stopLuckyBtn").remove();
        $("#btn").append(' <button id="startLuckyBtn" onclick="startlucky()" type="button" class="btn btn-primary btn-block btn-rounded" style="font-size: 30px;">点击随机</button>');
    }

    function startlucky() {
        myVar = setInterval(function(){updateone() }, 0.05*1000);
        $("#startLuckyBtn").remove();
        $("#btn").append(' <button id="stopLuckyBtn" onclick="stoplucky()" type="button" class="btn btn-primary btn-block btn-rounded" style="font-size: 30px;">停止</button>');
    }
    //更新一次
    function updateone() {
        if (dongtai==-1){
            dongtai=howmanypeople;
        }
        $("#name").empty();
        $("#name").append(studentform[dongtai][0]);
        $("#number").empty();
        $("#number").append(studentform[dongtai][1]);
        $("#userimg").empty();
        $("#userimg").append(
            '<img src="/img/artsix.png'+
            '" alt="user" class="avatar-img rounded-circle" >');
        dongtai=dongtai-1;
    }
</script>
</html>
