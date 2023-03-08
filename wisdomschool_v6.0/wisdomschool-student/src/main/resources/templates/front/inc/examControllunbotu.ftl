<!DOCTYPE html>
<html >
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />


    <style>
        *{padding: 0px;margin: 0px;}
        #flash{width:1400px;height: 370px;margin: 50px auto;position: relative;}
        #flash #play{width:1400px;height: 370px;list-style: none;position:absolute;top:0px;left:0px;}
        #flash #play li{display: none;position:absolute;top:0px;left:0px;}
        #flash #play li img{float: left;}
        #button{position: absolute;bottom:20px;left:650px;list-style: none;}
        #button li{margin-left: 10px;float: left;}
        #button li div{width:12px;height: 12px;background:#DDDDDD;border-radius: 6px;cursor: pointer;}
        #prev{width:40px;height:63px;background:url(img/images/beijing.png) 0px 0px;position: absolute;top:205px;left:10px;z-index: 1000;}
        #next{width:40px;height:63px;background:url(img/images/beijing.png) -40px 0px;position: absolute;top:205px;right:10px;z-index: 1000;}
        #prev:hover{background:url(img/images/beijing.png) 0px -62px;}
        #next:hover{background:url(img/images/beijing.png) -40px -62px;}
    </style>

    <script type="text/javascript" src="/js/script.js"></script>

</head>
<body>

<div id="flash" style="position: relative;top: -111px;">

    <ul id="play">
        <li style="display: block;"></li>
    </ul>

</div>

</body>
</html>