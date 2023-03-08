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

</head>


<body style="background-color: #eee">
<div style="background-color: #fff;width: 100%;" class="container" id="div1">
    <div style="margin-top: 10px">
        <h4 style="font-size: 16px;font-weight: 700">
            <span class="glyphicon glyphicon-pencil" style="color:#23aaa0"></span>
            &nbsp;&nbsp;话题发布
        </h4>
    </div>
    <div style="width: 100%;">
        <div class="module-content" style="float: left;">
            <select id="selectforum" class="module-content-select">
                <#list forumList as forum>
                    <option value="${forum.id}">${forum.name}</option>
                </#list>
            </select>
        </div>

        <div style="float:left;width:100%">
            <input type="text" id="talktitle" style="margin-top:10px; height: 39px" class="form-control"
                   placeholder="标题：一句话(十字以内)说明你想分享的经验或问题">
        </div>
        <div class="form-control" style="width:100%;background-color:#23aaa0;text-align:center;color:#fff0ff;height:40px;">
           请选择相关话题后发布
        </div>
        <div style="margin-top:30px"></div>
        <!--发布的框 -->
        <textarea class="layui-textarea" id="LAY_demo1" style="display: none">

            </textarea>
    </div>

    <div style="float: right;margin-top: 10px;">

        <button type="button" class="btn btn-default" id="cancel"  style="width: 50px;padding: 9px 0"
           onclick="quxiao()"
        >取消</button>

        <button type="button" class="btn btn-primary" id="saveQuestion"
           style="background-color: #00BC9B;width: 50px;padding: 9px 0"
           onclick="fabu()">发布</button>
    </div>
</div>
</body>
<script type="text/javascript" src="/js/plugins/ckeditor/ckeditor.js"></script>
<script>

    $(document).ready(function () {
        //页面加载完自动执行
    });
    ckeditor1 = CKEDITOR.replace('LAY_demo1', {
        height: 400,
        toolbar: [

            {name: 'tools', items: ['ShowBlocks']}]
    });

        function fabu() {
                let talktitle = $("#talktitle").val();
                let talkcontent = CKEDITOR.instances.LAY_demo1.document.getBody().getText();
                let forumid = $("#selectforum option:selected").val();
                if (talktitle == "" && talkcontent == "") {
                    alert("标题和内容不能为空");
                } else {
                    console.log(talkcontent);

                    $.ajax({
                        type: "post",
                        url: "/wx/forum/indexadd",
                        data: {
                            "talktitle": talktitle,
                            "talkcontent": CKEDITOR.instances.LAY_demo1.getData(),
                            "forumid": forumid,
                            "openId": "${openId}"
                        },
                        dataType: 'json',
                        success: function (data, status) { //这里的status是ajax自己的参数，请求成功就success
                            window.location.href = "/wx/forum/indexlist?forumid=0&sort=${sort}&openId=${openId}" ;
                        }
                    });
                }
            }
    function prompt() {

        //弹出一个对话框

        alert("提示信息！");

    }
    function quxiao() {
        window.open('/wx/forum/indexlist?forumid=0&sort='+"${sort}"+"&"+"openId="+"${openId}");
    }
</script>
</html>