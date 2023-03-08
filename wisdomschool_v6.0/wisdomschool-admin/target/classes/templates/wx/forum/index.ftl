<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1"/>
    <title>学习讨论</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="shortcut icon"
          href="static/plugins/home/img/favicon.ico"
          type="image/x-icon"/>
    <!--地址栏和标签上显示图标-->
    <!--bootstrap引用-->
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
    <style>
        body {
            font: 12px/1.5 arial, STHeiti, 'Microsoft YaHei', \5b8b\4f53;
            background: #eee;
            width: 100%;
        }

        .label-success {
            background-color: #00BC9B;
        }

        .menu-txt li {
            float: left;
            white-space: nowrap;
            font-weight: 400;
            padding: 15px 5px;
        }

        .menu-txt li a {
            color: #888;
            font-size: 14px;
            text-decoration: none;
        }

        .menu-txt li a:HOVER {
            color: #00BC9B;
        }

        .menu_txt_Active {
            color: #00BC9B !important;
        }

        .list-group-item {
            margin-bottom: 2px;
            border: none;
            border-bottom: 1px solid #ddd;
            padding: 15px 15px;
        }

        .M-box a:HOVER {
            background: #00BC9B;
        }

        .M-box .active {
            float: left;
            margin: 0 5px;
            width: 38px;
            height: 38px;
            line-height: 38px;
            background: #00BC9B;
            color: #fff;
            font-size: 14px;
            border: 1px solid #00BC9B;
        }

        .navbar-left {
            margin: 28px 30px;
        }
        a {
            color: #333;
            cursor: pointer;
        }

        .nav-pills > li > a {
            border-radius: 0px;
            display: block;
            width: 100%;
            font-size: 20px;
            line-height: 50px;
            text-align: center;
        }

        .nav > li > a {
            padding: 5px 15px;
        }

        .nav-pills ul li:hover>a{
            background: #00BC9B;
        }
        .nav-pills li dl{
            float: none;
            display: none;
            position: absolute;
            left: 0;
            top: 60px;
            width: 100%;
            background-color: #00BC9B;
        }
        .nav-pills li dl dd{
            position: relative;
            border-bottom: 1px solid white;
            text-align: center;
        }
        .nav-pills li dl dd a{
            display:block;
            position: relative;
            width: 100%;
            font-size: 16px;
            line-height: 50px;
        }
        .nav-pills li:hover>dl{
            display: block;
        }
        .nav-pills > li.active > a, .nav-pills > li.active > a:focus, .nav-pills > li.active > a:hover {
            background-color: #00BC9B;
        }
    </style>
</head>
<body>

<!--大图-->
<#--<div class="talk-datu">-->
    <#--<img src="/img/front/banner-talk.jpg"/>-->
