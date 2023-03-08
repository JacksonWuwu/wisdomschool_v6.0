<!DOCTYPE html>
<html lang="en" style="height: 100%">
<head>
    <meta charset="UTF-8" name="viewport" content="width=device-width,user-scalable=no">
    <title>练习测试</title>
    <link rel="stylesheet" href="/css/bootstrap.min.css">
    <script type="text/javascript" src="/js/jquery.min.js"></script>
</head>
<body style="height: 100%;">
<div class="container" style="height: 100%">
    <div style="height: 6%"></div>
    <div style="border-radius:15px;height: 40%;width: 100%;background-color: #FF4500;margin:0 auto;text-align:center;display:table;vertical-align:middle;" onclick="tofrontExam()">
        <p style="display: table-cell;
        vertical-align: middle;color: #FFFFFF;font-size: 20px;font-weight: bolder">课前测试</p>
    </div>
    <div style="height: 8%"></div>
    <div style="border-radius:15px;height: 40%;width: 100%;background-color: #5CACEE;margin:0 auto;text-align:center;display:table;vertical-align:middle;" onclick="courseList()">
        <p style="display: table-cell;
        vertical-align: middle;color: #FFFFFF;font-size: 20px;font-weight: bolder">章节练习</p>
    </div>
</div>
</body>
<script>
        function tofrontExam() {
            console.log("!!!!!!!!!!!!!!"+$(window).height());
            var openId="${openId}";
            //var openId = $("#openId").text();
            console.log("!!!!!!!!!!!!!?"+openId);
            window.open('/wx/exam/toFrontExam?openId='+openId);
        }
        function courseList() {
            console.log("!!!!!!!!!!!!!!"+$(window).height());
            var openId="${openId}";
            //var openId = $("#openId").text();
            console.log("!!!!!!!!!!!!!?"+openId);
            window.open('/wx/exam/courseList?openId='+openId);
        }
</script>
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.min.js"></script>
</html>

