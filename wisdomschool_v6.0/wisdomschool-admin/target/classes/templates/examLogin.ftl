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
        .code
        {
            font-family:Arial;
            font-style:italic;
            color:blue;
            font-size:30px;
            border:0;
            padding:2px 3px;
            letter-spacing:3px;
            font-weight:bolder;
            float:left;
            cursor:pointer;
            width:110px;
            height:30px;
            line-height:30px;
            text-align:center;
            vertical-align:middle;
            background-color:#D8B7E3;
        }
        span {
            text-decoration:none;
            font-size:12px;
            color:#288bc4;
            padding-left:10px;
        }

        span:hover {
            text-decoration:underline;
            cursor:pointer;
        }
    </style>

</head>
<body>

<div class="login-container" style="background: url(../img/front/cy5.jpg) no-repeat;background-size:100% 100%;">
    <div class="page-top-box">
        <div class="pagemm top">
            <h1 class="navbar-text" style="font-size: 35px;position:absolute;text-align: center;margin: 3px; margin-left: 50px;color: #e0eee8;">学生的专属在线考试平台</h1>
        </div>
    </div>

            <div class="login-content " style="overflow: hidden; height: 52%">
                <form action="" method="post" id="loginForm" class="w-75" align="center">
                <h2 class="text-center login-title">欢迎登录</h2>
                <div class="form-group">
                    <input type="text" id="username" class="form-control" placeholder="请输入职工号/学号">
                </div>
                <div class="form-group">
                    <input type="password" id="password" class="form-control" placeholder="请输入密码">
                </div>
                <div class="form-group">
                    <div class="row">
                        <div class="col-md-7">
                            <input type="text" id="inputCode" class="form-control" name="inputCode" placeholder="请输入验证码"></div>
                        <div class="col-md-3 text-right">
                            <div id="checkCode" class="code"  onclick="createCode(4)"></div>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <input type="button" onclick="login()" id="btnSubmit" class="btn-login btn btn-primary btn-block" data-loading="正在验证登录，请稍后..." value="登录">
                </div>

       <div class="text-center">
           <a href="javascript:forgotPsw()"  style="color: white; font-size: 15px;">忘记密码？</a>
       </div>

            </form>
            </div>
