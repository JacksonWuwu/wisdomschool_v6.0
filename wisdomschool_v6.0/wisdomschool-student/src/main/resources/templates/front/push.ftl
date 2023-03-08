<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, maximum-scale=1.0">

    <link href="/css/bootstrap.min.css" type="text/css" rel="stylesheet">
    <link href="/css/indexstyle.css" type="text/css" rel="stylesheet">
    <script src="/js/jquery-2.1.4.min.js" type="text/javascript"></script>
    <script src="/js/bootstrap.min.js" type="text/javascript"></script>

    <link rel="stylesheet" href="/css/style-home.css"/>

    <link rel="stylesheet" type="text/css"
          href="/js/plugins/pagination/style/pagination.css"
          media="screen">
    <link rel="stylesheet" type="text/css"
          href="/js/plugins/pagination/style/normalize.css"
          media="screen">


    <#--<link href="/js/plugins/layui/css/pl.css" type="text/css"-->
    <#--rel="stylesheet">-->
    <meta name="renderer" content="webkit">

    <style type="text/css">

        .module-content-select {
            cursor: pointer;
            line-height: normal;
            outline: 0;
            white-space: nowrap;
            float: right;
            background: #FFF;
            border: 1px solid #d4d4d4;
            border-radius: 3px;
            color: #333;
            padding: 10px 25px 10px 10px;
            text-align: left;
            position: relative;
            font-size: 14px;
            min-width: 160px;
            vertical-align: top;
            width: 160px;
            outline: 0;
            height: 39px;
        }

    </style>
    <#include '/front/inc/commonJs.ftl'/>
</head>
<#include '/front/inc/header.ftl'/>
<!--大图-->
<div class="talk-datu">
    <img src="/img/front/banner-talk.jpg"/>
</div>
<!--主体-->

<body style="background-color: #eee">
<div style="background-color: #fff" class="container" id="div1">
    <div style="margin-top: 10px">
        <h4 style="font-size: 16px;font-weight: 700">
            <span class="glyphicon glyphicon-pencil" style="color:#23aaa0"></span>
            &nbsp;&nbsp;话题发布
        </h4>
    </div>
    <div style="padding:20px 0px;">
        <div class="module-content" style="float: left;">
            <select id="selectforum" class="module-content-select">
                <#list forumList as forum>
                    <option value="${forum.id}">${forum.name}</option>
                </#list>
            </select>
        </div>

        <div style="float:left;margin-left:20px;width:500px">
            <input type="text" id="talktitle" style="height: 39px" class="form-control"
                   placeholder="标题：请以一句话（15字以内）说明你想分享的经验或问题">
        </div>
        <div
                style="margin-top:45px;width:400px;background-color:rgba(255, 203, 203, 0.6);padding:5px;text-align:center;color:#ff7d0d;">
            请选择相关话题后发布，如发布至无关话题下，帖子将被删除
        </div>
        <div style="margin-top:30px"></div>
        <!--发布的框 -->
        <textarea class="layui-textarea" id="LAY_demo1" style="display: none">

            </textarea>
    </div>

    <div style="float: right;margin-bottom: 10px">

        <a id="cancel" class="btn btn-default" style="width: 110px;padding: 9px 0"
           href="javascript:void(0)">取消</a>
        <a id="saveQuestion" class="btn btn-primary"
           style="background-color: #00BC9B;width: 110px;padding: 9px 0"
           href="javascript:void(0)">发布</a>
    </div>
</div>
</body>
<script type="text/javascript" src="/dist/vendors/ckeditor/ckeditor.js"></script>
<script>
    $(document).ready(function () {
        //页面加载完自动执行
    });
    ckeditor1 = CKEDITOR.replace('LAY_demo1', {
        height: 300
    });

    //-----------------进入论坛----------------------------
    function talktongji(course, forumid, sort) {//参数：课程类别1=第一门，板块类别0=全部，排序方式1=第一种排序
        window.location.href = "/user/topicList/${tcid}?" +
            "course=" + course + "&" +
            "forumid=" + forumid + "&" +
            "sort=" + sort+"&token="+localStorage.getItem("token");
    }

    $("#cancel").click(function () {
        window.location.href = "/user/topicList/${tcid}"+"?token="+localStorage.getItem("token");
    });
    $("#saveQuestion").click(
        function () {
            let talktitle = $("#talktitle").val();
            let talkcontent = CKEDITOR.instances.LAY_demo1.document.getBody().getText();
            let forumid = $("#selectforum option:selected").val();
            if (talktitle == "" && talkcontent == "") {
                alert("标题和内容不能为空");
            } else {
                console.log(talkcontent);

                $.ajax({
                    type: "post",
                    url: "/user/topic/add",
                    data: {
                        "talktitle": talktitle,
                        "talkcontent": CKEDITOR.instances.LAY_demo1.getData(),
                        "forumid": forumid,
                    },
                    dataType: 'json',
                    success: function (data, status) { //这里的status是ajax自己的参数，请求成功就success
                        window.location.href = "/user/topicList/${tcid}?" +
                            "course=1"+"&token="+localStorage.getItem("token");
                    }
                });
            }
        });
</script>
</html>
