<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>人脸注册系统</title>

    <meta name="description" content="Source code generated using layoutit.com">
    <meta name="author" content="LayoutIt!">

    <link href="/face/css/bootstrap.min.css" rel="stylesheet">
    <link href="/face/css/style.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="/face/css/dmaku-demo.css" />
    <script src="/face/build/tracking.js"></script>
    <script src="/face/build/data/face.js" ></script>
    <script src="/face/js/stats.min.js"></script>
    <script src="/face/js/jquery.min.js"></script>
    <script src="/face/js/bootstrap.min.js"></script>
    <script src="/face/js/circleChart.min.js"></script>
    <script type="text/javascript" src="/js/common.js"></script>
    <script type="text/javascript" src="/js/plugins/jquery-validation/jquery.validate.min.js"></script>
    <script type="text/javascript" src="/js/plugins/jquery-validation/messages_zh.min.js"></script>
    <script type="text/javascript" src="/js/plugins/layer/layer.js"></script>
    <style>
        .qr-scanner .box {
            width: 54.5vw;
            height: 52.7vw;
            max-width: 54.5vh;
            max-height: 52.7vh;
            position: relative;
            left: 50%;
            top: 28vh;
            transform: translate(-50%, -50%);
            overflow: hidden;
            border: 0.1rem solid rgba(0, 255, 51, 0.2);
        }
        #video{
            width: 54vw;
            height: 60vw;
            max-width: 54vh;
            max-height: 70vh;
            position: absolute;
            left: 50%;
            top: 35.4vh;
            transform: translate(-50%, -50%);
        }

        .qr-scanner .line {
            height: calc(100% - 2px);
            width: 100%;
            background: linear-gradient(180deg, rgba(0, 255, 51, 0) 43%, #40B6E0 211%);
            border-bottom: 3px solid #40B6E0;
            transform: translateY(-100%);
            animation: radar-beam 2s infinite;
            animation-timing-function: cubic-bezier(0.53, 0, 0.43, 0.99);
            animation-delay: 1.4s;
        }

        .qr-scanner .box:after,
        .qr-scanner .box:before,
        .qr-scanner .angle:after,
        .qr-scanner .angle:before {
            content: '';
            display: block;
            position: absolute;
            width: 3vw;
            height: 3vw;
            border: 0.2rem solid transparent;
        }

        .qr-scanner .box:after,
        .qr-scanner .box:before {
            top: 0;
            border-top-color: #40B6E0;
        }

        .qr-scanner .angle:after,
        .qr-scanner .angle:before {
            bottom: 0;
            border-bottom-color: #40B6E0;
        }

        .qr-scanner .box:before,
        .qr-scanner .angle:before {
            left: 0;
            border-left-color: #40B6E0;
        }

        .qr-scanner .box:after,
        .qr-scanner .angle:after {
            right: 0;
            border-right-color: #40B6E0;
        }

        @keyframes radar-beam {
            0% {
                transform: translateY(-100%);
            }

            100% {
                transform: translateY(0);
            }
        }

    </style>
</head>
<body>
<div class="container-fluid mb-1" >
    <div class="row">
        <div class="col-md-12"  >
            <h2 class="text-center mb-3 mt-3 text-primary" >
                人脸注册系统
            </h2>
        </div>
    </div>

    <div style="background-image: url('/face/images/background.jpg');">
        <div class="row pr-4 pb-3 pt-1"  >
            <div class="col-md-6 text-center p-4"  >

                <div class="row" >
                    <div class="col-md-12" >
                        <div class="row">
                            <div class="col-md-12">
                            </div>
                        </div>
                        <div class="row" >
                            <div class="col-md-12" >
                                <div class="row h-100" >
                                    <div class="col-md-12 text-center"  >
                                        <div id="videoDiv" class="row " style='display: block'>
                                            <h4 class="text-center mb-3" style="color: white;">请将人脸放到正中间</h4>
                                            <video id='video'  style='margin-top: 20px'  autoplay  ></video>
                                            <#--<canvas id="canvas"></canvas>-->
                                            <div id="informationTitle" class="text-danger"></div>
                                            <div class="qr-scanner">
                                                <div class="box">
                                                    <div class="line"></div>
                                                    <div class="angle"></div>
                                                </div>
                                            </div>

                                        </div>
                                        <div id="picture" class="row" style='display: none'>
                                            <h4 class="text-center" style="color: white;">点击图片区域上传文件</h4>
                                            <input style='display: none' type='file' name='file1' id='file1' multiple='multiple' /><br>
                                            <img class="figure-img img-fluid rounded img-thumbnail" src='/face/images/shibie.jpg' onclick='ChooseFile()' id='img0' style='width: 25vw;height: 23vw;'>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-6 text-center p-4"  >
            <div class="row  w-100 h-75 m-0 mb-3 " >
                <div class="col-md-12 h-100 p-3 " style="border-width:1px;border-style:solid;border-color:#40B6E0;border-radius: 8px">
                    <img class="img-fluid rounded border img-thumbnail" id="nowimg" src="https://tse3-mm.cn.bing.net/th/id/OIP-C.WsVs_1wXmbVZ1543fXUHbwAAAA?pid=ImgDet&rs=1"
                         style="width: 15vw;height: 15vw;"/>
                    <h5 class="text-center " style="color: white;">现场采集照片</h5>
                </div>
            </div>
            <div class="row w-100 h-25 m-0 mb-2 p-1" >
                <div class="col-md-12 text-center text-info nav flex-column justify-content-center mb-3" style="border: #40B6E0 1px solid;border-radius: 8px" >
                    <div>
                        <label class="font-weight-bold h3 ">学号:</label>
                        <span class="font-weight-bold h3" id="loginName">20183350544</span>
                    </div>
                    <div>
                        <label class="font-weight-bold h3">姓名:</label>
                        <span class="font-weight-bold h3 " id="userName">王小明</span>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="row" id="btnDiv1"  >
        <div class="col-md-4 text-center" >
            <button type="button" class="btn btn-lg btn-outline-success  mt-4 mb-4"
                    onclick="faceCheck()" >
                摄像头注册
            </button>
        </div>
        <div class="col-md-4 text-center" >
            <button type="button" class="btn btn-lg btn-outline-success  mt-4 mb-4"
                    onclick="imageTo()">
                照片注册
            </button>
        </div>
        <div class="col-md-4 text-center" >
            <button type="button" class="btn  btn-lg btn-outline-success  mt-4 mb-4"  onclick="takePhoto()" >
                提交
            </button>
        </div>
    </div>
    <div class="row" id="btnDiv2"  >
        <div class="col-md-6 text-center" >
            <button type="button" class="btn  btn-lg btn-outline-success  mt-4 mb-4" id="check"  onclick="faceCheck()" >
               开始检测
            </button>
        </div>
            <div class="col-md-6 text-center" >
                <button type="button" class="btn  btn-lg btn-outline-success  mt-4 mb-4"  onclick="confirm()" >
                    提交
                </button>
            </div>
        </div>
    </div>
</div>
</body>

<script type="text/javascript">
    $(document).ready(function(){

        // getMedia2()
        if(true){ //是否开启照片注册
            $("#btnDiv1").hide();
        }else {
            $("#btnDiv2").hide();
        }
    });
     var check=0;
     function faceCheck() {
         check++;
        var tipFlag = false  // 是否检测
        var faceflag = false // 是否进行拍照
         var informationTitle = $("#informationTitle")//人脸提示
         informationTitle.html('未检测到人脸');
        // 获取video、canvas实例
        var facevideo = document.getElementById('video');
        // var canvas = document.createElement("canvas");
        // canvas.width = video.videoWidth ;
        // canvas.height = video.videoHeight ;
        var facecanvas = document.createElement('canvas');
        facecanvas.width = facevideo.videoWidth ;
        facecanvas.height = facevideo.videoHeight ;
        var facecontext = facecanvas.getContext('2d');
        // 使用监听人脸的包
        var tracker = new tracking.ObjectTracker('face');
        tracker.setInitialScale(4);
        tracker.setStepSize(2);
        tracker.setEdgesDensity(0.1);
        // 每次打开弹框先清除canvas没拍的照片
        facecontext.clearRect(0, 0, facecanvas.width, facecanvas.height);
        //打开摄像头
        var tra = tracking.track('#video', tracker, { camera: true });
        // 创建监听 每帧都会触发
        tracker.on('track', function(event) {
            if(!tipFlag){
                facecontext.clearRect(0, 0, facecanvas.width, facecanvas.height);
                if (event.data.length === 0) {
                    //未检测到人脸
                    if(!faceflag){
                        informationTitle.html('未检测到人脸')
                    }
                } else if (event.data.length === 1) { // 长度为多少代表检测到几张人脸
                    //检测到一张人脸
                    if(!tipFlag){
                        informationTitle.html('识别成功，正在拍照，请勿乱动~');
                        // 给检测到的人脸绘制矩形
                        event.data.forEach(function(rect) {
                            facecontext.strokeStyle = '#a64ceb';
                            facecontext.strokeRect(rect.x, rect.y, rect.width, rect.height);
                        });
                        if(!faceflag){// 检测到人脸进行拍照，延迟两秒
                            faceflag = true
                            setTimeout(() => {
                                //保存照片至canvas 利用canvas覆盖video形成截图界面
                                // facecontext.drawImage(facevideo, 0, 0, facecanvas.width, facecanvas.height)
                                takePhoto()
                                // let imgData = facecanvas.toDataURL()
                                // $("#nowimg").attr("src", imgData);
                                tipFlag = true
                            }, 2000);
                        }
                    }
                } else {
                    //检测到多张人脸
                    if(!faceflag){
                        informationTitle.html('只可一人进行人脸识别！')
                    }
                }
            }

        });

    };

    function ChooseFile()
    {
        $("#file1").trigger('click');
    }
    $(document).on("change","#file1",function(){
        var objUrl = getObjectURL(this.files[0]) ;//获取文件信息
        console.log("objUrl = "+objUrl);
        if (objUrl) {
            $("#img0").attr("src", objUrl);
        }
    });

    function getObjectURL(file) {
        var url = null;
        if (window.createObjectURL!=undefined) {
            url = window.createObjectURL(file) ;
        } else if (window.URL!=undefined) { // mozilla(firefox)
            url = window.URL.createObjectURL(file) ;
        } else if (window.webkitURL!=undefined) { // webkit or chrome
            url = window.webkitURL.createObjectURL(file) ;
        }
        return url ;
    }

    let constraints = {
        video: {width: 500, height: 500},
    };
    function getMedia2() {
        $("#videoDiv").css("display","block")
        $("#picture").css("display","none");
        faceCheck();
    }
    function imageTo()
    {
        $("#picture").css("display","block")
        $("#videoDiv").css("display","none")

    }

    $(".circleChart#1").circleChart({
        size:100,
        text: 0,
        onDraw: function(el, circle) {
            circle.text(Math.round(circle.value) + "%");
        }
    });
    function isHidden(el) {
        var style = window.getComputedStyle(el);//el即DOM元素
        return (style.display === 'none')
    }
    function takePhoto() {
        if (check>0){
            $("#check").text("重新注册")
        }
        var formData = new FormData();
        if(!$("#picture").is(":visible"))
        {
            // var scale = 0.25;
            let video = document.getElementById("video");
            var canvas = document.createElement("canvas");
            canvas.width = video.videoWidth ;
            canvas.height = video.videoHeight ;
            canvas.getContext('2d').drawImage(video, 0, 0, canvas.width, canvas.height);

            var base64File = canvas.toDataURL()
            $("#nowimg").attr("src", base64File);
            formData.append("file", base64File);
            register(formData)
        } else {
            var file = $("#file1")[0].files[0];
            if (file == undefined) {
                $.modal.alertWarning("请选择有人脸的图片进行注册");
                return;
            }
            var reader = new FileReader();
            reader.readAsDataURL(file);
            reader.onload = function () {
                var base64 = reader.result;
                formData.append("file", base64);
                register(formData)
            }
        }


    }
    var isRegistered=0;
    var token=localStorage.getItem("token")
    function register(formData){

        $.ajax({
            type: "post",
            url: "/face/register",
            data: formData,
            contentType: false,
            processData: false,
            async: false,
            headers:{"Authorization":token},
            success: function (res) {
                if (res.code == 0) {
                    $.modal.alertSuccess(res.msg);
                    console.log(res)
                    isRegistered=1;
                    <#--window.location.href="/exam?tcid="+${tcid}+"&cid="+${cid}+"&testPaperOneId="+${paperId}+"&token="+token-->
                } else {
                    $.modal.alertError(res.msg);
                }

            },
            error: function (error) {
                $.modal.alertError(error);
            }
        });
    }
    function confirm(){
        if(isRegistered===1){
            logout();
            window.location.href="/face/registerIndex"
        }else {
            $.modal.alertWarning("请进行注册");
        }
    }
    function logout(){
        $.ajax({
            type: "get",
            url: "/admin/sysLogin/logout",
            headers:{"Authorization":token},
            success: function (res) {
                localStorage.clear();
            },error: function(res){
                localStorage.clear();
            }
        });
    }
</script>

</html>