<#--</div>-->
<!--主体-->
<div class="container" style="background: #fff;width: 100%;">
    <div class="row">
        <div class="col-md-8 col-md-offset-2"style="height: 80px;">
            <!-- 导航上 -->
            <div>
                <h2 class="pull-left"
                    style="font-size: 16px; line-height: 14px; white-space: nowrap; margin-top: 16px; margin-bottom: 30px">
                    分享与求助</h2>
                </div>
            <button id="push" style="margin-top: 5px; background: #00BC9B"
                    class="pull-right btn btn-success" onclick="topush()">
                <span class="glyphicon glyphicon-pencil "></span> 我要发布
            </button>

            </div>

        </div>

        <div class="col-md-8 col-md-offset-2" style="z-index: 2;">
            <!--导航-->
            <ul class="nav nav-pills">
                <li role="presentation" id="forumid_0"><a href="#" onclick="talktongji('1','0','1','${openId}')">全部</a></li>
                <li role="presentation" id="forumid_0" class="nav" style="width: 160px;"><a href="#" >话题讨论</a>
                     <dl class="clearfix">

                         <#list ForumList as forum>
                         <dd role="presentation" id="forumid_${forum.id}">

                        <a href="javascript:void(0);"
                           onclick="talktongji('1','${forum.id}','${sort}','${openId}')">${forum.name}</a>

                        </dd>
                        </#list>
                    </dl>
                </li>
            </ul>
            <div style="height: 2px; background: #00BC9B"></div>
        </div>
        <script type="text/javascript">
            //选择样式的改变
            $("#forumid_" +"${forumid}").addClass("active");
        </script>

        <div class="col-md-8 col-md-offset-2"style="height: 40px;width: 100%;">
            <ul class="menu-txt">
                <li><a href="#" id="sort1"
                       onclick="talktongji('1','${forumid}','1','${openId}')">最新回复</a></li>
                <li style="color: #ededed">|</li>
                <li><a href="#" id="sort2"
                       onclick="talktongji('1','${forumid}','2','${openId}')">最新发表</a></li>
                <li style="color: #ededed">|</li>
                <li><a href="#" id="sort3"
                       onclick="talktongji('1','${forumid}','3','${openId}')">最热</a></li>
                <li style="color: #ededed">|</li>
                <li><a href="#" id="sort4"
                       onclick="talktongji('1','${forumid}','4','${openId}')">精华</a></li>
            </ul>
        </div>
        <script type="text/javascript">
            //选择样式的改变
            $("#sort" +${sort}).addClass("menu_txt_Active");
        </script>
        <div class="col-md-8 col-md-offset-2" style="z-index: 1;">
            <ul class="list-group">

                <#list pageModel.rows as topic>

                    <li class="list-group-item" style="margin-bottom: 25px;">
                        <div class="box">
                            <div>
                                <a style="font-size: 14px; color: 0x333" name="username">
                                    ${topic.createName}
                                </a>
                                <span class="pull-right">${topic.createTime?string('yyyy-MM-dd hh:mm')}</span>
                            </div>

                            <div  style="margin-bottom: 22px; margin-top: 9px">
                                <a href="javaScript:void(0)" id="${topic.id }" name="topic"
                                   style="font-size: 16px; color: #00BC9B">${topic.title }</a>
                                <#if "${topic.essence}"==1>
                                    <span class="label label-danger">精华</span>
                                </#if>
                                <#if "${topic.type}"==2>
                                    <span class="label label-success">置顶</span>
                                </#if>
                            </div>
                            <div style="margin-bottom: 22px; margin-top: 9px">
                                <div class="pull-left">
                                    <ul style="color: #999;">
                                        <a>发表在[${topic.forum.name}]</a>
                                    </ul>
                                </div>
                                <div class="pull-right">
										<span> "回复" <span>${topic.replyCount}</span>
										</span> <span>|</span> <span> "赞" <span>${topic.thumbsUp}</span>
										</span> <span>|</span> <span> "浏览" <span>${topic.browse}</span>
										</span>
                                </div>
                            </div>

                        </div>
                    </li>
                </#list>

            </ul>
        </div>
    </div>
    <!-- 分页 -->
    <script src="${ctx }/js/plugins/pagination/js/jquery.pagination.js"></script>

    <div class="row">
        <div class="col-md-5"></div>
        <div class="col-md-7">
            <div class="M-box"></div>
        </div>
    </div>

<script type="text/javascript">
    $('.M-box').pagination({
        totalData: "${pageModel.total}",
        showData: "${pageModel.pageSize}",
        current: "${pageModel.pageNum}",
        pageCount: "${pageModel.pages}",
        coping: true,
        callback: function (index) {
            console.log(index);
            currentPage = index.getCurrent();
            console.log("currentPage:" + currentPage);
            window.location.href = "/wx/forum/indexlist?"+"forumid="+forumid+"&sort="+sort+"&openId="+openId+"&pageNum=" + currentPage;
        }
    }, function (api) {
        currentPage = api.getCurrent();
        console.log("currentPage:" + currentPage);
        $('.now').text(api.getCurrent());
    });
</script>

<script type="text/javascript">
    $(document).ready(function () {
        //页面加载完自动执行
    });

    $("[name='username']").click(function () {
        var id = $(this).attr("id");
        window.location.href = "integral/allintegralAct!personalByuserid.action?userId=" + id;
    });

    //-----------------进入论坛----------------------------
    function talktongji(course, forumid, sort, openId) {//参数：课程类别1=第一门，板块类别0=全部，排序方式1=第一种排序
        window.location.href = "/wx/forum/indexlist?" + "course="+ course +"&" + "forumid=" + forumid + "&" + "sort=" + sort + "?openId=" +openId;
    }

    //-----------------进入帖子----------------------------
    $("[name='topic']").click(function () {
        var id = $(this).attr("id");
        window.location.href = "/wx/forum/indexdetail/" + id+
                "?openId=" +"${openId}" ;
    });

    function topush() {
        window.open('/wx/forum/indexlist/toPush?course='+"${course}"+"&"+"forumid="+"${forumid}"+"&"+"sort="+"${sort}"+"&openId="+"${openId}");
    }

</script>
</body>
</html>
