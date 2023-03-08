<!doctype html>
<html>
<head>
    <base href="/">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>WSTOM - KPlayer</title>
    <link rel="stylesheet" href="/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/recourse/overrall.css">
    <link rel="stylesheet" href="/css/recourse/video-js.css">
    <link rel="icon" type="image/x-icon" href="/css/recourse/player.png"/>
    <script src="/js/recourse/video.js"></script>
    <!--[if lt IE 9]>
<#--<!--    <script type="text/javascript" src="/js/recourse/videojs-ie8.min.js"></script>&ndash;&gt;-->
    <script src="/js/ace/js/html5shiv.min.js"></script>
    <script src="/js/ace/js/respond.min.js"></script>
    <![endif]-->
</head>

<body>
<div class="container">

    <div class="row">
        <div class="col-md-12">
            <div class="titlebox">
					<span class="titletext"><em> WSTOM视频播放器 <small><span
                                        class="graytext">WSTOM-Player</span></small></em></span>
                <button class="btn btn-link rightbtn" onclick="reMainPage()">
                    关闭 <span class="glyphicon glyphicon-share-alt" aria-hidden="true"></span>
                </button>
            </div>
            <hr/>
        </div>
    </div>

    <div class="row">
        <div class="col-md-12">
            <p class="subtitle">
                视频名称：<span id="vname">加载中...</span>
            </p>
            <p class="subtitle">
                <span id="vcdate">加载中...</span>/<span
                        id="vsize">加载中...</span> MB
            </p>
            <br/>
            <!-- 播放窗口组件位置 -->
            <div id="playerbox" class="col-md-12">

             </video>
                <p>加载中...</p>
            </div>
        </div>
    </div>

<script>

</script>


</div>

</body>
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/js/recourse/video.js"></script>
<script type="text/javascript">
    var fid=null

    $(function () {
        let fileId = "${fileId}";
        $.ajax({
            url: '/recourse/playVideo/' + fileId,
            type: 'get',
            dataType: 'json',
            success: function (result) {
                if (result.code === 0) {
                    playVideo(result.data, result.pf);
                    fid=result.data.fileId;
                } else {
                    alert("错误：无法定位要预览的文件或该操作未被授权。");
                    reMainPage();
                }
            },
            error: function () {
                alert("错误：请求失败，请刷新重试。");
                reMainPage();
            }
        });
    });




    /*原版*/
        function playVideo(f, pf) {
            console.log('视频可以播放了', this);
            $("#vname").text(f.fileName);
            $("#vcdate").text(f.fileCreationDate);
            $("#vsize").text(f.fileSize);
            $("#playerbox")
                .html(
                    "<video id='kiftplayer' class='video-js col-md-12' controls preload='auto' height='500'>"
                    + "<source src='${storage}/resource/getResource?fid=" + f.fileId + "' type='video/mp4'>"
                    + "<source src='${storage}/resource/getResource?fid=" + f.fileId + "' type='video/webm'>"
                    + "</video>");
            var player = videojs('kiftplayer');
            player.ready(function () {

                this.play();
            });
        }
        function reMainPage() {
            window.opener = null;
            window.open('', '_self');
            window.close();
        }
</script>
</html>