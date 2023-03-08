<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, maximum-scale=1.0">

    <link rel="stylesheet"
          href="${basePath}/static/plugins/home/css/bootstrap.min.css"/>
    <link rel="stylesheet"
          href="${basePath}/static/plugins/home/css/style-home.css"/>
    <script type="text/javascript"
            src="${basePath}/static/plugins/home/js/jquery-3.1.1.min.js"></script>
    <script type="text/javascript"
            src="${basePath}/static/plugins/home/js/bootstrap.min.js"></script>

    <link href="${basePath}/static/plugins/home/css/indexstyle.css" type="text/css" rel="stylesheet">
    <link href="${basePath}/static/plugins/layui-v2.2.45/pl.css" type="text/css"
          rel="stylesheet">
    <meta name="renderer" content="webkit">
    <link rel="stylesheet"
          href="${basePath}/static/plugins/layui-v2.2.45/layui/css/layui.css" media="all">

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

<%@ include file="/basePage/baseMenu.jsp"%>
<!--大图-->
<div class="talk-datu">
    <img src="${basePath}/static/plugins/home/img/banner-talk.jpg"/>
</div>
<!--主体-->

<body style="background-color: #eee">
<div style="background-color: #fff" class="container" id="div1">
    <div style="margin-top: 10px">
        <h4 style="font-size: 16px;font-weight: 700">
            <span class="glyphicon glyphicon-pencil" style="color:#23aaa0"></span>
            &nbsp;&nbsp;
        </h4>
    </div>
    <div style="padding:20px 0px;">
        <div class="module-content" style="float: left;">
            <select id="selectforum" class="module-content-select">
                <c:forEach items="${forumList}" var="forum">
                    <option value="${forum.id}">${forum.name}</option>
                </c:forEach>
            </select>
        </div>

        <div style="float:left;margin-left:20px;width:500px">
            <input type="text" id="talktitle" style="height: 39px" class="form-control"
                   placeholder="标题：一句话说明你想分享的经验或问题">
        </div>
        <div
                style="margin-top:45px;width:400px;background-color:rgba(255, 203, 203, 0.6);padding:5px;text-align:center;color:#ff7d0d;">
            请选择相关话题后发布，如发布至无关话题下，帖子将被删除
        </div>
        <div style="margin-top:30px"></div>
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
<script src="${basePath}/static/plugins/layui-v2.2.45/layui/layui.all.js" charset="utf-8"></script>
<script>
    $(document).ready(function () {
        //页面加载完自动执行
        judgeLogin();    //登录后移除登录按钮
    });

    function judgeLogin() {
        //判断是否已登录
        var s = "<%=session.getAttribute("
        LOGINUSER
        ")%>";
        if (s != "null") {
            //$("#login-ad").children("button").remove();
            document.getElementById('login-ad').style.display = "none";
            document.getElementById('login-houtai').style.display = "none";
            document.getElementById('user-img').style.display = "inline";
            document.getElementById('user-na').style.display = "inline";

        } else {
            return false;
        }
    }


    layui.use('layedit', function () {
        var layedit = layui.layedit, $ = layui.jquery;

        layedit.set({
            uploadImage: {
                url: '${basePath}/servlet/UploadServlet',
                type: 'post' //默认post
            }
        });


        //构建一个默认的编辑器
        var index = layedit.build('LAY_demo1');

        //编辑器外部操作
        var active = {
            content: function () {
                return layedit.getContent(index);
                //获取编辑器内容
            },
            text: function () {
                return layedit.getText(index);
                //获取编辑器纯文本内容
            },
            selection: function () {
                alert(layedit.getSelection(index));
            }
        };


        $("#saveQuestion")
            .click(
                function () {
                    var talktitle = $("#talktitle").val();
                    var talkcontent = active.content();
                    var forumid = $("#selectforum option:selected").val();
                    var dangqianyema = 1;
                    if (talktitle == "") {
                        alert("问题不能为空");
                    } else {
                        console.log(talkcontent);

                        $.ajax({
                            type: "post",
                            url: "${basePath }/talk/topicAction!add.action",
                            data: {
                                "talktitle": talktitle,
                                "talkcontent": talkcontent,
                                "forumid": forumid,
                            },
                            async: false, //同步处理
                            success: function (data, status) { //这里的status是ajax自己的参数，请求成功就success
                                window.location.href = "${basePath}/talk/talkMangeAction!talkyema.action?" +
                                    "course=1";
                            }
                        });
                    }
                });


        $('.site-demo-layedit').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });


        //自定义工具栏
        layedit.build('LAY_demo2',
            {
                tool: ['face', 'link', 'unlink', '|', 'left', 'center',
                    'right'],
                height: 100
            });

    });

    //-----------------进入论坛----------------------------
    function talktongji(course, forumid, sort) {//参数：课程类别1=第一门，板块类别0=全部，排序方式1=第一种排序
        window.location.href = "${basePath}/talk/talkMangeAction!talkyema.action?" +
            "course=" + course + "&" +
            "forumid=" + forumid + "&" +
            "sort=" + sort;
    }

    $("#cancel")
        .click(
            function () {
                window.location.href = "${basePath}/talk/talkMangeAction!talkyema.action"

            }
        );

    //学习指导
    function xuexizhidao() {

        window.location.href = "${basePath}/tool/DeveToolMangeAction!showDaoxueQiantai.action";

    }

    //课程学习
    function coursefg() {
        window.location.href = "${basePath}/learn/courseLearnMangeAction!toCourseLearnList.action";
    }

    //资源库
    function yematongji(dangqianyema) {
        window.location.href = "${basePath}/courseUP/CourseUploadAction!fileyema.action?DangQianYeMa=" + dangqianyema;

    }

    //-----------------进入论坛----------------------------
    function talktongji(course, forumid, sort) {//参数：课程类别1=第一门，板块类别0=全部，排序方式1=第一种排序
        window.location.href = "${basePath}/talk/talkMangeAction!talkyema.action?" +
            "course=" + course + "&" +
            "forumid=" + forumid + "&" +
            "sort=" + sort;
    }
</script>
</html>