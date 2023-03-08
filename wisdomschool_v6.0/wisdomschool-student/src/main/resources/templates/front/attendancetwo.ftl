<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"  content="width=device-width">
    <title>签到</title>
    <script src="https://cdn.staticfile.org/jquery/1.10.2/jquery.min.js">
    </script>
    <script src="/js/BrowserUtil.js"></script>
    <script src="/js/verifyCode.js"></script>
    <link href="/css/verifyCode.css" rel="stylesheet">
    <#include '/front/inc/commonJs.ftl'/>
    <style>
        *{
            box-sizing: border-box;
            padding: 0;
            margin: 0;
        }
        #codeDiv{
            margin: 0 auto;
            margin-top: 20%;
        }
    </style>
</head>
<body>
<div id="codeDiv" align="center"></div>
<script>
    verifyCodeDOMUtil.buildCodeModule("codeDiv", "code")
    verifyCode.init("code")
    let object = {
        alert : function (value) {
            alert(value)
        }
    }
    verifyCode.that = object
    verifyCode.onFinish = function (value,that) {
        let psd=${psd}
            if(psd==value){
                $.ajax({
                    type: "post",
                    dataType: 'json',
                    url: "http://192.168.0.101:8080/user/updataattendance",
                    success: function (data) {
                        alert("签到cehngg")
                    }
                });
                window.close();
            }
            else {
                that.alert("签到失败")
            }
    }
</script>
</body>
</html>
