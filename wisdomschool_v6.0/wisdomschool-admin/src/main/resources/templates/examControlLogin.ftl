<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>登录</title>
    <link rel="stylesheet" href="/css/bootstrap.min.css">
    <link rel="stylesheet" href="/js/font-awesome/4.5.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="/css/style.css">
    <script src="/js/browser.min.js"></script>
    <script type="/js/text/babel"></script>
    <script type="text/javascript">
        // 获取谷歌版本
        function getChromeVersion() {
            var arr = navigator.userAgent.split(' ');
            var chromeVersion = '';
            for(var i=0;i < arr.length;i++){
                if(/chrome/i.test(arr[i]))
                    chromeVersion = arr[i]
            }
            if(chromeVersion){
                return Number(chromeVersion.split('/')[1].split('.')[0]);
            } else {
                return false;
            }
        }
        if(getChromeVersion()) {
            var version = getChromeVersion();
            if(version < 55) {
                window.location.href = "${ctx}/remind";
            }
        }


        function IEVersion(){
            var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串
            var isIE = userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1;
            if(isIE) {
                var reIE = new RegExp("MSIE (\\d+\\.\\d+);");
                reIE.test(userAgent);
                var fIEVersion = parseFloat(RegExp["$1"]);
                if (fIEVersion == 7) {
                    window.location.href = "${ctx}/remind";
                } else if (fIEVersion == 8) {
                    window.location.href = "${ctx}/remind";
                } else if (fIEVersion == 9) {
                    window.location.href = "${ctx}/remind";
                } else if (fIEVersion == 10) {
                    window.location.href = "${ctx}/remind";
                } else {
                    window.location.href = "${ctx}/remind";//IE版本<=7
                }
                }
        }


        if (!!window.ActiveXObject || "ActiveXObject" in window) {
            window.location.href = "${ctx}/remind";
        }

        window.onload = function () {

            //application/vnd.chromium.remoting-viewer 可能为360特有
            var is360 = _mime("type", "application/vnd.chromium.remoting-viewer");

            if (isChrome() && is360) {
                window.location.href = "${ctx}/remind";
            }
        }

    </script>

    <style>
        .text-center {
            text-align: center;
        }

        .forget-label{
            left : 15px;
            font-size: 15px;
        }

        .forget-input{
            width : 60%;
        }

        .no-margin{
            margin-left: 0 !important;
            margin-right: 0 !important;
        }


    </style>

</head>
<body>
<script>
    let wp = window;
    if(wp.parent&&wp.parent!==wp) {
        while(wp.parent&&wp.parent!==wp) {
            wp=wp.parent;
        }
        wp.location.href='${ctx}/login';
    }
</script>
<div class="login-container">
    <div class="page-top-box">
        <div class="pagemm top">
            <h1 class="navbar-text" style="font-size: 35px;position:absolute;text-align: center;margin: 3px; margin-left: 50px;color: #e0eee8;">智慧教育云</h1>
            <h3 class="navbar-text" style="font-size: 25px;position:absolute;margin-left: 260px; text-align: center;color: #e0eee8;font-family: cursive;">监考平台
            </h3>
        </div>
    </div>

            <div class="login-content " style="overflow: hidden; height: 52%">
                <h2 class="text-center login-title">请输入考试监考密码</h2>
                <div class="form-group">
                    <input type="text" name="username" class="form-control"  value="201735020000" readonly>
                </div>
                <div class="form-group">
                    <input type="password" name="password" id="password" class="form-control" placeholder="请输入密码">
                </div>
                <div class="form-group">
                    <input type="button" onclick="login()" class="btn-login btn btn-primary btn-block"  value="登录">
                </div>
            </div>
</div>
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/js/plugins/jquery-validation/jquery.validate.min.js"></script>
<script type="text/javascript" src="/js/plugins/jquery-validation/messages_zh.min.js"></script>
<#--<script type="text/javascript" src="/js/plugins/blockUI/jquery.blockUI.min.js"></script>-->
<#--<script type="text/javascript" src="/js/plugins/layer/layer.js"></script>-->
<#--<script type="text/javascript" src="/js/plugins/jsencrypt/jsencrypt.min.js"></script>-->
<#--<script type="text/javascript" src="/js/examcontrollogin.js"></script>-->
<#--<script type="text/javascript" src="/js/common.js"></script>-->
</body>
<script>
    function login() {
        var password = $(" #password ").val();
        var cid = ${cid};
        var tid = ${tid};
        var paperId = ${paperId};
        $.ajax({
            type: "post",
            url: "${ctx}/examControl/login",
            dataType: "json",
            data: {
                "password":password
            },
            success: function(r) {
                console.log(r.code)
                if (r.code === 0) {
                    location.href = '/infoStudent?cid='+cid+'&tid='+tid+'&paperId='+paperId;
                } else {
                   alert("密码错误")
                }
            }
        })
    }
</script>
</html>

