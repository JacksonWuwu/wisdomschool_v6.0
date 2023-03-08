<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1"/>
    <title>在线作业</title>
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
    <!--课程面板-->

    <!--课程主要内容面板-->
<div class="learn-main">
    <#if selected == "forum">
        <#include '/front/forum.ftl'/>
    <#elseif selected == "editor">
        <#include '/front/editor.ftl'/>
    <#elseif selected == "detail">
        <#include '/front/detail.ftl'/>
    <#else >
        <#include '/front/testExam.ftl'/>
    </#if>
    <!--页脚-->
    <div class="footer">
        <center>
            <br/>
            智慧教育云平台版权所有 | 学生端
        </center>

    </div>
</div>

    <!-- 视频在线播放模态框 -->

<script type="text/javascript">
    let tmpFileId;
    function toExam(userId,testPaperOneId,tutId) {
        var token=localStorage.getItem("token")
        window.open('/user/chapterPaperWork?testPaperOneId='+ testPaperOneId+'&studentId=' + userId +"&tutId="+tutId+"&token="+token);
    }



</script>
</body>
</html>
