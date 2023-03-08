<!DOCTYPE html>
<html lang="en">
<head>
    <#--    设定界面大小，取消用户缩放   -->
    <meta charset="UTF-8" name="viewport" content="width=device-width,user-scalable=no">
    <title>画板</title>
    <link rel="stylesheet" href="/css/bootstrap.min.css">
    <link href="/huaban/css/index.css"  rel="stylesheet" type="text/css" />
    <link rel="stylesheet" type="text/css" href="/css/attendance/style.css">
    <link rel="stylesheet" type="text/css" href="/huaban/css/index.css">
    <link href="/static/ui2/vendors4/ion-rangeslider/css/ion.rangeSlider.css" rel="stylesheet" type="text/css">
    <link href="/static/ui2/vendors4/ion-rangeslider/css/ion.rangeSlider.skinHTML5.css"  rel="stylesheet" type="text/css">
</head>
<body>
<div class="hk-wrapper hk-vertical-nav">
    <div class="h_box">
        <canvas id="canvas" width="1200" height="600"></canvas>
        <div class="h_console">
            <div class="h_tool">工具台</div>
            <ul class="h_tool1">
                <li id="line" class="bg">
                    <span class="iconfont" title="绘制直线"><img src="/huaban/img/23.png" style="height: 30px;width: 30px" ></span>
                </li>
                <li id="rect" class="bg">
                    <span class="iconfont" title="绘制矩形"><img src="/huaban/img/1.png" style="height: 20px;width: 20px" ></span>
                </li>
                <li id="dytriangle" class="bg">
                    <span class="iconfont" title="绘制等腰三角形"><img src="/huaban/img/2.png" style="height: 20px;width: 20px" ></span>
                </li>
                <li id="zjtriangle" class="bg">
                    <span class="iconfont" title="绘制直角三角形"><img src="/huaban/img/3.png" style="height: 20px;width: 20px" ></span>
                </li>
                <li id="tablet" class="bg bgtb">
                    <span class="iconfont" title="铅笔"><img src="/huaban/img/4.png" style="height: 20px;width: 20px" ></span>
                </li>
                <li id="polygon" class="bg bgp">
                    <span class="iconfont" title="绘制多边形"><img src="/huaban/img/5.png" style="height: 20px;width: 20px" ></span>
                    <div class="polygon1">
                        <input type="number" min="3" max="30" value="3" id="range">
                        <div class="trag"></div>
                    </div>
                </li>
                <li class="bg" id="circle">
                    <span class="iconfont" title="绘制圆形"><img src="/huaban/img/6.png" style="height: 20px;width: 20px" ></span>
                </li>
                <li class="bg" id="ellipse">
                    <span class="iconfont" title="绘制椭圆"><img src="/huaban/img/7.png" style="height: 20px;width: 20px" ></span>
                </li>
                <li class="bg" id="eraser">
                    <span class="iconfont" title="橡皮擦"><img src="/huaban/img/8.png" style="height: 20px;width: 20px" ></span>
                </li>
            </ul>
            <ul class="h_tool1 h_tool2">
                <li class="bg" id="way">
                    <span class="iconfont" title="转换填充样式"><img src="/huaban/img/9.png" style="height: 30px;width: 30px" ></span>
                </li>
                <li class="bg bgp">
                    <span class="iconfont" title="修改填充色"><img src="/huaban/img/10.png" style="height: 40px;width: 40px" ></span>
                    <div class="polygon1 poly1">
                        <input type="color" id="fillCo">
                        <div class="trag"></div>
                    </div>
                </li>
                <li class="bg bgp">
                    <span class="iconfont" title="线条加粗"><img src="/huaban/img/11.png" style="height: 40px;width: 40px" ></span>
                    <div class="polygon2">
                        <input type="range" min="1" max="30" value="1" id="linew">
                        <div class="trag trag1"></div>
                        <p id="numW">0</p>
                    </div>
                </li>
                <li class="bg" id="revo">
                    <span class="iconfont" title="撤销"><img src="/huaban/img/12.png" style="height: 30px;width: 30px" ></span>
                </li>
                <li class="bg" id="refresh">
                    <span class="iconfont" title="刷新画板"><img src="/huaban/img/13.png" style="height: 30px;width: 30px" ></span>
                </li>
                <li class="bg" id="save">
                    <span class="iconfont" title="保存"><img src="/huaban/img/14.png" style="height: 10px;width: 10px" ></span>
                </li>
            </ul>
        </div>
        <div class="h_tittle">
            <div class="h_tittle1">
                <img src="/static/huaban/img/palette_03.png" alt="" />
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/huaban/js/index.js"></script>
<script type="text/javascript" src="/huaban/js/jquery-3.3.1.min.js"></script>


<#--<script>-->
<#--    var path ="${path}";-->
<#--    $(function () {-->

<#--        // window.location.href=path;-->
<#--        var tempwindow=window.open('_blank');-->
<#--        tempwindow.location=path;-->
<#--    })-->
<#--</script>-->
<#--<script type="text/javascript">-->
<#--    var path ="${path}";-->
<#--    function demo() {-->
<#--    window.open(path);-->
<#--    }-->
<#--</script>-->
</body>

</html>

