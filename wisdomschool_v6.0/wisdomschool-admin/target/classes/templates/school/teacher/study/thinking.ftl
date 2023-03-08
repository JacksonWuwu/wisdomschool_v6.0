<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>jsPlumb流程图</title>
    <link rel="stylesheet" type="text/css" href="/huaban/siwei/jquery-ui.min.css">
    <link rel="stylesheet" type="text/css" href="/huaban/siwei/style.css">
</head>
<body>
<div class="container device">
    <div class="deviceLeft">
        <h3>配置1</h3>
        <div class="deviceLeft_box">
            <div class="node node1css" data-type="server">服务器</div>
            <div class="node node2css" data-type="host">主机</div>
            <div class="node node3css" data-type="aisle">通道</div>
        </div>
        <h3>配置2</h3>
        <div class="deviceLeft_box">
            <div class="node node4css" data-type="route">路由</div>
        </div>
        <h3>配置3</h3>
        <div class="deviceLeft_box">
            <div class="node node5css" data-type="signal">信号</div>
        </div>
    </div>
    <div id="deviceRight" style="background: url(/huaban/siwei/bg.png)">
        <div id="main"></div>
    </div>
</div>

<script src="https://www.jq22.com/jquery/jquery-1.10.2.js"></script>
<script type="text/javascript" src="/huaban/siwei/jquery-ui.min.js"></script>
<script type="text/javascript" src="/huaban/siwei/jquery.jsPlumb.min.js"></script>
<script type="text/javascript" src="/huaban/siwei/index.js"></script>
</body>
</html>