</div>
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/js/plugins/jquery-validation/jquery.validate.min.js"></script>
<script type="text/javascript" src="/js/plugins/jquery-validation/messages_zh.min.js"></script>
<script type="text/javascript" src="/js/plugins/blockUI/jquery.blockUI.min.js"></script>
<script type="text/javascript" src="/js/plugins/layer/layer.js"></script>
<script type="text/javascript" src="/js/plugins/jsencrypt/jsencrypt.min.js"></script>
<#--<script type="text/javascript" src="/js/examloginOne.js"></script>-->
<script type="text/javascript" src="/js/common.js"></script>
<script type="text/javascript">

    $(document).ready(function(){
        createCode(4);

    });
    //生成验证码的方法
    function createCode(length) {
        var code = "";
        var codeLength = parseInt(length); //验证码的长度
        var checkCode = document.getElementById("checkCode");
        ////所有候选组成验证码的字符，当然也可以用中文的
        var codeChars = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9,
            'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'];
        //循环组成验证码的字符串
        for (var i = 0; i < codeLength; i++)
        {
            //获取随机验证码下标
            var charNum = Math.floor(Math.random() * 62);
            //组合成指定字符验证码
            code += codeChars[charNum];
        }
        if (checkCode)
        {
            //为验证码区域添加样式名
            checkCode.className = "code";
            //将生成验证码赋值到显示区
            checkCode.innerHTML = code;
        }
    }



    function login() {
        $.modal.loading("正在处理中，请稍后...");
        //登录
        if ( $("#username").val()==''){
            $.modal.closeLoading();
            $.modal.alertWarning("请输入账号");
        } else if ($("#password").val()==''){
            $.modal.closeLoading();
            $.modal.alertWarning("请输入密码");
        }else if(!validateCode()){
            $.modal.closeLoading();
            $.modal.alertWarning("验证码有误");
        } else {
            $.ajax({
                type: "post",
                url: "/admin/sysLogin/login",
                data: {
                    "loginName": $("#username").val(),
                    "password": $("#password").val()
                },
                success: function (res) {
                    $.modal.closeLoading();
                    if(res.code===0){
                        localStorage.setItem("token",res.data.token);
                        localStorage.setItem("expiredDate",res.data.expiredDate);
                        check()
                    }

                },error: function(res){
                    $.modal.closeLoading();
                    $.modal.alertWarning(res.msg);
                }
            });

        }
    }
    function check(){
        //检测是否开启人脸
        $.ajax({
            type: "get",
            url: "/school/onlineExam/testPaperOne/examFaceStatus/${testPaperOneId}",
            success: function (res) {
                if (res.face=='1'){
                   window.location.href="/face/compare/${testPaperOneId}/${tcid}/${cid}?token="+localStorage.getItem("token")
                }else {
                    window.location.href="/exam?tcid="+${tcid}+"&cid="+${cid}+"&testPaperOneId="+${testPaperOneId}+"&token="+localStorage.getItem("token")
                }
            }
        });
    }

    function toIndex(){

        let tcidOne = ${tcid};
        let cidOne = ${cid};
        let testPaperOneId = ${testPaperOneId};
        // 先缓存 name.length
        let token = ""
        token=localStorage.getItem("token");
        window.location.href="/exam?tcid="+tcidOne+"&cid="+cidOne+"&testPaperOneId="+testPaperOneId+"&token="+token
    }

    /**
     * 页面跳转
     * @param url   请求地址xxx\xxx?xxx=xx&xx=xx
     * @param params 可选参数 json对象数据{‘a’:1}
     */
    function httpPostLocationUrl (url,params) {
        var form = $("<form method='post' style='display:none'></form>");
        if(!params)params ={};
        if(url.indexOf('?')!=-1){
            var paramsStr = url.split("?")[1].split('&');
            for (var i = 0 ;i<paramsStr.length;i++){
                if(paramsStr[i]&&paramsStr[i].indexOf("=")!=-1){
                    var data =  paramsStr[i].split('=');
                    params[data[0]] = data[1];
                }
            }
            url = url.split("?")[0];
        }
        form.attr({"action": url});
        if(!$.isEmptyObject(params)){
            for (arg in params ) {
                var input = $("<input type='hidden'>");
                input.attr({"name": arg});
                input.val(params[arg]);
                form.append(input);
            }
        }
        $("html").append(form); //兼容火狐
        form.submit();
    }


    //检查验证码是否正确
    function validateCode() {
        //获取显示区生成的验证码
        var checkCode = document.getElementById("checkCode").innerHTML;
        //获取输入的验证码
        var inputCode = document.getElementById("inputCode").value;
        if (inputCode.length <= 0) {
            return false
        }
        else if (inputCode.toUpperCase() !== checkCode.toUpperCase()) {
            createCode(4);
            return false
        } else {
            return true
        }
    }


                    function forgotPsw(){
                        var html = "";
                        html += "<form name=\"emailForm\" class=\"form-horizontal\" role=\"form\" method=\"POST\" style=\"margin-top: 30px;\">";
                        html += "  <div class=\"form-group no-margin\">";
                        html += "    <label class=\"col-sm-3 control-label text-center forget-label\" for=\"email\">邮箱地址：</label>";
                        html += "    <div class=\"col-sm-6\">";
                        html += "      <input id=\"email\" type=\"text\" class=\"form-control\" name=\"email\" placeholder=\"请输入邮箱地址\">";
                        html += "    </div>";
                        html += "    <div class=\"col-sm-3\">";
                        html += "       <button type=\"button\" class=\"btn btn-default\" onclick=\"submitEmail()\">发送验证码</button>";
                        html += "    </div> ";
                        html += "    </div> ";
                        html += "  <div class=\"form-group no-margin\">";
                        html += "    <label class=\"col-sm-3 control-label text-center forget-label\" for=\"captcha\">验证码：</label>";
                        html += "    <div class=\"col-sm-9\">";
                        html += "      <input id=\"captcha\" type=\"text\" class=\"form-control forget-input\" name=\"captcha\" placeholder=\"请输入验证码\" >";
                        html += "    </div>";
                        html += "  </div>";
                        html += "  <div class=\"form-group no-margin\">";
                        html += "    <label class=\"col-sm-3 control-label text-center forget-label\" for=\"forgetpassword\">新密码：</label>";
                        html += "    <div class=\"col-sm-9\">";
                        html += "      <input id=\"forget-password\" type=\"password\" class=\"form-control forget-input\" name=\"forget-password\" placeholder=\"请输入新密码\">";
                        html += "    </div>";
                        html += "  </div>";
                        html += "    <div class=\"form-group no-margin\">";
                        html += "      <label class=\"col-sm-3 control-label text-center forget-label\" for=\"re-forget-password\">再次输入密码：</label>";
                        html += "     <div class=\"col-sm-9\">";
                        html += "      <input id=\"re-forget-password\" type=\"password\" class=\"form-control forget-input\" name=\"re-forget-password\" placeholder=\"请再次输入密码\">";
                        html += "     </div>";
                        html += "     </div>";
                        html += "     <div class=\"col-sm-12 text-center \">";
                        html += "       <button type=\"button\" class=\"btn btn-primary\" style=\"width: 30%\" onclick=\"updatePasswordByCaptcha()\" id=\"submitpassword\">提交</button>";
                        html += "     </div> ";
                        html += "</form>";
                        html += " ";
                        html += " ";

                        layer.open({
                            type: 1,
                            title: '忘记密码',
                            shadeClose: true,
                            shade: 0.8,
                            area: ['50%', '40%'],
                            content:html
                        });
                    }


                    function submitEmail() {

                        if ($("#email").val() == "") {
                            layer.msg("邮箱不能为空!");
                            return;
                        }

                        $.ajax({
                            type: "POST",
                            url: "${ctx}/sendemail",
                            data : {
                                email : $("#email").val()
                            },
                            dataType: "json",
                            success: function (result) {
                                layer.msg(result.msg);
                            },
                            error: function (result) {
                                layer.msg(result.msg);
                            }

                        });
                    }


                    function updatePasswordByCaptcha() {

                        if ($("#email").val() == "") {
                            layer.msg("邮箱不能为空!");
                            return;
                        }

                        if ($("#captcha").val() == "") {
                            layer.msg("验证码不能为空!");
                            return;
                        }

                        if ($("#forget-password").val() == "" || $("#re-forget-password").val() == "") {
                            layer.msg("密码不能为空!");
                            return;
                        }

                        if ($("#forget-password").val() != $("#re-forget-password").val()) {
                            layer.msg("密码输入不一致");
                            return;
                        }

                        $.ajax({
                        type: "POST",
                            url: "${ctx}/updatePasswordByCaptcha",
                            data : {
                                email : $("#email").val(),
                                repassword : $("#forget-password").val(),
                                captcha : $("#captcha").val()

                            },
                            dataType: "json",
                            success: function (result) {
                                layer.msg(result.msg);
                            },
                            error: function (result) {
                                layer.msg(result.msg);
                            }
                        });
                    }
                </script>


</body>
</html>

