<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1"/>
    <title>学习讨论</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="shortcut icon"
          href="${cxt }/static/plugins/home/img/favicon.ico"
          type="image/x-icon"/>
    <!--地址栏和标签上显示图标-->
    <!--bootstrap引用-->
    <link rel="stylesheet"
          href="${cxt }/static/plugins/home/css/style-home.css"/>
    <%--ue编辑器 --%>
    <script type="text/javascript" charset="utf-8"
            src="${cxt }/resources/ueditor135/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8"
            src="${cxt }/resources/ueditor135/ueditor.all.min.js">
    </script>

    <script type="text/javascript" charset="utf-8"
            src="${cxt }/resources/ueditor135/lang/zh-cn/zh-cn.js"></script>
    <link rel="stylesheet" type="text/css"
          href="${cxt }/css/pagination.css"
          media="screen">
    <link rel="stylesheet" type="text/css"
          href="${cxt }/css/normalize.css"
          media="screen">
    <link href="${cxt}/css/indexstyle.css"
          type="text/css" rel="stylesheet">
    <style>
        body {
            font: 12px/1.5 arial, STHeiti, 'Microsoft YaHei', \5b8b\4f53;
            background: #eee;
        }

        .nav-pills > li.active > a, .nav-pills > li.active > a:focus, .nav-pills > li.active > a:hover {
            background-color: #00BC9B;
        }

        .label-success {
            background-color: #00BC9B;
        }

        a {
            color: #333;
            cursor: pointer;
        }

        .nav-pills > li > a {
            border-radius: 0px;
        }

        .nav > li > a {
            padding: 5px 15px;
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
    </style>
</head>
<body>
<!--大图-->
<div class="talk-datu">

    <img src="/static/plugins/home/img/banner-talk.jpg"/>
</div>
<!--主体-->
<div class="container" style="background: #fff">
    <div class="row">
        <div class="col-md-8 col-md-offset-2">
            <!-- 导航上 -->
            <div>
                <h2 class="pull-left"
                    style="font-size: 16px; line-height: 14px; white-space: nowrap; margin-top: 16px; margin-bottom: 30px">
                    分享与求助</h2>
                <button id="push" style="margin-top: 10px; background: #00BC9B"
                        class="pull-right btn btn-success">
                    <span class="glyphicon glyphicon-pencil "></span> 我要发布
                </button>
            </div>

        </div>

        <div class="col-md-8 col-md-offset-2">
            <!--导航-->
            <ul class="nav nav-pills">
                <li role="presentation" id="forumid_0"><a href="#"
                                                          onclick="talktongji('1','0','1')">全部</a></li>
                <#list ForumList as forum>
                    <li role="presentation" id="forumid_${forum.id}"><a href="#"
                </#list>
            </ul>
            <div style="height: 2px; background: #00BC9B"></div>
        </div>
        <script type="text/javascript">
            //选择样式的改变
            $("#forumid_" + "${forumid}").addClass("active");
        </script>

        <div class="col-md-8 col-md-offset-2">
            <ul class="menu-txt">
                <li><a href="#" id="sort1" onclick="talktongji('${course}','${forumid}',1)"> 最新回复 </a></li>
                <li style="color:#ededed"> |</li>
                <li><a href="#" id="sort2" onclick="talktongji('${course}','${forumid}',2)"> 最新发表 </a></li>
                <li style="color: #ededed"> |</li>
                <li><a href="#" id="sort3" onclick="talktongji('${course}','${forumid}',3)"> 最热 </a></li>
                <li style="color: #ededed"> |</li>
                <li><a href="#" id="sort4" onclick="talktongji('${course}','${forumid}',4)"> 精华 </a></li>
            </ul>
        </div>
        <script type="text/javascript">
            //选择样式的改变
            $("#sort" +${sort}).addClass("menu_txt_Active");
        </script>
        <div class="col-md-8 col-md-offset-2">
            <ul class="list-group">
                <#list pageModel.list as topic>

                    <li class="list-group-item">
                        <div class="box">
                            <div>
                                <a href="javaScript:void(0)" id="${topic.id }" name="topic"
                                   style="font-size: 14px; color: 0x333">${topic.title }</a>
                                <#if topic.essence == 1>
                                    <span class="label label-danger">精华</span>
                                </#if>
                                <#if topic.type == 2>
                                    <span class="label label-success">置顶</span>
                                </#if>
                            </div>
                            <div style="margin-bottom: 22px; margin-top: 9px">
                                <div class="pull-left">
                                    <p style="color: #999">

                                        <a href="javaScript:void(0)" style="color: #24e5bf"
                                           id="${topic.createId}" name="username">${topic.createName}</a>
                                        &nbsp; &nbsp; 发表在 <a>[${topic.forum.name}]</a> &nbsp; &nbsp;
                                        <#if topic.lastReply??>
                                            最后回复 &nbsp; &nbsp;
                                            <fmt:formatDate value="${topic.modifyDate}"
                                                            pattern="yyyy-MM-dd hh:mm "/>
                                        </#if>
                                    </p>
                                </div>
                                <div class="pull-right">
                                    <span> "回复" <span>${topic.replyCount}</span>
                                    </span> <span>|</span> <span> "赞" <span>${topic.likee}</span>
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
    <script
            src="${cxt }/static/plugins/pagination/js/jquery.pagination.js"></script>

    <div class="row">
        <div class="col-md-5"></div>
        <div class="col-md-7">
            <div class="M-box"></div>
        </div>
    </div>
</div>

<!-- 提问模态框 -->
<div class="modal fade" id="tiwenModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">&times;
                </button>
                <h4 class="modal-title" id="myModalLabel">提问</h4>
            </div>
            <div class="modal-body">
                <form>
                    <div class="form-group">
                        <!-- <label for="newstitle">问题说明</label>  -->
                        <select id="selectforum" style="width: 72px; height: 30px">
                            <#list ForumList as forum>
                                <option value="${forum.id}">${forum.name}</option>
                            </#list>
                        </select> <input type="text"
                                         style="width: 480px; border: 1px solid #d4d4d4; border-radius: 3px; height: 30px"
                                         id="talktitle" placeholder="请在这里概述你的问题">
                    </div>
                    <div class="form-group">
                        <label for="newstitle">问题补充</label>
                        <script id="editor" type="text/plain" name="new_content"></script>
                    </div>
                    <script type="text/javascript">
                        //实例化编辑器
                        UE.delEditor('editor');
                        var ue = UE.getEditor('editor');
                    </script>

                </form>


            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                </button>
                <button type="button" class="btn btn-primary" id="saveQuestion">
                    提交
                </button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal -->
</div>

<script type="text/javascript">
    $('.M-box').pagination({
        totalData: "${pageModel.total}",
        showData: "${pageModel.pageSize}",
        current: "${pageModel.pageNum}",
        pageCount: "${pageModel.pages}",
        coping: true,
        callback: function (index) {
            currentPage = index.getCurrent();
            console.log("currentPage:" + currentPage);
            window.location.href = "${pageContext.request.contextPath}/talk/talkMangeAction!talkyema.action?course=${course}&forumid=${forumid}&sort=${sort}&pageNum="
                + currentPage;
        }
    }, function (api) {
        currentPage = api.getCurrent();
        console.log("currentPage:" + currentPage);
        $('.now').text(api.getCurrent());
    });
</script>

<script type="text/javascript">

    $("[name='username']").click(function () {
        var id = $(this).attr("id");
        window.location.href = "${cxt }/integral/allintegralAct!personalByuserid.action?userId=" + id;
    });

    //-----------------进入论坛----------------------------
    function talktongji(course, forumid, sort) {//参数：课程类别1=第一门，板块类别0=全部，排序方式1=第一种排序
        window.location.href = "${cxt }/talk/talkMangeAction!talkyema.action?" +
            "course=" + course + "&" +
            "forumid=" + forumid + "&" +
            "sort=" + sort;
    }
    //-----------------进入帖子----------------------------
    $("[name='topic']").click(function () {
        var id = $(this).attr("id");
        window.location.href = "${cxt }/talk/topicAction!show.action?id=" + id;
    });


    $("#push").click(function () {
        //判断是否已登录
        var s = "";
        if (s == "null") {
            if (confirm("请先登录!")) {
                window.location.href = "${cxt }/login.jsp";
            } else {
                return false;
            }
        } else {

            //$('#tiwenModal').modal('show');
            window.location.href = "${cxt }/talk/topicAction!toPush.action";
        }
    });

    //学习指导
    function xuexizhidao() {

        window.location.href = "${cxt }/tool/DeveToolMangeAction!showDaoxueQiantai.action";

    }

    //课程学习
    function coursefg() {
        window.location.href = "${cxt }/learn/courseLearnMangeAction!toCourseLearnList.action";
    }

    //资源库
    function yematongji(dangqianyema) {
        window.location.href = "${cxt }/courseUP/CourseUploadAction!fileyema.action?DangQianYeMa=" + dangqianyema;

    }
</script>
</body>
</html>
