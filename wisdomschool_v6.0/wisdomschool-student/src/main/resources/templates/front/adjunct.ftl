<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1"/>
    <title>考试任务-考试</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!--地址栏和标签上显示图标-->
    <!--bootstrap引用-->
    <link rel="shortcut icon"
          href="/img/front/favicon.ico"
          type="image/x-icon">
    <link href="/css/bootstrap.min.css" type="text/css" rel="stylesheet">
    <link href="/css/indexstyle.css" type="text/css" rel="stylesheet">
    <script src="/js/jquery-2.1.4.min.js" type="text/javascript"></script>
    <script src="/js/bootstrap.min.js" type="text/javascript"></script>

    <link rel="stylesheet" href="/css/style-home.css"/>
    <link rel="stylesheet" href="/css/course.css"/>

    <style>
        .chapter a {
            color: #5a5c5f;
        }

        ul.chapter-sub.chapter-resource li {
            color: #0089D2;
            font-size: 16px;
        }
        table.imagetable {
            font-family: verdana,arial,sans-serif;
            font-size:18px;
            color:#333333;
            width: 100%;
            border-width: 1px;
            border-color: #999999;
            border-collapse: collapse;
        }
        table.imagetable th {
            background:#b5cfd2 url('cell-blue.jpg');
            border-width: 1px;
            padding: 8px;
            border-style: solid;
            border-color: #999999;
        }
        table.imagetable td {
            background:#dcddc0 url('cell-grey.jpg');
            border-width: 1px;
            padding: 8px;
            border-style: solid;
            border-color: #999999;
        }

    </style>

</head>
<body>
<!--页头-->
<#include '/front/inc/commonJs.ftl'/>
<#include '/front/inc/header.ftl'/>
<!--课程主要内容面板-->
<div class="learn-main">
    <#include '/front/adjunctone_new.ftl'/>
    <!--页脚-->
    <div class="footer">
        <center>
            <br/>
            智慧教育云平台版权所有 | 后台管理
        </center>

    </div>
</div>


<script type="text/javascript">

    $().ready(function () { //$().ready页面加载好就执行
    });
    function toadjunct(id) {
        var url = '/user/toadjunct?id=' + id;
        window.location.href=url+"&token="+localStorage.getItem("token");
    }
</script>
</body>
</html>
