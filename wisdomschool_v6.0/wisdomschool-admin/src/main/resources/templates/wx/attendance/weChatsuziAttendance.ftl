<!DOCTYPE html>
<html lang="en">
<head>
    <#--    设定界面大小，取消用户缩放   -->
    <meta charset="UTF-8" name="viewport" content="width=device-width,user-scalable=no">
    <title>数字签到页面</title>
    <link rel="stylesheet" href="/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <link rel="stylesheet" type="text/css" href="/css/attendance/style.css">
</head>
<body>
<form id="suziAttendanceform" name="suziAttendanceform">
        <div class="wrapper_00068">
            <div class="title_00042">请输入数字，手动完成签到</div>
            <div class="title_00043">(数字由老师处获取)</div>
        </div>
        <div class="wrapper_00068" >
            <div class="col-md-6 mt-20"  >
                <input type="text" class="form-control" aria-label="Large" aria-describedby="inputGroup-sizing-sm" id="numbercode" name="numbercode">
            </div>
        </div>
         <input type="hidden" id="openid" name="openid"  value="${openid}">
         <input type="hidden" id="tcid" name="tcid"  value="${tcid}">
         <input type="hidden" id="sic" name="sid"  value="${sid}">
</form>
<div class="wrapper_00039" >

    <button id="suziAttendanceBtn" class="btn btn-block mr-10" style="background-color: #FFC938;border-color: #FFC938;" type="button" onclick="suziAttendance()">
        签到
    </button>
</div>
</body>
<#include "/common/stretch.ftl"/>
<script type="text/javascript">
    $(function () {
        $.common.initBind();
    });
    let prefix = "${ctx}/wx/attendance";
    $("#suziAttendanceform").validate({
        rules: {
            "numbercode": {
                required: true
            },
        },
        messages: {
            "numbercode": {
                required: "请输入签到码"
            },
        }
    });
    function suziAttendance() {
        $.ajax({
            type: "POST",
            url: "/wx/attendance/suzitoattendance",
            data: $("#suziAttendanceform").serialize(),
            dataType: "JSON",
            success: function (data) {
                alert(data.msg);
                // window.location.href = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx37847780767eaf5c&redirect_uri=http://5hbcfn.natappfree.cc/wx/account/authorize?returnUrl=http://5hbcfn.natappfree.cc/wx/course/index&response_type=code&scope=snsapi_userinfo&state=null&connect_redirect=1#wechat_redirect";
            },
            error: function() {
                alert("签到失败");
            }
        });
    }
</script>


</html>



